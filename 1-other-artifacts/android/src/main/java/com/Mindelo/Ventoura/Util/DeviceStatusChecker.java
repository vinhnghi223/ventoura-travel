package com.Mindelo.Ventoura.Util;



import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class DeviceStatusChecker {

    /* FIELDS */

    private Activity activity;
    private Pass pass;
    private List<Resource> resourcesList;

    /* TYPES */

    public enum Resource {
            NETWORK, GPS, BLUETOOTH
    }

    public static abstract class Pass {
            public abstract void pass();
    }

    /* API */

    public DeviceStatusChecker(Activity activity) {
            this.activity = activity;
    }

    public void check(Resource... resources) {
            resourcesList = Arrays.asList(resources);
            if (resourcesList.contains(Resource.GPS) && !isGPSActivated(activity)) {
                    new AlertDialog.Builder(activity).setMessage("启用 GPS ？").setCancelable(false).setPositiveButton("GPS", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                    }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    activity.finish();
                            }
                    }).create().show();
            } else if (resourcesList.contains(Resource.NETWORK) && !isNetworkActivated(activity)) {
                    new AlertDialog.Builder(activity).setMessage("Network required.").setCancelable(false).setPositiveButton("3G", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.setClassName("com.android.phone", "com.android.phone.Settings");
                                    activity.startActivity(intent);
                            }
                    }).setNeutralButton("WiFi", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                    }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    activity.finish();
                            }
                    }).create().show();
            } else if (resourcesList.contains(Resource.BLUETOOTH) && !isBluetoothActivated(activity)) {
                    new AlertDialog.Builder(activity).setMessage("Bluetooth required.").setCancelable(false).setPositiveButton("Bluetooth",
                                    new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                    activity.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                                            }
                                    }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    activity.finish();
                            }
                    }).create().show();
            }else{
               pass.pass();
            }
    }

    public DeviceStatusChecker pass(Pass pass) {
            this.pass = pass;
            return this;
    }

    public boolean isGPSActivated(Context context) {
            return ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean isBluetoothActivated(Context context) {
            return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public boolean isNetworkActivated(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
    }

}
