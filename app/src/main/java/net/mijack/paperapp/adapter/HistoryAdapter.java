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

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder h = (Holder) view.getTag();
        if (h != null) {
            h.bind(cursor);
        }
    }

    static DateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm");

    static private class Holder {


        TextView idView;
        TextView apkUrlView;
        TextView apkMD5View;
        TextView taskTimeView;
        TextView statusView;

        public Holder(View view) {
            idView = (TextView) view.findViewById(R.id.id);
            apkUrlView = (TextView) view.findViewById(R.id.apkUrl);
            apkMD5View = (TextView) view.findViewById(R.id.apkMD5);
            taskTimeView = (TextView) view.findViewById(R.id.taskTime);
            statusView = (TextView) view.findViewById(R.id.status);
        }

        public void bind(Cursor cursor) {
            bindValue(idView, cursor, HistoryDAO.COLUMN_ID);
            bindValue(apkUrlView, cursor, HistoryDAO.COLUMN_APK_URL);
            bindValue(apkMD5View, cursor, HistoryDAO.COLUMN_FILE_MD5);
            bindValue(statusView, cursor, HistoryDAO.COLUMN_ANALYSIS_STATUS);
            try {
                int index = cursor.getColumnIndex(HistoryDAO.COLUMN_CREATE_TIME);
                String time = format.format(new Date(cursor.getLong(index)));
                taskTimeView.setText(time);
            } catch (Exception e) {
            }
        }

        private void bindValue(TextView idView, Cursor cursor, String column) {
            int index = cursor.getColumnIndex(column);
            String string = cursor.getString(index);
            idView.setText(string);
        }
    }
}
