package pharmacie.rest;

import pharmacie.service.ApprovisionnementService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApprovisionnementController {

    private ApprovisionnementService approvisionnementService;

    @PostMapping("/approvisionnement")
    public String lancerApprovisionnement() {

        approvisionnementService.lancerApprovisionnement();

        return "Demandes de devis envoyées aux fournisseurs";
    }
}