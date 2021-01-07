package nicokla.com.musicos.MySongsFrag;


import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import nicokla.com.musicos.R;
import nicokla.com.musicos.Realm.Song;
import nicokla.com.musicos.databinding.SwipableCellBinding;
import nicokla.com.musicos.databinding.VideoItemBinding;

public class SongAdapter extends FirestoreAdapter<SongAdapter.ViewHolder> {

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
    return new ViewHolder(VideoItemBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(getSnapshot(position), mListener);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private VideoItemBinding binding;

    public ViewHolder(@NonNull VideoItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public ViewHolder(View itemView) {
      super(itemView);
    }

    public void bind(final DocumentSnapshot snapshot,
                     final OnSongSelectedListener listener) {

      Song song = snapshot.toObject(Song.class);
      Resources resources = itemView.getResources();

      // Load image
//      Glide.with(binding.videoThumbnail.getContext())
//              .load(song.getImageUrl())
//              .resize(480,270)
//              .centerCrop()
//              .into(binding.videoThumbnail);
      Picasso.get()//mContext
              .load(song.getImageUrl())
              .resize(480,270)
              .centerCrop()
              .into(binding.videoThumbnail);
      binding.videoDescription.setText(song.getDescription());
      binding.videoId.setText(song.getVideoId());
      binding.videoTitle.setText(song.getTitle());

      // Click listener
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (listener != null) {
            listener.onSongSelected(snapshot);
          }
        }
      });
    }

  }
}

