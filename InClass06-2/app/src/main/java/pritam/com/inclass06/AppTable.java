package pritam.com.inclass06;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tess on 6/16/2016.
 */
public class AppTable {

    static final String TABLE_NAME="AppTable";
    static final String COLUMN_ID="_ID";
    static final String COLUMN_TITLE="APP_NAME";
    static final String COLUMN_DEVNAME="DEVELOPER_NAME";
    static final String COLUMN_URL="APP_URL";
    static final String COLUMN_SMALLICON="SMALL_ICON_URL";
    static final String COLUMN_BIGICON="LARGE_ICON_URL";
    static final String COLUMN_PRICE="PRICE";
    static final String COLUMN_RELEASEDATE="RELEASE_DATE";
    static final String COLUMN_CATEGORY="CATEGORY";

    static public void onCreate(SQLiteDatabase db){

        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+TABLE_NAME+" (");
        sb.append(COLUMN_ID+ " integer primary key, ");
        sb.append(COLUMN_TITLE+" text not null, ");
        sb.append(COLUMN_DEVNAME+" text not null, ");
        sb.append(COLUMN_RELEASEDATE+" text not null, ");
        sb.append(COLUMN_PRICE+" text not null, ");
        sb.append(COLUMN_BIGICON+" text not null, ");
        sb.append(COLUMN_SMALLICON+" text not null, ");
        sb.append(COLUMN_URL+" text not null, ");
        sb.append(COLUMN_CATEGORY+" text not null);");

        try{
        db.execSQL(sb.toString());
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        AppTable.onCreate(db);

    }

}
