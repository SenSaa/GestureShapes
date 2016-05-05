package com.mushin.gestureshapes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *
 **/

public class SecondActivity extends Activity implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {


/////////////////// ___________________________________________________________ ///////////////////
    // _______________________________ Declaring variables. _______________________________  //

    // Declare Button & TextView variables.
    Button button;
    TextView textView1;
    // Declare TextViews variables to monitor Scale Gesture Detector.
    TextView textViewAD;
    TextView textViewAUP;
    TextView textViewAM;
    TextView textViewAC;
    TextView textViewAPUP;
    TextView textViewAPD;

    // Declare Scale Gesture Detector variable.
    ScaleGestureDetector scaleGestureDetect;

    //
    int INVALID_POINTER_ID = -1;
    // The ‘active pointer’ is the one currently moving our object.
    private int activePointerId = INVALID_POINTER_ID;
    // Scale Gesture Detector's touch motion event positional variables.
    float mLastTouchX;
    float mLastTouchY;
    float mPosX;
    float mPosY;

    //
    float touchX;
    float touchY;

    int[] locationX = new int[0];
    int[] locationY = new int[1];


/////////////////// ----------------------------------------------------------- ///////////////////


    /////////////////// ___________________________________________________________ ///////////////////
    // _______________________________ Initialising variables. _______________________________  //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Accessing Button widget from XML layout and setting up OnClickListener.
        button = (Button) findViewById(R.id.button);
        // Call setOnClickListener method on button, so that the Button can be manipulated using the onClick callback method.
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          // Intent creation, so that another Activity can be initiated from this Activity class.
                                          Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                                          startActivity(intent);
                                      }
                                  }
        );

        // Referencing activity_main XML layout TextView widget.
        textView1 = (TextView) findViewById(R.id.textView1);
        // Enable setOnTouchListener so that we can manipulate TextView using the OnTouch callback method.
        textView1.setOnTouchListener(this);

        // Accessing activity_main XML layout TextView widgets.
        textViewAD = (TextView) findViewById(R.id.textViewAD); // Action_Down TextView.
        textViewAUP = (TextView) findViewById(R.id.textViewAUP); // Action_Up TextView.
        textViewAM = (TextView) findViewById(R.id.textViewAM); // Action_Move TextView.
        textViewAC = (TextView) findViewById(R.id.textViewAC); // Action_Cancel TextView.
        textViewAPD = (TextView) findViewById(R.id.textViewAPD); // Action_Pointer_Down TextView.
        textViewAPUP = (TextView) findViewById(R.id.textViewAPUP); // Action_Pointer_Up TextView.


        // Create Scale Gesture Detector object.
        scaleGestureDetect = new ScaleGestureDetector(this, this);

        // Retrieving Display Metrics from Resources class, to find screen width and screen height of device.
        DisplayMetrics disMetrics = getResources().getDisplayMetrics();
        int screenWidth = disMetrics.widthPixels;
        int screenHeight = disMetrics.heightPixels;

/*
        //button.getLocationOnScreen(locationX);

        Bitmap bm = Bitmap.createBitmap( screenWidth/2, screenHeight/2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        canvas.drawLine(0, 0, canvas.getWidth(), canvas.getHeight(), textPaint);

        ImageView iView = (ImageView) findViewById(R.id.imageView);
        iView.setImageBitmap(bm);
*/
/*
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);
        canvas.drawCircle(50, 50, 100, paint);
*/

        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);


    }
/////////////////// ----------------------------------------------------------- ///////////////////


    /////////////////// ___________________________________________________________ ///////////////////
    // Reset TextView
    public void resetTxView() {
        textViewAD.setText("ACTION_DOWN"); // Set text view text.
        textViewAD.setTextColor(Color.YELLOW);
        textViewAUP.setText("ACTION_UP"); // Set text view text.
        textViewAUP.setTextColor(Color.YELLOW);
        textViewAM.setText("ACTION_MOVE"); // Set text view text.
        textViewAM.setTextColor(Color.YELLOW);
        textViewAC.setText("ACTION_CANCEL"); // Set text view text.
        textViewAC.setTextColor(Color.YELLOW);
        textViewAPD.setText("ACTION_POINTER_DOWN"); // Set text view text.
        textViewAPD.setTextColor(Color.YELLOW);
        textViewAPUP.setText("ACTION_POINTER_UP"); // Set text view text.
        textViewAPUP.setTextColor(Color.YELLOW);
    }
/////////////////// ----------------------------------------------------------- ///////////////////

    /////////////////// ___________________________________________________________ ///////////////////
    // -------------- Runnable interface for Resetting TextView after a delay . ----------------- //
    // Create an Anonymous Runnable interface.
    Runnable runnable = new Runnable() {
        @Override
        // Method must be implemented for Runnable interface.
        public void run() { // It is a callback method that starts executing the active part of the class' code.
            resetTxView(); // Run the method that resets TextView.
        }
    };
/////////////////// ----------------------------------------------------------- ///////////////////


    /////////////////// ___________________________________________________________ ///////////////////
    // __________________________ Touch screen Motion Event Handling. __________________________  //
    // Called when a touch screen motion event occurs.
    // *** "onTouchEvent" must be implemented - to make it possible for the GestureDetector object to receive ev
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Let the ScaleGestureDetector object receive motion events.
        scaleGestureDetect.onTouchEvent(event);

        // getActionMasked() is the method for retrieving the motion touch event.
        switch (MotionEventCompat.getActionMasked(event)) { // if the motion event action performed (such as ACTION_DOWN or ACTION_POINTER_DOWN),

            case MotionEvent.ACTION_DOWN: { // if the action performed is ACTION_DOWN (if view is being touched - or first finger being held down/touching view), then...
                textViewAD.setText("Active");
                textViewAD.setTextColor(Color.RED);
                textViewAD.postDelayed(runnable, 1500); // After the specified delay, execute Runnable.

                // ACTION_DOWN starts gesture.
                // The pointer data for this finger is always at index 0 in the MotionEvent.

                //// (1) "getActionIndex" and store it - so that we can pass it to getX and getY. ////
                final int pointerIndex = MotionEventCompat.getActionIndex(event); // getActionIndex() returns the index (pointer data) for this finger.
                //// (2) "getX"  & "getY" and store it - so that we can retrieve the X & Y positions of the touch event. ////
                final float x = MotionEventCompat.getX(event, pointerIndex); // Returns the X coordinate of this event for the given pointer index. (use getPointerId(int) to find the pointer identifier for this index).
                final float y = MotionEventCompat.getY(event, pointerIndex); // Returns the Y coordinate of this event for the given pointer index. (use getPointerId(int) to find the pointer identifier for this index).
                //// (3) Assign X & Y positions to global variables - so that we can use them to control the coordinates of views, shapes or whichever objects as required. ////
                // ** This also allows us to - Remember where we started (for dragging):
                touchX = x;
                touchY = y;

                //// (4) Use "getPointerId" - to save the ID of this pointer - (for dragging). ////
                activePointerId = MotionEventCompat.getPointerId(event, 0); // Return the pointer identifier associated with a particular pointer data index is this event.

                //// (5) Set the x & y coordinates of the view - according to the position of the touch event. ////
                textView1.setX(touchX - (textView1.getWidth()) / 2);
                textView1.setY(touchY - (textView1.getHeight()) * 4);
/*
                // Position the view's x & y coordinates using the Margins properties of the LayoutParams. // (left margin = x, top margin = y).
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); //The WRAP_CONTENT parameters can be replaced by an absolute width and height or the FILL_PARENT option)
                params.leftMargin = (int) touchX; //Your X coordinate
                params.topMargin = (int) touchY; //Your Y coordinate
                textView1.setLayoutParams(params);
*/
                break;
            }

            case MotionEvent.ACTION_UP: { // if the action performed is ACTION_UP (if view is not being touched - or last finger moved away / not touching view anymore), then...
                textViewAUP.setText("Active");
                textViewAUP.setTextColor(Color.RED);
                textViewAUP.postDelayed(runnable, 1500); // After the specified delay, execute Runnable.

                // ACTION_UP ends the gesture.

                // The last data sample about the finger that went up is at index 0.

                activePointerId = INVALID_POINTER_ID;

                break;
            }

            case MotionEvent.ACTION_MOVE: { // if the action performed is ACTION_MOVE (if touch event occurring is a movement across view - or finger moving across view), then...
                textViewAM.setText("Active");
                textViewAM.setTextColor(Color.RED);
                textViewAM.postDelayed(runnable, 1500); // After the specified delay, execute Runnable.

                //// (1) Use "findPointerIndex" and store it - to find the index of the active pointer and fetch its position. ////
                final int pointerIndex = MotionEventCompat.findPointerIndex(event, activePointerId); // Given a pointer identifier, find the index of its data in the event. // Returns either the index of the pointer (for use with getX(int) et al.), or -1 if there is no data available for that pointer identifier.
                //// (2) "getX"  & "getY" and store it - so that we can retrieve the X & Y positions of the touch event. ////
                final float x = MotionEventCompat.getX(event, pointerIndex); // Returns the X coordinate of this event for the given pointer index. (use getPointerId(int) to find the pointer identifier for this index).
                final float y = MotionEventCompat.getY(event, pointerIndex); // Returns the Y coordinate of this event for the given pointer index. (use getPointerId(int) to find the pointer identifier for this index).


                //// (3) Calculate the distance moved - by subtracting the action_down position (old touch position) from the position the pointer is being moved to (new touch position). ////
                final float dx = x - touchX;
                final float dy = y - touchY;

                //// (4) Calculate the new positions - by adding the distance to the same positional variable - which updates the position as movement occurs. ////
                mPosX += dx; // The addition assignment operator. //  mPosX = mPosX +  dx;
                mPosY += dy; // The addition assignment operator. //  mPosX = mPosX +  dy;

                //invalidate(); // Schedule a call to the onDraw method.

                //// (5) Assign X & Y positions to global variables - so that we can use them to control the coordinates of views, shapes or whichever objects as required. ////
                // ** This also allows us to - Remember this touch position for the next move event.
                touchX = x;
                touchY = y;


                //// (6) Set the x & y coordinates of the view - according to the position of the touch event. ////
                textView1.setX(touchX - (textView1.getWidth()) / 2); // Set view x-coordinate. // We subtract by 1/2 of view's width, so that we can centre the touch position on the view.
                textView1.setY(touchY - (textView1.getHeight()) * 4); // Set view y-coordinate.

                break;
            }

            case MotionEvent.ACTION_CANCEL: { // if the action performed is ACTION_CANCEL (if touch gesture is aborted), then...
                textViewAC.setText("Active");
                textViewAC.setTextColor(Color.RED);
                textViewAC.postDelayed(runnable, 1500); // After the specified delay, execute Runnable.

                activePointerId = INVALID_POINTER_ID;

                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN: { // if the action performed is ACTION_POINTER_DOWN (extra fingers that enter the screen beyond the first), then...
                textViewAPD.setText("Active");
                textViewAPD.setTextColor(Color.RED);
                textViewAPD.postDelayed(runnable, 1500); // After the specified delay, execute Runnable.

                // The pointer data for _extra fingers that enter the screen beyond the first_ is at the index returned by getActionIndex() from ACTION_POINTER_DOWN.


                break;
            }

            case MotionEvent.ACTION_POINTER_UP: { // if the action performed is ACTION_POINTER_UP (a finger leaves the screen but at least one finger is still touching it), then...
                textViewAPUP.setText("Active");
                textViewAPUP.setTextColor(Color.RED);
                textViewAPUP.postDelayed(runnable, 1500); // After the specified delay, execute Runnable.

                // The last data sample about the _finger that went up_ is at the index returned by getActionIndex() from ACTION_POINTER_DOWN.
                final int pointerIndex = MotionEventCompat.getActionIndex(event); // getActionIndex() returns the index (pointer data) for this finger.

                break;
            }

        }

        return true;
    }
/////////////////// ----------------------------------------------------------- ///////////////////


    /////////////////// ___________________________________________________________ ///////////////////
    // Variables to store the view's x&y positions.
    float dX, dY;

    //// __________ Touch screen Motion Event Handling. __________  ////
    // Callback Method must be implemented for OnTouchListener interface.
    @Override
    // Callback method for when a touch event is dispatched to a view.
    public boolean onTouch(View view, MotionEvent event) {
/*
        //// (1) Assign the view parameter to a textView variable, making it the view that will be manipulated in this method. ////
        view = textView1;

        switch (event.getActionMasked()) { // if the motion event action performed is...

            case MotionEvent.ACTION_DOWN: // if the motion event action performed is ACTION_DOWN, then...

                //// (2) Retrieve coordinates of the view in ACTION_DOWN.
                dX = view.getX() - event.getRawX(); // Retrieve x-position of view, and store it in variable.
                dY = view.getY() - event.getRawY(); // Retrieve y-position of view, and store it in variable.
                break;

            case MotionEvent.ACTION_MOVE: // if the motion event action performed is ACTION_MOVE, then...

                //// (2) Animate view in ACTION_MOVE.
                view.animate()
                        .x(event.getRawX() + dX) // Assign the raw position of the motion event to the x-coordinate of the view, and add the distance retrieved in the ACTION_DOWN event.
                        .y(event.getRawY() + dY) // Assign the raw position of the motion event to the y-coordinate of the view, and add the distance retrieved in the ACTION_DOWN event.
                        .setDuration(0) // Set the duration or delay of the animation.
                        .start(); // Start animation.
                break;

            default:
                return false;
        }
*/
        return true;
    }
/////////////////// ----------------------------------------------------------- ///////////////////


    /////////////////// ___________________________________________________________ ///////////////////
    // Callback Methods must be implemented for ScaleGestureDetector interface.
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }
/////////////////// ----------------------------------------------------------- ///////////////////

/*
    class MyCustomView extends View {
        Context context;
        Paint paint1;
        public MyCustomView(Context context) {
            super(context);
            //this.context = context;
            init();
        }
        public MyCustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
            //this.context = context;
            init();
        }
        public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            //this.context = context;
            init();

        }

        public void init() {
            paint1 = new Paint();
            paint1.setColor(Color.GREEN);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // your custom drawing
            canvas.drawRect(0, 0, 50, 50, paint1);
            canvas.drawCircle(40,40,40, paint1);

        }

    }
*/
}

/////////////////// A custom view that is added to the activity_main XML layout. ///////////////////

    class MyCustomView2 extends View implements ScaleGestureDetector.OnScaleGestureListener {
        Context context;
        Paint paint1;
        SecondActivity secActObj = new SecondActivity();
        float x;
        float y;
        int width;
        int height;
        ScaleGestureDetector scaleGestDetect;
        float touchX;
        float touchY;

        public MyCustomView2(Context context) {
            super(context);
            this.context = context;
            init(context);
        }

        public MyCustomView2(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            init(context);
        }

        /*
            public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                //this.context = context;
                init(context);

            }
        */

        public void init(Context context) {
            paint1 = new Paint();
            paint1.setColor(Color.GREEN);

            x = getX();
            y = getY();
            width = getWidth();
            height = getHeight();

            scaleGestDetect = new ScaleGestureDetector(context,this);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            //canvas.drawColor(Color.BLACK);

            // your custom drawing

            // top left
            canvas.drawRect(0, 0, 100, 100, paint1);
            canvas.drawCircle(touchX, touchY, 100, paint1);

            // bottom left
            canvas.drawRect(0, 700, 100, 800, paint1);
            canvas.drawCircle(100, 700, 100, paint1);

            // top right
            canvas.drawRect(500, 0, 600, 100, paint1);
            canvas.drawCircle(500, 100, 100, paint1);

            // bottom right
            canvas.drawRect(500, 700, 600, 800, paint1);
            canvas.drawCircle(500, 700, 100, paint1);

        }

        public boolean onTouchEvent(MotionEvent event) {

            scaleGestDetect.onTouchEvent(event);

            if ( MotionEventCompat.getActionMasked(event)== MotionEvent.ACTION_DOWN) { // if the action performed is ACTION_DOWN (if view is being touched - or first finger being held down/touching view), then...

                //// (1) "getActionIndex" and store it - so that we can pass it to getX and getY. ////
                final int pointerIndex = MotionEventCompat.getActionIndex(event); // getActionIndex() returns the index (pointer data) for this finger.
                //// (2) "getX"  & "getY" and store it - so that we can retrieve the X & Y positions of the touch event. ////
                final float x = MotionEventCompat.getX(event, pointerIndex); // Returns the X coordinate of this event for the given pointer index. (use getPointerId(int) to find the pointer identifier for this index).
                final float y = MotionEventCompat.getY(event, pointerIndex); // Returns the Y coordinate of this event for the given pointer index. (use getPointerId(int) to find the pointer identifier for this index).
                //// (3) Assign X & Y positions to global variables - so that we can use them to control the coordinates of views, shapes or whichever objects as required. ////
                // ** This also allows us to - Remember where we started (for dragging):
                touchX = x;
                touchY = y;

                //// (4) Use "getPointerId" - to save the ID of this pointer - (for dragging). ////
                //activePointerId = MotionEventCompat.getPointerId(event, 0); // Return the pointer identifier associated with a particular pointer data index is this event.
            }
            invalidate();
            return true;
        }


        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }


