package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService){
        this.fileService= fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }
    @GetMapping()
    public String getHomePage(Authentication auth, Model model){
        model.addAttribute("fileList",fileService.getFilesList(auth.getName()));
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("credentialForm", new CredentialForm());
        model.addAttribute("credentialList",credentialService.getCredentialList(auth.getName()));

        model.addAttribute("noteList",noteService.getNoteList(auth.getName()));

        return "home";
    }
}
