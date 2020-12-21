package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Mapper
@Repository
public interface NotesMapper {

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES (null, #{notes.notetitle}, #{notes.notedescription}, #{userid})")
    int insert(Notes notes, Integer userid);

    @Select("SELECT * FROM NOTES WHERE userid= #{userid}")
    ArrayList<Notes> queryNoteList(String userid);

    @Delete("DELETE FROM NOTES WHERE noteid= #{noteid}")
    boolean deleteNote(String noteid);

    @Update("UPDATE NOTES SET notetitle=#{notes.notetitle}, notedescription = #{notes.notedescription} WHERE noteid= #{noteid}")
    int updateNote(Notes notes, Integer noteid);
}
