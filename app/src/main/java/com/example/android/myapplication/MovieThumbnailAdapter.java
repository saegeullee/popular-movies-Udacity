package com.example.android.myapplication;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieThumbnailAdapter extends
        RecyclerView.Adapter<MovieThumbnailAdapter.PosterImageViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    private OnItemClickListener mClickListener;

    private List<Poster> mPosterList;

    public MovieThumbnailAdapter(OnItemClickListener listener) {
        mClickListener = listener;
    }

    @NonNull
    @Override
    public PosterImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_poster_item, viewGroup, false);

        return new PosterImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterImageViewHolder holder, int i) {

        Poster poster = mPosterList.get(i);
        holder.bind(poster);

    }

    /**
     * 먼저 이 메서드에서 mPosterList == null 조건을 추가 해주니
     * onBindViewHolder 에서 null exception 에러가 발생하지 않았다.
     * 어댑터는 내부적으로 getItemCount() 메서드를 통해 아이템의 개수를 다른 override 메서드들에게
     * 제공함을 알 수 있다.
     * @return
     */

    @Override
    public int getItemCount() {
        if(mPosterList == null) return 0;
        return mPosterList.size();
    }

    public void setPosterListData(List<Poster> posterList) {
        mPosterList = posterList;
        notifyDataSetChanged();
    }

    public class PosterImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView posterImage;

        private PosterImageViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.poster_image);
            itemView.setOnClickListener(this);
        }

        private void bind(Poster poster) {
            Picasso.get()
                    .load(Constants.MOVIE_POSTER_IMAGE_PATH + poster.getThumbnail())
                    .into(posterImage);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClicked(getAdapterPosition());
        }
    }
}
