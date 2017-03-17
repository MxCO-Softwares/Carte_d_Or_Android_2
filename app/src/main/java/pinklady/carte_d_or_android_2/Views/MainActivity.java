package pinklady.carte_d_or_android_2.Views;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import pinklady.carte_d_or_android_2.Utils.GPSTracker;
import pinklady.carte_d_or_android_2.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;

    private GPSTracker gpsTracker;

    private static final String Web_Api_Key = "AIzaSyD2nvgDTgvLSkaXxqX-xpdlOta9tKy9u4Q";

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        ArrayList<String> permissionsArrayList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsArrayList.add(Manifest.permission.INTERNET);

        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsArrayList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsArrayList.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        }

        if (permissionsArrayList.size() != 0)
        {
            String[] permissionsArray = permissionsArrayList.toArray(new String[permissionsArrayList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,
                    permissionsArray,
                    PERMISSION_REQUEST_CODE);
        }
        else
        {
            gpsTracker = new GPSTracker(activity);
        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                activity));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                boolean oneNotGranted = false;
                for (int i = 0; i < permissions.length; i++) {
                    Log.i(TAG, permissions[i] + " : " + grantResults[i]);
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                    {
                        oneNotGranted = true;
                    }
                }

                if (grantResults.length == 0
                        || oneNotGranted) {
                    return;
                }

                gpsTracker = new GPSTracker(activity);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        gpsTracker.stopUsingGPS();
        super.onDestroy();
    }

    public GPSTracker getGpsTracker() {
        return gpsTracker;
    }

    public static String getWeb_Api_Key() {
        return Web_Api_Key;
    }


}
