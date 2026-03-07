package pharmacie.rest;

import pharmacie.service.ApprovisionnementService;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approvisionnement")
public class ApprovisionnementController {

    private final ApprovisionnementService service;

    public ApprovisionnementController(ApprovisionnementService service) {
        this.service = service;
    }

    @PostMapping("/approvisionnement")
    public ResponseEntity<Map<String, String>> approvisionner() {

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Demandes de devis envoyées aux fournisseurs");

        return ResponseEntity.ok(response);
    }
}