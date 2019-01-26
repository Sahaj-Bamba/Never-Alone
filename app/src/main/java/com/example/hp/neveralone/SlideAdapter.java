package com.example.hp.neveralone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context=context;
    }

    public int[] slide_images = {
            R.drawable.eat_icon,
            R.drawable.sleep_icon,
            R.drawable.code_icon
    };

    public String[] slider_headings = {

            "TRAVEL",
            "FIND COMPANIONS",
            "ENJOY"
    };

    public String[] slide_descs = {
            "Add Travel Checklist,Create events,Make new friends",
            "This app will help you to find travel companions for you",
            "Chat,Create Polls,Share Photos and Enjoy"
    };


    @Override
    public int getCount() {
        return slider_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);


        ImageView  slideImageView = (ImageView)view.findViewById(R.id.slide_image);
        TextView slideheading = (TextView)view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView)view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideheading.setText(slider_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;



    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {


        container.removeView((RelativeLayout)object);
    }
}