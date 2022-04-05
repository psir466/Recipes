package recipes.logic;

import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import recipes.repository.RecipRepository;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindMethodByCategory implements FindMethod {

    @Autowired
    private ConverterDTO converterDTO;

    @Autowired
    private RecipRepository recipRepository;

    @Override
    public List<RecipDTOGet> findMethodRecip(String recherche) {

        System.out.println(recipRepository + " " + recherche + " " + converterDTO);

        List<Recip> arlR = recipRepository.findByCategoryRecip(recherche);

        List<Recip> sortedList = null;

        if(arlR.size() != 0) {

            sortedList = arlR.stream()
                    .sorted(new DateRecipComprator())
                    .collect(Collectors.toList());
        }else{

          sortedList = Collections.emptyList();
        }


        return converterDTO.convertListRecipToListRecipDTOGet(sortedList);

    }
}
