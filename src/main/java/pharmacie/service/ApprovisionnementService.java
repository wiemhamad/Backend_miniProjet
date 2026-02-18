package pharmacie.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import pharmacie.dao.MedicamentRepository;
import pharmacie.entity.Categorie;
import pharmacie.entity.Fournisseur;
import pharmacie.entity.Medicament;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovisionnementService {

    private final MedicamentRepository medicamentRepository;
    private final JavaMailSender mailSender;

    public ApproResult lancerApprovisionnement(boolean dryRun) {
        List<Medicament> aReappro = medicamentRepository.medicamentsAReapprovisionner();

        // Map fournisseur -> (categorie -> meds)
        Map<Fournisseur, Map<Categorie, List<Medicament>>> parFournisseur = new LinkedHashMap<>();

        for (Medicament m : aReappro) {
            Categorie cat = m.getCategorie();
            for (Fournisseur f : cat.getFournisseurs()) {
                parFournisseur
                        .computeIfAbsent(f, k -> new LinkedHashMap<>())
                        .computeIfAbsent(cat, k -> new ArrayList<>())
                        .add(m);
            }
        }

        List<ApproMail> mails = new ArrayList<>();

        for (var entry : parFournisseur.entrySet()) {
            Fournisseur f = entry.getKey();
            Map<Categorie, List<Medicament>> byCat = entry.getValue();

            String subject = "[Pharmacie] Demande de devis de réapprovisionnement";
            String body = buildBody(f, byCat);

            mails.add(new ApproMail(f.getEmail(), subject, body, countMeds(byCat)));

            if (!dryRun) {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(f.getEmail());
                msg.setSubject(subject);
                msg.setText(body);
                mailSender.send(msg);
            }
        }

        return new ApproResult(aReappro.size(), mails, dryRun);
    }

    private int countMeds(Map<Categorie, List<Medicament>> byCat) {
        return byCat.values().stream().mapToInt(List::size).sum();
    }

    private String buildBody(Fournisseur f, Map<Categorie, List<Medicament>> byCat) {
        StringBuilder sb = new StringBuilder();
        sb.append("Bonjour ").append(f.getNom()).append(",\n\n");
        sb.append("Pouvez-vous nous transmettre un devis pour le réapprovisionnement des médicaments suivants ?\n");
        sb.append("(Médicaments dont le stock est inférieur au niveau de réapprovisionnement)\n\n");

        for (var e : byCat.entrySet()) {
            Categorie cat = e.getKey();
            sb.append("== ").append(cat.getLibelle()).append(" ==\n");

            // tri par nom pour un mail lisible
            List<Medicament> meds = e.getValue().stream()
                    .sorted(Comparator.comparing(Medicament::getNom))
                    .collect(Collectors.toList());

            for (Medicament m : meds) {
                sb.append("- ").append(m.getNom())
                        .append(" | stock=").append(m.getUnitesEnStock())
                        .append(" | seuil=").append(m.getNiveauDeReappro())
                        .append("\n");
            }
            sb.append("\n");
        }

        sb.append("Merci,\nPharmacie\n");
        return sb.toString();
    }

    // DTOs simples pour réponse REST
    public record ApproMail(String to, String subject, String body, int nbMedicaments) {
    }

    public record ApproResult(int nbMedicamentsAReappro, List<ApproMail> mails, boolean dryRun) {
    }
}
