package myapp.tae.ac.uk.myquicknote.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import myapp.tae.ac.uk.myquicknote.model.RestoreData;
import myapp.tae.ac.uk.myquicknote.model.UserBriefNote;
import myapp.tae.ac.uk.myquicknote.model.UserDetailNote;

/**
 * Created by Karma on 15/03/16.
 */
public class DataService {
    @Inject
    SharedPreferences mPreferences;
    @Inject
    Realm mRealm;
    public final static String TAG = DataService.class.getName();

    public void createNote(String noteTitle, String noteContent) {
        int nextId = autoIncrementId();

        UserDetailNote noteDetail = new UserDetailNote();
        noteDetail.setNoteDetailId(nextId);
        noteDetail.setNote(noteContent);

        UserBriefNote briefNote = new UserBriefNote();
        briefNote.setNoteId(nextId);
        briefNote.setNoteTitle(noteTitle);
        briefNote.setNoteFirstLine(getFirstLineOfNote(noteContent));
        briefNote.setLastModified(getDateTime());
        briefNote.setIsModified(false);
        briefNote.setDetailNote(noteDetail);

        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(noteDetail);
        mRealm.copyToRealmOrUpdate(briefNote);
        mRealm.commitTransaction();
    }

    public RealmResults<UserBriefNote> getBriefNotes() {
        List<UserBriefNote> briefNotes = new ArrayList<>();
        RealmResults<UserBriefNote> queryResults = mRealm.where(UserBriefNote.class).findAll();
//        briefNotes = queryResults.subList(0, queryResults.size());
        return queryResults;
    }

    public UserDetailNote getNoteDetailById(int id) {
        RealmResults<UserDetailNote> query = mRealm.where(UserDetailNote.class).equalTo("noteDetailId", id).findAll();
        UserDetailNote detailNote = query.first();
        return detailNote;
    }

    public void removeNote(int id) {
        RealmResults<UserBriefNote> briefNoteResults = mRealm.where(UserBriefNote.class).findAll();
        mRealm.beginTransaction();
        UserBriefNote briefNote = briefNoteResults.where().equalTo("noteId", id).findFirst();
        UserDetailNote detailNote = briefNote.getDetailNote();
        detailNote.removeFromRealm();
        briefNote.removeFromRealm();
        mRealm.commitTransaction();
    }

    public void editNote(int id, String noteTitle, String noteContent) {
        UserDetailNote detailNote = new UserDetailNote();
        detailNote.setNoteDetailId(id);
        detailNote.setNote(noteContent);

        UserBriefNote briefNote = new UserBriefNote();
        briefNote.setNoteId(id);
        briefNote.setNoteTitle(noteTitle);
        briefNote.setNoteFirstLine(getFirstLineOfNote(noteContent));
        briefNote.setLastModified(getDateTime());
        briefNote.setDetailNote(detailNote);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(detailNote);
        mRealm.copyToRealmOrUpdate(briefNote);
        mRealm.commitTransaction();
    }

    private String getFirstLineOfNote(String noteContent) {
        String noteLine = "";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(noteContent));
        try {
            noteLine = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return noteLine;
    }

    private int autoIncrementId() {
        int nextId = mRealm.where(UserBriefNote.class).findAll().size();
        Log.i(TAG, "autoIncrementId: Value = " + nextId);
        if (nextId == 0) {
            return nextId++;
        } else {
            nextId = mRealm.where(UserBriefNote.class).findAll().max("noteId").intValue() + 1;
        }
        Log.i(TAG, "autoIncrementId: Value 2: " + nextId);
        return nextId;
    }

    private Date getDateTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;

    }

    public void restoreNote(RestoreData restoreData) {
        UserDetailNote detailNote = new UserDetailNote();
        detailNote.setNoteDetailId(restoreData.getRestoreId());
        detailNote.setNote(restoreData.getFullNote());

        UserBriefNote briefNote = new UserBriefNote();
        briefNote.setNoteId(restoreData.getRestoreId());
        briefNote.setNoteTitle(restoreData.getTitle());
        briefNote.setNoteFirstLine(getFirstLineOfNote(restoreData.getFullNote()));
        briefNote.setLastModified(restoreData.getLastModified());
        briefNote.setDetailNote(detailNote);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(detailNote);
        mRealm.copyToRealmOrUpdate(briefNote);
        mRealm.commitTransaction();
    }
}
