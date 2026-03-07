package pharmacie.service;

import org.springframework.stereotype.Service;

import kong.unirest.Unirest;

@Service
public class MailgunService {

    private static final String API_KEY = "TA_CLE_MAILGUN";
    private static final String DOMAIN = "sandboxxxxx.mailgun.org";

    public void sendEmail(String to, String subject, String text) {

        Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages")
                .basicAuth("api", API_KEY)
                .field("from", "Pharmacie <mailgun@" + DOMAIN + ">")
                .field("to", to)
                .field("subject", subject)
                .field("text", text)
                .asJson();
    }
}