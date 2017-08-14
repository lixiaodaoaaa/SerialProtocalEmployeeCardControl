/*
 * Copyright 2016 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2016-10-02 00:22:33
 *
 */

package com.lixiaodaoaaa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.gcssloop.graphics.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import vmc.serialport.SerialPort;

public class MainActivity extends Activity {


    private SerialPort sp;
    private FileInputStream mInputStream;
    private FileOutputStream mOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void executeJobs() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendDataToSerial();
            }
        }, 6 * 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readSerial();
            }
        }, 6 * 1000);
    }

    private void readSerial() {

        int size;
        try {
            byte[] buffer = new byte[64];
            if (mInputStream == null) return;
            size = mInputStream.read(buffer);
            if (size > 0) {
                onDataReceived(buffer, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }

    private void sendDataToSerial() {
        try {
            mOutputStream = (FileOutputStream) sp.getOutputStream();
            mOutputStream.write('\n');
            mOutputStream.write("write data to the  serial".getBytes());
            Log.i("MainActivity", "send  data  success to the serial");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("MainActivity", "read data error");
        }
    }

    private void openSerial() {

    }


    public void openSerial(View view) {
        try {
            sp = new SerialPort(new File("/dev/ttymxc3"), 115200, 0);
            Log.i("MainActivity", "open serial success");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
            Log.i("MainActivity", "open serial exception");
        }
        mInputStream = (FileInputStream) sp.getInputStream();
    }
}
