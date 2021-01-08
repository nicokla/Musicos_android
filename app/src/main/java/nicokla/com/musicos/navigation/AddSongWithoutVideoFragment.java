package nicokla.com.musicos.navigation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.UUID;

import nicokla.com.musicos.Login.SignInFragDirections;
import nicokla.com.musicos.R;

public class AddSongWithoutVideoFragment extends Fragment {
  public AddSongWithoutVideoFragment() {
    // Required empty public constructor
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_add_song_without_video, container, false);
    Button letsGo = (Button)view.findViewById(R.id.button);
    letsGo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO : Create song without video in firebase.
        Navigation.findNavController(view).navigate(
           AddSongWithoutVideoFragmentDirections.confirmWithoutVideo("",  UUID.randomUUID().toString())
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
