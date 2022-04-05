package recipes.logic;

import java.util.List;

public class FinderRecip {

    public FindMethod findMethod;

    public FinderRecip() {
    }

    public FinderRecip(FindMethod findMethod) {
        this.findMethod = findMethod;
    }

    public List<RecipDTOGet> finderMethod(String recherche){

       return this.findMethod.findMethodRecip(recherche);
    }
}
