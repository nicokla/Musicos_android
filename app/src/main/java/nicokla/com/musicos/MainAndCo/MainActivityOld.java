//package nicokla.com.musicos.MainAndCo;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.NavigationUI;
//
//import android.os.Bundle;
//
//import io.realm.Realm;
//import jp.kshoji.javax.sound.midi.InvalidMidiDataException;
//import jp.kshoji.javax.sound.midi.Receiver;
//import jp.kshoji.javax.sound.midi.ShortMessage;
//import jp.kshoji.javax.sound.midi.impl.SequencerImpl;
//import nicokla.com.musicos.PlayerFrag.LibgdxStuff.GameScreen;
//import nicokla.com.musicos.PlayerFrag.YoutubeController;
//import nicokla.com.musicos.R;
//import nicokla.com.musicos.Realm.Parent;
//
//
//public class MainActivityOld extends AppCompatActivity {
////    public MidiSequencer midiSequencer;
//    public GameScreen gameScreen;
//    public YoutubeController youtubeController;
//    public Parent parent;
//    public SequencerImpl sequencer;
//    public Receiver rcvr;
//
//
//    public void sendNoteOn(int data1, int data2){
//        ShortMessage myMsg = new ShortMessage();
//        try {
//            myMsg.setMessage(ShortMessage.NOTE_ON, 0, data1, data2);
//        } catch (InvalidMidiDataException e) {
//            e.printStackTrace();
//        }
//        rcvr.send(myMsg, 0);
//    }
//
//    public void sendNoteOff(int data1){
//        ShortMessage myMsg = new ShortMessage();
//        try {
//            myMsg.setMessage(ShortMessage.NOTE_OFF, 0, data1, 0);
//        } catch (InvalidMidiDataException e) {
//            e.printStackTrace();
//        }
//        rcvr.send(myMsg, 0);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home);
//        sequencer = new SequencerImpl(60, GlobalVars.getInstance().midiPlayer);
//        rcvr = sequencer.sequencerThread.midiEventRecordingReceiver;
//
//        Realm realm = Realm.getDefaultInstance();
//        parent = realm.where(Parent.class).findFirst();
////        midiSequencer = new MidiSequencer();
//
//        NavigationUI.setupActionBarWithNavController(this,
//            Navigation.findNavController(this, R.id.nav_host_fragment));
//
//        //songRepository = SongRepository.getInstance();
//        //songRepository.getRealm();
//
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        return
//            Navigation.findNavController(this,
//                R.id.nav_host_fragment).navigateUp();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        GlobalVars.getInstance().midiPlayer.resume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        GlobalVars.getInstance().midiPlayer.pause();
//    }
//
//}
