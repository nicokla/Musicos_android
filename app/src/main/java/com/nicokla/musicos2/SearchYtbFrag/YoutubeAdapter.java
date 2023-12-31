package com.nicokla.musicos2.SearchYtbFrag;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

//import io.realm.Realm;
//import DataHelper;
import com.nicokla.musicos2.Firebase.SongStorage;
import com.nicokla.musicos2.MainAndCo.GlobalVars;
import com.nicokla.musicos2.R;
import com.nicokla.musicos2.SearchYtbFrag.SearchYtbFragmentDirections;

/**
 * Created by Abhishek on 16-Feb-18.
 */


//Adapter class for RecyclerView of videos
public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.MyViewHolder> {

    private Context mContext;
    private List<VideoItem> mVideoList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView thumbnail;
        public String thumbnailURL;
        public TextView video_title, video_id, video_description;
        public RelativeLayout video_view;

        public MyViewHolder(View view) {
            
            super(view);

            //the video_item.xml file is now associated as view object
            //so the view can be called from view's object
            thumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);
            video_title = (TextView) view.findViewById(R.id.video_title);
            video_id = (TextView) view.findViewById(R.id.video_id);
            video_description = (TextView) view.findViewById(R.id.video_description);
            video_view = (RelativeLayout) view.findViewById(R.id.video_view);
        }
    }

    //Parameterised Constructor to save the Activity context and video list
    //helps in initializing a oject for this class
    public YoutubeAdapter(Context mContext, List<VideoItem> mVideoList) {
        this.mContext = mContext;
        this.mVideoList = mVideoList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    //filling every item of view with respective text and image
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

         // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final VideoItem singleVideo = mVideoList.get(position);

        //replace the default text with id, title and description with setText method
        holder.video_id.setText("Video ID : " + singleVideo.getId() + " ");
        holder.video_title.setText(singleVideo.getTitle());
        holder.video_description.setText(singleVideo.getDescription());

        //Picasso library allows for hassle-free image loading
        // in your application—often in one line of code!
        //Features :
        //-Handling ImageView recycling and download cancelation in an adapter
        //-Complex image transformations with minimal memory use
        //-Automatic memory and disk caching
        
        //placing the thumbnail with picasso library 
        //by resizing it to the size of thumbnail

        //with method gives access to the global default Picasso instance
        //load method starts an image request using the specified path may be a remote URL, file resource, etc.
        //resize method resizes the image to the specified size in pixels wrt width and height
        //centerCrop crops an image inside of the bounds specified by resize(int, int) rather than distorting the aspect ratio
        //into method asynchronously fulfills the request into the specified Target
        Picasso.get()//mContext
                .load(singleVideo.getThumbnailURL())
                .resize(480,270)
                .centerCrop()
                .into(holder.thumbnail);
        holder.thumbnailURL = singleVideo.getThumbnailURL();

        //setting on click listener for each video_item to launch clicked video in new activity
        holder.video_view.setOnClickListener(new View.OnClickListener() {
            
            //onClick method called when the view is clicked
            @Override
            public void onClick(View view) {
//                Realm realm = Realm.getDefaultInstance();
//                DataHelper.addItemAsync2(realm,
//                        singleVideo.getTitle(),
//                        singleVideo.getId(),
//                        singleVideo.getThumbnailURL(),
//                        60,
//                        singleVideo.getDescription()
//                );

                // CRUD : Create
                String newId = UUID.randomUUID().toString();
                Log.d("newId", newId);
                GlobalVars.getInstance().songFirestore.set(
                        180, // TODO : get ytb video vrai duree
                        // ---> https://developers.google.com/youtube/v3/docs/videos
                        System.currentTimeMillis()/1000,
                        GlobalVars.getInstance().meFirestore.name,
                        holder.thumbnailURL,
                        singleVideo.getId(),
                        "",
                        GlobalVars.getInstance().meFirestore.objectID,
                        singleVideo.getTitle(),
                        newId);
                GlobalVars.getInstance().songFirestore.save();

                GlobalVars.getInstance().songStorage = new SongStorage();
                GlobalVars.getInstance().songStorage.save(newId);

                Navigation.findNavController(view).navigate(
                        SearchYtbFragmentDirections.Companion.confirmVideo(singleVideo.getId(), newId)
                );

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    //here the dataset is mVideoList
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}