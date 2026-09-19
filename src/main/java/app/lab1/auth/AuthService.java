package app.lab1.auth;

import app.lab1.entities.AppUser;
import app.lab1.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String username, String password) {
        AppUser user = userRepository.findByUsername(username)
                .filter(foundUser -> passwordEncoder.matches(password, foundUser.getPasswordHash()))
                .orElseThrow(InvalidCredentialsException::new);

        return jwtService.generateJwtToken(user.getUsername());
    }
}
