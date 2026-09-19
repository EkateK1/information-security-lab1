package app.lab1.http.responses;

import java.time.Instant;

import app.lab1.entities.Note;
import org.springframework.web.util.HtmlUtils;

public record NoteResponse(Long id, String title, String text, Instant createdAt) {

    public static NoteResponse from(Note note) {
        return new NoteResponse(
                note.getId(),
                HtmlUtils.htmlEscape(note.getTitle()),
                HtmlUtils.htmlEscape(note.getText()),
                note.getCreatedAt()
        );
    }
}
