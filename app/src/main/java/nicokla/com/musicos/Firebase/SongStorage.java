package nicokla.com.musicos.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

//  https://github.com/google/gson/blob/master/UserGuide.md#TOC-Object-Examples

public class SongStorage {
  public interface MyCallback {
    void onCallback(SongStorage songStorage);
  }

  // List vs [] vs arrays ?
  public boolean[] scale;
  public String[] chordNames;
  public int[][] chordsNotes;
  public int instru2_n;
  public int volumeRecording;
  public int rootNote;
  public int instru1_n;
  public int noteNames;
  public float volumeYoutube;
  public NoteFirebase[] notes;
  public boolean[] chordsRoots;
  public int volumePlayer;
  public ChordFirebase[] notesAccompagnement;
  public int showChords;

  public SongStorage() {
    scale = new boolean[]{true,true,true,true,
                          true,true,true,true,
                          true,true,true,true};
    chordNames = new String[]{
            "+","","-","","-","+","","+","","-","","dim",
            "Δ","","m7","","m7","Δ","","7","","m7","","ø",
            "Δ","","m7","","m7","Δ","","7","","m7","","ø"
    };
    chordsNotes = new int[36][];
    chordsNotes[0] = new int[]{0,4,7};
    chordsNotes[1] = new int[]{};
    chordsNotes[2] = new int[]{2,5,9};
    chordsNotes[3] = new int[]{};
    chordsNotes[4] = new int[]{4,7,11};
    chordsNotes[5] = new int[]{5,9,12};
    chordsNotes[6] = new int[]{};
    chordsNotes[7] = new int[]{7,11,14};
    chordsNotes[8] = new int[]{};
    chordsNotes[9] = new int[]{9,12,16};
    chordsNotes[10] = new int[]{};
    chordsNotes[11] = new int[]{11,14,17};
    chordsNotes[12] = new int[]{0,4,7,11};
    chordsNotes[13] = new int[]{};
    chordsNotes[14] = new int[]{5,12,9,2};
    chordsNotes[15] = new int[]{};
    chordsNotes[16] = new int[]{7,4,11,14};
    chordsNotes[17] = new int[]{12,5,16,9};
    chordsNotes[18] = new int[]{};
    chordsNotes[19] = new int[]{17,7,11,14};
    chordsNotes[20] = new int[]{};
    chordsNotes[21] = new int[]{19,16,9,12};
    chordsNotes[22] = new int[]{};
    chordsNotes[23] = new int[]{14,21,17,11};
    chordsNotes[24] = new int[]{0,4,7,11};
    chordsNotes[25] = new int[]{};
    chordsNotes[26] = new int[]{5,12,9,2};
    chordsNotes[27] = new int[]{};
    chordsNotes[28] = new int[]{7,4,11,14};
    chordsNotes[29] = new int[]{12,5,16,9};
    chordsNotes[30] = new int[]{};
    chordsNotes[31] = new int[]{17,7,11,14};
    chordsNotes[32] = new int[]{};
    chordsNotes[33] = new int[]{19,16,9,12};
    chordsNotes[34] = new int[]{};
    chordsNotes[35] = new int[]{14,21,17,11};
    instru2_n = 0;
    volumeRecording = 90;
    rootNote = 48;
    instru1_n = 0;
    noteNames = 0;
    volumeYoutube = (float) 0.9;
    notes = new NoteFirebase[]{};
    chordsRoots = new boolean[]{true,false,true,false,true,true,false,true,false,true,false,true};
    volumePlayer = 100;
    notesAccompagnement = new ChordFirebase[]{};
    showChords = 0;
  }

  public static void load(String id, MyCallback myCallback){
    Log.d("get", id);
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference docRef = storageRef.child("songs/" + id);
    final long ONE_MEGABYTE = 1024 * 1024;
    docRef.getBytes(ONE_MEGABYTE) // Long.MAX_VALUE
    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
      @Override
      public void onSuccess(byte[] bytes) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = new String(bytes, StandardCharsets.UTF_8);
        SongStorage songStorage = gson.fromJson(json, SongStorage.class);
        myCallback.onCallback(songStorage);
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception exception) {
        Log.d("pas cool", "hein ?");
      }
    });
  }

  public void save(String id){
    Log.d("set", id);
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference docRef = storageRef.child("songs/" + id);
//    Gson gson = new Gson();
    GsonBuilder builder = new GsonBuilder().serializeNulls();
//    builder.registerTypeAdapter(Float.class, (JsonSerializer<Float>) (src, typeOfSrc, context) -> {
//      DecimalFormat df = new DecimalFormat("#.################");
//      DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//      dfs.setDecimalSeparator('.');
//      df.setDecimalFormatSymbols(dfs);
//      df.setRoundingMode(RoundingMode.CEILING);
//      return new JsonPrimitive(Float.parseFloat(df.format(src)));
//    });
    Gson gson = builder.create();
    String json = gson.toJson(this);
    byte[] data=json.getBytes();
    UploadTask uploadTask = docRef.putBytes(data);
    uploadTask.addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception exception) {
        Log.d("snif", "ouin.");
      }
    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
        Log.d("youpi", "houra.");
      }
    });
  }

  public static void delete(String id){
    Log.d("delete", id);
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference docRef = storageRef.child("songs/" + id);
    docRef.delete();
  }
}
