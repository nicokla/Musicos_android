package com.nicokla.musicos2.Firebase;

public class NoteFirebase{
  public float duration;
  public int midiNote;
  public int velocity;
  public float start;

  public NoteFirebase(float duration, int midiNote, int velocity, float start) {
    this.duration = duration;
    this.midiNote = midiNote;
    this.velocity = velocity;
    this.start = start;
  }

  public NoteFirebase() {}

}