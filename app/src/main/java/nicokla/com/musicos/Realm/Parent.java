package nicokla.com.musicos.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Parent extends RealmObject {
  private RealmList<Song> itemList;

  public RealmList<Song> getItemList() {
    return itemList;
  }
}