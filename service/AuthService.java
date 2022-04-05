package recipes.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Ne fonctionne pas !!!!!!!!!!!
    // Exception quand on l'utilise !!!!

    @Bean
    public Authentication getAuth() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

}
