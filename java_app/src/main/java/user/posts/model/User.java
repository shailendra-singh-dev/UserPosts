package user.posts.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class User {

    @SerializedName("id")
    public int mID;

    @SerializedName("avatar")
    public String mAvatar;

    @SerializedName("name")
    public String mName;

    @SerializedName("username")
    public String mUsername;

    @SerializedName("email")
    public String mEmail;

    @SerializedName("address")
    public Address mAddress;

    @SerializedName("phone")
    public String mPhone;

    @SerializedName("website")
    public String mWebsite;

    @SerializedName("company")
    public Company mCompany;

    public int getID() {
        return mID;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }

    public Address getAddress() {
        return mAddress;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public Company getCompany() {
        return mCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return mID == user.mID &&
                mAvatar.equals(user.mAvatar) &&
                mName.equals(user.mName) &&
                mUsername.equals(user.mUsername) &&
                mEmail.equals(user.mEmail) &&
                mAddress.equals(user.mAddress) &&
                mPhone.equals(user.mPhone) &&
                mWebsite.equals(user.mWebsite) &&
                mCompany.equals(user.mCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mID, mAvatar, mName, mUsername, mEmail, mAddress, mPhone, mWebsite, mCompany);
    }

    @Override
    public String toString() {
        return "User{" +
                "mID=" + mID +
                ", mAvatar='" + mAvatar + '\'' +
                ", mName='" + mName + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mAddress=" + mAddress +
                ", mPhone='" + mPhone + '\'' +
                ", mWebsite='" + mWebsite + '\'' +
                ", mCompany=" + mCompany +
                '}';
    }
}

