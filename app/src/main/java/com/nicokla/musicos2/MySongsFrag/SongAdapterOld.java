package com.nicokla.musicos2.MySongsFrag;

//import io.realm.OrderedRealmCollection;
//import io.realm.RealmRecyclerViewAdapter;
//import io.realm.OrderedRealmCollection;
//import io.realm.RealmRecyclerViewAdapter;
import com.nicokla.musicos2.R;

class SongAdapterOld {//extends RealmRecyclerViewAdapter<Song, SongAdapterOld.SongHolder> {
//  //private List<Song> songList;
//  public OnSwipeListener mSwipeListener;
// // public int toto;
//
//  SongAdapterOld(OrderedRealmCollection<Song> data) {
//    super(data, true);
//    // Only set this if the model class has a primary key that is also a integer or long.
//    // In that case, {@code getItemId(int)} must also be overridden to return the key.
//    // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#hasStableIds()
//    // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#getItemId(int)
//    setHasStableIds(true);
//  }
//
//  @NonNull
//  @Override
//  public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//    View itemView = LayoutInflater.from(parent.getContext())
//            .inflate(R.layout.swipable_cell, parent, false);
//    return new SongHolder(itemView);
//  }
//
//  @Override
//  public void onBindViewHolder(@NonNull SongHolder holder, int position) {
//    final Song song = getItem(position);
//    holder.populate(song);
//    holder.video_view.setOnClickListener(new View.OnClickListener() {
//
//      //onClick method called when the view is clicked
//      @Override
//      public void onClick(View view) {
////        HomeFragmentDirections.SeeVideo action =
////                HomeFragmentDirections.seeVideo(song.getVideoId(), "abcdefg");
////        Navigation.findNavController(view).navigate(action);
//      }
//    });
//
//  }
//
//  /*@Override
//  public int getItemCount() {
//    return 0;
//  }*/
//
//  @Override
//  public long getItemId(int index) {
//    //noinspection ConstantConditions
//    return getItem(index).getId();
//  }
//
//  class SongHolder extends RecyclerView.ViewHolder{
//    public ImageView thumbnail;
//    public TextView video_title, video_id, video_description;
//    public RelativeLayout video_view;
//    public Button btnDelete;
//
//    private Song song;
//
//    public SongHolder(View view) {
//      super(view);
//      thumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);
//      video_title = (TextView) view.findViewById(R.id.video_title);
//      video_id = (TextView) view.findViewById(R.id.video_id);
//      video_description = (TextView) view.findViewById(R.id.video_description);
//      video_view = (RelativeLayout) view.findViewById(R.id.video_view);
//      btnDelete = itemView.findViewById(R.id.btnDelete);
//    }
//
//    public void populate(Song song) {
//      this.song = song;
//      video_id.setText("Video ID : "+song.getVideoId()+" ");
//      video_title.setText(song.getTitle());
//      video_description.setText(song.getDescription());
//      Picasso.get()//mContext
//              .load(song.getImageUrl())
//              .resize(480,270)
//              .centerCrop()
//              .into(thumbnail);
//      btnDelete.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//          if (mSwipeListener != null) {
//            mSwipeListener.onDelete(getAdapterPosition());
//          }
//        }
//      });
//
//    }
//
//  }
//
//  public void addSwipeListener(OnSwipeListener listener) {
//    this.mSwipeListener = listener;
//  }

}
