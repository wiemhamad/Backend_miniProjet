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

    @PostMapping("/test-mail")
    public String testMail() {

        mailgunService.sendEmail(
                "wiem.hamad@etud.univ-jfc.fr",
                "Test Mailgun",
                "Votre backend fonctionne correctement.");

        return "Email envoyé";
    }
}