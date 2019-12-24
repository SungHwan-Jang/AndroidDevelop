package com.syna.updatefirmwaredemo.helper;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.util.Log;

import com.syna.FirmwareParam;
import com.syna.updatefirmwaredemo.usbctrl.SynaUsbDeviceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuh3 on 12/13/2016.
 */
public class SvcModClient {
    private static final String TAG = SvcModClient.class.getSimpleName();

    private static final Object mSyncObj = new Object();
    private static SvcModClient mInstance;
    private Context mContext = null;
    private UsbDevice mUsbDevice;
    private SynaUsbDeviceBase mSynaUsbDeviceBase = null;

    private UsbDeviceDetector mUsbDetector;
    private UsbDevicePermissionDetector mUsbPermissionDetector;

    private List<DevicePlugListener> devicePlugListeners = new ArrayList<>();
    private List<DevicePermissionListener> devicePermissionListeners = new ArrayList<>();

    public static SvcModClient get() {
        return mInstance;
    }

    public static void initClient(Context context) {
        synchronized (mSyncObj) {
            if (mInstance == null) {
                mInstance = new SvcModClient();
                mInstance.init(context);
            }
        }
    }

    private void init(Context context) {
        mContext = context;
        mUsbDetector = new UsbDeviceDetector(mContext);
        mUsbDetector.addOnUsbDeviceStateChanged(mUsbDeviceStateChanged);
        mUsbPermissionDetector = new UsbDevicePermissionDetector(mContext);
        mUsbPermissionDetector.addOnPermissionStateChanged(mUsbPermissionStateChanged);
    }

    public static void releaseClient() {
        synchronized (mSyncObj) {
            if (mInstance != null) {
                mInstance.release();
                mInstance = null;
            }
            Log.d(TAG, "releaseClient done");
        }
    }

    public UsbDevice getUsbDevice(){
        return mUsbDevice;
    }

    public static SynaUsbDeviceBase getUsbDeviceBase(){
        if(null != get()){
            return get().getUsbCtrl();
        }
        return null;
    }

    public SynaUsbDeviceBase getUsbCtrl(){
        return mSynaUsbDeviceBase;
    }

    public String getDisplayName() {
        if (null != mSynaUsbDeviceBase) {
            return mSynaUsbDeviceBase.getUsbDevice().getProductName();
        }
        return "No Device";
    }

    public FirmwareParam getFirmwareInfo(){
        if(null != mSynaUsbDeviceBase){
            return mSynaUsbDeviceBase.getFirmwareInfo();
        }
        return null;
    }

    /*
    public int getCurFwPartition(){
        if(null != mSynaUsbDeviceBase){
            return mSynaUsbDeviceBase.getCurrentFwPartition();
        }
        return 0;
    }

    public long getFwCrcValue(){
        if(null != mSynaUsbDeviceBase){
            return mSynaUsbDeviceBase.getFwCrcValue();
        }
        return 0;
    }*/

    private final UsbDeviceDetector.onUsbDeviceStateChanged mUsbDeviceStateChanged = new UsbDeviceDetector.onUsbDeviceStateChanged() {
        @Override
        public void plugStateChange(UsbDevice usbDevice, boolean isAttached) {
            mUsbDevice = usbDevice;
            Log.d(TAG, "plugStateChange: mUsbDevice isAttached:" + isAttached);
            if (isAttached) {
                onDeviceAttach();
            } else {
                onDeviceDetach();
            }
        }
    };

    private final UsbDevicePermissionDetector.onPermissionStateChanged mUsbPermissionStateChanged = new UsbDevicePermissionDetector.onPermissionStateChanged() {
        @Override
        public void permissionStateChange(UsbDevice usbDevice, boolean isAllowed) {
            Log.d(TAG, "permissionStateChange: isAllowed: " + isAllowed);
            if (mUsbDevice != usbDevice) {
                if (null == usbDevice||null== mSynaUsbDeviceBase) {
                    Log.e(TAG, "onPermissionChanged: device is null");
                } else {
                    SynaUsbDeviceBase synaUsbDeviceBase = null;
                    if (usbDevice.getProductId() == mSynaUsbDeviceBase.getUsbDevice().getProductId() && usbDevice.getVendorId() == mSynaUsbDeviceBase.getUsbDevice().getVendorId()) {
                        synaUsbDeviceBase = mSynaUsbDeviceBase;
                    }
                    if (null != synaUsbDeviceBase) {
                        onDevicePermissionChanged(isAllowed);
                    }
                }
            } else {
                Log.e(TAG, "permissionStateChange: ERROR ERROR ERROR ERROR ERROR");
            }
        }
    };

    public void forceDetectUSBDevice() {
        Log.d(TAG, "forceDetectUSBDevice");
        onDeviceAttach();
    }

    public void requestPermission() {
        Log.d(TAG, "requestPermission: mUsbDeviceBaseID = " + mSynaUsbDeviceBase.getId());
        if (null != mUsbDevice && null != mSynaUsbDeviceBase && !mSynaUsbDeviceBase.getHasPermission()) {
            mUsbPermissionDetector.requestUsbDevicePermission(mSynaUsbDeviceBase.getUsbDevice());
        }
    }

    public interface DevicePlugListener {
        void onStateChange(SynaUsbDeviceBase mSynaUsbDeviceBase, boolean isAttach);
    }

    public void addDevicePlugListener(DevicePlugListener listener) {
        devicePlugListeners.add(listener);
    }

    public interface DevicePermissionListener {
        void onStateChange(SynaUsbDeviceBase mSynaUsbDeviceBase, boolean isAllowed);
    }

    public void addDevicePermissionListener(DevicePermissionListener listener) {
        devicePermissionListeners.add(listener);
    }

    private void onDeviceAttach() {
        if (null == mUsbDevice) {
            Log.e(TAG, "onDeviceAttach: no device plugged");
            return;
        }
        tryCreateController();
        for (DevicePlugListener l : devicePlugListeners) {
            Log.d(TAG, "onDeviceAttach:DevicePlugListener ");
            l.onStateChange(mSynaUsbDeviceBase, true);
        }
    }

    private void onDeviceDetach() {
        if (mSynaUsbDeviceBase == null) {
            Log.e(TAG, "onDeviceDetach: no device when detach !!!");
        } else {
            for (DevicePlugListener l : devicePlugListeners) {
                l.onStateChange(mSynaUsbDeviceBase, false);
            }

            if (null != mSynaUsbDeviceBase) {
                mSynaUsbDeviceBase.releaseUsbDevice();
                mSynaUsbDeviceBase = null;
            }
        }
    }

    private void onDevicePermissionChanged(boolean hasPermission) {
        if (mSynaUsbDeviceBase == null) {
            Log.e(TAG, "onDevicePermissionChanged: no device!!!");
        } else {
            mSynaUsbDeviceBase.setHasPermission(hasPermission);

            tryCreateController();
            
            for (DevicePermissionListener l : devicePermissionListeners) {
                Log.d(TAG, "onDevicePermissionChanged:  l.onStateChange");
                l.onStateChange(mSynaUsbDeviceBase, hasPermission);
            }
        }
    }

    private void tryCreateController() {
        Log.d(TAG, "tryCreateController: create instance");
        mSynaUsbDeviceBase = new SynaUsbDeviceBase(mContext, mUsbDevice);
        Log.d(TAG, "tryCreateController: mSynaUsbDeviceBase = "+ mSynaUsbDeviceBase);
    }

    public void release() {
        Log.d(TAG, "release: ");
        devicePlugListeners.clear();
        devicePermissionListeners.clear();
        if (mUsbDetector != null) {
            mUsbDetector.release();
            mUsbDetector = null;
        }

        if (mUsbPermissionDetector != null) {
            mUsbPermissionDetector.release();
            mUsbPermissionDetector = null;
        }

        if (mSynaUsbDeviceBase != null) {
            mSynaUsbDeviceBase.releaseUsbDevice();
            mSynaUsbDeviceBase = null;
        }
    }
}
