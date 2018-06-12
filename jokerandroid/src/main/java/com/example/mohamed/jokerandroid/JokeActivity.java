package com.example.mohamed.jokerandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    TextView JokeTv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        JokeTv = findViewById(R.id.joke_tv);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String joke =  bundle.getString("joke");
            JokeTv.setText(joke);
        }
    }
}
