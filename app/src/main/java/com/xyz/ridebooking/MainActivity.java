package com.xyz.ridebooking;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Existing Patterns
import com.xyz.ridebooking.singleton.RideBookingSystem;
import com.xyz.ridebooking.factory.Ride;
import com.xyz.ridebooking.factory.RideFactory;
import com.xyz.ridebooking.abstractfactory.EconomyRideFactory;
import com.xyz.ridebooking.abstractfactory.PremiumRideFactory;
import com.xyz.ridebooking.abstractfactory.RideFamilyFactory;
import com.xyz.ridebooking.proxy.RideService;
import com.xyz.ridebooking.proxy.RideServiceProxy;
import com.xyz.ridebooking.chain.DetailsHandler;
import com.xyz.ridebooking.chain.LoginHandler;
import com.xyz.ridebooking.chain.RideHandler;
import com.xyz.ridebooking.chain.RideRequest;
import com.xyz.ridebooking.chain.RideTypeHandler;

// Presenter 1: Observer Pattern
import com.xyz.ridebooking.observer.Driver;
import com.xyz.ridebooking.observer.Rider;
import com.xyz.ridebooking.observer.RideCoordinator;
import com.xyz.ridebooking.observer.DatabaseManager;

// Presenter 2: Template Method Pattern
import com.xyz.ridebooking.templatemethod.RideDispatchTemplate;
import com.xyz.ridebooking.templatemethod.DispatchWorkflowFactory;

// Presenter 3: Strategy Pattern
import com.xyz.ridebooking.strategy.FareCalculationStrategy;
import com.xyz.ridebooking.strategy.FareStrategyContext;
import com.xyz.ridebooking.strategy.NightFareStrategy;
import com.xyz.ridebooking.strategy.RegularFareStrategy;
import com.xyz.ridebooking.strategy.SurgeFareStrategy;

// Presenter 4: Prototype Pattern
import com.xyz.ridebooking.prototype.RideBookingPrototype;
import com.xyz.ridebooking.prototype.RidePrototypeRegistry;

// Presenter 5: Memento Pattern
import com.xyz.ridebooking.memento.BookingCaretaker;
import com.xyz.ridebooking.memento.BookingOriginator;
import com.xyz.ridebooking.memento.RideBookingMemento;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // UI Input Widgets
    private EditText etName;
    private EditText etPickup;
    private EditText etDestination;
    private Spinner spinnerRideType;
    private Spinner spinnerCategory;
    private Spinner spinnerPricingMode;
    private Button btnBookRide;
    private Button btnLogin;
    private Button btnPrefill;
    private Button btnClonePreset;
    private Button btnUndoMemento;
    private Button btnShowDatabase;
    private Button btnClearDatabase;

    // UI Status / Diagnostic Displays
    private TextView tvStatus;
    private TextView tvRideDetails;
    private TextView tvSingleton;
    private TextView tvProxyStatus;
    private TextView tvPatternDiagnostics;

    // Proxy Auth State
    private boolean loggedIn = false;

    // Pattern Coordinators
    private RideCoordinator observerCoordinator;
    private FareStrategyContext fareStrategyContext;
    private RidePrototypeRegistry prototypeRegistry;
    private BookingOriginator mementoOriginator;
    private BookingCaretaker mementoCaretaker;
    private int prototypeCycleIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind View Elements
        initViews();

        // Setup Pattern Controllers
        setupSingletonPattern();
        setupProxyPattern();
        setupObserverPattern();        // PRESENTER 1
        setupStrategyPattern();        // PRESENTER 3
        setupPrototypePattern();       // PRESENTER 4
        setupMementoPattern();         // PRESENTER 5
        setupValidationChain();
        setupDatabaseActions();

        // Primary Booking Action Trigger
        btnBookRide.setOnClickListener(v -> executeBookingPipeline());
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPickup = findViewById(R.id.etPickup);
        etDestination = findViewById(R.id.etDestination);
        spinnerRideType = findViewById(R.id.spinnerRideType);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerPricingMode = findViewById(R.id.spinnerPricingMode);
        btnBookRide = findViewById(R.id.btnBookRide);
        btnLogin = findViewById(R.id.btnLogin);
        btnPrefill = findViewById(R.id.btnPrefill);
        btnClonePreset = findViewById(R.id.btnClonePreset);
        btnUndoMemento = findViewById(R.id.btnUndoMemento);
        btnShowDatabase = findViewById(R.id.btnShowDatabase);
        btnClearDatabase = findViewById(R.id.btnClearDatabase);

        tvStatus = findViewById(R.id.tvStatus);
        tvRideDetails = findViewById(R.id.tvRideDetails);
        tvSingleton = findViewById(R.id.tvSingleton);
        tvProxyStatus = findViewById(R.id.tvProxyStatus);
        tvPatternDiagnostics = findViewById(R.id.tvPatternDiagnostics);

        // Dropdown options
        String[] rideTypes = {"Bike", "Auto", "Car"};
        String[] categories = {"Economy", "Premium"};
        String[] pricingModes = {"Standard Rate (1.0x)", "Peak Rush Surge (1.8x)", "Night Owl Rate (1.25x + ₹20)"};

        spinnerRideType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rideTypes));
        spinnerCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));
        spinnerPricingMode.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pricingModes));
    }

    // ========================================================================
    // SINGLETON PATTERN
    // ========================================================================
    private void setupSingletonPattern() {
        RideBookingSystem system1 = RideBookingSystem.getInstance();
        RideBookingSystem system2 = RideBookingSystem.getInstance();
        boolean same = (system1 == system2);

        tvSingleton.setText("Singleton: Same instance = " + same +
                "\n(ID1: " + Integer.toHexString(System.identityHashCode(system1)) +
                " == ID2: " + Integer.toHexString(System.identityHashCode(system2)) + ")");
    }

    // ========================================================================
    // PROXY PATTERN (Access Control & Security)
    // ========================================================================
    private void setupProxyPattern() {
        btnLogin.setOnClickListener(v -> {
            loggedIn = !loggedIn;
            if (loggedIn) {
                tvProxyStatus.setText("Proxy: Authenticated (Access Granted)");
                tvProxyStatus.setTextColor(getColor(R.color.status_success));
                btnLogin.setText("LOGOUT");
            } else {
                tvProxyStatus.setText("Proxy: Not authenticated");
                tvProxyStatus.setTextColor(getColor(R.color.on_surface));
                btnLogin.setText("LOGIN");
            }
        });
    }

    // ========================================================================
    // PRESENTER 1: OBSERVER PATTERN (Subject-Observer Real-Time Broadcast)
    // ========================================================================
    private void setupObserverPattern() {
        observerCoordinator = new RideCoordinator(this);
        // Registering multiple concrete observers
        observerCoordinator.registerObserver(new Rider());
        observerCoordinator.registerObserver(new Driver());
    }

    // ========================================================================
    // PRESENTER 3: STRATEGY PATTERN (Dynamic Fare Calculation)
    // ========================================================================
    private void setupStrategyPattern() {
        fareStrategyContext = new FareStrategyContext(new RegularFareStrategy());

        // Update strategy when spinner changes
        spinnerPricingMode.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                switch (position) {
                    case 1:
                        fareStrategyContext.setStrategy(new SurgeFareStrategy());
                        break;
                    case 2:
                        fareStrategyContext.setStrategy(new NightFareStrategy());
                        break;
                    default:
                        fareStrategyContext.setStrategy(new RegularFareStrategy());
                        break;
                }
                updateDiagnosticsDisplay("Strategy changed to: " + fareStrategyContext.getStrategy().getStrategyName());
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    // ========================================================================
    // PRESENTER 4: PROTOTYPE PATTERN (Commute Cloning & Presets)
    // ========================================================================
    private void setupPrototypePattern() {
        prototypeRegistry = new RidePrototypeRegistry();

        btnClonePreset.setOnClickListener(v -> {
            // Save current form state to Memento before overwriting with clone
            saveCurrentStateToMemento();

            // Cycle through available presets: College Commute, Office Express, City Mall Trip
            String[] presets = {"College Commute", "Office Express", "City Mall Trip"};
            String selectedPresetKey = presets[prototypeCycleIndex % presets.length];
            prototypeCycleIndex++;

            // PROTOTYPE PATTERN: Clone without calling 'new'
            RideBookingPrototype original = prototypeRegistry.getOriginalPrototype(selectedPresetKey);
            RideBookingPrototype clone = prototypeRegistry.getClonedRide(selectedPresetKey);

            if (clone != null && original != null) {
                // Populate UI from the cloned prototype
                etName.setText(clone.getPassengerName());
                etPickup.setText(clone.getPickup());
                etDestination.setText(clone.getDestination());
                setSpinnerSelection(spinnerRideType, clone.getRideType());
                setSpinnerSelection(spinnerCategory, clone.getCategory());

                tvStatus.setText("Status: Prototype Cloned 📋");
                tvStatus.setTextColor(getColor(R.color.primary));

                tvRideDetails.setText(
                        "── PROTOTYPE PATTERN DEMONSTRATION ──\n" +
                        "• Preset Cloned: '" + clone.getProfileName() + "'\n" +
                        "• Original Prototype Hash : " + original.getMemoryId() + "\n" +
                        "• Cloned Instance Hash    : " + clone.getMemoryId() + "\n" +
                        "✓ Verification: Independent memory instances (" + original.getMemoryId() + " != " + clone.getMemoryId() + ")\n" +
                        "• All passenger & route fields auto-populated from cloned prototype!"
                );

                updateDiagnosticsDisplay("Prototype Cloned: " + clone.getProfileName() + " (" + clone.getMemoryId() + ")");
            }
        });

        // Demo Fill Button (also saves memento snapshot)
        btnPrefill.setOnClickListener(v -> {
            saveCurrentStateToMemento();
            etName.setText("Siddhesh");
            etPickup.setText("Home");
            etDestination.setText("College");
            updateDiagnosticsDisplay("Demo fields filled. Snapshot saved to Memento.");
        });
    }

    // ========================================================================
    // PRESENTER 5: MEMENTO PATTERN (Form State Undo & History Rollback)
    // ========================================================================
    private void setupMementoPattern() {
        mementoOriginator = new BookingOriginator();
        mementoCaretaker = new BookingCaretaker();

        btnUndoMemento.setOnClickListener(v -> {
            if (!mementoCaretaker.canUndo()) {
                tvStatus.setText("Status: No Snapshots ⚠️");
                tvStatus.setTextColor(getColor(R.color.status_warning));
                tvRideDetails.setText("Memento Stack is empty.\nMake changes or fill fields to create an undo history.");
                return;
            }

            // Pop previous snapshot from caretaker and restore in originator
            RideBookingMemento previousState = mementoCaretaker.undo();
            mementoOriginator.restoreFromMemento(previousState);

            // Revert UI fields back to restored snapshot
            etName.setText(mementoOriginator.getPassengerName());
            etPickup.setText(mementoOriginator.getPickupLocation());
            etDestination.setText(mementoOriginator.getDestinationLocation());
            setSpinnerSelection(spinnerRideType, mementoOriginator.getRideType());
            setSpinnerSelection(spinnerCategory, mementoOriginator.getCategory());

            tvStatus.setText("Status: Form Rolled Back ↩️");
            tvStatus.setTextColor(getColor(R.color.primary));
            tvRideDetails.setText(
                    "── MEMENTO PATTERN: UNDO SUCCESSFUL ──\n" +
                    "• Restored Snapshot: " + previousState.getSummary() + "\n" +
                    "• Snapshots remaining in Caretaker: " + mementoCaretaker.getHistoryCount() + "\n" +
                    "✓ Encapsulated state restored without exposing private fields!"
            );

            updateDiagnosticsDisplay("Memento: Reverted to snapshot. " + mementoCaretaker.getHistoryCount() + " remaining.");
        });
    }

    private void saveCurrentStateToMemento() {
        String name = etName.getText().toString();
        String pickup = etPickup.getText().toString();
        String dest = etDestination.getText().toString();
        String type = spinnerRideType.getSelectedItem() != null ? spinnerRideType.getSelectedItem().toString() : "Bike";
        String cat = spinnerCategory.getSelectedItem() != null ? spinnerCategory.getSelectedItem().toString() : "Economy";
        String price = spinnerPricingMode.getSelectedItem() != null ? spinnerPricingMode.getSelectedItem().toString() : "Regular";

        mementoOriginator.setFormState(name, pickup, dest, type, cat, price);
        mementoCaretaker.saveSnapshot(mementoOriginator.saveStateToMemento());
    }

    // ========================================================================
    // CHAIN OF RESPONSIBILITY PATTERN (Sequential Validation)
    // ========================================================================
    private RideHandler loginHandler;

    private void setupValidationChain() {
        loginHandler = new LoginHandler();
        RideHandler detailsHandler = new DetailsHandler();
        RideHandler rideTypeHandler = new RideTypeHandler();

        loginHandler.setNextHandler(detailsHandler);
        detailsHandler.setNextHandler(rideTypeHandler);
    }

    // ========================================================================
    // DATABASE ACTIONS (SQLite Persistence)
    // ========================================================================
    private void setupDatabaseActions() {
        DatabaseManager database = new DatabaseManager(this);

        btnShowDatabase.setOnClickListener(v -> {
            tvStatus.setText("Database Records");
            tvStatus.setTextColor(getColor(R.color.primary));
            tvRideDetails.setGravity(android.view.Gravity.START);
            tvRideDetails.setText(database.getAllRides());
        });

        btnClearDatabase.setOnClickListener(v -> {
            database.clearDatabase();
            tvStatus.setText("Status: Database Cleared");
            tvStatus.setTextColor(getColor(R.color.status_warning));
            tvRideDetails.setGravity(android.view.Gravity.START);
            tvRideDetails.setText("Database cleared successfully.\nAll ride records have been removed.");
        });
    }

    // ========================================================================
    // COMPLETE MULTI-PATTERN EXECUTION PIPELINE
    // ========================================================================
    private void executeBookingPipeline() {
        String name = etName.getText().toString();
        String pickup = etPickup.getText().toString();
        String destination = etDestination.getText().toString();
        String rideType = spinnerRideType.getSelectedItem().toString();
        String category = spinnerCategory.getSelectedItem().toString();

        tvRideDetails.setGravity(android.view.Gravity.START);

        // Save state to Memento before proceeding
        saveCurrentStateToMemento();

        // --------------------------------------------------------------------
        // 1. CHAIN OF RESPONSIBILITY: Validation Check
        // --------------------------------------------------------------------
        RideRequest request = new RideRequest(name, pickup, destination, rideType, loggedIn);

        boolean loginOk = loggedIn;
        boolean detailsOk = !name.trim().isEmpty() && !pickup.trim().isEmpty() && !destination.trim().isEmpty();
        boolean typeOk = rideType.equalsIgnoreCase("Bike") || rideType.equalsIgnoreCase("Auto") || rideType.equalsIgnoreCase("Car");

        if (!loginHandler.handle(request)) {
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
            if (!loginOk) {
                chainTrace.append("Tap 'LOGIN' at the top to pass Proxy Auth.");
            } else if (!detailsOk) {
                chainTrace.append("Enter Name, Pickup & Destination (or tap DEMO FILL / CLONE PRESET).");
            } else {
                chainTrace.append("Choose a valid ride type.");
            }

            tvRideDetails.setText(chainTrace.toString());
            return;
        }

        // --------------------------------------------------------------------
        // 2. FACTORY METHOD: Instantiate Polymorphic Vehicle
        // --------------------------------------------------------------------
        RideFactory factory = new RideFactory();
        Ride ride = factory.createRide(rideType, name);

        int baseFare;
        if (rideType.equalsIgnoreCase("Bike")) {
            baseFare = 50;
        } else if (rideType.equalsIgnoreCase("Auto")) {
            baseFare = 90;
        } else {
            baseFare = 180;
        }

        // --------------------------------------------------------------------
        // 3. ABSTRACT FACTORY: Product Family & Tier Multiplier
        // --------------------------------------------------------------------
        RideFamilyFactory familyFactory;
        double categoryMultiplier;

        if (category.equalsIgnoreCase("Economy")) {
            familyFactory = new EconomyRideFactory();
            categoryMultiplier = 1.0;
        } else {
            familyFactory = new PremiumRideFactory();
            categoryMultiplier = 1.5;
        }

        String familyRide;
        if (rideType.equalsIgnoreCase("Bike")) {
            familyRide = familyFactory.createBike();
        } else {
            familyRide = familyFactory.createCar();
        }

        // --------------------------------------------------------------------
        // 4. PRESENTER 3: STRATEGY PATTERN (Dynamic Fare Calculation)
        // --------------------------------------------------------------------
        FareCalculationStrategy currentStrategy = fareStrategyContext.getStrategy();
        int finalCalculatedFare = fareStrategyContext.executeFareCalculation(baseFare, categoryMultiplier);

        // --------------------------------------------------------------------
        // 5. PROXY PATTERN: Access Protection Gatekeeper
        // --------------------------------------------------------------------
        RideService system = new RideServiceProxy(loggedIn);
        String proxyResult = system.bookRide(ride);

        if (!loggedIn) {
            tvStatus.setText("Status: Booking Denied ❌");
            tvStatus.setTextColor(getColor(R.color.status_denied));
            tvRideDetails.setText(proxyResult);
            return;
        }

        String rideId = "R" + System.currentTimeMillis();

        // --------------------------------------------------------------------
        // 6. PRESENTER 2: TEMPLATE METHOD (Dispatch & Safety Pipeline)
        // --------------------------------------------------------------------
        RideDispatchTemplate dispatchWorkflow = DispatchWorkflowFactory.getWorkflow(rideType);
        List<String> templateLogs = dispatchWorkflow.executeDispatchWorkflow(name, pickup, destination);

        // --------------------------------------------------------------------
        // 7. PRESENTER 1: OBSERVER PATTERN (Broadcast to Observers & DB)
        // --------------------------------------------------------------------
        DatabaseManager db = new DatabaseManager(this);
        db.insertRide(rideId, name, pickup, destination, rideType, category, "Requested");
        List<String> observerTrace = observerCoordinator.notifyObserversWithTrace(rideId, "Accepted");

        // --------------------------------------------------------------------
        // 8. COMPOSE COMPREHENSIVE MULTI-PATTERN CONSOLE TRACE
        // --------------------------------------------------------------------
        tvStatus.setText("Status: Ride Confirmed ✅");
        tvStatus.setTextColor(getColor(R.color.status_success));

        StringBuilder summary = new StringBuilder();
        summary.append("✓ ").append(proxyResult).append("\n");
        summary.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        summary.append("• Passenger : ").append(name).append("\n");
        summary.append("• Route     : ").append(pickup).append(" ➔ ").append(destination).append("\n");
        summary.append("• Vehicle   : ").append(familyRide).append("\n");
        summary.append("• Ride ID   : ").append(rideId).append("\n");
        summary.append("• Final Fare: ₹").append(finalCalculatedFare).append(" (").append(currentStrategy.getStrategyName()).append(")\n\n");

        summary.append("── PRESENTER 3: Strategy Pattern Pricing ──\n");
        summary.append("• Strategy : ").append(currentStrategy.getStrategyName()).append("\n");
        summary.append("• Formula  : ").append(currentStrategy.getFormulaDescription()).append("\n");
        summary.append("• Breakdown: Base ₹").append(baseFare).append(" × ").append(categoryMultiplier).append("x Tier ➔ ₹").append(finalCalculatedFare).append("\n\n");

        summary.append("── PRESENTER 2: Template Method Dispatch ──\n");
        for (String logStep : templateLogs) {
            summary.append("• ").append(logStep).append("\n");
        }
        summary.append("\n");

        summary.append("── PRESENTER 1: Observer Pattern Broadcast ──\n");
        for (String obsLog : observerTrace) {
            summary.append(obsLog).append("\n");
        }
        summary.append("\n");

        summary.append("── Validation & Memory Integrity ──\n");
        summary.append("✓ Chain of Responsibility: 3/3 Handlers Passed\n");
        summary.append("✓ Singleton Instance ID  : ").append(tvSingleton.getText().toString().split("\n")[1]).append("\n");
        summary.append("✓ Memento Caretaker Depth: ").append(mementoCaretaker.getHistoryCount()).append(" saved snapshot(s)");

        tvRideDetails.setText(summary.toString());
        updateDiagnosticsDisplay("Booking successful. Fare: ₹" + finalCalculatedFare + " via " + currentStrategy.getStrategyName());
    }

    private void updateDiagnosticsDisplay(String message) {
        String strategyName = (fareStrategyContext != null && fareStrategyContext.getStrategy() != null)
                ? fareStrategyContext.getStrategy().getStrategyName() : "Standard";
        int mementoCount = (mementoCaretaker != null) ? mementoCaretaker.getHistoryCount() : 0;

        tvPatternDiagnostics.setText(
                "Strategy: " + strategyName +
                "\nMemento: " + mementoCount + " snapshot(s) in Caretaker" +
                "\nLatest: " + message
        );
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        if (spinner == null || value == null) return;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
