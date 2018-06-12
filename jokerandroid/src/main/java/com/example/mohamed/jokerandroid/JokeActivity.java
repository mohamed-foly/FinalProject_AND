package com.example.mohamed.jokerandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    String JOKE_KEY = "joke";
    TextView JokeTv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        JokeTv = findViewById(R.id.joke_tv);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
           if( intent.hasExtra(JOKE_KEY)){
               String joke =  bundle.getString(JOKE_KEY);
               JokeTv.setText(joke);
           }
        }
    }
}
