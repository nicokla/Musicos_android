package nicokla.com.musicos.PlayerFrag;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import nicokla.com.musicos.MainAndCo.GlobalVars;
import nicokla.com.musicos.MainAndCo.MainActivity;
import nicokla.com.musicos.PlayerFrag.MidiStuff.MidiSequencer;
import nicokla.com.musicos.R;

public class YoutubeController extends AbstractYouTubePlayerListener {
  private View playerUi;

  public MainActivity mainActivity;
  public YouTubePlayer youTubePlayer;
  public YouTubePlayerView youTubePlayerView;
  public YouTubePlayerTracker playerTracker;
  public ImageButton playPauseButton;
  public SeekBar seekBar;
  public float duration;

  public YoutubeController(MainActivity mainActivity, View customPlayerUi, YouTubePlayer youTubePlayer, YouTubePlayerView youTubePlayerView) {
    this.playerUi = customPlayerUi;
    this.mainActivity = mainActivity;
    this.youTubePlayer = youTubePlayer;
    this.youTubePlayerView = youTubePlayerView;

    playerTracker = new YouTubePlayerTracker();
    youTubePlayer.addListener(playerTracker);

    initViews(customPlayerUi);
  }

  private void initViews(View playerUi) {
    playPauseButton = playerUi.findViewById(R.id.playPause);
    seekBar = playerUi.findViewById(R.id.video_time);

    playPauseButton.setOnClickListener( (view) -> {
      if (GlobalVars.getInstance().songFirestore.videoID != ""){ // si avec video
        if (playerTracker.getState() == PlayerConstants.PlayerState.PLAYING) {
          youTubePlayer.pause();
        } else {
          youTubePlayer.play();
        }
      } else{ // si sans video
        if( mainActivity.sequencer.isRecording() ){
          mainActivity.sequencer.stop();
          playPauseButton.setImageResource(R.drawable.ic_play_arrow_black_48dp);
        } else {
          mainActivity.sequencer.startRecording();
          playPauseButton.setImageResource(R.drawable.ic_pause_black_48dp);
        }
      }
    });

    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        if (GlobalVars.getInstance().songFirestore.videoID != ""){
          youTubePlayer.pause();
        }
//        mainActivity.sequencer.stop();
      }
      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
//        mainActivity.sequencer.stop();
      }
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
        if (GlobalVars.getInstance().songFirestore.videoID != ""){
          if(fromUser) {
            youTubePlayer.seekTo((float) progress);
            mainActivity.sequencer.setMicrosecondPosition(((long) progress) * 1000000);
          }
        } else {
          if (fromUser){
            mainActivity.sequencer.setMicrosecondPosition(((long) progress) * 1000000);
          }
        }
      }
    });

  }

  @Override
  public void onApiChange(YouTubePlayer youTubePlayer) {
    super.onApiChange(youTubePlayer);
  }

  @Override
  public void onCurrentSecond(YouTubePlayer youTubePlayer, float second) {
    super.onCurrentSecond(youTubePlayer, second);
    seekBar.setProgress((int)second);
  }

  @Override
  public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {
    super.onError(youTubePlayer, error);
  }

  @Override
  public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {
    super.onPlaybackQualityChange(youTubePlayer, playbackQuality);
  }

  @Override
  public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {
    super.onPlaybackRateChange(youTubePlayer, playbackRate);
  }

  @Override
  public void onReady(YouTubePlayer youTubePlayer) {
    super.onReady(youTubePlayer);
//    youTubePlayer.pause();
  }

  @Override
  public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {
    if(state == PlayerConstants.PlayerState.PLAYING){
//      mainActivity.midiSequencer.play();
      mainActivity.sequencer.startRecording();
//        mainActivity.gameScreen.defile = true;
      playPauseButton.setImageResource(R.drawable.ic_pause_black_48dp);
    }else if (state == PlayerConstants.PlayerState.PAUSED){
      mainActivity.sequencer.stop();
//      mainActivity.midiSequencer.pause();
//        mainActivity.gameScreen.defile = false;
      playPauseButton.setImageResource(R.drawable.ic_play_arrow_black_48dp);
    }
    super.onStateChange(youTubePlayer, state);
  }

  @Override
  public void onVideoDuration(YouTubePlayer youTubePlayer, float duration) {
    super.onVideoDuration(youTubePlayer, duration);
    this.duration = duration;
    seekBar.setMax((int)duration);
    GlobalVars.getInstance().songFirestore.duration = duration;
  }

  @Override
  public void onVideoId(YouTubePlayer youTubePlayer, String videoId) {
    super.onVideoId(youTubePlayer, videoId);
  }

  @Override
  public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float loadedFraction) {
    super.onVideoLoadedFraction(youTubePlayer, loadedFraction);
  }
}
