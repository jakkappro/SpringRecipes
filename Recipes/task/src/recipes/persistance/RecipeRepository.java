package recipes.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recipes.business.recipe.Recipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    Optional<Recipe> findById(Integer id);
    void deleteById(Integer id);
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainsIgnoreCaseOrderByDateDesc(String name);
}
