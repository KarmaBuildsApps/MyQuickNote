package myapp.tae.ac.uk.myquicknote.services;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import myapp.tae.ac.uk.myquicknote.model.UserBriefNote;
import myapp.tae.ac.uk.myquicknote.model.UserDetailNote;

/**
 * Created by Karma on 15/03/16.
 */
public class DataService {
    private SharedPreferences mPreferences;
    private Realm mRealm;
    private Context mContext;

    public DataService(Context context) {
        this.mContext = context;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        mRealm = Realm.getInstance(realmConfiguration);
    }

    public void createNote(String noteTitle, String noteContent) {
        mRealm.beginTransaction();
        UserBriefNote briefNote = mRealm.createObject(UserBriefNote.class);
        int nextId = autoIncrementId();
        UserDetailNote noteDetail = mRealm.createObject(UserDetailNote.class);
        noteDetail.setNoteId(nextId);

        briefNote.setNoteId(nextId);
        briefNote.setNoteTitle(noteTitle);
        briefNote.setNoteFirstLine(getFirstLineOfNote(noteContent));
        briefNote.setLastModified(getDateTime());
        briefNote.setIsModified(false);
        briefNote.setDetailNote(noteDetail);

        mRealm.commitTransaction();
    }

    public List<UserBriefNote> getBriefNotes() {
        List<UserBriefNote> briefNotes = new ArrayList<>();
        RealmResults<UserBriefNote> queryResults = mRealm.where(UserBriefNote.class).findAll();
        briefNotes = queryResults.subList(0, queryResults.size());
        return briefNotes;
    }

    public UserDetailNote getNoteDetailById(int id) {
        RealmResults<UserDetailNote> query = mRealm.where(UserDetailNote.class).equalTo("noteId", id).findAll();
        UserDetailNote detailNote = query.first();
        return detailNote;
    }

    public void removeNote(int id) {
        RealmResults<UserBriefNote> briefNoteResults = mRealm.where(UserBriefNote.class).findAll();
        mRealm.beginTransaction();
        UserBriefNote briefNote = briefNoteResults.where().equalTo("noteId", id).findFirst();
        UserDetailNote detailNote = briefNote.getDetailNote();
        briefNote.removeFromRealm();
        detailNote.removeFromRealm();
        mRealm.commitTransaction();
    }

    public void editNote(int id, String noteTitle, String noteContent) {
        UserDetailNote detailNote = new UserDetailNote();
        detailNote.setNoteId(id);
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
        if (nextId == 0) {
            return nextId++;
        } else {
            nextId = mRealm.where(UserBriefNote.class).findAll().max("noteId").intValue();
        }
        return nextId;
    }

    private Date getDateTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;

    }
}
