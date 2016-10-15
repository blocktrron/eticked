package ovh.blocktrron.eticked;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ovh.blocktrron.eticked.dataset.eticket.Station;

public class StationProvider {
    private JSONArray jsonArray;

    public StationProvider(Context context) {
        String inputString = getInputString(context);
        jsonArray = parseInputStringToJSON(inputString);
    }

    private String getInputString(Context context) {
        BufferedReader bufferedReader = null;
        String outputString = "";
        try {
            InputStream stream = context.getAssets().open(context.getString(R.string.asset_stationjson));
            bufferedReader = new BufferedReader(new InputStreamReader(stream));
            int buffersize = stream.available();
            char[] charBuff = new char[buffersize];
            while (bufferedReader.read(charBuff, 0, buffersize) != -1) {
                outputString += String.copyValueOf(charBuff, 0, buffersize);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return outputString;
    }

    private JSONArray parseInputStringToJSON(String inputString) {
        try {
            return new JSONArray(inputString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Station getStation(int id) {
        id += 3000000;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = null;
            try {
                o = jsonArray.getJSONObject(i);
                if (o.getInt("id") == id) {
                    Station station = new Station();
                    station.setId(o.getInt("id"));
                    station.setName(o.getString("name"));
                    station.setX(o.getInt("x"));
                    station.setY(o.getInt("y"));
                    return station;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
