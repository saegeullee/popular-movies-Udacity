package com.example.android.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.database.AppDatabase;
import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.models.Review;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity
        implements MovieReviewsAdapter.ItemClickListener {

    private static final String TAG = "MovieDetailsActivity";

    private TextView release_date, vote_average, plot_synopsis;
    private ImageView poster_image;
    private Button mark_as_favorite;

    private Movie mMovie;

    private boolean isMarkedFavorite = false;
    private MainViewModel mainViewModel;

    private RecyclerView reviewRecyclerView;
    private MovieReviewsAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getIntentFromMainActivity();
        initUI();

        setupViewModel();
    }

    private void setupViewModel() {

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.movieListObserveTester().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d(TAG, "onChanged: Retrieving database update from LiveData");

                for(int i = 0; i < movies.size(); i++) {
                    System.out.println(movies.get(i).toString());
                }
            }
        });

        mainViewModel.getMoviesListTest();

        mainViewModel.movieObserver().observe(this, new Observer<Movie>() {
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
            mainViewModel.movieReviewsObserver().observe(this, new Observer<List<Review>>() {
                @Override
                public void onChanged(@Nullable List<Review> reviews) {
                    Log.d(TAG, "onChanged: reviews : " + reviews.toString());
                    mReviewAdapter.setReviews(reviews);
                }
            });

            mainViewModel.getReviews(mMovie.getMovieId());
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
        mainViewModel.getMovieByTitle(mMovie.getOriginal_title());
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

        reviewRecyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setNestedScrollingEnabled(false);
        mReviewAdapter = new MovieReviewsAdapter(this, this);
        reviewRecyclerView.setAdapter(mReviewAdapter);

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

                    mainViewModel.insertFavoriteMovie(mMovie);
                    isMarkedFavorite = true;


                } else if(mMovie != null && isMarkedFavorite) {

                    Date date = new Date();
                    mMovie.setUpdatedAt(date);

                    mainViewModel.unFavoriteMovie(mMovie);
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
    public void onItemClicked(String itemId) {

    }
}
