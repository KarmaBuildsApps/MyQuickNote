package myapp.tae.ac.uk.myquicknote.presenter;

import android.content.Intent;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import myapp.tae.ac.uk.myquicknote.constants.Constants;
import myapp.tae.ac.uk.myquicknote.model.UserBriefNote;
import myapp.tae.ac.uk.myquicknote.model.UserDetailNote;
import myapp.tae.ac.uk.myquicknote.services.DataService;

/**
 * Created by Karma on 17/03/16.
 */
public class NotePresenter {
    private INoteView mNoteView;
    private DataService mService;

    public NotePresenter(INoteView noteView, DataService service) {
        this.mNoteView = noteView;
        this.mService = service;
    }

    public Bundle getBundleForActivityStart(UserBriefNote userBriefNote) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NOTE_TITLE, userBriefNote.getNoteTitle());
        bundle.putString(Constants.NOTE_DETAIL, userBriefNote.getDetailNote().getNote());
        bundle.putString(Constants.NOTE_DATE, getDateInString(userBriefNote.getLastModified()));
        bundle.putInt(Constants.NOTE_ID, userBriefNote.getNoteId());
        return bundle;
    }

    private String getDateInString(Date lastModified) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String dateInStringFormat = simpleDateFormat.format(lastModified);
        return dateInStringFormat;
    }

    public void updateReturnedDetailNote(Bundle bundle) {
        boolean isModified = bundle.getBoolean(Constants.IS_NOTE_CONTENT_ALTERED);
        if (isModified) {
            String title = bundle.getString(Constants.NOTE_TITLE);
            String noteContent = bundle.getString(Constants.NOTE_DETAIL);
            int id = bundle.getInt(Constants.NOTE_ID);
            if (id == -1) {
                mService.createNote(title, noteContent);
            } else if (bundle.getBoolean(Constants.IS_NOTE_CONTENT_ALTERED)) {
                mService.editNote(id, title, noteContent);
                mNoteView.notifyAdapterChange();
            }
        }
    }

    public void createNewNote() {
        mNoteView.startNoteDetailToCreate();
    }

    public void startDetailActivity(UserBriefNote userBriefNote) {
        mNoteView.startNoteDetailToOpen(getBundleForActivityStart(userBriefNote));
    }

    public List<UserBriefNote> getNoteList() {
        return mService.getBriefNotes();
    }
}
