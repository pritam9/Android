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
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AppInterface{
    DatabaseReference mDatabase;
    String userName;
    ProgressDialog progressDialog;
    List<App> appArrayList;
    //DatabaseDataManager dbManager;
    List<App> favAppList;
    AppListAdapter appListFavAdapter;
    AppListAdapter adapter;
    boolean isFavChecked=false;
    final String[] favs={null} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //submitPost();
        Log.d("__________------" , mDatabase.toString());

        userName=getIntent().getExtras().getString("USERNAME");
        //writeNewPost("1", "me", "First", "Body");
        if(isConnectedOnline())
            new GetRssFeed(MainActivity.this).execute("http://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
        else
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        ListView appList = (ListView) findViewById(R.id.appList);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setTitle("App Activity");




        //Listener
        mDatabase.child("Test").child(userName).child("favs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String favString = dataSnapshot.getValue().toString();
                if(favString.equals("NoFavs"))
                {
                    Toast.makeText(MainActivity.this, "No Favs", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d("getFavList",favString);
                    favs[0] =favString;


                    Toast.makeText(MainActivity.this, "Favs", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


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

                favAppList = getAllApps();
                appListFavAdapter =new AppListAdapter(MainActivity.this,R.layout.list_view,favAppList);
                appListFavAdapter.setNotifyOnChange(true);
                listView.setAdapter(appListFavAdapter);

                break;
            case R.id.showAll:
                isFavChecked=false;
                Log.d("Demo","Show All!!");
                //ListView listView = (ListView) findViewById(R.id.appList);
                //List<App> dbApps = dbManager.getAllApps();
                adapter =new AppListAdapter(MainActivity.this,R.layout.list_view,appArrayList);
                listView.setAdapter(adapter);
                break;

            default:
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    private List<App> getAllApps() {
        List<App> favApp=new ArrayList<App>();
        String str = getFavList();
        if (str==null)
            Toast.makeText(MainActivity.this, "No Favs Bro!!", Toast.LENGTH_SHORT).show();
        else
        {
            for (String s:str.split(","))
            {
                if(!s.equals("")) {
                    int pos = Integer.parseInt(s);
                    App app = appArrayList.get(pos);
                    app.setFavourite(true);
                    Log.d("GETALLAPPS", app.app_title);
                    favApp.add(app);
                }
            }
        }


        return favApp;

    }

    @Override
    public void startActivity() {
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Apps For You...");
        progressDialog.show();
    }

    @Override
    public void endActivity(ArrayList<App> apps) {
        Log.d("Demo",apps.size()+" and First element is -"+apps.get(0).toString());
        appArrayList=apps;
        checkIfFav(apps);
        appArrayList=apps;
        ListView appList = (ListView) findViewById(R.id.appList);
        adapter = new AppListAdapter(MainActivity.this,R.layout.list_view,appArrayList);
        adapter.setNotifyOnChange(true);
        appList.setAdapter(adapter);
        appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,AppPreview.class);
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
                    Log.d("LongCLickListener",app.isFavourite()+" and App 2 "+app2.isFavourite());
                   /* if(app.isFavourite())
                    {*/
                        app.setFavourite(false);
                        checkToAddFavs(index);              //Latest addition
                        favAppList.remove(position);
                        //appListFavAdapter.remove(app);
                        appListFavAdapter.notifyDataSetChanged();
                        //appListFavAdapter.getView(position-1,view,parent);
                        Toast.makeText(MainActivity.this, "App removed from favourite List", Toast.LENGTH_SHORT).show();
                        //Log.d("DB",dbManager.getAllApps().toString());
                    //}
                    /*else
                    {
                        app.setFavourite(true);
                        dbManager.saveApp(app);
                        //dbManager.getApp();
                    *//*appArrayList.remove(position);
                    appArrayList.add(position,app);
                    Log.d("DB",app.getId()+"");*//*
                        adapter.getView(position,view,parent);
                        Toast.makeText(AppActivity.this, "App added to favourite List", Toast.LENGTH_SHORT).show();
                        //Log.d("DB",dbManager.getAllNotes().toString());
                    }*/
                }
                else
                {
                    App app = appArrayList.get(position);
                    if(app.isFavourite())
                    {
                        app.setFavourite(false);
                        checkToAddFavs(position);
                        //dbManager.deleteApp(app);

                        adapter.getView(position,view,parent);
                        Toast.makeText(MainActivity.this, "App removed from favourite List", Toast.LENGTH_SHORT).show();
                        //Log.d("DB",dbManager.getAllApps().toString());
                    }
                    else
                    {
                        app.setFavourite(true);
                        String f_apps=getFavList();
                        if (f_apps==null)
                        {
                            savFavs(""+position);
                        }
                        else
                            savFavs(f_apps+","+position);

                        //Log.d("SaveIs",f_apps);
                        //dbManager.saveApp(app);
                        //dbManager.getApp();
                    /*appArrayList.remove(position);
                    appArrayList.add(position,app);
                    Log.d("DB",app.getId()+"");*/
                        adapter.getView(position,view,parent);
                        Toast.makeText(MainActivity.this, "App added to favourite List", Toast.LENGTH_SHORT).show();
                        //Log.d("DB",dbManager.getAllNotes().toString());
                    }
                }

                return true;
            }
        });
        progressDialog.dismiss();

    }


    private void checkToAddFavs(int position){
        String f_apps=getFavList();
        String sb ="";
        for(String pos:f_apps.split(","))
        {
            if(!pos.equals(""+position))
                sb=sb+pos+",";
        }


        if (sb.equals(""))
        {
            favs[0]=null;
            savFavs("NoFavs");
        }
        else
            savFavs(sb);
    }

    private void checkIfFav(ArrayList<App> apps) {
        favAppList=getAllApps();
        /*String f_apps= getFavList();
        Log.d("RRRRRR",f_apps);*/
        if(favAppList!=null)
        {
            for(int i=0;i<favAppList.size();i++)
            {
                App app = favAppList.get(i);
                //app.setFavourite(true);
                if(apps.contains(app))
                {
                    //Log.d()
                    int index = apps.indexOf(app);
                    apps.remove(index);
                    apps.add(index,app);
                }
            }
        }

    }

/*



            Firebase Methods



 */
    private String getFavList(){
        return favs[0];
    }


    private void savFavs(String s){
        mDatabase.child("Test").child(userName).child("favs").setValue(s);
        Log.d("savApps",s);

    }

    private void deleteFavs(String s){

    }




}
