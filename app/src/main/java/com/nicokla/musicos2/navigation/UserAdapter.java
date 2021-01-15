package com.nicokla.musicos2.navigation;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import com.nicokla.musicos2.Firebase.UserFirestore;
import com.nicokla.musicos2.MySongsFrag.FirestoreAdapter;
import com.nicokla.musicos2.databinding.SongItemWithAuthorBinding;
import com.nicokla.musicos2.databinding.UserItemBinding;

class UserAdapter extends FirestoreAdapter<UserAdapter.ViewHolder> {

  public interface OnUserSelectedListener {

    void onUserSelected(DocumentSnapshot song);

  }

  private UserAdapter.OnUserSelectedListener mListener;

  public UserAdapter(Query query, UserAdapter.OnUserSelectedListener listener) {
    super(query);
    mListener = listener;
  }

  @Override
  public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new UserAdapter.ViewHolder(UserItemBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
    holder.bind(getSnapshot(position), mListener);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private UserItemBinding binding;

    public ViewHolder(@NonNull UserItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public ViewHolder(View itemView) {
      super(itemView);
    }

    public void bind(final DocumentSnapshot snapshot,
                     final UserAdapter.OnUserSelectedListener listener) {

      UserFirestore user = snapshot.toObject(UserFirestore.class);
      Resources resources = itemView.getResources();

      binding.userName.setText(user.name);

      // Click listener
      binding.userView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (listener != null) {
            listener.onUserSelected(snapshot);
          }
        }
      });
    }

  }
}

