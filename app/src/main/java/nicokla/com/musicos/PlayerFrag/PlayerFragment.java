package nicokla.com.musicos.PlayerFrag;

import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import java.util.Hashtable;

import nicokla.com.musicos.Firebase.SongFirestore;
import nicokla.com.musicos.Firebase.SongStorage;
import nicokla.com.musicos.Firebase.UserFirestore;
import nicokla.com.musicos.MainAndCo.GlobalVars;
import nicokla.com.musicos.MainAndCo.MainActivity;
//import nicokla.com.musicos.PlayerFrag.LibgdxStuff.GameFragment;
import nicokla.com.musicos.PlayerFrag.LibgdxStuff.GameFragment;
import nicokla.com.musicos.R;
import nicokla.com.musicos.databinding.FragmentOtherUserBinding;
import nicokla.com.musicos.databinding.FragmentPlayerBinding;
import nicokla.com.musicos.navigation.OtherUserFragmentDirections;
//import nicokla.com.musicos.SearchFrag.SearchFragmentDirections;

import com.badlogic.gdx.backends.android.AndroidxFragmentApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment
        implements  AndroidxFragmentApplication.Callbacks,
        View.OnTouchListener, SongStorage.MyCallback,
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

  Hashtable<Integer, Integer> idToLine; // num
  Hashtable<Integer, Integer> idToDouze; // num
  Hashtable<Integer, Integer> idToColumn;
  Hashtable<Integer, Integer> idToIsDiese;
  Hashtable<Integer, Integer>  idToTrenteSix;

  SongStorage songStorage;
  SongFirestore songFirestore;
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
    mBinding.likeButton.setText("Dislike");
  }

   public void actionIfNotLiked(SongFirestore song){
     isLiked = false;
     mBinding.likeButton.setText("Like");
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


   @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

     idToLine=new Hashtable<Integer, Integer>();
    idToColumn=new Hashtable<Integer, Integer>();
    idToDouze=new Hashtable<Integer, Integer>();
    idToIsDiese=new Hashtable<Integer, Integer>();
    idToTrenteSix=new Hashtable<Integer, Integer>();
    activity = (MainActivity)getActivity();

    Point size = new Point();
    activity.getWindowManager().getDefaultDisplay().getSize(size);
    int screenWidth = size.x;
    int screenHeight = size.y;

    // Inflate the layout for this fragment
     mBinding = FragmentPlayerBinding.inflate(getLayoutInflater());
     myView = mBinding.getRoot();
//     myView =  inflater.inflate(R.layout.fragment_player, container, false);
     vidId = PlayerFragmentArgs.fromBundle(getArguments()).getVideoId();
    String songId = PlayerFragmentArgs.fromBundle(getArguments()).getSongId();
    GlobalVars.getInstance().songFirestore.objectID = songId;

    SongStorage.load(songId, this);

    youTubePlayerView = myView.findViewById(R.id.youtube_player_view);
    myConstraintLayout = myView.findViewById(R.id.root);
    myFrameLayout = myView.findViewById(R.id.content_framelayout);
    set = new ConstraintSet();

     Button buttonBack = myView.findViewById(R.id.buttonBack);
     buttonBack.setOnTouchListener(this);

    //playNextVideoButton = view.findViewById(R.id.next_video_button);
    //initYouTubePlayerView();
    getLifecycle().addObserver(youTubePlayerView);

    //1
    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
      @Override
      public void onReady(@NonNull YouTubePlayer youTubePlayer) {
        activity.youtubeController = new YoutubeController(activity, myView, youTubePlayer, youTubePlayerView);
        youTubePlayer.addListener(activity.youtubeController);

        YouTubePlayerUtils.loadOrCueVideo(
                youTubePlayer, getLifecycle(),
                vidId,0f
        );
      }
    });

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
     int [] isDieseInt = new int[]
             {0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0};

//     ConstraintLayout mConstraintLayout = activity.findViewById(R.id.root);
     int i = 0, j = 0, column = 0;

     int myId;
     for (i = 0; i < 3; i++) {
      for (j = 0; j < 12; j++) {
        if (tableau[j]) {
          column = GlobalVars.getInstance().positions[j];
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
          idToIsDiese.put(button.getId(), isDieseInt[j]);
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
                 idToColumn.get(id),
                 idToLine.get(id),
                 idToIsDiese.get(id));
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

 @Override
 public void onCallback(SongStorage songStorage) {
    int i;
    for (i=0; i < songStorage.scale.length; i++){
      if (songStorage.scale[i]){
        Log.d("scale :", String.valueOf(i));
      }
    }
   this.songStorage = songStorage;
 }

 public void actionBackButton(){
//   GlobalVars.getInstance().songFirestore.set(
//           180, // pas bonne duree
//           System.currentTimeMillis()/1000,
//           GlobalVars.getInstance().meFirestore.name,
//           holder.thumbnail.toString(),
//           singleVideo.getId(),
//           "",
//           GlobalVars.getInstance().meFirestore.objectID,
//           singleVideo.getTitle(),
//           newId);
//        GlobalVars.getInstance().songFirestore.save();

//        GlobalVars.getInstance().songStorage = ...
//        GlobalVars.getInstance().songStorage.save();
   Navigation.findNavController(getView()).popBackStack();
 }


}


