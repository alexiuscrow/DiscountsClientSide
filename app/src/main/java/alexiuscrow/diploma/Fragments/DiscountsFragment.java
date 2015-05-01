package alexiuscrow.diploma.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alexiuscrow.diploma.R;
import alexiuscrow.diploma.Settings;
import alexiuscrow.diploma.activities.FullDiscountInfoActivity;
import alexiuscrow.diploma.activities.NewDiscountActivity;
import alexiuscrow.diploma.entity.Discounts;
import alexiuscrow.diploma.entity.Shops;
import alexiuscrow.diploma.tasks.RefreshShopsTask;
import alexiuscrow.diploma.tasks.criteria.SearchCriteria;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class DiscountsFragment extends Fragment implements RefreshShopsTask.Callback {
    View rootView;
    Switch swNearR;
    ListView lvContainer;
    List<Shops> lShops = null;
    LayoutInflater inflater = null;

    public DiscountsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeVariables(inflater, container);
        this.inflater = inflater;
        swNearR.setChecked(Settings.getDiscSwitchStatus(getActivity()));
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        new RefreshShopsTask(this).execute(new SearchCriteria(51.522256, 31.229335));
        setRetainInstance(true);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.discounts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    public SimpleAdapter getDiscountItmAdapter(LayoutInflater inflater, int[] img, String[] title,
                                               String[] deadline, Double[] distance, String[] shops) {
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
            if (distance != null) {
                m.put(ATTRIBUTE_NAME_DISTANCE, distance[i]);
            } else {
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

    public SimpleAdapter getDiscountItmAdapter(LayoutInflater inflater, int[] img, String[] title,
                                               String[] deadline, String[] shops) {
        return getDiscountItmAdapter(inflater, img, title, deadline, null, shops);
    }

    public SimpleAdapter getDiscountItmAdapter(List<Shops> lShops) {
        if (lShops != null) {
            final String ATTRIBUTE_NAME_TITLE = "title";
            final String ATTRIBUTE_NAME_DEADLINE = "deadline";
            final String ATTRIBUTE_NAME_IMAGE = "image";
            final String ATTRIBUTE_NAME_DISTANCE = "distance";
            final String ATTRIBUTE_NAME_SHOP = "shop";

            ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                    lShops.size());
            Map<String, Object> m;

            for (Shops shop : lShops) {
                for (Discounts discount : shop.getDiscounts()) {
                    Log.d(Settings.MAIN_APP_TAG, "---- " + discount.getTitle());
                    m = new HashMap<String, Object>();
                    if (discount.getImageUrl() != null) {
                        m.put(ATTRIBUTE_NAME_IMAGE, discount.getImageUrl());
                    }
                    m.put(ATTRIBUTE_NAME_TITLE, discount.getTitle());
                    m.put(ATTRIBUTE_NAME_DEADLINE, discount.getEndDate());
                    if (shop.getDistance() != null) {
                        m.put(ATTRIBUTE_NAME_DISTANCE, shop.getDistance());
                    } else {
                        m.put(ATTRIBUTE_NAME_DISTANCE, null);

                    }
                    m.put(ATTRIBUTE_NAME_SHOP, shop.getName());
                    data.add(m);
                }
            }

            String[] from = {ATTRIBUTE_NAME_IMAGE, ATTRIBUTE_NAME_TITLE, ATTRIBUTE_NAME_DEADLINE,
                    ATTRIBUTE_NAME_DISTANCE, ATTRIBUTE_NAME_SHOP};
            int[] to = {R.id.ivDiscItemPrev, R.id.tvDiscItemTitle, R.id.tvDiscItemDeadline, R.id.tvDiscItemDistance, R.id.tvDiscItemShopName};

            SimpleAdapter sAdapter = new SimpleAdapter(inflater.getContext(), data, R.layout.item_discounts,
                    from, to);

            sAdapter.setViewBinder(new DiscItmViewBinder());

            return sAdapter;
        } else {
            return null;
        }
    }

    @Override
    public void onTaskCompleted(List<Shops> lShops) {
        this.lShops = lShops;
        lvContainer.setAdapter(getDiscountItmAdapter(this.lShops));
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

        if (sdFile.exists()) {
            Log.d("IMG", "Изображение найдено");
            return BitmapFactory.decodeFile(sdFile.getAbsolutePath());
        }
        Log.d("IMG", "Изображение НЕ найдено");
        return null;
    }

    class DiscItmViewBinder implements SimpleAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if (view.getId() == R.id.tvDiscItemDistance) {
                if (((Double) data) == null) {
                    view.setVisibility(View.GONE);
                    return true;
                } else {
                    ((TextView) view).setText(((Double) data).toString() + "м");
                    return true;
                }
            } else if (view.getId() == R.id.ivDiscItemPrev) {
                if (((String) data) == null) {
                    view.setVisibility(View.GONE);
                    Log.d("IMG", "Изображение НЕ установлено (data == null)");
                    return true;
                } else {
                    ImageLoader.getInstance().displayImage("http://192.168.0.102:8080/app/api/v1/images/" + (String) data, (ImageView) view);
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

    private void initializeVariables(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_discounts, container, false);
        lvContainer = (ListView) rootView.findViewById(R.id.lvDiscounts);
        swNearR = (Switch) rootView.findViewById(R.id.sw_disc_nearest_r);
        lvContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FullDiscountInfoActivity.class);
                getActivity().startActivity(intent);
            }
        });
        swNearR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO something else
                Settings.setDiscSwitchStatus(isChecked);
            }
        });
    }
}
