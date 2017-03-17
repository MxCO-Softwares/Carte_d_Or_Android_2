package pinklady.carte_d_or_android_2.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import pinklady.carte_d_or_android_2.AsyncTasks.GetNearbySearch;
import pinklady.carte_d_or_android_2.Adapter.PlaceItemAdapter;
import pinklady.carte_d_or_android_2.Utils.Places;
import pinklady.carte_d_or_android_2.R;

public class NearbySearch extends Fragment {

    private Activity mActivity;

    ListView nearbySearchListView;
    PlaceItemAdapter mAdapter;

    public NearbySearch() {
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public static NearbySearch newInstance(Activity activity) {
        NearbySearch nearbySearch = new NearbySearch();
        nearbySearch.setmActivity(activity);
        return nearbySearch;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_search, container, false);

        Button nearbySearchButton = (Button) view.findViewById(R.id.nearbySearchButton);

        nearbySearchListView = (ListView) view.findViewById(R.id.nearbySearchListView);

        nearbySearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra("ID", mAdapter.getPlace_idAtPosition(position));
                startActivity(intent);
            }
        });

        nearbySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null)
                {
                    mAdapter.clear();
                }
                new GetNearbySearch(MainActivity.getWeb_Api_Key(),
                        ((MainActivity)mActivity).getGpsTracker().getLocation(),
                        1000){
                    @Override
                    protected void onPostExecute(Places places) {
                        mAdapter = new PlaceItemAdapter(mActivity, R.layout.place_item, places.getPlaceArrayList());
                        nearbySearchListView.setAdapter(mAdapter);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                        super.onPostExecute(places);

                    }
                }.execute();
            }
        });
        return view;
    }

}
