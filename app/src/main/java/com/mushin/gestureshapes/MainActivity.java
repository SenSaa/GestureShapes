package com.mushin.gestureshapes;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


// ActionBarActivity requires the AppCompat themes, so to apply other type of themes (e.g. Holo), extend Activity instead of ActionBarActivity.
public class MainActivity extends Activity implements SensorEventListener{

    // GestureShapes class object.
    GestureShapes customView;
    GestureShapes customViewContext;

    // View variables.
    TextView textView;
    EditText editText;
    Button button;
    // Light Sensor TextView Variables.
    TextView lightSensorTX;
    TextView lightSensorTX2;
    // Accelerometer TextView Variable.
    TextView accelTX;
    // Step Counter TextView Variable.
    TextView stepCounterTX;
    // Step Counter Button Variable.
    Button stepCounterButton;
    // Gyroscope TextView Variable.
    TextView gyroTX;
    // Proximity TextView Variable.
    TextView proximityTX;
    // Pressure TextView Variable.
    TextView pressureTX;
    // Button to start second Activity.
    Button startActvity2Button;

    // String text Variable.
    String textViewString; // TextView String Text.

    // RelativeLayout ViewGroup Variable.
    RelativeLayout layout;

    // Sensor variables.
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accelerometer;
    private Sensor stepCounter;
    private Sensor gyroscope;
    private Sensor proximity;
    private Sensor pressure;

    // Accelerometer values variables.
    float xAccelmA; // x-coordinate
    float yAccelmA; // y-coordinate
    float zAccelmA; // z-coordinate

    // Step Counter value.
    float stepCounterValueI;
    float stepCounterValueF;
    float stepCounterValueN;
    float stepCounterValueO;


    // Avoid compatiblity issues with older APIs by targeting a new minimum API.
    @TargetApi(Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the activity content a view.
        // In this case, link to activity_main layout xml.
        //setContentView(R.layout.activity_main);
        //textView = (TextView) findViewById(R.id.textView);

        // Link Main Activity to GestureShapes View class, instead of the usual layout xml file.
        //setContentView(new GestureShapes(this));


        // Set Screen Orientation attribute of Activity.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // Dynamically built RelativeLayout with My View (GestureShapes) class and view components like a Button.
        // *** You can only add child views (my custom GestureShapes View class and button for example) to your root view if you pass setContentView() a ViewGroup (like RelativeLayout, LinearLayout).
        layout = new RelativeLayout(this);
        // Define the Layout's characteristics.
        //layout.setGravity(Gravity.CENTER); // Describes how the child views are positioned.
        //layout.setOrientation(LinearLayout.VERTICAL); // Set layout orientation. Works for LinerLayout and not for RelativeLayout.

        // Set generic layout parameters (it affects the entire RelativeLayout Viewgroup components).
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );


        // ____________________________ GestureShapes Custom View. ____________________________  //
        // Add GestureShapes View Class to layout.
        customView = new GestureShapes(this); // Create an instance (object) of GestureShapes class.
        // Set layout parameters for the GestureShapes custom view.
        //customView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        layout.addView(customView); // Add custom object (which holds GestureShapes View) as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // __________________________________ Button View. __________________________________  //
        // Add a Button widget to layout.
        button = new Button(this); // Create an instance of the Button class.
        button.setText("Button!"); // Set button text.
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        buttonParams.addRule(RelativeLayout.CENTER_IN_PARENT); // Center view relative to parent view (TextView ViewGroup).
        //buttonParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        //buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        //buttonParams.setMargins(10,0,50,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(button,buttonParams); // Add button as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // *** TextView and EditText views must be added to the ViewGroup (RelativeLayout) after the custom view, so that it stacks on top of it. Otherwise, it won't show up.

        // ______________________________ TextView for onTouch. ______________________________  //
        // Add a TextView widget to layout.
        textView = new TextView(this); // / Create an instance of the TextView class.
        textViewString = "TextView!";
        textView.setText(textViewString); // Set text view text.
        textView.setTextSize(25);
        textView.setTextColor(Color.GRAY);
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        textViewParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        //textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams.setMargins(700,0,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(textView,textViewParams); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // __________________________________ EditText View. __________________________________  //
        // Add an EditText view to layout.
        editText = new EditText(this);
        editText.setText("EditView!"); // Set Edit view text.
        editText.setTextSize(25);
        editText.setTextColor(Color.GRAY);
        // Set layout parameters for EditText view.
        RelativeLayout.LayoutParams editTextParams = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
                //));
        //editTextParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        editTextParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        editTextParams.setMargins(50,300,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(editText,editTextParams); // Add an Edit View as a child view to the layout.
        // __________________________________ -------------- __________________________________  //

/*
        // ___________________ TextView created within GestureShapes class . _________________  //
        GestureShapes gShapesObj = new GestureShapes(this); // Create an object of the GestureShapes class.
        TextView txV = gShapesObj.txView; // Use the object of the GestureShapes class, to access the "txView" variable, and assign it to a new variable.
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams2 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams2.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams2.setMargins(300,100,300,100); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(txV,textViewParams2); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //
*/

        // ____________________________ TextView for Light Sensor. ____________________________  //
        // Add a TextView widget to layout.
        lightSensorTX = new TextView(this); // / Create an instance of the TextView class.
        lightSensorTX.setText("Light Sensor!"); // Set text view text.
        lightSensorTX.setTextSize(25);
        lightSensorTX.setTextColor(Color.GREEN);
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams3 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams3.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams3.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams3.setMargins(0,1100,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(lightSensorTX,textViewParams3); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //

        // ____________________________ TextView2 for Light Sensor. ____________________________  //
        // Add a TextView widget to layout.
        lightSensorTX2 = new TextView(this); // / Create an instance of the TextView class.
        lightSensorTX2.setText("Light Sensor!"); // Set text view text.
        lightSensorTX2.setTextSize(25);
        lightSensorTX2.setTextColor(Color.GREEN);
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams4 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams4.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams4.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams4.setMargins(0,1200,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(lightSensorTX2,textViewParams4); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // ____________________________ TextView for Accelerometer. ____________________________  //
        // *** The Accelerometer event in question is the one defined in this MainActivity class.
        // Add a TextView.
        accelTX = new TextView(this); // / Create an instance of the TextView class.
        accelTX.setText("Accelerometer"); // Set text view text.
        accelTX.setTextSize(25);
        accelTX.setTextColor(Color.MAGENTA);
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams5 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams5.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams5.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams5.setMargins(0,200,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(accelTX,textViewParams5); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // ____________________________ TextView for Step Counter. ____________________________  //
        // Add a TextView.
        stepCounterTX = new TextView(this); // / Create an instance of the TextView class.
        stepCounterTX.setText("Step Counter"); // Set text view text.
        stepCounterTX.setTextSize(25);
        stepCounterTX.setTextColor(Color.CYAN);
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams6 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams6.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams6.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams6.setMargins(0,1350,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(stepCounterTX,textViewParams6); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // ____________________________ Button for Reset Step Counter. ____________________________  //
        // Add a Button.
        stepCounterButton = new Button(this); // / Create an instance of the TextView class.
        stepCounterButton.setText("Step Counter"); // Set text view text.
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams10 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams10.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams10.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams10.setMargins(0,1435,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(stepCounterButton,textViewParams10); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // ____________________________ TextView for Gyroscope. ____________________________  //
        // Add a TextView.
        gyroTX = new TextView(this); // / Create an instance of the TextView class.
        gyroTX.setText("Gyroscope"); // Set text view text.
        gyroTX.setTextSize(25);
        gyroTX.setTextColor(Color.CYAN);
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams7 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams7.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams7.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams7.setMargins(0,500,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(gyroTX,textViewParams7); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // ____________________________ TextView for Proximity. ____________________________  //
        // Add a TextView.
        proximityTX = new TextView(this); // / Create an instance of the TextView class.
        proximityTX.setText("Proximity"); // Set text view text.
        proximityTX.setTextSize(25);
        proximityTX.setTextColor(Color.MAGENTA);
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams8 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams8.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams8.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams8.setMargins(0,1550,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(proximityTX,textViewParams8); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // ____________________________ TextView for Pressure. ____________________________  //
        // Add a TextView.
        pressureTX = new TextView(this); // / Create an instance of the TextView class.
        pressureTX.setText("Pressure"); // Set text view text.
        pressureTX.setTextSize(25);
        pressureTX.setTextColor(Color.GREEN);
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams9 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams9.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams9.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams9.setMargins(0,1750,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(pressureTX,textViewParams9); // Add a TextView as a child view to the layout.
        // __________________________________ -------------- __________________________________  //


        // ____________________________ Button for Starting another Activity. ____________________________  //
        // Add a Button.
        startActvity2Button = new Button(this); // / Create an instance of the TextView class.
        startActvity2Button.setText("Start Activity 2"); // Set text view text.
        // Set layout parameters for TextView view.
        RelativeLayout.LayoutParams textViewParams11 = new RelativeLayout.LayoutParams(
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, // Width layout property.
                        RelativeLayout.LayoutParams.WRAP_CONTENT));  // Height layout property.
        //));
        //textViewParams11.addRule(RelativeLayout.CENTER_VERTICAL); // Center view Vertically.
        textViewParams11.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center view Horizontally.
        textViewParams11.setMargins(0,750,0,0); // Set Margins to view. Parameters are the pixel values of Left,Top,Right,Bottom.
        layout.addView(startActvity2Button,textViewParams11); // Add a TextView as a child view to the layout.

        startActvity2Button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                return true;
            }
         }
        );
        // __________________________________ -------------- __________________________________  //


        // Set the the content view as this custom view with GestureShapes View class + button.
        setContentView(layout, params);


        // Method for managing changes on TextView upon a Button click.
        //onButtonClick();

        // Method for managing changes on TextView upon a Button Touch.
        onButtonTouch();

        // Android supports several sensors via the SensorManager.
        // Access a SensorManager via getSystemService(SENSOR_SERVICE).
        // You can access the sensor via the sensorManager.getDefaultSensor() method, which takes the sensor type and the delay defined as constants on SensorManager as parameters.
        // Once you acquired a sensor, you can register a SensorEventListener object on it. This listener will get informed, if the sensor data changes.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // Retrieve a SensorManager for accessing sensors.
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // Get the sensor of the type specified in the argument.
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // Get the sensor of the type specified in the argument.
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER); // Get the sensor of the type specified in the argument.
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE); // Get the sensor of the type specified in the argument.
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY); // Get the sensor of the type specified in the argument.
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE); // Get the sensor of the type specified in the argument.
    }


    // Reset TextView
    public void resetTextView() {
        textView.setText("Reset TextView"); // Set text view text.
        textView.setTextSize(20);
        textView.setTextColor(Color.YELLOW);
    }

    //Method used to hold the Step Counter sensor event value, so that we can use it to reset count.
    public void resetStepCountButton() {
        stepCounterValueO = stepCounterValueI;
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Register a SensorEventListener for the given sensor at the given sampling frequency. Where the argument (this) is the SensorEvent Listener object
        sensorManager.registerListener(this, lightSensor , SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, stepCounter , SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope , SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity , SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, pressure , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister SensorEventListener.
        sensorManager.unregisterListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*
    // Method that utilises OnClickListener interface so that TextView can change once Button is clicked.
    public void onButtonClick() {
        button.setOnClickListener(new View.OnClickListener() { // set OnClickListener interface on button view.
            @Override
            public void onClick(View v) { // Callback method for OnClickListener interface.
                textView.setTextColor(Color.WHITE);
                textView.setText("OnClick!");
            }
        });
    }
*/


    // -------------- Runnable interface for Resetting TextView after a delay . ----------------- //
    // Create an Anonymous Runnable interface.
    private Runnable runnable = new Runnable() {
        @Override
        // Method must be implemented for Runnable interface.
        public void run() { // It is a callback method that starts executing the active part of the class' code.
            Log.v("Runnable","Runnable");
            resetTextView(); // Run the method that resets TextView.
        }
    };
    // __________________________________ -------------- __________________________________  //


    // -------------- Handling Button touch event. ----------------- //
    public void onButtonTouch() {
        button.setOnTouchListener(new View.OnTouchListener() { // Set OnTouchListener on the Button.
            //// __________ Touch screen Motion Event Handling. __________  ////
            // Callback Method must be implemented for OnTouchListener interface.
            @Override
            // Callback method for when a touch event is dispatched to a view.
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){ // When the Action Event that occurred is...
                    case MotionEvent.ACTION_DOWN: // When the Action Event that occurred is "ACTION_DOWN",
                // This is equivalent to:
                // if (event.getAction() == MotionEvent.ACTION_DOWN)
                        textView.setTextColor(Color.WHITE);
                        textView.setText("DOWN!");
                    break;

                    case MotionEvent.ACTION_UP: // When the Action Event that occurred is "ACTION_UP",
                        Log.v("onTouch","ACTION_UP");
                        textView.setTextColor(Color.WHITE);
                        textView.setText("UP!");
                        textView.postDelayed(runnable, 1500); // After the specified delay, execute Runnable.
                        /*
                        textView.postDelayed(new Runnable() {
                            public void run() {
                                resetTextView();
                            }
                        }, 2000);
                        */
                    break;

                    case MotionEvent.ACTION_MOVE: // When the Action Event that occurred is "ACTION_MOVE",
                        textView.setTextColor(Color.WHITE);
                        textView.setText("MOVE!");
                    break;
                }
                return true;
            }
        });
        /*
        stepCounterButton.setOnTouchListener(new View.OnTouchListener() { // Set OnTouchListener on the Button.
            //// __________ Touch screen Motion Event Handling. __________  ////
            // Callback Method must be implemented for OnTouchListener interface.
            @Override
            // Callback method for when a touch event is dispatched to a view.
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){ // When the Action Event that occurred is...
                    case MotionEvent.ACTION_DOWN: // When the Action Event that occurred is "ACTION_DOWN",
                        // This is equivalent to:
                        // if (event.getAction() == MotionEvent.ACTION_DOWN)
                        textView.setTextColor(Color.WHITE);
                        textView.setText("DOWN!");
                        break;
                }
                return true;
            }
        });
        */
        stepCounterButton.setOnClickListener(new View.OnClickListener() { // Set OnClickListener on a Button.
        @Override
        // Callback method for when a touch event is dispatched to a view.
        public void onClick(View v) {
            Log.v("OnClick", "Button Clicked");
            resetStepCountButton(); // Call the method to hold the sensor value used for resetting step counter.
        }

        });
    }
    // __________________________________ -------------- __________________________________  //

    /////////////////// ___________________________________________________________ ///////////////////
    // __________________________ Sensor Event Handling. __________________________  //
    // Callback Methods must be implemented for SensorEventListener interface.
    @Override
    public void onSensorChanged(SensorEvent event) {
        // Do something when sensor values have changed.

        // If the type of sensor event occurring is the light sensor,
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            // Retrieve light sensor event values, and assign it to a variable.
            float xLightSenValue = event.values[0];
            // Convert it to a String, and format it.
            //String currentLux = Float.toString(xLightSenValue);
            String currentLux = String.format("Light Sensor Value = %slx",xLightSenValue);
            // Set the String value of the light sensor event as TextView text.
            lightSensorTX.setText(currentLux);
            // Associate value ranges with a text output.
            if (xLightSenValue == 0) {
                lightSensorTX2.setText("Dim");
            }
            else if (xLightSenValue == 1) {
                lightSensorTX2.setText("I see the light!!!");
            }
            else if (xLightSenValue > 1 && xLightSenValue < 10) {
                lightSensorTX2.setText("Slighty Bright");
            }
            else if (xLightSenValue > 10 && xLightSenValue < 100) {
                lightSensorTX2.setText("Bright");
            }
            else if (xLightSenValue > 100 && xLightSenValue < 1000) {
                lightSensorTX2.setText("Very Bright");
            }
            else if (xLightSenValue >= 1000 && xLightSenValue <= 300000 ) {
                lightSensorTX2.setText("My Retinas are burning!");
            }
            else if (xLightSenValue == 300000) {
                lightSensorTX2.setText("Max Luminance!!!!!");
            }
        }

        // If the type of sensor event occurring is the Accelerometer,
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Retrieve Accelerometer event values, and assign it to a variable.
            xAccelmA = -event.values[0]; // Acceleration minus Gx on the x-axis.  // The minus is so that we can correct the orientation of the gravity.
            yAccelmA = event.values[1]; // Acceleration minus Gy on the y-axis.
            zAccelmA = event.values[2]; // Acceleration minus Gy on the y-axis.
            //String xAccelString = Float.toString(xAccel);
            //String yAccelString = Float.toString(yAccel);
            //String zAccelString = Float.toString(zAccel);
            // Convert Accelerometer event values to a String, and format it.
            String accelString = String.format("Accelerometer Values = %sx, %sy, %sz",xAccelmA,yAccelmA,zAccelmA);
            // Set the String value of the Accelerometer event as TextView text.
            accelTX.setText(accelString);
        }

        // If the type of sensor event occurring is the Step Counter,
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            /*
            *** For the Step Counter Sensor, since there is no built-in feature to reset the sensor without rebooting,
             * we need to manually clear the sensor value, if we need to start a new count,
             * so we need to retrieve the sensor event value twice.
            *** A trick is to use an external method to hold the sensor event value (stepCounterValueI), and use that as an old value (stepCounterValueO).
             * Then, subtract it from a sensor event value within the OnSensorChanged method (stepCounterValueF), which will be a new value (since this method will only run when a new value is retrieved).
             * So, when this method runs, it will update the value within this method (stepCounterValueF), and when subtracted by the value stored in the other method(stepCounterValueO), a new count will be returned.
             */
            stepCounterValueI = event.values[0]; // Retrieve  Step Counter event value, and assign it to a variable.
            stepCounterValueF = event.values[0]; // Retrieve  Step Counter event value, and assign it to a variable.
            stepCounterValueN = stepCounterValueF - stepCounterValueO; // Subtract event value stored in external method from the the event value that is updated by onSensorChanged, to reset count.
            String stepCounterString = String.format("Pedometer = %s Steps",stepCounterValueN); // Convert sensor event values to a String, and format it.
            stepCounterTX.setText(stepCounterString);  // Set the String value of the sensor event as TextView text.
        }
        stepCounterValueN++;

        // If the type of sensor event occurring is the Gyroscope,
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Retrieve Gyroscope event values, and assign it to a variable.
            float xGyro = event.values[0]; // x-axis.
            float yGyro = event.values[1]; // y-axis.
            float zGyro = event.values[2]; // z-axis.
            String gyrolString = String.format("GyroScope Values = %sx, %sy, %sz",xGyro,yGyro,zGyro); // Convert sensor event values to a String, and format it.
            gyroTX.setText(gyrolString); // Set the String value of the sensor event as TextView text.
        }

        // If the type of sensor event occurring is the Proximity,
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // Retrieve Proximity event values, and assign it to a variable.
            float proximityValue = event.values[0]; //
            String proximityString = String.format("Proximity = %scm",proximityValue); // Convert sensor event values to a String, and format it.
            proximityTX.setText(proximityString); // Set the String value of the sensor event as TextView text.
        }

        // If the type of sensor event occurring is the Pressure,
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            // Retrieve Pressure event values, and assign it to a variable.
            float pressureValue = event.values[0];
            String pressureString = String.format("Pressure = %shPa",pressureValue); // Convert sensor event values to a String, and format it.
            pressureTX.setText(pressureString); // Set the String value of the sensor event as TextView text.
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }
    ///////////////// ----------------------------------------------------------- /////////////////


    // Use Sticky Immersion (Immersive Mode).
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            layout.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }



}

