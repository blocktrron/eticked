package ovh.blocktrron.eticked.dataset.eticket;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import ovh.blocktrron.eticked.Tools;

public class JourneyTransaction extends Tools implements Parcelable {
    private byte[] rawData;

    public JourneyTransaction(byte[] input) {
        rawData = input;
    }

    protected JourneyTransaction(Parcel in) {
        rawData = in.createByteArray();
    }

    public static final Creator<JourneyTransaction> CREATOR = new Creator<JourneyTransaction>() {
        @Override
        public JourneyTransaction createFromParcel(Parcel in) {
            return new JourneyTransaction(in);
        }

        @Override
        public JourneyTransaction[] newArray(int size) {
            return new JourneyTransaction[size];
        }
    };

    public Long getPermitSequenceNumber() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 2, 4));
    }

    public Permit getPermitID() {
        return new Permit(Arrays.copyOfRange(rawData, 4, 10));
    }

    public Product getProductID() {
        return new Product(Arrays.copyOfRange(rawData, 10, 14));
    }

    public LinieVariant getRouteVariant() {
        return new LinieVariant(Arrays.copyOfRange(rawData, 14, 17));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(rawData);
    }
}
