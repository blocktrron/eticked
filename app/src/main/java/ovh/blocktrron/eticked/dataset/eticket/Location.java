package ovh.blocktrron.eticked.dataset.eticket;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import ovh.blocktrron.eticked.Tools;

public class Location extends Tools implements Parcelable {
    private byte[] rawData;
    private String locationName;

    public Location(byte[] input) {
        rawData = input;
    }

    protected Location(Parcel in) {
        rawData = in.createByteArray();
        locationName = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public Long getType() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 0, 1));
    }

    public Long getID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 1, 4));
    }

    public Long getOrganisationID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 4, 6));
    }

    public String getName() {
        return locationName;
    }

    public void setName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(rawData);
        dest.writeString(locationName);
    }
}
