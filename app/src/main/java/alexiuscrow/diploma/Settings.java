package alexiuscrow.diploma;

/**
 * Created by Alexiuscrow on 21.04.2015.
 */
public class Settings {
    public static final String MAIN_APP_TAG = "DISCOUNTAPP";
    public static final String DIR_SD = "DiscForMe";

    public static final String IP = "192.168.0.102";
    public static final String PORT = "8080";
    public static final String API_V = "1";

    public static String getLocalityShopsURL(Double lat, Double lng){
        return "http://"+IP+":"+PORT+"/app/api/v"+API_V+"/shops/discounts?lat="+
                String.valueOf(lat)+"&lng="+String.valueOf(lng);
    }
}
