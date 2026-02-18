package pharmacie.rest;

import org.springframework.web.bind.annotation.*;

import pharmacie.service.ApprovisionnementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/approvisionnement")
@RequiredArgsConstructor
public class ApprovisionnementController {

    private final ApprovisionnementService service;

    // POST /api/approvisionnement/run?dryRun=true
    @PostMapping("/run")
    public ApprovisionnementService.ApproResult run(@RequestParam(defaultValue = "true") boolean dryRun) {
        return service.lancerApprovisionnement(dryRun);
    }
}
