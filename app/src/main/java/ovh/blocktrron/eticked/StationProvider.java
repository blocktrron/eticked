package ovh.blocktrron.eticked;

import android.content.Context;
import android.os.AsyncTask;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;


import ovh.blocktrron.eticked.dataset.eticket.Station;

public class StationProvider {
    interface OnStationResultListener {
        void onStationResult(Station station);
    }
    private OnStationResultListener listener;

    public StationProvider(OnStationResultListener listener) {
        this.listener = listener;
    }

    private String buildRequestString(int id) {
        String request_string_format = "http://www.rmv.de/auskunft/bin/jp/ajax-getstop.exe/dny" +
                "?start=1" +
                "&tpl=suggest2json" +
                "&REQ0JourneyStopsS0A=1" +
                "&getstop=1" +
                "&noSession=yes" +
                "&REQ0JourneyStopsB=1" +
                "&REQ0JourneyStopsS0G=%d";
        return String.format(request_string_format, id);
    }

    public void getStation(int id) {
        id += 3000000;

        new AsyncTask<Integer, Integer, Station>() {
            @Override
            protected Station doInBackground(Integer... integers) {
                String request_string = buildRequestString(integers[0]);

                try {
                    String response_body = HttpRequest.get(request_string).body();
                    String response_json = response_body.substring(8, response_body.length()-22);

                    JSONObject root_obj = new JSONObject(response_json);
                    JSONObject first_result = root_obj.getJSONArray("suggestions").getJSONObject(0);

                    String station_name = first_result.getString("value");

                    Station station = new Station();
                    station.setId(integers[0]);
                    station.setName(station_name);
                    return station;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                } catch (HttpRequest.HttpRequestException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            protected void onPostExecute(Station station) {
                listener.onStationResult(station);
            }
        }.execute(id);
    }
}
