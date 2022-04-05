package recipes.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConverterDTO {

  //  @Autowired
  //  Authentication auth;


    @Autowired
    FindMethodByCategory findMethodByCategory;

    @Autowired
    FindMethodByName findMethodByName;

    @Autowired
    PasswordEncoder passwordEncoder;



    public RecipDTOGet convertRecipToRecipDTO(Recip recip) {

        RecipDTOGet recipDTOGet = new RecipDTOGet();

        recipDTOGet.setDescription(recip.getDescription());

        recipDTOGet.setCategory(recip.getCategory());


        for (Direction d : recip.getDirections()) {

            recipDTOGet.getDirections().add(d.getDirection());

        }

        for (Ingredient i : recip.getIngredients()) {

            recipDTOGet.getIngredients().add(i.getIngredient());

        }

        recipDTOGet.setDate(recip.getDate());

        recipDTOGet.setName(recip.getName());

        return recipDTOGet;
    }

    public Recip convertRecipDTOToRecip(RecipDTO recipDTO) {

        Recip recip = new Recip();

        recip.setDescription(recipDTO.getDescription());

        recip.setCategory(recipDTO.getCategory());

        for (String d : recipDTO.getDirections()) {

            recip.getDirections().add(new Direction(d));

        }

        for (String i : recipDTO.getIngredients()) {

            recip.getIngredients().add(new Ingredient(i));

        }

        recip.setName(recipDTO.getName());

        // date
        recip.setDate(LocalDateTime.now());

        // récupération du username connecté !!!
        // ne marche pas en faisant un bean ???
        // voir dans service.AuthService.java ????
        recip.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return recip;
    }

    public Recip updateRecipDTOToRecip(RecipDTO recipDTO, long id) {

        // on a un recip avec id
        Recip r = this.convertRecipDTOToRecip(recipDTO);
        r.setId(id);

        return r;
    }

    public List<RecipDTOGet> findListFollow(String recherche, String typrec) {

      // Pas trouver pour utiliser Stategy avec Spring ?????!!!!!!

        List<RecipDTOGet> l = null;

        if (typrec.equals("NAME")) {

          l = findMethodByName.findMethodRecip(recherche);

        }

        if (typrec.equals("CATEGORY")) {

           l = findMethodByCategory.findMethodRecip(recherche);

        }


      return l;


    }

    public List<RecipDTOGet> convertListRecipToListRecipDTOGet(List<Recip> lr) {

        ArrayList<RecipDTOGet> alDTOget = new ArrayList<>();

        for (Recip r : lr) {

            alDTOget.add(this.convertRecipToRecipDTO(r));
        }

        return alDTOget;

    }

    public User convertUserDTOToUser(UserDTO userDTO){

        User user = new User();

        user.setActive(true);
        user.setUserName(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles("");


        System.out.println("user: " + userDTO.getEmail());
        System.out.println("password: " + userDTO.getPassword());


        return user;

    }

}
