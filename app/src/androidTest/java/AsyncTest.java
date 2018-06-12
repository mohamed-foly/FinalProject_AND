import android.support.test.filters.SmallTest;
import android.util.Log;

import com.example.mohamed.builditbigger.EndpointsAsyncTask;
import com.example.mohamed.builditbigger.OnTaskCompleted;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@SmallTest
public class AsyncTest implements OnTaskCompleted {

    private CountDownLatch signal = null;
    private String async_result ;

    @Before
    public void setUp() {
        signal = new CountDownLatch(1);
    }

    @Test
    public void testAsyncTaskTest() throws InterruptedException {

        new EndpointsAsyncTask(this).execute();
        signal.await();
        Log.e("AsyncTask",async_result);
        Assert.assertNotEquals("",async_result);
        Assert.assertNotEquals("connect timed out",async_result);
        Assert.assertNotNull(async_result);

    }
    @Override
    public void onTaskCompletedListener(String result) {
        async_result = result;
        signal.countDown();
    }

}