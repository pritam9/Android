package pritam.com.inclass06;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity implements AppInterface{
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
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
        ListView appList = (ListView) findViewById(R.id.appList);
        AppListAdapter adapter = new AppListAdapter(AppActivity.this,R.layout.list_view,apps);
        appList.setAdapter(adapter);
        appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AppActivity.this,AppPreview.class);
                intent.putExtra("APP",apps.get(position));

                startActivity(intent);
            }
        });
        progressDialog.dismiss();
    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
