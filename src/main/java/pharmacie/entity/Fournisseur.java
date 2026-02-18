package pharmacie.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @NonNull
    @NotBlank
    @Column(length = 255, nullable = false)
    private String nom;

    @NonNull
    @NotBlank
    @Email
    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @ManyToMany(mappedBy = "fournisseurs")
    @JsonIgnoreProperties({ "fournisseurs", "medicaments" })
    private Set<Categorie> categories = new LinkedHashSet<>();
}
