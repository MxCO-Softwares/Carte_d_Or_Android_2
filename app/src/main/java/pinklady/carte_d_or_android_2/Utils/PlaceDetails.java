package pinklady.carte_d_or_android_2.Utils;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ETCHELECOU on 13/03/2017.
 */
public class PlaceDetails {

    private String formatted_address;
    private String formatted_phone_number;
    private Geometry geometry;
    private String icon;
    private String international_phone_number;
    private String name;
    private Photos photos;
    private String place_id;
    private int price_level;
    private double rating;
    private ArrayList<String> types;
    private String url;
    private String website;

    public PlaceDetails() {
        types = new ArrayList<>();
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public void setInternational_phone_number(String international_phone_number) {
        this.international_phone_number = international_phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public int getPrice_level() {
        return price_level;
    }

    public void setPrice_level(int price_level) {
        this.price_level = price_level;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void parsePlaceDetailsJSON(String data){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonResult = null;
        try {
            if (jsonObject != null) {
                jsonResult = jsonObject.getJSONObject("result");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonResult != null) {
            try {
                // Get formatted_address
                if (jsonResult.has("formatted_address"))
                {
                    this.formatted_address = jsonResult.getString("formatted_address");
                }

                // Get formatted_phone_number
                if (jsonResult.has("formatted_phone_number"))
                {
                    this.formatted_phone_number = jsonResult.getString("formatted_phone_number");
                }

                // Get geometry
                if (jsonResult.has("geometry"))
                {
                    JSONObject geometryObject = jsonResult.getJSONObject("geometry");
                    Double latitude= ((geometryObject.getJSONObject("location"))).getDouble("lat");
                    Double longitude= ((geometryObject.getJSONObject("location"))).getDouble("lng");
                    this.geometry = new Geometry(new LatLng(latitude, longitude));
                }

                // Get icon
                if (jsonResult.has("icon"))
                {
                    this.icon = jsonResult.getString("icon");
                }

                // Get international_phone_number
                if (jsonResult.has("international_phone_number"))
                {
                    this.international_phone_number = jsonResult.getString("international_phone_number");
                }

                // Get name
                if (jsonResult.has("name"))
                {
                    this.name = jsonResult.getString("name");
                }

                // Get photos
                if (jsonResult.has("photos"))
                {
                    Photos photos = new Photos();
                    photos.parsePhotosJSON(jsonResult.getJSONArray("photos"));
                    this.photos = photos;
                }

                // Get place_id
                if (jsonResult.has("place_id"))
                {
                    this.place_id = jsonResult.getString("place_id");
                }

                // Get price_level
                if (jsonResult.has("price_level"))
                {
                    this.price_level = jsonResult.getInt("price_level");
                }

                // Get rating
                if (jsonResult.has("rating"))
                {
                    this.rating = jsonResult.getDouble("rating");
                }

                // Get types
                if (jsonResult.has("types"))
                {
                    JSONArray jsonTypes = jsonResult.getJSONArray("types");
                    for (int i = 0; i < jsonTypes.length(); i++) {
                        types.add((String)jsonTypes.get(i));
                    }
                }

                // Get url
                if (jsonResult.has("url"))
                {
                    this.url = jsonResult.getString("url");
                }

                // Get website
                if (jsonResult.has("website"))
                {
                    this.website = jsonResult.getString("website");
                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}
