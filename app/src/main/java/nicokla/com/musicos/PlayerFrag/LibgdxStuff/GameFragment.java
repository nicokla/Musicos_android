package nicokla.com.musicos.PlayerFrag.LibgdxStuff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidxFragmentApplication;

import nicokla.com.musicos.MainAndCo.MainActivity;

public class GameFragment extends AndroidxFragmentApplication {

//    public MyGdxGame myGdxGame;
    View myView;
    public PianoTiles pianoTiles;
    public GameFragment(View myView){
        this.myView = myView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        pianoTiles = new PianoTiles((MainActivity) getActivity());
        return initializeForView(pianoTiles);
    }
}

