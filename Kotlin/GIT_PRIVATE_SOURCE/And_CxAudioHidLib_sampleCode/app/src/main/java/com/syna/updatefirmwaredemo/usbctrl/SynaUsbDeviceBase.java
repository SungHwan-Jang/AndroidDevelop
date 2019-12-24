package com.syna.updatefirmwaredemo.usbctrl;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.syna.FirmwareParam;
import com.syna.FirmwareUpdate;
import com.syna.updatefirmwaredemo.common.SynaConstants;

public class SynaUsbDeviceBase {

    private static final String TAG = SynaUsbDeviceBase.class.getSimpleName();
    protected String mDeviceName = "";

    private static Object mClassSyncObj = new Object();
    private static int mClassIdCounter = 0;

    private int mId;
    protected Context mContext = null;
    protected UsbDevice mUsbDevice = null;
    protected UsbDeviceConnection mConnection = null;
    protected UsbEndpoint mUsbEndpoint = null;
    protected FirmwareUpdate mUpdateFirmware = null;
    protected boolean mHasPermission = false;
    protected boolean mSynaConnected = false;
    private Thread thread;

    public SynaUsbDeviceBase(Context context, UsbDevice usbDevice) {
        mContext = context;
        mId = generateSessionId();
        mUsbDevice = usbDevice;
    }

    public static int generateSessionId() {
        synchronized (mClassSyncObj) {
            return ++mClassIdCounter;
        }
    }

    public int getId() {
        return mId;
    }

    public UsbDevice getUsbDevice() {
        return mUsbDevice;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String name) {
        mDeviceName = name;
    }

    public boolean getHasPermission() {
        return mHasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        mHasPermission = hasPermission;
    }

    public boolean prepareUsbDevice() {
        if (mConnection == null) {
            Log.d(TAG, "prepareUsbDevice");

            UsbManager mUsbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
            if (mUsbManager == null) {
                Log.e(TAG, "***** prepareUsbDevice UsbManager is not OK");
                return false;
            }

            if (!mUsbManager.hasPermission(mUsbDevice)) {
                Log.e(TAG, "****** prepareUsbDevice: Synaptics device has no permission");
                return false;
            }

            mConnection = mUsbManager.openDevice(mUsbDevice);
            if (mConnection == null) {
                Log.e(TAG, "***** prepareUsbDevice UsbDeviceConnection is not OK");
                return false;
            }

        }
        return true;
    }

    public boolean checkIsCnxtUsbDevice() {
        if (prepareUsbDevice()) {
            mUpdateFirmware = new FirmwareUpdate();
            if (mUpdateFirmware == null) {
                Log.e(TAG, "SynaUpdateFirmware is not OK");
                return false;
            }
             //You'd better check if the device is you want upgrade here. If it is, call the
            //connect function.
            if (mUpdateFirmware.connectSynaDevice(mConnection.getFileDescriptor())) {
                mSynaConnected = true;
                return true;
            }else{
                Log.e(TAG,"connect Syna Device failed \n");
            }
        }
        return false;
    }

    /*
    public int getCurrentFwPartition(){//TODO getCurrentFwPartition
        if (checkIsCnxtUsbDevice()) {
            int curFwPartition = mUpdateFirmware.getCurFwPatition();
            Log.d(TAG, "getCurrentFwPartition: curFwPartition: "+curFwPartition);
            return curFwPartition;
        }
        return 0;
    }

    public long getFwCrcValue(){//TODO getFwCrcValue
        if (checkIsCnxtUsbDevice()) {
            long curCrc = mUpdateFirmware.getCrcValue(getCurrentFwPartition());
            Log.d(TAG, "getFwCrcValue: curCrc: "+curCrc);
            return curCrc;
        }
        return 0;
    }*/

    public FirmwareParam getFirmwareInfo() {
        if (checkIsCnxtUsbDevice()) {
            FirmwareParam firmwareParam = mUpdateFirmware.getSynaDeviceInfo();
            Log.d(TAG, "getFirmwareInfo: " + firmwareParam.getManufacture() + " # " + firmwareParam.getProduct());
            return firmwareParam;
        }
        return null;
    }

    public int updateFirmware(final String firmwareUrl) {
        Log.d(TAG, "updateFirmware: SynaUsbDeviceBase firmwareUrl = " + firmwareUrl);
        if (mUpdateFirmware == null) {
            Log.e(TAG, "mUpdateFirmware is not prepare well");
            return SynaConstants.RC_GENERIC_FAILURE;
        }

        boolean newVer = mUpdateFirmware.DetectNewFirmware(firmwareUrl);
        if(newVer){
            Log.d(TAG,"New version file, should update the firmware");
        }else{
            Log.d(TAG,"No need to update the firmware");
        }

        int updateRtr = mUpdateFirmware.downloadFirmware(firmwareUrl, SynaConstants.OPTION_UPGRADE);
        Log.d(TAG, "updateFirmware: updateRtr: "+updateRtr);
        return updateRtr == 0 ? SynaConstants.RC_SUCCESS : SynaConstants.RC_GENERIC_FAILURE;
    }

    public String getFileFwVersion(final String firmwareUrl)
    {
        return mUpdateFirmware.fileGetFwVersion(firmwareUrl);
    }

    public void resetDevice(){
        if((mUpdateFirmware != null) && mSynaConnected )
        {
            mUpdateFirmware.deviceReset();
        }
    }

     public int getUpdateFwProgress() {
        int updateFwProgress = 0;
        if (null == mUpdateFirmware) {
            mUpdateFirmware = new FirmwareUpdate();
        }
        updateFwProgress = mUpdateFirmware.getUpdateFirmwareProgress();
        return updateFwProgress;
    }

    public void releaseUsbDevice() {
        mUsbEndpoint = null;
        if (mConnection != null) {
            if (mSynaConnected){
                if(mUpdateFirmware != null) {
                    mUpdateFirmware.disconnectSynaDevice();
                    mUpdateFirmware = null;
                }
                mSynaConnected = false;

            }

            mConnection.close();
            mConnection = null;
        }
        mUsbDevice = null;
        mContext = null;
    }
}
