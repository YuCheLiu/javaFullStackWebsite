package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("home")
public class CredentialController {
    private CredentialService credentialService;
    public CredentialController(CredentialService credentialService){
        this.credentialService = credentialService;
    }

    @ModelAttribute("credentialForm")
    public CredentialForm getCredentialForm() {
        return new CredentialForm();
    }

    @PostMapping("/credential")
    public String addCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication auth, Model model){
        switch(credentialForm.getCredentialAction()){
            case "add":
                credentialService.addCredential(credentialForm, auth.getName());
            case "edit":
                credentialService.updateCredential(credentialForm, auth.getName());
        }
        model.addAttribute("credentialList",credentialService.getCredentialList(auth.getName()));
        return "redirect:/home";
    }

    @PostMapping("/credential/delete")
    public String deleteCredential(@RequestParam("CredentialDelete") Integer credentialid, Model model){
        credentialService.deleteCredential(credentialid);
        return "redirect:/home";
    }

}
