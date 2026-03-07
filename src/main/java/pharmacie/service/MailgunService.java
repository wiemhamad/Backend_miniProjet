package pharmacie.service;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailgunService {

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    @Value("${mailgun.base-url}")
    private String baseUrl;

    public void sendEmail(String to, String subject, String text) {

        HttpResponse<JsonNode> response = Unirest.post(baseUrl + "/v3/" + domain + "/messages")
                .basicAuth("api", apiKey)
                .field("from", "Pharmacie <mailgun@" + domain + ">")
                .field("to", to)
                .field("subject", subject)
                .field("text", text)
                .asJson();

        System.out.println("Mailgun response : " + response.getBody());
    }
}