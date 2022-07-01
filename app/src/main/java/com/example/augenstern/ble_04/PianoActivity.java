package com.example.augenstern.ble_04;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

/**
  * 电子琴页面
  * @HNU根根儿
   * QQ：871005218
*/
public class PianoActivity extends Activity implements View.OnTouchListener {
    /**低音*/
    Button lb1;
    Button lb2;
    Button lb3;
    Button lb4;
    Button lb5;
    Button lb6;
    Button lb7;
    /**中音*/
    Button zb1;
    Button zb2;
    Button zb3;
    Button zb4;
    Button zb5;
    Button zb6;
    Button zb7;
    /**高音*/
    Button hb1;
    Button hb2;
    Button hb3;
    Button hb4;
    Button hb5;
    Button hb6;
    Button hb7;
    //调用蓝牙API
    API_BLE api_ble;
    //用于计算时间
    long begin = 0;
    long end = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);
        Init();
    }
    @SuppressLint("ClickableViewAccessibility")
    public void Init(){
        //低中高音按键
        lb1 = findViewById(R.id.button1l); lb1.setOnTouchListener(this);
        lb2 = findViewById(R.id.button2l); lb2.setOnTouchListener(this);
        lb3 = findViewById(R.id.button3l); lb3.setOnTouchListener(this);
        lb4 = findViewById(R.id.button4l); lb4.setOnTouchListener(this);
        lb5 = findViewById(R.id.button5l); lb5.setOnTouchListener(this);
        lb6 = findViewById(R.id.button6l); lb6.setOnTouchListener(this);
        lb7 = findViewById(R.id.button7l); lb7.setOnTouchListener(this);

        zb1 = findViewById(R.id.button1z); zb1.setOnTouchListener(this);
        zb2 = findViewById(R.id.button2z); zb2.setOnTouchListener(this);
        zb3 = findViewById(R.id.button3z); zb3.setOnTouchListener(this);
        zb4 = findViewById(R.id.button4z); zb4.setOnTouchListener(this);
        zb5 = findViewById(R.id.button5z); zb5.setOnTouchListener(this);
        zb6 = findViewById(R.id.button6z); zb6.setOnTouchListener(this);
        zb7 = findViewById(R.id.button7z); zb7.setOnTouchListener(this);

        hb1 = findViewById(R.id.button1h); hb1.setOnTouchListener(this);
        hb2 = findViewById(R.id.button2h); hb2.setOnTouchListener(this);
        hb3 = findViewById(R.id.button3h); hb3.setOnTouchListener(this);
        hb4 = findViewById(R.id.button4h); hb4.setOnTouchListener(this);
        hb5 = findViewById(R.id.button5h); hb5.setOnTouchListener(this);
        hb6 = findViewById(R.id.button6h); hb6.setOnTouchListener(this);
        hb7 = findViewById(R.id.button7h); hb7.setOnTouchListener(this);
        api_ble = MainActivity.api_ble;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.button1l:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();
                    System.out.println("begin"+begin);

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    System.out.println("end"+end);
                    api_ble.sendTone("11",end-begin);
                }
                break;
            }
            case R.id.button2l:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("12",end-begin);
                }
                break;
            }
            case R.id.button3l:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("13",end-begin);
                }
                break;
            }
            case R.id.button4l:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("14",end-begin);
                }
                break;
            }
            case R.id.button5l:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("15",end-begin);
                }
                break;
            }
            case R.id.button6l:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("16",end-begin);
                }
                break;
            }
            case R.id.button7l:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("17",end-begin);
                }
                break;
            }
            case R.id.button1z:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();
                    System.out.println("begin"+begin);

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    System.out.println("end"+end);
                    api_ble.sendTone("21",end-begin);
                }
                break;
            }
            case R.id.button2z:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("22",end-begin);
                }
                break;
            }
            case R.id.button3z:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("23",end-begin);
                }
                break;
            }
            case R.id.button4z:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("24",end-begin);
                }
                break;
            }
            case R.id.button5z:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("25",end-begin);
                }
                break;
            }
            case R.id.button6z:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("26",end-begin);
                }
                break;
            }
            case R.id.button7z:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("27",end-begin);
                }
                break;
            }
            case R.id.button1h:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();
                    System.out.println("begin"+begin);

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    System.out.println("end"+end);
                    api_ble.sendTone("31",end-begin);
                }
                break;
            }
            case R.id.button2h:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("32",end-begin);
                }
                break;
            }
            case R.id.button3h:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("33",end-begin);
                }
                break;
            }
            case R.id.button4h:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("34",end-begin);
                }
                break;
            }
            case R.id.button5h:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("35",end-begin);
                }
                break;
            }
            case R.id.button6h:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("36",end-begin);
                }
                break;
            }
            case R.id.button7h:{
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    begin = System.currentTimeMillis();

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    end = System.currentTimeMillis();
                    api_ble.sendTone("37",end-begin);
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        return false;
    }
}
