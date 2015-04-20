package alexiuscrow.diploma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import alexiuscrow.diploma.R;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class SettingsFragment extends Fragment{
    View rootView;
    TextView tvRadiusVal;
    SeekBar sbRadius;

    public SettingsFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeVariables(inflater, container);

        return rootView;
    }

    private void initializeVariables(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        tvRadiusVal = (TextView) rootView.findViewById(R.id.tvSettRadiusValue);
        sbRadius = (SeekBar) rootView.findViewById(R.id.sbRadius);
    }

}
