package pritam.com.inclass06;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Pritam on 6/14/16.
 */
public class GetRssFeed extends AsyncTask<String,Void,ArrayList<App>> {
    AppInterface appInterface;

    public GetRssFeed(AppActivity mainActivity){
        this.appInterface=mainActivity;

    }

    @Override
    protected ArrayList<App> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode =con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK)
            {
                InputStream inputStream =con.getInputStream();

                return ParserUtil.AppParser.getApps(inputStream);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<App> apps) {
        super.onPostExecute(apps);
        appInterface.endActivity(apps);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        appInterface.startActivity();
    }


}
