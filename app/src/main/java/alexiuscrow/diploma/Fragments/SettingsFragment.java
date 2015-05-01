package alexiuscrow.diploma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import alexiuscrow.diploma.R;
import alexiuscrow.diploma.Settings;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class SettingsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    View rootView;
    TextView tvRadiusVal;
    SeekBar sbRadius;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeVariables(inflater, container);

        sbRadius.setOnSeekBarChangeListener(this);
        sbRadius.setProgress(Settings.getNearestRadius(getActivity()));
        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvRadiusVal.setText(String.valueOf(seekBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        /*NOP*/
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Settings.setNearestRadius(seekBar.getProgress());
        String disclaimerMsg = getResources().getString(R.string.settings_radius_set_disclaimer);
        Toast.makeText(getActivity().getApplicationContext(), disclaimerMsg, Toast.LENGTH_SHORT).show();
    }

    private void initializeVariables(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        tvRadiusVal = (TextView) rootView.findViewById(R.id.tvSettRadiusValue);
        sbRadius = (SeekBar) rootView.findViewById(R.id.sbRadius);
    }
}
