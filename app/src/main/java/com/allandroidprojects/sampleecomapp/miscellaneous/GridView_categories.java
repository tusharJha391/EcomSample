package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.model.Categories;
import com.allandroidprojects.sampleecomapp.model.CategoryData;

import java.util.ArrayList;
import java.util.List;

public class GridView_categories extends AppCompatActivity {
    RecyclerView recyclerView;
    public static String whichActivityFrom = "whichActivityFrom";
    String[] women_fashion_name = new String[]{"Clothes", "Footwear", "Bags", "Watches", "Fragrance", "Sunglasses"};
    Integer[] womenFashion = new Integer[]{Integer.valueOf(R.drawable.dress_women), Integer.valueOf(R.drawable.footware_female), Integer.valueOf(R.drawable.clothing_handbag), Integer.valueOf(R.drawable.women_watches), Integer.valueOf(R.drawable.perfume_bottle), Integer.valueOf(R.drawable.sunglasses)};
    String[] electronics_name = new String[]{"SmartPhone","Accessories","Laptops","Tablets","Storage","Wearable"};
    Integer[] electronicsItem = new Integer[]{R.drawable.smartphone,R.drawable.headphones,R.drawable.laptop,R.drawable.ipad
            ,R.drawable.pen_drive,R.drawable.smartwatch};
    String[] tv_appliances_name = new String[]{"Televisions","Cameras","Large Appliances","Speakers","Kitchen Appliances","Office Appliances"};
    Integer[] tv_appliances_items = new Integer[]{R.drawable.smart_tv,R.drawable.camera_item,R.drawable.refrigerator
            ,R.drawable.speaker,R.drawable.blender,R.drawable.printer};
    String[] men_fashion_name = new String[]{"Clothing","Shoes","Watches","Bags & Luggage","Sunglasses","Accessories"};
    Integer[] men_fashion_items = new Integer[]{R.drawable.polo,R.drawable.shoes,R.drawable.wristwatch_of_circular_shape
            ,R.drawable.bag,R.drawable.sunglasses_men,R.drawable.purse};
    String[] home_kitchen_name = new String[]{"kitchen Utensils","Furniture","Home Decor","Garden","Pet Food","Home Furnishing"};
    Integer[] home_kitchen_items = new Integer[]{R.drawable.cooking_pot,R.drawable.sofa,R.drawable.chandelier
            ,R.drawable.plant,R.drawable.dog_food,R.drawable.window};
    String[] beauty_grocery_name = new String[]{"Beauty","Fragrances","Health & Supplements","Grocery","Personal care","Others"};
    Integer[] beauty_grocery_items = new Integer[]{R.drawable.cosmetics,R.drawable.perfume_item,R.drawable.protein
            ,R.drawable.groceries,R.drawable.hair_clipper,R.drawable.packaging};
    String[] sports_fitness_name = new String[]{"Cricket","Badminton","Football","Fitness","Trolley bags","Sport Ware"};
    Integer[] sports_fitness_items = new Integer[]{R.drawable.cricket,R.drawable.badminton,R.drawable.football
            ,R.drawable.dumbbells,R.drawable.suitcase,R.drawable.football_shirt};
    String[] furniture_name = new String[]{"Bed","Sofa","Cupboard","Tables & Chairs","Dining sets","Racks"};
    Integer[] furniture_item = new Integer[]{R.drawable.bed_profile,R.drawable.sofa_set,R.drawable.cupboard
            ,R.drawable.table_with_chair,R.drawable.dining_set,R.drawable.shelves};



    private List<Categories> categoriesList;
    /* renamed from: com.enthuons.ecommerce.miscellaneous.GridView_categories$1 *//*
    class C03561 implements OnItemClickListener {
        C03561() {
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            Context baseContext = GridView_categories.this.getBaseContext();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Grid Item ");
            stringBuilder.append(position + 1);
            stringBuilder.append(" Selected");
            Toast.makeText(baseContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;
        private final String[] women_fashion_name;
        private final Integer[] womenFashion;

        public ImageAdapterGridView(Context c, String[] women_fashion_name, Integer[] womenFashion) {
            this.mContext = c;
            this.womenFashion = womenFashion;
            this.women_fashion_name = women_fashion_name;
        }

        public int getCount() {
            return this.womenFashion.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView != null) {
                return convertView;
            }
            View gridView = new View(this.mContext);
            gridView = inflater.inflate(R.layout.grid_layout, null);
            ((TextView) gridView.findViewById(R.id.grid_item_label)).setText(this.women_fashion_name[position]);
            ((ImageView) gridView.findViewById(R.id.grid_item_image)).setImageResource(this.womenFashion[position].intValue());
            return gridView;
        }
    }*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.gridview_catagories);
        recyclerView = (RecyclerView) findViewById(R.id.grid_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();




        categoriesList=new ArrayList<>();
        List<CategoryData> categoryDataList=new ArrayList<>();
        for (int i=0; i<8; i++) {
            if (i==0) {
                Categories categories=new Categories();
                CategoryData categoryData=new CategoryData();
                categories.setCategoryHeader("Mobiles, Computers");
                categoryData.setCategItemName(electronics_name);
                categoryData.setCategItemImage(electronicsItem);
                categoryDataList.add(categoryData);
                categories.setCategoryData(categoryDataList);
                categoriesList.add(categories);
            } else if (i==1) {
                Categories categories=new Categories();
                CategoryData categoryData=new CategoryData();
                categories.setCategoryHeader("TV, Appliances");
                categoryData.setCategItemName(tv_appliances_name);
                categoryData.setCategItemImage(tv_appliances_items);
                categoryDataList.add(categoryData);
                categories.setCategoryData(categoryDataList);
                categoriesList.add(categories);
            } else if (i==2) {
                Categories categories=new Categories();
                CategoryData categoryData=new CategoryData();
                categories.setCategoryHeader("Men's Fashion");
                categoryData.setCategItemName(men_fashion_name);
                categoryData.setCategItemImage(men_fashion_items);
                categoryDataList.add(categoryData);
                categories.setCategoryData(categoryDataList);
                categoriesList.add(categories);
            } else if (i==3) {
                Categories categories=new Categories();
                CategoryData categoryData=new CategoryData();
                categories.setCategoryHeader("Women's Fashion");
                categoryData.setCategItemName(women_fashion_name);
                categoryData.setCategItemImage(womenFashion);
                categoryDataList.add(categoryData);
                categories.setCategoryData(categoryDataList);
                categoriesList.add(categories);
            } else if (i==4) {
                Categories categories=new Categories();
                CategoryData categoryData=new CategoryData();
                categories.setCategoryHeader("Home, Kitchen");
                categoryData.setCategItemName(home_kitchen_name);
                categoryData.setCategItemImage(home_kitchen_items);
                categoryDataList.add(categoryData);
                categories.setCategoryData(categoryDataList);
                categoriesList.add(categories);

            } else if (i==5) {
                Categories categories=new Categories();
                CategoryData categoryData=new CategoryData();
                categories.setCategoryHeader("Beauty, Health, Grocery");
                categoryData.setCategItemName(beauty_grocery_name);
                categoryData.setCategItemImage(beauty_grocery_items);
                categoryDataList.add(categoryData);
                categories.setCategoryData(categoryDataList);
                categoriesList.add(categories);

            } else if (i==6) {
                Categories categories=new Categories();
                CategoryData categoryData=new CategoryData();
                categories.setCategoryHeader("Sports, Fitness");
                categoryData.setCategItemName(sports_fitness_name);
                categoryData.setCategItemImage(sports_fitness_items);
                categoryDataList.add(categoryData);
                categories.setCategoryData(categoryDataList);
                categoriesList.add(categories);

            } else  if (i==7) {
                Categories categories=new Categories();
                CategoryData categoryData=new CategoryData();
                categories.setCategoryHeader("Home, Furniture");
                categoryData.setCategItemName(furniture_name);
                categoryData.setCategItemImage(furniture_item);
                categoryDataList.add(categoryData);
                categories.setCategoryData(categoryDataList);
                categoriesList.add(categories);

            }
        }
        RecyclerViewAdapterCategories adapter=new RecyclerViewAdapterCategories(this,categoriesList);
        recyclerView.setAdapter(adapter);
        if (bd !=null && getIntent().getExtras()!=null) {
            int position = 0;
            String activityFrom =(String) bd.getString(whichActivityFrom);
            switch (getIntent().getStringExtra(whichActivityFrom)) {
                case "MyAdapterCategories":
                     position = (int)bd.get("name");
                    break;

                case "MainActivity" :
                    position = (int)bd.get("jump_to");
                    break;

            }
            recyclerView.smoothScrollToPosition(position);


        }



/*      this.androidGridVi
ew = (GridView) findViewById(R.id.gridview_android_example);
        this.androidGridView.setAdapter(new ImageAdapterGridView(this, this.women_fashion_name, this.womenFashion));
        this.androidGridView.setOnItemClickListener(new C03561());*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
        super.onDestroy();
    }
}
