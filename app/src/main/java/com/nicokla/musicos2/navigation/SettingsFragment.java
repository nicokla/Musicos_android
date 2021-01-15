package com.nicokla.musicos2.navigation;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

import com.nicokla.musicos2.R;
import com.nicokla.musicos2.navigation.SettingsFragmentDirections;

public class SettingsFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button logOutButton=view.findViewById(R.id.logOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        Button followedUsersButton = view.findViewById(R.id.followedUsers);
        followedUsersButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate( // (view).navigate
                        SettingsFragmentDirections.Companion.seeFollowedUsers()
                );
            }
        });
        Button gemsButton = view.findViewById(R.id.gemsButton);
        gemsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate( // (view).navigate
                        SettingsFragmentDirections.Companion.actionSettingsFragmentToGemsFragment()
                );
            }
        });
        return view;
    }

    void logout(){
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.signOut();
//        HomeFragmentDirections.SeeVideo action =
//                HomeFragmentDirections.seeVideo(song.videoID);
        Navigation.findNavController(this.getView()).popBackStack(R.id.signInFrag, false);
//        Navigation.findNavController(this.getView()).popBackStack(R.id.signUpFrag, false);
        //.navigate(action);
    }
}
