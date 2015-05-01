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
            Log.d(Settings.MAIN_APP_TAG, json);
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