package myapp.tae.ac.uk.myquicknote.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Karma on 16/03/16.
 */
public class UserBriefNote extends RealmObject {
    @PrimaryKey
    private int noteId;
    private String noteTitle;
    private String noteFirstLine;
    private Date lastModified;
    private boolean isModified;
    private UserDetailNote detailNote;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteFirstLine() {
        return noteFirstLine;
    }

    public void setNoteFirstLine(String noteFirstLine) {
        this.noteFirstLine = noteFirstLine;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }

    public UserDetailNote getDetailNote() {
        return detailNote;
    }

    public void setDetailNote(UserDetailNote detailNote) {
        this.detailNote = detailNote;
    }
}
