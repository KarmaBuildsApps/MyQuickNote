package myapp.tae.ac.uk.myquicknote.presenter;

import android.os.Bundle;

import myapp.tae.ac.uk.myquicknote.R;
import myapp.tae.ac.uk.myquicknote.constants.Constants;
import myapp.tae.ac.uk.myquicknote.model.UserDetailNote;
import myapp.tae.ac.uk.myquicknote.ui.NoteDetailActvitiy;

/**
 * Created by Karma on 17/03/16.
 */
public class NoteDetailPresenter {
    private INoteDetailView mView;

    public NoteDetailPresenter(INoteDetailView view) {
        this.mView = view;
    }

    public void restorePreviousState(Bundle bundle) {
        mView.setNoteTitle(bundle.getString(Constants.NOTE_TITLE));
        mView.setLastModified(bundle.getString(Constants.NOTE_DATE));
        mView.setNoteDetail(bundle.getString(Constants.NOTE_DETAIL));
    }


    public void onSaveButtonClicked() {
        String title = mView.getNoteTitle();
        if (title.isEmpty()) {
            mView.setTitleEmptyError(R.string.error_title_empty);
            return;
        }
        String detailNote = mView.getNoteDetail();
        if (detailNote.isEmpty()) {
            mView.setDetailNoteEmptyError(R.string.error_detail_empty);
            return;
        }

        mView.showSaveConfirmationDialog();
    }

    public void saveAndCloseNoteDetailActivity() {
        mView.saveAndClose();
    }

    public void setDataFromMainActivity(Bundle dataFromMainActivity) {
        mView.setNoteId(dataFromMainActivity.getInt(Constants.NOTE_ID));
        restorePreviousState(dataFromMainActivity);
    }
}
