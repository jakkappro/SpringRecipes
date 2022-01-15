package recipes.business.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import recipes.business.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipe {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    private LocalDateTime date;

    @NotBlank
    private String description;

    @Size(min=1)
    @ElementCollection
    private List<String> ingredients;

    @Size(min=1)
    @ElementCollection
    private List<String> directions;

    @JsonIgnore
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @JsonIgnore
    @Column(name = "user_id")
    private Integer userId;

    public Recipe(String name, String description, List<String> ingredients, List<String> directions, String category, int userId) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
        this.date = LocalDateTime.now();
        this.userId = userId;
    }

    public void updateData(Recipe updated) {
        this.name = updated.name;
        this.description = updated.description;
        this.ingredients = updated.ingredients;
        this.directions = updated.directions;
        this.category = updated.category;
        this.date = LocalDateTime.now();
    }
}
