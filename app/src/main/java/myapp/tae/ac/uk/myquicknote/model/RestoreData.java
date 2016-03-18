package myapp.tae.ac.uk.myquicknote.model;

import java.util.Date;

/**
 * Created by Karma on 18/03/16.
 */
public class RestoreData {
    private int restoreId;
    private String title;
    private String fullNote;
    private Date lastModified;

    public int getRestoreId() {
        return restoreId;
    }

    public void setRestoreId(int restoreId) {
        this.restoreId = restoreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullNote() {
        return fullNote;
    }

    public void setFullNote(String fullNote) {
        this.fullNote = fullNote;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
