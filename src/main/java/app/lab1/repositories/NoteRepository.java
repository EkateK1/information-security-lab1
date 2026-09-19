package app.lab1.repositories;

import java.util.List;

import app.lab1.entities.AppUser;
import app.lab1.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByOwnerOrderByCreatedAtDesc(AppUser owner);
}
