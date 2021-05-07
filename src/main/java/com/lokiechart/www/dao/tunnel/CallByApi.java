package com.lokiechart.www.dao.tunnel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CallByApi {
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(CallByApi.class);

    public String get(String url, HttpHeaders httpHeaders) {
        return callApiEndpoint(url, HttpMethod.GET, httpHeaders, null);
    }

    public String post(String url, HttpHeaders httpHeaders, Object body) {
        return callApiEndpoint(url, HttpMethod.POST, httpHeaders, body);
    }

    public String delete(String url, HttpHeaders httpHeaders, Object body) {
        return callApiEndpoint(url, HttpMethod.DELETE, httpHeaders, body);
    }

    private String callApiEndpoint(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Object body) {
//        httpHeaders.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));
        String response = null;
        try {
            response = restTemplate.exchange(url, httpMethod, new HttpEntity<>(body, httpHeaders), String.class).getBody();
        } catch (RuntimeException e) {
            logger.error(url);
            logger.error(httpMethod.toString());
            logger.error(body.toString());
            e.printStackTrace();
        }
        logger.error("ERROR ::::::: " + response);
        return response;
    }

}
