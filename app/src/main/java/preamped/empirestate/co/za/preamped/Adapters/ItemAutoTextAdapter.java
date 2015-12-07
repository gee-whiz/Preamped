package preamped.empirestate.co.za.preamped.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by George on 2015-06-10.
 */
public class ItemAutoTextAdapter extends CursorAdapter  {
    private static final String LOG = "";
    public ItemAutoTextAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }


}
