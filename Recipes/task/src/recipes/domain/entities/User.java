package recipes.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotNull
    @NotBlank
    @Pattern(regexp = ".+@.+\\..+", message = "YOU MUST PROVIDE VALID EMAIL")
    private String email;


    @NonNull
    @NotNull
    @NotBlank
    @Size(min = 8, message = "PASSWORD MUST HAVE AT LEAST 8 CHARACTERS.")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Recipe> recipes;
}
