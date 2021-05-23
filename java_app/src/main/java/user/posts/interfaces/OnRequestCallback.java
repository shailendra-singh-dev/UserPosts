package user.posts.interfaces;

public interface OnRequestCallback<T> {

    void onSuccess(T response);
    void onFailure(Throwable throwable);
}
