package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;


@Service
public class FileService {
    private FilesMapper filesMapper;
    private UsersMapper usersMapper;
    public FileService(FilesMapper filesMapper, UsersMapper usersMapper){
        this.filesMapper = filesMapper;
        this.usersMapper = usersMapper;
    }

    public int uploadFile(String username, MultipartFile multipartFile) throws IOException {
        System.out.print("upload successful: "+multipartFile.getOriginalFilename());

        return filesMapper.insert(new Files(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(), String.valueOf(multipartFile.getSize()),usersMapper.getUsers(username).getUserid(), multipartFile.getBytes()), usersMapper.getUsers(username).getUserid().toString() );
    }

    public boolean isFileEmpty(MultipartFile multipartFile){
        return multipartFile.isEmpty();
    }

    public ArrayList<Files> getFilesList(String username){
        ArrayList<Files> filelist = filesMapper.queryFileList(usersMapper.getUsers(username).getUserid().toString());
        return filelist;
    }

    public boolean deleteFileByFileId(Integer fileId){
        System.out.print("file deleted");
        return filesMapper.deleteFile(fileId);
    }

    public Files getFileDetail(Integer fileId){
        System.out.print("getFileDetail");
        return filesMapper.queryFile(fileId);
    }
}
