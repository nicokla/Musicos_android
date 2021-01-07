package nicokla.com.musicos.Realm;

import android.util.Log;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

@IgnoreExtraProperties
public class Song extends RealmObject {

  @PrimaryKey
  private int id;
  @Required
  private String title;
  private String videoId;
  private String imageUrl;
  private int duration;
  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getVideoId() {
    return videoId;
  }

  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }


    public static final String FIELD_ID = "id";

  //private static AtomicInteger INTEGER_COUNTER = new AtomicInteger(0);

  public String getCountString() {
      return Integer.toString(id);
    }
  //  create() & delete() needs to be called inside a transaction.
  static void create(Realm realm) {
    create(realm, false);
  }

  static void create(Realm realm, boolean randomlyInsert) {
    Parent parent = realm.where(Parent.class).findFirst();
    RealmList<Song> items = parent.getItemList();
    Number maxIdNumber = realm.where(Song.class).max("id");
    final long nextId = maxIdNumber != null ? maxIdNumber.longValue() + 1 : 1;

    Song counter = realm.createObject(Song.class, nextId); //increment()
    if (randomlyInsert && items.size() > 0) {
      Random rand = new Random();
      items.listIterator(rand.nextInt(items.size())).add(counter);
    } else {
      items.add(counter);
    }
  }

  static void create2(Realm realm,
                      final String title,
                      final String videoId,
                      final String imageUrl,
                      final int duration,
                      final String description
                      ){
    Parent parent = realm.where(Parent.class).findFirst();
    RealmList<Song> items = parent.getItemList();
    Number maxIdNumber = realm.where(Song.class).max("id");
    final long nextId = maxIdNumber != null ? maxIdNumber.longValue() + 1 : 1;

    Log.d("id of song:", String.valueOf(nextId));
    Song song = realm.createObject(Song.class, nextId); //increment()

    song.setTitle(title);
    song.setDescription(description);
    song.setDuration(duration);
    song.setImageUrl(imageUrl);
    song.setVideoId(videoId);

    items.add(song);
  }

  public static void delete(Realm realm, long id) {
    Song item = realm.where(Song.class).equalTo(FIELD_ID, id).findFirst();
    // Otherwise it has been deleted already.
    if (item != null) {
      item.deleteFromRealm();
    }
  }

  /*private static int increment() {
    return INTEGER_COUNTER.getAndIncrement();
  }*/

}
