package ovh.blocktrron.eticked.dataset.eticket;

import java.util.Calendar;

public class DateTimeCompact {
    private byte[] rawData;

    public DateTimeCompact(byte[] input) {
        rawData = input;
    }

    public Calendar getCalendar() {
        byte[] raw = rawData;
        byte year_b = raw[0];
        year_b = (byte) (year_b >> 1);
        year_b = (byte) (year_b & ~(1 << 7));
        int year = year_b;

        byte month_b = raw[1];
        month_b = (byte) (month_b >> 5);
        int leftByte = (raw[0] & 1);
        if (leftByte == 1) {
            month_b = (byte) (month_b | (1 << 3));
        } else {
            month_b = (byte) (month_b & ~(1 << 3));
        }
        month_b = (byte) (month_b & ~(1 << 4));
        month_b = (byte) (month_b & ~(1 << 5));
        month_b = (byte) (month_b & ~(1 << 6));
        month_b = (byte) (month_b & ~(1 << 7));
        int month = month_b;

        byte day_b = raw[1];
        day_b = (byte) (day_b & ~(1 << 7));
        day_b = (byte) (day_b & ~(1 << 6));
        day_b = (byte) (day_b & ~(1 << 5));
        int day = day_b;

        byte hour_b = raw[2];
        hour_b = (byte) (hour_b >> 3);
        hour_b = (byte) (hour_b & ~(1 << 7));
        hour_b = (byte) (hour_b & ~(1 << 6));
        hour_b = (byte) (hour_b & ~(1 << 5));
        int hour = hour_b;

        byte minute_b1 = raw[2];
        minute_b1 = (byte) (minute_b1 << 3);
        minute_b1 = (byte) (minute_b1 & ~(1 << 7));
        minute_b1 = (byte) (minute_b1 & ~(1 << 6));
        minute_b1 = (byte) (minute_b1 & ~(1 << 2));
        minute_b1 = (byte) (minute_b1 & ~(1 << 1));
        minute_b1 = (byte) (minute_b1 & ~(1));

        byte minute_b2 = raw[3];
        minute_b2 = (byte) (minute_b2 >> 5);
        minute_b2 = (byte) (minute_b2 & ~(1 << 7));
        minute_b2 = (byte) (minute_b2 & ~(1 << 6));
        minute_b2 = (byte) (minute_b2 & ~(1 << 5));
        minute_b2 = (byte) (minute_b2 & ~(1 << 4));
        minute_b2 = (byte) (minute_b2 & ~(1 << 3));

        int minute = minute_b1 + minute_b2;

        byte seconds_b = raw[3];
        seconds_b = (byte) (seconds_b & ~(1 << 7));
        seconds_b = (byte) (seconds_b & ~(1 << 6));
        seconds_b = (byte) (seconds_b & ~(1 << 5));
        int seconds = seconds_b;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, (1990 + year));
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, seconds);
        return c;
    }
}
