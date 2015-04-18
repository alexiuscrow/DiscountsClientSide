package alexiuscrow.diploma.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;

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


    Switch swNearR;
    ListView lvSimple;

    public DiscountsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discounts, container,
                false);
        SimpleAdapter sAdapter = getDiscountItmAdapter(inflater, img, title, deadline, shops);
        // определяем список и присваиваем ему адаптер
        lvSimple = (ListView) rootView.findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);

        return rootView;
    }

    private SimpleAdapter getDiscountItmAdapter(LayoutInflater inflater, int[] img, String[] title,
                                                String[] deadline, String[] shops){
        final String ATTRIBUTE_NAME_TITLE = "title";
        final String ATTRIBUTE_NAME_DEADLINE = "deadline";
        final String ATTRIBUTE_NAME_IMAGE = "image";
        final String ATTRIBUTE_NAME_SHOP = "shop";

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                title.length);
        Map<String, Object> m;

        for (int i = 0; i < title.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_IMAGE, img[i]);
            m.put(ATTRIBUTE_NAME_TITLE, title[i]);
            m.put(ATTRIBUTE_NAME_DEADLINE, deadline[i]);
            m.put(ATTRIBUTE_NAME_SHOP, shops[i]);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = {ATTRIBUTE_NAME_IMAGE, ATTRIBUTE_NAME_TITLE, ATTRIBUTE_NAME_DEADLINE,
                ATTRIBUTE_NAME_SHOP};
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = {R.id.imageView, R.id.tvTitle, R.id.tvDeadline, R.id.tvShopName};

        // создаем адаптер
        return new SimpleAdapter(inflater.getContext(), data, R.layout.item_discounts,
                from, to);
    }
}
