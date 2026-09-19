package app.lab1.config;

import app.lab1.entities.Note;
import app.lab1.entities.AppUser;
import app.lab1.repositories.NoteRepository;
import app.lab1.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            AppUserRepository userRepository,
            NoteRepository noteRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            AppUser alice = createUserIfMissing(userRepository, passwordEncoder, "alice", "password1");
            AppUser bob = createUserIfMissing(userRepository, passwordEncoder, "bob", "password2");

            if (noteRepository.count() == 0) {
                noteRepository.save(new Note(alice, "План", "Подготовить отчет по лабораторной работе."));
                noteRepository.save(new Note(bob, "Идея", "Проверить, что Bob не видит заметки Alice."));
            }
        };
    }

    private AppUser createUserIfMissing(
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String username,
            String password
    ) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.save(new AppUser(username, passwordEncoder.encode(password))));
    }
}
