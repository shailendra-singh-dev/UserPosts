package user.posts.concurrancy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class for putting multiple request into queue .
 */
public class TaskManager {

    private final ThreadPoolExecutor downloadThreadPool;

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 50;

    private static TaskManager downloadManager = null;
    private static MainThreadExecutor mMainThreadExecutor;

    static {
        downloadManager = new TaskManager();
        mMainThreadExecutor = new MainThreadExecutor();
    }

    private TaskManager() {
        BlockingQueue<Runnable> downloadWorkQueue = new LinkedBlockingQueue<Runnable>();

        downloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, downloadWorkQueue);
    }

    public static TaskManager getInstance() {
        return downloadManager;
    }

    public void run(Runnable task) {
        downloadThreadPool.execute(task);
    }

    //to runs task on main thread from background thread
    public MainThreadExecutor getMainThreadExecutor() {
        return mMainThreadExecutor;
    }

    public void doCleanUp(){
        downloadThreadPool.shutdown();
        mMainThreadExecutor.doCleanUp();
    }
}
