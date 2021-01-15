package com.nicokla.musicos2.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;

import com.nicokla.musicos2.MainAndCo.GlobalVars;

@IgnoreExtraProperties
public class SongFirestore {
  public interface MyCallback {
    void onCallback(SongFirestore songFirestore);
  }
  public interface CallbackContainerLiked {
    void actionIfLiked(SongFirestore song);
    void actionIfNotLiked(SongFirestore song);
  }

  public static final String FIELD_DURATION = "duration";
  public static final String FIELD_DATETIME = "datetime";
  public static final String FIELD_OWNER_NAME = "ownerName";
  public static final String FIELD_IMAGE_URL = "imageURL";
  public static final String FIELD_VIDEO_ID = "videoID";
  public static final String FIELD_ORIGINAL_ID = "originalID";
  public static final String FIELD_OWNER_ID = "ownerID";
  public static final String FIELD_TITLE = "title";
  public static final String OBJECT_ID = "objectID";

  public float duration;
  public long datetime;
  public String ownerName;
  public String imageUrl;
  public String videoID;
  public String originalID;
  public String ownerID;
  public String title;
  public String objectID;

  public SongFirestore() {}

  public SongFirestore(float duration, long datetime, String ownerName, String imageUrl,
                       String videoID, String originalID, String ownerID, String title,
                       String objectID) {
    set(duration, datetime, ownerName, imageUrl,
            videoID, originalID, ownerID, title,
            objectID);
  }

  public void set(float duration, long datetime, String ownerName, String imageUrl,
             String videoID, String originalID, String ownerID, String title,
             String objectID){
    this.duration = duration;
    this.datetime = datetime;
    this.ownerName = ownerName;
    this.imageUrl = imageUrl;
    this.videoID = videoID;
    this.originalID = originalID;
    this.ownerID = ownerID;
    this.title = title;
    this.objectID = objectID;
  }

  public static void load(String objectID, MyCallback myCallback){
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("songs")
            .document(objectID);
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document.exists()) {
            Log.d("youhou", "DocumentSnapshot data: " + document.getData());
            SongFirestore songFirestore = document.toObject(SongFirestore.class);
            myCallback.onCallback(songFirestore);
          } else {
            Log.d("padchance.", "No such document");
          }
        } else {
          Log.d("c balo.", "get failed with ", task.getException());
        }
      }
    });

  }

  public void save(){
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("songs")
    .document(objectID)
    .set(this);
//    .addOnSuccessListener(new OnSuccessListener<Void>() {
//      @Override
//      public void onSuccess(Void aVoid) {
//        Log.d("yop", "DocumentSnapshot successfully written!");
//      }
//    })
//    .addOnFailureListener(new OnFailureListener() {
//      @Override
//      public void onFailure(@NonNull Exception e) {
//        Log.w("yopyop", "Error writing document", e);
//      }
//    });
  }

  public static void delete(String id){
    Log.d("delete", id);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("songs")
            .document(id)
            .delete();
  }

// --------------------------------


  public void like(CallbackContainerLiked callbackContainer) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SongFirestore song = this;
    db.collection("users")
      .document(GlobalVars.getInstance().me.getUid())
      .collection("likedSongs")
      .document(objectID)
      .set(this)
      .addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {
        callbackContainer.actionIfLiked(song);
      }
    });
  }

  public void dislike(CallbackContainerLiked callbackContainer) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SongFirestore song = this;
    db.collection("users")
            .document(GlobalVars.getInstance().me.getUid())
            .collection("likedSongs")
            .document(objectID)
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                callbackContainer.actionIfNotLiked(song);
              }
            });
  }

  public void checkIfLiked(CallbackContainerLiked callbackContainer){
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users")
            .document(GlobalVars.getInstance().me.getUid())
            .collection("likedSongs")
            .document(objectID);
    SongFirestore song = this;
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document.exists()) {
            callbackContainer.actionIfLiked(song);
          } else {
            callbackContainer.actionIfNotLiked(song);
          }
        } else {
          Log.d("c balo.", "get failed with ", task.getException());
        }
      }
    });
  }


  // -------------------------------------

  public float getDuration() {
    return duration;
  }

  public void setDuration(float duration) {
    this.duration = duration;
  }

  public long getDatetime() {
    return datetime;
  }

  public void setDatetime(long datetime) {
    this.datetime = datetime;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getVideoID() {
    return videoID;
  }

  public void setVideoID(String videoID) {
    this.videoID = videoID;
  }

  public String getOriginalID() {
    return originalID;
  }

  public void setOriginalID(String originalID) {
    this.originalID = originalID;
  }

  public String getOwnerID() {
    return ownerID;
  }

  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getObjectID() {
    return objectID;
  }

  public void setObjectID(String objectID) {
    this.objectID = objectID;
  }

}
