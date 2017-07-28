package pritam.com.appstoretop100;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Pritam on 6/12/16.
 */
public class ParserUtil {

    public static class AppParser{
        public static ArrayList<App> getApps(InputStream inputStream) throws XmlPullParserException, IOException {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream,"UTF-8");
            String namespace=parser.getNamespace();
            App app = null;
            ArrayList<App> apps = new ArrayList<App>();
            int event =parser.getEventType();
            Log.d("Demo","~~"+namespace);
            while (!(event== XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase("entry")))
            {
                event=parser.next();
                //Log.d("Demo",parser.getName());
            }
            //Log.d("Demo",parser.getName());

            while (event!=XmlPullParser.END_DOCUMENT)
            {
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equalsIgnoreCase("title"))
                        {
                            //app=new App();
                            app.setApp_title(parser.nextText().trim());
                            //apps.add(app);
                        }
                        else if(parser.getName().equalsIgnoreCase("id"))
                        {
                            app=new App();
                            //String url = parser.getAttributeValue(null,"href").trim();
                            String url = parser.nextText().trim();
                            app.setUrl(url);
                            //app.setUrl();
                        }
                        else if(parser.getName().equalsIgnoreCase("artist"))
                        {
                            String dev_name=parser.nextText().trim();
                            app.setDeveloper_name(dev_name);
                            //Log.d("Demo","~~~~"+dev_name);
                        }
                        else if(parser.getName().equalsIgnoreCase("price"))
                        {
                            String price = parser.getAttributeValue(null,"amount");
                            //Log.d("Demo","$$$$"+price);
                            price=price.substring(0,price.length()-3);
                                app.setAnd_app_price("$"+price);
                        }
                        else if (parser.getName().equalsIgnoreCase("image"))
                        {
                            if (parser.getAttributeValue(null,"height").equals("53"))
                                app.setSmall_photo_url(parser.nextText().trim());
                            else if (parser.getAttributeValue(null,"height").equals("100"))
                                app.setLarge_photo_url(parser.nextText().trim());
                        }
                        else if (parser.getName().equalsIgnoreCase("releaseDate"))
                        {
                            String releaseDate = parser.getAttributeValue(null,"label");
                            app.setReleaseDate(releaseDate);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equalsIgnoreCase("entry"))
                            apps.add(app);
                        break;
                }

                event=parser.next();
            }


            return apps;
        }



    }
}
