package pinklady.carte_d_or_android_2.Utils;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ETCHELECOU on 13/03/2017.
 */
public class Places {

    private ArrayList<Place> placeArrayList;

    public Places() {
        placeArrayList = new ArrayList<>();
    }

    public ArrayList<Place> getPlaceArrayList() {
        return placeArrayList;
    }

    public void parsePlacesJSON(String data){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = null;
        try {
            if (jsonObject != null) {
                jsonArray = jsonObject.getJSONArray("results");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Place place = new Place();

                try {
                    // Get icon
                    if ((jsonArray.getJSONObject(i)).has("icon"))
                    {

                        place.setIcon((jsonArray.getJSONObject(i)).getString("icon"));
                    }

                    // Get geometry
                    if ((jsonArray.getJSONObject(i)).has("geometry"))
                    {
                        JSONObject geometryObject = (jsonArray.getJSONObject(i)).getJSONObject("geometry");
                        Double latitude= ((geometryObject.getJSONObject("location"))).getDouble("lat");
                        Double longitude= ((geometryObject.getJSONObject("location"))).getDouble("lng");
                        place.setGeometry(new Geometry(new LatLng(latitude, longitude)));
                    }

                    // Get name
                    if ((jsonArray.getJSONObject(i)).has("name"))
                    {
                        place.setName((jsonArray.getJSONObject(i)).getString("name"));
                    }

                    // Get photos
                    if ((jsonArray.getJSONObject(i)).has("photos"))
                    {
                        Photos photos = new Photos();
                        photos.parsePhotosJSON(jsonArray.getJSONObject(i).getJSONArray("photos"));
                        place.setPhotos(photos);
                    }

                    // Get place_id
                    if ((jsonArray.getJSONObject(i)).has("place_id"))
                    {
                        place.setPlace_id((jsonArray.getJSONObject(i)).getString("place_id"));
                    }

                    placeArrayList.add(place);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
