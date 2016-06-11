package net.mijack.paperapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentActivity;

/**
 * Created by MiJack on 2016/5/28.
 */
public class HistoryDAO {

    public static final String DATABASE_NAME = "histories.db";
    public static final String TABLE = "history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_APK_URL = "apk_url";
    public static final String COLUMN_FILE_MD5 = "file_md5";
    public static final String COLUMN_ANALYSIS_STATUS = "analysis_status";
    public static final String COLUMN_CREATE_TIME = "createTime";
    public static final String COLUMN_UPDATE_TIME = "updateTime";
    public static final String[] COLUMNS = new String[]{COLUMN_ID, COLUMN_APK_URL, COLUMN_FILE_MD5, COLUMN_ANALYSIS_STATUS, COLUMN_CREATE_TIME, COLUMN_UPDATE_TIME};

    public static void insertOrUpdateHistory(Context context, String apkUrl, String fileMd5, String createTime) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
//        判断之前是否存在相同的url
//        database.execSQL("SELECT id FROM " + TABLE + " WHERE " + COLUMN_APK_URL + "=''");
        int id = exist(database, apkUrl);
        if (id != -1) {
            updateHistory(database, apkUrl, fileMd5, createTime);
        } else {
            insertHistory(database, apkUrl, fileMd5, createTime);
        }
        database.close();
    }

    private static void insertHistory(SQLiteDatabase database, String apkUrl, String fileMd5, String createTime) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_APK_URL, apkUrl);
        values.put(COLUMN_FILE_MD5, fileMd5);
        values.put(COLUMN_CREATE_TIME, createTime);
        database.insert(TABLE, null, values);
    }

    private static void updateHistory(SQLiteDatabase database, String apkUrl, String fileMd5, String createTime) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FILE_MD5, fileMd5);
        values.put(COLUMN_CREATE_TIME, createTime);
        database.update(TABLE, values, COLUMN_APK_URL + "=?", new String[]{apkUrl});
    }

    private static int exist(SQLiteDatabase database, String apkUrl) {
        Cursor cursor = database.query(TABLE, new String[]{COLUMN_ID}, COLUMN_APK_URL + "=?", new String[]{apkUrl}, null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        return -1;
    }

    public static Cursor getQueryCursor(FragmentActivity activity) {
        DatabaseHelper helper = new DatabaseHelper(activity);
        SQLiteDatabase database = helper.getReadableDatabase();
        return database.query(TABLE, COLUMNS, null, null, null, null, null);
    }

    public static int delete(FragmentActivity activity, String id) {
        DatabaseHelper helper = new DatabaseHelper(activity);
        SQLiteDatabase database = helper.getReadableDatabase();
        return database.delete(TABLE, COLUMN_ID+"="+id,null);
    }


    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS \"history\"");
            db.execSQL("CREATE TABLE \"" + TABLE + "\" (\n" +
                    "\"" + COLUMN_ID + "\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "\"" + COLUMN_APK_URL + "\"  TEXT,\n" +
                    "\"" + COLUMN_FILE_MD5 + "\"  TEXT,\n" +
                    "\"" + COLUMN_ANALYSIS_STATUS + "\"  TEXT,\n" +
                    "\"" + COLUMN_CREATE_TIME + "\"  TEXT,\n" +
                    "\"" + COLUMN_UPDATE_TIME + "\"  TEXT\n" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
