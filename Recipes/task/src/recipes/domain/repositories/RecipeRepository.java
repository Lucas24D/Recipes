package recipes.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recipes.domain.entities.Recipe;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Recipe SET name=?1, category=?2, date=?3, description=?4 WHERE id=?5")
    void updateRecipeById(String name, String category, LocalDateTime date, String description, Long id);
}