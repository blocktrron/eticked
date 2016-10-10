package ovh.blocktrron.eticked.dataset.Cards;

import android.os.Parcel;
import android.os.Parcelable;

import ovh.blocktrron.eticked.dataset.eticket.ApplicationData;

public class ApplicationDetailsCard extends BaseCard implements Parcelable {
    protected ApplicationDetailsCard() {

    }

    public ApplicationDetailsCard(byte[] in) {
        this();
        rawData = in;
    }

    protected ApplicationDetailsCard(Parcel in) {
        super(in);
    }

    public ApplicationData getApplicationData() {
        return new ApplicationData(rawData);
    }

    @Override
    public int describeContents() {
        return TAG_APPDETAIL;
    }

    public static final Creator<ApplicationDetailsCard> CREATOR = new Creator<ApplicationDetailsCard>() {
        @Override
        public ApplicationDetailsCard createFromParcel(Parcel in) {
            return new ApplicationDetailsCard(in);
        }

        @Override
        public ApplicationDetailsCard[] newArray(int size) {
            return new ApplicationDetailsCard[size];
        }
    };
}
