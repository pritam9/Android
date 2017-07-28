package pritam.com.inclass06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tess on 6/16/2016.
 */
public class AppDAO {

    private SQLiteDatabase db;

    public AppDAO(SQLiteDatabase db) {
        this.db = db;
    }


    public int save(App app){
        ContentValues values=new ContentValues();
        values.put(AppTable.COLUMN_ID, app.getId());
        values.put(AppTable.COLUMN_TITLE, app.getApp_title());
        values.put(AppTable.COLUMN_DEVNAME, app.getDeveloper_name());
        values.put(AppTable.COLUMN_PRICE, app.getAnd_app_price());
        values.put(AppTable.COLUMN_RELEASEDATE, app.getReleaseDate());
        values.put(AppTable.COLUMN_BIGICON, app.getLarge_photo_url());
        values.put(AppTable.COLUMN_SMALLICON, app.getSmall_photo_url());
        values.put(AppTable.COLUMN_URL, app.getUrl());
        values.put(AppTable.COLUMN_CATEGORY, app.getCategory());



        db.insert(AppTable.TABLE_NAME,null,values);

        return app.getId();

    }


    public boolean update(App app){
        ContentValues values=new ContentValues();
        values.put(AppTable.COLUMN_ID, app.getId());
        values.put(AppTable.COLUMN_TITLE, app.getApp_title());
        values.put(AppTable.COLUMN_DEVNAME, app.getDeveloper_name());
        values.put(AppTable.COLUMN_PRICE, app.getAnd_app_price());
        values.put(AppTable.COLUMN_RELEASEDATE, app.getReleaseDate());
        values.put(AppTable.COLUMN_BIGICON, app.getLarge_photo_url());
        values.put(AppTable.COLUMN_SMALLICON, app.getSmall_photo_url());
        values.put(AppTable.COLUMN_URL, app.getUrl());
        values.put(AppTable.COLUMN_CATEGORY, app.getCategory());



        return(db.update(AppTable.TABLE_NAME,values,AppTable.COLUMN_ID + "=?", new String[]{app.getId()+""})>0);


    }
    public boolean delete(App app){
            //db.
        return (db.delete(AppTable.TABLE_NAME,AppTable.COLUMN_ID+"=?", new String[]{app.getId()+""})>0);


    }

    public boolean deleteAll(){
        return (db.delete(AppTable.TABLE_NAME,null, null)>0);
    }

    public App get(int id){
        App app=null;

        Cursor c=db.query(true,AppTable.TABLE_NAME,
                            new String[]{AppTable.COLUMN_ID,
                                    AppTable.COLUMN_TITLE,
                                    AppTable.COLUMN_DEVNAME,
                                    AppTable.COLUMN_PRICE,
                                    AppTable.COLUMN_RELEASEDATE,
                                    AppTable.COLUMN_BIGICON,
                                    AppTable.COLUMN_SMALLICON,
                                    AppTable.COLUMN_URL,
                                    AppTable.COLUMN_CATEGORY},
                AppTable.COLUMN_ID+"=?",new String[]{id+""},null,null,null,null,null);

        if(c!=null&& c.moveToFirst()){
            app=buildAppFromCursor(c);

            if(!c.isClosed()){
                c.close();
            }

        }

        return app;

    }

    public List<App> getAll() {

        List<App> apps = new ArrayList<App>();

        Cursor c = db.query(AppTable.TABLE_NAME, new String[]{AppTable.COLUMN_ID, AppTable.COLUMN_TITLE, AppTable.COLUMN_DEVNAME,
                        AppTable.COLUMN_PRICE, AppTable.COLUMN_RELEASEDATE, AppTable.COLUMN_BIGICON, AppTable.COLUMN_SMALLICON, AppTable.COLUMN_URL, AppTable.COLUMN_CATEGORY}
                , null, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {

                if (apps != null) {
                    App app=buildAppFromCursor(c);
                    apps.add(app);
                }


            }
            while (c.moveToNext());

            if (!c.isClosed()) {
                c.close();
            }


        }
        return apps;
    }

    private App buildAppFromCursor(Cursor c){

        App app=null;
        if(c!=null){
            app=new App();
            app.setId(c.getInt(0));
            app.setApp_title(c.getString(1));
            app.setDeveloper_name(c.getString(2));
            app.setAnd_app_price(c.getString(3));
            app.setReleaseDate(c.getString(4));
            app.setLarge_photo_url(c.getString(5));
            app.setSmall_photo_url(c.getString(6));
            app.setUrl(c.getString(7));
            app.setCategory(c.getString(8));
            app.setFavourite(true);
        }
return app;
    }



}

