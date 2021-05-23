package user.posts.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import user.posts.java.R;
import user.posts.java.apiclients.LoginClient;
import user.posts.java.apiclients.Session;
import user.posts.java.apiclients.UserPostsClient;
import user.posts.java.apiclients.UsersClient;
import user.posts.java.concurrancy.MainThreadExecutor;
import user.posts.java.concurrancy.TaskManager;
import user.posts.java.interfaces.OnRequestCallback;
import user.posts.java.interfaces.OnRequestItemsCallback;
import user.posts.java.interfaces.SCREEN;
import user.posts.java.model.Account;
import user.posts.java.model.User;
import user.posts.java.model.UserPost;
import user.posts.java.views.RecyclerViewAdapter;

public class UserPostsFragment extends BaseFragment {

    public static final String TAG = "MobileCodingChallenge:" + UserPostsFragment.class.getSimpleName();
    private static final int UPDATE_USER_POST = 1000;
    private RecyclerView recyclerView;
    private List<User> mUsers;
    private int[] mUserIDs;
    private List<UserPost> mUserPosts = new ArrayList<>();
    private MainThreadExecutor mMainThreadExecutor;
    private Handler mHandler;
    private static int mIndex;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TaskManager mTaskManager = TaskManager.getInstance();
        mMainThreadExecutor = mTaskManager.getMainThreadExecutor();
        mHandler = mMainThreadExecutor.getHandler();
    }

    private void performUserLogin() {
        LoginClient.login("user_name", "password", new OnRequestCallback<Account>() {
            @Override
            public void onSuccess(Account response) {
                Log.i(TAG, "performUserLogin()->onSuccess(),ApiKey:" + response.getApiKey());
                Session.getInstance().setAPIKey(response.getApiKey());
                if (null == mUsers || mUsers.isEmpty() || mUserPosts.isEmpty())
                    fetchUsers();
                else
                    initUserIds();
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                Log.e(TAG, "performUserLogin()->onFailure(),throwable.getMessage():" + throwable.getMessage());
                performUserLogin();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_user_posts, container, false);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.user_posts_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getAttachedActivity());
        recyclerView.setHasFixedSize(true);

        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(recyclerView) {

            @Override
            protected View createView(Context context, ViewGroup viewGroup, int viewType) {
                return (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_posts_row, viewGroup, false);
            }

            @Override
            protected void bindView(int position, ViewHolder viewHolder) {
                UserPost userPost = mUserPosts.get(position);
                if (null != userPost) {
                    int foundIndex = Arrays.binarySearch(mUserIDs, userPost.getUserId());
                    User user = mUsers.get(foundIndex);
                    String avatar = user.getAvatar();
                    viewHolder.itemView.setTag(userPost);

                    final ImageView avatarView = (ImageView) viewHolder.getView(R.id.user_avatar);
                    if (null != avatar && !avatar.isEmpty()) {
                        Picasso.with(viewHolder.itemView.getContext())
                                .load(avatar)
                                .transform(new CropCircleTransformation())
                                .into(avatarView);
                    } else {
                        avatarView.setImageDrawable(null);
                    }

                    TextView userNameView = (TextView) viewHolder.getView(R.id.user_name);
                    userNameView.setText(user.getUsername());

                    TextView userPostTitleView = (TextView) viewHolder.getView(R.id.user_post_title);
                    userPostTitleView.setText(userPost.getTitle());

                    TextView userPostDescriptionView = (TextView) viewHolder.getView(R.id.user_post_description);
                    userPostDescriptionView.setText(userPost.getBody());
                }
            }

            @Override
            public int getItemCount() {
                return null == mUserPosts ? 0 : mUserPosts.size();
            }

            @Override
            public long getItemId(int position) {
                Object listItem = mUserPosts.get(position);
                return listItem.hashCode();
            }

            @Override
            public void onDataAvailable() {
                notifyDataSetChanged();
            }

            @Override
            public void onDataRemovedAtIndex(int index) {
                remove(index);
            }

            @Override
            public void onDataAvailableAtIndex(int index) {
                add(index);
            }
        };

        recyclerView.setAdapter(recyclerViewAdapter);
        setIOnDataAvailableListener(recyclerViewAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTopBar();
        if (null == mUsers || mUsers.isEmpty()) {
            performUserLogin();
        }
    }

    public void updateUserPostsScreen() {
        if (mUserPosts.isEmpty())
            return;

        if (null != getIOnDataAvailableListener()) {
            getIOnDataAvailableListener().onDataAvailable();
        }
    }

    private void fetchUsers() {
        UsersClient.fetchUsers(new OnRequestItemsCallback<User>() {
            @Override
            public void onSuccess(List<User> response) {
                mUsers = response;
                initUserIds();

                UserPostsDownloadTask userPostsDownloadTask = new UserPostsDownloadTask();
                mMainThreadExecutor.execute(userPostsDownloadTask);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                Log.e(TAG, "fetchUsers()->onFailure(),throwable.getMessage():" + throwable.getMessage());
                fetchUsers();
            }
        });
    }

    private class UserPostsDownloadTask extends Thread {

        @Override
        public void run() {
            fetchUserPosts();
        }

        private void fetchUserPosts() {
            synchronized (this) {
                while (null == mUserIDs || mUserIDs.length == 0) {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(mIndex == mUserIDs.length){
                return;
            }
            User user = mUsers.get(mIndex);
            UserPostsClient.fetchUserPosts(new OnRequestItemsCallback<UserPost>() {
                @Override
                public void onSuccess(List<UserPost> response) {
                    mUserPosts.addAll(response);
                    mHandler.sendEmptyMessageDelayed(UPDATE_USER_POST, 1000);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateUserPostsScreen();
                            ++mIndex;
                        }
                    });
                }

                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                    Log.e(TAG, "fetchUserPosts()->onFailure(),throwable.getMessage():" + throwable.getMessage());
                    fetchUserPosts();
                }
            }, user.getID());

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doCleanUpResources();
    }

    private void doCleanUpResources() {
        if (null != mUsers) {
            mUsers.clear();
        }
        mUsers = null;
        if (null != mUserPosts) {
            mUserPosts.clear();
        }
        mUserPosts = null;
        recyclerView.setAdapter(null);
    }

    @Override
    public String getScreenName() {
        return SCREEN.USER_POSTS.getScreenName();
    }

    @Override
    protected void updateTopBar() {
        updateToolBarTitle(getScreenName());
    }

    private void initUserIds() {
        if (null == mUsers || mUsers.isEmpty())
            return;

        mUserIDs = new int[mUsers.size()];
        int i = 0;
        for (User user : mUsers) {
            mUserIDs[i++] = user.getID();
        }
    }

}
