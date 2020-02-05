package com.example.projeto_firebase_novo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.util.logging.Logger.global;

public class MainActivity extends AppCompatActivity implements DeleteDialog.OnDeleteDialogInteractionListener {

    Button buttonAdd;
    Spinner spinnerMaker, spinnerModel, spinnerBuildYear, spinnerEngine;
    DatabaseReference databaseCars;

    private int currentItemPosition = -1;

    public String carIdRemove;

    ListView listViewCars;

    List<Car> carList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseCars = FirebaseDatabase.getInstance().getReference("cars");

        buttonAdd = (Button) findViewById(R.id.button_add);
        spinnerMaker = (Spinner) findViewById(R.id.spinner_maker);
        spinnerModel = (Spinner) findViewById(R.id.spinner_model);
        spinnerBuildYear = (Spinner) findViewById(R.id.spinner_buildYear);
        spinnerEngine = (Spinner) findViewById(R.id.spinner_engine);

        listViewCars = (ListView) findViewById(R.id.listViewCars);

        carList = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCar();
            }
        });

        listViewCars.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Car car = carList.get(position);
                carIdRemove = car.carId;
                showDeleteDialog();
                return false;
            }
        });
    }

    private void deleteCar(String CarId){
        DatabaseReference drCar = FirebaseDatabase.getInstance().getReference("cars").child(CarId);
        drCar.removeValue();
        Toast.makeText(this, "Element removed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseCars.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carList.clear();
                for (DataSnapshot carSnapshot : dataSnapshot.getChildren()) {
                    Car car = carSnapshot.getValue(Car.class);
                    carList.add(car);
                }

                CarList adapter = new CarList(MainActivity.this, carList);
                listViewCars.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*
        databaseCars.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ItemListContent.clearList();
                for(DataSnapshot carSnapshot : dataSnapshot.getChildren()){
                    Car car = carSnapshot.getValue(Car.class);

                    ItemListContent.addItem(new ItemListContent.Item(car.carId,
                            "Maker: " + car.carMaker,
                            "Model: " + car.carModel, "Build year: " + car.carBuildYear,
                            "Engine: " + car.carEngine));
                }
                ((ItemFragment) getSupportFragmentManager().findFragmentById(R.id.itemFragment)).notifyDataChange();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */
    }

    private void addCar(){
        String maker = spinnerMaker.getSelectedItem().toString();
        String model = spinnerModel.getSelectedItem().toString();
        String buildyear = spinnerBuildYear.getSelectedItem().toString();
        String engine = spinnerEngine.getSelectedItem().toString();

        String id = databaseCars.push().getKey();

        Car car = new Car(id, maker, model, buildyear, engine);

        databaseCars.child(id).setValue(car);

        Toast.makeText(this, "Car added!", Toast.LENGTH_LONG).show();
    }

    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        deleteCar(carIdRemove);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
