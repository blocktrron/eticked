package ovh.blocktrron.eticked.dataset.eticket;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import ovh.blocktrron.eticked.Tools;

public class Transaction extends Tools implements Parcelable {
    private byte[] rawData;
    public Location location;

    public Transaction(byte[] input) {
        rawData = input;
        location = createLocation();
    }

    protected Transaction(Parcel in) {
        rawData = in.createByteArray();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    private Location createLocation() {
        return new Location(Arrays.copyOfRange(rawData, 22, 28));
    }

    public Long getApplicationSequenceNumber() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 2, 4));
    }

    public TransactionID getTransactionID() {
        return new TransactionID(Arrays.copyOfRange(rawData, 4, 11));
    }

    public Long getOrganisationID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 12, 14));
    }

    public TerminalID getTerminalID() {
        return new TerminalID(Arrays.copyOfRange(rawData, 13, 18));
    }

    public DateTimeCompact getDateTime() {
        return new DateTimeCompact(Arrays.copyOfRange(rawData, 18, 22));
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(rawData);
        dest.writeParcelable(location, 1);
    }
}
