package com.example.srravela.koolo.moods.fragments;

/**
 * Created by tviswana on 12/24/2015.
 */

 import android.content.Context;
 import android.content.Intent;
 import android.graphics.Typeface;
 import android.support.v4.view.PagerAdapter;
 import android.support.v4.view.ViewPager;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import com.example.srravela.koolo.R;
 import com.example.srravela.koolo.humor.activities.KooloHumourActivity;
 import com.example.srravela.koolo.settings.activities.KooloSettingsActivity;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    String[] colorName;
    int[] colorCode;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, int[] colorCode,String[] colorName) {
        this.context = context;
        this.colorCode = colorCode;
        this.colorName = colorName;
    }

    @Override
    public int getCount() {
        return colorCode.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables
       final TextView colorNameTextView;
        View colorCodeImageView;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item1, container,
                false);
        colorCodeImageView = (View) itemView.findViewById(R.id.color_iv);
       // colorCodeImageView.setImageResource();
        colorCodeImageView.setBackgroundResource(colorCode[position]);
        colorNameTextView = (TextView) itemView.findViewById(R.id.color_name_tv);
        colorNameTextView.setText(colorName[position]);
        colorNameTextView.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Intent licenseIntent = new Intent(context, KooloHumourActivity.class);
                                                     context.startActivity(licenseIntent);
                                                 }
                                             }

        );
        colorNameTextView.setText(colorName[position]);
        colorNameTextView.setTypeface(null, Typeface.NORMAL);
        colorCodeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorNameTextView.setText(colorName[position]);
                colorNameTextView.setTypeface(null, Typeface.BOLD);
            }
        });
        // Add viewpager_item1.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item1.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    @Override
    public float getPageWidth(int position) {
        return 0.3f;
    }
}