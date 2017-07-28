package pritam.com.inclass05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pritam on 6/7/16.
 */
class GetImage extends AsyncTask<String, Void, Bitmap> {
    ImageDisplayer imageDisplayer;

    public GetImage(MainActivity mainActivity) {
        this.imageDisplayer = mainActivity;
    }

    @Override  protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream inputStream = con.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageDisplayer.startLoading();
    }

    @Override  protected void onPostExecute(Bitmap result) {
        imageDisplayer.displayImage(result);
    }

    public interface ImageDisplayer{
        public void displayImage(Bitmap bitmap);
        public void startLoading();
    }
}
