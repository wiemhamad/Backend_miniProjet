package pharmacie.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Base64;
import java.util.Map;

@Service
public class MailgunService {

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    @Value("${mailgun.base-url}")
    private String baseUrl;

    public void sendEmail(String to, String subject, String text) {

        String url = baseUrl + "/v3/" + domain + "/messages";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        String auth = "api:" + apiKey;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        headers.add("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> body = Map.of(
                "from", "Pharmacie <mailgun@" + domain + ">",
                "to", to,
                "subject", subject,
                "text", text);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}