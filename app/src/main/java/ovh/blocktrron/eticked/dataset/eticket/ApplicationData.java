package ovh.blocktrron.eticked.dataset.eticket;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class ApplicationData implements Parcelable {
    protected byte[] rawData;
    protected StaticData staticData;

    protected ApplicationData(Parcel in) {
        rawData = in.createByteArray();
        generateObject();
    }

    public ApplicationData(byte[] in) {
        rawData = in;
        generateObject();
    }

    private void generateObject() {
        staticData = new StaticData(Arrays.copyOfRange(rawData, 5, 23));
    }

    public StaticData getStaticData() {
        return staticData;
    }

    public static final Creator<ApplicationData> CREATOR = new Creator<ApplicationData>() {
        @Override
        public ApplicationData createFromParcel(Parcel in) {
            return new ApplicationData(in);
        }

        @Override
        public ApplicationData[] newArray(int size) {
            return new ApplicationData[size];
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

        public ApplicationinstanceID getApplikationInstanzID() {
            return new ApplicationinstanceID(Arrays.copyOfRange(rawData, 2, 8));
        }

        public int getVersion() {
            return rawData[8];
        }

        public DateTimeCompact getValidFrom() {
            return new DateTimeCompact(Arrays.copyOfRange(rawData, 9, 13));
        }

        public DateTimeCompact getValidThru() {
            return new DateTimeCompact(Arrays.copyOfRange(rawData, 13, 17));
        }
    }
}
