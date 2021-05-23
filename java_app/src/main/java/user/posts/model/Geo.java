package user.posts.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Geo {
    
    @SerializedName("lat")
    public String mLatitude;

    @SerializedName("lng")
    public String mLongitude;

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geo geo = (Geo) o;
        return mLatitude.equals(geo.mLatitude) &&
                mLongitude.equals(geo.mLongitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mLatitude, mLongitude);
    }

    @Override
    public String toString() {
        return "Geo{" +
                "mLatitude='" + mLatitude + '\'' +
                ", mLongitude='" + mLongitude + '\'' +
                '}';
    }
}
