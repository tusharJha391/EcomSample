package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.allandroidprojects.sampleecomapp.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class ImageSliderActivity extends AppCompatActivity {
    DotsIndicator dotsIndicator;
    int[] images = new int[]{R.drawable.special_offer_image, R.drawable.pexelsphoto186613, R.drawable.special_offers, R.drawable.offer_image_slide};
    MyCustomPagerAdapter myCustomPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deals_layout);
        this.dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        setTitle("Offers Zone");
        this.myCustomPagerAdapter = new MyCustomPagerAdapter(this, this.images);
        this.viewPager.setAdapter(this.myCustomPagerAdapter);
        this.dotsIndicator.setViewPager(this.viewPager);
    }
}
