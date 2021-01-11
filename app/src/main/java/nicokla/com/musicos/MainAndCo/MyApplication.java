package nicokla.com.musicos.MainAndCo;

import android.app.Application;

//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//import nicokla.com.musicos.Realm.Parent;

public class MyApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
//    Realm.init(this);
//    RealmConfiguration.Builder
//            realmConfigBuilder = new RealmConfiguration.Builder();
//    /*RealmConfiguration.Builder
//            realmConfigBuilder = new RealmConfiguration.Builder()
//            .name("musicos.realm")
//            .schemaVersion(1)
//            .deleteRealmIfMigrationNeeded();*/
//
//    /*realmConfigBuilder.initialData(new Realm.Transaction() {
//              @Override
//              public void execute(Realm realm) {
//                realm.createObject(Parent.class);
//              }});*/
//
//    RealmConfiguration myConfig = realmConfigBuilder.build();
//
//    //Realm.deleteRealm(myConfig); // Delete Realm between app restarts.
//
//    Realm.setDefaultConfiguration(myConfig);
//
//    Realm realm = Realm.getDefaultInstance();
//    Parent object = realm.where(Parent.class)
//            .findFirst();
//    if(object == null){
//      realm.executeTransactionAsync(new Realm.Transaction() {
//        @Override
//        public void execute(Realm realm) {
//          realm.createObject(Parent.class);
//        }
//      });
//    } else {
//    }

  }
}
