package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";

    private TextView release_date, vote_average, plot_synopsis;
    private ImageView poster_image;

    private Movie mPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getIntentFromMainActivity();
        initUI();
    }

    private void getIntentFromMainActivity() {

        if(getIntent().hasExtra(getString(R.string.movie))) {
            mPoster = getIntent().getParcelableExtra(getString(R.string.movie));
            Log.d(TAG, "onCreate: poster : " + mPoster.toString());
        }

    }

    private void initUI() {

        release_date = findViewById(R.id.release_date);
        vote_average = findViewById(R.id.vote_average);
        plot_synopsis = findViewById(R.id.plot_synopsis);
        poster_image = findViewById(R.id.poster_image);

        if(mPoster != null) {
            setTitle(mPoster.getOriginal_title());
            vote_average.setText(String.valueOf(mPoster.getUser_rating()));
            release_date.setText(mPoster.getRelease_date());
            plot_synopsis.setText(mPoster.getPlot_synopsis());

            Picasso.get()
                    .load(Constants.MOVIE_POSTER_IMAGE_PATH + mPoster.getThumbnail())
                    .into(poster_image);
        }


    }
}
