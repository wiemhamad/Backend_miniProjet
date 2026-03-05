package pharmacie.rest;

import pharmacie.service.ApprovisionnementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approvisionnement")
public class ApprovisionnementController {

    private final ApprovisionnementService service;

    public ApprovisionnementController(ApprovisionnementService service) {
        this.service = service;
    }

    @PostMapping(value = "/envoyer", produces = "application/json")
    public String envoyerCommandes() {

        service.envoyerCommandes();

        return "Emails envoyés aux fournisseurs";
    }
}