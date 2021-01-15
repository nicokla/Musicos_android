package com.nicokla.musicos2.navigation;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.nicokla.musicos2.Firebase.SongFirestore;
import com.nicokla.musicos2.MySongsFrag.FirestoreAdapter;
import com.squareup.picasso.Picasso;

import com.nicokla.musicos2.databinding.SongItemBinding;
import com.nicokla.musicos2.databinding.SongItemWithAuthorBinding;

class NotSwipableWithoutAuthorSongAdapter extends FirestoreAdapter<NotSwipableWithoutAuthorSongAdapter.ViewHolder> {

  public interface OnSongSelectedListener {

    void onSongSelected(DocumentSnapshot song);

  }

  private NotSwipableWithoutAuthorSongAdapter.OnSongSelectedListener mListener;

  public NotSwipableWithoutAuthorSongAdapter(Query query, NotSwipableWithoutAuthorSongAdapter.OnSongSelectedListener listener) {
    super(query);
    mListener = listener;
  }

  @Override
  public NotSwipableWithoutAuthorSongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new NotSwipableWithoutAuthorSongAdapter.ViewHolder(SongItemBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(NotSwipableWithoutAuthorSongAdapter.ViewHolder holder, int position) {
    holder.bind(getSnapshot(position), mListener);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private SongItemBinding binding;

    public ViewHolder(@NonNull SongItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public ViewHolder(View itemView) {
      super(itemView);
    }

    public void bind(final DocumentSnapshot snapshot,
                     final NotSwipableWithoutAuthorSongAdapter.OnSongSelectedListener listener) {

      SongFirestore song = snapshot.toObject(SongFirestore.class);
      Resources resources = itemView.getResources();

      if(song.imageUrl != ""){
        Picasso.get()//mContext
                .load(song.getImageUrl())
                .resize(480,270)
                .centerCrop()
                .into(binding.videoThumbnail);
      }
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
