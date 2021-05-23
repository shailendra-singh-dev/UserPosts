package user.posts.concurrancy;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Thread pool executor
 */
public class MainThreadExecutor implements Executor, Handler.Callback {

    private final Handler handler;
    private Runnable mRunnable;

    {
        handler = new Handler(Looper.getMainLooper(), this);
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        if (null == mRunnable)
            mRunnable = runnable;

        handler.post(runnable);
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.what == 1000) {
            handler.post(mRunnable);
        }
        return false;
    }

    public void doCleanUp() {
        handler.removeCallbacks(mRunnable);
        handler.removeMessages(1000);
    }
}
