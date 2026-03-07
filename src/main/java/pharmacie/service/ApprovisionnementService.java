package pharmacie.service;

import pharmacie.entity.Medicament;
import pharmacie.entity.Fournisseur;
import pharmacie.dao.MedicamentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovisionnementService {

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private MailgunService mailgunService;

    public void lancerApprovisionnement() {

        List<Medicament> medicaments = medicamentRepository.findAll();

        for (Medicament medicament : medicaments) {

            if (medicament.getUnitesEnStock() < medicament.getNiveauDeReappro()) {

                List<Fournisseur> fournisseurs = medicament.getCategorie().getFournisseurs()
                        .stream()
                        .toList();

                for (Fournisseur fournisseur : fournisseurs) {

                    mailgunService.sendEmail(
                            "wiem.hamad@etud.univ-jfc.fr",
                            "Demande de devis - Pharmacie",
                            "Bonjour,\n\n" +
                                    "Le médicament " + medicament.getNom() +
                                    " est en rupture de stock.\n" +
                                    "Merci de nous envoyer un devis.\n\n" +
                                    "Cordialement.");
                }
            }
        }
    }
}