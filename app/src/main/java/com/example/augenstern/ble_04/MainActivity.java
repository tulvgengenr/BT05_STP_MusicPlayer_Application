package com.example.augenstern.ble_04;
/**
  * 主页面
  * @HNU根根儿
  * QQ：871005218
  * 参考工程：https://github.com/IOXusu/Android-BLE-Demo 万分感谢！
*/
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "activity"+ this.getClass().getSimpleName();
    public ListView mListView;
    public Button disConnected,scan,piano;
    public TextView statusConnected;

    public static API_BLE api_ble =new API_BLE();//BLE的API

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
        Init_Item();
        api_ble.setInit(this,mListView);
        api_ble.Init_Bluetooth();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //控件初始化
    private void Init_Item(){
        disConnected = findViewById(R.id.disConnected);//断开连接按钮
        disConnected.setOnClickListener(this);
        scan = findViewById(R.id.scan);//扫描设备按钮
        scan.setOnClickListener(this);
        piano = findViewById(R.id.piano_button);//转电子琴页面按钮
        piano.setOnClickListener(this);
        statusConnected = findViewById(R.id.status);//连接状态
        statusConnected.setText("请连接BT05: 00:15:83:31:58:D3");
        mListView = findViewById(R.id.devicesList);//设备列表
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(api_ble.mAdapter.isDiscovering()) api_ble.mAdapter.cancelDiscovery();//停止当前的搜索
                String info = ((TextView)view).getText().toString();
                Log.d(TAG, "onCreate: info = "+info);
                String adress = info.substring(info.length() - 17);
                Log.d(TAG, "onCreate: adress = "+adress);//蓝牙地址
                api_ble.device = api_ble.mAdapter.getRemoteDevice(adress);//根据地址获取蓝牙设备
                Log.d(TAG, "onItemClick: device = "+adress);
                api_ble.bluetoothGatt = api_ble.device.connectGatt(MainActivity.this, false,api_ble.bluetoothGattCallback);//client向server发起连接
            }
        });
    }

    /**
     * 监听器
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
               //断开连接
            case R.id.disConnected:
                if(api_ble.bluetoothGatt.connect()){
                    api_ble.bluetoothGatt.disconnect();
                    api_ble.bluetoothGatt.close();
                    Log.d(TAG, "onClick: 手动断开连接.....");
                    api_ble.scanDevices();
                }
                break;
                //搜索设备
            case R.id.scan:
                api_ble.scanDevices();
                break;
            case R.id.piano_button:
                startActivity1();
        }
    }
    public void startActivity1(){
        Intent intent = new Intent(this, PianoActivity.class);
        startActivity(intent);
    }

}
