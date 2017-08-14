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
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gcssloop.graphics.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

import vmc.machine.impl.employee.card.StringUtils;
import vmc.serialport.SerialPort;

public class MainActivity extends Activity {


    private SerialPort serialPort;
    private FileInputStream mInputStream;
    private FileOutputStream mOutputStream;

    private static String EMPLOYEE_DEVICE_NAME = "/dev/ttymxc1";
    private EditText etPaymentAmount;
    private TextView readCardTv;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    byte[] buffer = (byte[]) msg.obj;
                    StringBuilder stringBuilder = new StringBuilder();
                    StringUtils.toHexString(buffer, buffer.length, stringBuilder);
                    readCardTv.setText(stringBuilder);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView() {
        etPaymentAmount = (EditText) findViewById(R.id.et_pament_amount);
        readCardTv = (TextView) findViewById(R.id.readCardTv);
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


    public void openSerial(View view) {
        try {
            serialPort = new SerialPort(new File(EMPLOYEE_DEVICE_NAME), 9600, 0);
            Log.i("MainActivity", "open serial success");
            Toast.makeText(MainActivity.this, "打开串口成功！", Toast.LENGTH_SHORT).show();
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
            Log.i("MainActivity", "open serial exception");
            Toast.makeText(MainActivity.this, "打开串口出现异常！", Toast.LENGTH_SHORT).show();
        }
        mInputStream = (FileInputStream) serialPort.getInputStream();
        Executors.newSingleThreadExecutor().execute(new ReadRunnable());
    }


    public void sendDataToSerial(View view) {
        String paymentAmountStr = etPaymentAmount.getText().toString().trim();
        try {
            mOutputStream = (FileOutputStream) serialPort.getOutputStream();
            final byte[] bytes = StringUtils.toByteArray("AABB010A0000007001");
            mOutputStream.write(bytes, 0, bytes.length);
            mOutputStream.flush();
            Log.i("MainActivity", "send  data  success to the serial");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("MainActivity", "read data error");
        }
    }

    private class ReadRunnable implements Runnable {

        @Override
        public void run() {
            byte[] buffer = new byte[64];
            int length;
            final InputStream ins = serialPort.getInputStream();
            while (true) {
                try {
                    length = ins.read(buffer);
                    if (length > 0) {
                        Message msg = new Message();
                        msg.what = 11;
                        msg.obj = buffer;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
