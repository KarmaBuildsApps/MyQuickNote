package myapp.tae.ac.uk.myquicknote.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Karma on 16/03/16.
 */
public class UserDetailNote extends RealmObject {
    @PrimaryKey
    private int noteDetailId;
    private String note;

    public int getNoteDetailId() {
        return noteDetailId;
    }

    public void setNoteDetailId(int noteDetailId) {
        this.noteDetailId = noteDetailId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
