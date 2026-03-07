package pharmacie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import pharmacie.dao.CategorieRepository;
import pharmacie.dao.FournisseurRepository;
import pharmacie.entity.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final FournisseurRepository fournisseurRepository;
    private final CategorieRepository categorieRepository;

    @Override
    public void run(String... args) {

        if (fournisseurRepository.count() > 0)
            return;

        Fournisseur f1 = fournisseurRepository.save(
                new Fournisseur("Pfizer", "wiem+pfizer@gmail.com"));

        Fournisseur f2 = fournisseurRepository.save(
                new Fournisseur("Sanofi", "wiem+sanofi@gmail.com"));

        Fournisseur f3 = fournisseurRepository.save(
                new Fournisseur("Bayer", "wiem+bayer@gmail.com"));

        List<Categorie> categories = categorieRepository.findAll();

        if (categories.size() >= 2) {

            categories.get(0).getFournisseurs().add(f1);
            categories.get(0).getFournisseurs().add(f2);

            categories.get(1).getFournisseurs().add(f2);
            categories.get(1).getFournisseurs().add(f3);

            categorieRepository.saveAll(categories);
        }
    }
}