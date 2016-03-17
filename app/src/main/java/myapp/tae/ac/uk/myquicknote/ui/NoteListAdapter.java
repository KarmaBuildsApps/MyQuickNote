package myapp.tae.ac.uk.myquicknote.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.myquicknote.MainActivity;
import myapp.tae.ac.uk.myquicknote.R;
import myapp.tae.ac.uk.myquicknote.extras.OnListItemClicked;
import myapp.tae.ac.uk.myquicknote.model.UserBriefNote;
import myapp.tae.ac.uk.myquicknote.model.UserDetailNote;

/**
 * Created by Karma on 17/03/16.
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private List<UserBriefNote> noteList;
    private Context mcontext;

    public NoteListAdapter(Context context, List<UserBriefNote> noteList) {
        this.mcontext = context;
        this.noteList = noteList;
    }

    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.note_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteListAdapter.ViewHolder holder, int position) {
        final UserBriefNote userBriefNote = noteList.get(position);
        holder.mLastModifiedTextView.setText(getDateInString(userBriefNote.getLastModified()));
        holder.mNoteTitleTextView.setText(userBriefNote.getNoteTitle());
        holder.mFirstLineTextView.setText(userBriefNote.getNoteFirstLine());
        holder.setOnListItemClicked(new OnListItemClicked() {
            @Override
            public void onListItemClicked(View view, int position) {
                ((MainActivity) mcontext).startNoteDetailActivity(userBriefNote);
            }
        });
    }

    private String getDateInString(Date lastModified) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return sdf.format(lastModified);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNoteList(List<UserBriefNote> noteList) {
        this.noteList = noteList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        }

        public void setOnListItemClicked(OnListItemClicked onListItemClicked) {
            this.onListItemClicked = onListItemClicked;
        }

        @Override
        public void onClick(View v) {
            onListItemClicked.onListItemClicked(v, getLayoutPosition());
        }
    }
}
