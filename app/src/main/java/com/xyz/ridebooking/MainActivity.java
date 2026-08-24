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
import com.xyz.ridebooking.chain.DetailsHandler;
import com.xyz.ridebooking.chain.LoginHandler;
import com.xyz.ridebooking.chain.RideHandler;
import com.xyz.ridebooking.chain.RideRequest;
import com.xyz.ridebooking.chain.RideTypeHandler;

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

        // ==================== PROXY PATTERN ====================
        // Login state used by the Proxy to control access.
        tvProxyStatus = findViewById(R.id.tvProxyStatus);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            loggedIn = !loggedIn;

            if(loggedIn) {
                tvProxyStatus.setText("Proxy: Authenticated (Access Granted)");
                tvProxyStatus.setTextColor(getColor(R.color.status_success));
                btnLogin.setText("LOGOUT");
            } else {
                tvProxyStatus.setText("Proxy: Not authenticated");
                tvProxyStatus.setTextColor(getColor(R.color.on_surface));
                btnLogin.setText("LOGIN");
            }
        });

        // ==================== OBSERVER PATTERN ====================

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

        // ==================== DATABASE ====================

        DatabaseManager database = new DatabaseManager(this);

        // ==================== CHAIN OF RESPONSIBILITY ====================

        RideHandler loginHandler = new LoginHandler();
        RideHandler detailsHandler = new DetailsHandler();
        RideHandler rideTypeHandler = new RideTypeHandler();

        loginHandler.setNextHandler(detailsHandler);
        detailsHandler.setNextHandler(rideTypeHandler);

        // ==================== DATABASE DISPLAY ====================

        btnShowDatabase = findViewById(R.id.btnShowDatabase);

        btnShowDatabase.setOnClickListener(v -> {
            tvStatus.setText("Database Records");
            tvStatus.setTextColor(getColor(R.color.primary));
            tvRideDetails.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tvRideDetails.setGravity(android.view.Gravity.START);
            tvRideDetails.setText(database.getAllRides());
        });

        btnClearDatabase = findViewById(R.id.btnClearDatabase);

        // ==================== SINGLETON PATTERN ====================

        tvSingleton = findViewById(R.id.tvSingleton);

        RideBookingSystem system1 = RideBookingSystem.getInstance();
        RideBookingSystem system2 = RideBookingSystem.getInstance();

        boolean same = system1 == system2;

        tvSingleton.setText("Singleton: Same instance = " + same +
                "\n(ID1: " + Integer.toHexString(System.identityHashCode(system1)) +
                " == ID2: " + Integer.toHexString(System.identityHashCode(system2)) + ")");

        // ==================== DEMO PREFILL ====================

        Button btnPrefill = findViewById(R.id.btnPrefill);
        if(btnPrefill != null) {
            btnPrefill.setOnClickListener(v -> {
                ((android.widget.EditText) findViewById(R.id.etName)).setText("Siddhesh");
                ((android.widget.EditText) findViewById(R.id.etPickup)).setText("Home");
                ((android.widget.EditText) findViewById(R.id.etDestination)).setText("College");
            });
        }

        spinnerRideType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rideTypes));
        spinnerCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

        btnBookRide.setOnClickListener(v -> {

            String name = ((android.widget.EditText) findViewById(R.id.etName)).getText().toString();
            String pickup = ((android.widget.EditText) findViewById(R.id.etPickup)).getText().toString();
            String destination = ((android.widget.EditText) findViewById(R.id.etDestination)).getText().toString();
            String rideType = spinnerRideType.getSelectedItem().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            tvRideDetails.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tvRideDetails.setGravity(android.view.Gravity.START);

            // ==================== CHAIN OF RESPONSIBILITY TRACER ====================

            RideRequest request = new RideRequest(name, pickup, destination, rideType, loggedIn);

            boolean loginOk = loggedIn;
            boolean detailsOk = !name.trim().isEmpty() && !pickup.trim().isEmpty() && !destination.trim().isEmpty();
            boolean typeOk = rideType.equalsIgnoreCase("Bike") || rideType.equalsIgnoreCase("Auto") || rideType.equalsIgnoreCase("Car");

            if(!loginHandler.handle(request)) {
                tvStatus.setText("Status: Request Denied ❌");
                tvStatus.setTextColor(getColor(R.color.status_denied));

                StringBuilder chainTrace = new StringBuilder();
                chainTrace.append("Validation Failed in Chain of Responsibility\n");
                chainTrace.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
                chainTrace.append("── Chain of Responsibility Tracer ──\n");
                chainTrace.append(loginOk ? "✓ [1/3] LoginHandler: PASSED (Proxy Authenticated)\n" : "❌ [1/3] LoginHandler: REJECTED (User Not Logged In)\n");
                chainTrace.append(loginOk ? (detailsOk ? "✓ [2/3] DetailsHandler: PASSED (All Fields Valid)\n" : "❌ [2/3] DetailsHandler: REJECTED (Missing Name or Locations)\n") : "⏭️ [2/3] DetailsHandler: SKIPPED\n");
                chainTrace.append((loginOk && detailsOk) ? (typeOk ? "✓ [3/3] RideTypeHandler: PASSED (Valid Ride Type)\n" : "❌ [3/3] RideTypeHandler: REJECTED\n") : "⏭️ [3/3] RideTypeHandler: SKIPPED\n");
                chainTrace.append("\n👉 Next Step: ");
                if(!loginOk) {
                    chainTrace.append("Tap 'LOGIN' at the top to pass Proxy Auth.");
                } else if(!detailsOk) {
                    chainTrace.append("Enter Name, Pickup & Destination (or tap DEMO FILL).");
                } else {
                    chainTrace.append("Choose a valid ride type.");
                }

                tvRideDetails.setText(chainTrace.toString());
                return;
            }

            // ==================== FACTORY METHOD ====================

            RideFactory factory = new RideFactory();
            Ride ride = factory.createRide(rideType, name);

            int baseFare;
            if(rideType.equalsIgnoreCase("Bike")) {
                baseFare = 50;
            } else if(rideType.equalsIgnoreCase("Auto")) {
                baseFare = 90;
            } else {
                baseFare = 180;
            }

            // ==================== ABSTRACT FACTORY ====================

            RideFamilyFactory familyFactory;
            double categoryMultiplier;

            if(category.equalsIgnoreCase("Economy")) {
                familyFactory = new EconomyRideFactory();
                categoryMultiplier = 1.0;
            } else {
                familyFactory = new PremiumRideFactory();
                categoryMultiplier = 1.5;
            }

            String familyRide;
            if(rideType.equalsIgnoreCase("Bike"))
                familyRide = familyFactory.createBike();
            else
                familyRide = familyFactory.createCar();

            int finalFare = (int) (baseFare * categoryMultiplier);

            // ==================== PROXY PATTERN ====================
            // Client uses the Proxy instead of directly accessing RealRideService.
            RideService system = new RideServiceProxy(loggedIn);

            // Proxy checks authentication and either blocks the request
            // or forwards it to RealRideService.
            String result = system.bookRide(ride);

            // Fallback check
            if(!loggedIn) {
                tvStatus.setText("Status: Booking Denied ❌");
                tvStatus.setTextColor(getColor(R.color.status_denied));
                tvRideDetails.setText(result);
                return;
            }

            String rideId = "R" + System.currentTimeMillis();

            // ==================== DATABASE ====================

            database.insertRide(rideId, name, pickup, destination, rideType, category, "Requested");

            // ==================== OBSERVER PATTERN ====================

            coordinator.notifyObservers(rideId, "Accepted");

            tvStatus.setText("Status: Ride Accepted ✅");
            tvStatus.setTextColor(getColor(R.color.status_success));
            tvRideDetails.setText(
                "✓ " + result + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "• Passenger: " + name + "\n" +
                "• Route: " + pickup + " ➔ " + destination + "\n" +
                "• Vehicle: " + familyRide + "\n" +
                "• Est. Fare: ₹" + finalFare + " (Base: ₹" + baseFare + " × " + categoryMultiplier + "x " + category + ")\n" +
                "• Ride ID: " + rideId + "\n\n" +
                "── Chain of Responsibility Tracer ──\n" +
                "✓ [1/3] LoginHandler: PASSED (Proxy Auth Valid)\n" +
                "✓ [2/3] DetailsHandler: PASSED (Passenger & Route Valid)\n" +
                "✓ [3/3] RideTypeHandler: PASSED (" + rideType + " Confirmed)\n\n" +
                "── Observer Pattern Broadcast ──\n" +
                "📢 RideCoordinator notified 2 observers:\n" +
                "  ↳ Driver Observer: Ride " + rideId + " is Accepted\n" +
                "  ↳ Rider Observer: Ride " + rideId + " is Accepted"
            );
        });

        // ==================== DATABASE ====================

        btnClearDatabase.setOnClickListener(v -> {
            database.clearDatabase();
            tvStatus.setText("Status: Database Cleared");
            tvStatus.setTextColor(getColor(R.color.status_warning));
            tvRideDetails.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tvRideDetails.setGravity(android.view.Gravity.START);
            tvRideDetails.setText("Database cleared successfully.\nAll ride records have been removed.");
        });
    }
}