package recipes.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.domain.entities.Recipe;
import recipes.domain.repositories.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipes) {
        this.recipeRepository = recipes;
    }

    public void save(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> searchRecipeByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchRecipeByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Transactional
    public void update(Long id, Recipe r) {
        recipeRepository.updateRecipeById(r.getName(), r.getCategory(), r.getDate(), r.getDescription(), id);
        Recipe recipe = recipeRepository.findById(id).get();
        recipe.setIngredients(r.getIngredients());
        recipe.setDirections(r.getDirections());
        recipeRepository.save(recipe);
    }
}