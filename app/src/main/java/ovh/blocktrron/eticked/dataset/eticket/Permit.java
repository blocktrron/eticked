package ovh.blocktrron.eticked.dataset.eticket;

import java.util.Arrays;

import ovh.blocktrron.eticked.Tools;

public class Permit extends Tools {
    private byte[] rawData;

    public Permit(byte[] input) {
        rawData = input;
    }

    public Long getID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 0, 4));
    }

    public Long getOrganisationID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 4, 6));
    }
}
