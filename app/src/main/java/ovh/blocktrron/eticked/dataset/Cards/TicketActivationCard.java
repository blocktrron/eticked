package ovh.blocktrron.eticked.dataset.Cards;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import ovh.blocktrron.eticked.dataset.eticket.Transaction;

public class TicketActivationCard extends BaseCard implements Parcelable {
    public Transaction transactionData;

    public TicketActivationCard(byte[] input) {
        super(input);
        transactionData = new Transaction(Arrays.copyOfRange(rawData, 2, 32));
    }

    protected TicketActivationCard(Parcel in) {
        super(in);
        transactionData = in.readParcelable(Transaction.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(transactionData, flags);
    }

    @Override
    public int describeContents() {
        return TAG_ACTIVATION_TICKET;
    }

    public static final Creator<TicketActivationCard> CREATOR = new Creator<TicketActivationCard>() {
        @Override
        public TicketActivationCard createFromParcel(Parcel in) {
            return new TicketActivationCard(in);
        }

        @Override
        public TicketActivationCard[] newArray(int size) {
            return new TicketActivationCard[size];
        }
    };
}
