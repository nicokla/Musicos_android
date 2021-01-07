package nicokla.com.musicos.MySongsFrag;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import io.realm.Realm;
import io.realm.RealmList;
import nicokla.com.musicos.MainAndCo.GlobalVars;
import nicokla.com.musicos.databinding.ActivityMainBinding;
import nicokla.com.musicos.databinding.FragmentMySongsBinding;
import nicokla.com.musicos.navigation.HomeFragmentDirections;
import nicokla.com.musicos.Realm.DataHelper;
import nicokla.com.musicos.Realm.Parent;
import nicokla.com.musicos.R;
import nicokla.com.musicos.Realm.Song;


/**
 * A simple {@link Fragment} subclass.
 */
//
public class MySongsFragment extends Fragment implements SongAdapter.OnSongSelectedListener {
//  private RecyclerView recyclerView;
  private SongAdapter mAdapter;
  private RecyclerView.LayoutManager layoutManager;
//  private Realm realm;
  private FragmentMySongsBinding mBinding;
  private FirebaseFirestore mFirestore;
  private Query mQuery;

  public MySongsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
//    View view = inflater.inflate(R.layout.fragment_my_songs, container, false);
//    realm = Realm.getDefaultInstance();
    mBinding = FragmentMySongsBinding.inflate(getLayoutInflater());
//    setContentView(mBinding.getRoot());

//    FloatingActionButton floating = view.findViewById(R.id.floatingActionButton);
    mBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigation.findNavController(mBinding.getRoot()).navigate( // (view).navigate
                HomeFragmentDirections.newSong()
        );
      }
    });

//    recyclerView = view.findViewById(R.id.myRecyclerView);
    mBinding.myRecyclerView.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(this.getActivity());
    mBinding.myRecyclerView.setLayoutManager(layoutManager);
//    RealmList<Song> myList =
//            realm.where(Parent.class).findFirst().getItemList();
//    mAdapter = new SongAdapterOld(myList);
    // Enable Firestore logging
    FirebaseFirestore.setLoggingEnabled(true);

    // Firestore
    mFirestore = FirebaseFirestore.getInstance();

    // Get ${LIMIT} restaurants
    mQuery = mFirestore.collection("songs")
            .whereEqualTo("ownerID", GlobalVars.getInstance().me.getUid());
//            .document(GlobalVars.getInstance().me.getUid())
//            .orderBy("avgRating", Query.Direction.DESCENDING);
//            .limit(10);

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
        Snackbar.make(mBinding.getRoot(),
                "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
      }
    };

    mBinding.myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mBinding.myRecyclerView.setAdapter(mAdapter);

//    recyclerView.setAdapter(mAdapter);
//    OnSwipeListener myListener = new OnSwipeListener() {
//      @Override
//      public void onDelete(int position) {
//        super.onDelete(position);
//        long id = mAdapter.getItemId(position);
//
//        realm.beginTransaction();
//        myList.remove(position);
//        realm.commitTransaction();
//
//        DataHelper.deleteItemAsync(realm, id);
//
//        mAdapter.notifyItemRemoved(position);
//      }
//    };
//    mAdapter.mSwipeListener = myListener;

    return mBinding.getRoot(); // view
  }

  @Override
  public void onSongSelected(DocumentSnapshot snapshot) {
    Song song = snapshot.toObject(Song.class);
    HomeFragmentDirections.SeeVideo action =
            HomeFragmentDirections.seeVideo(song.getVideoId());
    Navigation.findNavController(mBinding.getRoot()).navigate(action);
  }
}
