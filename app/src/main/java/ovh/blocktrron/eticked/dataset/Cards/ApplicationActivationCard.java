package ovh.blocktrron.eticked.dataset.Cards;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import ovh.blocktrron.eticked.dataset.eticket.Transaction;

public class ApplicationActivationCard extends BaseCard implements Parcelable {
    public Transaction transactionData;

    public ApplicationActivationCard(byte[] input) {
        super(input);
        transactionData = new Transaction(Arrays.copyOfRange(rawData, 2, 32));
    }

    protected ApplicationActivationCard(Parcel in) {
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
        return TAG_ACTIVATION_CARD;
    }

    public static final Creator<ApplicationActivationCard> CREATOR = new Creator<ApplicationActivationCard>() {
        @Override
        public ApplicationActivationCard createFromParcel(Parcel in) {
            return new ApplicationActivationCard(in);
        }

        @Override
        public ApplicationActivationCard[] newArray(int size) {
            return new ApplicationActivationCard[size];
        }
    };
}
