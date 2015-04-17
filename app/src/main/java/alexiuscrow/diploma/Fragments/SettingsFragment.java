package alexiuscrow.diploma.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alexiuscrow.diploma.R;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class SettingsFragment extends Fragment{
    public SettingsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container,
                false);

        return rootView;
    }
}
