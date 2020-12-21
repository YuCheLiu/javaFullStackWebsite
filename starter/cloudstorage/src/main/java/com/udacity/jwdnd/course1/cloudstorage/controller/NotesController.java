package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("home")
public class NotesController {
    private NoteService noteService;

    @ModelAttribute("noteForm")
    public NoteForm getNoteForm() {
        return new NoteForm();
    }

    public NotesController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping("/note/add")
    public String addNote(@ModelAttribute("noteForm") NoteForm noteForm,Authentication auth, Model model){
        switch (noteForm.getNoteAction()){
            case "add":
                noteService.addNote(noteForm,auth.getName());
            case "edit":
                noteService.editNote(noteForm,auth.getName());
        }
        model.addAttribute("noteList",noteService.getNoteList(auth.getName()));
        noteService.checkAction(noteForm.getNoteAction());
        return "redirect:/home";
    }

    @PostMapping("/note/delete")
    public String deleteFile(@RequestParam("NoteDelete") Integer noteid, Authentication auth, Model model){
        noteService.deleteNote(noteid);
        model.addAttribute("noteList",noteService.getNoteList(auth.getName()));
        return "redirect:/home";
    }
}
