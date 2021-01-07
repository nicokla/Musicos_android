package nicokla.com.musicos.MySongsFrag;


import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import nicokla.com.musicos.Firebase.SongFirestore;
import nicokla.com.musicos.R;
import nicokla.com.musicos.Realm.Song;
import nicokla.com.musicos.databinding.SwipableCellBinding;
import nicokla.com.musicos.databinding.VideoItemBinding;

public class SongAdapter extends FirestoreAdapter<SongAdapter.ViewHolder> {
  static public OnSwipeListener mSwipeListener;

  public interface OnSongSelectedListener {

    void onSongSelected(DocumentSnapshot song);

  }

  private OnSongSelectedListener mListener;

  public SongAdapter(Query query, OnSongSelectedListener listener) {
    super(query);
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//    return new ViewHolder(VideoItemBinding.inflate(
//            LayoutInflater.from(parent.getContext()), parent, false));
    View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.swipable_cell, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(getSnapshot(position), mListener);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView video_title, video_id, video_description;
    public RelativeLayout video_view;
    public Button btnDelete;

//    private VideoItemBinding binding;

//    public ViewHolder(@NonNull VideoItemBinding binding) {
//      super(binding.getRoot());
//      this.binding = binding;
//    }

    public ViewHolder(View view) {
      super(view);
      thumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);
      video_title = (TextView) view.findViewById(R.id.video_title);
      video_id = (TextView) view.findViewById(R.id.video_id);
      video_description = (TextView) view.findViewById(R.id.video_description);
      video_view = (RelativeLayout) view.findViewById(R.id.video_view);
      btnDelete = itemView.findViewById(R.id.btnDelete);
    }

    public void bind(final DocumentSnapshot snapshot,
                     final OnSongSelectedListener listener) {

      SongFirestore song = snapshot.toObject(SongFirestore.class);
      Resources resources = itemView.getResources();

      // Load image
//      Glide.with(binding.videoThumbnail.getContext())
//              .load(song.getImageUrl())
//              .resize(480,270)
//              .centerCrop()
//              .into(binding.videoThumbnail);
      if(song.imageUrl != ""){
        Picasso.get()//mContext
                .load(song.getImageUrl())
                .resize(480,270)
                .centerCrop()
                .into(thumbnail);
      }
      video_description.setText("super la description.");
      video_id.setText(song.videoID);
      video_title.setText(song.getTitle());

      // Click listener
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (listener != null) {
            listener.onSongSelected(snapshot);
          }
        }
      });

      btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (mSwipeListener != null) {
            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
            mFirestore.collection("songs")
                    .document(song.objectID)
                    .delete();


//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                      @Override
//                      public void onSuccess(Void aVoid) {
//                        Log.d("hop", "DocumentSnapshot successfully deleted!");
//                      }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                      @Override
//                      public void onFailure(@NonNull Exception e) {
//                        Log.w("houp", "Error deleting document", e);
//                      }
//                    });

            // TODO: delete storage song document

            mSwipeListener.onDelete(getAdapterPosition());
          }
        }
      });

    }

  }
}

