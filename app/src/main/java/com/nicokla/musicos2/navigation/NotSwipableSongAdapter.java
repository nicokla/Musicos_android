package com.nicokla.musicos2.navigation;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import com.google.firebase.firestore.Query;

import com.nicokla.musicos2.Firebase.SongFirestore;
import com.nicokla.musicos2.MySongsFrag.FirestoreAdapter;

import com.nicokla.musicos2.databinding.SongItemWithAuthorBinding;
import com.nicokla.musicos2.databinding.VideoItemBinding;

public class NotSwipableSongAdapter extends FirestoreAdapter<NotSwipableSongAdapter.ViewHolder> {

  public interface OnSongSelectedListener {

    void onSongSelected(DocumentSnapshot song);

  }

  private OnSongSelectedListener mListener;

  public NotSwipableSongAdapter(Query query, OnSongSelectedListener listener) {
    super(query);
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(SongItemWithAuthorBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(getSnapshot(position), mListener);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private SongItemWithAuthorBinding binding;

    public ViewHolder(@NonNull SongItemWithAuthorBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public ViewHolder(View itemView) {
      super(itemView);
    }

    public void bind(final DocumentSnapshot snapshot,
                     final OnSongSelectedListener listener) {

      SongFirestore song = snapshot.toObject(SongFirestore.class);
      Resources resources = itemView.getResources();

      if(song.imageUrl != ""){
        Picasso.get()//mContext
                .load(song.getImageUrl())
                .resize(480,270)
                .centerCrop()
                .into(binding.videoThumbnail);
      }
      binding.videoDescription.setText(song.ownerName);
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

