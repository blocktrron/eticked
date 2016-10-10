package ovh.blocktrron.eticked.dataset.Cards;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseCard implements Parcelable {
    public static final int TAG_BASE_CARD = 0x00;
    public static final int TAG_JOURNEY = 0xF1;
    public static final int TAG_ACTIVATION_TICKET = 0xF6;
    public static final int TAG_ACTIVATION_CARD = 0xF7;
    public static final int TAG_MESSAGE = 0xE1;
    public static final int TAG_APPDETAIL = 0xE2;

    protected byte[] rawData;

    protected BaseCard(byte[] input) {
        rawData = input;
    }

    protected BaseCard() {

    }

    protected BaseCard(Parcel in) {
        rawData = in.createByteArray();
    }

    public static final Creator<BaseCard> CREATOR = new Creator<BaseCard>() {
        @Override
        public BaseCard createFromParcel(Parcel in) {
            return new BaseCard(in);
        }

        @Override
        public BaseCard[] newArray(int size) {
            return new BaseCard[size];
        }
    };

    @Override
    public int describeContents() {
        return TAG_BASE_CARD;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(rawData);
    }
}
