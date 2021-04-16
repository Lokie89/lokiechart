package com.lokiechart.www.dao.tunnel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
@PropertySource("classpath:application-secret.yml")
@Component
public class UpbitHeader implements ApiHeader {
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;


    public HttpHeaders getHeaders(String account, Map<String, Object> params) {
        JWTCreator.Builder tokenCreatorBuilder = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString());

        try {
            buildParams(tokenCreatorBuilder, params);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = tokenCreatorBuilder
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Content-Type", "application/json");
        headers.add("Authorization", authenticationToken);
        return headers;
    }

    private void buildParams(JWTCreator.Builder builder, Map<String, Object> params) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (Objects.isNull(params)) {
            return;
        }
        ArrayList<String> queryElements = new ArrayList<>();
        for (Map.Entry<String, Object> entity : params.entrySet()) {
            queryElements.add(entity.getKey() + "=" + entity.getValue());
        }

        String queryString = String.join("&", queryElements.toArray(new String[0]));


        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("UTF-8"));

        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
        builder.withClaim("query_hash", queryHash);
    }
}
