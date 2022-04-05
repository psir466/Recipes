package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.logic.Ingredient;
import recipes.logic.Recip;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
