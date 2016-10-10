package ovh.blocktrron.eticked.dataset.eticket;

import java.util.Arrays;

import ovh.blocktrron.eticked.Tools;

public class TransactionID extends Tools {
    private byte[] rawData;

    public TransactionID(byte[] input) {
        rawData = input;
    }

    public Long getSamSequenceNumber() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 0, 4));
    }

    public Long getSamID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 4, 7));
    }
}
