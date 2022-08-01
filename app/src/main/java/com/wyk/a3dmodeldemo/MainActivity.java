package com.wyk.a3dmodeldemo;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private float mX1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.img);
        GLSurfaceView view = new GLSurfaceView(this);
        OpenGLRenderer openGLRenderer = new OpenGLRenderer(view,bitmap);
        view.setRenderer(openGLRenderer);
        view.setOnTouchListener(new View.OnTouchListener() {

            private float mY2;
            private float mX2;
            private float mY1;
            private float xDegree = 0;
            private float yDegree = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        mX1 = event.getX();
                        mY1 = event.getY();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP: {
                        mX2 = event.getX();
                        mY2 = event.getY();
                        float vx = mX2 - mX1;
                        vx = (float) Math.toDegrees(0.01*vx);
                        xDegree = vx;
                        if (xDegree > 90) {
                            xDegree = 90;
                        }
                        if (xDegree < -90) {
                            xDegree = -90;
                        }
                        float vy = mY2 - mY1;
                        vy = (float) Math.toDegrees(0.01F*vy);
                        yDegree = vy;
                        if (yDegree > 90) {
                            yDegree = 90;
                        }
                        if (yDegree < -90) {
                            yDegree = -90;
                        }
                        openGLRenderer.setModeX(0, xDegree);
                        openGLRenderer.setModey(0, yDegree);
                        break;
                    }
                    default:

                }


                return true;
            }
        });
        setContentView(view);
    }
}
