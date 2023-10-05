package com.bjoggis.mono.openai.adapter.out.openai;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class HasherFunction implements Function<String, String> {

  @Override
  public String apply(String userId) {
    String hashedUserID = "unknown";
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hashedId = digest.digest((userId).getBytes(StandardCharsets.UTF_8));
      hashedUserID = Base64.getEncoder().encodeToString(hashedId);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return hashedUserID;
  }
}
