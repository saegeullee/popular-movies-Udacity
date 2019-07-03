package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.database.AppDatabase;
import com.example.android.myapplication.database.FavoriteMovieEntry;
import com.example.android.myapplication.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";

    private TextView release_date, vote_average, plot_synopsis;
    private ImageView poster_image;
    private Button mark_as_favorite;

    private Movie mMovie;
    private FavoriteMovieEntry mFavoriteMovieEntry;

    private boolean isMarkedFavorite = false;
    private AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getIntentFromMainActivity();
        initUI();
        
        testLoadAll();
    }

    private void testLoadAll() {

        Log.d(TAG, "testLoadAll: in");
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<FavoriteMovieEntry> list = mDatabase.favoriteMovieDao().loadAllFavoriteMovies();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i).toString());
                        }
                    }
                });
            }
        });
    }

    private void didUserMarkThisMovieAsFavorite() {

        if(mMovie != null) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mFavoriteMovieEntry = mDatabase.favoriteMovieDao().getMovieByTitle(mMovie.getOriginal_title());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(mFavoriteMovieEntry == null) {
                                isMarkedFavorite = false;
                                setMarkAsFavoriteBtn();
                            } else {
                                isMarkedFavorite = true;
                                setMarkAsFavoriteBtn();
                            }
                        }
                    });
                }
            });
        }
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

        mDatabase = AppDatabase.getInstance(getApplicationContext());

        if(mMovie != null) {
            setTitle(mMovie.getOriginal_title());
            vote_average.setText(String.valueOf(mMovie.getVote_average()));
            release_date.setText(mMovie.getRelease_date());
            plot_synopsis.setText(mMovie.getOverview());

            Picasso.get()
                    .load(Constants.MOVIE_POSTER_IMAGE_PATH + mMovie.getPoster_path())
                    .into(poster_image);
        }

        didUserMarkThisMovieAsFavorite();

        mark_as_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: isMarkedFavorite : " + isMarkedFavorite);

                if(mMovie != null && !isMarkedFavorite) {

                    Date date = new Date();

                    final FavoriteMovieEntry favoriteMovieEntry = new FavoriteMovieEntry(
                            mMovie.getOriginal_title(),
                            mMovie.getPoster_path(),
                            mMovie.getOverview(),
                            mMovie.getVote_average(),
                            mMovie.getRelease_date(),
                            date);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDatabase.favoriteMovieDao().insertFavoriteMovie(favoriteMovieEntry);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    isMarkedFavorite = true;
                                    setMarkAsFavoriteBtn();
                                }
                            });
                        }
                    });


                } else if(mMovie != null && isMarkedFavorite) {

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            FavoriteMovieEntry favoriteMovieEntry = mDatabase.favoriteMovieDao().getMovieByTitle(mMovie.getOriginal_title());
                            mDatabase.favoriteMovieDao().deleteFavoriteMovie(favoriteMovieEntry);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    isMarkedFavorite = false;
                                    setMarkAsFavoriteBtn();
                                }
                            });
                        }
                    });
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.temporary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.favoriteMovieDao().deleteAllData();
                    }
                });
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
