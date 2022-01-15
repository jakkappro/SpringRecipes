package recipes.business.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistance.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(@Autowired RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    public Optional<Recipe> findById(Integer id) {
        return repository.findById(id);
    }

    public boolean delete(Integer id) {
        var r = findById(id);
        if (r.isPresent()) {
            repository.deleteById(id);
            return true;
        } else
            return false;
    }

    public List<Recipe> findAllByName(String name) {
        return new ArrayList<>(repository.findAllByNameContainsIgnoreCaseOrderByDateDesc(name.toLowerCase(Locale.ROOT)));
    }

    public List<Recipe> findAllByCategory(String category) {
        return new ArrayList<>(repository.findAllByCategoryIgnoreCaseOrderByDateDesc(category.toLowerCase(Locale.ROOT)));
    }
}
