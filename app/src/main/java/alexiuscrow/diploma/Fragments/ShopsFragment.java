package alexiuscrow.diploma.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import alexiuscrow.diploma.R;
import alexiuscrow.diploma.Settings;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class ShopsFragment extends Fragment {
    String[] shops = {"Велика Кишеня", "Мегацентр", "Квартал",
            "АТБ", "ЕКО Маркет", "Симпатик"};
    String[] address = {"вул. Савчука, 4", "вул. Савчука, 4", "вул. Савчука, 4",
            "вул. Савчука, 4", "вул. Савчука, 4", "вул. Савчука, 4"};
    Double[] distance = {25.3, 163.8, 54.6, 5.9, 5.5, 4.5};
    boolean[] favorite = {true, false, true, true, false, true};

    View rootView;
    Switch swNearR;
    ListView lvContainer;

    public ShopsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeVariables(inflater, container);
        SimpleAdapter sAdapter = getShopsItmAdapter(inflater, shops, address, distance, favorite);
        lvContainer.setAdapter(sAdapter);
        swNearR.setChecked(Settings.getShopsSwitchStatus(getActivity()));
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shops, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static SimpleAdapter getShopsItmAdapter(LayoutInflater inflater, String[] shops, String[] address,
                                                   Double[] distance, boolean[] favorite) {
        final String ATTRIBUTE_NAME_SHOPNAME = "shopname";
        final String ATTRIBUTE_NAME_ADDRESS = "address";
        final String ATTRIBUTE_NAME_DISTANCE = "distance";
        final String ATTRIBUTE_NAME_FAVORITE = "favorite";

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(shops.length);
        Map<String, Object> m;

        for (int i = 0; i < shops.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_SHOPNAME, shops[i]);
            m.put(ATTRIBUTE_NAME_ADDRESS, address[i]);
            if (distance != null) {
                m.put(ATTRIBUTE_NAME_DISTANCE, distance[i]);
            } else {
                m.put(ATTRIBUTE_NAME_DISTANCE, null);
            }
            m.put(ATTRIBUTE_NAME_FAVORITE, favorite[i]);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = {ATTRIBUTE_NAME_SHOPNAME, ATTRIBUTE_NAME_ADDRESS, ATTRIBUTE_NAME_DISTANCE, ATTRIBUTE_NAME_FAVORITE};
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = {R.id.tvShopItemName, R.id.tvShopItemAddress, R.id.tvShopItemDistance, R.id.cbFavShop};

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(inflater.getContext(), data, R.layout.item_shops,
                from, to);

        sAdapter.setViewBinder(new ShopItmViewBinder());

        return sAdapter;
    }

    public static SimpleAdapter getShopsItmAdapter(LayoutInflater inflater, String[] shops, String[] address,
                                                   boolean[] favorite) {
        return getShopsItmAdapter(inflater, shops, address, null, favorite);
    }

    static class ShopItmViewBinder implements SimpleAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if ((view.getId() == R.id.tvShopItemDistance)) {
                if (((Double) data) == null) {
                    view.setVisibility(View.GONE);
                    return true;
                } else {
                    ((TextView) view).setText(((Double) data).toString() + "м");
                    return true;
                }
            }
            return false;
        }
    }

    private void initializeVariables(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_shops, container, false);
        lvContainer = (ListView) rootView.findViewById(R.id.lvShops);
        swNearR = (Switch) rootView.findViewById(R.id.sw_shops_nearest_r);
        swNearR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO something else
                Settings.setShopsSwitchStatus(isChecked);
            }
        });
    }
}
