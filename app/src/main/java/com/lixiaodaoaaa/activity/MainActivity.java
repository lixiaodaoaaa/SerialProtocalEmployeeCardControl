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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gcssloop.graphics.R;
import com.google.common.eventbus.Subscribe;

import de.greenrobot.event.EventBus;
import vmc.employee.card.event.ReadSerialEvent;
import vmc.employee.card.protocal.CardFunctionImpl;

public class MainActivity extends Activity {


    private EditText etPaymentAmount;
    private TextView readCardTv;


    CardFunctionImpl cardFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        cardFunction = new CardFunctionImpl();
        cardFunction.init(this);
    }


    private void initView() {
        etPaymentAmount = (EditText) findViewById(R.id.et_pament_amount);
        readCardTv = (TextView) findViewById(R.id.readCardTv);
    }


    public void sendDataToSerial(View view) {
        String paymentAmountStr = etPaymentAmount.getText().toString().trim();
        if (TextUtils.isEmpty(paymentAmountStr)) {
            paymentAmountStr = "1";
        }
        cardFunction.cutAmount(Float.valueOf(paymentAmountStr));
    }

    public void cancleCutMoney(View view) {
        cardFunction.cancelCutAmount();
    }

    public void readMachineId(View view) {
        cardFunction.readMachineID();
    }

    public void readLastMoney(View view) {
        cardFunction.readLastTimeAvailbleAmount();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(ReadSerialEvent readSerialEvent) {
        if (!TextUtils.isEmpty(readSerialEvent.getMsg())) {
            readCardTv.setText(readSerialEvent.getMsg());
        }
    }

    public void readMachineStatus(View view) {
        cardFunction.readConnectServerStatus();
    }

    public void readCustomerCardNumber(View view) {
        cardFunction.readCustomerCardNumber();
    }
}
