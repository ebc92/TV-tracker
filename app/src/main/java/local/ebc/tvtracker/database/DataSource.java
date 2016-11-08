package local.ebc.tvtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import local.ebc.tvtracker.model.Tvshow;

/**
 * Created by ebc on 25.09.2016.
 */

public class DataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] tvshowAllColumns = { MySQLiteHelper.COLUMN_TVSHOW_ID, MySQLiteHelper.COLUMN_TVSHOW, MySQLiteHelper.COLUMN_TVSHOW_DESCRIPTION, MySQLiteHelper.COLUMN_TVSHOW_IMGPATH, MySQLiteHelper.COLUMN_TVSHOW_RATING, MySQLiteHelper.COLUMN_TVSHOW_DATEADDED, MySQLiteHelper.COLUMN_TVSHOW_STATUS};

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }
    // Opens the database to use it
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    // Closes the database when you no longer need it
    public void close() {
        dbHelper.close();
    }
    public long createTvshow(Tvshow tvshow) {
        if (!database.isOpen()) {
            open();
        }

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TVSHOW, tvshow.getTitle());
        values.put(MySQLiteHelper.COLUMN_TVSHOW_DESCRIPTION, tvshow.getDescription());
        values.put(MySQLiteHelper.COLUMN_TVSHOW_IMGPATH, tvshow.getImgPath());
        values.put(MySQLiteHelper.COLUMN_TVSHOW_RATING, tvshow.getRating());
        values.put(MySQLiteHelper.COLUMN_TVSHOW_DATEADDED, tvshow.getDateadded());
        values.put(MySQLiteHelper.COLUMN_TVSHOW_STATUS, tvshow.getStatus());
        long insertId = database.insert(MySQLiteHelper.TABLE_TVSHOWS, null, values);
        if(database.isOpen()) {
            close();
        }
        return insertId;
    }
    public void deleteTvshow(Tvshow tvshow) {
        if (!database.isOpen())
            open();
        database.delete(MySQLiteHelper.TABLE_TVSHOWS, MySQLiteHelper.COLUMN_TVSHOW_ID + " =?", new String[] { Long.toString(tvshow.getId())});
        if (database.isOpen())
            close();
    }
    public void deleteAllTvshows() {
        if (!database.isOpen())
            open();
        database.execSQL("DELETE from tvshows");
        if (database.isOpen())
            close();
    }
    public void updateTvshow(Tvshow tvshow) {
        if (!database.isOpen())
            open();
        ContentValues args = new ContentValues();
        args.put(MySQLiteHelper.COLUMN_TVSHOW, tvshow.getTitle());
        args.put(MySQLiteHelper.COLUMN_TVSHOW_DESCRIPTION, tvshow.getDescription());
        args.put(MySQLiteHelper.COLUMN_TVSHOW_IMGPATH, tvshow.getImgPath());
        args.put(MySQLiteHelper.COLUMN_TVSHOW_RATING, tvshow.getRating());
        args.put(MySQLiteHelper.COLUMN_TVSHOW_DATEADDED, tvshow.getDateadded());
        args.put(MySQLiteHelper.COLUMN_TVSHOW_STATUS, tvshow.getStatus());

        database.update(MySQLiteHelper.TABLE_TVSHOWS, args, MySQLiteHelper.COLUMN_TVSHOW_ID + "=?", new String[] { Long.toString(tvshow.getId()) });
        if (database.isOpen())
            close();
    }
    public List<Tvshow> getAllTvshows() {
        if (!database.isOpen())
            open();
        List<Tvshow> tvshows = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TVSHOWS, tvshowAllColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tvshow tvshow = cursorToTvshow(cursor);
            tvshows.add(tvshow);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        if (database.isOpen())
            close();
        return tvshows;
    }
    private Tvshow cursorToTvshow(Cursor cursor) {
        try {
            long tvshowId = cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TVSHOW_ID));
            String tvshowTitle = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TVSHOW));
            String tvshowDescription = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TVSHOW_DESCRIPTION));
            String tvshowPath = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TVSHOW_IMGPATH));
            int tvshowRating = cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TVSHOW_RATING));
            String tvshowDateAdded = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TVSHOW_DATEADDED));
            String tvshowStatus = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_TVSHOW_STATUS));
            Tvshow tvshow = new Tvshow(tvshowId, tvshowTitle, tvshowDescription, tvshowPath, tvshowRating, tvshowDateAdded, tvshowStatus);

            return tvshow;
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public Tvshow getTvshow(int columnId) {
        if (!database.isOpen())
            open();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TVSHOWS, tvshowAllColumns, MySQLiteHelper.COLUMN_TVSHOW_ID + "=?", new String[] { Integer.toString(columnId)}, null, null, null);
        cursor.moveToFirst();
        Tvshow tvshow = cursorToTvshow(cursor);
        cursor.close();
        if (database.isOpen())
            close();
        return tvshow;
    }



}
