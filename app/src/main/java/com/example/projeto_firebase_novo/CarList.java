package com.example.projeto_firebase_novo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.List;

public class CarList extends ArrayAdapter<Car> {

    private Activity context;
    public List<Car> carList;

    public CarList(Activity context, List<Car> carList){
        super(context, R.layout.list_layout, carList);
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView text_maker = (TextView) listViewItem.findViewById(R.id.maker);
        TextView text_model = (TextView) listViewItem.findViewById(R.id.model);
        TextView text_buildYear = (TextView) listViewItem.findViewById(R.id.buildYear);
        TextView text_engine = (TextView) listViewItem.findViewById(R.id.engine);

        Car car = carList.get(position);

        text_maker.setText(car.getCarMaker());
        text_model.setText(car.getCarModel());
        text_buildYear.setText(car.getCarBuildYear());
        text_engine.setText(car.getCarEngine());

        return listViewItem;
    }
}
