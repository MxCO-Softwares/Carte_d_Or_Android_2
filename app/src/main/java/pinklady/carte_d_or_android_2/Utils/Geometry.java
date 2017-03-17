package pinklady.carte_d_or_android_2.Utils;

import com.google.android.gms.maps.model.LatLng;

public class Geometry {

    private LatLng latLng;

    Geometry(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
