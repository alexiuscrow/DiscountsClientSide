package alexiuscrow.diploma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import alexiuscrow.diploma.R;

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

    public DiscountsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeVariables(inflater, container);

        SimpleAdapter sAdapter = getDiscountItmAdapter(inflater, img, title, deadline, distance, shops);
        lvContainer.setAdapter(sAdapter);

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
        inflater.inflate(R.menu.discounts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private SimpleAdapter getDiscountItmAdapter(LayoutInflater inflater, int[] img, String[] title,
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

    private SimpleAdapter getDiscountItmAdapter(LayoutInflater inflater, int[] img, String[] title,
                                                String[] deadline, String[] shops){
        return getDiscountItmAdapter(inflater, img, title, deadline, null, shops);
    }

    class DiscItmViewBinder implements SimpleAdapter.ViewBinder{

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if ((view.getId() == R.id.tvDiscItemDistance)){
                if(((Double)data) == null){
                    view.setVisibility(View.GONE);
                    return true;
                }
                else{
                    ((TextView)view).setText(((Double)data).toString() + "м");
                    return true;
                }
            }
            return false;
        }
    }

    private void initializeVariables(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_discounts, container, false);
        lvContainer = (ListView) rootView.findViewById(R.id.lvDiscounts);
        swNearR = (Switch) rootView.findViewById(R.id.sw_disc_nearest_r);
    }
}
