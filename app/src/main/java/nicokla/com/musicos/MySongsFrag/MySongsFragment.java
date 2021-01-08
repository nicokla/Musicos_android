package nicokla.com.musicos.MySongsFrag;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import io.realm.Realm;
import io.realm.RealmList;
import nicokla.com.musicos.Firebase.SongFirestore;
import nicokla.com.musicos.MainAndCo.GlobalVars;
import nicokla.com.musicos.databinding.ActivityMainBinding;
import nicokla.com.musicos.databinding.FragmentMySongsBinding;
import nicokla.com.musicos.navigation.HomeFragmentDirections;
import nicokla.com.musicos.Realm.DataHelper;
import nicokla.com.musicos.Realm.Parent;
import nicokla.com.musicos.R;
import nicokla.com.musicos.Realm.Song;


public class MySongsFragment extends Fragment implements SongAdapter.OnSongSelectedListener {
  private SongAdapter mAdapter;
  private FragmentMySongsBinding mBinding;
  private FirebaseFirestore mFirestore;
  private Query mQuery;

  public MySongsFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mBinding = FragmentMySongsBinding.inflate(getLayoutInflater());

//    mBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Navigation.findNavController(mBinding.getRoot()).navigate( // (view).navigate
//                HomeFragmentDirections.newSong()
//        );
//      }
//    });

    FirebaseFirestore.setLoggingEnabled(true);
    mFirestore = FirebaseFirestore.getInstance();
    mQuery = mFirestore.collection("songs")
            .whereEqualTo("ownerID", GlobalVars.getInstance().me.getUid());

//    mQuery.get()
//    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//      @Override
//      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//        if (task.isSuccessful()) {
//          for (QueryDocumentSnapshot document : task.getResult()) {
//            Log.d("henlo", document.getId() + " => " + document.getData());
//          }
//        } else {
//          Log.d("frems", "Error getting documents: ", task.getException());
//        }
//      }
//    });

    // RecyclerView
    mAdapter = new SongAdapter(mQuery, this) {
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

    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mBinding.myRecyclerView.setLayoutManager(layoutManager);
    mBinding.myRecyclerView.setAdapter(mAdapter);
    DividerItemDecoration dividerItemDecoration =
            new DividerItemDecoration(mBinding.myRecyclerView.getContext(),
            layoutManager.getOrientation());
    mBinding.myRecyclerView.addItemDecoration(dividerItemDecoration);

    OnSwipeListener myListener = new OnSwipeListener() {
      @Override
      public void onDelete(int position) {
        super.onDelete(position);
//        long id = mAdapter.getItemId(position);
//
//        realm.beginTransaction();
//        myList.remove(position);
//        realm.commitTransaction();
//
//        DataHelper.deleteItemAsync(realm, id);
        mAdapter.notifyItemRemoved(position);
      }
    };
    mAdapter.mSwipeListener = myListener;

    return mBinding.getRoot();
  }

  @Override
  public void onStart() {
    super.onStart();

    // Start sign in if necessary
//    if (shouldStartSignIn()) {
//      startSignIn();
//      return;
//    }

    // Apply filters
//    onFilter(mViewModel.getFilters());

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
    HomeFragmentDirections.SeeVideo action =
            HomeFragmentDirections.seeVideo(song.videoID, song.objectID);
    Navigation.findNavController(getView()).navigate(action);
  }
}
