package com.example.bluejackpharmacyv2.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.activities.Login;
import com.example.bluejackpharmacyv2.utils.UserDatabaseHelper;

public class ProfileFragment extends Fragment {
    View view;
    private Button logoutButton;
    private TextView profileNameTextview, profileEmailTextView, profilePhoneTextview;
    private LinearLayout profileCard;
    Context context;
    UserDatabaseHelper userDb;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.context = getContext();
        Bundle args = getArguments();

        if(args != null){
            email = args.getString("email");
        }
        initialize(email);
        return view;
    }

    public void initialize(String email){

        profileNameTextview = view.findViewById(R.id.profile_name);
        profileEmailTextView = view.findViewById(R.id.profile_email);
        profilePhoneTextview = view.findViewById(R.id.profile_phone);
        profileCard = view.findViewById(R.id.profile_card);

        logoutButton = view.findViewById(R.id.logout_button);

        setInfo(email);
        setListener();
    }

    public void setInfo(String email){
        userDb = new UserDatabaseHelper(context);
        String profileName = userDb.getName(email).toString();
        String profilePhone = userDb.getPhoneNumber(email).toString();

        profileNameTextview.setText(profileName);
        profileEmailTextView.setText(email);
        profilePhoneTextview.setText(profilePhone);
    }

    public void setListener(){
        logoutButton.setOnClickListener(e -> {
            startLogin();
        });

        profileCard.setForeground(ContextCompat.getDrawable(context, R.drawable.clicked_profile));
        profileCard.setOnClickListener(v ->{

        });
    }

    public void startLogin(){
        context = getContext();
        Intent intent = new Intent(context, Login.class);
        startActivity(intent);
        getActivity().finish();
    }
}