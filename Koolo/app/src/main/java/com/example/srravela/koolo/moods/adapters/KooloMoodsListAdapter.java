package com.example.srravela.koolo.moods.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.MoodShot;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.moods.listeners.KooloMoodsListener;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by srikar on 5/12/15.
 */
public class KooloMoodsListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = KooloMoodsListAdapter.class.getSimpleName();
    public List<MoodShot> items = null;
    LayoutInflater layoutInflater;
    public Context context;
    private KooloMoodsListener mListener;


    /**
     * Constructor used for initializing the KooloChecklistItemAdapter.
     * @param context
     * @param items
     */
    public KooloMoodsListAdapter(List<MoodShot> items, Context context, KooloMoodsListener updateListener ) {
        super();
        this.items = items;
        this.context = context;
        this.mListener = updateListener;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Method that returns the dropdown view.
     * @param position
     * @param convertView
     * @param parent
     * @return itemView
     */
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View itemView = getView(position, convertView, parent);
        return itemView;
    }

    /**
     * Method that returns the item view type.
     * @param position
     * @return position
     */
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * Method that returns the view type count.
     * @return int
     */
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * Method that returns the number of items.
     * @return int
     */
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        ViewHolder holder;
        if (convertView == null) {
            convertView= layoutInflater.inflate(R.layout.view_moodshot_item, null);
            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.moodShotImageDateRelLayout = (RelativeLayout) convertView.findViewById(R.id.moodshot_image_date_rv);
            holder.moodShotImageView = (ImageView)convertView.findViewById(R.id.moodshot_image);
            holder.moodShotTextView = (TextView)convertView.findViewById(R.id.moodshot_date_tv);
            holder.moodShotColorButton = (Button) convertView.findViewById(R.id.moodshot_color_button);
            holder.moodShotTraingleView = (View) convertView.findViewById(R.id.traingle_view);
           holder.moodShotColorButton.setTag(position);
            convertView.setTag(holder);
            holder.moodShotImageView.setOnClickListener(this);
            holder.moodShotImageView.getLayoutParams().height =  holder.moodShotImageView.getMaxHeight()/2;
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.


            holder = (ViewHolder) convertView.getTag();

        }
        MoodShot item = items.get(position);
       // Uri moodShotUri = Uri.parse(item.getMoodCaptureUri());
        if(item.getMoodCaptureUri()!= null) {
          //  holder.moodShotImageView.setImageURI(Uri.parse(item.getMoodCaptureUri()));
            InputStream inputStream = null;
            try {
               /* URL myUrl = new URL(item.getMoodCaptureUri());
                InputStream inputStream = (InputStream) myUrl.getContent();
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                holder.moodShotImageView.setImageDrawable(drawable);*/

                ContentResolver res = context.getContentResolver();
                Uri uri = Uri.parse(item.getMoodCaptureUri());
                inputStream =  res.openInputStream(uri);
               // Drawable drawable = Drawable.createFromStream(inputStream, null);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;

                options.inSampleSize = 4;

              //  Bitmap myBitmap = BitmapFactory.decodeFile(uri.toString(),options);
                Bitmap preview_bitmap = BitmapFactory.decodeStream(inputStream, null, options);

                Drawable drawable = new BitmapDrawable(Resources.getSystem(),preview_bitmap);
               // updateDrawable(d);
                holder.moodShotImageView.setImageDrawable(drawable);

               // holder.moodShotImageView.getLayoutParams().height = 50;
               // holder.moodShotImageView.setBackgroundColor(Color.WHITE);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
               if(inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        if(item.getMoodCaptureDate() !=null) {
            String dateString = item.getMoodCaptureDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String formattedDate = null;
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(dateString);
                System.out.println(convertedDate);
                formattedDate = targetFormat.format(convertedDate);
                System.out.println(formattedDate);
                holder.moodShotTextView.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        holder.moodShotColorButton.setOnClickListener(this);
        Utils.ColorType colorType =  Utils.ColorType.valueOf(item.getMoodColor());
        //Utils.ColorType colorType = item.getMoodColor();
        switch (colorType) {
            case RED:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_red);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Red));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_red));
                break;
            case GREEN:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_theme_green);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.SkyBlue));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_sky_blue));
                break;
            case YELLOW:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_yellow);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Yellow));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_yellow));
                break;
            case ORANGE:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_orange);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Orange));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_orange));
                break;
            case GREY:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_grey);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Light_Grey));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_light_grey));
                break;
            case BLACK:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_black);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Black));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_black));
                break;
            case BROWN:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_brown);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Brown));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_brown));
                break;
            case PINK:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_pink);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Pink));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_pink));
                break;
            case DARK_GREY:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_darkgrey);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Dark_Grey));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_dark_grey));
                break;
            case BLUE:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_blue);
                holder.moodShotImageDateRelLayout.setBackgroundColor(context.getResources().getColor(R.color.Blue));
                holder.moodShotTraingleView.setBackground(context.getResources().getDrawable(R.drawable.triangle_blue));
                break;
            default:
                break;
        }

        return convertView;
    }
    static class ViewHolder {
        RelativeLayout moodShotImageDateRelLayout;
        Button moodShotColorButton;
        TextView moodShotTextView;
        ImageView moodShotImageView;
        View moodShotTraingleView;
    }
    /**
     * Method to check whether if the list is empty or not.
     * @return boolean
     */
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public void onClick(View v) {
       /* Integer position = (Integer) v.getTag();
        MoodShot selectedMoodShot = items.get(position.intValue());
        Bundle bundle = new Bundle();
        bundle.putInt(KooloMoodsListener.KOOLO_MOODS_ACTION, KooloMoodsListener.MOOD_SHOT_SELECTED);
        mListener.onMoodsAction(bundle);*/
    }
}
