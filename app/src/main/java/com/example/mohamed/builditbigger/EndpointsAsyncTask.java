package com.example.mohamed.builditbigger;


import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private OnTaskCompleted onTaskCompleted ;
    public EndpointsAsyncTask(OnTaskCompleted onTaskCompleted){
        this.onTaskCompleted = onTaskCompleted;
    }
    private static MyApi myApiService = null;
    @Override
    protected String doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.1.10:8085/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }
        try {
            return myApiService.joke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        onTaskCompleted.onTaskCompletedListener(result);
    }
}