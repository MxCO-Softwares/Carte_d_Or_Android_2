package pinklady.carte_d_or_android_2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pinklady.carte_d_or_android_2.AsyncTasks.GetIcon;
import pinklady.carte_d_or_android_2.Utils.Place;
import pinklady.carte_d_or_android_2.R;

/**
 * Created by ETCHELECOU on 13/03/2017.
 */
public class PlaceItemAdapter extends ArrayAdapter<Place> {

    private final Context context;
    private int layoutResourceId;
    private ArrayList<Place> places;
    private ArrayList<Bitmap> bitmaps;

    public PlaceItemAdapter(Context context, int layoutResourceId, ArrayList<Place> places) {
        super(context, layoutResourceId, places);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.places = places;
        bitmaps = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            bitmaps.add(null);
        }
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final PlaceHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PlaceHolder();
            holder.placeIcon = (ImageView)row.findViewById(R.id.placeIcon);
            holder.placeName = (TextView)row.findViewById(R.id.placeName);
            row.setTag(holder);
        }
        else
        {
            holder = (PlaceHolder)row.getTag();
        }

        Place place = places.get(position);
        holder.placeName.setText((place.getName()!=null?place.getName():place.getPlace_id()));
        if (place.getIcon() != null)
        {
            if (bitmaps.get(position) == null )
            {
                holder.placeIcon.setImageBitmap(null);
                new GetIcon(place.getIcon()){
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        bitmaps.set(position, bitmap);
                        holder.placeIcon.setImageBitmap(bitmap);
                    }
                }.execute();
            }
            else
            {
                holder.placeIcon.setImageBitmap(bitmaps.get(position));
            }
        }

        return row;
    }

    private static class PlaceHolder
    {
        ImageView placeIcon;
        TextView placeName;
    }

    public String getPlace_idAtPosition (int position)
    {
        return places.get(position).getPlace_id();
    }
}
