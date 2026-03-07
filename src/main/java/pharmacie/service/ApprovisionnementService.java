package pharmacie.service;

import pharmacie.dao.MedicamentRepository;
import pharmacie.entity.Categorie;
import pharmacie.entity.Fournisseur;
import pharmacie.entity.Medicament;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovisionnementService {

    private final MedicamentRepository medicamentRepository;
    private final MailgunService mailgunService;

    public ApprovisionnementService(
            MedicamentRepository medicamentRepository,
            MailgunService mailgunService) {

        this.medicamentRepository = medicamentRepository;
        this.mailgunService = mailgunService;
    }

    public void envoyerCommandes() {

        List<Medicament> medicaments = medicamentRepository.findByUnitesEnStockLessThanNiveauDeReappro();

        for (Medicament m : medicaments) {

            Categorie categorie = m.getCategorie();

            for (Fournisseur f : categorie.getFournisseurs()) {

                String message = "Bonjour " + f.getNom() + "\n\n" +
                        "Merci de nous envoyer un devis pour :\n\n" +
                        m.getNom() + "\n\n" +
                        "Cordialement\nPharmacie";

                mailgunService.sendEmail(
                        f.getEmail(),
                        "Demande de devis",
                        message);
            }
        }
    }
}