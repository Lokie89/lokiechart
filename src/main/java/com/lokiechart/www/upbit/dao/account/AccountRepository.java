package com.lokiechart.www.upbit.dao.account;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.upbit.dao.account.dto.AssetResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author SeongRok.Oh
 * @since 2021/04/13
 */
@Component
@PropertySource("classpath:application-secret.yml")
public class AccountRepository {

    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;

    public List<AssetResponse> getAssets(String account) {
        String serverUrl = "https://api.upbit.com";

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/v1/accounts");
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            AssetResponse[] assetResponse = new ObjectMapper().readValue(EntityUtils.toString(entity, "UTF-8"), AssetResponse[].class);
            System.out.println(Arrays.asList(assetResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
