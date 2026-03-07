package pharmacie.rest;

import pharmacie.service.ApprovisionnementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approvisionnement")
public class ApprovisionnementController {

    private final ApprovisionnementService service;

    public ApprovisionnementController(ApprovisionnementService service) {
        this.service = service;
    }

    @PostMapping
    public String lancerApprovisionnement() {

        service.envoyerCommandes();

        return "Demandes de devis envoyées aux fournisseurs";
    }
}