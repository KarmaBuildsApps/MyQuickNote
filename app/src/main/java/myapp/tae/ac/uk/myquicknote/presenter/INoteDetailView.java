package myapp.tae.ac.uk.myquicknote.presenter;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import myapp.tae.ac.uk.myquicknote.model.UserDetailNote;

/**
 * Created by Karma on 17/03/16.
 */
public interface INoteDetailView {

    public void onItemClicked(View view);

    public String getNoteTitle();

    public String getNoteDetail();

    public String getLastModified();

    public void setNoteTitle(String title);

    public void setLastModified(String lastModifiedDate);

    public void setNoteDetail(String noteDetail);

    void setIsContentChanged(boolean isContentChanged);

    void setTitleEmptyError(int resId);

    void setDetailNoteEmptyError(int resId);

    void showSaveConfirmationDialog();

    void saveAndClose();

    void setNoteId(int id);
}
