package ovh.blocktrron.eticked.dataset.Cards;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import ovh.blocktrron.eticked.dataset.eticket.JourneyTransaction;
import ovh.blocktrron.eticked.dataset.eticket.Transaction;

public class JourneyCard extends BaseCard implements Parcelable {
    public Transaction transactionData;
    public JourneyTransaction journeyTransactionData;

    public JourneyCard(byte[] input) {
        super(input);
        createTransaction();
        createJourneyTransaction();
    }

    protected JourneyCard(Parcel in) {
        super(in);
        rawData = in.createByteArray();
        transactionData = in.readParcelable(Transaction.class.getClassLoader());
        journeyTransactionData = in.readParcelable(JourneyTransaction.class.getClassLoader());
    }

    public static final Creator<JourneyCard> CREATOR = new Creator<JourneyCard>() {
        @Override
        public JourneyCard createFromParcel(Parcel in) {
            return new JourneyCard(in);
        }

        @Override
        public JourneyCard[] newArray(int size) {
            return new JourneyCard[size];
        }
    };

    private void createTransaction() {
        transactionData = new Transaction(Arrays.copyOfRange(rawData, 2, 31));
    }

    public void createJourneyTransaction() {
        journeyTransactionData = new JourneyTransaction(Arrays.copyOfRange(rawData, 33, 86));
    }

    public byte[] getApplicationSequenceNumber() {
        return Arrays.copyOfRange(rawData, 4, 6);
    }

    @Override
    public int describeContents() {
        return TAG_JOURNEY;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(transactionData, 1);
        dest.writeParcelable(journeyTransactionData, 1);
    }
}
