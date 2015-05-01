package alexiuscrow.diploma;

import android.content.Context;
import android.content.SharedPreferences;

import alexiuscrow.diploma.util.geo.TravelMode;

/**
 * Created by Alexiuscrow on 21.04.2015.
 */
public class Settings {
    public static final transient String MAIN_APP_TAG = "DISCOUNTAPP";
    public static final transient String DIR_SD = "DiscForMe";

    public static final transient String IP = "192.168.0.101";
    public static final transient String PORT = "8080";
    public static final transient String API_V = "1";

    private static transient SharedPreferences preferences = null;
    private static transient SharedPreferences.Editor prefEdit = null;

    private static final String NEAREST_RADIUS_KEY = "NEAREST_RADIUS";
    private static final String NEAREST_SWITCH_DISC_KEY = "NEAREST_SWITCH_DISC";
    private static final String NEAREST_SWITCH_SHOPS_KEY = "NEAREST_SWITCH_SHOPS";
    private static final String NEAREST_SWITCH_CATEG_KEY = "NEAREST_SWITCH_CATEG";
    private static final String TRAVEL_MODE_KEY = "TRAVEL_MODE";

    private static transient Integer nearestRadius = null;
    private static transient Boolean nearestSwitchDisc = null;
    private static transient Boolean nearestSwitchShops = null;
    private static transient Boolean nearestSwitchCateg = null;
    private static transient TravelMode travelMode = null;

    private Settings(){}

    public static String getLocalityShopsURL(Double lat, Double lng){
        return "http://"+IP+":"+PORT+"/app/api/v"+API_V+"/shops/discounts?lat="+
                String.valueOf(lat)+"&lng="+String.valueOf(lng);
    }

    public static String getNearestShopsURL(Double lat, Double lng, Double radius){
        return getLocalityShopsURL(lat, lng) + "&radius=" + String.valueOf(radius);
    }

    public static void initializePreferences(Context context){
        preferences = context.getSharedPreferences(DIR_SD, Context.MODE_PRIVATE);
        if (prefEdit == null) prefEdit = preferences.edit();
    }

    public static Integer getNearestRadius(Context context){
        if (nearestRadius == null){
            initializePreferences(context);
            Integer defValue = 200;
            nearestRadius = preferences.getInt(NEAREST_RADIUS_KEY, defValue);
        }
        return nearestRadius;
    }

    public static TravelMode getTravelMode(Context context){
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

    public static Boolean getDiscSwitchStatus(Context context){
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

    public static Boolean getShopsSwitchStatus(Context context){
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

    public static Boolean getCategSwitchStatus(Context context){
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

    public static void setNearestRadius(Integer nearestRadius){
        Settings.nearestRadius = nearestRadius;
    }

    public static void setTravelMode(TravelMode travelMode){
        Settings.travelMode = travelMode;
    }

    public static void setDiscSwitchStatus(Boolean status){
        Settings.nearestSwitchDisc = status;
    }

    public static void setShopsSwitchStatus(Boolean status){
        Settings.nearestSwitchShops = status;
    }

    public static void setCategSwitchStatus(Boolean status){
        Settings.nearestSwitchCateg = status;
    }

    public static void saveNearestRadius(Integer nearestRadius, Context context){
        setNearestRadius(nearestRadius);
        if (preferences == null) initializePreferences(context);
        prefEdit.putInt(NEAREST_RADIUS_KEY, nearestRadius);
        prefEdit.apply();
    }

    public static void saveTravelMode(TravelMode travelMode, Context context){
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

    public static void saveDiscSwitchStatus(Boolean status, Context context){
        setDiscSwitchStatus(status);
        if (preferences == null) initializePreferences(context);
        prefEdit.putBoolean(NEAREST_SWITCH_DISC_KEY, status);
        prefEdit.apply();
    }

    public static void saveShopsSwitchStatus(Boolean status, Context context){
        setShopsSwitchStatus(status);
        if (preferences == null) initializePreferences(context);
        prefEdit.putBoolean(NEAREST_SWITCH_SHOPS_KEY, status);
        prefEdit.apply();
    }

    public static void saveCategSwitchStatus(Boolean status, Context context){
        setCategSwitchStatus(status);
        if (preferences == null) initializePreferences(context);
        prefEdit.putBoolean(NEAREST_SWITCH_CATEG_KEY, status);
        prefEdit.apply();
    }

    public static void saveAllToPreferences(Context context){
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
