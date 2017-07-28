/*
package pritam.com.inclass09;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity implements AppInterface{
    ProgressDialog progressDialog;
    List<App> appArrayList;
    DatabaseDataManager dbManager;
    List<App> favAppList;
    AppListAdapter appListFavAdapter;
    AppListAdapter adapter;
    boolean isFavChecked=false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        menu.clear();
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.horario, menu);

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ListView listView = (ListView) findViewById(R.id.appList);
        switch (item.getItemId())
        {
            case R.id.showFavs:
                isFavChecked=true;
                Log.d("Demo","Show Favourites!!!");

                favAppList = dbManager.getAllApps();
                appListFavAdapter =new AppListAdapter(AppActivity.this,R.layout.list_view,favAppList);
                appListFavAdapter.setNotifyOnChange(true);
                listView.setAdapter(appListFavAdapter);

                break;
            case R.id.showAll:
                isFavChecked=false;
                Log.d("Demo","Show All!!");
                //ListView listView = (ListView) findViewById(R.id.appList);
                //List<App> dbApps = dbManager.getAllApps();
                adapter =new AppListAdapter(AppActivity.this,R.layout.list_view,appArrayList);
                listView.setAdapter(adapter);
                break;

            default:
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        //favAppList=new ArrayList<App>();

        dbManager = new DatabaseDataManager(this);
        Log.d("DB",dbManager.getAllApps().toString());
        */
/*App app1 = new App();
        app1.setApp_title("Woila!!");
        app1.setAnd_app_price("0");
        app1.setSmall_photo_url("Smalll");
        app1.setLarge_photo_url("Large");
        app1.setReleaseDate("AAj");
        app1.setDeveloper_name("Hum");
        app1.setUrl("WWW");
        dbManager.saveApp(app1);
        Log.d("Demo",dbManager.getAllNotes().toString());*//*


        if(isConnectedOnline())
            new GetRssFeed(this).execute("http://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
        else
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        ListView appList = (ListView) findViewById(R.id.appList);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setTitle("App Activity");


    }

    @Override
    public void startActivity() {
        progressDialog=new ProgressDialog(AppActivity.this);
        progressDialog.setMessage("Loading Apps For You...");
        progressDialog.show();
    }

    @Override
    public void endActivity(final ArrayList<App> apps) {
        Log.d("Demo",apps.size()+" and First element is -"+apps.get(0).toString());
        checkIfFav(apps);
        appArrayList=apps;
        ListView appList = (ListView) findViewById(R.id.appList);
        adapter = new AppListAdapter(AppActivity.this,R.layout.list_view,appArrayList);
        adapter.setNotifyOnChange(true);
        appList.setAdapter(adapter);
        appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AppActivity.this,AppPreview.class);
                if(isFavChecked)
                    intent.putExtra("APP",favAppList.get(position));
                else
                    intent.putExtra("APP",appArrayList.get(position));

                startActivity(intent);
            }
        });

        appList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isFavChecked)
                {
                    App app = favAppList.get(position);
                    int index = appArrayList.indexOf(app);
                    App app2 = appArrayList.get(index);
                    app2.setFavourite(false);
                    appArrayList.remove(index);
                    appArrayList.add(index,app2);
                    if(app.isFavourite())
                    {
                        app.setFavourite(false);
                        dbManager.deleteApp(app);
                        favAppList.remove(position);
                        //appListFavAdapter.remove(app);
                        appListFavAdapter.notifyDataSetChanged();
                        //appListFavAdapter.getView(position-1,view,parent);
                        Toast.makeText(AppActivity.this, "App removed from favourite List", Toast.LENGTH_SHORT).show();
                        Log.d("DB",dbManager.getAllApps().toString());
                    }
                    */
/*else
                    {
                        app.setFavourite(true);
                        dbManager.saveApp(app);
                        //dbManager.getApp();
                    *//*
*/
/*appArrayList.remove(position);
                    appArrayList.add(position,app);
                    Log.d("DB",app.getId()+"");*//*
*/
/*
                        adapter.getView(position,view,parent);
                        Toast.makeText(AppActivity.this, "App added to favourite List", Toast.LENGTH_SHORT).show();
                        //Log.d("DB",dbManager.getAllNotes().toString());
                    }*//*

                }
                else
                {
                    App app = appArrayList.get(position);
                    if(app.isFavourite())
                    {
                        app.setFavourite(false);
                        dbManager.deleteApp(app);

                        adapter.getView(position,view,parent);
                        Toast.makeText(AppActivity.this, "App removed from favourite List", Toast.LENGTH_SHORT).show();
                        Log.d("DB",dbManager.getAllApps().toString());
                    }
                    else
                    {
                        app.setFavourite(true);
                        dbManager.saveApp(app);
                        //dbManager.getApp();
                    */
/*appArrayList.remove(position);
                    appArrayList.add(position,app);
                    Log.d("DB",app.getId()+"");*//*

                        adapter.getView(position,view,parent);
                        Toast.makeText(AppActivity.this, "App added to favourite List", Toast.LENGTH_SHORT).show();
                        //Log.d("DB",dbManager.getAllNotes().toString());
                    }
                }

                return true;
            }
        });
        progressDialog.dismiss();
    }

    private void checkIfFav(ArrayList<App> apps) {
        favAppList=dbManager.getAllApps();
        if(favAppList!=null)
        {
            for(int i=0;i<favAppList.size();i++)
            {
                App app = favAppList.get(i);
                if(apps.contains(app))
                {
                    int index = apps.indexOf(app);
                    apps.remove(index);
                    apps.add(index,app);
                }
            }
        }

    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    @Override
    protected void onDestroy() {

        dbManager.close();
        super.onDestroy();
    }
}
*/
