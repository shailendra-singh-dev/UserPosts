package user.posts.ui;

import android.app.Dialog;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public abstract class BaseFragment extends Fragment {

    public interface IOnDataAvailableListener {
        void onDataAvailableAtIndex(int index);

        void onDataAvailable();

        void onDataRemovedAtIndex(int index);
    }

    private IOnDataAvailableListener mIOnDataAvailableListener;

    private BaseActivity mActivity;

    public void setIOnDataAvailableListener(IOnDataAvailableListener onDataAvailableListener) {
        mIOnDataAvailableListener = onDataAvailableListener;
    }

    protected IOnDataAvailableListener getIOnDataAvailableListener() {
        return mIOnDataAvailableListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected BaseActivity getAttachedActivity() {
        return mActivity;
    }

    public Dialog getProgressDialog() {
        return mActivity.getProgressDialog();
    }

    protected void progressShow(long delay) {
        mActivity.progressShow(delay);
    }

    protected void progressHide() {
        mActivity.progressHide();
    }

    @Override
    public void onPause() {
        super.onPause();
        progressHide();
    }

    protected void updateToolBarTitle(final String title) {
        mActivity.setToolBarTitle(title);
    }

    abstract protected String getScreenName();

    abstract protected void updateTopBar();
}
