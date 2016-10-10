package ovh.blocktrron.eticked.dataset.Cards;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageCard extends BaseCard implements Parcelable {
    protected String message;

    protected MessageCard(byte[] input) {
        super(input);
    }

    protected MessageCard(Parcel in) {
        super(in);
        message = in.readString();
    }

    public MessageCard(String input) {
        super();
        message = input;
    }

    public String getMessage() {
        if (message != null) {
            return message;
        }
        return "";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return TAG_MESSAGE;
    }

    public static final Creator<MessageCard> CREATOR = new Creator<MessageCard>() {
        @Override
        public MessageCard createFromParcel(Parcel in) {
            return new MessageCard(in);
        }

        @Override
        public MessageCard[] newArray(int size) {
            return new MessageCard[size];
        }
    };
}
