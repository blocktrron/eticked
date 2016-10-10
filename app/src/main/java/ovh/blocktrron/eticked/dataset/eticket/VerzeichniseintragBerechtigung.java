package ovh.blocktrron.eticked.dataset.eticket;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class VerzeichniseintragBerechtigung implements Parcelable {
    private byte[] rawData;

    public VerzeichniseintragBerechtigung(byte[] in) {
        rawData = in;
    }

    protected VerzeichniseintragBerechtigung(Parcel in) {
        this(in.createByteArray());
    }

    public int getPointer() {
        return rawData[4];
    }

    public StaticData getStaticData() {
        return new StaticData(Arrays.copyOfRange(rawData, 5, 27));
    }

    public static final Creator<VerzeichniseintragBerechtigung> CREATOR = new Creator<VerzeichniseintragBerechtigung>() {
        @Override
        public VerzeichniseintragBerechtigung createFromParcel(Parcel in) {
            return new VerzeichniseintragBerechtigung(in);
        }

        @Override
        public VerzeichniseintragBerechtigung[] newArray(int size) {
            return new VerzeichniseintragBerechtigung[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(rawData);
    }

    public class StaticData {
        private byte[] rawData;

        public StaticData(byte[] in) {
            rawData = in;
        }

        public Permit getPermit() {
            return new Permit(Arrays.copyOfRange(rawData, 2, 8));
        }

        public Product getProduct() {
            return new Product(Arrays.copyOfRange(rawData, 8, 12));
        }

        public DateTimeCompact getValidFrom() {
            return new DateTimeCompact(Arrays.copyOfRange(rawData, 14, 18));
        }

        public DateTimeCompact getValidEnd() {
            return new DateTimeCompact(Arrays.copyOfRange(rawData, 18, 22));
        }
    }
}
