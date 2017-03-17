package pinklady.carte_d_or_android_2.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import pinklady.carte_d_or_android_2.Utils.Places;

/**
 * Created by ETCHELECOU on 13/03/2017.
 */
public class GetRadarSearch extends AsyncTask<Void, Void, Places> {

    // Paramètres obligatoires
    private LatLng location;
    private int radius;
    private String mKey;

    protected GetRadarSearch(String key, LatLng location, int radius) {
        this.mKey = key;
        this.location = location;
        this.radius = radius;
    }

    @Override
    protected Places doInBackground(Void... params) {
        Places places = new Places();
        String data = "";
        String request = "https://maps.googleapis.com/maps/api/place/radarsearch/" +
                "json?location=" + Double.toString(location.latitude) + "," + Double.toString(location.longitude) +
                "&radius=" + Integer.toString(radius) +
                "&key=" + mKey +
                "&types=food";
        try {
            data = downloadUrl(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        places.parsePlacesJSON(data);
        return places;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        HttpURLConnection urlConnection;
        URL url = new URL(strUrl);
        // Creating an http connection to communicate with url
        urlConnection = (HttpURLConnection) url.openConnection();
        // Connecting to url
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(10000);
        urlConnection.connect();
        // Reading data from url
        try (InputStream iStream = urlConnection.getInputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception downloading", e.toString());
        } finally {
            urlConnection.disconnect();
        }
        return data;
    }
}

