//package nicokla.com.musicos.navigation;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
////import android.support.annotation.NonNull;
////import android.support.annotation.Nullable;
////import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.NavigationUI;
//
//import java.io.IOException;
//
//import io.realm.Realm;
//import jp.kshoji.javax.sound.midi.InvalidMidiDataException;
//import jp.kshoji.javax.sound.midi.Receiver;
//import jp.kshoji.javax.sound.midi.ShortMessage;
//import jp.kshoji.javax.sound.midi.impl.SequencerImpl;
//import nicokla.com.musicos.MainAndCo.GlobalVars;
//import nicokla.com.musicos.PlayerFrag.LibgdxStuff.GameScreen;
//import nicokla.com.musicos.PlayerFrag.YoutubeController;
//import nicokla.com.musicos.R;
//import nicokla.com.musicos.Realm.Parent;
//
//public class HomeFragment extends Fragment {
////    //    public MidiSequencer midiSequencer;
////    public GameScreen gameScreen;
////    public YoutubeController youtubeController;
////    public Parent parent;
////    public SequencerImpl sequencer;
////    public Receiver rcvr;
////
////    public void sendNoteOn(int data1, int data2){
////        ShortMessage myMsg = new ShortMessage();
////        try {
////            myMsg.setMessage(ShortMessage.NOTE_ON, 0, data1, data2);
////        } catch (InvalidMidiDataException e) {
////            e.printStackTrace();
////        }
////        rcvr.send(myMsg, 0);
////    }
////
////    public void sendNoteOff(int data1){
////        ShortMessage myMsg = new ShortMessage();
////        try {
////            myMsg.setMessage(ShortMessage.NOTE_OFF, 0, data1, 0);
////        } catch (InvalidMidiDataException e) {
////            e.printStackTrace();
////        }
////        rcvr.send(myMsg, 0);
////    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_my_songs, container, false);
//////        setContentView(R.layout.fragment_home);
////        sequencer = new SequencerImpl(60, GlobalVars.getInstance().midiPlayer);
////        rcvr = sequencer.sequencerThread.midiEventRecordingReceiver;
////
////        Realm realm = Realm.getDefaultInstance();
////        parent = realm.where(Parent.class).findFirst();
//////        midiSequencer = new MidiSequencer();
////
////        NavigationUI.setupActionBarWithNavController((AppCompatActivity) this.getActivity(),
////                Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment));
//
//        return view;
//    }
//
//}
