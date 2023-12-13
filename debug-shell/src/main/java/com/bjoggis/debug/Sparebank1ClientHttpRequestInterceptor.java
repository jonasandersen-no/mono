package com.bjoggis.debug;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Sparebank1ClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

  private static final String BASE_URL = "https://api-auth.sparebank1.no";

  private static final Logger logger = LoggerFactory.getLogger(
      Sparebank1ClientHttpRequestInterceptor.class);
  private String accessToken;
  private Date expiry;

  private final Sparebank1Properties properties;


  private final RestTemplate restTemplate = new RestTemplate();

  public Sparebank1ClientHttpRequestInterceptor(Sparebank1Properties properties) {
    this.properties = properties;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {

    if (accessToken == null || expiry == null || expiry.before(new Date())) {

      logger.info("Refreshing access token");

      MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
      requestBody.add("client_id", properties.clientId());
      requestBody.add("client_secret", properties.clientSecret());
      requestBody.add("refresh_token", "9bf7e69881a74f629709df3358354027");
      requestBody.add("grant_type", "refresh_token");

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody,
          headers);

      ParameterizedTypeReference<MultiValueMap<String, String>> responseType = new ParameterizedTypeReference<>() {
      };

      ResponseEntity<MultiValueMap<String, String>> responseEntity = restTemplate.exchange(BASE_URL,
          HttpMethod.POST, requestEntity, responseType);

      accessToken = responseEntity.getBody().get("access_token").get(0);
      final String expiryString = responseEntity.getBody().get("expires_in").get(0);

      expiry = Date.from(Instant.ofEpochSecond(Long.parseLong(expiryString)));
    } else {
      logger.info("Reusing access token. Expires in: {}", expiry);
    }

    request.getHeaders().setBearerAuth(accessToken);

    return execution.execute(request, body);
  }

}
