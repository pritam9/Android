package pritam.com.inclass06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Tess on 6/16/2016.
 */
public class DatabaseDataManager {
   private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private AppDAO appDAO;

    public DatabaseDataManager(Context mContext){
        this.mContext=mContext;
        dbOpenHelper=new DatabaseOpenHelper(this.mContext);
        db=dbOpenHelper.getWritableDatabase();
       appDAO=new AppDAO(db);
    }

    public void close(){
        if(db!=null){
            db.close();
        }
    }


    public AppDAO getAppDAO(){
        return this.appDAO;

    }


    public int saveApp(App app){
        return this.appDAO.save(app);

    }

    public boolean updateApp(App app){
        return this.appDAO.update(app);

    }

    public boolean deleteApp(App app){
        return this.appDAO.delete(app);

    }

    public App getApp(int id){
        return this.appDAO.get(id);

    }

    List<App> getAllApps(){
        return this.appDAO.getAll();

    }

    public boolean deleteAllRecords(){
        return this.appDAO.deleteAll();
    }

}
