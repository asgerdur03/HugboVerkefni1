package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.Note;
import hi.hugboverkefni1.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImplementation implements NoteService {

    // would be link to repository


    // fake repo
    private List<Note> noteRepository = new ArrayList<Note>();
    private int id_counter = 0;


    @Autowired
    public NoteServiceImplementation() {
        //empty constructor
        //will be removed when jpa added
        // add random notes
        noteRepository.add(new Note("blablabla", "note1"));
        noteRepository.add(new Note("blablabla", "note2"));
        // JPA gives each note ID, but hear we add manually
        for (Note note : noteRepository) {
            note.setID(id_counter);
            id_counter++;
        }
    }






    @Override
    public Note findByTitle(String title) {
        // skilar bara fyrsta, possibly laga
        for (Note note : noteRepository) {
            if (note.getTitle().equals(title)) {
                return note;
            }
        }
        return null;
    }

    @Override
    public List<Note> findByTitleContaining(String title) {
        for (Note note : noteRepository) {
            if (note.getTitle().contains(title)) {
                //add to list

            }
        }
        return List.of();
    }

    @Override
    public void delete(Note note) {
        noteRepository.remove(note);
    }

    @Override
    public List<Note> findAll() {
        return noteRepository;
    }

    @Override
    public Note findByID(long id) {
        return null;
    }
}

