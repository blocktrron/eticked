package ovh.blocktrron.eticked.dataset.eticket;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import ovh.blocktrron.eticked.Tools;

public class PermitEFS extends Tools {
    private byte[] rawData;

    public PermitEFS(byte[] input) {
        rawData = input;
    }

    public String getCustomerName() {
        return new String(Arrays.copyOfRange(rawData, 36, 61));
    }

    public Long getPassengerGender() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 61, 62));
    }

    public Date getPassengerBirthday() {
        byte[] data = Arrays.copyOfRange(rawData, 62, 66);
        int year = Integer.parseInt(bytesToHex(Arrays.copyOfRange(data, 0, 2)));
        int month = Integer.parseInt(bytesToHex(Arrays.copyOfRange(data, 2, 3)));
        int day = Integer.parseInt(bytesToHex(Arrays.copyOfRange(data, 3, 4)));
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        return c.getTime();
    }
}
