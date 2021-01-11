package nicokla.com.musicos.navigation;

import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import nicokla.com.musicos.Firebase.SongFirestore;
import nicokla.com.musicos.MainAndCo.GlobalVars;
import nicokla.com.musicos.MySongsFrag.OnSwipeListener;
import nicokla.com.musicos.MySongsFrag.SongAdapter;
import nicokla.com.musicos.R;
import nicokla.com.musicos.databinding.FavouriteSongsLayoutBinding;
import nicokla.com.musicos.databinding.FragmentMySongsBinding;


public class FavouriteSongsFragment extends Fragment implements NotSwipableSongAdapter.OnSongSelectedListener {
  private NotSwipableSongAdapter mAdapter;
  private FavouriteSongsLayoutBinding mBinding;
  private FirebaseFirestore mFirestore;
  private Query mQuery;

  public FavouriteSongsFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mBinding = FavouriteSongsLayoutBinding.inflate(getLayoutInflater());

    FirebaseFirestore.setLoggingEnabled(true);
    mFirestore = FirebaseFirestore.getInstance();
    mQuery = mFirestore.collection("users")
            .document(GlobalVars.getInstance().me.getUid())
            .collection("likedSongs");

    // RecyclerView
    mAdapter = new NotSwipableSongAdapter(mQuery, this) {
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
    Navigation.findNavController(getView()).navigate(
      HomeFragmentDirections.Companion.seeVideo(song.videoID, song.objectID)
    );
  }
}
