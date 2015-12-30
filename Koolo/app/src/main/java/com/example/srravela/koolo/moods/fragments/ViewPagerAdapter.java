package com.example.srravela.koolo.moods.fragments;

/**
 * Created by tviswana on 12/24/2015.
 */

 import android.content.Context;
 import android.content.Intent;
 import android.graphics.Typeface;
 import android.os.Bundle;
 import android.support.v4.view.PagerAdapter;
 import android.support.v4.view.ViewPager;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import com.example.srravela.koolo.R;
 import com.example.srravela.koolo.entities.Humour;
 import com.example.srravela.koolo.entities.MoodShot;
 import com.example.srravela.koolo.entities.Utils;
 import com.example.srravela.koolo.humor.activities.KooloHumourActivity;
 import com.example.srravela.koolo.moods.listeners.KooloMoodSelectListener;
 import com.example.srravela.koolo.moods.listeners.KooloMoodsListener;
 import com.example.srravela.koolo.settings.activities.KooloSettingsActivity;

 import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
   /* String[] colorName;
    int[] colorCode;*/
    LayoutInflater inflater;
    private KooloMoodSelectListener moodSelectListener;
    public List<Humour> items = null;
   /* public ViewPagerAdapter(Context context, int[] colorCode,String[] colorName,KooloMoodSelectListener moodSelectListener) {
        this.context = context;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.moodSelectListener = moodSelectListener;
    }*/
   public ViewPagerAdapter(Context context, List<Humour> items,KooloMoodSelectListener moodSelectListener) {
       this.context = context;
       this.items = items;
       this.moodSelectListener = moodSelectListener;
   }
    /**
     * Method that returns the item view type.
     * @param position
     * @return position
     */
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Method that returns the view type count.
     * @return int
     */
    public int getViewTypeCount() {
        return items.size();
    }

    /**
     * Method that returns the number of items.
     * @return int
     */
    public int getCount() {
        return items.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    Humour item;
    Humour selectedHumor;
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables
       final TextView colorNameTextView;
        View hunmourColorView;
        View colorCodeImageView;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hunmourColorView = inflater.inflate(R.layout.viewpager_item1, container,
                false);
        colorCodeImageView = (View) hunmourColorView.findViewById(R.id.color_iv);
       // colorCodeImageView.setImageResource();
      //  MoodShot item = items.get(position);
        //colorCodeImageView.setBackgroundResource(colorCode[position]);
        colorNameTextView = (TextView) hunmourColorView.findViewById(R.id.color_name_tv);

         item = items.get(position);
        String humourItemText = item.getHumourText();
        colorNameTextView.setText(humourItemText);
        colorCodeImageView.setTag(items.get(position));
     //   colorNameTextView.setText(colorName[position]);
        colorNameTextView.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Intent licenseIntent = new Intent(context, KooloHumourActivity.class);
                                                     context.startActivity(licenseIntent);
                                                 }
                                             }

        );

        colorNameTextView.setTypeface(null, Typeface.NORMAL);
        colorCodeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Bundle bundle = new Bundle();
                bundle.putString("COLOR_TYPE", item.getColorType().toString());
                bundle.putString("HUMOUR_TEXT",item.getHumourText());*/
                selectedHumor = (Humour)v.getTag();
                moodSelectListener.onMoodSelectListener(selectedHumor);
                colorNameTextView.setText(selectedHumor.getHumourText());
                colorNameTextView.setTypeface(null, Typeface.BOLD);
            }
        });
        // Add viewpager_item1.xml to ViewPager
        ((ViewPager) container).addView(hunmourColorView);


        //Set Parcel Major Location.
      //  View hunmourColorView = (View)convertView.findViewById(R.id.color_view);

        Utils.ColorType colorType = item.getColorType();
        switch (colorType) {
            case RED:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_red);
                break;
            case GREEN:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_theme_green);
                break;
            case YELLOW:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_yellow);
                break;
            case ORANGE:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_orange);
                break;
            case GREY:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_grey);
                break;
            case BLACK:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_black);
                break;
            case BROWN:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_brown);
                break;
            case PINK:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_pink);
                break;
            case DARK_GREY:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_darkgrey);
                break;
            case BLUE:
                colorCodeImageView.setBackgroundResource(R.drawable.drawable_color_round_blue);
                break;

            default:
                break;
        }
        return hunmourColorView;
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