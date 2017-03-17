package pinklady.carte_d_or_android_2.AsyncTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by ETCHELECOU on 13/03/2017.
 */
public class GetIcon extends AsyncTask<Void, Void, Bitmap> {
    // Paramètres obligatoires
    private String url;

    protected GetIcon(String url) {
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return getBitmapFromURL(url);
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

