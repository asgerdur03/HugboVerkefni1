package hi.hugboverkefni1.services;

import hi.hugboverkefni1.persistence.entities.Note;

import java.util.List;

public interface NoteService {
    Note findByTitle(String title);
    List<Note> findByTitleContaining(String title);

    void delete(Note note);

    List<Note> findAll();

    Note findByID(long id);

    //meira
}
