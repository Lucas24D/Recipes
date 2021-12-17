package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.domain.entities.Recipe;
import recipes.domain.services.RecipeService;
import recipes.domain.services.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class RecipeController {

    private final RecipeService recipes;
    private final UserService userService;

    @Autowired
    public RecipeController(RecipeService recipes, UserService userService) {
        this.recipes = recipes;
        this.userService = userService;
    }

    @PostMapping(path = "/recipe/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public String create(@Valid @RequestBody Recipe recipe) {
        recipe.setUser(userService.findUserByEmail(getLoggedUserEmail()).get());
        recipes.save(recipe);
        return String.format("{ \"id\":%s }", recipe.getId());
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Recipe> recipe = this.recipes.findById(id);
        return recipe.isPresent() ?
                ResponseEntity.ok(recipe) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Recipe> delete(@PathVariable Long id) {
        Optional<Recipe> recipe = this.recipes.findById(id);

        if (recipe.isPresent()) {
            if (recipe.get().getUser().getEmail().equals(getLoggedUserEmail())) {
                recipes.deleteById(id);
                return ResponseEntity.noContent().build();
            }

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "YOU CAN ONLY DELETE YOUR OWN RECIPE.");
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        Optional<Recipe> foundRecipe = this.recipes.findById(id);

        if (foundRecipe.isPresent()) {
            if (foundRecipe.get().getUser().getEmail().equals(getLoggedUserEmail())) {
                recipes.update(id, recipe);
                return ResponseEntity.noContent().build();
            }

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "YOU CAN ONLY UPDATE YOUR OWN RECIPE.");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/recipe/search", params = "category")
    public ResponseEntity<?> searchByCategory(@RequestParam("category") String category) {
        return ResponseEntity.ok(recipes.searchRecipeByCategory(category));
    }

    @GetMapping(value = "/recipe/search", params = "name")
    public ResponseEntity<?> searchByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(recipes.searchRecipeByName(name));
    }

    private String getLoggedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
