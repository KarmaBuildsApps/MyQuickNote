package myapp.tae.ac.uk.myquicknote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.myquicknote.constants.Constants;
import myapp.tae.ac.uk.myquicknote.model.UserBriefNote;
import myapp.tae.ac.uk.myquicknote.presenter.INoteView;
import myapp.tae.ac.uk.myquicknote.presenter.NotePresenter;
import myapp.tae.ac.uk.myquicknote.services.DataService;
import myapp.tae.ac.uk.myquicknote.ui.NoteDetailActvitiy;
import myapp.tae.ac.uk.myquicknote.ui.NoteListAdapter;


public class MainActivity extends AppCompatActivity implements INoteView, View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rcBriefNote)
    RecyclerView mNoteRecyclerview;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    private NotePresenter mPresenter;
    private NoteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new NotePresenter(this, new DataService(getApplication()));
        setSupportActionBar(mToolbar);
        mFab.setOnClickListener(this);
        List<UserBriefNote> noteList = mPresenter.getNoteList();
        adapter = new NoteListAdapter(this, noteList);
        mNoteRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mNoteRecyclerview.setAdapter(adapter);
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
}
