package pharmacie.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import kong.unirest.Unirest;

@Service
public class MailgunService {

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    public void sendEmail(String to, String subject, String text) {

        Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
                .basicAuth("api", apiKey)
                .field("from", "Pharmacie <mailgun@" + domain + ">")
                .field("to", to)
                .field("subject", subject)
                .field("text", text)
                .asJson();
    }
}