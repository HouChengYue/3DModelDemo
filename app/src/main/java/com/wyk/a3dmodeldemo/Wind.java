package com.wyk.a3dmodeldemo;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author houch
 * date: 2022/7/30
 **/
public class Wind extends Mesh {
    private static final String TAG = "Wind";
    private GLSurfaceView mGLSurfaceV1iew;
    private float stage = 0.01f;
    private float mMax = 0.055f;
    private float mMin = 0.005f;

    private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(1, 3, 500, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), r -> new Thread(r, TAG));

    private int time = 0;

    public Wind(float width, float depth, GLSurfaceView GLSurfaceView) {
        this.mGLSurfaceV1iew = GLSurfaceView;
        mExecutor.execute(() -> {
            while (true) {
                try {
                    notify(width, depth);
                    Thread.sleep(50);
                    time += 1;
                    if (time==720){
                        time=0;
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    private void notify(float width, float depth) {
        int row = ((int) (width / stage));
        int lines = (int) (depth / stage);
        float startx = -0.5F * width;
        float startz = 0;
        float[] vertices = new float[(lines + 1) * (row + 1) * 3];
        float[] textCodes = new float[(lines + 1) * (row + 1) * 3];
        short indices[] = new short[lines * row * 2 * 3];
        int vPosition = 0, iPositon = 0;
        for (float z = 0; z <= lines; z++) {
            for (float x = 0; x <= row; x++) {
                float v = mMax - mMin;
                vertices[vPosition] = startx + (x * stage);
                vertices[vPosition + 2] = startz + (z * stage);
                float vertX=vertices[vPosition];
                float vertZ = vertices[vPosition + 2];
                float v1 = mMin + (v * vertZ);
                float v2 =1F-(0.01F*v*vertZ);
                vertices[vPosition + 1] = v1 * (float) Math.sin(0.25F*time +v2* Math.toRadians((1-vertZ) * 360)+v2* Math.toRadians((1-vertX) * 720));
                if (z > 1 && x < row) {
                    int verPosition = vPosition / 3;
                    indices[iPositon] = (short) (verPosition - row - 1);
                    indices[iPositon + 1] = (short) (verPosition - row);
                    indices[iPositon + 2] = (short) (verPosition);
                    indices[iPositon + 3] = (short) (verPosition - row);
                    indices[iPositon + 4] = (short) (verPosition + 1);
                    indices[iPositon + 5] = (short) (verPosition);
                    iPositon += 6;
                }
                textCodes[vPosition]=vertices[vPosition]+0.5f;
                textCodes[vPosition+1]=vertices[vPosition+1]+0.5f;
                textCodes[vPosition+1]=vertices[vPosition+2]+0.5f;
                vPosition += 3;
            }
        }
        setVertices(vertices);
        setIndices(indices);
        setTextureCoordinates(textCodes);
//        setColor(0.6f, 0.6f, 0.6f, 0.6f);
        mGLSurfaceV1iew.requestRender();
    }

}
