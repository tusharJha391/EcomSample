package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allandroidprojects.sampleecomapp.R;
import com.bumptech.glide.Glide;

public class MyCustomPagerAdapter extends PagerAdapter {
    Context context;
    int[] images;
    LayoutInflater layoutInflater;

    /* renamed from: com.enthuons.ecommerce.miscellaneous.MyCustomPagerAdapter$1 */
    class C03571 implements OnClickListener {
        C03571() {
        }

        public void onClick(View view) {
            //ImageProvider(activity).getImage(ImageSource.CAMERA){ bitmap ->
            }
    }

    public MyCustomPagerAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.images.length;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = this.layoutInflater.inflate(R.layout.item_image_slider, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        //imageView.setImageResource(this.images[position]);
        Glide.with(context).load(this.images[position]).thumbnail(0.5f).into(imageView);

        container.addView(itemView);
        imageView.setOnClickListener(new C03571());
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
