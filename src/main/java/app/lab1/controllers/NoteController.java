package app.lab1.controllers;

import java.util.List;

import app.lab1.auth.BearerTokenInterceptor;
import app.lab1.entities.AppUser;
import app.lab1.entities.Note;
import app.lab1.http.requests.CreateNoteRequest;
import app.lab1.http.responses.NoteResponse;
import app.lab1.repositories.NoteRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class NoteController {

    private final NoteRepository noteRepository;

    @GetMapping
    public List<NoteResponse> getCurrentUserNotes(HttpServletRequest request) {
        AppUser currentUser = currentUser(request);
        return noteRepository.findAllByOwnerOrderByCreatedAtDesc(currentUser).stream()
                .map(NoteResponse::from)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse createNote(@RequestBody CreateNoteRequest requestBody, HttpServletRequest request) {
        if (isBlank(requestBody.title()) || isBlank(requestBody.text())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title and text are required");
        }

        Note note = new Note(currentUser(request), requestBody.title(), requestBody.text());
        return NoteResponse.from(noteRepository.save(note));
    }

    private AppUser currentUser(HttpServletRequest request) {
        return (AppUser) request.getAttribute(BearerTokenInterceptor.CURRENT_USER_ATTRIBUTE);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
