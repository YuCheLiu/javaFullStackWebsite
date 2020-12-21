package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FilesController {
    private FileService fileService;
    public FilesController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/delete")
    public String deleteFile(@RequestParam("fileDelete") Integer fileid, Authentication auth, Model model){
        fileService.deleteFileByFileId(fileid);
        model.addAttribute("fileList",fileService.getFilesList(auth.getName()));
        return "redirect:/home";
    }

    @PostMapping("/upload")
    public String fileupload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication auth, Model model) throws IOException {
        if(auth.isAuthenticated()){
            String fileuploadError=null;
            if(fileService.isFileEmpty(fileUpload)){
                fileuploadError ="Please select the file!";
            }else{
                fileService.uploadFile(auth.getName(), fileUpload);
                model.addAttribute("fileList",fileService.getFilesList(auth.getName()));
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/download")
    public ResponseEntity downloadFromDB(@RequestParam("fileId") Integer fileId, Authentication auth) {
        Files file = fileService.getFileDetail(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFiledata());
    }


}
