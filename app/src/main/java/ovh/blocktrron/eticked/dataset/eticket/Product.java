package ovh.blocktrron.eticked.dataset.eticket;

import java.util.Arrays;

import ovh.blocktrron.eticked.Tools;

public class Product extends Tools {
    private byte[] rawData;

    public Product(byte[] input) {
        rawData = input;
    }

    public Long getID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 0, 2));
    }

    public Long getOrganisationID() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 2, 4));
    }
}
