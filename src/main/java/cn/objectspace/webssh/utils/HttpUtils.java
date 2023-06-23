package cn.objectspace.webssh.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author yonsun
 * 4月 24, 2023.
 */
public class HttpUtils {
    // 类主体部分
    private static final RestTemplate restTemplate = new RestTemplate();


    public static <T> T sendRequest(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> responseType) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);
        return responseEntity.getBody();
    }

    public static <T> T sendGetRequest(String url, HttpHeaders headers, Class<T> responseType) throws RestClientException {
        return sendRequest(url, HttpMethod.GET, headers, null, responseType);
    }

    /**
     * 发送 HTTP POST 请求
     *
     * @param url           请求 URL
     * @param headers       请求头
     * @param body          请求体
     * @param responseType  响应数据类型
     * @param <T>           响应数据类型
     * @return 响应数据
     * @throws RestClientException 如果请求出错
     */
    public static <T> T sendPostRequest(String url, HttpHeaders headers, Object body, Class<T> responseType) throws RestClientException {
        return sendRequest(url, HttpMethod.POST, headers, body, responseType);
    }


}