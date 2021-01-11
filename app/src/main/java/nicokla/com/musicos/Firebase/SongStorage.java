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

  public SongStorage() {}

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
