package com.allandroidprojects.sampleecomapp.options;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.database.AppDatabase;
import com.allandroidprojects.sampleecomapp.database.CartDatabase;
import com.allandroidprojects.sampleecomapp.product.ItemDetailsActivity;
import com.allandroidprojects.sampleecomapp.startup.MainActivity;
import com.allandroidprojects.sampleecomapp.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.allandroidprojects.sampleecomapp.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.allandroidprojects.sampleecomapp.fragments.ImageListFragment.STRING_IMAGE_URI;
import static com.allandroidprojects.sampleecomapp.fragments.ImageListFragment.STRING_TEXT_DESCRIPTION;
import static com.allandroidprojects.sampleecomapp.fragments.ImageListFragment.STRING_TEXT_NAME;
import static com.allandroidprojects.sampleecomapp.fragments.ImageListFragment.STRING_TEXT_PRICE;

public class CartListActivity extends AppCompatActivity {
    private static final String LIST_STATE_KEY = "list_state_key";
    private static Context mContext;
    ArrayList<String> cartlistImageUri;
    private AppDatabase appDatabase;
    Parcelable itemList;
    static int mPosition;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;
    CartDatabase cartDatabase;
    static ArrayList<CartDatabase> mList;
    Boolean isClicked;
    LinearLayout layoutCartItems;
    LinearLayout layoutCartPayments;
    LinearLayout layoutCartNoItems;
    Button bStartShopping;
    TextView mCartTotal;
    TextView mCartPayment;
    ArrayList<CartDatabase> mList1;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mContext = CartListActivity.this;
        sp = getSharedPreferences("my_count_preference",MODE_PRIVATE);
        editor = sp.edit();
        mList1 = new ArrayList<>();
        mList = new ArrayList<>();
        mList = mList1;
        isClicked = false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);
        mCartTotal = (TextView) findViewById(R.id.text_action_bottom1);
        mCartPayment = (TextView) findViewById(R.id.text_action_bottom2);

        bStartShopping = (Button) findViewById(R.id.bAddNew);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
        cartlistImageUri =imageUrlUtils.getCartListImageUri();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        //Show cart layout based on items
        setCartLayout();
        displayList();



        recylerViewLayoutManager = new LinearLayoutManager(mContext);
        simpleStringRecyclerViewAdapter=new SimpleStringRecyclerViewAdapter(recyclerView,mList);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(simpleStringRecyclerViewAdapter);

    }

    private void displayList() {
        appDatabase = AppDatabase.getInstance(CartListActivity.this);
        new RetrieveTask(this).execute();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        itemList = recylerViewLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY,itemList);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            itemList=savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (itemList != null)
            recylerViewLayoutManager.onRestoreInstanceState(itemList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        //private ArrayList<String> mCartlistImageUri;
        private RecyclerView mRecyclerView;
        private List<CartDatabase> list;
        private LayoutInflater layoutInflater;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final TextView mTextViewName;
            public final TextView mTextDescrip;
            public final TextView mTextPrice;

            public final LinearLayout mLayoutItem, mLayoutRemove , mLayoutEdit;



            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
                mTextViewName = (TextView)view.findViewById(R.id.id_product_name);
                mTextDescrip = (TextView) view.findViewById(R.id.id_product_description);
                mTextPrice = (TextView) view.findViewById(R.id.id_product_price);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<CartDatabase> wishlistImageUri) {
            layoutInflater = LayoutInflater.from(getApplicationContext());
            list = wishlistImageUri;
            mRecyclerView = recyclerView;
        }

        @Override
        public CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.layout_cartlist_item, parent, false);
            return new CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);

//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            //final Uri uri = Uri.parse(mCartlistImageUri.get(position));
            //holder.mImageView.setImageURI(uri);
            holder.mImageView.setImageURI(list.get(position).getStringImageUri());
            final String stringTextName = list.get(position).getItemName();
            holder.mTextViewName.setText(stringTextName);
            final String stringTextDescription = list.get(position).getItemDescription();
            holder.mTextDescrip.setText(stringTextDescription);
            final String stringTextPrice = list.get(position).getItemPrice();
            holder.mTextPrice.setText("₹" + stringTextPrice);

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI,list.get(position).getStringImageUri());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    intent.putExtra(STRING_TEXT_NAME, stringTextName);
                    intent.putExtra(STRING_TEXT_DESCRIPTION, stringTextDescription);
                    intent.putExtra(STRING_TEXT_PRICE, stringTextPrice);
                    mContext.startActivity(intent);
                }
            });

            //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isClicked = true;
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    //imageUrlUtils.removeCartListImageUri(position);
                    //appDatabase.cartDoa().delete();
                    //mList.get(position).getStringImageUri()
                    //appDatabase.cartDoa().delete(mList.get(position));
                    //mList.remove(position);
                    mPosition = position;
                    new RetrieveTask(CartListActivity.this).execute();

                    notifyDataSetChanged();
                    //Decrease notification count

                    MainActivity.notificationCountCart--;
                    if (MainActivity.notificationCountCart == 0) {
                        /*CartListActivity cartListActivity = new CartListActivity();
                        cartListActivity.setCartLayout();*/
                        /* int found = 1;
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment emptyList = new EmptyLsuper.onPostExecute(aBoolean);ist();
                        //FragmentTransaction transaction = view
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.frame_container,emptyList).addToBackStack(null).commit();*/
                        openFragment();
                    }
                }
            });

            //Set click action
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
/*
        private void openFragment() {
            EmptyList emptyList  = EmptyList.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.replace(R.id.frame_container,emptyList).addToBackStack(null).commit();
            *//*CartListActivity cartListActivity = new CartListActivity();
            cartListActivity.setCartLayout();*//*


        }*/

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private void openFragment() {
        setCartLayout();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void setCartLayout() {

        if(mList != null && mList.size()>0) {
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        } else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        //onSaveInstanceState(itemList);
        isClicked = false;
        editor.putInt("count_int",mList.size());
        editor.apply();

        super.onDestroy();
    }

    private class RetrieveTask extends AsyncTask<Void,Void,List<CartDatabase>> {
        private WeakReference<CartListActivity> activityWeakReference;

        RetrieveTask(CartListActivity context) {
            activityWeakReference=new WeakReference<>(context);
        }
        @Override
        protected List<CartDatabase> doInBackground(Void... voids) {
            if (activityWeakReference.get()!=null) {
                return activityWeakReference.get().appDatabase.cartDoa().getallCartDatabase();
            } else {
                return null;
            }

            }

        @Override
        protected void onPostExecute(List<CartDatabase> cartDatabases) {
            if (cartDatabases != null && cartDatabases.size()>0) {
                activityWeakReference.get().mList.clear();
                activityWeakReference.get().mList.addAll(cartDatabases);
                layoutCartNoItems.setVisibility(View.GONE);
                layoutCartItems.setVisibility(View.VISIBLE);
                layoutCartPayments.setVisibility(View.VISIBLE);
                activityWeakReference.get().simpleStringRecyclerViewAdapter.notifyDataSetChanged();
                String cartTotal;
                int total = 0;
                int foo = 0;
                for (int i=0;i<mList.size();i++) {
                    try {

                        foo = Integer.parseInt(mList.get(i).getItemPrice());
                    } catch (NumberFormatException e) {
                        foo = 0;

                    }

                    total = total + foo;
                }
                cartTotal = String.valueOf(total);
                mCartTotal.setText("₹" + " " + cartTotal);
                if (isClicked) {
                    total = 0;
                    appDatabase.cartDoa().delete(mList.get(mPosition));
                    mList.remove(mPosition);
                    for (int i=0;i<mList.size();i++) {
                        try {

                            foo = Integer.parseInt(mList.get(i).getItemPrice());
                        } catch (NumberFormatException e) {
                            foo = 0;

                        }

                        total = total + foo;
                    }
                    cartTotal = String.valueOf(total);
                    mCartTotal.setText("₹" + " " + cartTotal);
                    setCartLayout();
                }



            } else {
                layoutCartNoItems.setVisibility(View.VISIBLE);
                layoutCartItems.setVisibility(View.GONE);
                layoutCartPayments.setVisibility(View.GONE);
                activityWeakReference.get().simpleStringRecyclerViewAdapter.notifyDataSetChanged();
                bStartShopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }
    }

}

