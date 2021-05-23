package user.posts.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Company {

    @SerializedName("name")
    public String mName;

    @SerializedName("catchPhrase")
    public String mCatchPhrase;

    @SerializedName("bs")
    public String mBS;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(mName, company.mName) &&
                Objects.equals(mCatchPhrase, company.mCatchPhrase) &&
                Objects.equals(mBS, company.mBS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mCatchPhrase, mBS);
    }

    @Override
    public String toString() {
        return "Company{" +
                "mName='" + mName + '\'' +
                ", mCatchPhrase='" + mCatchPhrase + '\'' +
                ", mBS='" + mBS + '\'' +
                '}';
    }
}
