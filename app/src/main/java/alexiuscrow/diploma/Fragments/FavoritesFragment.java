package alexiuscrow.diploma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import alexiuscrow.diploma.R;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class FavoritesFragment extends Fragment{
    String[] shops = { "Велика Кишеня", "Мегацентр", "Квартал",
            "АТБ", "ЕКО Маркет", "Симпатик"};
    String[] address = { "вул. Савчука, 4", "вул. Савчука, 4", "вул. Савчука, 4",
            "вул. Савчука, 4", "вул. Савчука, 4", "вул. Савчука, 4"};
    Double[] distance = { 25.3, 163.8, 54.6, 5.9, 5.5, 4.5 };
    boolean[] favorite = { true, false, true, true, false, true };

    View rootView;
    ListView lvContainer;

    public FavoritesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeVariables(inflater, container);
        SimpleAdapter sAdapter = ShopsFragment.getShopsItmAdapter(inflater, shops, address, favorite);
        lvContainer.setAdapter(sAdapter);
        return rootView;
    }

    private void initializeVariables(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        lvContainer = (ListView) rootView.findViewById(R.id.lvFav);
    }
}
