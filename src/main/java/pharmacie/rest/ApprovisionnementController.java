package pharmacie.rest;

import pharmacie.service.ApprovisionnementService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApprovisionnementController {

    private final ApprovisionnementService approvisionnementService;

    public ApprovisionnementController(ApprovisionnementService approvisionnementService) {
        this.approvisionnementService = approvisionnementService;
    }

    @PostMapping("/approvisionnement")
    public String approvisionnement() {

        approvisionnementService.lancerApprovisionnement();

        return "Demandes de devis envoyées aux fournisseurs";
    }
}