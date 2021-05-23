package user.posts.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class UserPost {

    @SerializedName("userId")
    public int mUserId;

    @SerializedName("id")
    public int mID;

    @SerializedName("title")
    public String mTitle;

    @SerializedName("body")
    public String mBody;

    public int getUserId() {
        return mUserId;
    }

    public int getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPost userPost = (UserPost) o;
        return mUserId == userPost.mUserId &&
                mID == userPost.mID &&
                mTitle.equals(userPost.mTitle) &&
                mBody.equals(userPost.mBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUserId, mID, mTitle, mBody);
    }

    @Override
    public String toString() {
        return "UserPost{" +
                "mUserId=" + mUserId +
                ", mID=" + mID +
                ", mTitle='" + mTitle + '\'' +
                ", mBody='" + mBody + '\'' +
                '}';
    }
}
