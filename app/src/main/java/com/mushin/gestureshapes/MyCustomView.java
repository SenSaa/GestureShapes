package com.mushin.gestureshapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


// A custom view that is added to the activity_main XML layout.
// You can utilise custom view classes as separate app files, or declare them in the same class app file.

class MyCustomView1 extends View {
    Context context;
    Paint paint1;

    public MyCustomView1(Context context) {
        super(context);
        //this.context = context;
        //init(context);
    }

    public MyCustomView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        //this.context = context;
        //init(context);
    }

    public MyCustomView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //this.context = context;
        //init(context);

    }

/*
    public void init(Context context) {
        paint1 = new Paint();
        paint1.setColor(Color.GREEN);
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
        canvas.drawCircle(100, 100, 100, paint1);

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
*/
}