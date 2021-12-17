package recipes.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recipes")
@NoArgsConstructor
@RequiredArgsConstructor
public class Recipe {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NonNull
    @NotBlank
    private String name;

    @NotNull
    @NonNull
    @NotBlank
    private String category;

    @NonNull
    private LocalDateTime date = LocalDateTime.now();

    @NotNull
    @NonNull
    @NotBlank
    private String description;

    @NotNull
    @NonNull
    @Size(min = 1, message = "YOU MUST PROVIDE AT LEAST 1 INGREDIENT.")
    @ElementCollection
    private List<String> ingredients;

    @NotNull
    @NonNull
    @Size(min = 1, message = "YOU MUST PROVIDE AT LEAST 1 DIRECTION.")
    @ElementCollection
    private List<String> directions;


    @JsonIgnore
    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "user_id")
    private User user;
}
