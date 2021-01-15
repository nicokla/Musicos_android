package com.nicokla.musicos2.Realm;

import androidx.annotation.NonNull;

import java.util.List;

//import io.realm.Realm;
//import io.realm.RealmResults;
//import io.realm.Sort;

public class SongRepository {
//
//  private Realm realm;
//  private volatile static SongRepository instance;
//
//  private SongRepository() {
//  }
//
//  public static SongRepository getInstance() {
//    if (instance == null) {
//      synchronized (SongRepository.class) {
//        if (instance == null) {
//          instance = new SongRepository();
//        }
//      }
//    }
//    return instance;
//  }
//
//  public RealmResults<Song> getAllSongs() {
//    return realm.where(Song.class).findAll().sort("createdTime", Sort.ASCENDING);
//  }
//
//  public RealmResults<Song> querySong(long id) {
//    return realm.where(Song.class).equalTo("id", id).findAll();
//  }
//
//  public void addNewSong(final String title,
//                         final String videoId,
//                         final String imageUrl,
//                         final int duration,
//                         final String description) {
//    //,
//    //                         final Realm.Transaction.OnSuccess onSuccess,
//    //                         final Realm.Transaction.OnError onError
//    Number maxIdNumber = realm.where(Song.class).max("id");
//    final long nextId = maxIdNumber != null ? maxIdNumber.longValue() + 1 : 1;
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        Song list = realm.createObject(Song.class, nextId);
//        list.setTitle(title);
//        list.setDescription(description);
//        list.setDuration(duration);
//        list.setImageUrl(imageUrl);
//        list.setVideoId(videoId);
//        //list.setCreatedTime(new Date().getTime());
//      }
//    }/*, new Realm.Transaction.OnSuccess() {
//      @Override
//      public void onSuccess() {
//        //LogUtils.d("insert success");
//        if (onSuccess != null) {
//          onSuccess.onSuccess();
//        }
//      }
//    }, new Realm.Transaction.OnError() {
//      @Override
//      public void onError(Throwable error) {
//        //LogUtils.e("insert failed: " + error);
//        if (onError != null) {
//          onError.onError(error);
//        }
//      }
//    }*/);
//  }
//
//  public void deleteSongs(@NonNull final List<Long> listIds) {
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        Long[] ids = listIds.toArray(new Long[listIds.size()]);
//        RealmResults<Song> toDeleteLists = realm.where(Song.class).in("id", ids).findAll();
//        for (Song list : toDeleteLists) {
//          //list.getTasks().deleteAllFromRealm();
//          list.deleteFromRealm();
//        }
//      }
//    });
//  }
//
//  public void deleteSong(final long listId, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<Song> toDeleteLists = realm.where(Song.class).equalTo("id", listId).findAll();
//        Song list = toDeleteLists.get(0);
//        //list.getTasks().deleteAllFromRealm();
//        list.deleteFromRealm();
//      }
//    }, onSuccess, onError);
//  }
//
//  public void updateSongTitle(final long listId, final String newTitle) {
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<Song> listResults = realm.where(Song.class).equalTo("id", listId).findAll();
//        if (listResults.size() > 0) {
//          Song list = listResults.get(0);
//          list.setTitle(newTitle);
//          realm.copyToRealmOrUpdate(list);
//        }
//      }
//    });
//  }
///*
//  public void addNewTask(final long listId, final String taskTitle, final Realm.Transaction.OnSuccess onSuccess,
//                         final Realm.Transaction.OnError onError) {
//    LogUtils.d("add New Task to list " + listId + ", " + taskTitle);
//    Number maxIdNumber = realm.where(Task.class).max("id");
//    final long nextId = maxIdNumber != null ? maxIdNumber.longValue() + 1 : 1;
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<Song> results = realm.where(Song.class).equalTo("id", listId).findAll();
//        if (results.size() > 0) {
//          Song list = results.get(0);
//          Task task = new Task();
//          task.setId(nextId);
//          task.setTitle(taskTitle);
//          task.setListId(listId);
//          task.setCreatedTime(new Date().getTime());
//          list.addTask(task);
//          realm.copyToRealmOrUpdate(list);
//        }
//      }
//    }, new Realm.Transaction.OnSuccess() {
//      @Override
//      public void onSuccess() {
//        LogUtils.d("insert success");
//        if (onSuccess != null) {
//          onSuccess.onSuccess();
//        }
//
//      }
//    }, new Realm.Transaction.OnError() {
//      @Override
//      public void onError(Throwable error) {
//        LogUtils.e("insert failed: " + error);
//        if (onError != null) {
//          onError.onError(error);
//        }
//
//      }
//    });
//  }
//
//  public void deleteTask(final long taskId, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<Task> toDeleteTasks = realm.where(Task.class).equalTo("id", taskId).findAll();
//        toDeleteTasks.deleteAllFromRealm();
//      }
//    }, onSuccess, onError);
//  }
//
//  public void deleteTasks(@NonNull final List<Long> taskIds) {
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        LogUtils.d("delete: " + taskIds);
//        Long[] ids = taskIds.toArray(new Long[taskIds.size()]);
//        RealmResults<Task> toDeleteTasks = realm.where(Task.class).in("id", ids).findAll();
//        toDeleteTasks.deleteAllFromRealm();
//      }
//    });
//  }
//
//  public void updateTaskState(final long taskId, final boolean isDone) {
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<Task> results = realm.where(Task.class).equalTo("id", taskId).findAll();
//        if (results.size() > 0) {
//          Task task = results.get(0);
//          task.setDone(isDone);
//          realm.copyToRealmOrUpdate(task);
//        }
//      }
//    });
//  }
//
//  public void updateTaskTitle(final long taskId, final String newTitle) {
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<Task> results = realm.where(Task.class).equalTo("id", taskId).findAll();
//        if (results.size() > 0) {
//          Task task = results.get(0);
//          task.setTitle(newTitle);
//          realm.copyToRealmOrUpdate(task);
//        }
//      }
//    });
//  }
//
//  public void updateTaskNote(final long taskId, final String newNote) {
//    realm.executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<Task> results = realm.where(Task.class).equalTo("id", taskId).findAll();
//        if (results.size() > 0) {
//          Task task = results.get(0);
//          task.setNote(newNote);
//          realm.copyToRealmOrUpdate(task);
//        }
//      }
//    });
//  }
//
//  public RealmResults<Task> queryTasks(final long listId, final boolean isDone) {
//    return realm.where(Task.class)
//            .equalTo("listId", listId)
//            .equalTo("isDone", isDone)
//            .findAll()
//            .sort("createdTime", Sort.ASCENDING);
//  }
//
//  public RealmResults<Task> queryTask(final long taskId) {
//    return realm.where(Task.class)
//            .equalTo("id", taskId)
//            .findAll();
//  }*/
//
//  public void getRealm() {
//    realm = Realm.getDefaultInstance();
//  }
//
//  public void close() {
//    realm.close();
//  }
}
