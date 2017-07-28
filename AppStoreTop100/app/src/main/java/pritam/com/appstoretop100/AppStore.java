package pritam.com.appstoretop100;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppStore extends AppCompatActivity{
    private static int appId = 0;
    private static int backButton = 0;

    @Override
    protected void onDestroy() {
        appId=0;
        backButton=0;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if(backButton==0)
        {
            Toast.makeText(AppStore.this, "Press one more time to exit", Toast.LENGTH_LONG).show();
            new WaitForSomeTime().execute();
        }
        else
        {
            backButton=-1;
            super.onBackPressed();
        }
        backButton++;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_store);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
      //  getSupportActionBar().setTitle("App");
        final ArrayList<App> apps = (ArrayList<App>) getIntent().getExtras().getSerializable("APPS");
        final App app = apps.get(appId);
        final TextView title = (TextView) findViewById(R.id.appTitle);
        final TextView developer = (TextView) findViewById(R.id.devName);
        final TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        final TextView price = (TextView) findViewById(R.id.price);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        title.setText(app.getApp_title());
        developer.setText(app.getDeveloper_name());
        price.setText(app.getAnd_app_price());
        releaseDate.setText(app.getReleaseDate());
        Picasso.with(this).
                load(app.getLarge_photo_url()).
                placeholder(R.drawable.loading_img).
                error(R.drawable.error).
                into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App currentApp = apps.get(appId);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentApp.getUrl()));
                startActivity(intent);

            }
        });



        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appId++;
                if(appId==apps.size())
                    appId=0;
                App nextApp=apps.get(appId);
                Picasso.with(AppStore.this).
                        load(nextApp.getLarge_photo_url()).
                        placeholder(R.drawable.loading_img).
                        error(R.drawable.error).
                        into(imageView);
                title.setText(nextApp.getApp_title());
                developer.setText(nextApp.getDeveloper_name());
                price.setText(nextApp.getAnd_app_price());
                releaseDate.setText(nextApp.getReleaseDate());
            }
        });

        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appId--;
                if(appId==-1)
                    appId=apps.size()-1;
                App prevApp=apps.get(appId);
                Picasso.with(AppStore.this).
                        load(prevApp.getLarge_photo_url()).
                        placeholder(R.drawable.loading_img).
                        error(R.drawable.error).
                        into(imageView);
                title.setText(prevApp.getApp_title());
                developer.setText(prevApp.getDeveloper_name());
                price.setText(prevApp.getAnd_app_price());
                releaseDate.setText(prevApp.getReleaseDate());
            }
        });





    }
    class WaitForSomeTime extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            backButton=0;
        }
    }


}
