package unlenen.cloud.openstack.be.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.HashMap;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.Header;
import unlenen.cloud.openstack.be.constant.Parameter;

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
    
    public ResponseEntity commonCall(Call call, String baseURL, HttpEntity requestEntity, Parameter[] parameters) {
        RestTemplate restTemplate = new RestTemplate();
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
        
        if(log.isDebugEnabled()){
            log.debug("[Request] URL:"+ url+" type:"+call.type());
        }

        ResponseEntity<String> response;
        switch (call.type()) {
            case PATCH:
                String output = restTemplate.patchForObject(url, requestEntity, String.class, params);
                return new ResponseEntity(output, HttpStatus.OK);
            default:
                return restTemplate.exchange(url, call.type(), requestEntity, String.class, params);
        }
        
    }
    
    public ResponseEntity push(Call call, String baseURL, String data, HttpHeaders headers, Parameter[] parameters) {
        HttpEntity<String> requestEntity;
        
        if (call.mediaType() != null && data != null) {
            headers.setContentType(org.springframework.http.MediaType.parseMediaType(call.mediaType()));
            requestEntity = new HttpEntity<>(data, headers);
        } else {
            requestEntity = new HttpEntity<>(headers);
        }
        
        return commonCall(call, baseURL, requestEntity, parameters);
    }
    
    public ResponseEntity call(Call call, String baseURL, Parameter[] extraHeaders, Parameter[] parameters) throws IOException {
        
        HttpHeaders headers = prepareHeaders(call, extraHeaders);
        
        String data = null;
        if (call.bodyFile() != null && !"".equals(call.bodyFile())) {
            switch (call.type()) {
                case DELETE, PUT, POST,PATCH -> {
                    data = enrichPayloadData(readResourceFileToString(call.bodyFile()), parameters);
                }
            }
        }
                
        switch (call.type()) {
            default:
            case GET:
            case DELETE: {
                return commonCall(call, baseURL, new HttpEntity<>(headers), parameters);
            }
            case PATCH:
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
