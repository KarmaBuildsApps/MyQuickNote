package myapp.tae.ac.uk.myquicknote.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import myapp.tae.ac.uk.myquicknote.MainActivity;
import myapp.tae.ac.uk.myquicknote.R;
import myapp.tae.ac.uk.myquicknote.extras.OnListItemClicked;
import myapp.tae.ac.uk.myquicknote.model.UserBriefNote;

/**
 * Created by Karma on 17/03/16.
 */
public class NoteListRealmAdapter extends RealmBasedRecyclerViewAdapter<UserBriefNote, NoteListRealmAdapter.ViewHolder> {
    private Context mContext;
    private RealmResults<UserBriefNote> noteList;
    public static final String TAG = NoteListRealmAdapter.class.getName();

    public NoteListRealmAdapter(Context context, RealmResults<UserBriefNote> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.mContext = context;
        this.noteList = realmResults;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_list_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder holder, int position) {
        final UserBriefNote note = noteList.get(position);
        holder.mLastModifiedTextView.setText(getDateInString(note.getLastModified()));
        holder.mNoteTitleTextView.setText(note.getNoteTitle());
        holder.mFirstLineTextView.setText(note.getNoteFirstLine());
        holder.setOnListItemClicked(new OnListItemClicked() {
            @Override
            public void onListItemClicked(View view, int position) {
                ((MainActivity) mContext).startNoteDetailActivity(note);
            }

            @Override
            public boolean onLongItemClicked(View view, int position) {
                return ((MainActivity) mContext).showDeleteOption(note);
            }
        });
    }

    @Override
    public void onItemSwipedDismiss(int position) {
        super.onItemSwipedDismiss(position);
//        Log.i(TAG, "onItemSwipedDismiss: This is swipe to delete");
//        ((MainActivity) mContext).showDeleteOption(noteList.get(position));
    }

    private String getDateInString(Date lastModified) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return sdf.format(lastModified);
    }

    public void setNoteList(RealmResults<UserBriefNote> noteList) {
        this.noteList = noteList;
    }

    public class ViewHolder extends RealmViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @Bind(R.id.tvListLastModified)
        TextView mLastModifiedTextView;
        @Bind(R.id.tvListNoteTitle)
        TextView mNoteTitleTextView;
        @Bind(R.id.tvListFirstLine)
        TextView mFirstLineTextView;
        private OnListItemClicked onListItemClicked;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setOnListItemClicked(OnListItemClicked onListItemClicked) {
            this.onListItemClicked = onListItemClicked;
        }

        @Override
        public void onClick(View v) {
            onListItemClicked.onListItemClicked(v, getLayoutPosition());
        }


        @Override
        public boolean onLongClick(View v) {
            return onListItemClicked.onLongItemClicked(v, getLayoutPosition());
        }
    }
}
