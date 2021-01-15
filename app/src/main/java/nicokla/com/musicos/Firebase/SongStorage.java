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

import java.nio.charset.StandardCharsets;

//  https://github.com/google/gson/blob/master/UserGuide.md#TOC-Object-Examples

public class SongStorage {
  public interface MyCallback {
    void onCallback(SongStorage songStorage);
  }

  // List vs [] vs arrays ?
  public boolean[] scale;
  public String[] chordNames;
  public int[][] chordNotes;
  public int instru2_n;
  public int volumeRecording;
  public int rootNote;
  public int instru1_n;
  public int noteNames;
  public float volumeYoutube;
  public NoteFirebase[] notes;
  public boolean[] chordRoots;
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
    chordNotes = new int[36][];
    chordNotes[0] = new int[]{0,4,7};
    chordNotes[1] = new int[]{};
    chordNotes[2] = new int[]{2,5,9};
    chordNotes[3] = new int[]{};
    chordNotes[4] = new int[]{4,7,11};
    chordNotes[5] = new int[]{5,9,12};
    chordNotes[6] = new int[]{};
    chordNotes[7] = new int[]{7,11,14};
    chordNotes[8] = new int[]{};
    chordNotes[9] = new int[]{9,12,16};
    chordNotes[10] = new int[]{};
    chordNotes[11] = new int[]{11,14,17};
    chordNotes[12] = new int[]{0,4,7,11};
    chordNotes[13] = new int[]{};
    chordNotes[14] = new int[]{5,12,9,2};
    chordNotes[15] = new int[]{};
    chordNotes[16] = new int[]{7,4,11,14};
    chordNotes[17] = new int[]{12,5,16,9};
    chordNotes[18] = new int[]{};
    chordNotes[19] = new int[]{17,7,11,14};
    chordNotes[20] = new int[]{};
    chordNotes[21] = new int[]{19,16,9,12};
    chordNotes[22] = new int[]{};
    chordNotes[23] = new int[]{14,21,17,11};
    chordNotes[24] = new int[]{0,4,7,11};
    chordNotes[25] = new int[]{};
    chordNotes[26] = new int[]{5,12,9,2};
    chordNotes[27] = new int[]{};
    chordNotes[28] = new int[]{7,4,11,14};
    chordNotes[29] = new int[]{12,5,16,9};
    chordNotes[30] = new int[]{};
    chordNotes[31] = new int[]{17,7,11,14};
    chordNotes[32] = new int[]{};
    chordNotes[33] = new int[]{19,16,9,12};
    chordNotes[34] = new int[]{};
    chordNotes[35] = new int[]{14,21,17,11};
    instru2_n = 0;
    volumeRecording = 90;
    rootNote = 48;
    instru1_n = 0;
    noteNames = 0;
    volumeYoutube = (float) 0.9;
    notes = new NoteFirebase[]{};
    chordRoots = new boolean[]{true,false,true,false,true,true,false,true,false,true,false,true};
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
    Gson gson = new GsonBuilder().serializeNulls().create();
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
