package nicokla.com.musicos.Firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import nicokla.com.musicos.MainAndCo.GlobalVars;

@IgnoreExtraProperties
public class UserFirestore {
  public String name;
  public String objectID;
  public interface MyCallback {
    void onCallback(UserFirestore userFirestore);
  }
  public interface CallbackContainerFollowed {
    void actionIfFollowed(UserFirestore user);
    void actionIfNotFollowed(UserFirestore user);
  }

  public UserFirestore(){}

  // -----------------------

  public static void load(String objectID, UserFirestore.MyCallback myCallback){
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users")
            .document(objectID);
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document.exists()) {
            Log.d("youhou", "DocumentSnapshot data: " + document.getData());
            UserFirestore userFirestore = document.toObject(UserFirestore.class);
            myCallback.onCallback(userFirestore);
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
    db.collection("users")
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


  // ----------------

  public void follow(CallbackContainerFollowed callbackContainer) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserFirestore user = this;
    db.collection("users")
            .document(GlobalVars.getInstance().me.getUid())
            .collection("followedUsers")
            .document(objectID)
            .set(this)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                callbackContainer.actionIfFollowed(user);
              }
            });
  }

  public void unfollow(CallbackContainerFollowed callbackContainer) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserFirestore user = this;
    db.collection("users")
            .document(GlobalVars.getInstance().me.getUid())
            .collection("followedUsers")
            .document(objectID)
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                callbackContainer.actionIfNotFollowed(user);
              }
            });
  }

  public void checkIfFollowed(CallbackContainerFollowed callbackContainer){
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users")
            .document(GlobalVars.getInstance().me.getUid())
            .collection("followedUsers")
            .document(objectID);
    UserFirestore user = this;
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document.exists()) {
//            Log.d("youhou", "DocumentSnapshot data: " + document.getData());
//            SongFirestore songFirestore = document.toObject(SongFirestore.class);
            callbackContainer.actionIfFollowed(user);
          } else {
            callbackContainer.actionIfNotFollowed(user);
          }
        } else {
          Log.d("c balo.", "get failed with ", task.getException());
        }
      }
    });
  }


}
