package recipes.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.repository.RecipRepository;

import java.util.List;

public interface FindMethod {

    public List<RecipDTOGet> findMethodRecip(String recherche);

}
