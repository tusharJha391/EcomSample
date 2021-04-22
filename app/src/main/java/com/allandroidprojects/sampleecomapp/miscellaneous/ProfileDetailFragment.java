package com.allandroidprojects.sampleecomapp.miscellaneous;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.BuildConfig;
import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.authentication.SignInActivity;
import com.allandroidprojects.sampleecomapp.fragments.EditFragment;
import com.allandroidprojects.sampleecomapp.model.ProfileUserModel;
import com.allandroidprojects.sampleecomapp.options.CartListActivity;
import com.allandroidprojects.sampleecomapp.options.WishlistActivity;
import com.allandroidprojects.sampleecomapp.startup.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class ProfileDetailFragment extends Fragment {

    RecyclerView profile_recycler;
    CustomProfileDetailAdapter customProfileDetailAdapter;
    ArrayList<ProfileUserModel> models = new ArrayList<>();
    TextView userName;
    TextView userNumber;
    TextView userMail;
    String getUserName;
    String getUsermail;
    String getUserNumber;


    public ProfileDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_page_user,container,false);
        ImageView profile_image = (ImageView)view.findViewById(R.id.profile_pic);
        userName = (TextView)view.findViewById(R.id.user_name);
        userName.setText(MainActivity.mUserName);
        userNumber = (TextView)view.findViewById(R.id.profile_phone_number);
        userNumber.setText(MainActivity.mUserPhone);
        userMail = (TextView)view.findViewById(R.id.profile_mail);
        userMail.setText(MainActivity.mUserMail);
        ImageView settingIcon = (ImageView)view.findViewById(R.id.setting_icon);
        ImageView cartIcon = (ImageView)view.findViewById(R.id.cart_profile_detail);
        profile_recycler = (RecyclerView)view.findViewById(R.id.profile_detail_recycler);
        ImageView iconEdit = (ImageView)view.findViewById(R.id.shop_cart_profile_edit);

        customProfileDetailAdapter = new CustomProfileDetailAdapter(models);
        profile_recycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        profile_recycler.setItemAnimator(new DefaultItemAnimator());
        //profile_recycler.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));
        models.clear();
        prepareProfileDetailList();
        customProfileDetailAdapter.notifyDataSetChanged();
        profile_recycler.setAdapter(customProfileDetailAdapter);
        //getUserName = userName.getText().toString();
        //getUsermail = userMail.getText().toString();
        //getUserNumber = userNumber.getText().toString();
        getUserName = MainActivity.mUserName;
        getUsermail = MainActivity.mUserMail;
        getUserNumber = MainActivity.mUserPhone;


        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CartListActivity.class));
            }
        });
        iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFragment();

            }
        });


        customProfileDetailAdapter.setClickListener(new CustomProfileDetailAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                    Log.d(TAG, "onItemClick position: " + position);
                    Toast.makeText(getContext(),"got" + position,Toast.LENGTH_SHORT).show();
                    if (position == 0) {
                        startActivity(new Intent(getActivity(),CartListActivity.class));
                    }
                    if (position == 1) {
                        startActivity(new Intent(getActivity(), WishlistActivity.class));
                    }
                    if (position == 3) {
                        swapFragment();
                    }
                    if (position == 4) {
                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                            String shareMessage= "\nLet me recommend you this application\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));

                        }

                        catch (Exception e) {

                        }
                    }
                    if (position == 7) {
                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        Toast.makeText(getActivity(),"User logged out!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), SignInActivity.class));
                    }


            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void swapFragment() {

        Fragment editFragment = EditFragment.newInstance(getUserName,getUsermail,getUserNumber);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.id_main_coordinator,editFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void prepareProfileDetailList() {
        ProfileUserModel profileUserModel = new ProfileUserModel(R.drawable.ic_shopping_bag,"Orders");
        models.add(profileUserModel);
        profileUserModel = new ProfileUserModel(R.drawable.ic_heart,"Likes");
        models.add(profileUserModel);
        profileUserModel = new ProfileUserModel(R.drawable.ic_chat,"Comments");
        models.add(profileUserModel);
        profileUserModel = new ProfileUserModel(R.drawable.ic_pencil,"Edit");
        models.add(profileUserModel);
        profileUserModel = new ProfileUserModel(R.drawable.ic_share,"Share this App");
        models.add(profileUserModel);
        profileUserModel = new ProfileUserModel(R.drawable.ic_smartphone,"Rate us");
        models.add(profileUserModel);
        profileUserModel = new ProfileUserModel(R.drawable.ic_info,"About us");
        models.add(profileUserModel);
        profileUserModel = new ProfileUserModel(R.drawable.ic_logout,"Logout");
        models.add(profileUserModel);

    }

    @Override
    public void onDestroyView() {
        //MainActivity.coordinatorLayout.setVisibility(View.VISIBLE);

        super.onDestroyView();
    }
}
