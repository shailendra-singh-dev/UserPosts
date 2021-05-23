package user.posts.views;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import user.posts.java.ui.BaseFragment;

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements ViewAdapter, BaseFragment.IOnDataAvailableListener {

    private final RecyclerView mRecyclerView;

    public RecyclerViewAdapter(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public Context getContext() {
        return mRecyclerView.getContext();
    }

    @Override
    public int getWidth() {
        return mRecyclerView.getWidth();
    }

    @Override
    public int getChildCount() {
        return mRecyclerView.getChildCount();
    }

    @Override
    public View getChildAt(int index) {
        return mRecyclerView.getChildAt(index);
    }

    public void add(int position) {
        notifyItemInserted(position);
    }

    public void remove(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public int getChildPosition(View child) {
        return mRecyclerView.getChildPosition(child);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Map<Integer, View> mMapView;

        public ViewHolder(View view) {
            super(view);
            mMapView = new HashMap<>();
            mMapView.put(0, view);
        }

        public void initViewList(int[] idList) {
            for (int id : idList)
                initViewById(id);
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                mMapView.put(id, view);
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (mMapView.containsKey(id))
                return mMapView.get(id);
            else
                initViewById(id);

            return mMapView.get(id);
        }
    }

    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView( int position, ViewHolder viewHolder);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(createView(getContext(), viewGroup, viewType));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        bindView(position, viewHolder);
    }

}
