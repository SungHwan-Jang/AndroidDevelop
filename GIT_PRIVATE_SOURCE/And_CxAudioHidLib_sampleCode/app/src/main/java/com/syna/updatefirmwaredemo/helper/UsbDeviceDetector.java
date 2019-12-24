package com.syna.updatefirmwaredemo.helper;

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
public class UsbDeviceDetector {
    private static final String TAG = UsbDeviceDetector.class.getSimpleName();
    private static final String ACTION_USB_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    private static final String ACTION_USB_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    private Context mContext = null;
    private List<UsbDevice> mUsbDevices = new ArrayList<UsbDevice>();

    public UsbDeviceDetector(Context context) {
        mContext = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_ATTACHED);
        filter.addAction(ACTION_USB_DETACHED);
        mContext.registerReceiver(mUsbDeviceAttachedReceiver, filter);
        checkHasUsbDevice();
    }

    private List<onUsbDeviceStateChanged> mUsbDeviceStateChangeds = new ArrayList<onUsbDeviceStateChanged>();
    public interface onUsbDeviceStateChanged {
        void plugStateChange(UsbDevice mUsbDevice, boolean isAttached);
    }
    public void updateUsbDeviceState(UsbDevice usbDevice, boolean isAttached) {
        if (isAttached) {
            mUsbDevices.add(usbDevice);
        } else {
            mUsbDevices.remove(usbDevice);
        }

        for (onUsbDeviceStateChanged l : mUsbDeviceStateChangeds) {
            l.plugStateChange(usbDevice, isAttached);
        }
    }
    public void addOnUsbDeviceStateChanged(onUsbDeviceStateChanged listener) {
        mUsbDeviceStateChangeds.add(listener);

        for (UsbDevice usbDevice: mUsbDevices) {
            listener.plugStateChange(usbDevice, true);
        }
    }

    private BroadcastReceiver mUsbDeviceAttachedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            boolean isAttach = action.equalsIgnoreCase(ACTION_USB_ATTACHED);
            Log.d(TAG, "onReceive:" + (isAttach ? "ACTION_USB_ATTACHED":"ACTION_USB_DETACHED"));
            updateUsbDeviceState(device, isAttach);
        }
    };

    private void checkHasUsbDevice() {
        UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        for (UsbDevice device : usbManager.getDeviceList().values()) {
            mUsbDevices.add(device);
        }
    }

    public void release() {
        Log.d(TAG, "release: ");
        mUsbDevices.clear();
        mUsbDeviceStateChangeds.clear();
        if (mContext != null) {
            mContext.unregisterReceiver(mUsbDeviceAttachedReceiver);
            mContext = null;
        }
    }

    @Override
    protected void finalize() {
        release();
    }
}
