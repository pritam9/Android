package pritam.com.appstoretop100;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetRssFeed.AppInterface{
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isConnectedOnline())
            new GetRssFeed(this).execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=100/xml");
        else
        {
            Toast.makeText(MainActivity.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();
            ImageView bkgImage = (ImageView) findViewById(R.id.bkgImag);
            bkgImage.setImageResource(R.drawable.oops);
        }
    }

    @Override
    public void startActivity() {
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Apps...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    public void endActivity(ArrayList<App> apps) {
        Intent intent = new Intent(this,AppStore.class);
        intent.putExtra("APPS",apps);
        pd.dismiss();
        App app = apps.get(6);
        Log.d("Demo"," - "+apps.size()+" and Name is - "+app.getApp_title()+" - La la  - "+app.getDeveloper_name()+" $ $ "+app.getAnd_app_price()+" Small Pic is -"+app.getSmall_photo_url()+" & Large pic is - "+app.getLarge_photo_url());
        startActivity(intent);
        finish();
    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
