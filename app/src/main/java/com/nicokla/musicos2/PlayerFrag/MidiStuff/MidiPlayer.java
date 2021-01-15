package com.nicokla.musicos2.PlayerFrag.MidiStuff;

import android.util.Log;

import org.billthefarmer.mididriver.MidiDriver;

public class MidiPlayer implements MidiDriver.OnMidiStartListener{

    public MidiDriver midiDriver;
    public int instrument = 0;
    public int[] config;

    public MidiPlayer() {
        midiDriver = new MidiDriver();
        midiDriver.setOnMidiStartListener(this);
    }

    public void resume() {
        midiDriver.start();

        // Get the configuration.
        config = midiDriver.config();

        // Print out the details.
        Log.d(this.getClass().getName(), "maxVoices: " + config[0]);
        Log.d(this.getClass().getName(), "numChannels: " + config[1]);
        Log.d(this.getClass().getName(), "sampleRate: " + config[2]);
        Log.d(this.getClass().getName(), "mixBufferSize: " + config[3]); // !!!
    }

    public void pause() {
        midiDriver.stop();
    }


    @Override
    public void onMidiStart(){
        //changeInstrument(0);
    }

    public void sendMidi(int m, int p) {
        byte msg[] = new byte[2];

        msg[0] = (byte) m;
        msg[1] = (byte) p;

        midiDriver.write(msg);
    }

    public void sendMidi(int m, int n, int v) {
        byte msg[] = new byte[3];

        msg[0] = (byte) m;
        msg[1] = (byte) n;
        msg[2] = (byte) v;

        midiDriver.write(msg);
    }

    public void changeInstrument(int n){
        instrument=(n%128);
        sendMidi(0b11000000, (byte)instrument);
    }

    public void jouerNote(int x, int myVolume){
        sendMidi(0x90, x, myVolume);
    }

    public void stopNote(int x){
        sendMidi(0x80, x, 0);
    }



}
