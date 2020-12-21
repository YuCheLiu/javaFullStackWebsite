package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {
    private NotesMapper notesMapper;
    private UsersMapper usersMapper;

    public NoteService(NotesMapper notesMapper, UsersMapper usersMapper){
        this.notesMapper = notesMapper;
        this.usersMapper = usersMapper;
    }

    public void addNote(NoteForm noteForm, String username){
        Integer userid = usersMapper.getUsers(username).getUserid();
        Notes note = new Notes(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), usersMapper.getUsers(username).getUserid());
        notesMapper.insert(note,userid);
        System.out.print("added note: "+noteForm.getNoteTitle());
    }

    public ArrayList<Notes> getNoteList(String username){
        return notesMapper.queryNoteList(usersMapper.getUsers(username).getUserid().toString());
    }

    public void deleteNote(Integer noteid){
        notesMapper.deleteNote(noteid.toString());
    }

    public void checkAction(String action){
        System.out.print(action);
    }

    public void editNote(NoteForm noteForm, String username){
        Notes note = new Notes(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), usersMapper.getUsers(username).getUserid());
        notesMapper.updateNote(note,noteForm.getNoteId());
    }
}
