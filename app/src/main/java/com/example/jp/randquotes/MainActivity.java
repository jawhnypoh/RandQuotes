package com.example.jp.randquotes;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jp.randquotes.utilities.RandQuotesUtils;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity: ";

    private TextView quoteText, authorText;

    private static final String QUOTES_SEARCH_KEY = "quotesSearchURL";

    public int RANDQUOTES_SEARCH_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        final RelativeLayout relativeLayout = findViewById(R.id.bg_layout);

        doQuotesSearch();

        relativeLayout.setBackgroundColor(getRandomColor());


        Button refreshButton = findViewById(R.id.refresh_button);
        refreshButton.setTypeface(fontAwesomeFont);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button pressed! ");

                // Make API call again for another quote
                doQuotesSearch();

                // Change background color to random color
                relativeLayout.setBackgroundColor(getRandomColor());
            }
        });

        Button aboutButton = findViewById(R.id.about_button);
        aboutButton.setTypeface(fontAwesomeFont);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to About Activity
                goToAboutActivity();
            }
        });

    }

    private void doQuotesSearch() {
        quoteText = (TextView)findViewById(R.id.quote_text);
        authorText = (TextView)findViewById(R.id.quote_author);

        RandQuotesUtils.placeIdTask asyncTask = new RandQuotesUtils.placeIdTask(new RandQuotesUtils.AsyncResponse() {

            public void processFinish(String quote, String author) {

                quoteText.setText(quote);
                authorText.setText(author);
            }
        });

        asyncTask.execute();
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void goToAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);

        startActivity(intent);
    }
}
