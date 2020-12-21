package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface FilesMapper {

    @Insert("INSERT INTO Files (userid, filename, contenttype, filesize, filedata) VALUES (#{userid}, #{file.filename}, #{file.contenttype}, #{file.filesize}, #{file.filedata})")
    int insert(Files file, String userid);

    @Select("SELECT * FROM FILES WHERE userid= #{userid}")
    ArrayList<Files> queryFileList(String userid);

    @Delete("DELETE FROM FILES WHERE fileId= #{fileid}")
    boolean deleteFile(Integer fileid);

    @Select("SELECT * FROM FILES WHERE fileId= #{fileId}")
    Files queryFile(Integer fileId);
}
