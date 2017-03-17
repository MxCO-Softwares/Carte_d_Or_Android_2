package pinklady.carte_d_or_android_2.AsyncTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by ETCHELECOU on 15/03/2017.
 */
public class GetPhoto extends AsyncTask<Void, Void, Bitmap> {
    // Paramètres obligatoires
    private String key;
    private String photoreference ;
    private int maxheight ;

    protected GetPhoto(String key, String photoreference, int maxheight) {
        this.key = key;
        this.photoreference = photoreference;
        this.maxheight  = maxheight ;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        String request = "https://maps.googleapis.com/maps/api/place/photo?" +
                "maxheight=" + maxheight +
                "&photoreference=" + photoreference +
                "&key=" + key;
        return getBitmapFromURL(request);
    }

    private Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
