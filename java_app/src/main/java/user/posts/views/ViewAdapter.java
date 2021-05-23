package user.posts.views;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;


public interface ViewAdapter {
    Context getContext();
    int getWidth();
    int getChildCount();
    View getChildAt(int index);
    int getChildPosition(View position);
}
