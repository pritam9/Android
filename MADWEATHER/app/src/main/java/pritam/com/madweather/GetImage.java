package pritam.com.madweather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pritam on 6/9/16.
 */
class GetImage extends AsyncTask<String, Void, Bitmap> {

    WeatherDisplay weatherDisplay;

    public GetImage(WeatherDisplay weatherDisplay) {
        this.weatherDisplay = weatherDisplay;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            String urlE = "http://openweathermap.org/img/w/" + params[0] + ".png";

            URL url = new URL(urlE);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream inputStream = con.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //imageDisplayer.startLoading();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        //imageDisplayer.displayImage(result);
        ImageView imageView =new ImageView(weatherDisplay);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(200,200));
        imageView.setImageBitmap(result);
        WeatherDisplay.imaggeDisplayer.addView(imageView);

    }
}
