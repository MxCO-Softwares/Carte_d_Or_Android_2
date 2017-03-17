package pinklady.carte_d_or_android_2.Utils;

import java.util.ArrayList;

/**
 * Created by ETCHELECOU on 13/03/2017.
 */
public class Photo {

    private int height;
    private int width;
    private String photo_reference;

    Photo(int height, int width, String photo_reference) {
        this.height = height;
        this.width = width;
        this.photo_reference = photo_reference;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }
}
