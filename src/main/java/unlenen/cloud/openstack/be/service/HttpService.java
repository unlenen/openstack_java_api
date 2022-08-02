package unlenen.cloud.openstack.be.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.Header;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.constant.ParameterType;

/**
 *
 * @author Nebi
 */
@Service
public class HttpService {

    Logger log = LoggerFactory.getLogger(HttpService.class);
    final static String MEDIA_TYPE_OCTET_STREAM = "application/octet-stream";

    SSLSocketFactory socketFactory;
    X509TrustManager x509TrustManager;

    public HttpService() {
        x509TrustManager = new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        };

        TrustManager[] trustAllCerts = new TrustManager[] {
                x509TrustManager
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            socketFactory = sc.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);
        } catch (GeneralSecurityException e) {
        }

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    public ResponseEntity pushMultiPart(Call call, String baseURL, String data, HttpHeaders headers,
            Parameter[] parameters) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity;

        List<Parameter> uriParameterList = new ArrayList();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (Parameter parameter : parameters) {
            switch (parameter.getParameterType()) {
                case URI: {
                    uriParameterList.add(parameter);
                    break;
                }
                case REQUEST: {
                    body.add(parameter.getKey(), (String) parameter.getValue());
                    break;
                }
                case FILE: {
                    body.add(parameter.getKey(), new FileSystemResource(new File(parameter.getValue())));
                    break;
                }
            }
        }

        requestEntity = new HttpEntity<>(body, headers);

        return commonCall(call, baseURL, requestEntity, uriParameterList.toArray(new Parameter[0]));
    }

    public ResponseEntity commonCall(Call call, String baseURL, HttpEntity requestEntity, Parameter[] parameters) {
        return commonCall(call, baseURL, requestEntity, parameters, null, null);
    }

    public ResponseEntity commonCall(Call call, String baseURL, HttpEntity requestEntity, Parameter[] parameters,
            RequestCallback requestCallback, ResponseExtractor responseExtractor) {
        RestTemplate restTemplate = new RestTemplate();
        return commonCall(restTemplate, call, baseURL, requestEntity, parameters, requestCallback, responseExtractor);
    }

    public ResponseEntity commonCall(RestTemplate restTemplate, Call call, String baseURL,
            HttpEntity requestEntity, Parameter[] parameters,
            RequestCallback requestCallback, ResponseExtractor responseExtractor) {

        if (requestCallback != null) {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setBufferRequestBody(false);
            restTemplate.setRequestFactory(requestFactory);
        }
        if (call.type() == HttpMethod.PATCH) {
            HttpClient client = HttpClients.createDefault();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
        }

        boolean hasQuery = false;
        String url = baseURL + call.url();
        Map<String, String> params = new HashMap();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseURL + call.url());
        for (Parameter parameter : parameters) {
            if (parameter.getValue() == null || "".equals(parameter.getValue())) {
                continue;
            }
            switch (parameter.getParameterType()) {
                case REQUEST -> {
                    builder.queryParam(parameter.getKey(), parameter.getValue());
                    hasQuery = true;
                }
                case URI ->
                    params.put(parameter.getKey(), parameter.getValue());

            }
        }
        if (hasQuery) {
            url = builder.build().encode().toUriString();
        }

        if (log.isDebugEnabled()) {
            log.debug("[Request] URL:" + url + " type:" + call.type());
        }

        ResponseEntity<String> response;

        switch (call.type()) {
            case PATCH:
                String output = restTemplate.patchForObject(url, requestEntity, String.class, params);
                return new ResponseEntity(output, HttpStatus.OK);
            default:
                if (requestCallback != null) {

                    for (String key : params.keySet()) {
                        url = url.replaceAll("\\{" + key + "\\}", params.get(key));
                    }
                    if (call.isDownload()) {
                        restTemplate.execute(url, call.type(), requestCallback, responseExtractor,
                                Void.class, params);
                        return new ResponseEntity<String>("", call.statusCode());
                    } else {
                        String body = restTemplate.execute(url, call.type(), requestCallback,
                                (HttpMessageConverterExtractor<String>) responseExtractor,
                                String.class, params);
                        return new ResponseEntity<String>(body, call.statusCode());
                    }
                } else
                    return restTemplate.exchange(url, call.type(), requestEntity, String.class, params);
        }

    }

    public ResponseEntity push(Call call, String baseURL, String data, final HttpHeaders headers,
            Parameter[] parameters) {
        HttpEntity requestEntity = null;
        RestTemplate restTemplate = new RestTemplate();

        RequestCallback requestCallback = null;
        ResponseExtractor responseExtractor = null;
        if (call.mediaType() != null) {

            if (data != null) {
                requestEntity = new HttpEntity<>(data, headers);
                headers.setContentType(org.springframework.http.MediaType.parseMediaType(call.mediaType()));
            }
            if (MEDIA_TYPE_OCTET_STREAM.equals(call.mediaType())) {

                final Parameter parameter = Arrays.stream(parameters)
                        .filter(p -> p.getParameterType() == ParameterType.FILE)
                        .findFirst().get();

                requestCallback = new RequestCallback() {
                    @Override
                    public void doWithRequest(final ClientHttpRequest request) throws IOException {

                        for (String headerKey : headers.keySet()) {
                            request.getHeaders().add(headerKey, headers.get(headerKey) + "");
                        }

                        request.getHeaders().add("Content-type", MEDIA_TYPE_OCTET_STREAM);
                        IOUtils.copy(new FileInputStream(new File(parameter.getValue())), request.getBody());
                    }
                };
                responseExtractor = new HttpMessageConverterExtractor<String>(String.class,
                        restTemplate.getMessageConverters());

            }
        }

        if (requestEntity == null) {
            requestEntity = new HttpEntity<>(headers);
        }

        return commonCall(restTemplate, call, baseURL, requestEntity, parameters, requestCallback, responseExtractor);
    }

    public ResponseEntity downloadFile(Call call, String baseURL, String data, final HttpHeaders headers,
            Parameter[] parameters) {
        HttpEntity requestEntity = null;

        final Parameter filePathParameter = Arrays.stream(parameters)
                .filter(p -> p.getParameterType() == ParameterType.FILE)
                .findFirst().get();

        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void doWithRequest(final ClientHttpRequest request) throws IOException {

                for (String headerKey : headers.keySet()) {
                    request.getHeaders().add(headerKey, headers.get(headerKey) + "");
                }

                request.getHeaders().add("Content-type", MEDIA_TYPE_OCTET_STREAM);
            }
        };

        ResponseExtractor<Void> responseExtractor = response -> {
            Path path = Paths.get(filePathParameter.getValue());
            Files.copy(response.getBody(), path);
            return null;
        };

        return commonCall(call, baseURL, requestEntity, parameters, requestCallback, responseExtractor);

    }

    public ResponseEntity call(Call call, String baseURL, Parameter[] extraHeaders, Parameter[] parameters,
            String reqBody) throws IOException {

        HttpHeaders headers = prepareHeaders(call, extraHeaders);

        String data = null;
        if (call.bodyFile() != null && !"".equals(call.bodyFile())) {
            switch (call.type()) {
                case DELETE, PUT, POST, PATCH -> {
                    data = enrichPayloadData(readResourceFileToString(call.bodyFile()), parameters);
                }
            }
        }

        if (reqBody != null) {
            data = reqBody;
        }

        switch (call.type()) {
            default:
            case GET: {
                if (call.isDownload()) {
                    return downloadFile(call, baseURL, data, headers, parameters);
                }
            }
            case DELETE: {
                return commonCall(call, baseURL, new HttpEntity<>(headers), parameters);
            }
            case PATCH:
            case PUT:
            case POST: {
                if (org.springframework.http.MediaType.MULTIPART_FORM_DATA.getType().equals(call.mediaType())) {
                    return pushMultiPart(call, baseURL, data, headers, parameters);
                }

                return push(call, baseURL, data, headers, parameters);
            }
        }

    }

    private HttpHeaders prepareHeaders(Call call, Parameter[] extraHeaders) {
        HttpHeaders headers = new HttpHeaders();
        for (Header header : call.headers()) {
            headers.add(header.key(), header.value());
        }
        for (Parameter header : extraHeaders) {
            headers.add(header.getKey(), header.getValue());
        }
        return headers;
    }

    private String enrichPayloadData(String payloadData, Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            payloadData = payloadData.replaceAll("\\$\\{" + parameter.getKey() + "\\}", parameter.getValue());
        }
        return payloadData;
    }

    private String readResourceFileToString(String path) throws IOException {
        if (path == null) {
            return null;
        }
        return copyStreamToString(getResourceStream(path));
    }

    private InputStream getResourceStream(String payloadFilePath) {
        InputStream is = HttpService.class.getClassLoader().getResourceAsStream(payloadFilePath);
        return is;
    }

    private String copyStreamToString(InputStream stream) throws IOException {
        StringBuilder str = new StringBuilder();
        while (true) {
            byte[] data = new byte[4096];

            int count = stream.read(data);
            if (count < 0) {
                break;
            }
            str.append(new String(data, 0, count));
        }
        return str.toString();
    }
}
