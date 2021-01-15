package com.nicokla.musicos2.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.nicokla.musicos2.Firebase.SongFirestore;
import com.nicokla.musicos2.Firebase.UserFirestore;
import com.nicokla.musicos2.MainAndCo.GlobalVars;

import com.nicokla.musicos2.databinding.FragmentOtherUserBinding;
import com.nicokla.musicos2.navigation.OtherUserFragmentArgs;
import com.nicokla.musicos2.navigation.OtherUserFragmentDirections;

public class OtherUserFragment  extends Fragment
        implements NotSwipableWithoutAuthorSongAdapter.OnSongSelectedListener,
        UserFirestore.CallbackContainerFollowed {
  private NotSwipableWithoutAuthorSongAdapter mAdapter;
  private FragmentOtherUserBinding mBinding;
  private FirebaseFirestore mFirestore;
  private Query mQuery;
  private String userId;
  private boolean isFollowed = false;

  public OtherUserFragment() {
    // Required empty public constructor
  }

  public void actionIfFollowed(UserFirestore user){
    isFollowed = true;
    mBinding.followButton.setText("Unfollow");
  }

  public void actionIfNotFollowed(UserFirestore user){
    isFollowed = false;
    mBinding.followButton.setText("Follow");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mBinding = FragmentOtherUserBinding.inflate(getLayoutInflater());

    FirebaseFirestore.setLoggingEnabled(true);
    mFirestore = FirebaseFirestore.getInstance();
    userId = OtherUserFragmentArgs.fromBundle(getArguments()).getUserId();
    mQuery = mFirestore.collection("songs")
            .whereEqualTo("ownerID", userId);

    GlobalVars.getInstance().user.objectID = userId;
    GlobalVars.getInstance().user.checkIfFollowed(this);

    UserFirestore.CallbackContainerFollowed callbackContainer = this;
    mBinding.followButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        UserFirestore otherUser = GlobalVars.getInstance().user;
        if(isFollowed){
          otherUser.unfollow(callbackContainer);
        }else{
          otherUser.follow(callbackContainer);
        }
      }
    });

    // RecyclerView
    mAdapter = new NotSwipableWithoutAuthorSongAdapter(mQuery, this) {
      @Override
      protected void onDataChanged() {
        // Show/hide content if the query returns empty.
        if (getItemCount() == 0) {
          mBinding.myRecyclerView.setVisibility(View.GONE);
//          mBinding.viewEmpty.setVisibility(View.VISIBLE);
        } else {
          mBinding.myRecyclerView.setVisibility(View.VISIBLE);
//          mBinding.viewEmpty.setVisibility(View.GONE);
        }
      }

      @Override
      protected void onError(FirebaseFirestoreException e) {
        // Show a snackbar on errors
        Log.d("prout:", e.getLocalizedMessage());
        Snackbar.make(mBinding.getRoot(),
                "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
      }
    };

    mBinding.myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mBinding.myRecyclerView.setAdapter(mAdapter);

    return mBinding.getRoot();
  }

  @Override
  public void onStart() {
    super.onStart();

    // Start listening for Firestore updates
    if (mAdapter != null) {
      mAdapter.startListening();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (mAdapter != null) {
      mAdapter.stopListening();
    }
  }

  @Override
  public void onSongSelected(DocumentSnapshot snapshot) {
    SongFirestore song = snapshot.toObject(SongFirestore.class);
    GlobalVars.getInstance().songFirestore = song;
    Navigation.findNavController(getView()).navigate(
            OtherUserFragmentDirections.Companion.actionOtherUserFragmentToPlayerFragment(song.videoID, song.objectID)
    );
  }
}