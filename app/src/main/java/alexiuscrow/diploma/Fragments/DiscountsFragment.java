package alexiuscrow.diploma.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alexiuscrow.diploma.R;
import alexiuscrow.diploma.Settings;
import alexiuscrow.diploma.activities.NewDiscountActivity;
import alexiuscrow.diploma.entity.Discounts;
import alexiuscrow.diploma.entity.Shops;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class DiscountsFragment extends Fragment {
    int[] img = {R.mipmap.img, R.mipmap.success, R.mipmap.img, R.mipmap.success,
            R.mipmap.img, R.mipmap.success};
    String[] title = { "Super Discounts $)", "All -50%", "Second thing is free", "Shorts Funny",
            "Olala", "Come on. Buy Me!" };
    String[] deadline = { "05/04/15", "05/04/15", "05/04/15",
            "05/04/15", "05/04/15", "05/04/15"};
    String[] shops = { "Велика Кишеня", "Мегацентр", "Квартал",
            "АТБ", "ЕКО Маркет", "Симпатик"};
    Double[] distance = { 25.3, 163.8, 54.6, 5.9, 5.5, 4.5 };

    View rootView;
    Switch swNearR;
    ListView lvContainer;
    List<Shops> lShops = null;
    SimpleAdapter sAdapter = null;
    LayoutInflater inflater = null;

    public DiscountsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeVariables(inflater, container);
//        new RefreshLocalTasks(inflater, lvContainer).execute("51.522256", "31.229335");
        this.inflater = inflater;
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        new RefreshLocalTasks().execute(51.522256, 31.229335);
//        new RefreshLocalTasks().execute();
        setRetainInstance(true);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.discounts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(getActivity(), NewDiscountActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.action_disc_refresh:
                //TODO
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static SimpleAdapter getDiscountItmAdapter(LayoutInflater inflater, int[] img, String[] title,
                                                String[] deadline, Double[] distance, String[] shops){
        final String ATTRIBUTE_NAME_TITLE = "title";
        final String ATTRIBUTE_NAME_DEADLINE = "deadline";
        final String ATTRIBUTE_NAME_IMAGE = "image";
        final String ATTRIBUTE_NAME_DISTANCE = "distance";
        final String ATTRIBUTE_NAME_SHOP = "shop";

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                title.length);
        Map<String, Object> m;

        for (int i = 0; i < title.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_IMAGE, img[i]);
            m.put(ATTRIBUTE_NAME_TITLE, title[i]);
            m.put(ATTRIBUTE_NAME_DEADLINE, deadline[i]);
            if (distance != null){
                m.put(ATTRIBUTE_NAME_DISTANCE, distance[i]);
            }
            else{
                m.put(ATTRIBUTE_NAME_DISTANCE, null);

            }
            m.put(ATTRIBUTE_NAME_SHOP, shops[i]);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = {ATTRIBUTE_NAME_IMAGE, ATTRIBUTE_NAME_TITLE, ATTRIBUTE_NAME_DEADLINE,
                ATTRIBUTE_NAME_DISTANCE, ATTRIBUTE_NAME_SHOP};
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = {R.id.ivDiscItemPrev, R.id.tvDiscItemTitle, R.id.tvDiscItemDeadline, R.id.tvDiscItemDistance, R.id.tvDiscItemShopName};

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(inflater.getContext(), data, R.layout.item_discounts,
                from, to);

        sAdapter.setViewBinder(new DiscItmViewBinder());

        return sAdapter;
    }

    public static SimpleAdapter getDiscountItmAdapter(LayoutInflater inflater, int[] img, String[] title,
                                                String[] deadline, String[] shops){
        return getDiscountItmAdapter(inflater, img, title, deadline, null, shops);
    }

    public SimpleAdapter getDiscountItmAdapter(List<Shops> lShops){
        if (lShops != null){
            final String ATTRIBUTE_NAME_TITLE = "title";
            final String ATTRIBUTE_NAME_DEADLINE = "deadline";
            final String ATTRIBUTE_NAME_IMAGE = "image";
            final String ATTRIBUTE_NAME_DISTANCE = "distance";
            final String ATTRIBUTE_NAME_SHOP = "shop";

            ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                    lShops.size());
            Map<String, Object> m;

            for (Shops shop: lShops) {
                for (Discounts discount: shop.getDiscounts()){
                    m = new HashMap<String, Object>();
                    if(discount.getImageUrl() != null) {
                        m.put(ATTRIBUTE_NAME_IMAGE, discount.getImageUrl());
                    }
                    m.put(ATTRIBUTE_NAME_TITLE, discount.getTitle());
                    m.put(ATTRIBUTE_NAME_DEADLINE, discount.getEndDate());
                    if (shop.getDistance() != null){
                        m.put(ATTRIBUTE_NAME_DISTANCE, shop.getDistance());
                    }
                    else{
                        m.put(ATTRIBUTE_NAME_DISTANCE, null);

                    }
                    m.put(ATTRIBUTE_NAME_SHOP, shop.getName());
                    data.add(m);
                }
            }

            // массив имен атрибутов, из которых будут читаться данные
            String[] from = {ATTRIBUTE_NAME_IMAGE, ATTRIBUTE_NAME_TITLE, ATTRIBUTE_NAME_DEADLINE,
                    ATTRIBUTE_NAME_DISTANCE, ATTRIBUTE_NAME_SHOP};
            // массив ID View-компонентов, в которые будут вставлять данные
            int[] to = {R.id.ivDiscItemPrev, R.id.tvDiscItemTitle, R.id.tvDiscItemDeadline, R.id.tvDiscItemDistance, R.id.tvDiscItemShopName};

            // создаем адаптер
            SimpleAdapter sAdapter = new SimpleAdapter(inflater.getContext(), data, R.layout.item_discounts,
                    from, to);

            sAdapter.setViewBinder(new DiscItmViewBinder());

            return sAdapter;
        }
        else{
            return null;
        }
    }

    static class DiscItmViewBinder implements SimpleAdapter.ViewBinder{

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if (view.getId() == R.id.tvDiscItemDistance){
                if(((Double)data) == null){
                    view.setVisibility(View.GONE);
                    return true;
                }
                else{
                    ((TextView)view).setText(((Double)data).toString() + "м");
                    return true;
                }
            }
            else if (view.getId() == R.id.ivDiscItemPrev){
                if(((String)data) == null){
                    view.setVisibility(View.GONE);
                    Log.d("IMG", "Изображение НЕ установлено (data == null)");
                    return true;
                }
                else{
                    ImageLoader.getInstance().displayImage("http://192.168.0.102:8080/app/api/v1/images/" + (String)data, (ImageView)view);
//                    Bitmap image = readFileSD((String)data);
//                    if (image != null){
//                        ((ImageView)view).setImageBitmap(image);
//                        Log.d("IMG", "Изображение установлено");
//                    }
//                    else{
//                        view.setVisibility(View.GONE);
//                        Log.d("IMG", "Изображение НЕ установлено (readFileSD((String)data) ==  null)");
//                    }
                    return true;
                }
            }

            return false;
        }
    }

    private static Bitmap readFileSD(String fileName) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(Settings.MAIN_APP_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return null;
        }

        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + Settings.DIR_SD);
        File sdFile = new File(sdPath, fileName + ".jpg");

        if(sdFile.exists()){
            Log.d("IMG", "Изображение найдено");
            return BitmapFactory.decodeFile(sdFile.getAbsolutePath());
        }
        Log.d("IMG", "Изображение НЕ найдено");
        return null;
    }

    private void initializeVariables(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_discounts, container, false);
        lvContainer = (ListView) rootView.findViewById(R.id.lvDiscounts);
        swNearR = (Switch) rootView.findViewById(R.id.sw_disc_nearest_r);
    }

    class RefreshLocalTasks extends AsyncTask<Double, Void, List<Shops>> {

        @Override
        protected List<Shops> doInBackground(Double... latlng) {
            List<Shops> lShops = null;
        if (latlng.length == 2){
            try {
                String url = Settings.getLocalityShopsURL(latlng[0], latlng[1]);
                String json = readUrl(url);
                GsonBuilder builder = new GsonBuilder();
                builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Gson gson = builder.create();
                lShops = gson.fromJson(json, new TypeToken<List<Shops>>(){}.getType());
            }
            catch (Exception e){}

//            writeImagesToSD(lShops);
        }
        else{
            Log.d(Settings.MAIN_APP_TAG, "Не вірна кількість параметрів передана в 'execute()': " + latlng.length);
        }
            return lShops;
        }

        @Override
        protected void onPostExecute(List<Shops> lShops) {
            super.onPostExecute(lShops);
            lvContainer.setAdapter(getDiscountItmAdapter(lShops));
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

        private  String readUrl(String urlString) throws Exception {
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

        public  String convertFromUTF8(String s) {
            String out = null;
            try {
                out = new String(s.getBytes("windows-1251"), "UTF-8");
            } catch (java.io.UnsupportedEncodingException e) {
                return null;
            }
            return out;
        }
    }
}
