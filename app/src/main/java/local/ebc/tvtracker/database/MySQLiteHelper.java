package local.ebc.tvtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ebc on 25.09.2016.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "TVtracker.db";
    private static final int DATABASE_VERSION = 10;
    // Assignments
    public static final String TABLE_TVSHOWS = "tvshows";
    public static final String COLUMN_TVSHOW_ID = "tvshow_id";
    public static final String COLUMN_TVSHOW = "tvshow";
    public static final String COLUMN_TVSHOW_DESCRIPTION = "description";
    public static final String COLUMN_TVSHOW_IMGPATH = "imgpath";
    public static final String COLUMN_TVSHOW_RATING = "rating";
    public static final String COLUMN_TVSHOW_DATEADDED = "dateadded";
    public static final String COLUMN_TVSHOW_STATUS = "status";

    // Creating the table
    private static final String DATABASE_CREATE_TVSHOWS =
            "CREATE TABLE " + TABLE_TVSHOWS +
                    "(" +
                    COLUMN_TVSHOW_ID + " integer primary key autoincrement, " +
                    COLUMN_TVSHOW + " text not null, " +
                    COLUMN_TVSHOW_DESCRIPTION + " text, " +
                    COLUMN_TVSHOW_IMGPATH + " text, " +
                    COLUMN_TVSHOW_RATING + " integer, " +
                    COLUMN_TVSHOW_DATEADDED + " text, " +
                    COLUMN_TVSHOW_STATUS + " text" +
                    ");";

    // Mandatory constructor which passes the context, database name and database version and passes it to the parent
        public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Execute the sql to create the table assignments
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_TVSHOWS);
    }


    //Cleanup in case of change in database structure.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS);
        onCreate(db);
    }
}
