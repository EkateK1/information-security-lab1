package app.lab1.controllers;

import app.lab1.auth.AuthService;
import app.lab1.auth.InvalidCredentialsException;
import app.lab1.http.requests.LoginRequest;
import app.lab1.http.responses.ErrorResponse;
import app.lab1.http.responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        if (isBlank(request.username()) || isBlank(request.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }

        return new LoginResponse(authService.login(request.username(), request.password()), "Bearer");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse invalidCredentials() {
        return new ErrorResponse("invalid username or password");
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
