package com.nicokla.musicos2.PlayerFrag.MidiStuff;

//import com.leff.midi.MidiFile;
//import com.leff.midi.MidiTrack;
//import com.leff.midi.event.MidiEvent;
//import com.leff.midi.event.NoteOff;
//import com.leff.midi.event.NoteOn;
//import com.leff.midi.util.MidiProcessor;

import com.leff.midi.MidiTrack;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;

import com.nicokla.musicos2.MainAndCo.GlobalVars;

public class MidiSequencer {
//  SequencerImpl sequencer;
//  Sequence sequence;
//  Receiver rcvr;
//  public MidiPlayer midiPlayer;

  long debuts[] = new long[128];
  long timeNowRel = 0;
  long timeLastStartAbs = 0;
  long timeNowAbs = 0;
  Double timeNowAbsDouble;
  long timeLastStartRel = 0;
  MidiTrack noteTrack;
  int channel = 0, velocity = 100;
  public Boolean isPlaying = false;
//  MidiFile midi;
//  MidiProcessor processor;

  public MidiSequencer(){
    for(int i = 0; i < debuts.length; i++) {
      debuts[i] = 0;
    }
    noteTrack = new MidiTrack();
//    ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
//    tracks.add(noteTrack);
//    midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
//    processor = new MidiProcessor(midi);
//    MidiSoundPlayer ep = new MidiSoundPlayer();
//    processor.registerEventListener(ep, NoteOn.class);
//    processor.registerEventListener(ep, NoteOff.class);
    setTime(0);
  }

  public void setTime(long time){
//    processor.setTime(time);
    timeLastStartRel = time;
    timeLastStartAbs = getNow();
  }

  public long getNow(){
    Double truc = System.nanoTime()/1e6;
    return truc.longValue();
  }

  public void play(){
//    processor.start();
    isPlaying = true;
    timeLastStartAbs = getNow();
//    timeLastStartRel = 0;
  }

  public void pause(){
//    processor.stop();
//    timeLastStartRel = processor.mMsElapsed;
    timeLastStartRel = getTimeRel();
    isPlaying = false;
  }

//  public boolean isPlaying() {
//    return processor.isRunning();
//  }

  public long getTimeRel(){
    if(isPlaying){
      timeNowAbs = getNow();
      return (timeNowAbs - timeLastStartAbs + timeLastStartRel);
    } else
      return timeLastStartRel;
  }

  public void addNote(int midiNote){
    GlobalVars.getInstance().midiPlayer.jouerNote(midiNote, 80);
    timeNowRel = getTimeRel();
    NoteOn on = new NoteOn(timeNowRel, channel, midiNote, velocity);
    noteTrack.insertEvent(on);
  }

  public void addNoteOff(int midiNote){
    GlobalVars.getInstance().midiPlayer.stopNote(midiNote);
    timeNowRel = getTimeRel();
    NoteOff off = new NoteOff(timeNowRel, channel, midiNote, 0);
    noteTrack.insertEvent(off);
  }

  public void deleteAllNotes(){
    noteTrack.removeAllEvents();
  }
}
