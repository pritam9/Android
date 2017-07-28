package pritam.com.inclass06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tess on 6/16/2016.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME="myapps.db";
    static final int DB_VERSION=1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    public void onCreate(SQLiteDatabase db){
        AppTable.onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        AppTable.onUpgrade(db,oldVersion,newVersion);
    }

}