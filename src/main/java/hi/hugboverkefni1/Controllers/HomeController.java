package hi.hugboverkefni1.Controllers;


import hi.hugboverkefni1.persistence.entities.Note;
import hi.hugboverkefni1.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {

    private NoteService noteService;


    @Autowired
    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping("/")
    public String HomePage(Model model) {
        //busines logic
        //call a methodd in a service class

        List<Note> allNotes = noteService.findAll();

        model.addAttribute("notes", noteService.findAll());
        // add some data to the model
        return "home"; //leitar að html file clalled home
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteNote(@PathVariable("id") long ID, Model model) {
        Note noteToDelete = noteService.findByID(ID);
        noteService.delete(noteToDelete);

        return "redirect:/";
    }


}
