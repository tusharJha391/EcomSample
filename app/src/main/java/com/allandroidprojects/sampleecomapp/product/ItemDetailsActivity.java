package com.allandroidprojects.sampleecomapp.product;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.database.AppDatabase;
import com.allandroidprojects.sampleecomapp.database.CartDatabase;
import com.allandroidprojects.sampleecomapp.fragments.ImageListFragment;
import com.allandroidprojects.sampleecomapp.fragments.ViewPagerActivity;
import com.allandroidprojects.sampleecomapp.model.ModelReviewer;
import com.allandroidprojects.sampleecomapp.notification.NotificationCountSetClass;
import com.allandroidprojects.sampleecomapp.options.CartListActivity;
import com.allandroidprojects.sampleecomapp.startup.MainActivity;
import com.allandroidprojects.sampleecomapp.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemDetailsActivity extends AppCompatActivity {
    int imagePosition;
    String stringImageUri;
    String stringText;
    String stringDescription;
    String stringPrice;
    private AppDatabase appDatabase;
    private CartDatabase cartDatabase;
    boolean ifClicked;
    ArrayList<String> mlistUrl;
    RecyclerView mRecyclerReviews;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelReviewer> modelReviewerArrayList;
    TextView mtextName;
    TextView mTextDescription;
    TextView mTextPrice;

    @Override
    protected void onDestroy() {
        ifClicked = false;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ifClicked = false;
        mlistUrl = new ArrayList<>();
        modelReviewerArrayList = new ArrayList<>();
        setContentView(R.layout.activity_item_details);
        mtextName = (TextView)findViewById(R.id.text_item_name);
        mTextPrice = (TextView)findViewById(R.id.text_item_price);
        SimpleDraweeView mImageView = (SimpleDraweeView)findViewById(R.id.image1);
        TextView textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);
        appDatabase = AppDatabase.getInstance(ItemDetailsActivity.this);
        mRecyclerReviews = (RecyclerView)findViewById(R.id.review_product_recycler);
        mRecyclerReviews.setHasFixedSize(true);
        ModelReviewer modelReviewer = new ModelReviewer(R.drawable.ic_man_review,"Jacob","Great buy! Perfect for everyday use.");
        modelReviewerArrayList.add(modelReviewer);
        ModelReviewer modelReviewer1 = new ModelReviewer(R.drawable.image_profile_review,"Martin","Everything is good about this product. A must buy!",4.5);
        modelReviewerArrayList.add(modelReviewer1);
        ModelReviewer modelReviewer2 = new ModelReviewer("Ravi","A reliable product for sure.");
        modelReviewerArrayList.add(modelReviewer2);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerReviews.setLayoutManager(layoutManager);

        AdapterClass adapterClass = new AdapterClass(modelReviewerArrayList);
        mRecyclerReviews.setAdapter(adapterClass);




        //Getting image uri from previous screen
        if (getIntent() != null) {
            stringImageUri = getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_URI);
            imagePosition = getIntent().getIntExtra(ImageListFragment.STRING_IMAGE_URI,0);
            stringText = getIntent().getStringExtra(ImageListFragment.STRING_TEXT_NAME);
            stringDescription = getIntent().getStringExtra(ImageListFragment.STRING_TEXT_DESCRIPTION);
            stringPrice = getIntent().getStringExtra(ImageListFragment.STRING_TEXT_PRICE);

        }

        if (stringImageUri == null) return;
        Uri uri = Uri.parse(stringImageUri);
        mImageView.setImageURI(uri);
        mtextName.setText(stringText);
        mTextPrice.setText("₹" + stringPrice);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ItemDetailsActivity.this, ViewPagerActivity.class);
                    intent.putExtra("position", imagePosition);
                    startActivity(intent);
            }
        });

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifClicked = true;
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addCartListImageUri(stringImageUri);
                cartDatabase = new CartDatabase(stringImageUri,stringText, stringDescription, stringPrice, "Delivery charge:Free", "Qty: 1","Size: 41");

                Toast.makeText(ItemDetailsActivity.this,"Item added to cart.",Toast.LENGTH_SHORT).show();
                new InsertTask(ItemDetailsActivity.this,cartDatabase).execute();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);

            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ifClicked) {


                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    imageUrlUtils.addCartListImageUri(stringImageUri);
                    cartDatabase = new CartDatabase(stringImageUri,stringText,stringDescription, stringPrice, "Delivery charge:Free", "Qty: 1","Size: 41");
                    new InsertTask(ItemDetailsActivity.this,cartDatabase).execute();
                    MainActivity.notificationCountCart++;
                    NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                    startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));
                } else
                    {
                    startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));
                }
            }
        });


    }
    public void setResult(CartDatabase cartDatabase, int flag) {
        setResult(flag,new Intent().putExtra("putItem",cartDatabase));

    }


    public static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<ItemDetailsActivity> activityWeakReference;
        private CartDatabase cartDatabase;

        InsertTask(ItemDetailsActivity itemDetailsActivity, CartDatabase cartDatabase) {
            activityWeakReference = new WeakReference<>(itemDetailsActivity);
            this.cartDatabase = cartDatabase;

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityWeakReference.get().appDatabase.cartDoa().insert(cartDatabase);
            return true;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                activityWeakReference.get().setResult(cartDatabase,1);
                //activityWeakReference.get().finish();
            }
        }
    }
    public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

        ArrayList<ModelReviewer> modelReviewers;

        public AdapterClass(ArrayList<ModelReviewer> modelReviewers) {
            this.modelReviewers = modelReviewers;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context parentContext = viewGroup.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parentContext);
            View contactView = layoutInflater.inflate(R.layout.review_products_view, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(contactView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            ModelReviewer mModel = modelReviewers.get(i);
            CircleImageView mCircleImageView = viewHolder.circleImageView;
            mCircleImageView.setImageResource(mModel.getCircleImageView());
            TextView textView = viewHolder.txtName;
            textView.setText(mModel.getmTextName());
            TextView mtextview = viewHolder.txtReview;
            mtextview.setText(mModel.getmTextReview());
            RatingBar bar = viewHolder.mRating;
            bar.setRating((float) mModel.getRatingNumber());
        }

        @Override
        public int getItemCount() {
            return modelReviewers.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public CircleImageView circleImageView;
            public TextView txtName;
            public TextView txtReview;
            public RatingBar mRating;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                circleImageView = (CircleImageView)itemView.findViewById(R.id.pic_user);
                txtName = (TextView)itemView.findViewById(R.id.text_user_name);
                txtReview = (TextView)itemView.findViewById(R.id.review_from_user);
                mRating = (RatingBar)itemView.findViewById(R.id.rating_product);
            }

        }
    }

}
