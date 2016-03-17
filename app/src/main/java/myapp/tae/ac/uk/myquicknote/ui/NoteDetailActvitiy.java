package myapp.tae.ac.uk.myquicknote.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myapp.tae.ac.uk.myquicknote.R;
import myapp.tae.ac.uk.myquicknote.constants.Constants;
import myapp.tae.ac.uk.myquicknote.presenter.INoteDetailView;
import myapp.tae.ac.uk.myquicknote.presenter.NoteDetailPresenter;

/**
 * Created by Karma on 17/03/16.
 */
public class NoteDetailActvitiy extends AppCompatActivity implements INoteDetailView {
    @Bind(R.id.etNoteTitle)
    EditText mNoteTitleEditText;
    @Bind(R.id.tvLastModified)
    TextView mLastModifiedTextView;
    @Bind(R.id.etNoteDetail)
    EditText mNoteDetailEditText;
    @Bind(R.id.ibToolbarSave)
    ImageButton mSaveImageButton;

    private boolean isContentChanged = true;
    private NoteDetailPresenter mDetailPresenter;
    private AlertDialog mAlertDialog;
    private int mNoteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail_layout);
        mDetailPresenter = new NoteDetailPresenter(this);
        ButterKnife.bind(this);
        setupAlertDialog();
        if (savedInstanceState != null) {
            mDetailPresenter.restorePreviousState(savedInstanceState);
        } else
            checkIfActivityStartedTOAddOrModify();
    }

    private void setupAlertDialog() {
        mAlertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.save_confirm_dialog_title))
                .setMessage(getString(R.string.save_confirm_dialog_message))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDetailPresenter.saveAndCloseNoteDetailActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create();
    }

    private void checkIfActivityStartedTOAddOrModify() {
        Bundle bundle = getIntent().getBundleExtra(Constants.NOTE_DETAIL_BUNDLE);
        if (bundle != null) {
            mDetailPresenter.setDataFromMainActivity(bundle);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.NOTE_TITLE, mNoteTitleEditText.getText().toString());
        outState.putString(Constants.NOTE_DETAIL, mNoteDetailEditText.getText().toString());
        outState.putString(Constants.NOTE_DATE, mLastModifiedTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClicked(View view) {

    }

    @Override
    public String getNoteTitle() {
        return mNoteTitleEditText.getText().toString();
    }

    @Override
    public String getNoteDetail() {
        return mNoteDetailEditText.getText().toString();
    }

    @Override
    public String getLastModified() {
        return mLastModifiedTextView.getText().toString();
    }

    @Override
    public void setNoteTitle(String title) {
        mNoteTitleEditText.setText(title);
    }

    @Override
    public void setLastModified(String lastModifiedDate) {
        mLastModifiedTextView.setText(lastModifiedDate);
    }

    @Override
    public void setNoteDetail(String noteDetail) {
        mNoteDetailEditText.setText(noteDetail);
    }

    @Override
    public void setIsContentChanged(boolean isContentChanged) {
        this.isContentChanged = isContentChanged;
    }

    @Override
    public void setTitleEmptyError(int resId) {
        mNoteTitleEditText.setError(getString(resId));
    }

    @Override
    public void setDetailNoteEmptyError(int resId) {
        mNoteDetailEditText.setError(getString(resId));
    }

    @Override
    public void showSaveConfirmationDialog() {
        mAlertDialog.show();
    }

    @Override
    public void setNoteId(int id) {
        this.mNoteId = id;
    }

    @OnClick(R.id.ibToolbarSave)
    public void onSaveButtonClicked(View view) {
        mDetailPresenter.onSaveButtonClicked();
    }

    @Override
    public void saveAndClose() {
        Intent intentReturnResults = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IS_NOTE_CONTENT_ALTERED, isContentChanged);
        bundle.putString(Constants.NOTE_TITLE, getNoteTitle());
        bundle.putString(Constants.NOTE_DETAIL, getNoteDetail());
        bundle.putString(Constants.NOTE_DATE, getLastModified());
        bundle.putInt(Constants.NOTE_ID, mNoteId);
        intentReturnResults.putExtra(Constants.NOTE_DETAIL_BUNDLE, bundle);
        setResult(Activity.RESULT_OK, intentReturnResults);
        finish();
    }
}
