package com.syna.updatefirmwaredemo.helper;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuh3 on 12/13/2016.
 */
public class UsbDevicePermissionDetector {

    private static final String TAG = UsbDevicePermissionDetector.class.getSimpleName();
    private static final String ACTION_USB_PERMISSION = "com.example.syna.USB_PERMISSION";

    private Context mContext = null;

    public UsbDevicePermissionDetector(Context context) {
        mContext = context;
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        mContext.registerReceiver(mUsbDevicePermissionReceiver, filter);
    }

    public void requestUsbDevicePermission(UsbDevice usbDevice) {
        UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
        usbManager.requestPermission(usbDevice, mPermissionIntent);
    }

    public void updatePermissionState(UsbDevice usbDevice, boolean isAllowed) {
        for (onPermissionStateChanged l : mPermissionStateChangeds) {
            l.permissionStateChange(usbDevice, isAllowed);
        }
    }

    public interface onPermissionStateChanged {
        void permissionStateChange(UsbDevice usbDevice, boolean isAllowed);
    }

    private List<onPermissionStateChanged> mPermissionStateChangeds = new ArrayList<onPermissionStateChanged>();

    public void addOnPermissionStateChanged(onPermissionStateChanged listener) {
        mPermissionStateChangeds.add(listener);
    }

    private BroadcastReceiver mUsbDevicePermissionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive:mUsbDevicePermissionReceiver ");
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            Log.d(TAG, "onReceive: permission is true");
                            updatePermissionState(device, true);
                        } else {
                            Log.e(TAG, "onReceive: permission is true but no device");
                            updatePermissionState(null, true);
                        }
                    } else {
                        Log.e(TAG, "onReceive: permission is false");
                        updatePermissionState(device, false);
                    }
                }
            }
        }
    };

    public void release() {
        Log.d(TAG, "release: ");
        mPermissionStateChangeds.clear();
        mContext.unregisterReceiver(mUsbDevicePermissionReceiver);
        mContext = null;
    }

}
