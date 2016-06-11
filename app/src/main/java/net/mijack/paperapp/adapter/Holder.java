package net.mijack.paperapp.adapter;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.db.HistoryDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MiJack on 2016/6/2.
 */
public class Holder {
    static DateFormat format = new SimpleDateFormat("MM dd HH:mm");
    public String url;
    TextView idView;
    TextView apkUrlView;
    TextView apkMD5View;
    TextView taskTimeView;
    TextView statusView;
    private String id;

    public Holder(View view) {
        idView = (TextView) view.findViewById(R.id.id);
        apkUrlView = (TextView) view.findViewById(R.id.apkUrl);
        apkMD5View = (TextView) view.findViewById(R.id.apkMD5);
        taskTimeView = (TextView) view.findViewById(R.id.taskTime);
        statusView = (TextView) view.findViewById(R.id.status);
    }

    public void bind(Cursor cursor, int position) {
        idView.setText(String.valueOf(position));
        int index = cursor.getColumnIndex(HistoryDAO.COLUMN_ID);
        id = cursor.getString(index);
        bindValue(apkUrlView, cursor, HistoryDAO.COLUMN_APK_URL);
        bindValue(apkMD5View, cursor, HistoryDAO.COLUMN_FILE_MD5);
        bindValue(statusView, cursor, HistoryDAO.COLUMN_ANALYSIS_STATUS);
        try {
            index = cursor.getColumnIndex(HistoryDAO.COLUMN_CREATE_TIME);
            String time = format.format(new Date(cursor.getLong(index)));
            taskTimeView.setText(time);
        } catch (Exception e) {
        }
        url = apkUrlView.getText().toString();
    }

    private void bindValue(TextView idView, Cursor cursor, String column) {
        int index = cursor.getColumnIndex(column);
        String string = cursor.getString(index);
        idView.setText(string);
    }

    public String getId() {
        return id;
    }
}