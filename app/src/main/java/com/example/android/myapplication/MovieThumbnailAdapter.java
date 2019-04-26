package com.example.android.myapplication;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieThumbnailAdapter extends RecyclerView.Adapter<MovieThumbnailAdapter.PosterImageViewHolder> {

    private List<Poster> mPosterList;

    public MovieThumbnailAdapter(List<Poster> posterList) {
        mPosterList = posterList;
    }

    @NonNull
    @Override
    public PosterImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_poster_item, viewGroup, false);

        return new PosterImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterImageViewHolder posterImageViewHolder, int i) {

        Poster poster = mPosterList.get(i);
        posterImageViewHolder.bind(poster);


    }

    @Override
    public int getItemCount() {
        return mPosterList.size();
    }

    public class PosterImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView posterImage;

        public PosterImageViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.poster_image);
        }

        public void bind(Poster poster) {
            Picasso.get().load(poster.getThumbnail()).into(posterImage);
        }
    }
}
