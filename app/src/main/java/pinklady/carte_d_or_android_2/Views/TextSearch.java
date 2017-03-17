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
import android.widget.EditText;
import android.widget.ListView;

import pinklady.carte_d_or_android_2.Adapter.PlaceItemAdapter;
import pinklady.carte_d_or_android_2.AsyncTasks.GetTextSearch;
import pinklady.carte_d_or_android_2.R;
import pinklady.carte_d_or_android_2.Utils.Places;

public class TextSearch extends Fragment {

    private Activity mActivity;

    ListView textSearchListView;
    PlaceItemAdapter mAdapter;
    EditText textSearchEditText;

    public TextSearch() {
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public static TextSearch newInstance(Activity activity) {
        TextSearch textSearch = new TextSearch();
        textSearch.setmActivity(activity);
        return textSearch;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_search, container, false);

        Button textSearchButton = (Button) view.findViewById(R.id.textSearchButton);

        textSearchEditText = (EditText) view.findViewById(R.id.textSearchEditText);

        textSearchListView = (ListView) view.findViewById(R.id.textSearchListView);

        textSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra("ID", mAdapter.getPlace_idAtPosition(position));
                startActivity(intent);
            }
        });

        textSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textSearchEditText.getText().toString().isEmpty())
                {
                    return;
                }
                if (mAdapter != null)
                {
                    mAdapter.clear();
                }
                new GetTextSearch(MainActivity.getWeb_Api_Key()){
                    @Override
                    protected void onPostExecute(Places places) {
                        mAdapter = new PlaceItemAdapter(mActivity, R.layout.place_item, places.getPlaceArrayList());
                        textSearchListView.setAdapter(mAdapter);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                        super.onPostExecute(places);
                    }
                }.execute(textSearchEditText.getText().toString());
            }
        });
        return view;
    }

}
