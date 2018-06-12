import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.filters.SmallTest;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


@SmallTest
public class AsyncTest  {

    private CountDownLatch signal = null;
    private String async_result ;






    @Before
    public void setUp() {
        signal = new CountDownLatch(1);
    }

    @After
    public void tearDown() {
        signal.countDown();
    }

    @Test
    public void testAsyncTaskTest() throws InterruptedException {

        new EndpointsAsyncTask().execute();
        signal.await();
        Log.e("AsyncTask",async_result);
        Assert.assertNotEquals("",async_result);
        Assert.assertNotEquals("connect timed out",async_result);
        Assert.assertNotNull(async_result);

    }



    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
        private MyApi myApiService = null;

        @Override
        protected String doInBackground(Context... params) {
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

        @Test
        @Override
        protected void onPostExecute(String result) {
            async_result = result;
            signal.countDown();
        }
}


}