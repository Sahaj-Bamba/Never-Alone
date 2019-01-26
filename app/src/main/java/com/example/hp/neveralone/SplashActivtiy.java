package com.example.hp.neveralone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivtiy extends AppCompatActivity {

    private ViewPager viewPager ;
    private LinearLayout linearLayout;

    FirebaseUser firebaseUser;

    private TextView[] mDots;

    private SlideAdapter slideAdapter;

    private Button mNextBtn;

    private Button mBackbtn;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activtiy);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(SplashActivtiy.this , LoginActivity.class);
            startActivity(intent);
        }


        viewPager= (ViewPager) findViewById(R.id.viewpager);
        linearLayout = (LinearLayout)findViewById(R.id.dots);

        mBackbtn = (Button)findViewById(R.id.prev_btn);

        mNextBtn = (Button) findViewById(R.id.next_btn);

        slideAdapter =  new SlideAdapter(this);

        viewPager.setAdapter(slideAdapter);

        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage == mDots.length-1){
                    Intent intent = new Intent(SplashActivtiy.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    public void addDotsIndicator (int position) {

        mDots = new TextView[3];
        linearLayout.removeAllViews();
        for(int i=0; i<mDots.length; i++ ){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            linearLayout.addView(mDots[i]);

        }

        if(mDots.length >=0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }




    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            mCurrentPage = i;

            if(i==0){
                mNextBtn.setEnabled(true);
                mBackbtn.setEnabled(false);
                mBackbtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackbtn.setText("");

            } else if(i==mDots.length-1) {
                mNextBtn.setEnabled(true);
                mBackbtn.setEnabled(true);
                mBackbtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Finish");
                mBackbtn.setText("Back");
            }else {
                mNextBtn.setEnabled(true);
                mBackbtn.setEnabled(true);
                mBackbtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBackbtn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
