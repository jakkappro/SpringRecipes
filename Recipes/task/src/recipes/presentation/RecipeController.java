package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import recipes.business.recipe.Recipe;
import recipes.business.recipe.RecipeService;
import recipes.business.security.UserDetailsImp;
import recipes.business.user.User;
import recipes.business.user.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class RecipeController {

    private final RecipeService service;
    private final UserService userService;

    public RecipeController(@Autowired RecipeService service, @Autowired UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Integer> createRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetailsImp user) {
        if (areListsValid(recipe))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        recipe.setUserId(user.getId());
        recipe.setDate(LocalDateTime.now());
        return Map.of("id", service.save(recipe).getId());
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Integer id) {
        var recipe = service.findById(id);
        if (recipe.isPresent())
            return recipe.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable Integer id, @AuthenticationPrincipal UserDetailsImp user) {
        var recipe = service.findById(id);
        if (recipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if(recipe.get().getUserId() != user.getId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.delete(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@PathVariable Integer id, @Valid @RequestBody Recipe updated, @AuthenticationPrincipal UserDetailsImp user) {
        var recipe = service.findById(id);
        if (recipe.isPresent()) {
            if (areListsValid(updated))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            var r = recipe.get();

            if (r.getUserId() != user.getId())
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);

            updated.setDate(LocalDateTime.now());
            r.updateData(updated);
            service.save(r);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> findAllRecipesByName(@RequestParam Map<String, String> params) {
        if (params == null || params.size() != 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        System.out.println(params);

        if (params.containsKey("name"))
            return service.findAllByName(params.get("name"));

        if (params.containsKey("category"))
            return service.findAllByCategory(params.get("category"));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        if (!userService.save(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    private boolean areListsValid(Recipe r) {
        return !(r.getDirections() != null && r.getIngredients() != null);
    }
}
