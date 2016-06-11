package net.mijack.paperapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.db.HistoryDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MiJack on 2016/5/28.
 */
public class HistoryAdapter extends CursorAdapter {
    public HistoryAdapter(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        Holder holder = new Holder(view);
        view.setTag(holder);
        return view;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        View v;
        if (convertView == null) {
            v = newView(mContext, mCursor, parent);
        } else {
            v = convertView;
        }
        bindView(v, mCursor, position);
        return v;
    }

    private void bindView(View view, Cursor cursor, int position) {
        Holder h = (Holder) view.getTag();
        if (h != null) {
            h.bind(cursor, position);
        }
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
