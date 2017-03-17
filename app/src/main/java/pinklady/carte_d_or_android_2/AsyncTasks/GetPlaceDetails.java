package pinklady.carte_d_or_android_2.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import pinklady.carte_d_or_android_2.Utils.PlaceDetails;

/**
 * Created by ETCHELECOU on 15/03/2017.
 */
public class GetPlaceDetails extends AsyncTask<String, Void, PlaceDetails> {

    // Paramètres obligatoires
    private String mKey;

    protected GetPlaceDetails(String mKey) {
        this.mKey = mKey;
    }

    @Override
    protected PlaceDetails doInBackground(String... params) {
        String place_id = params[0];
        PlaceDetails placeDetails = new PlaceDetails();

        String data = "";
        String request = "https://maps.googleapis.com/maps/api/place/details/json?" +
                "placeid=" + place_id + "&key=" + mKey;
        try {
            data = downloadUrl(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        placeDetails.parsePlaceDetailsJSON(data);
        return placeDetails;
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

