package com.example.android.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myapplication.models.MovieTrailer;

import java.util.List;

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.TrailerViewHolder> {

    private List<MovieTrailer> mTrailersList;
    private OnTrailerItemClickListener mListener;

    public interface OnTrailerItemClickListener {
        void onTrailerItemClicked(String key);
    }

    public MovieTrailersAdapter(OnTrailerItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_trailer_row, viewGroup, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int i) {

        MovieTrailer trailer = mTrailersList.get(i);
        holder.trailerName.setText(trailer.getName());

    }

    @Override
    public int getItemCount() {
        if(mTrailersList == null) return 0;
        return mTrailersList.size();
    }

    public void setTrailerList(List<MovieTrailer> trailers) {
        mTrailersList = trailers;
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

        private TextView trailerName;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);

            trailerName = itemView.findViewById(R.id.trailerName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String key = mTrailersList.get(getAdapterPosition()).getKey();
            mListener.onTrailerItemClicked(key);

        }
    }
}
