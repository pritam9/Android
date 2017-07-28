package pritam.com.inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements GetImage.ImageDisplayer{
    ProgressDialog progressBar;
    ArrayList<String> fetchedImageUrls;
    int imageId=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchedImageUrls = new ArrayList<String>();
        final TextView keywordText = (TextView) findViewById(R.id.keyword);
        /*findViewById(R.id.keyword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.alertDialogTitle).setItems(R.array.keywords, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Demo",""+which);
                        switch (which){
                            case 0: keywordText.setText("UNCC");
                                break;
                            case 1: keywordText.setText("Android");
                                break;
                            case 2: keywordText.setText("Winter");
                                break;
                            case 3: keywordText.setText("Aurora");
                                break;
                            case 4: keywordText.setText("Wonders");
                                break;
                        }

                    }
                }).show();
            }
        });*/

        findViewById(R.id.Go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                /*String keyword=keywordText.getText().toString().trim();
                if(keyword.equals("")||!(keyword.equals("UNCC")||keyword.equals("Android")||keyword.equals("Winter")||keyword.equals("Aurora")||keyword.equals("Wonders")))
                {
                    Toast.makeText(MainActivity.this, "Invalid Keyword, Please select again!!", Toast.LENGTH_SHORT).show();
                }
                else */

                if (isConnectedOnline()) {
                    //Toast.makeText(MainActivity.this, "Connection Available!!", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.alertDialogTitle).setItems(R.array.keywords, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("Demo",""+which);
                            switch (which){
                                case 0: keywordText.setText("UNCC");
                                    break;
                                case 1: keywordText.setText("Android");
                                    break;
                                case 2: keywordText.setText("Winter");
                                    break;
                                case 3: keywordText.setText("Aurora");
                                    break;
                                case 4: keywordText.setText("Wonders");
                                    break;
                            }
                            new GetImageLinks().execute(keywordText.getText().toString());
                        }
                    }).show();

                }
                else
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });

        ImageButton next = (ImageButton) findViewById(R.id.next);
        ImageButton prev = (ImageButton) findViewById(R.id.prev);
        next.setEnabled(false);
        prev.setEnabled(false);

        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("Demo",""+v.getId());
                                        imageId++;
                                        if(imageId==fetchedImageUrls.size())
                                            imageId=0;
                                        new GetImage(MainActivity.this).execute(fetchedImageUrls.get(imageId));
                                    }
                                }

        );

        prev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("Demo",""+v.getId());
                                        imageId--;
                                        if(imageId==-1)
                                            imageId=fetchedImageUrls.size()-1;
                                        new GetImage(MainActivity.this).execute(fetchedImageUrls.get(imageId));
                                    }
                                }

        );
        //findViewById(R.id.next)


    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void displayImage(Bitmap bitmap) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Log.d("Demo","Inside Display Image");
        progressBar.dismiss();
        if(bitmap!=null)
        {
            imageView.setImageBitmap(bitmap);

        }
        else
            Toast.makeText(MainActivity.this, "Unable to download Image at this point", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoading() {
        progressBar=new ProgressDialog(MainActivity.this);
        progressBar.setMessage("Loading Image...");
        progressBar.setCancelable(false);
        progressBar.show();
    }

    private class GetImageLinks extends AsyncTask<String,Void,ArrayList<String>>{



        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            Log.d("Demo",strings.toString());
            super.onPostExecute(strings);
            fetchedImageUrls=strings;
            if(fetchedImageUrls.size()==0)
                Toast.makeText(MainActivity.this, "No Images to display!!", Toast.LENGTH_SHORT).show();
            else {
                imageId=0;
                new GetImage(MainActivity.this).execute(strings.get(0));
                ImageButton next = (ImageButton) findViewById(R.id.next);
                ImageButton prev = (ImageButton) findViewById(R.id.prev);
                if(strings.size()!=1) {
                    next.setEnabled(true);
                    prev.setEnabled(true);
                }
                if(strings.size()==1)
                {
                    next.setEnabled(false);
                    prev.setEnabled(false);
                }
            }

        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> imageUrls = new ArrayList<String>();

            // Code for getting URLs
            StringBuilder sb = new StringBuilder();
            String fetchUrl ="http://dev.theappsdr.com/apis/summer_2016/inclass5/index.php?keyword="+params[0];
            try {
                URL url = new URL(fetchUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                InputStream is =con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                reader.close();
                Log.d("Demo",sb.toString());
               // imageUrls = Arrays.asList(sb.toString().split("\\s*;\\s*"));
                boolean isFirst =true;
                for(String imageUrl:sb.toString().split(";"))
                {
                    if(isFirst)
                        isFirst=false;
                    else
                        imageUrls.add(imageUrl.trim());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return imageUrls;
        }
    }
}
