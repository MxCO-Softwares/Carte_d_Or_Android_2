package pinklady.carte_d_or_android_2.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ETCHELECOU on 13/03/2017.
 */
public class Photos {

    private ArrayList<Photo> photos;

    Photos() {
        photos = new ArrayList<>();
    }

    void parsePhotosJSON(JSONArray photosArray) throws JSONException {

        for (int i = 0; i < photosArray.length(); i++) {
            int height = (int) photosArray.getJSONObject(i).get("height");
            int width = (int) photosArray.getJSONObject(i).get("width");
            String photo_reference = (String) photosArray.getJSONObject(i).get("photo_reference");

            Photo photo = new Photo(height, width, photo_reference);

            photos.add(photo);
        }

    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
