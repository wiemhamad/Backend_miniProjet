package pharmacie.service;

import pharmacie.entity.Medicament;
import pharmacie.dao.MedicamentRepository;

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

        List<Medicament> medicaments = medicamentRepository.findAll();

        StringBuilder message = new StringBuilder();

        message.append("Bonjour,\n\n");
        message.append("Merci de nous envoyer les médicaments suivants :\n\n");

        for (Medicament m : medicaments) {

            if (m.getUnitesEnStock() <= m.getNiveauDeReappro()) {

                int quantite = m.getNiveauDeReappro() * 2;

                message.append(m.getNom())
                        .append(" : ")
                        .append(quantite)
                        .append(" unités\n");
            }
        }

        mailgunService.sendEmail(
                "pharmacie@test.com",
                "Commande pharmacie",
                message.toString());
    }
}