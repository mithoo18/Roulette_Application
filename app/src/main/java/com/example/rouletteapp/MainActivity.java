package com.example.rouletteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class MainActivity extends AppCompatActivity {
    public static final String[] partitions =
                                            {
                                                    "32 red","15 black","19 red","4 black","21 red","2 black","25 red","17 black","34 red","6 black",
                                                    "27 red","13 black","36 red","11 black","30 red","8 black","23 red","10 black","5 red","24 black",
                                                    "16 red","33 black","1 red","20 black","14 red","31 black","9 red","22 black","18 red","29 black",
                                                    "7 red","28 black","12 red","35 black","3 red","26 black","zero"
                                            };
//id assign ki hai
    @BindView(R.id.spinBtn)
    Button spinBtn;
    @BindView(R.id.resultTv)
    TextView resultTv;
    @BindView(R.id.wheel)
    ImageView Wheel;

    //WHEEL SPIN
    private static final Random RANDOM = new Random();
    private int degree = 0,degreeOld = 0;
    //div by 360 and 2 = half
    private static final float HALF_PARTITIONS = 360f/37f/2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.spinBtn)
    public void spin(View v) {
        degreeOld = degree % 360;
        degree = RANDOM.nextInt(360) + 720;//max - min+min
        RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(3600);
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new DecelerateInterpolator());//diff effect in start and end
        rotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //empty result
                resultTv.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //pointer ko set
                resultTv.setText(getSector(360 - (degree % 360)));//degrees
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        Wheel.startAnimation(rotateAnim);
    }
    private String getSector(int degrees) {
       int i = 0;
       String text = null;
        do {
        float start = HALF_PARTITIONS * ( i * 2 + 1 );
        float end = HALF_PARTITIONS *( i * 2 + 3 );

        if(degrees >=start && degrees <end) {
            //degree is in start;end
            //so text is equal to partitions[i]
            text = partitions[i];
        }

        i++;
        //testing
        }
        while(text==null && i <partitions.length );
        return text;
    }
}

