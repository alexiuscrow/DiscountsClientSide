package alexiuscrow.diploma.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import alexiuscrow.diploma.R;

/**
 * Created by Alexiuscrow on 17.04.2015.
 */
public class DiscountsFragment extends Fragment {


    String[] name = { "Алексей", "Дарья", "Екатерина", "Михаил", "Дима", "Алексей",
            "Жанна", "Игорь" };
    String[] position = { "Программер", "Бухгалтер", "Программер",
            "Программер", "Бухгалтер", "Директор", "Программер", "Охранник" };
    int salary[] = { 13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000 };

    public DiscountsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_discounts, container,
                false);
        LinearLayout linLayout = (LinearLayout) rootView.findViewById(R.id.linLayout);

        for (int i = 0; i < name.length; i++) {
            View item = inflater.inflate(R.layout.item_discount, linLayout, false);
            TextView tvName = (TextView) item.findViewById(R.id.tvName);
            tvName.setText(name[i]);
            TextView tvPosition = (TextView) item.findViewById(R.id.tvPosition);
            tvPosition.setText("Должность: " + position[i]);
            TextView tvSalary = (TextView) item.findViewById(R.id.tvSalary);
            tvSalary.setText("Оклад: " + String.valueOf(salary[i]));
            linLayout.addView(item);
        }

        return rootView;
    }
}
