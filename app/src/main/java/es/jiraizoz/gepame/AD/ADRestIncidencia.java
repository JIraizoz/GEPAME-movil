package es.jiraizoz.gepame.AD;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.jiraizoz.gepame.LD.Incidencia;

/**
 * Created by Jesus on 11/05/2018.
 */

public class ADRestIncidencia extends AsyncTask<Void, Void, ArrayList<Incidencia>> {

    private ArrayList<Incidencia> lIncidencia;
    private static final String URL = "http://gepame.jiraizoz.es/api/Incidencias";

    @Override
    protected ArrayList<Incidencia> doInBackground(Void... voids) {
        try {
            URL gepameEndpoint = new URL(URL);

            HttpURLConnection connection = (HttpURLConnection) gepameEndpoint.openConnection();

            connection.setRequestProperty("User-Agent", "gepame-mobile");

            if (connection.getResponseCode() == 200) {
                InputStream resp = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(resp, "UTF-8");

                JsonParser parser = new JsonParser();
                JsonArray arr = parser.parse(isr).getAsJsonArray();

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create(); // your format
//                AndroidNetworking.setParserFactory(new GsonParserFactory(gson));
                lIncidencia = new ArrayList<Incidencia>();

                for (JsonElement je : arr) {
                    Incidencia i = gson.fromJson(je,Incidencia.class);
                    i.setUtm(i.getUtm().replace("(",""));
                    i.setUtm(i.getUtm().replace(")",""));
                    lIncidencia.add(i);
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.lIncidencia;
    }
}
