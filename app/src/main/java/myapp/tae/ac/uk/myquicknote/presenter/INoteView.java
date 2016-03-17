package myapp.tae.ac.uk.myquicknote.presenter;

import android.app.Application;
import android.os.Bundle;

import myapp.tae.ac.uk.myquicknote.model.UserDetailNote;

/**
 * Created by Karma on 17/03/16.
 */
public interface INoteView {

    public void showNoteDetail(Bundle bundle);

    public void notifyAdapterChange();

    public void startNoteDetailToCreate();

    void startNoteDetailToOpen(Bundle bundle);
}
