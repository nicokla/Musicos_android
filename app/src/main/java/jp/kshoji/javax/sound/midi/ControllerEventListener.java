package jp.kshoji.javax.sound.midi;

//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.EventListener;

/**
 * {@link EventListener} for MIDI Control Change messages.
 * 
 * @author K.Shoji
 */
public interface ControllerEventListener extends EventListener {

	/**
	 * Called at {@link ShortMessage} event has fired
	 * 
	 * @param event the source message
	 */
	void controlChange(@NonNull ShortMessage event);
}
