package ovh.blocktrron.eticked.dataset.eticket;

import java.util.Arrays;

import ovh.blocktrron.eticked.Tools;

public class ApplicationinstanceID {
    private byte[] rawData;

    public ApplicationinstanceID(byte[] in) {
        rawData = in;
    }

    public long getAppInstanz() {
        return Tools.byteArrayToLong(Arrays.copyOfRange(rawData, 0, 4));
    }

    public long getOrganisation() {
        return Tools.byteArrayToLong(Arrays.copyOfRange(rawData, 4, 6));
    }
}
