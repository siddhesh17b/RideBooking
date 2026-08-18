package com.xyz.ridebooking;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.xyz.ridebooking.singleton.RideBookingSystem;
import com.xyz.ridebooking.factory.Ride;
import com.xyz.ridebooking.factory.RideFactory;
import com.xyz.ridebooking.proxy.RideService;
import com.xyz.ridebooking.proxy.RideServiceProxy;
import com.xyz.ridebooking.abstractfactory.EconomyRideFactory;
import com.xyz.ridebooking.abstractfactory.PremiumRideFactory;
import com.xyz.ridebooking.abstractfactory.RideFamilyFactory;
import com.xyz.ridebooking.observer.Driver;
import com.xyz.ridebooking.observer.RideCoordinator;
import com.xyz.ridebooking.observer.Rider;
import com.xyz.ridebooking.observer.DatabaseManager;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerRideType;
    private Spinner spinnerCategory;
    private Button btnBookRide;
    private TextView tvStatus;
    private TextView tvRideDetails;
    private TextView tvSingleton;
    private boolean loggedIn = false;
    private TextView tvProxyStatus;
    private Button btnLogin;
    private Button btnClearDatabase;
    private Button btnShowDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvProxyStatus = findViewById(R.id.tvProxyStatus);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            loggedIn = !loggedIn;

            if(loggedIn) {
                tvProxyStatus.setText("Proxy: Authentication Successful");
                btnLogin.setText("LOGOUT");
            } else {
                tvProxyStatus.setText("Proxy: Not authenticated");
                btnLogin.setText("LOGIN");
            }
        });

        RideCoordinator coordinator = new RideCoordinator(this);
        coordinator.registerObserver(new Rider());
        coordinator.registerObserver(new Driver());

        spinnerRideType = findViewById(R.id.spinnerRideType);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnBookRide = findViewById(R.id.btnBookRide);
        tvStatus = findViewById(R.id.tvStatus);
        tvRideDetails = findViewById(R.id.tvRideDetails);

        String[] rideTypes = {"Bike", "Auto", "Car"};
        String[] categories = {"Economy", "Premium"};

        DatabaseManager database = new DatabaseManager(this);

        btnShowDatabase = findViewById(R.id.btnShowDatabase);

        btnShowDatabase.setOnClickListener(v -> {
            tvStatus.setText("Database Records");
            tvRideDetails.setText(database.getAllRides());
        });

        btnClearDatabase = findViewById(R.id.btnClearDatabase);

        tvSingleton = findViewById(R.id.tvSingleton);

        spinnerRideType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rideTypes));
        spinnerCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

        btnBookRide.setOnClickListener(v -> {
            String name = ((android.widget.EditText) findViewById(R.id.etName)).getText().toString();
            String pickup = ((android.widget.EditText) findViewById(R.id.etPickup)).getText().toString();
            String destination = ((android.widget.EditText) findViewById(R.id.etDestination)).getText().toString();
            String rideType = spinnerRideType.getSelectedItem().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            RideFactory factory = new RideFactory();
            Ride ride = factory.createRide(rideType, name);

            RideFamilyFactory familyFactory;

            if(category.equalsIgnoreCase("Economy"))
                familyFactory = new EconomyRideFactory();
            else
                familyFactory = new PremiumRideFactory();

            String familyRide;

            if(rideType.equalsIgnoreCase("Bike"))
                familyRide = familyFactory.createBike();
            else
                familyRide = familyFactory.createCar();

            RideService system = new RideServiceProxy(loggedIn);
            String result = system.bookRide(ride);

            if(!loggedIn) {
                tvStatus.setText("Status: Booking Denied");
                tvRideDetails.setText(result);
                return;
            }

            String rideId = "R" + System.currentTimeMillis();

            database.insertRide(rideId, name, pickup, destination, rideType, category, "Requested");

            coordinator.notifyObservers(rideId, "Accepted");

            tvStatus.setText("Status: Ride Accepted");
            tvRideDetails.setText(result + "\nCategory: " + familyRide + "\nRide ID: " + rideId);
        });

        btnClearDatabase.setOnClickListener(v -> {
            database.clearDatabase();
            tvStatus.setText("Database cleared");
            tvRideDetails.setText("All ride records have been deleted.");
        });
    }
}