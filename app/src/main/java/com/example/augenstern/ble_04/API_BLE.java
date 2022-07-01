package com.example.augenstern.ble_04;
/**
 * 为BLE蓝牙开发提供的API
 * @HNU根根儿
 * QQ：871005218
 * 参考工程：https://github.com/IOXusu/Android-BLE-Demo 万分感谢！
 */
import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.content.Context.BLUETOOTH_SERVICE;

public class API_BLE implements Serializable {
    /**
     * 成员变量
     * */
    private static final long serialVersionUID = 9060527069391618394L;
    private static final String notifyUUID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    private final String TAG = "activity"+ this.getClass().getSimpleName();
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private MainActivity activity;
    private ListView mListView;

    public BluetoothManager mManager;
    public BluetoothAdapter mAdapter;
    public BluetoothGatt bluetoothGatt;
    public BluetoothDevice device;

    public ArrayAdapter<String> mStringArrayAdapter;
    public List<String> mStringList  = new ArrayList<>();

    private BluetoothGattCharacteristic writeCharacteristic;//发送数据
    private BluetoothGattCharacteristic notifyCharacteristic;
    /**
     * 蓝牙初始化
     * */
    public void Init_Bluetooth(){
        mManager = (BluetoothManager) activity.getSystemService(BLUETOOTH_SERVICE);
        mAdapter = mManager.getAdapter();
        if(!mAdapter.isEnabled()){
            mAdapter.disable();
        }
        mAdapter.enable();
        Log.d(TAG, "on Init_Bluetooth: 蓝牙已经打开.....");
        if(mAdapter.isDiscovering()){
            mAdapter.cancelDiscovery();
            Log.d(TAG, "on Init_Bluetooth: 关闭搜索设备.....");
        }
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0);
    }
    /**
     * 初始化组件
     * */
    public void setInit(MainActivity activity, ListView listView){
        this.activity = activity;
        this.mListView = listView;
    }
    /**
     * 扫描附近设备
     * */
    public void scanDevices(){
        System.out.println(mStringList);
        mStringArrayAdapter = new ArrayAdapter<>(activity.getBaseContext(),android.R.layout.simple_list_item_1,mStringList);
        mListView.setAdapter(mStringArrayAdapter);
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        activity.registerReceiver(mReceiver, intentFilter);
        mStringList.clear();
        mStringArrayAdapter.notifyDataSetChanged();
        Set pairedDevice = mAdapter.getBondedDevices();
        mStringList.add("已配对的设备：");
        if(pairedDevice.size() > 0){
            for(Iterator it = pairedDevice.iterator(); it.hasNext();){
                BluetoothDevice pDevice = (BluetoothDevice) it.next();
                mStringList.add("设备名称："+pDevice.getName()+"\n设备地址："+pDevice.getAddress());
                Log.d(TAG, "onReceive: 已配对的设备："+pDevice.getName()+"\t"+pDevice.getAddress());
                mStringArrayAdapter.notifyDataSetChanged();
            }
        }else {
            mStringList.add("无");
            Log.d(TAG, "on scanDevices: 没有已配对的设备.....");
        }
        mStringList.add("附近的设备：");
        mAdapter.startDiscovery();
        Log.d(TAG, "on scanDevices: 开始扫描.....");
    }
    /**
     * 搜索设备的广播
     * */
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mStringList.add("设备名称："+device.getName()+"\n设备地址："+device.getAddress());
                Log.d(TAG, "onReceive: 搜索到的设备："+device.getName()+"\t"+device.getAddress());
                mStringArrayAdapter.notifyDataSetChanged();
            }
        }
    };
    /**
     * 建立GATT的回调
     * */
    public final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG, "onConnectionStateChange: 连接成功.....");
                gatt.discoverServices();
            }
            else {
                Log.d(TAG, "onConnectionStateChange: 连接断开.....");
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d(TAG, "onCharacteristicWrite: 写入成功.....");
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {//连接成功
                for (BluetoothGattService BluetoothGattService : gatt.getServices()) {
                    Log.e(TAG, "--->BluetoothGattService" + BluetoothGattService.getUuid().toString());
                    Log.d(TAG, "onItemClick: BluetoothGattServer 服务 -----> " + BluetoothGattService.getUuid().toString());
                    //遍历所有特征
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : BluetoothGattService.getCharacteristics()) {
                        Log.e("---->gattCharacteristic", bluetoothGattCharacteristic.getUuid().toString());
                        Log.d(TAG, "onItemClick: bluetoothGattCharacteristic 特征 -----> " + bluetoothGattCharacteristic.getUuid().toString());
                        String str = bluetoothGattCharacteristic.getUuid().toString();
                        Log.d(TAG, "onServicesDiscovered: notifyUUID = "+notifyUUID);
                        Log.d(TAG, "onServicesDiscovered: bluetoothGattCharacteristic = "+bluetoothGattCharacteristic);
                        notifyCharacteristic = bluetoothGattCharacteristic;
                        Log.d(TAG, "onServicesDiscovered: notifyCharacteristic 特征 = "+notifyCharacteristic);
                        writeCharacteristic = bluetoothGattCharacteristic;
                        Log.d(TAG, "onServicesDiscovered: writeCharacteristic 特征 = "+writeCharacteristic);
                    }
                }
            }
            if ((null == writeCharacteristic) || (null == notifyCharacteristic)) {
                //连接失败
                Log.d(TAG, "onServicesDiscovered: 失败.....");
            }else {
                setNotify(notifyCharacteristic);
                setEnableNotify(notifyCharacteristic, true);
                Log.d(TAG, "onServicesDiscovered: 成功.....");
            }

        }
        //接收信息
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
//            final byte[] value = characteristic.getValue();
//            Log.d(TAG, "onCharacteristicChanged: 收到数据： "+bytesToHexFun1(value));
//            MainActivity.this.runOnUiThread(new Runnable() {
//                public void run() {
//                    ListView arraylistview=(ListView)findViewById(R.id.receiveList);
//                    adtDevices=new ArrayAdapter<String>(MainActivity.this,R.layout.array_item,listDevices);
//                    String str=bytesToHexFun1(value);
//                    int n=0;
//                    listDevices.add(n,str);
//                    arraylistview.setAdapter(adtDevices);
//                n++;
//            }
//            });
        }
    };
    /**
     * 查看是否带有可通知属性
     * */
    public boolean setNotify(BluetoothGattCharacteristic data_char) {
        if (0 != (data_char.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY)) {
            bluetoothGatt.setCharacteristicNotification(data_char, true);
            BluetoothGattDescriptor descriptor = data_char.getDescriptor(
                    UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            bluetoothGatt.writeDescriptor(descriptor);
        } else if (0 != (data_char.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE)) {
            bluetoothGatt.setCharacteristicNotification(data_char, true);
            BluetoothGattDescriptor descriptor = data_char.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            bluetoothGatt.writeDescriptor(descriptor);
        }
        return true;
    }
    /**
     * 蓝牙使能
     * */
    public boolean setEnableNotify(BluetoothGattCharacteristic data_char, boolean enable) {
        bluetoothGatt.setCharacteristicNotification(data_char, enable);
        return true;
    }
    /**
     * 发送16进制信息
     * */
    public void sendMessage(String str)
    {
        byte[] mbyte = HexToByteArr(str);
        writeCharacteristic.setValue(mbyte);
        bluetoothGatt.writeCharacteristic(writeCharacteristic);
        Log.d(TAG, "onClick: 数据 %d"+str+"发送成功.....");
    }
    /**
     * 发送对应音调和时间的音阶
     * 特别注意这里的不是节拍是时间，单位是ms
     * */
    public void sendTone(String yindiao, long time){
        System.out.println("time:"+time);
        long num = (time)/20;//根据手机延迟调参
        String str_time = "";
        String string  = Long.toHexString(num);
        if(string.length() == 1) {
            str_time += "0";
            str_time += string.charAt(0);
        }
        else {
            str_time = string.substring(string.length()-2, string.length());
        }
        this.sendMessage(yindiao+str_time);
    }
    /**
     * 字节数组转16进制
     * */
    public static String bytesToHexFun1(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }
            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }
    /**
     * 16进制转字节数组
     * */
    public static byte[] HexToByteArr(String HexStr) {
        char[] charArr = HexStr.toCharArray();
        byte btArr[] = new byte[charArr.length / 2];
        int index = 0;
        int highBit;
        int lowBit;
        for (int i = 0; i < charArr.length; i++) {
            if(charArr[i] >= '0' && charArr[i] <= '9')
                highBit = charArr[i] - '0';
            else
                highBit = charArr[i] - 'a' + 10;
            i++;
            if(charArr[i] >= '0' && charArr[i] <= '9')
                lowBit =  charArr[i] - '0';
            else
                lowBit = charArr[i] - 'a' + 10;
            btArr[index] = (byte) (highBit << 4 | lowBit);
            index++;
        }
        return btArr;
    }



}
