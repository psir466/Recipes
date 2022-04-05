package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recipes.logic.Recip;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipRepository extends JpaRepository<Recip, Long> {

    // recherche par nom case insenstive
    @Query("select r from Recip r where lower(r.name) like lower(concat('%', :nameToFind,'%'))")
    public List<Recip> findByNameRecip(@Param("nameToFind") String name);



    // recherche par category case insenstive
    @Query("select r from Recip r where lower(r.category) = lower(:categoryToFind)")
    public List<Recip> findByCategoryRecip(@Param("categoryToFind") String category);

}
