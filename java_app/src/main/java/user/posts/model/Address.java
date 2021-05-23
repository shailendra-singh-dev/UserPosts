package user.posts.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Address {

    @SerializedName("street")
    public String mStreet;

    @SerializedName("suite")
    public String mSuite;

    @SerializedName("city")
    public String mCity;

    @SerializedName("zipcode")
    public String mZipcode;

    @SerializedName("geo")
    public Geo geo;

    public String getStreet() {
        return mStreet;
    }

    public String getSuite() {
        return mSuite;
    }

    public String getCity() {
        return mCity;
    }

    public String getZipcode() {
        return mZipcode;
    }

    public Geo getGeo() {
        return geo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return mStreet.equals(address.mStreet) &&
                mSuite.equals(address.mSuite) &&
                mCity.equals(address.mCity) &&
                mZipcode.equals(address.mZipcode) &&
                geo.equals(address.geo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mStreet, mSuite, mCity, mZipcode, geo);
    }

    @Override
    public String toString() {
        return "Address{" +
                "mStreet='" + mStreet + '\'' +
                ", mSuite='" + mSuite + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mZipcode='" + mZipcode + '\'' +
                ", geo=" + geo +
                '}';
    }
}
