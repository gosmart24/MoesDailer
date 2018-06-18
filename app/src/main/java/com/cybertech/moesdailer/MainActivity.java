package com.cybertech.moesdailer;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


public class MainActivity extends AppCompatActivity {
    EditText edtPhoneNo;
    TextView lblinfo;
    BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPhoneNo = (EditText) findViewById(R.id.edtPhoneNumber);
        lblinfo = (TextView) findViewById(R.id.lblinfo);

        bt = new BluetoothSPP(this);

        if (!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not Supported!"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                lblinfo.setText(message);
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buttonClickEvent(View v) {
        String phoneNo = edtPhoneNo.getText().toString();
        try {

            switch (v.getId()) {
                case R.id.btnAterisk:
                    lblinfo.setText("");
                    phoneNo += "*";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnHash:
                    lblinfo.setText("");
                    phoneNo += "#";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnZero:
                    lblinfo.setText("");
                    phoneNo += "0";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnOne:
                    lblinfo.setText("");
                    phoneNo += "1";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnTwo:
                    lblinfo.setText("");
                    phoneNo += "2";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnThree:
                    lblinfo.setText("");
                    phoneNo += "3";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnFour:
                    lblinfo.setText("");
                    phoneNo += "4";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnFive:
                    lblinfo.setText("");
                    phoneNo += "5";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnSix:
                    lblinfo.setText("");
                    phoneNo += "6";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnSeven:
                    lblinfo.setText("");
                    phoneNo += "7";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnEight:
                    lblinfo.setText("");
                    phoneNo += "8";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnNine:
                    lblinfo.setText("");
                    phoneNo += "9";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btndel:
                    lblinfo.setText("");
                    if (phoneNo != null && phoneNo.length() > 0) {
                        phoneNo = phoneNo.substring(0, phoneNo.length() - 1);
                    }

                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnClearAll:
                    lblinfo.setText("");
                    edtPhoneNo.setText("");
                    break;
                case R.id.btnCall:
                    if (phoneNo.trim().equals("")) {
                        lblinfo.setText("Please enter a number to call on!");
                    } else {
                        Boolean isHash = false;
                        if (phoneNo.subSequence(phoneNo.length() - 1, phoneNo.length()).equals("#")) {
                          //  phoneNo = phoneNo.substring(0, phoneNo.length() - 1);
                            String callInfo = phoneNo;
                            edtPhoneNo.setText("");
                            if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                                bt.send(callInfo, true);
                            } else {
                                Toast.makeText(MainActivity.this, "Please connect to Moes Device!!", Toast.LENGTH_SHORT).show();
                            }
                            //Intent callIntent = new Intent(Intent.ACTION_CALL);
                            // callIntent.setData(Uri.parse(callInfo));
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            //startActivity(callIntent);
                        } else {
                            String callInfo = "tel:" + phoneNo;
                            // Intent callIntent = new Intent(Intent.ACTION_CALL);
                            // callIntent.setData(Uri.parse(callInfo));
                            //startActivity(callIntent);
                            edtPhoneNo.setText("");
                            if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                                bt.send(callInfo, true);
                            } else {
                                Toast.makeText(MainActivity.this, "Please connect to Moes Device!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;
            }

        } catch (Exception ex) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }

    }

    public void setup() {
        if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
            bt.send("1", true);
        } else {
            // bt.autoConnect("moes");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        bt.stopService();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                //finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        bt.disconnect();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exitApp) {
            System.exit(0);
            return true;
        } else if (id == R.id.connect) {
            if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                //bt.disconnect();
                Toast.makeText(getApplicationContext(), "Device already connected!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}