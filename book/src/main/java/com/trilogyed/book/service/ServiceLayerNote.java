package com.trilogyed.book.service;

import com.trilogyed.book.util.feign.NoteClient;
import com.trilogyed.book.viewModel.NotesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceLayerNote {

    private final NoteClient client;

    @Autowired
    public ServiceLayerNote(NoteClient client){
        this.client = client;
    }

    //uri: /notes ///////////////////////////////////////////
//    public NotesViewModel createNote(NotesViewModel nvm){
//
//
//    }
}
