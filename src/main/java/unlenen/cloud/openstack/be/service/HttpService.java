package unlenen.cloud.openstack.be.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

        TrustManager[] trustAllCerts = new TrustManager[]{
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

    public ResponseEntity commonCall(Call call, String baseURL, HttpHeaders headers, Parameter[] parameters) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity requestEntity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseURL + call.url());

        Arrays.stream(parameters).filter(p -> p.getParameterType() == ParameterType.REQUEST && (p.getValue() != null && !"".equals(p.getValue()))).forEach(p -> {
            builder.queryParam(p.getKey(), p.getValue());
        });

        Map<String, String> params = new HashMap();
        Arrays.stream(parameters).filter(p -> p.getParameterType() == ParameterType.URI && (p.getValue() != null && !"".equals(p.getValue()))).forEach(p -> {
            params.put(p.getKey(), p.getValue());
        });

        ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUriString(), call.type(), requestEntity, String.class, params);
        return response;
    }

    public ResponseEntity push(Call call, String baseURL, String data, HttpHeaders headers, Parameter[] parameters) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        HttpEntity<String> requestEntity;

        if (call.mediaType() != null && data != null) {
            headers.setContentType(org.springframework.http.MediaType.parseMediaType(call.mediaType()));
            requestEntity = new HttpEntity<>(data, headers);
        } else {
            requestEntity = new HttpEntity<>(headers);
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
                default ->
                    throw new AssertionError();
            }
        }
        if (hasQuery) {
            url = builder.build().encode().toUriString();
        }

        response = restTemplate.exchange(url, call.type(), requestEntity, String.class, params);
        return response;
    }

    public ResponseEntity call(Call call, String baseURL, Parameter[] extraHeaders, Parameter[] parameters) throws IOException {

        HttpHeaders headers = prepareHeaders(call, extraHeaders);

        String data = null;
        if (call.bodyFile() != null && !"".equals(call.bodyFile())) {
            switch (call.type()) {
                case DELETE, PUT, POST -> {
                    data = enrichPayloadData(readResourceFileToString(call.bodyFile()), parameters);
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("[HTTP][CALL][" + call.type() + "] url : " + call.url() + " parameters:" + parameters);
        }

        switch (call.type()) {
            default:
            case GET:
            case DELETE: {
                return commonCall(call, baseURL, headers, parameters);
            }

            case PUT:
            case POST: {
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
