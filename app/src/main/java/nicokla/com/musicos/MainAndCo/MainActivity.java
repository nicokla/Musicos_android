package nicokla.com.musicos.MainAndCo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import io.realm.Realm;
import nicokla.com.musicos.PlayerFrag.LibgdxStuff.GameScreen;
import nicokla.com.musicos.PlayerFrag.MidiStuff.MidiSequencer;
import nicokla.com.musicos.PlayerFrag.YoutubeController;
import nicokla.com.musicos.R;
import nicokla.com.musicos.Realm.Parent;


public class MainActivity extends AppCompatActivity {
    int alloQuoi;
    public MidiSequencer midiSequencer;
    public GameScreen gameScreen;
    public YoutubeController youtubeController;
    public Parent parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm realm = Realm.getDefaultInstance();
        parent = realm.where(Parent.class).findFirst();
        alloQuoi = GlobalVars.getInstance().couleurs[0];
        midiSequencer = new MidiSequencer();

        NavigationUI.setupActionBarWithNavController(this,
            Navigation.findNavController(this, R.id.nav_host_fragment));

        //songRepository = SongRepository.getInstance();
        //songRepository.getRealm();

    }

    @Override
    public boolean onSupportNavigateUp() {
        return
            Navigation.findNavController(this,
                R.id.nav_host_fragment).navigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalVars.getInstance().midiPlayer.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        GlobalVars.getInstance().midiPlayer.pause();
    }

}
