package app.lab1.http.responses;

import java.time.Instant;

import app.lab1.entities.Note;

public record NoteResponse(Long id, String title, String text, Instant createdAt) {

    public static NoteResponse from(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getText(),
                note.getCreatedAt()
        );
    }
}
