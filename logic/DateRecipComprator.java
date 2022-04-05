package recipes.logic;

import java.util.Comparator;

public class DateRecipComprator implements Comparator<Recip> {

    @Override
    public int compare(Recip o1, Recip o2) {
        // on multiplie par -1 pour avoir les plus récent avants les plus anciens
        return (o1.getDate().compareTo(o2.getDate())) * (-1);
    }
}
