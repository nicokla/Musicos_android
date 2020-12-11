package nicokla.com.musicos.PlayerFrag.MidiStuff;

import android.util.Log;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.util.MidiEventListener;

import org.billthefarmer.mididriver.MidiDriver;

import nicokla.com.musicos.MainAndCo.GlobalVars;
import nicokla.com.musicos.MainAndCo.MainActivity;

public class MidiSoundPlayer implements MidiEventListener
     , MidiDriver.OnMidiStartListener
{
    @Override
    public void onMidiStart() {
        Log.d(this.getClass().getName(), "onMidiStart()");
    }

    public MidiSoundPlayer() {

    }

    @Override
    public void onStart(boolean fromBeginning) {
        if(fromBeginning) {
            System.out.println("Started!");
        }
        else {
            System.out.println("resumed");
        }
    }

    @Override
    public void onEvent(MidiEvent event, long ms) {
        if (event instanceof NoteOn){
            NoteOn myEvent = (NoteOn)event;
            GlobalVars.getInstance().midiPlayer.jouerNote
                    (myEvent.getNoteValue(), 80);
        }else if (event instanceof NoteOff){
            NoteOff myEvent = (NoteOff)event;
            GlobalVars.getInstance().midiPlayer.stopNote(myEvent.getNoteValue());
        }
    }

    @Override
    public void onStop(boolean finished) {
        if(finished) {
        }
        else {
        }
    }

    public void main()
    {

        //midiDriver.setOnMidiStartListener(this);

        /* 1. Read in a MidiFile
        MidiFile midi = null;
        try
        {
            midi = new MidiFile(new File("inputmid.mid"));
        }
        catch(IOException e)
        {
            System.err.println(e);
            return;
        }

        // 2. Create a MidiProcessor
        MidiProcessor processor = new MidiProcessor(midi);

        // 3. Register listeners for the events you're interested in
        MidiSoundPlayer ep = new MidiSoundPlayer("Individual Listener");
        processor.registerEventListener(ep, Tempo.class);
        processor.registerEventListener(ep, NoteOn.class);

        // or listen for all events:
        MidiSoundPlayer ep2 = new MidiSoundPlayer("Listener For All");
        processor.registerEventListener(ep2, MidiEvent.class);

        // 4. Start the processor
        processor.start();

        // Listeners will be triggered in real time with the MIDI events
        // And you can pause/resume with stop() and start()
        try
        {
            Thread.sleep(10 * 1000);
            processor.stop();

            Thread.sleep(10 * 1000);
            processor.start();
        }
        catch(Exception e)
        {
        }*/

    }
}
