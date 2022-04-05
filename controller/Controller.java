package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.logic.*;
import recipes.repository.RecipRepository;
import recipes.repository.UserRepository;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

//@Controller est utilisé dans les systèmes hérités
// qui utilisent des JSP. il peut renvoyer des vues.
// @RestController consiste à marquer que le contrôleur
// fournit des services REST avec le type de réponse JSON.
// il encapsule donc les annotations @Controller et @ResponseBody ensemble.

@RestController
@Validated
public class Controller {

    @Autowired
    private RecipRepository recipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConverterDTO converterDTO;

    @Autowired
    FindMethodByCategory f;


    @RequestMapping(value = "/api/register",  method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public void apiRegister(@Valid @RequestBody UserDTO userDTO) {

       Optional<User> userOption = userRepository.findByUserName(userDTO.getEmail());

        if(userOption.isPresent()){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User already exists");

        }else {
            User u = userRepository.save(converterDTO.convertUserDTOToUser(userDTO));
        }

    }

    @RequestMapping(value = "/actuator/shutdown",  method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public void apiShutDown() {


    }

    @RequestMapping(value = "/api/recipe/new",  method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public IndexResponse apiPostNewRecip(@Valid @RequestBody RecipDTO recip1) {

        Recip r = recipRepository.save(converterDTO.convertRecipDTOToRecip(recip1));

        IndexResponse indexResponse = new IndexResponse(r.getId());

        return indexResponse;
    }

    @RequestMapping(value = "/api/recipe/{id}",  method= RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
  // c'est la réponses par défaut (elle dit pas de contenu en retour)
   @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void apiUpdateRecip(@Valid @RequestBody RecipDTO recip1, @PathVariable long id) {

        if(!recipRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "id not found");
        }

        // test si user connecté = le user qui a créé le recip
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals(recipRepository.findById(id).get().getUsername())){

            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "forbidden");
        }

        // updateRecipDTO renoit un objet Recip avec les données du recipDTO et id
        // reçu en paramètre
        recipRepository.save(converterDTO.updateRecipDTOToRecip(recip1, id));

    }


    @RequestMapping(value = "/api/recipe/{id}", method=RequestMethod.GET)
    public RecipDTOGet getRecipe(@PathVariable long id) {

        if(!recipRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "id not found");
        }

        return converterDTO.convertRecipToRecipDTO(recipRepository.findById(id).get());
    }

    @RequestMapping(value = "/api/recipe/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable long id) {

        if(!recipRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "id not found");
        }

        // test si user connecté = le user qui a créé le recip
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals(recipRepository.findById(id).get().getUsername())){

            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "forbidden");
        }

       recipRepository.deleteById(id);
    }

    @RequestMapping(value="/api/recipe/search", method=RequestMethod.GET)
    public List<RecipDTOGet> findByNameOrCategory(

            @RequestParam Map<String,String> allParams
    )
    {


        if(allParams.size() == 0 || allParams.size() > 1){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Parameters not valid");
        }

        if(!allParams.containsKey("name") && !allParams.containsKey("category")){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Parameters not valid");
        }


        List<RecipDTOGet> listRetour;
        listRetour = null;

        if(allParams.containsKey("name")){

            listRetour = converterDTO.findListFollow(allParams.get("name"), "NAME" );

        }


        if(allParams.containsKey("category")){

            listRetour = converterDTO.findListFollow(allParams.get("category"), "CATEGORY" );

        }


        return listRetour;
    }

    // pas possible car déjà défini
   /* @RequestMapping(value="/api/recipe/search", method=RequestMethod.GET)
    public List<RecipDTOGet> findByCategory(

            @RequestParam(name = "category", defaultValue = " ") String category
    )
    {


        return converterDTO.findListFollow(category, "CATEGORY");

    }*/

}
