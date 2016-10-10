package ovh.blocktrron.eticked.dataset.eticket;

import java.util.Arrays;
import java.util.HashMap;

import ovh.blocktrron.eticked.Tools;

public class LinieVariant extends Tools {
    private byte[] rawData;
    public static final HashMap<Integer, String> route_names;

    static {
        route_names = new HashMap<>();
        route_names.put(678, "678");
        route_names.put(720, "O");
        route_names.put(724, "R");
        route_names.put(753, "NE");
        route_names.put(722, "P");
        route_names.put(738, "WX");
        route_names.put(726, "K");
        route_names.put(556, "K 56");
        route_names.put(730, "H");
        route_names.put(736, "F");
        route_names.put(550, "K 50");
        route_names.put(555, "K 55");
        route_names.put(734, "U");
        route_names.put(515, "5515");
        route_names.put(516, "5516");
    }

    public LinieVariant(byte[] input) {
        rawData = input;
    }

    public Long getRoute() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 0, 2));
    }

    public String getRouteName() {
        return route_names.get(this.getRoute() != null ? this.getRoute().intValue() : null);
    }

    public Long getVariant() {
        return byteArrayToLong(Arrays.copyOfRange(rawData, 2, 3));
    }
}
