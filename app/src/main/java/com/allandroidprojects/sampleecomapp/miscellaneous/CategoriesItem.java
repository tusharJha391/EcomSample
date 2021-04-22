package com.allandroidprojects.sampleecomapp.miscellaneous;

import com.allandroidprojects.sampleecomapp.R;

import java.util.ArrayList;

public class CategoriesItem {
    public static ArrayList<SampleModelCategories> getCategories() {
        ArrayList<SampleModelCategories> sampleModelCategories = new ArrayList();
        SampleModelCategories s = new SampleModelCategories();
        s.setImageCategories(R.drawable.mobile_phone_and_computer_screen);
        s.setCategoriesName("Mobiles, Computers");
        s.setIconArrow(R.drawable.ic_keyboard_arrow_down_black_24dp);
        sampleModelCategories.add(s);
        s = new SampleModelCategories();
        s.setImageCategories(R.drawable.television_categorie);
        s.setCategoriesName("TV, Appliances");
        s.setIconArrow(R.drawable.ic_keyboard_arrow_down_black_24dp);
        sampleModelCategories.add(s);
        s = new SampleModelCategories();
        s.setImageCategories(R.drawable.clothes_categorie);
        s.setCategoriesName("Men's Fashion");
        s.setIconArrow(R.drawable.ic_keyboard_arrow_down_black_24dp);
        sampleModelCategories.add(s);
        s = new SampleModelCategories();
        s.setImageCategories(R.drawable.dress_women);
        s.setCategoriesName("Women's Fashion");
        s.setIconArrow(R.drawable.ic_keyboard_arrow_down_black_24dp);
        sampleModelCategories.add(s);
        s = new SampleModelCategories();
        s.setImageCategories(R.drawable.mixer);
        s.setCategoriesName("Home, Kitchen");
        s.setIconArrow(R.drawable.ic_keyboard_arrow_down_black_24dp);
        sampleModelCategories.add(s);
        s = new SampleModelCategories();
        s.setImageCategories(R.drawable.cream);
        s.setCategoriesName("Beauty, Health, Grocery");
        s.setIconArrow(R.drawable.ic_keyboard_arrow_down_black_24dp);
        sampleModelCategories.add(s);
        s = new SampleModelCategories();
        s.setImageCategories(R.drawable.tennis_raquet_ball);
        s.setCategoriesName("Sports, Fitness");
        s.setIconArrow(R.drawable.ic_keyboard_arrow_down_black_24dp);
        sampleModelCategories.add(s);
        s = new SampleModelCategories();
        s.setImageCategories(R.drawable.house_kitchen);
        s.setCategoriesName("Home, Furniture");
        s.setIconArrow(R.drawable.ic_keyboard_arrow_down_black_24dp);
        sampleModelCategories.add(s);
        return sampleModelCategories;
    }
}
