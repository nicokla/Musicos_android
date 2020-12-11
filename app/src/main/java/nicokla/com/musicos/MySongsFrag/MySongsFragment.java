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

import io.realm.Realm;
import io.realm.RealmList;
import nicokla.com.musicos.MySongsFrag.MySongsFragmentDirections;
import nicokla.com.musicos.Realm.DataHelper;
import nicokla.com.musicos.Realm.Parent;
import nicokla.com.musicos.R;
import nicokla.com.musicos.Realm.Song;


/**
 * A simple {@link Fragment} subclass.
 */
public class MySongsFragment extends Fragment {
  private RecyclerView recyclerView;
  private SongAdapter mAdapter;
  private RecyclerView.LayoutManager layoutManager;
  private Realm realm;

  public MySongsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_my_songs, container, false);
    realm = Realm.getDefaultInstance();

    FloatingActionButton floating = view.findViewById(R.id.floatingActionButton);
    floating.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigation.findNavController(view).navigate(
                MySongsFragmentDirections.newSong()
        );
      }
    });

    recyclerView = view.findViewById(R.id.myRecyclerView);
    recyclerView.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(this.getActivity());
    recyclerView.setLayoutManager(layoutManager);
    RealmList<Song> myList =
            realm.where(Parent.class).findFirst().getItemList();
    mAdapter = new SongAdapter(myList);
    recyclerView.setAdapter(mAdapter);
    OnSwipeListener myListener = new OnSwipeListener() {
      @Override
      public void onDelete(int position) {
        super.onDelete(position);
        long id = mAdapter.getItemId(position);

        realm.beginTransaction();
        myList.remove(position);
        realm.commitTransaction();

        DataHelper.deleteItemAsync(realm, id);

        mAdapter.notifyItemRemoved(position);
      }
    };
    mAdapter.mSwipeListener = myListener;
    return view;
  }

}
