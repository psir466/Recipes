package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.logic.Direction;
import recipes.logic.Ingredient;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
