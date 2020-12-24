//package jp.kshoji.javax.sound.midi.impl;
//
////https://github.com/kshoji/javax.sound.midi-for-Android/blob/develop/javax.sound.midi/src/jp/kshoji/javax/sound/midi/impl/SequencerImpl.java
////https://apkpure.com/audio-evolution-mobile-studio-trial/com.extreamsd.aemobiledemo
//
//import android.util.Log;
//import android.util.SparseArray;
//import android.util.SparseBooleanArray;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.ConcurrentModificationException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import jp.kshoji.javax.sound.midi.InvalidMidiDataException;
//import jp.kshoji.javax.sound.midi.MidiEvent;
//import jp.kshoji.javax.sound.midi.MidiMessage;
//import jp.kshoji.javax.sound.midi.MidiUnavailableException;
//import jp.kshoji.javax.sound.midi.Receiver;
//import jp.kshoji.javax.sound.midi.Sequence;
//import jp.kshoji.javax.sound.midi.Sequencer;
//import jp.kshoji.javax.sound.midi.ShortMessage;
//import jp.kshoji.javax.sound.midi.Track;
//import jp.kshoji.javax.sound.midi.Track.TrackUtils;
//import jp.kshoji.javax.sound.midi.io.StandardMidiFileReader;
//
//import nicokla.com.myapplication.MidiPlayer;
//
//public class SequencerImpl implements Sequencer {
//
//  public MidiPlayer midiPlayer;
//
//  @Nullable
//  public SequencerThread sequencerThread = null;
//  private Sequence sequence = null;
//  private volatile boolean isOpen = false;
//
//  private volatile boolean isRunning = false;
//  private volatile boolean isRecording = false;
//  private final Map<Track, Set<Integer>> recordEnable = new HashMap<Track, Set<Integer>>();
//
//    @Override
//    public long getTickPosition() {
//        if (sequencerThread == null) {
//            return 0;
//        }
//        return sequencerThread.getTickPosition();
//    }
//
//    @Override
//    public void setTickPosition(final long tick) {
//        if (sequencerThread != null) {
//            sequencerThread.setTickPosition(tick);
//        }
//    }
//
//  public class SequencerThread extends Thread {
//    public long tickPosition = 0;
//
//    // recording
//    public long recordingStartedTime;
//    public long recordStartedTick;
//    public Track recordingTrack;
//
//    // playing
//    public long tickPositionSetTime;
//    public long runningStoppedTime;
//    public Receiver midiEventRecordingReceiver;
//
//    public SequencerThread() {
////            refreshPlayingTrack();
//      midiEventRecordingReceiver = new Receiver() {
//
//        public void send(@NonNull final MidiMessage message, final long timeStamp) {
//          if (isRecording) {
//            // !!!
//            recordingTrack.add(new MidiEvent(message, (long) (recordStartedTick + ((System.currentTimeMillis() - recordingStartedTime)))));
//          }
////                    Log.d("message", message.toString());
////                    Log.d("time", String.valueOf((long) (recordStartedTick + ((System.currentTimeMillis() - recordingStartedTime) ))));
////          fireEventListeners(message);
//        }
//
//
//        public void close() {
//          // do nothing
//        }
//      };
//    }
//
//
//    private boolean isRecordable(@Nullable final Collection<Integer> recordEnableChannels, @NonNull final MidiEvent midiEvent) {
//      if (recordEnableChannels == null) {
//        return false;
//      }
//
//      if (recordEnableChannels.contains(-1)) {
//        return true;
//      }
//
//      final int status = midiEvent.getMessage().getStatus();
//      switch (status & ShortMessage.MASK_EVENT) {
//        // channel messages
//        case ShortMessage.NOTE_OFF:
//        case ShortMessage.NOTE_ON:
//        case ShortMessage.POLY_PRESSURE:
//        case ShortMessage.CONTROL_CHANGE:
//        case ShortMessage.PROGRAM_CHANGE:
//        case ShortMessage.CHANNEL_PRESSURE:
//        case ShortMessage.PITCH_BEND:
//          // recorded Track and channel
//          return recordEnableChannels.contains(status & ShortMessage.MASK_CHANNEL);
//        // exclusive messages
//        default:
//          return true;
//      }
//    }
//
//    public long getTickPosition() {
//      if (isRunning) {
//        // running
//        return (long) (tickPosition + ((System.currentTimeMillis() - tickPositionSetTime) ));
//      } else {
//        // stopping
//        return (long) (tickPosition + ((runningStoppedTime - tickPositionSetTime) ));
//      }
//    }
//
//    public void setTickPosition(long tick) {
//      tickPosition = tick;
//      if (isRunning) {
//        tickPositionSetTime = System.currentTimeMillis();
//      }
//    }
//
//    public void startRecording() {
//      if (isRecording) {
//        // already recording
//        return;
//      }
//
//      recordingTrack = sequence.createTrack();
//      recordingStartedTime = System.currentTimeMillis();
//      recordStartedTick = getTickPosition();
//      isRecording = true;
//    }
//
//    public void stopRecording() {
//      if (isRecording == false) {
//        // already stopped
//        return;
//      }
//
//      final long recordEndedTime = System.currentTimeMillis();
//      isRecording = false;
//
//      final Collection<MidiEvent> eventToRemoval = new HashSet<MidiEvent>();
//      for (final Track track : sequence.getTracks()) {
//        final Set<Integer> recordEnableChannels = recordEnable.get(track);
//
//        // remove events while recorded time
//        eventToRemoval.clear();
//        for (int trackIndex = 0; trackIndex < track.size(); trackIndex++) {
//          final MidiEvent midiEvent = track.get(trackIndex);
//          if (isRecordable(recordEnableChannels, midiEvent) &&
//                  midiEvent.time >= recordingStartedTime && midiEvent.getTime() <= recordEndedTime) { // recorded time
//            eventToRemoval.add(midiEvent);
//          }
//        }
//
//        for (final MidiEvent event : eventToRemoval) {
//          track.remove(event);
//        }
//
//        // add recorded events
//        for (int eventIndex = 0; eventIndex < recordingTrack.size(); eventIndex++) {
//          if (isRecordable(recordEnableChannels, recordingTrack.get(eventIndex))) {
//            track.add(recordingTrack.get(eventIndex));
//          }
//        }
//
//        TrackUtils.sortEvents(track);
//      }
//    }
//
//    public void startPlaying() {
//      if (isRunning) {
//        // already playing
//        return;
//      }
//
//      tickPosition = 0;
//      tickPositionSetTime = System.currentTimeMillis();
//      isRunning = true;
//
//      synchronized (this) {
//        notifyAll();
//      }
//    }
//
//    public void stopPlaying() {
//      if (isRunning == false) {
//        // already stopping
//        synchronized (this) {
//          notifyAll();
//        }
//        interrupt();
//        return;
//      }
//
//      isRunning = false;
//      runningStoppedTime = System.currentTimeMillis();
//
//      // force stop sleeping
//      synchronized (this) {
//        notifyAll();
//      }
//      interrupt();
//    }
//
//
//    void playMidiMessage(MidiMessage midiMessage){
//      if (midiMessage instanceof ShortMessage) {
//        // process control change / program change messages
//        final ShortMessage shortMessage = (ShortMessage) midiMessage;
//        switch (shortMessage.getCommand()) {
//          case ShortMessage.NOTE_ON:// !!!
//            midiPlayer.jouerNote(shortMessage.getData1(), shortMessage.getData2());
//            break;
//          case ShortMessage.NOTE_OFF:// !!!
//            midiPlayer.stopNote(shortMessage.getData1());
//            break;
//          default:
//            break;
//        }
//      }
//    }
//
//
//    public void run() {
//      super.run();
//
//      // playing
//      while (isOpen) {
//        synchronized (this) {
//          try {
//            // wait for being notified
//            while (!isRunning && isOpen) {
//              wait();
//            }
//          } catch (final InterruptedException ignored) {
//            // ignore exception
//          }
//        }
//
//        if (recordingTrack == null) {
//          continue;
//        }
//
//        // main loop
//        for (int i = 0; i < recordingTrack.size(); i++) {
//          final MidiEvent midiEvent = recordingTrack.get(i);
//          final MidiMessage midiMessage = midiEvent.getMessage();
//
//          if (midiEvent.time < tickPosition) {
//            continue;
//          }
//
//          try {
//            final long sleepLength = (long) ((midiEvent.time - tickPosition));
////((1.0f / getTicksPerMicrosecond())  / 1000f *
//            if (sleepLength > 0) {
//              sleep(sleepLength);
//            }
//            tickPosition = midiEvent.time;
//            tickPositionSetTime = System.currentTimeMillis();
//          } catch (final InterruptedException ignored) {
//            // ignore exception
//          }
//
//          if (isRunning == false) {
//            break;
//          }
//
//          // send MIDI events
//          playMidiMessage(midiMessage);
//
////          fireEventListeners(midiMessage);
//        }
//
//        // loop end
//        isRunning = false;
//        runningStoppedTime = System.currentTimeMillis();
//      }
//    }
//
//
//  }
//
//  public SequencerImpl() {
//  }
//
//
//
//  public void open() throws MidiUnavailableException {
//    // open devices
//
//    if (sequencerThread == null) {
//      sequencerThread = new SequencerThread();
//      sequencerThread.setName("MidiSequencer_" + sequencerThread.getId());
//      try {
//        sequencerThread.start();
//      } catch (final IllegalThreadStateException ignored) {
//        // maybe already started
//      }
//    }
//
//    isOpen = true;
//    synchronized (sequencerThread) {
//      sequencerThread.notifyAll();
//    }
//  }
//
//  public void close() {
//    if (sequencerThread != null) {
//      sequencerThread.stopPlaying();
//      sequencerThread.stopRecording();
//      isOpen = false;
//      sequencerThread = null;
//    }
//  }
//
//  public boolean isOpen() {
//    return isOpen;
//  }
//
//
//
//  public Sequence getSequence() {
//    return sequence;
//  }
//
//
//  public void setSequence(@NonNull final InputStream stream) throws IOException, InvalidMidiDataException {
//    setSequence(new StandardMidiFileReader().getSequence(stream));
//  }
//
//
//  public void setSequence(@Nullable final Sequence sequence) throws InvalidMidiDataException {
//    this.sequence = sequence;
//
////        if (sequencerThread != null && sequence != null) {
////            sequencerThread.needRefreshPlayingTrack = true;
////        }
//  }
//
//
//
//  public void startRecording() {
//    // start playing AND recording
//    if (sequencerThread != null) {
//      sequencerThread.startRecording();
//      sequencerThread.startPlaying();
//    }
//  }
//
//
//  public boolean isRecording() {
//    return isRecording;
//  }
//
//
//  public void stopRecording() {
//    // stop recording
//    if (sequencerThread != null) {
//      sequencerThread.stopRecording();
//    }
//  }
//
//
//  public void start() {
//    // start playing
//    if (sequencerThread != null) {
//      sequencerThread.startPlaying();
//    }
//  }
//
//
//  public boolean isRunning() {
//    return isRunning;
//  }
//
//
//  public void stop() {
//    // stop playing AND recording
//    if (sequencerThread != null) {
//      sequencerThread.stopRecording();
//      sequencerThread.stopPlaying();
//    }
//  }
//}
