package myapp.tae.ac.uk.myquicknote.extras;

import android.view.View;

/**
 * Created by Karma on 17/03/16.
 */
public interface OnListItemClicked {
    public void onListItemClicked(View view, int position);

    public boolean onLongItemClicked(View view, int position);
}
