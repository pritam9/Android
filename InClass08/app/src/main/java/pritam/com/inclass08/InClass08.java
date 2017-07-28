package pritam.com.inclass08;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class InClass08 extends AppCompatActivity implements AppPreviewFragment.OnFragmentInteractionListener,ListviewFragment.OnFragmentInteractionListener{

    private App previewApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getFragmentManager().beginTransaction()
                .add(R.id.mainContainer,new LoginFragment(),"LOGIN").commit();
    }



    @Override
    public void onPreviewItemClick(App app) {
        getFragmentManager().beginTransaction().replace(R.id.mainContainer,new AppPreviewFragment(),"PREVIEW").addToBackStack(null).commit();
        previewApp=app;
        /*AppPreviewFragment preview_Fragment= (AppPreviewFragment) getFragmentManager().findFragmentByTag("PREVIEW");
        if(preview_Fragment!=null)
        {
            Log.d("PREVIEW","FRAGMENT NOT NULL");
        }
        else
            Toast.makeText(this, "Fragment not available!!", Toast.LENGTH_SHORT).show();
*/


    }

    @Override
    public void onFragmentSwitch(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void loadAppDetails() {
        AppPreviewFragment preview_Fragment= (AppPreviewFragment) getFragmentManager().findFragmentByTag("PREVIEW");
        if(preview_Fragment!=null)
        {
            TextView title = (TextView) findViewById(R.id.fPreviewTitle);
            ImageView img = (ImageView) findViewById(R.id.fPreviewImage);
            title.setText(previewApp.getApp_title());
            Picasso.with(this).load(previewApp.getLarge_photo_url()).into(img);

        }
        else
            Toast.makeText(this, "Fragment not available!!", Toast.LENGTH_SHORT).show();
    }
}
