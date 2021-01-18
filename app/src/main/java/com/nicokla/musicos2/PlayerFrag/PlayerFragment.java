package com.nicokla.musicos2.PlayerFrag;

import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import jp.kshoji.javax.sound.midi.MidiEvent;
import jp.kshoji.javax.sound.midi.MidiMessage;
import jp.kshoji.javax.sound.midi.ShortMessage;
import jp.kshoji.javax.sound.midi.Track;
import com.nicokla.musicos2.Firebase.NoteFirebase;
import com.nicokla.musicos2.Firebase.SongFirestore;
import com.nicokla.musicos2.MainAndCo.GlobalVars;
import com.nicokla.musicos2.MainAndCo.MainActivity;
//import GameFragment;
import com.nicokla.musicos2.PlayerFrag.LibgdxStuff.GameFragment;

import com.nicokla.musicos2.PlayerFrag.PlayerFragmentArgs;
import com.nicokla.musicos2.PlayerFrag.PlayerFragmentDirections;
import com.nicokla.musicos2.R;
import com.nicokla.musicos2.databinding.FragmentOtherUserBinding;
import com.nicokla.musicos2.databinding.FragmentPlayerBinding;
//import com.nicokla.musicos2.SearchFrag.SearchFragmentDirections;

import com.badlogic.gdx.backends.android.AndroidxFragmentApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment
        implements  AndroidxFragmentApplication.Callbacks,
        View.OnTouchListener,
        SongFirestore.CallbackContainerLiked
         {
  MainActivity activity;
  public YouTubePlayerView youTubePlayerView;
//  public YouTubePlayer youTubePlayer;
   int[] guidelinesHorizontalID=new int[4];
  int[] guidelinesVerticalID=new int[8];
  int[] guidelinesHorizontal_demiID=new int[3];
  ConstraintLayout myConstraintLayout;
  FrameLayout myFrameLayout;
  ConstraintSet set;
  GameFragment libgdxFragment;
  View myView;
   String songId;
   Handler mHandler = new Handler();
   Runnable mStatusChecker = new Runnable() {
     @Override
     public void run() {
       try {
         updateSeekBar();
       } finally {
         mHandler.postDelayed(mStatusChecker, 1000);
       }
     }
   };
   void startRepeatingTask() {
     mStatusChecker.run();
   }

   void stopRepeatingTask() {
     mHandler.removeCallbacks(mStatusChecker);
   }

   Hashtable<Integer, Integer> idToLine; // num
  Hashtable<Integer, Integer> idToDouze; // num
  Hashtable<Integer, Integer> idToColumn;
  Hashtable<Integer, Integer> idToIsDiese;
  Hashtable<Integer, Integer>  idToTrenteSix;

//  SongStorage songStorage;
//  SongFirestore songFirestore;
  FragmentPlayerBinding mBinding;
  boolean isLiked = false;

  int screenWidth ;
  int screenHeight ;
  String key="AIzaSyDkwZzpuw-7C2U-ZGUvBRGSUbdjtn0VrKo";
             String vidId = "srH-2pQdKhg";
  //  ImageButton playPauseButton;
  //  SeekBar seekBar;
  //           Boolean isPlaying=true;

  public void actionIfLiked(SongFirestore song){
    isLiked = true;
    mBinding.likeButton.setImageResource(R.drawable.heart_full);
  }

   public void actionIfNotLiked(SongFirestore song){
     isLiked = false;
     mBinding.likeButton.setImageResource(R.drawable.heart);
   }

  public PlayerFragment() {
    // Required empty public constructor
  }

  @Override
  public void exit() {

  }

//  @Override
//  public void onDestroyView() {
//    FragmentManager mFragmentMgr= getFragmentManager();
//    FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();
//    Fragment childFragment =mFragmentMgr.findFragmentByTag("GameFragment");
//    mTransaction.remove(childFragment);
//    mTransaction.commit();
//    super.onDestroyView();
//  }

   void updateSeekBar(){
       mBinding.videoTime.setProgress((int)(activity.sequencer.getMicrosecondPosition()/1000000));
   }

   @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

     idToLine=new Hashtable<Integer, Integer>();
    idToColumn=new Hashtable<Integer, Integer>();
    idToDouze=new Hashtable<Integer, Integer>();
    idToIsDiese=new Hashtable<Integer, Integer>();
    idToTrenteSix=new Hashtable<Integer, Integer>();
    activity = (MainActivity)getActivity();

    activity.initSequencer();

    Point size = new Point();
    activity.getWindowManager().getDefaultDisplay().getSize(size);

    // Inflate the layout for this fragment
     mBinding = FragmentPlayerBinding.inflate(getLayoutInflater());
     myView = mBinding.getRoot();
//     myView =  inflater.inflate(R.layout.fragment_player, container, false);
     vidId = PlayerFragmentArgs.fromBundle(getArguments()).getVideoId();
     songId = PlayerFragmentArgs.fromBundle(getArguments()).getSongId();
    GlobalVars.getInstance().songFirestore.objectID = songId;

    youTubePlayerView = myView.findViewById(R.id.youtube_player_view);
    myConstraintLayout = myView.findViewById(R.id.root);
    myFrameLayout = myView.findViewById(R.id.content_framelayout);
    set = new ConstraintSet();

     mBinding.buttonBack.setOnTouchListener(this);

     GlobalVars.getInstance().songFirestore.checkIfLiked(this);

     SongFirestore.CallbackContainerLiked callbackContainer = this;
     mBinding.likeButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
         SongFirestore song = GlobalVars.getInstance().songFirestore;
         if(isLiked){
           song.dislike(callbackContainer);
         }else{
           song.like(callbackContainer);
         }
       }
     });

     mBinding.settingsButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
         Navigation.findNavController(getView()) .navigate(
               PlayerFragmentDirections.Companion.actionPlayerFragmentToSongSettingsFragment()
         );
       }
     });

     myView.setFocusableInTouchMode(true);
     myView.requestFocus();
     myView.setOnKeyListener( new View.OnKeyListener()
     {
       @Override
       public boolean onKey( View v, int keyCode, KeyEvent event )
       {
         if( keyCode == KeyEvent.KEYCODE_BACK )
         {
           // allows to save data in firebase instead of just going back :
           actionBackButton();
           return true;
         }
         return false;
       }
     } );

     boolean isWithVideo = ! GlobalVars.getInstance().songFirestore.videoID.equals("");

     if ( ! isWithVideo ) {
       startRepeatingTask();
       mBinding.youtubePlayerView.setVisibility(View.INVISIBLE);
     }

     mBinding.playPause.setImageResource(R.drawable.ic_play_arrow_black_48dp);
     getLifecycle().addObserver(youTubePlayerView);
     youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
       @Override
       public void onReady(@NonNull YouTubePlayer youTubePlayer) {
         activity.youtubeController = new YoutubeController(activity, myView, youTubePlayer, youTubePlayerView);
         youTubePlayer.addListener(activity.youtubeController);

         if (isWithVideo){
           YouTubePlayerUtils.loadOrCueVideo(
                   youTubePlayer, getLifecycle(),
                   vidId,0f
           );
           youTubePlayer.pause();
         }
       }
     });


     return myView;
  }

   @Override
   public void onViewCreated(View view, Bundle savedInstanceState) {
     super.onViewCreated(view, savedInstanceState);

     Boolean [] tableau = new Boolean[]
       {true, true, true, true, true,
        true, true, true, true, true, true, true};

     createGuideLines();
     remplirLignes(tableau);
     preparerLibGdxSubView();

   }

   void saveSong(Track track){
     float duration, start;
     int midiNote, velocity;
     float[] debuts = new float[150]; // index is midi pitch
     int[] velocities = new int[150];
     boolean[] seenDebut = new boolean[150];
     Arrays.fill(seenDebut, false);
     float time; // In Millisecond
     List<NoteFirebase> notes = new ArrayList<NoteFirebase>();
     for (int i = 0; i < track.size(); i++) {
       final MidiEvent midiEvent = track.get(i);
       time = midiEvent.getTick() / activity.sequencer.getTicksPerMicrosecond() / 1000000;
       final MidiMessage midiMessage = midiEvent.getMessage();
       if (midiMessage instanceof ShortMessage) {
         final ShortMessage shortMessage = (ShortMessage) midiMessage;
         switch (shortMessage.getCommand()) {
           case ShortMessage.NOTE_ON:// !!!
             midiNote = shortMessage.getData1();
             velocity = shortMessage.getData2();
             seenDebut[midiNote] = true;
             debuts[midiNote] = time;
             velocities[midiNote] = velocity;
             break;
           case ShortMessage.NOTE_OFF:// !!!
             midiNote = shortMessage.getData1();
             if(seenDebut[midiNote]){
               start = debuts[midiNote];
               duration = time - debuts[midiNote];
               notes.add(new NoteFirebase(duration, midiNote, velocities[midiNote], start));
             }
             seenDebut[midiNote] = false;
             break;
         }
       }
     }
     GlobalVars.getInstance().songStorage.notes = new NoteFirebase[notes.size()];
     GlobalVars.getInstance().songStorage.notes = notes.toArray(new NoteFirebase[0]);
     GlobalVars.getInstance().songStorage.save(GlobalVars.getInstance().songFirestore.objectID);
     GlobalVars.getInstance().songFirestore.save(); // to fix duration, actually only useful first time
     // is only useful for ios because ios version uses this number for slider max time.
   }

   @Override
   public void onStop() {
     super.onStop();
     if (GlobalVars.getInstance().songFirestore.videoID.equals("")) {
       stopRepeatingTask();
     }
     activity.sequencer.sequencerThread.refreshPlayingTrack();
     Track track = activity.sequencer.sequencerThread.playingTrack;
     if(track != null) {
       Log.d("ehoh", GlobalVars.getInstance().meFirestore.objectID);
       Log.d("ehoh2", GlobalVars.getInstance().songFirestore.ownerID);
       if (GlobalVars.getInstance().meFirestore.objectID
               .equals(GlobalVars.getInstance().songFirestore.ownerID)) {
         saveSong(track);
       }
     }
     activity.deinitSequencer();
  }

   void preparerLibGdxSubView(){
     int guideLineID = View.generateViewId();
     set.create(guideLineID, ConstraintSet.HORIZONTAL_GUIDELINE);
     set.setGuidelinePercent(guideLineID, 0.33f);
     set.connect(myFrameLayout.getId(),ConstraintSet.TOP,
             guideLineID,ConstraintSet.BOTTOM);
     set.connect(myFrameLayout.getId(),ConstraintSet.BOTTOM,
            guidelinesHorizontalID[0],ConstraintSet.TOP);
     set.connect(myFrameLayout.getId(),ConstraintSet.RIGHT,
             guidelinesVerticalID[7],ConstraintSet.LEFT);
     set.connect(myFrameLayout.getId(),ConstraintSet.LEFT,
             guidelinesVerticalID[0],ConstraintSet.RIGHT);
     set.applyTo(myConstraintLayout);

     libgdxFragment = new GameFragment(myView);
      getChildFragmentManager().beginTransaction()
             .add(R.id.content_framelayout, libgdxFragment)
             .commit();
      //    activity.getSupportFragmentManager().beginTransaction()
      //            .add(R.id.content_framelayout, libgdxFragment)
      //            .commit();

   }

   void connecterHaut(ConstraintSet set, Button button, int i,
                      int margin){
     set.connect(button.getId(),
             ConstraintSet.TOP,
             guidelinesHorizontalID[i],
             ConstraintSet.BOTTOM,
             margin);
   }

   void connecterBas(ConstraintSet set, Button button, int i,
                     int margin){
     set.connect(button.getId(),
             ConstraintSet.BOTTOM,
             guidelinesHorizontalID[i+1],
             ConstraintSet.TOP,
             margin);
   }

   void connecterMilieu(ConstraintSet set, Button button, int i,
                        int margin,
                        int bottomOrUp){ //ConstraintSet.BOTTOM
     set.connect(button.getId(),
             bottomOrUp,
             guidelinesHorizontal_demiID[i],
             ConstraintSet.TOP,
             margin);
   }

   int createGuidelineVertical(ConstraintSet set, float percent){
     int guideLineID = View.generateViewId();
     set.create(guideLineID, ConstraintSet.VERTICAL_GUIDELINE);
     set.setGuidelinePercent(guideLineID, percent);
     return guideLineID;
   }

     int createGuidelineHorizontal(ConstraintSet set, float percent) {
       int guideLineID = View.generateViewId();
       set.create(guideLineID, ConstraintSet.HORIZONTAL_GUIDELINE);
       set.setGuidelinePercent(guideLineID, percent);
       return guideLineID;
     }

     void createGuideLines(){
       int i = 0, j = 0;
       for (i=0; i<=7; i++){
         guidelinesVerticalID[i] =
                 createGuidelineVertical(set, i * 0.142856f);
       }
       for (i=0; i<4; i++){
         guidelinesHorizontalID[i] =
                 createGuidelineHorizontal(set,
                         0.67f + i * 0.11f);
       }
       for (i=0; i<3; i++){
         guidelinesHorizontal_demiID[i] =
                 createGuidelineHorizontal(set,
                         0.725f + i * 0.11f);
       }
       set.applyTo(myConstraintLayout);
     }

   void remplirLignes(Boolean [] tableau) {
    Integer [] info = new Integer[]
        {-1, 2, 1, 4, 3, -1, 7, 6, 9, 8, 11, 10};
    Boolean [] isNotDiese = new Boolean[]
        {true, false, true, false, true,
         true, false, true, false, true, false, true};

//     ConstraintLayout mConstraintLayout = activity.findViewById(R.id.root);
     int i = 0, j = 0, column = 0, isDieseInt = 0;

     int myId;
     for (i = 0; i < 3; i++) {
      for (j = 0; j < 12; j++) {
        if (tableau[j]) {
          column = GlobalVars.getInstance().positions[j];
          isDieseInt = GlobalVars.getInstance().isDieseInts[j];
          Button button = new Button(this.activity);
          myId = View.generateViewId();
          button.setId(myId);
          button.setTag(48 + i * 12 + j);
          GradientDrawable shape = new GradientDrawable();
          shape.setCornerRadius(8);
          if(isNotDiese[j]) {
            shape.setColor(GlobalVars.getInstance().couleurs[2*i]);
          }else {
            shape.setColor(GlobalVars.getInstance().couleurs[2*i+1]);
          }
          button.setBackground(shape);
          button.setAllCaps(false);
          //button.setText(String.valueOf(j));
          button.setText(GlobalVars.getInstance().notesFrench[j]);
          myConstraintLayout.addView(button);
          set.connect(button.getId(),
                  ConstraintSet.LEFT,
                  guidelinesVerticalID[column],
                  ConstraintSet.RIGHT, 3);
          set.connect(button.getId(),
                  ConstraintSet.RIGHT,
                  guidelinesVerticalID[column+1],
                  ConstraintSet.LEFT, 3);
          if(info[j] == -1) {
            connecterHaut(set, button, i, 3);
            connecterBas(set, button, i, 3);
          }
          else {
            if(tableau[info[j]]){
              button.setTextSize(10);
              button.setIncludeFontPadding(false);
              button.setPadding(0,0,0,0);
              if(!isNotDiese[j]){
                connecterHaut(set, button, i, 3);
                connecterMilieu(set, button, i, 3, ConstraintSet.BOTTOM);
              }else{
                connecterBas(set, button, i, 3);
                connecterMilieu(set, button, i, 3, ConstraintSet.TOP);
              }
            } else{
              connecterHaut(set, button, i, 3);
              connecterBas(set, button, i, 3);
            }
          }

          button.setOnTouchListener(this);
          idToLine.put(button.getId(), i);
          idToDouze.put(button.getId(), j);
          idToTrenteSix.put(button.getId(), i * 12 + j);
          idToColumn.put(button.getId(), column);
          idToIsDiese.put(button.getId(), isDieseInt);
        }
      }
    }
    set.applyTo(myConstraintLayout);
  }


 @Override
 public boolean onTouch(View v, MotionEvent event) {
   int action = event.getAction();
   int id = v.getId();
   int tag;
   Integer i = idToLine.get(id);
   if(i!=null){ // one of the buttons stored in the matrix
     //i=(int)i;
     tag=(int) v.getTag();
     switch(action){
       case MotionEvent.ACTION_DOWN:
//         GlobalVars.getInstance().midiPlayer.jouerNote(tag, 80);
//         activity.midiSequencer.addNote(tag);
         GlobalVars.getInstance().midiPlayer.jouerNote(tag, 80);
         activity.sendNoteOn(tag,80);
         libgdxFragment.pianoTiles.gameScreen.addTile(
                 idToColumn.get(id), // GlobalVars.getInstance().positions[(tag - 48) % 12]
                 idToLine.get(id), // (tag - 48) / 12
                 idToIsDiese.get(id)); // GlobalVars.getInstance().isDieseInts[(tag - 48) % 12]
         v.performClick();
         break;
       case MotionEvent.ACTION_UP:
//         GlobalVars.getInstance().midiPlayer.stopNote(tag);
//         activity.midiSequencer.addNoteOff(tag);
         activity.sendNoteOff(tag);
         GlobalVars.getInstance().midiPlayer.stopNote(tag);
         break;
     }
   }else{ // other button (not in matrix)
     switch(action) {
       case MotionEvent.ACTION_DOWN:
         switch (id) {
//           case R.id.playPause:
//             break;
//           case R.id.buttonDown:
////             libgdxFragment.myGdxGame.demoCamera.goesDown = true;
//             break;
           case R.id.buttonBack:
//             libgdxFragment.myGdxGame.demoCamera.goesUp = true;
             actionBackButton();
             break;

           default:
             break;
         }
         v.performClick();
         break;

       case MotionEvent.ACTION_UP:
         switch(id){
//           case R.id.buttonDown:
////             libgdxFragment.myGdxGame.demoCamera.goesDown = false;
//             break;
           case R.id.buttonBack:
//             libgdxFragment.myGdxGame.demoCamera.goesUp = false;
             break;
           default:
             break;
         }
         break;
     }
     return false;
   }
   return false;
 }

 public void actionBackButton(){
//   activity.youtubeController.youTubePlayer.pause();
   // will trigger activity.sequencer.stop();
   // which in turn will trigger refreshPlayingTrack
   // which will put the good notes inside playingTrack

   // wait a bit because this
   Navigation.findNavController(getView()).popBackStack();
   // --> will trigger onStop, which will trigger saving the notes to firebase storage.
 }


}


