package user.posts.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Account {

    @SerializedName("api_key")
    private String mApiKey;

    public String getApiKey() {
        return mApiKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return mApiKey.equals(account.mApiKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mApiKey);
    }

    @Override
    public String toString() {
        return "Account{" +
                "mApiKey='" + mApiKey + '\'' +
                '}';
    }
}
