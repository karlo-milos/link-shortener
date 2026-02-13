package hr.kmilos21.util;

import jakarta.enterprise.context.ApplicationScoped;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@ApplicationScoped
public class LinkShorteningSystem {

    public String createShortLink(String url){
        if (url == null || url.isEmpty()){
            return null;
        }

        try{
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            var hash = sha256.digest(url.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getUrlEncoder().encodeToString(hash);
            return encoded.substring(0, Math.min(encoded.length(), 10));
        } catch(Exception e){
            return null;
        }
    }
}
