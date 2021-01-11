package nicokla.com.musicos.MainAndCo;

//import com.algolia.search.saas.Client;
//import com.algolia.search.saas.Index;
import com.badlogic.gdx.graphics.Color;
import com.google.firebase.auth.FirebaseUser;

import nicokla.com.musicos.Firebase.SongFirestore;
import nicokla.com.musicos.Firebase.SongStorage;
import nicokla.com.musicos.Firebase.UserFirestore;
import nicokla.com.musicos.PlayerFrag.MidiStuff.MidiPlayer;

public class GlobalVars {
  public FirebaseUser me;
  private static GlobalVars instance = null;
  public String [] notesEnglish = new String[]
          {"C", "C#", "D", "Eb", "E", "F",
           "F#", "G", "Ab", "A", "Bb", "B"};
  public String [] notesFrench = new String[]
          {"Do", "Do#", "Re", "Mib", "Mi", "Fa",
           "Fa#", "Sol", "Lab", "La", "Sib", "Si"};
  public String [] notesLatin = new String[]
          {"1", "2b", "2", "3b", "3", "4",
           "5b", "5", "6b", "6", "7b", "7"};
  public String [] notesRoman = new String[]
          {"I", "IIb", "II", "IIIb", "III", "IV",
                  "Vb", "V", "VIb", "VI", "VIIb", "VII"};
  public Integer [] positions = new Integer[]
          {0, 1, 1, 2, 2, 3, 4, 4, 5, 5, 6, 6};
  public Color[][] colors = {
          {new Color(1.0f, 147f/255f, 147f/255f, 1f),
                  new Color(181f/255f, 0f, 0f, 1f)},
          {new Color(202f/255f, 1f, 198f/255f, 1f),
                  new Color(12f/255f, 183f/255f, 0f, 1f)},
          {new Color(168f/255f, 214f/255f, 1f, 1f),
                  new Color(0f, 112f/255f, 214f/255f, 1f)},
  };
  public int [] couleurs = new int[]{
          0xFFFF9393, 0xFFB50000,
          0xFFCAFFC6, 0xFF0CB700,
          0xFFA8D6FF, 0xFF0070D6,
  };
  public MidiPlayer midiPlayer = new MidiPlayer();;
  public SongFirestore songFirestore = new SongFirestore();
  public SongStorage songStorage = new SongStorage();
  public UserFirestore user = new UserFirestore();
  public UserFirestore meFirestore = new UserFirestore();

//  Client client = new Client("SKJIA8T5Z2", "5e2190935369d22de34d9ff049391343");
//  Index indexUsers = client.getIndex("users");
//  Index indexSongs = client.getIndex("songs");

  protected GlobalVars() {
    // Exists only to defeat instantiation.
  }
  public static GlobalVars getInstance() {
    if(instance == null) {
      instance = new GlobalVars();
    }
    return instance;
  }
}
