package pinklady.carte_d_or_android_2.Views;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "Nearby Search", "Text Search", "Radar Search" };
    private Activity activity;

    SampleFragmentPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position)
        {
            case 0:
                fragment = NearbySearch.newInstance(activity);
                break;
            case 1:
                fragment = TextSearch.newInstance(activity);
                break;
            case 2:
                fragment = RadarSearch.newInstance(activity);
                break;
            default:
                fragment = NearbySearch.newInstance(activity);
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}