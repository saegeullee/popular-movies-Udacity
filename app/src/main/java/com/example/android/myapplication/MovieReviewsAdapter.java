package com.example.android.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myapplication.models.Review;

import java.util.List;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ReviewViewHolder> {

    private List<Review> mReviews;
    private Context mContext;
    private OnReviewItemClickListener mItemClickListener;

    public interface OnReviewItemClickListener {
        void onReviewItemClicked(String itemId);
    }

    public MovieReviewsAdapter(Context context, OnReviewItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reviews_row, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int i) {

        Review review = mReviews.get(i);
        holder.review_author.setText(review.getAuthor());
        holder.review_content.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        if(mReviews == null) return 0;
        return mReviews.size();
    }

    public void setReviews(List<Review> reviewList) {
        mReviews = reviewList;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView review_author, review_content;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            review_author = itemView.findViewById(R.id.review_author);
            review_content = itemView.findViewById(R.id.review_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String reviewId = mReviews.get(getAdapterPosition()).getId();
            mItemClickListener.onReviewItemClicked(reviewId);
        }
    }
}
