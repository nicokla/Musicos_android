package nicokla.com.musicos.navigation;

import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nicokla.com.musicos.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SearchUsersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_users_layout, container, false);
    }
}