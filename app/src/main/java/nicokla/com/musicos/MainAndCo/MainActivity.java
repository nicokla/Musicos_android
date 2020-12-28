package nicokla.com.musicos.MainAndCo;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.realm.Realm;
import jp.kshoji.javax.sound.midi.InvalidMidiDataException;
import jp.kshoji.javax.sound.midi.Receiver;
import jp.kshoji.javax.sound.midi.ShortMessage;
import jp.kshoji.javax.sound.midi.impl.SequencerImpl;
import nicokla.com.musicos.MySongsFrag.MySongsFragment;
import nicokla.com.musicos.PlayerFrag.LibgdxStuff.GameScreen;
import nicokla.com.musicos.PlayerFrag.YoutubeController;
import nicokla.com.musicos.R;
import nicokla.com.musicos.Realm.Parent;
//import nicokla.com.musicos.navigation.HomeFragment;
import nicokla.com.musicos.navigation.SearchFragment;
import nicokla.com.musicos.navigation.SettingsFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

//replace
// android.support.design.widget.BottomNavigationView
//        with
//        com.google.android.material.bottomnavigation.BottomNavigationView

public class MainActivity extends AppCompatActivity {
    //    public MidiSequencer midiSequencer;
    public GameScreen gameScreen;
    public YoutubeController youtubeController;
    public Parent parent;
    public SequencerImpl sequencer;
    public Receiver rcvr;
    private NavController navController;


    public void sendNoteOn(int data1, int data2){
        ShortMessage myMsg = new ShortMessage();
        try {
            myMsg.setMessage(ShortMessage.NOTE_ON, 0, data1, data2);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        rcvr.send(myMsg, 0);
    }

    public void sendNoteOff(int data1){
        ShortMessage myMsg = new ShortMessage();
        try {
            myMsg.setMessage(ShortMessage.NOTE_OFF, 0, data1, 0);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        rcvr.send(myMsg, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sequencer = new SequencerImpl(60, GlobalVars.getInstance().midiPlayer);
        rcvr = sequencer.sequencerThread.midiEventRecordingReceiver;

        Realm realm = Realm.getDefaultInstance();
        parent = realm.where(Parent.class).findFirst();
//        midiSequencer = new MidiSequencer();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener(this));

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.playerFragment) {
                    hideBottomNav();
                } else {
                    showBottomNav();
                }
            }
        });

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new MySongsFragment()).commit(); // HomeFragment()
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navListener(AppCompatActivity acti) {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                onNavDestinationSelected(menuItem, Navigation.findNavController(acti, R.id.nav_host_fragment));

                /*
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.mySongsFragment:
                        selectedFragment = new MySongsFragment(); // HomeFragment()
                        break;
                    case R.id.searchFragment:
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.bookmarksFragment:
                        selectedFragment = new nicokla.com.musicos.navigation.BookmarksFragment();
                        break;
                    case R.id.settingsFragment:
                        selectedFragment = new SettingsFragment();
                        break;
                }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
*/
                return true;
            }
        };
    }


    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
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

    private void showBottomNav()
    {
        BottomNavigationView navBar = this.findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.VISIBLE);
    }

    private void hideBottomNav()
    {
        BottomNavigationView navBar = this.findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.GONE);
    }
}
