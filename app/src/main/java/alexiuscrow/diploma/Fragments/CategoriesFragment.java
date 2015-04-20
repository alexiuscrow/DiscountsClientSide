package alexiuscrow.diploma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import alexiuscrow.diploma.R;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class CategoriesFragment extends Fragment{
    String[] names = { "Дитячы товари", "Їжа", "Одяг"};

    View rootView;
    ListView lvContainer;

    public CategoriesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeVariables(inflater, container);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, names);

        lvContainer.setAdapter(adapter);

        return rootView;
    }

    private void initializeVariables(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        lvContainer = (ListView) rootView.findViewById(R.id.lvCategories);
    }
}
