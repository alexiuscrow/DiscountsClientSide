package alexiuscrow.diploma.util.geo;

import java.util.List;

/**
 * Created by Alexiuscrow on 27.04.2015.
 */
public class RouteResponse {
    public List<Route> routes;

    public String getPoints() {
        return this.routes.get(0).overview_polyline.points;
    }

    class Route {
        OverviewPolyline overview_polyline;
    }

    class OverviewPolyline {
        String points;
    }
}
