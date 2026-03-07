package pharmacie.rest;

import org.springframework.web.bind.annotation.*;
import pharmacie.service.MailgunService;

@RestController
@RequestMapping("/test-mail")
public class MailTestController {

    private final MailgunService mailgunService;

    public MailTestController(MailgunService mailgunService) {
        this.mailgunService = mailgunService;
    }

    @PostMapping
    public String envoyerMail() {

        mailgunService.sendEmail(
                "tonemail@gmail.com",
                "Test Mailgun Pharmacie",
                "Bonjour, ceci est un test d'envoi d'email depuis votre backend Spring Boot.");

        return "Email envoyé avec succès";
    }
}