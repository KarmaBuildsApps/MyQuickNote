package myapp.tae.ac.uk.myquicknote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmResults;
import myapp.tae.ac.uk.myquicknote.constants.Constants;
import myapp.tae.ac.uk.myquicknote.model.RestoreData;
import myapp.tae.ac.uk.myquicknote.model.UserBriefNote;
import myapp.tae.ac.uk.myquicknote.presenter.INoteView;
import myapp.tae.ac.uk.myquicknote.presenter.NotePresenter;
import myapp.tae.ac.uk.myquicknote.services.DataService;
import myapp.tae.ac.uk.myquicknote.ui.NoteDetailActvitiy;
import myapp.tae.ac.uk.myquicknote.ui.NoteListRealmAdapter;


public class MainActivity extends AppCompatActivity implements INoteView, View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.realm_recycler_view)
    RealmRecyclerView mRealmRCV;
    private NotePresenter mPresenter;
    private NoteListRealmAdapter adapter;
    RestoreData temp = null;
    DataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        service = new DataService();
        mPresenter = new NotePresenter(this, service);
        ((MyApplicaton) getApplication()).getLocalDataComponent().inject(service);
        setSupportActionBar(mToolbar);
        mFab.setOnClickListener(this);
        RealmResults<UserBriefNote> noteList = mPresenter.getNoteList();
        adapter = new NoteListRealmAdapter(this, noteList, true, true);
        mRealmRCV.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        mPresenter.createNewNote();
    }

    @Override
    public void showNoteDetail(Bundle bundle) {
        Intent intent = new Intent(this, NoteDetailActvitiy.class);
        intent.putExtra(Constants.NOTE_DETAIL_BUNDLE, bundle);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    public void notifyAdapterChange() {
        adapter.setNoteList(mPresenter.getNoteList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startNoteDetailToCreate() {
        Intent intent = new Intent(this, NoteDetailActvitiy.class);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    public void startNoteDetailToOpen(Bundle bundle) {
        Intent intent = new Intent(this, NoteDetailActvitiy.class);
        intent.putExtra(Constants.NOTE_DETAIL_BUNDLE, bundle);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_CODE) {
                mPresenter.updateReturnedDetailNote(data.getBundleExtra(Constants.NOTE_DETAIL_BUNDLE));
            }
        }
    }

    public void startNoteDetailActivity(UserBriefNote userBriefNote) {
        mPresenter.startDetailActivity(userBriefNote);
    }

    public boolean showDeleteOption(final UserBriefNote userBriefNote) {
        final Runnable removeRun = new Runnable() {
            @Override
            public void run() {
                mPresenter.remove(userBriefNote);
            }
        };
        final Handler handler = new Handler();
        handler.postDelayed(removeRun, Snackbar.LENGTH_LONG + 1000 * 2);
        Snackbar.make(findViewById(R.id.clMainView), getString(R.string.action_delete_warning_message),
                Snackbar.LENGTH_LONG)
                .setAction(R.string.action_delete_UNDO, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.removeCallbacks(removeRun);
                        notifyAdapterChange();
                    }
                }).show();

        return false;
    }
}
