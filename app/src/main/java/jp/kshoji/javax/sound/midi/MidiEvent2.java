//package jp.kshoji.javax.sound.midi;
//
////import android.support.annotation.NonNull;
//
//import androidx.annotation.NonNull;
//
///**
// * Represents MIDI Event
// *
// * @author K.Shoji
// */
//public class MidiEvent {
//  public final MidiMessage message;
//
//  public long time; // in milliseconds
//
//  public MidiEvent(@NonNull final MidiMessage message, final long time) {
//    this.message = message;
//    this.time = time;
//  }
//
//  @NonNull
//  public MidiMessage getMessage() {
//    return message;
//  }
//
//  public long getTime() {
//    return time;
//  }
//
//  public void setTime(long time) {
//    this.time = time;
//  }
//
//  public long getTick() {
//        return time;
//    }
//
//  public void setTick(long tick) {
//      this.time = tick;
//  }
//}