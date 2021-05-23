package user.posts.interfaces;

import java.util.List;

public interface OnRequestItemsCallback<T> {
    void onSuccess(List<T> response);

    void onFailure(Throwable throwable);
}
