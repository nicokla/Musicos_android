package com.nicokla.musicos2.MainAndCo;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nicokla.musicos2.Firebase.NoteFirebase;
import com.nicokla.musicos2.Firebase.SongStorage;
import com.nicokla.musicos2.PlayerFrag.LibgdxStuff.GameScreen;
import com.nicokla.musicos2.PlayerFrag.YoutubeController;
import com.nicokla.musicos2.Realm.Parent;

//import io.realm.Realm;
import jp.kshoji.javax.sound.midi.InvalidMidiDataException;
import jp.kshoji.javax.sound.midi.MidiEvent;
import jp.kshoji.javax.sound.midi.Receiver;
import jp.kshoji.javax.sound.midi.ShortMessage;
import jp.kshoji.javax.sound.midi.impl.SequencerImpl;
import com.nicokla.musicos2.R;
//import HomeFragment;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;
import androidx.navigation.ui.AppBarConfiguration;

//replace
// android.support.design.widget.BottomNavigationView
//        with
//        com.google.android.material.bottomnavigation.BottomNavigationView

public class MainActivity extends AppCompatActivity implements
        SongStorage.MyCallback {
    //    public MidiSequencer midiSequencer;
    public GameScreen gameScreen;
    public YoutubeController youtubeController;
    public Parent parent;
    public SequencerImpl sequencer;
    public Receiver rcvr;
    private NavController navController;

    void populateCanvas(){
        SongStorage songStorage = GlobalVars.getInstance().songStorage;
        int i;
        int midiNote;

        for (i=0; i < songStorage.notes.length; i++) {
            NoteFirebase note = songStorage.notes[i];
            midiNote = note.midiNote;
            gameScreen.addTileAtTime(
                    GlobalVars.getInstance().positions[(midiNote - 48) % 12],
                    (midiNote - 48) / 12,
                    GlobalVars.getInstance().isDieseInts[(midiNote - 48) % 12],
                    note.start);
        }
    }


    void populateMidi() throws InvalidMidiDataException {
        SongStorage songStorage = GlobalVars.getInstance().songStorage;
        int i;
        float start, end;
        int velocity, midiNote;
        MidiEvent event;
        sequencer.sequencerThread.recordingTrack = sequencer.sequence.createTrack();
        for (i=0; i < songStorage.notes.length; i++){
            NoteFirebase note = songStorage.notes[i];
            start = note.start;
            end = note.start + note.duration;
            midiNote = note.midiNote;
            velocity = note.velocity;

            sequencer.sequencerThread.recordingTrack.add(
                    new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, midiNote, velocity),
                             (long) (start * 1000000 * sequencer.getTicksPerMicrosecond()))
            );

            sequencer.sequencerThread.recordingTrack.add(
                    new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, midiNote, 0),
                            (long) (end * 1000000 * sequencer.getTicksPerMicrosecond()))
            );

        }
        Log.i("cTropCool:", "oupas.");
        sequencer.sequencerThread.refreshPlayingTrack();
    }

    @Override
    public void onCallback(SongStorage songStorage) {
        GlobalVars.getInstance().songStorage = songStorage;
        try {
            populateMidi();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        populateCanvas();
    }


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

    public void initSequencer(){
        sequencer = new SequencerImpl(60, GlobalVars.getInstance().midiPlayer);
        rcvr = sequencer.sequencerThread.midiEventRecordingReceiver;
    }

    public void deinitSequencer(){
        sequencer.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Realm realm = Realm.getDefaultInstance();
//        parent = realm.where(Parent.class).findFirst();
//        midiSequencer = new MidiSequencer();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener(this));

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.searchFragment,
                R.id.addSongFragment, R.id.settingsFragment,
                R.id.signInFrag, R.id.signUpFrag)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.playerFragment ||
                        destination.getId() == R.id.signUpFrag ||
                         destination.getId() == R.id.signInFrag) {
                    hideBottomNav();
                } else {
                    showBottomNav();
                }
                if(destination.getId() == R.id.playerFragment) {
                    hideActionBar();
                } else {
                    showActionBar();
                }
            }
        });

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new MySongsFragment()).commit(); // HomeFragment()
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navListener(AppCompatActivity acti) {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onNavDestinationSelected(item, Navigation.findNavController(acti, R.id.nav_host_fragment));
                return false;
            }
        };
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                /*
//                Fragment selectedFragment = null;
//
//                switch (menuItem.getItemId()) {
//                    case R.id.mySongsFragment:
//                        selectedFragment = new MySongsFragment(); // HomeFragment()
//                        break;
//                    case R.id.searchFragment:
//                        selectedFragment = new SearchFragment();
//                        break;
//                    case R.id.bookmarksFragment:
//                        selectedFragment = new com.nicokla.musicos2.navigation.BookmarksFragment();
//                        break;
//                    case R.id.settingsFragment:
//                        selectedFragment = new SettingsFragment();
//                        break;
//                }
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    selectedFragment).commit();
//*/
//                return true;
//            }
//        };
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

    private void showActionBar()
    {
        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.show();
    }

    private void hideActionBar()
    {
        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

}
