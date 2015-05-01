package alexiuscrow.diploma.tasks;

import android.os.AsyncTask;

import alexiuscrow.diploma.tasks.criteria.SearchCriteria;
import alexiuscrow.diploma.util.geo.RouteApi;
import alexiuscrow.diploma.util.geo.RouteResponse;
import alexiuscrow.diploma.util.geo.TravelMode;
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
        if (criterias.length != 2 ) return null;
        TravelMode mode = TravelMode.DRIVING;
        if (criterias[0].hasMode())
            mode = criterias[0].getMode();
        else if (criterias[1].hasMode())
            mode = criterias[1].getMode();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://maps.googleapis.com")
                .build();
        RouteApi routeService = restAdapter.create(RouteApi.class);

        String position = String.valueOf(criterias[0].getLat()) + "," + String.valueOf(criterias[0].getLng());
        String destination = String.valueOf(criterias[1].getLat()) + "," + String.valueOf(criterias[1].getLng());

        RouteResponse routeResponse;
        switch(mode){
            case WALKING:
                routeResponse = routeService.getRoute(position, destination, true, "ua", "walking");
                break;
            case BICYCLING:
                routeResponse = routeService.getRoute(position, destination, true, "ua", "bicycling");
                break;
            default:
                routeResponse = routeService.getRoute(position, destination, true, "ua", "driving");
        }
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