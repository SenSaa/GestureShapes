package com.mushin.gestureshapes;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 *
 */

// Implement Event Listener Interfaces.
public class GestureShapes extends View implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener /* , View.OnTouchListener */   , SensorEventListener, ScaleGestureDetector.OnScaleGestureListener {


/////////////////// ___________________________________________________________ ///////////////////
    // _______________________________ Declaring variables. _______________________________  //

    // Paint variables.
    Paint cirPaint;
    Paint cirPaint2;
    Paint cirPaint3;
    Paint linePaint;
    Paint textPaint;
    Paint rectPaint;

    // Screen properties variables.
    int screenWidth;
    int screenHeight;
    int halfWidth;
    int halfHeight;


    // Variables to control the shape drawing based on gestures.

    // Single Tap gesture variables.
    boolean singleTapTouchState = false; // Variable for setting the Boolean state of the Shape drawing after the single tap gesture.
    int  singleTapParity = 1; // Variable for checking the parity (odd or even) of the single tap, to switch (control) the touch state on or off.
    // Double Tap gesture variables.
    boolean doubleTapTouchState = false; // Variable for setting the Boolean state of the Shape drawing after the double tap gesture.
    int doubleTapParity = 1; // Variable for checking the parity (odd or even) of the double tap, to switch (control) the touch state on or off.
    // Long Press gesture variables.
    boolean longPressTouchState = false; // Variable for setting the Boolean state of the Shape drawing after the single tap gesture.
    int  longPressParity = 1; // Variable for checking the parity (odd or even) of the single tap, to switch (control) the touch state on or off.
    // Fling gesture variables.
    boolean flingTouchState = false; // Variable for setting the Boolean state of the Shape drawing after the single tap gesture.
    int  flingParity = 1; // Variable for checking the parity (odd or even) of the single tap, to switch (control) the touch state on or off.
    // Scroll gesture variables.
    boolean scrollTouchState = false; // Variable for setting the Boolean state of the Shape drawing after the single tap gesture.
    int  scrollParity = 1; // Variable for checking the parity (odd or even) of the single tap, to switch (control) the touch state on or off.

    GestureDetectorCompat gestureDetector; // GestureDetector variable.

    // Animation variables.
    int x = 5;
    int y = 5;
    int xVelocity = 5;
    int yVelocity = 5;

    // Sensor variables.
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accelerometer;

    float xLightSenValue;

    float xC; // x coordinate of TouchListener Shape.
    float yC; // y coordinate of TouchListener Shape.

    // View variables referencing the view components created in the Main Activity.
    TextView mainActivityTextView;
    Button mainActivityButton;
    EditText mainActivityEditText;

    MainActivity mainActivityObj;
    MainActivity contxObj;

    // Declare TextView to be accessed by Main Activity.
    TextView txView;
    TextView txView2;

    // Accelerometer x,y,z float values, and text string variables.
    float xAccelgS;
    float yAccelgS;
    float zAccelgS;
    String gSAccelString;

    private ScaleGestureDetector scaleDetector;
    float mLastTouchX;
    float mLastTouchY;
    float mPosX;
    float mPosY;
    private static final int INVALID_POINTER_ID = -1;

/////////////////// ----------------------------------------------------------- ///////////////////


/////////////////// ___________________________________________________________ ///////////////////
    // _______________________________ Initialising variables. _______________________________  //
    // Constructor.
    public GestureShapes(Context context) {
        super(context);

        // Create Paint objectives, and initialise their properties.
        cirPaint = new Paint();
        cirPaint.setColor(Color.MAGENTA);
        cirPaint.setStrokeWidth(3);

        cirPaint2 = new Paint();
        cirPaint2.setColor(Color.CYAN);
        cirPaint2.setStrokeWidth(6);

        cirPaint3 = new Paint();
        cirPaint3.setColor(Color.GREEN);
        cirPaint3.setStrokeWidth(9);

        linePaint = new Paint();
        linePaint.setColor(Color.YELLOW);
        linePaint.setStrokeWidth(12);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(12);
        textPaint.setTextSize(50);

        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStrokeWidth(12);
        rectPaint.setStrokeCap(Paint.Cap.ROUND);

        //Resources resources = GestureShapes.this.getResources();
        //DisplayMetrics metrics = resources.getDisplayMetrics();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        String sW = Integer.toString(screenWidth);
        String sH = Integer.toString(screenHeight);
        Log.v("Width",sW);
        Log.v("Height",sH);

        // Create Gesture Detector object.
        //gestureDetector = new GestureDetectorCompat(this,this);
        gestureDetector = new GestureDetectorCompat(getContext(),this);
        // *** getContext() retrieves context, or otherwise, context can be used which was passed into the constructor.
        // Set the listener which will be called for double-tap and related gestures.
        gestureDetector.setOnDoubleTapListener(this);


        // Android supports several sensors via the SensorManager.
        // Access a SensorManager via getSystemService(SENSOR_SERVICE).
        // You can access the sensor via the sensorManager.getDefaultSensor() method, which takes the sensor type and the delay defined as constants on SensorManager as parameters.
        // Once you acquired a sensor, you can register a SensorEventListener object on it. This listener will get informed, if the sensor data changes.
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE); // Retrieve a SensorManager for accessing sensors.
        //sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE); // Retrieve a SensorManager for accessing sensors.
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // Get the sensor of the type specified in the argument.
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // Get the sensor of the type specified in the argument.
        sensorManager.registerListener(this, lightSensor , SensorManager.SENSOR_DELAY_NORMAL); // Register a SensorEventListener for the given sensor at the given sampling frequency. Where the argument (this) is the SensorEvent Listener object.
        sensorManager.registerListener(this, accelerometer , SensorManager.SENSOR_DELAY_NORMAL); // Register a SensorEventListener for the given sensor at the given sampling frequency. Where the argument (this) is the SensorEvent Listener object.


/*
        MainActivity contxObj = (MainActivity) context; // Create an object holding the Main Activity context.
        MainActivity mainActivityObj = new MainActivity(); // Create an object of the Main Activity class.

        //mainActivityButton = new Button(getContext());  // Create a new Button, and assign it to a Button variable.
        mainActivityButton = contxObj.button; // Using the object holding the Main Activity context, access the button from Main Activity, and assign to a Button variable.
        mainActivityButton.setOnTouchListener(this); // Set OnTouchListener on the Button.

        //mainActivityTextView = new TextView(getContext());
        //mainActivityTextView  = contxObj.textView;

        //mainActivityEditText = new EditText(getContext());
        //mainActivityEditText = contxObj.editText;
*/

        // Create Scale Gesture Detector object.
        scaleDetector = new ScaleGestureDetector(getContext(),this);

        subViews();

    }
/////////////////// ----------------------------------------------------------- ///////////////////


    // Create sub views such as TextView.
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void subViews() {
        // Create a TextView.
        txView = new TextView(getContext()); // / Create an instance of the TextView class.
        txView.setText("gShapeTX"); // Set text view text.
        txView.setTextSize(40);
        txView.setTextColor(Color.MAGENTA);

        // Create a TextView.
        txView2 = new TextView(getContext()); // / Create an instance of the TextView class.
        String x = Float.toString(xAccelgS);
        txView2.setText(x); // Set text view text.
        txView2.setTextSize(40);
        txView2.setTextColor(Color.MAGENTA);
    }


/////////////////// ___________________________________________________________ ///////////////////
    // __________________________________ Drawing Shapes. __________________________________  //
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        halfWidth = screenWidth / 2; // Setting the half of screen width.
        halfHeight = screenHeight / 2; // Setting the half of screen height.
        canvas.drawColor(Color.DKGRAY); // Background color.
        canvas.save(); // Save transformation matrix.
        canvas.translate(halfWidth,halfHeight); // Translating the origin position/point into the centre.

        // Drawing three circles, one in the top left, centre, and bottom right.
        canvas.drawCircle(-halfWidth+20,-halfHeight+20,20,cirPaint);
        canvas.drawCircle(0,0,40,cirPaint2);
        canvas.drawCircle(halfWidth-60,halfHeight-310,60,cirPaint3);

        // The two lines separating the canvas into four quarters.
        canvas.drawLine(0,-halfHeight,0,halfHeight,linePaint);
        canvas.drawLine(-halfWidth,0,halfWidth,0,linePaint);


        // Shape dragged through touch events.
        canvas.drawCircle(mLastTouchX-halfWidth,mLastTouchY-halfHeight,50,linePaint);


        // The gesture drawing, shape animation, and sensor drawing organised into their own methods running within the onDraw.
        onTouchDraw(canvas);
        drawAnimation(canvas);
        lightSenDraw(canvas);
        accelDraw(canvas);

        canvas.restore();

    }

    // ___________ Draw lines when touch gestures are invoked. ___________
    private void onTouchDraw(Canvas canvas) {

        // Draw a line upon singleTap.
        if (singleTapTouchState) { // (singleTapTouchState == true)
            canvas.drawLine(0,0,540,640,cirPaint2);
        }

        // Draw a line upon doubleTap.
        if (doubleTapTouchState) {
            canvas.drawLine(-540,-890,0,0,cirPaint2);
        }

        // Draw a line upon longPress.
        if (longPressTouchState) { // (touchState == true)
            canvas.drawLine(540,-890,0,0,cirPaint);
        }

        // Draw a line upon fling.
        if (flingTouchState) {
            canvas.drawLine(0,0,-540,640,cirPaint);
        }

        // Draw a circle upon scroll.
        if (scrollTouchState) {
            Random randObj = new Random();
            int randXY = randObj.nextInt(400); // Randomising x & y positions of circle.
            int randR = randObj.nextInt(200); // Randomising radius.
            canvas.drawCircle(randXY,randXY,randR,cirPaint3);
        }

    }

    // ____________ Draw animated text. ____________
    private void drawAnimation(Canvas canvas) {

        x = x + xVelocity; // Apply velocity to x position.
        y = y + yVelocity; // Apply velocity to y position.

        canvas.drawText("Left & Right", x, 0, textPaint); // Text animated in x-axis.
        if (x >= 310) { // if x is equal to or exceeds this value, then...
            xVelocity = -5; // Apply negative velocity in x-axis.
        }
        else if (x <= -540) { // if x is equal to or less than this value, then...
            xVelocity = 5; // Apply positive velocity in x-axis.
        }
        else if (x ==  y) { //
            xVelocity = 10; //
        }

        canvas.drawText("Up & Down", 0, y, textPaint); // Text animated in y-axis.
        if (y >= 640) { // if y is equal to or exceeds this value, then...
            yVelocity = -5; // Apply negative velocity in y-axis.
        }
        else if (y <= -850) { // if y is equal to or less than this value, then...
            yVelocity = 5; // Apply positive velocity in y-axis.
        }
        else if (y == x) { //
            yVelocity = -10; //
        }

        invalidate(); // Call "onDraw", so that the shape can be animated.
    }

    // ____________ Draw something when light sensor values change. ____________
    private void lightSenDraw(Canvas canvas) {

        // Draw a circle, with its radius dependant on the light sensor value.
        canvas.drawCircle(0,0,xLightSenValue + 10,textPaint);



    }

    // ____________ Draw something when Accelerometer values change. ____________
    private void accelDraw(Canvas canvas) {

        // Draw a circle, which is Accelerometer dependent.
        // *** The Accelerometer event in question is the one defined in this GestureShapes class.

        //// Prevent shape from disappearing from the screen by executing conditional statements! ////

        // if Accelerometer x-axis event value exceeds the value in which where the ball starts disappearing from the Right screen, then...
        // *** To get the value where the shape starts disappearing from the display, return the accelerometer value as a TextView, and monitoring the accelerometer event values!
        if (xAccelgS > 3.5) {
            canvas.drawCircle((float)(3.5*100),(yAccelgS*100),(zAccelgS+2*100),textPaint); // Instead of passing the accelerometer event value (xAccelgS), enter the value that corresponds to the accelerometer x-axis value where the shape starts disappearinf from the screen.
        }
        // if Accelerometer x-axis event value is lower than the value in which where the ball starts disappearing from the Left screen, then...
        else if (xAccelgS < -3.5) {
            canvas.drawCircle((float)(-3.5*100),(yAccelgS*100),(zAccelgS+2*100),textPaint); // Instead of passing the accelerometer event value (xAccelgS), enter the value that corresponds to the accelerometer x-axis value where the shape starts disappearing from the screen.
    }
        // if Accelerometer y-axis event value exceeds the value in which where the ball starts disappearing from the Bottom screen, then...
        else if (yAccelgS > 8.5) {
            canvas.drawCircle((xAccelgS*100),(float)(8.5*100),(zAccelgS+2*100),textPaint); // Instead of passing the accelerometer event value (yAccelgS), enter the value that corresponds to the accelerometer y-axis value where the shape starts disappearinf from the screen.
        }
        // if Accelerometer y-axis event value is lower than the value in which where the ball starts disappearing from the Top screen, then...
        else if (yAccelgS < -7) {
            canvas.drawCircle((xAccelgS*100),(-7*100),(zAccelgS+2*100),textPaint); // Instead of passing the accelerometer event value (yAccelgS), enter the value that corresponds to the accelerometer y-axis value where the shape starts disappearinf from the screen.
        }
        // Otherwise, draw the circle based on Accelerometer event values.
        else {
        canvas.drawCircle((xAccelgS*100),(yAccelgS*100),(zAccelgS+2*100),textPaint);
        }

    }
/////////////////// ----------------------------------------------------------- ///////////////////


/////////////////// ___________________________________________________________ ///////////////////
// ________________________________________ Event Handling. ____________________________________  //


    // ________________________ Single and Double Taps Event Handling. ________________________  //
    // Callback Methods must be implemented for OnDoubleTapListener interface.
    @Override
    // Single tap callback method ( Do something when Single tap occurs).
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.v("Gesture","onSingleTapConfirmed"); // Verbose log.
        Toast toastSTC = Toast.makeText(this.getContext(), "onSingleTapConfirmed", Toast.LENGTH_SHORT); // Make Toast text.
        toastSTC.show(); // Show Toast text.

        // After a single tap event occurs, if the state of the single tap state variable is set to false, then...
        if (!singleTapTouchState) { // (!singleTapTouchState == false)
            // Set the single tap variable to true.
            singleTapTouchState = true;
        }
        // However, if the state of the single tap state variable is set to true while the parity variable is even, after a single tap event occurs, then...
        else if ( singleTapTouchState && (singleTapParity%2) == 0 ) {
            // Set the single tap variable to false.
            singleTapTouchState = false;
        }
        singleTapParity++; // Increment singleTapParity variable by 1.
/*
        // Show String text representation of the "singleTapParity" integer variable so that it can be shown in the log for testing purposes.
        String countSingleTaps = Integer.toString(singleTapParity); // Convert singleTapParity from Integer to String, so that it can be checked in the log.
        Log.v("SingleTaps","= " + countSingleTaps); // Show countSingleTaps String variable in verbose log.
        invalidate(); //
*/
        return true;
    }
    @Override
    // Double tap callback method ( Do something when Double tap occurs).
    public boolean onDoubleTap(MotionEvent e) {
        Log.v("Gesture","onDoubleTap");
        Toast toastDT = Toast.makeText(this.getContext(), "onDoubleTap", Toast.LENGTH_SHORT);
        toastDT.show();

        // After a double tap event occurs, if the state of the double tap state variable is set to false, then...
        if (!doubleTapTouchState) { // (!doubleTapTouchState == false)
            // Set the double tap variable to true.
            doubleTapTouchState = true;
        }
        // However, if the state of the double tap state variable is set to true while the parity variable is even, after a double tap event occurs, then...
        else if ( doubleTapTouchState && (doubleTapParity%2) == 0 ) {
            // Set the double tap variable to false.
            doubleTapTouchState = false;
        }
        doubleTapParity++; // Increment doubleTapParity variable by 1.
/*
        String countDoubleTaps = Integer.toString(doubleTapParity); // Convert doubleTapParity from Integer to String, so that it can be checked in the log.
        Log.v("DoubleTaps","= " + countDoubleTaps); // Show countDoubleTaps String variable in verbose log.
        invalidate(); // Schedules a call to "onDraw" to draw the next frame.
*/
        return true;
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Toast toastDTE = Toast.makeText(this.getContext(), "onDoubleTapEvent", Toast.LENGTH_SHORT);
        toastDTE.show();
        return true;
    }
/////////////////// ----------------------------------------------------------- ///////////////////

/////////////////// ___________________________________________________________ ///////////////////
    // ______________________________ Gestures Event Handling. ______________________________  //
    // Callback Methods must be implemented for OnGestureListener interface.
    @Override
    public boolean onDown(MotionEvent e) {
        Log.v("Gesture","onDown");
        Toast toastD = Toast.makeText(this.getContext(), "onDown", Toast.LENGTH_SHORT);
        toastD.show();
        return true;
    }
    @Override
    public void onShowPress(MotionEvent e) {
        Log.v("Gesture","onShowPress");
        Toast toastSP = Toast.makeText(this.getContext(), "onShowPress", Toast.LENGTH_SHORT);
        toastSP.show();
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.v("Gesture","onSingleTapUp");
        Toast toastSTU = Toast.makeText(this.getContext(), "onSingleTapUp", Toast.LENGTH_SHORT);
        toastSTU.show();
        return false;
    }
    @Override
    // Scroll callback method ( Do something when Scroll occurs).
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.v("Gesture","onScroll" + "onScroll");
        Toast toastS = Toast.makeText(this.getContext(), "onScroll", Toast.LENGTH_SHORT);
        toastS.show();

        if (!scrollTouchState) {
            scrollTouchState = true;
        }
        else if ( scrollTouchState && (scrollParity%2) == 0 ) {
            scrollTouchState = false;
        }
        scrollParity++;
/*
        String countScroll = Integer.toString(scrollParity);
        Log.v("Scroll","= " + countScroll);
        invalidate();
*/
        return false;
    }
    @Override
    // LongPress callback method ( Do something when Long press occurs).
    public void onLongPress(MotionEvent e) {
        Log.v("Gesture","onLongPress");
        Toast toastLP = Toast.makeText(this.getContext(), "onLongPress", Toast.LENGTH_SHORT);
        toastLP.show();

        if (!longPressTouchState) {
            longPressTouchState = true;
        }
        else if ( longPressTouchState && (longPressParity%2) == 0 ) {
            longPressTouchState = false;
        }
        longPressParity++;
/*
        String countLongPresses = Integer.toString(longPressParity);
        Log.v("LongPresses","= " + countLongPresses);
        invalidate();
*/
    }
    @Override
    // Fling callback method (// Do something when Fling occurs).
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.v("Gesture", "onFling");
        Toast toastF = Toast.makeText(this.getContext(), "onFling", Toast.LENGTH_SHORT);
        toastF.show();

        if (!flingTouchState) {
            flingTouchState = true;
        }
        else if ( flingTouchState && (flingParity%2) == 0 ) {
            flingTouchState = false;
        }
        flingParity++;
/*
        String countFling = Integer.toString(flingParity);
        Log.v("Fling","= " + countFling);
        invalidate();
*/
        return false;
    }

/////////////////// ----------------------------------------------------------- ///////////////////
    /////////////////// ___________________________________________________________ ///////////////////

    // __________________________ Touch screen Motion Event Handling. __________________________  //

    // Called when a touch screen motion event occurs.
    // *** "onTouchEvent" must be implemented - to make it possible for the GestureDetector object to receive events.
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate(); // Schedules a call to "onDraw" to draw the next frame.

        //this.gestureDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        //return super.onTouchEvent(event);

        return gestureDetector.onTouchEvent(event);
    }
*/

    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the GestureDetector object receive events.
        gestureDetector.onTouchEvent(ev);
        // Let the ScaleGestureDetector inspect all events.
        scaleDetector.onTouchEvent(ev);

        final int action = MotionEventCompat.getActionMasked(ev); // Return the masked action being performed, without pointer index information.

        switch (action) { // if the motion event action occurred,
            case MotionEvent.ACTION_DOWN: { // is ACTION_DOWN, then...
                final int pointerIndex = MotionEventCompat.getActionIndex(ev); // For ACTION_POINTER_DOWN or ACTION_POINTER_UP as returned by getActionMasked(), this returns the associated pointer index.
                final float x = MotionEventCompat.getX(ev, pointerIndex); // Returns the X coordinate of this event for the given pointer index (use getPointerId(int) to find the pointer identifier for this index).
                final float y = MotionEventCompat.getY(ev, pointerIndex); // Returns the Y coordinate of this event for the given pointer index (use getPointerId(int) to find the pointer identifier for this index).

                // Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0); // Return the pointer identifier associated with a particular pointer data index is this event.
                break;
            }

            case MotionEvent.ACTION_MOVE: { // if the motion event action occurred, is ACTION_MOVE, then...
                // Find the index of the active pointer and fetch its position
                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(ev, mActivePointerId); // Given a pointer identifier, find the index of its data in the event. // Returns either the index of the pointer (for use with getX(int) et al.), or -1 if there is no data available for that pointer identifier.

                final float x = MotionEventCompat.getX(ev, pointerIndex); // Returns the X coordinate of this event for the given pointer index (use getPointerId(int) to find the pointer identifier for this index).
                final float y = MotionEventCompat.getY(ev, pointerIndex); // Returns the Y coordinate of this event for the given pointer index (use getPointerId(int) to find the pointer identifier for this index).


                // Calculate the distance moved
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx; // The addition assignment operator. //  mPosX = mPosX +  dx;
                mPosY += dy; // The addition assignment operator. //  mPosX = mPosX +  dy;

                invalidate(); // Schedule a call to the onDraw method.

                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP: { // if the motion event action occurred, is ACTION_UP, then...
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: { // if the motion event action occurred, is ACTION_CANCEL, then...
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: { // if the motion event action occurred, is ACTION_POINTER_UP, then...

                final int pointerIndex = MotionEventCompat.getActionIndex(ev); // For ACTION_POINTER_DOWN or ACTION_POINTER_UP as returned by getActionMasked(), this returns the associated pointer index.
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex); // Return the pointer identifier associated with a particular pointer data index is this event.

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0; // If pointerIndex = 0, then newPointerIndex = 1. Else if pointerIndex /= 0, then newPointerIndex = 0.
                    mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex); // Returns the X coordinate of this event for the given pointer index (use getPointerId(int) to find the pointer identifier for this index).
                    mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex); // Returns the Y coordinate of this event for the given pointer index (use getPointerId(int) to find the pointer identifier for this index).
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex); // Return the pointer identifier associated with a particular pointer data index is this event.
                }
                break;
            }
        }
        return true;
    }
/////////////////// ----------------------------------------------------------- ///////////////////


/////////////////// ___________________________________________________________ ///////////////////
    // __________________________ Sensor Event Handling. __________________________  //
    // Callback Methods must be implemented for SensorEventListener interface.
    @Override
    public void onSensorChanged(SensorEvent event) {
        // Do something when sensor values have changed.
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float xLightSenValue = event.values[0];
            String currentLux = Float.toString(xLightSenValue);
            txView.setText(currentLux);
        }
        // If the type of sensor event occurring is the Accelerometer,
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Retrieve Accelerometer event values, and assign it to a variable.
            xAccelgS = -event.values[0]; // Acceleration minus Gx on the x-axis. // The minus is so that we can correct the orientation of the gravity.
            yAccelgS = event.values[1]; // Acceleration minus Gy on the y-axis.
            zAccelgS = event.values[2]; // Acceleration minus Gy on the y-axis.

            //String xAccelString = Float.toString(xAccel);
            //String yAccelString = Float.toString(yAccel);
            //String zAccelString = Float.toString(zAccel);
            gSAccelString = String.format("Accelerometer Values = %sx, %sy, %sz",xAccelgS,yAccelgS,zAccelgS);

        }
        invalidate();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }


    // Callback Methods must be implemented for ScaleGestureDetector interface.
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
/////////////////// ----------------------------------------------------------- ///////////////////


}


/***

For Gesture Detection:

1) Import GestureDetector Class(s).
2) Implement Listener Interface(s).

3) Create GestureDetector object.
4) For "onDoubleTap", set the listener which will be called for double-tap and related gestures.

5) Implement callback methods for listener interface(s).
6) Call "onTouchEvent" for touch screen motion events.

**

 /***

 For Sensor Detection:

 1) Import SensorEventListener and Sensor Classes.
 2) Implement SensorEventListener Interface.

 3) Retrieve a SensorManager for accessing sensors.
 4) Get the sensor of the type specified in the argument.
 5) Register a SensorEventListener for the given sensor.

 6) Implement the two callback methods (onSensorChanged & onAccuracyChanged) for SensorEventListener interface.

 ***/