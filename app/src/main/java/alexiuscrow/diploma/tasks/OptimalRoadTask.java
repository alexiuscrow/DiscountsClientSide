package alexiuscrow.diploma.tasks;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import alexiuscrow.diploma.Settings;
import alexiuscrow.diploma.entity.Discounts;
import alexiuscrow.diploma.entity.Shops;
import alexiuscrow.diploma.tasks.criteria.SearchCriteria;
import alexiuscrow.diploma.util.geo.RouteApi;
import alexiuscrow.diploma.util.geo.RouteResponse;
import retrofit.RestAdapter;

/**
 * Created by Alexiuscrow on 25.04.2015.
 */

public class OptimalRoadTask extends AsyncTask<SearchCriteria, Void, RouteResponse> {
    Callback listener;

    public OptimalRoadTask(Callback listener){
        this.listener = listener;
    }

    @Override
    protected RouteResponse doInBackground(SearchCriteria... criterias) {
        if (criterias.length != 2) return null;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://maps.googleapis.com")
                .build();
        RouteApi routeService = restAdapter.create(RouteApi.class);

        String position = String.valueOf(criterias[0].getLat()) + "," + String.valueOf(criterias[0].getLng());
        String destination = String.valueOf(criterias[1].getLat()) + "," + String.valueOf(criterias[1].getLng());

        RouteResponse routeResponse = routeService.getRoute(position, destination, true, "ua", "WALKING");
        return routeResponse;
    }

    @Override
    protected void onPostExecute(RouteResponse routeResponse) {
        if (routeResponse == null) return;

        listener.onTaskCompleted(routeResponse);
    }

    public interface Callback {
        void onTaskCompleted(RouteResponse routeResponse);
    }
}