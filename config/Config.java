package recipes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder getEncoder() {
        // Bcrypt est une implémentaion de d'un PasswordEncoder.
        return new BCryptPasswordEncoder();
    }

}