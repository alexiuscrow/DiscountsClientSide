package alexiuscrow.diploma.util.geo;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Alexiuscrow on 27.04.2015.
 */
public interface RouteApi {
    @GET("/maps/api/directions/json")
    RouteResponse getRoute(
            @Query(value = "origin", encodeValue = false) String position,
            @Query(value = "destination", encodeValue = false) String destination,
            @Query("sensor") boolean sensor,
            @Query("language") String language,
            @Query("mode") String mode);
}
