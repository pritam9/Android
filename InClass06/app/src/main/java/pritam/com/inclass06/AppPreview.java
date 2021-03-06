package pritam.com.inclass06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AppPreview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_preview);
        TextView title = (TextView) findViewById(R.id.previewTitle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setTitle("App Preview");

        ImageView previewImage = (ImageView) findViewById(R.id.previewImage);
        App app = (App) getIntent().getExtras().getSerializable("APP");
        Picasso.with(this).load(app.getLarge_photo_url()).placeholder(R.drawable.preview_file).into(previewImage);
        title.setText(app.getApp_title());
    }
}
