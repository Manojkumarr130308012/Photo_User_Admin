package com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.FullScreenActivity;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Model.BaseViewHolder;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Model.YoutubeVideo;

import java.util.List;


public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_NORMAL = 1;
    private List<YoutubeVideo> mYoutubeVideos;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    public YoutubeRecyclerAdapter(List<YoutubeVideo> youtubeVideos) {
        mYoutubeVideos = youtubeVideos;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_youtube_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }
    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }
    @Override
    public int getItemCount() {
        if (mYoutubeVideos != null && mYoutubeVideos.size() > 0) {
            return mYoutubeVideos.size();
        } else {
            return 1;
        }
    }
    public void setItems(List<YoutubeVideo> youtubeVideos) {
        mYoutubeVideos = youtubeVideos;
        notifyDataSetChanged();
    }
    public class ViewHolder extends BaseViewHolder {
        TextView textWaveTitle;
        ImageView delbtnPlayButton;
        ImageView playButton;
        YouTubePlayerView youTubePlayerView;
        public ViewHolder(View itemView) {
            super(itemView);
            textWaveTitle = itemView.findViewById(R.id.textViewTitle);
            delbtnPlayButton = itemView.findViewById(R.id.delbtnPlay);
            playButton = itemView.findViewById(R.id.btnPlay);
            youTubePlayerView = itemView.findViewById(R.id.youtube_view);
        }
        protected void clear() {
        }
        public void onBind(int position) {
            super.onBind(position);
            final YoutubeVideo mYoutubeVideo = mYoutubeVideos.get(position);
            ((Activity) itemView.getContext()).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;


            playButton.setVisibility(View.VISIBLE);
            delbtnPlayButton.setVisibility(View.GONE);
            youTubePlayerView.setVisibility(View.VISIBLE);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                    youTubePlayer.cueVideo(mYoutubeVideo.getVideoId(), 0);
                }
            });
            playButton.setOnClickListener(view -> {

//                playButton.setVisibility(View.GONE);

                Intent intent=new Intent(view.getContext(), FullScreenActivity.class);
                intent.putExtra("video_url",mYoutubeVideo.getVideoId());
                intent.putExtra("video_id",mYoutubeVideo.getId());
                view.getContext().startActivity(intent);

            });
        }
    }
}