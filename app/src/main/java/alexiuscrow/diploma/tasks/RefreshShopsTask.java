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
import java.util.ArrayList;
import java.util.List;

import alexiuscrow.diploma.Settings;
import alexiuscrow.diploma.entity.Discounts;
import alexiuscrow.diploma.entity.Shops;
import alexiuscrow.diploma.tasks.criteria.SearchCriteria;

/**
 * Created by Alexiuscrow on 25.04.2015.
 */

public class RefreshShopsTask extends AsyncTask<SearchCriteria, Void, List<Shops>> {
    Callback listener;

    public RefreshShopsTask(Callback listener){
        this.listener = listener;
    }

    @Override
    protected List<Shops> doInBackground(SearchCriteria... criterias) {
        List<Shops> lShops = null;
        try {
            String url;
            if (!criterias[0].hasRadius())
                url = Settings.getLocalityShopsURL(criterias[0].getLat(), criterias[0].getLng());
            else
                url = Settings.getNearestShopsURL(criterias[0].getLat(), criterias[0].getLng(), criterias[0].getRadius());

            String json = getJsonFromUrl(url);
            GsonBuilder builder = new GsonBuilder();
            builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Gson gson = builder.create();
            lShops = gson.fromJson(json, new TypeToken<ArrayList<Shops>>(){}.getType());
        }
        catch (Exception e){}
        return lShops;
    }

    @Override
    protected void onPostExecute(List<Shops> lShops) {
        super.onPostExecute(lShops);
        listener.onTaskCompleted(lShops);
    }

    private  void writeImagesToSD(List<Shops> lShops) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(Settings.MAIN_APP_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }

        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + Settings.DIR_SD);
        sdPath.mkdirs();
        if (lShops != null) Log.d(Settings.MAIN_APP_TAG, "writeImagesToSD: " + lShops.get(0).toString());
        else Log.d(Settings.MAIN_APP_TAG, "writeImagesToSD: lShops null");
        if (lShops != null){
            for (Shops shop: lShops){
                for(Discounts discount: shop.getDiscounts()){
                    if (discount.getImageUrl() != null){
                        InputStream input;
                        try {
                            URL url = new URL ("http://192.168.0.102:8080/app/api/v1/images/" + discount.getImageUrl());
                            input = url.openStream();
                            byte[] buffer = new byte[1500];
                            OutputStream output = new FileOutputStream(sdPath.getAbsolutePath() + "/" + discount.getImageUrl() + ".jpg");
                            try {
                                int bytesRead = 0;
                                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                                    output.write(buffer, 0, bytesRead);
                                }
                                Log.d(Settings.MAIN_APP_TAG, "writeImagesToSD: Файл '" + discount.getImageUrl() + ".jpg' записаний на SD");
                            }
                            finally {
                                output.close();
                                buffer=null;
                            }
                        }
                        catch(Exception e) {
                            Log.d(Settings.MAIN_APP_TAG, "Помилка при збереженні зображення");
                        }
                    }
                }
            }
        }
        else Log.d(Settings.MAIN_APP_TAG, "lShops null");
    }

    private  String getJsonFromUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public interface Callback {
        void onTaskCompleted(List<Shops> lShops);
    }
}