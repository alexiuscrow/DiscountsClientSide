package alexiuscrow.diploma;

import android.content.Context;
import android.content.SharedPreferences;

import alexiuscrow.diploma.util.geo.TravelMode;

/**
 * Created by Alexiuscrow on 21.04.2015.
 */
public class Settings {
    public static final String MAIN_APP_TAG = "DISCOUNTAPP";
    public static final String DIR_SD = "DiscForMe";

    public static final String IP = "192.168.0.101";
    public static final String PORT = "8080";
    public static final String API_V = "1";

    private static SharedPreferences preferences = null;
    private static SharedPreferences.Editor prefEdit = null;

    private static final String NEAREST_RADIUS_KEY = "NEAREST_RADIUS";
    private static final String NEAREST_SWITCH_DISC_KEY = "NEAREST_SWITCH_DISC";
    private static final String NEAREST_SWITCH_SHOPS_KEY = "NEAREST_SWITCH_SHOPS";
    private static final String NEAREST_SWITCH_CATEG_KEY = "NEAREST_SWITCH_CATEG";
    private static final String TRAVEL_MODE_KEY = "TRAVEL_MODE";

    private static Integer nearestRadius = null;
    private static Boolean nearestSwitchDisc = null;
    private static Boolean nearestSwitchShops = null;
    private static Boolean nearestSwitchCateg = null;
    private static TravelMode travelMode = null;

    private Settings(){}

    public static synchronized String getLocalityShopsURL(Double lat, Double lng){
        return "http://"+IP+":"+PORT+"/app/api/v"+API_V+"/shops/discounts?lat="+
                String.valueOf(lat)+"&lng="+String.valueOf(lng);
    }

    public static synchronized String getNearestShopsURL(Double lat, Double lng, Double radius){
        return getLocalityShopsURL(lat, lng) + "&radius=" + String.valueOf(radius);
    }

    public static synchronized void initializePreferences(Context context){
        preferences = context.getSharedPreferences(DIR_SD, Context.MODE_PRIVATE);
        if (prefEdit == null) prefEdit = preferences.edit();
    }

    public static synchronized Integer getNearestRadius(Context context){
        if (nearestRadius == null){
            initializePreferences(context);
            Integer defValue = 200;
            nearestRadius = preferences.getInt(NEAREST_RADIUS_KEY, defValue);
        }
        return nearestRadius;
    }

    public static synchronized TravelMode getTravelMode(Context context){
        if (travelMode == null){
            initializePreferences(context);
            String defValue = "driving";
            switch (preferences.getString(TRAVEL_MODE_KEY, defValue)){
                case "walking":
                    travelMode = TravelMode.WALKING;
                    break;
                case "bicycling":
                    travelMode = TravelMode.BICYCLING;
                    break;
                default:
                    travelMode = TravelMode.DRIVING;
            }
        }
        return travelMode;
    }

    public static synchronized Boolean getDiscSwitchStatus(Context context){
        if (nearestSwitchDisc == null){
            initializePreferences(context);
            boolean defValue = false;
            if (preferences.getBoolean(NEAREST_SWITCH_DISC_KEY, defValue))
                    nearestSwitchDisc = true;
            else
                nearestSwitchDisc = false;
        }
        return nearestSwitchDisc;
    }

    public static synchronized Boolean getShopsSwitchStatus(Context context){
        if (nearestSwitchShops == null){
            initializePreferences(context);
            boolean defValue = false;
            if (preferences.getBoolean(NEAREST_SWITCH_SHOPS_KEY, defValue))
                nearestSwitchShops = true;
            else
                nearestSwitchShops = false;
        }
        return nearestSwitchShops;
    }

    public static synchronized Boolean getCategSwitchStatus(Context context){
        if (nearestSwitchCateg == null){
            initializePreferences(context);
            boolean defValue = false;
            if (preferences.getBoolean(NEAREST_SWITCH_CATEG_KEY, defValue))
                nearestSwitchCateg = true;
            else
                nearestSwitchCateg = false;
        }
        return nearestSwitchCateg;
    }

    public static synchronized void setNearestRadius(Integer nearestRadius){
        Settings.nearestRadius = nearestRadius;
    }

    public static synchronized void setTravelMode(TravelMode travelMode){
        Settings.travelMode = travelMode;
    }

    public static synchronized void setDiscSwitchStatus(Boolean status){
        Settings.nearestSwitchDisc = status;
    }

    public static synchronized void setShopsSwitchStatus(Boolean status){
        Settings.nearestSwitchShops = status;
    }

    public static synchronized void setCategSwitchStatus(Boolean status){
        Settings.nearestSwitchCateg = status;
    }

    public static synchronized void saveNearestRadius(Integer nearestRadius, Context context){
        setNearestRadius(nearestRadius);
        if (preferences == null) initializePreferences(context);
        prefEdit.putInt(NEAREST_RADIUS_KEY, nearestRadius);
        prefEdit.apply();
    }

    public static synchronized void saveTravelMode(TravelMode travelMode, Context context){
        setTravelMode(travelMode);
        if (preferences == null) initializePreferences(context);
        switch (travelMode){
            case WALKING:
                prefEdit.putString(TRAVEL_MODE_KEY, "walking");
                break;
            case BICYCLING:
                prefEdit.putString(TRAVEL_MODE_KEY, "bicycling");
                break;
            default:
                prefEdit.putString(TRAVEL_MODE_KEY, "driving");
        }
        prefEdit.apply();
    }

    public static synchronized void saveDiscSwitchStatus(Boolean status, Context context){
        setDiscSwitchStatus(status);
        if (preferences == null) initializePreferences(context);
        prefEdit.putBoolean(NEAREST_SWITCH_DISC_KEY, status);
        prefEdit.apply();
    }

    public static synchronized void saveShopsSwitchStatus(Boolean status, Context context){
        setShopsSwitchStatus(status);
        if (preferences == null) initializePreferences(context);
        prefEdit.putBoolean(NEAREST_SWITCH_SHOPS_KEY, status);
        prefEdit.apply();
    }

    public static synchronized void saveCategSwitchStatus(Boolean status, Context context){
        setCategSwitchStatus(status);
        if (preferences == null) initializePreferences(context);
        prefEdit.putBoolean(NEAREST_SWITCH_CATEG_KEY, status);
        prefEdit.apply();
    }

    public static synchronized void saveAllToPreferences(Context context){
        if (nearestRadius != null){
            if (preferences == null) initializePreferences(context);
            saveNearestRadius(nearestRadius, context);
        }
        if (nearestSwitchDisc != null){
            if (preferences == null) initializePreferences(context);
            saveDiscSwitchStatus(nearestSwitchDisc, context);
        }
        if (nearestSwitchShops != null){
            if (preferences == null) initializePreferences(context);
            saveShopsSwitchStatus(nearestSwitchShops, context);
        }
        if (nearestSwitchCateg != null){
            if (preferences == null) initializePreferences(context);
            saveCategSwitchStatus(nearestSwitchCateg, context);
        }
        if (travelMode != null){
            if (preferences == null) initializePreferences(context);
            saveTravelMode(travelMode, context);
        }
    }
}
