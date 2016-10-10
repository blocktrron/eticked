package ovh.blocktrron.eticked.dataset.eticket;

import java.util.Arrays;

import ovh.blocktrron.eticked.Tools;

public class TerminalID extends Tools {
    private byte[] rawData;

    public TerminalID(byte[] input) {
        rawData = input;
    }

    public Long getType() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 0, 1));
    }

    public Long getID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 1, 3));
    }

    public Long getOrganisationID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 3, 5));
    }
}
