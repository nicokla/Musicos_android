package com.nicokla.musicos2.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.UUID;

import com.nicokla.musicos2.Firebase.SongStorage;
import com.nicokla.musicos2.MainAndCo.GlobalVars;
import com.nicokla.musicos2.R;
import com.nicokla.musicos2.databinding.FragmentAddSongWithoutVideoBinding;
import com.nicokla.musicos2.databinding.FragmentPlayerBinding;
import com.nicokla.musicos2.navigation.AddSongWithoutVideoFragmentDirections;

public class AddSongWithoutVideoFragment extends Fragment {
  FragmentAddSongWithoutVideoBinding mBinding;
  public AddSongWithoutVideoFragment() {
    // Required empty public constructor
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mBinding = FragmentAddSongWithoutVideoBinding.inflate(getLayoutInflater());
    View view = mBinding.getRoot();
//    View view = inflater.inflate(R.layout.fragment_add_song_without_video, container, false);
    Button letsGo = (Button)view.findViewById(R.id.button);
    letsGo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String newId =  UUID.randomUUID().toString();
        String durationTxt = mBinding.inputDuration.getText().toString();
        Float duration;
        if(durationTxt != ""){
          duration = Float.parseFloat(durationTxt) * 60;
        }else{
          duration = new Float(180.0);
        }

        // CRUD: Create
        Log.d("newId", newId);
        GlobalVars.getInstance().songFirestore.set(
                duration,
                System.currentTimeMillis()/1000,
                GlobalVars.getInstance().meFirestore.name,
                "",
                "",
                "",
                GlobalVars.getInstance().me.getUid(),
                mBinding.inputTitle.getText().toString(),
                newId);
        GlobalVars.getInstance().songFirestore.save();

        GlobalVars.getInstance().songStorage = new SongStorage();
        GlobalVars.getInstance().songStorage.save(newId);

        Navigation.findNavController(view).navigate(
           AddSongWithoutVideoFragmentDirections.Companion.confirmWithoutVideo("", newId)
        );
      }
    });
    return view;
  }

  @Override
  public void onPause() {
    super.onPause();
    View focusedView = getActivity().getCurrentFocus();
    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (focusedView != null) {
      imm.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }
}
