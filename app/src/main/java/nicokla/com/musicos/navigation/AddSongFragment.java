package nicokla.com.musicos.navigation;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import nicokla.com.musicos.R;
import nicokla.com.musicos.databinding.FragmentAddSongBinding;
import nicokla.com.musicos.databinding.FragmentMySongsBinding;

public class AddSongFragment extends Fragment {
    private FragmentAddSongBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAddSongBinding.inflate(getLayoutInflater());

//        return inflater.inflate(R.layout.fragment_add_song, container, false);
        mBinding.addSongWithVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mBinding.getRoot()).navigate( // (view).navigate
                        AddSongFragmentDirections.newSong()
                );
            }
        });
        mBinding.addSongWithoutVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(mBinding.getRoot()).navigate( // (view).navigate
                        AddSongFragmentDirections.newSongWithoutVideo()
                );
            }
        });
        return mBinding.getRoot();
    }
}
