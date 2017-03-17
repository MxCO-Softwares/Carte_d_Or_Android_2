package pinklady.carte_d_or_android_2.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import pinklady.carte_d_or_android_2.AsyncTasks.GetIcon;
import pinklady.carte_d_or_android_2.AsyncTasks.GetPhoto;
import pinklady.carte_d_or_android_2.AsyncTasks.GetPlaceDetails;
import pinklady.carte_d_or_android_2.R;
import pinklady.carte_d_or_android_2.Utils.OnSwipeTouchListener;
import pinklady.carte_d_or_android_2.Utils.Photo;
import pinklady.carte_d_or_android_2.Utils.PlaceDetails;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";

    String placeid;

    TextView formatted_addressTextView;
    TextView formatted_phone_numberTextView;
    TextView latitudeTextView;
    TextView longitudeTextView;
    ImageView iconImageView;
    TextView international_phone_numberTextView;
    TextView nameTextView;
    LinearLayout PicturesLinearLayout;
    TextView place_idTextView;
    TextView price_levelTextView;
    TextView ratingTextView;
    TextView typesTextView;
    TextView urlTextView;
    TextView websiteTextView;
    ScrollView parentScrollView;
    HorizontalScrollView childScrollView;

    ImageView full_screen_imageView1;
    ImageView full_screen_imageView2;
    RelativeLayout full_screen_parent;

    AppCompatActivity appCompatActivity;

    int selected_image = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        appCompatActivity = this;

        formatted_addressTextView = (TextView) findViewById(R.id.formatted_addressTextView);
        formatted_phone_numberTextView = (TextView) findViewById(R.id.formatted_phone_numberTextView);
        latitudeTextView = (TextView) findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
        iconImageView = (ImageView) findViewById(R.id.iconImageView);
        international_phone_numberTextView = (TextView) findViewById(R.id.international_phone_numberTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        PicturesLinearLayout = (LinearLayout) findViewById(R.id.PicturesLinearLayout);
        place_idTextView = (TextView) findViewById(R.id.place_idTextView);
        price_levelTextView = (TextView) findViewById(R.id.price_levelTextView);
        ratingTextView = (TextView) findViewById(R.id.ratingTextView);
        typesTextView = (TextView) findViewById(R.id.typesTextView);
        urlTextView = (TextView) findViewById(R.id.urlTextView);
        websiteTextView = (TextView) findViewById(R.id.websiteTextView);
        parentScrollView = (ScrollView) findViewById(R.id.parentScrollView);
        childScrollView = (HorizontalScrollView) findViewById(R.id.childScrollView);

        full_screen_imageView1 = (ImageView) findViewById(R.id.full_screen_imageView1);
        full_screen_imageView2 = (ImageView) findViewById(R.id.full_screen_imageView2);
        full_screen_parent = (RelativeLayout) findViewById(R.id.full_screen_parent);
        full_screen_imageView1.setVisibility(View.GONE);
        full_screen_imageView2.setVisibility(View.GONE);
        full_screen_parent.setVisibility(View.GONE);

        full_screen_imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        full_screen_imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);

        full_screen_imageView1.setOnTouchListener(new OnSwipeTouchListener(appCompatActivity){
            @Override
            public boolean onSwipeLeft() {
                Log.d(TAG, "onSwipeLeft");
                ImageViewAnimatedChange(true);
                super.onSwipeLeft();
                return true;
            }

            @Override
            public boolean onSwipeRight() {
                Log.d(TAG, "onSwipeRight");
                ImageViewAnimatedChange(false);
                super.onSwipeRight();
                return true;
            }
        });

        full_screen_imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                full_screen_imageView1.setVisibility(View.GONE);
                full_screen_imageView2.setVisibility(View.GONE);
                full_screen_parent.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        placeid = getIntent().getStringExtra("ID");
        new GetPlaceDetails(MainActivity.getWeb_Api_Key()){
            @Override
            protected void onPostExecute(PlaceDetails placeDetails) {
                if (placeDetails.getFormatted_address() != null)
                {
                    formatted_addressTextView.setText(placeDetails.getFormatted_address());
                }
                if (placeDetails.getFormatted_phone_number() != null)
                {
                    formatted_phone_numberTextView.setText(placeDetails.getFormatted_phone_number());
                }
                if (placeDetails.getGeometry() != null)
                {
                    latitudeTextView.setText(String.format(Locale.getDefault(), "%f", placeDetails.getGeometry().getLatLng().latitude));
                    longitudeTextView.setText(String.format(Locale.getDefault(), "%f", placeDetails.getGeometry().getLatLng().longitude));
                }
                // Icon
                if (placeDetails.getIcon() != null)
                {
                    new GetIcon(placeDetails.getIcon()){
                        @Override
                        protected void onPostExecute(final Bitmap bitmap) {
                            super.onPostExecute(bitmap);
                            appCompatActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iconImageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }.execute();
                }
                if (placeDetails.getInternational_phone_number() != null)
                {
                    international_phone_numberTextView.setText(placeDetails.getInternational_phone_number());
                }
                if (placeDetails.getName() != null)
                {
                    nameTextView.setText(placeDetails.getName());
                }
                // Photos
                if (placeDetails.getPhotos() != null) {
                    for (Photo photo :
                            placeDetails.getPhotos().getPhotos()) {
                        new GetPhoto(MainActivity.getWeb_Api_Key(), photo.getPhoto_reference(), 400) {
                            @Override
                            protected void onPostExecute(final Bitmap bitmap) {
                                Log.d(TAG, "new image");
                                //ImageView Setup
                                final ImageView imageView = new ImageView(appCompatActivity);
                                //setting image resource
                                imageView.setImageBitmap(bitmap);
                                //setting image position
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400,
                                        400);
                                if (PicturesLinearLayout.getChildCount() != 0) {
                                    layoutParams.setMargins(20, 0, 0, 0);
                                }
                                imageView.setLayoutParams(layoutParams);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                //adding view to layout
                                PicturesLinearLayout.addView(imageView);

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        selected_image = PicturesLinearLayout.indexOfChild(imageView);
                                        ImageViewAnimatedFirst();
                                    }
                                });

                                super.onPostExecute(bitmap);
                            }
                        }.execute();
                    }
                }
                if (placeDetails.getPlace_id() != null)
                {
                    place_idTextView.setText(placeDetails.getPlace_id());
                }
                price_levelTextView.setText(String.format(Locale.getDefault(), "%d", placeDetails.getPrice_level()));
                ratingTextView.setText(String.format(Locale.getDefault(), "%f", placeDetails.getRating()));

                StringBuilder sb = new StringBuilder();
                boolean appendSeparator = false;
                for (String type:
                        placeDetails.getTypes()) {
                    if (appendSeparator)
                        sb.append(", "); // a comma
                    appendSeparator = true;

                    sb.append(type);
                }
                typesTextView.setText(sb.toString());

                urlTextView.setText(placeDetails.getUrl());
                websiteTextView.setText(placeDetails.getWebsite());

                super.onPostExecute(placeDetails);
            }
        }.execute(placeid);
    }

    public void ImageViewAnimatedFirst()
    {
        full_screen_parent.setVisibility(View.VISIBLE);

        full_screen_imageView1.setVisibility(View.VISIBLE);

        full_screen_imageView1.setClickable(false);

        final Bitmap new_image = ((BitmapDrawable)((ImageView)PicturesLinearLayout.getChildAt(selected_image)).getDrawable()).getBitmap();

        final Animation anim_in  = AnimationUtils.loadAnimation(appCompatActivity, android.R.anim.fade_in);

        full_screen_imageView1.setImageBitmap(new_image);
        anim_in.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) {
                full_screen_imageView1.setClickable(true);
            }
        });
        full_screen_imageView1.startAnimation(anim_in);

    }

    public void ImageViewAnimatedChange(boolean leftSwipe) {

        full_screen_imageView1.setClickable(false);

        final Animation anim_out;
        final Animation anim_in;

        if (leftSwipe)
        {
            anim_out = AnimationUtils.loadAnimation(appCompatActivity, R.anim.slide_out_left);
            anim_in = AnimationUtils.loadAnimation(appCompatActivity, R.anim.slide_in_right);

            if (selected_image == PicturesLinearLayout.getChildCount()-1)
            {
                selected_image = 0;
            }
            else
            {
                selected_image++;
            }
        }
        else
        {
            anim_out = AnimationUtils.loadAnimation(appCompatActivity, R.anim.slide_out_right);
            anim_in = AnimationUtils.loadAnimation(appCompatActivity, R.anim.slide_in_left);

            if (selected_image == 0)
            {
                selected_image = PicturesLinearLayout.getChildCount()-1;
            }
            else
            {
                selected_image--;
            }
        }
        final Bitmap new_image = ((BitmapDrawable)((ImageView)PicturesLinearLayout.getChildAt(selected_image)).getDrawable()).getBitmap();

        full_screen_imageView2.setImageBitmap(new_image);

        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {
                full_screen_imageView2.setVisibility(View.VISIBLE);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {
                        full_screen_imageView1.setClickable(true);
                        full_screen_imageView1.setVisibility(View.VISIBLE);
                        full_screen_imageView2.setVisibility(View.GONE);
                    }
                });
                full_screen_imageView2.startAnimation(anim_in);
            }
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                full_screen_imageView1.setImageBitmap(new_image);
                full_screen_imageView1.setVisibility(View.GONE);
            }
        });
        full_screen_imageView1.startAnimation(anim_out);
    }

}
