package alexiuscrow.diploma.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

import alexiuscrow.diploma.R;
import alexiuscrow.diploma.Settings;
import alexiuscrow.diploma.tasks.OptimalRoadTask;
import alexiuscrow.diploma.tasks.criteria.SearchCriteria;
import alexiuscrow.diploma.util.geo.RouteResponse;
import alexiuscrow.diploma.util.geo.TravelMode;

public class MapActivity extends ActionBarActivity implements OptimalRoadTask.Callback {

    SupportMapFragment mapFragment;
    GoogleMap map;
    List<LatLng> mPoints;
    RadioButton rbDriving;
    RadioButton rbBiCycling;
    RadioButton rbWalking;
    RadioGroup rgModes;
    Boolean firstCaseIgnoreDrawRout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initializeVariables();

        if (map == null) {
            finish();
            return;
        }

        switch (Settings.getTravelMode(this)) {
            case WALKING:
                rgModes.check(R.id.rb_walking);
                break;
            case BICYCLING:
                rgModes.check(R.id.rb_bicycling);
                break;
            case DRIVING:
                rgModes.check(R.id.rb_driving);
                break;
        }
    }

    public void road(RouteResponse routeResponse) {
        map.clear();
        mPoints = PolyUtil.decode(routeResponse.getPoints());

        PolylineOptions line = new PolylineOptions();
        line.width(4f).color(R.color.material_deep_teal_500);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == 0) {
                MarkerOptions startMarkerOptions = new MarkerOptions()
                        .position(mPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place));
                map.addMarker(startMarkerOptions);
            } else if (i == mPoints.size() - 1) {
                MarkerOptions endMarkerOptions = new MarkerOptions()
                        .position(mPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place));
                map.addMarker(endMarkerOptions);
            }
            line.add(mPoints.get(i));
            latLngBuilder.include(mPoints.get(i));
        }
        map.addPolyline(line);
        int size = getResources().getDisplayMetrics().widthPixels;
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
        map.moveCamera(track);
    }

    @Override
    public void onTaskCompleted(RouteResponse routeResponse) {
        road(routeResponse);
    }

    private void initializeVariables() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map = mapFragment.getMap();
        rbDriving = (RadioButton) findViewById(R.id.rb_driving);
        rbBiCycling = (RadioButton) findViewById(R.id.rb_bicycling);
        rbBiCycling.setVisibility(View.GONE); // OFF !!!!!!!!!!!!!!
        rbWalking = (RadioButton) findViewById(R.id.rb_walking);
        rgModes = (RadioGroup) findViewById(R.id.rg_modes);

        rgModes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (!firstCaseIgnoreDrawRout) {
                switch (checkedId) {
                    case R.id.rb_driving:
                        Settings.setTravelMode(TravelMode.DRIVING);
                        new OptimalRoadTask(MapActivity.this).execute(new SearchCriteria(51.519664, 31.231423),
                                new SearchCriteria(51.521466, 31.232378, TravelMode.DRIVING));
                        break;
                    case R.id.rb_walking:
                        Settings.setTravelMode(TravelMode.WALKING);
                        new OptimalRoadTask(MapActivity.this).execute(new SearchCriteria(51.519664, 31.231423),
                                new SearchCriteria(51.521466, 31.232378, TravelMode.WALKING));
                        break;
                    case R.id.rb_bicycling:
                        Settings.setTravelMode(TravelMode.BICYCLING);
                        new OptimalRoadTask(MapActivity.this).execute(new SearchCriteria(51.519664, 31.231423),
                                new SearchCriteria(51.521466, 31.232378, TravelMode.BICYCLING));
                        break;
                }
//                }
//                else{
//                    firstCaseIgnoreDrawRout = false;
//                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        Settings.saveAllToPreferences(this);
        super.onDestroy();
    }
}
