package com.example.android.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.models.MovieTrailer;
import com.example.android.myapplication.models.Review;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity
        implements MovieReviewsAdapter.OnReviewItemClickListener,
        MovieTrailersAdapter.OnTrailerItemClickListener {

    private static final String TAG = "MovieDetailsActivity";

    private TextView release_date, vote_average, plot_synopsis;
    private TextView no_trailers, no_reviews;
    private ImageView poster_image;
    private Button mark_as_favorite;

    private Movie mMovie;

    private boolean isMarkedFavorite = false;
    private MovieDetailsViewModel movieDetailsViewModel;

    private RecyclerView reviewRecyclerView;
    private MovieReviewsAdapter mReviewAdapter;

    private RecyclerView trailersRecyclerView;
    private MovieTrailersAdapter mTrailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getIntentFromMainActivity();
        initUI();

        setupViewModel();
    }

    private void setupViewModel() {

        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);

        movieDetailsViewModel.movieListObserveTester().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d(TAG, "onChanged: Retrieving database update from LiveData");

                for(int i = 0; i < movies.size(); i++) {
                    System.out.println(movies.get(i).toString());
                }
            }
        });

        movieDetailsViewModel.getMoviesListTest();

        movieDetailsViewModel.movieObserver().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {

                if(movie == null) {
                    isMarkedFavorite = false;
                } else {
                    isMarkedFavorite = true;
                }

                setMarkAsFavoriteBtn();
            }
        });

        didUserMarkThisMovieAsFavorite();

        if(mMovie != null) {

            movieDetailsViewModel.movieReviewsObserver().observe(this, new Observer<List<Review>>() {
                @Override
                public void onChanged(@Nullable List<Review> reviews) {
                    Log.d(TAG, "onChanged: reviews : " + reviews.toString());
                    if(reviews.size() == 0) {
                        no_reviews.setVisibility(View.VISIBLE);
                    } else {
                        no_reviews.setVisibility(View.GONE);
                    }
                    mReviewAdapter.setReviews(reviews);
                }
            });

            movieDetailsViewModel.getReviews(mMovie.getMovieId());

            movieDetailsViewModel.movieTrailersObserver().observe(this, new Observer<List<MovieTrailer>>() {
                @Override
                public void onChanged(@Nullable List<MovieTrailer> movieTrailers) {
                    Log.d(TAG, "onChanged: trailers : " + movieTrailers.toString());

                    if(movieTrailers.size() == 0) {
                        no_trailers.setVisibility(View.VISIBLE);
                    } else {
                        no_trailers.setVisibility(View.GONE);
                    }
                    mTrailerAdapter.setTrailerList(movieTrailers);
                }
            });

            movieDetailsViewModel.getMovieTrailers(mMovie.getMovieId());
        }
    }

    /**
     * 현재 문제점
     * 메인액티비티에서 popular에서 details Activity 로 갔다가 메인으로
     * 되돌아오면 favorites 가 목록에 보인다.
     * -> 문제 해결
     */

    private void didUserMarkThisMovieAsFavorite() {

        if(mMovie != null)
        movieDetailsViewModel.getMovieByTitle(mMovie.getOriginal_title());
    }


    private void getIntentFromMainActivity() {

        if(getIntent().hasExtra(getString(R.string.movie))) {
            mMovie = getIntent().getParcelableExtra(getString(R.string.movie));
            Log.d(TAG, "onCreate: poster : " + mMovie.toString());
        }

    }

    private void initUI() {

        release_date = findViewById(R.id.release_date);
        vote_average = findViewById(R.id.vote_average);
        plot_synopsis = findViewById(R.id.plot_synopsis);
        poster_image = findViewById(R.id.poster_image);
        mark_as_favorite = findViewById(R.id.mark_as_favorite);

        no_reviews = findViewById(R.id.noReviewNoticeText);
        no_trailers = findViewById(R.id.noTrailersNoticeText);

        reviewRecyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setNestedScrollingEnabled(false);
        mReviewAdapter = new MovieReviewsAdapter(this, this);
        reviewRecyclerView.setAdapter(mReviewAdapter);

        trailersRecyclerView = findViewById(R.id.trailerRecyclerView);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trailersRecyclerView.setNestedScrollingEnabled(false);
        mTrailerAdapter = new MovieTrailersAdapter(this);
        trailersRecyclerView.setAdapter(mTrailerAdapter);


        if(mMovie != null) {
            setTitle(mMovie.getOriginal_title());
            vote_average.setText(String.valueOf(mMovie.getVote_average()));
            release_date.setText(mMovie.getRelease_date());
            plot_synopsis.setText(mMovie.getOverview());

            Picasso.get()
                    .load(Constants.MOVIE_POSTER_IMAGE_PATH + mMovie.getPoster_path())
                    .into(poster_image);
        }

        mark_as_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: isMarkedFavorite : " + isMarkedFavorite);

                if(mMovie != null && !isMarkedFavorite) {

                    Date date = new Date();
                    mMovie.setUpdatedAt(date);

                    movieDetailsViewModel.insertFavoriteMovie(mMovie);
                    isMarkedFavorite = true;


                } else if(mMovie != null && isMarkedFavorite) {

                    Date date = new Date();
                    mMovie.setUpdatedAt(date);

                    movieDetailsViewModel.unFavoriteMovie(mMovie);
                    isMarkedFavorite = false;
                }

                setMarkAsFavoriteBtn();

            }
        });
    }

    private void setMarkAsFavoriteBtn() {
        if(isMarkedFavorite) {
            mark_as_favorite.setText(R.string.mark_as_unfavorite);
            mark_as_favorite.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        } else {
            mark_as_favorite.setText(R.string.mark_as_favorite);
            mark_as_favorite.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onTrailerItemClicked(String key) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_VIDEO_URL + key));
        startActivity(intent);

    }

    @Override
    public void onReviewItemClicked(String itemId) {

    }
}
