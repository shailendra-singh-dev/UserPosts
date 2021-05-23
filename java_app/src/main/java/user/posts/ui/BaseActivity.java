package user.posts.ui;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import user.posts.java.R;

public class BaseActivity extends AppCompatActivity implements Handler.Callback {

    private static final int PROGRESS_SHOW_MSG = 1000;
    private static final int PROGRESS_HIDE_MSG = PROGRESS_SHOW_MSG + 1;

    private Dialog mProgressDialog;
    private Handler mMessageHandler = null;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageHandler = new Handler(getMainLooper(), this);
        initToolBar();
        initLoadingView();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initLoadingView() {
        ProgressBar progressBar = new ProgressBar(this);
        int padding = getResources().getDimensionPixelOffset(R.dimen.dialog_default_padding);
        progressBar.setPadding(padding, padding, padding, padding);

        mProgressDialog = new Dialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setContentView(progressBar);
        int color = ContextCompat.getColor(this, R.color.loading_color);
        final ColorDrawable loadingBackgroundColor = new ColorDrawable(color);
        mProgressDialog.getWindow().setBackgroundDrawable(loadingBackgroundColor);
    }

    public Dialog getProgressDialog() {
        return mProgressDialog;
    }

    protected void progressShow(long delay) {
        mMessageHandler.sendEmptyMessageDelayed(PROGRESS_SHOW_MSG, delay);
    }

    protected void progressHide() {
        mMessageHandler.removeMessages(PROGRESS_SHOW_MSG);
        mMessageHandler.sendEmptyMessage(PROGRESS_HIDE_MSG);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case PROGRESS_SHOW_MSG:
                mProgressDialog.show();
                break;

            case PROGRESS_HIDE_MSG:
                mProgressDialog.dismiss();
                break;
        }
        return false;
    }

    public void setToolBarTitle(final String title) {
        mTitle.setText(title);
    }
}
