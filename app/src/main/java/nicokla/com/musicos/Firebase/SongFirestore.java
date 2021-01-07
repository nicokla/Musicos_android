package nicokla.com.musicos.Firebase;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class SongFirestore {

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
