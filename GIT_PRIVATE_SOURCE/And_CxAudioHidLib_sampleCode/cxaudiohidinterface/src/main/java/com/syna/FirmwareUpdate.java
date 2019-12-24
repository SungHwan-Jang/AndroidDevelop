//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syna;

import android.os.HandlerThread;
import android.util.Log;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

import com.syna.FirmwareParam;
import java.io.IOException;
import java.io.InterruptedIOException;

public class FirmwareUpdate {
    private static final String TAG = FirmwareUpdate.class.getSimpleName();
    private FirmwareParam firmwareParam = null;
    private int CR_TRUE = 1;
    private int flags = 0;
    private int ConResult = 0;
    private int DetectFlag = 0;
    private int DetectRes = 0;
    public FirmwareUpdate(){
    }

    public boolean connectSynaDevice(final int fileDescriptor) {

        flags = 0;
        ConResult = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConResult =  native_connect(fileDescriptor);
                flags = 1;
            }
        }).start();

        try {
            int count = 0;
            while((count < 80) && (flags == 0)) {
                Thread.sleep(10);
                count++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ConResult == this.CR_TRUE) {
            return true;
        }else {
            return false;
        }
    }

    public int downloadFirmware(String firmwareFileUrl, int option) {
        int res = native_download(firmwareFileUrl, option);
        return res;
    }

    public boolean deviceReset() {
        int res = native_DeviceReset();
        return res == this.CR_TRUE;
    }

    private void GetDeviceInformation() {
        this.firmwareParam = new FirmwareParam();
        if (this.firmwareParam != null) {
            this.firmwareParam.setUsb_product_id(native_GetProductID());
            this.firmwareParam.setUsb_vendor_id(native_GetVendorID());
            this.firmwareParam.setFirmware_version(native_GetFirmwareVersionString());
        }
    }


    public String fileGetFwVersion(String filePath)
    {

        String str = native_FileGetFirmwareVersionString(filePath);
        if(str == null){
            str = "0.0.0.0";
        }

        return str;
    }


    public boolean DetectNewFirmware(final String FileName)
    {
        DetectFlag = 0;
        DetectRes  = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                DetectRes  =  native_FirmwareDetect(FileName);
                DetectFlag =  1;
            }
        }).start();

        try {
            int count = 0;
            while((count < 100) && (DetectFlag == 0)) {
                Thread.sleep(6);
                count++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (DetectRes == this.CR_TRUE) {
            return true;
        }else {
            return false;
        }

    }

    public int getUpdateFirmwareProgress() {
        return native_GetProgressPerCent();
    }

    public FirmwareParam getSynaDeviceInfo() {
        if(this.firmwareParam == null) {
            this.GetDeviceInformation();
        }
        return this.firmwareParam;
    }

    public void disconnectSynaDevice() {
        native_disconnect();
        this.firmwareParam = null;
    }

    private static final native void native_init();

    private static final native void native_finalize();

    private static final native void native_disconnect();

    private static final native int native_DeviceReset();

    private static final native int native_connect(int var0);

    private static final native int native_download(String fileName, int var1);

    private static final native int native_GetVendorID();

    private static final native int native_GetProductID();

    private static final native int native_GetProgressPerCent();

    private static final native int native_FirmwareDetect(String fileNam);

    private static final native String native_GetFirmwareVersionString();

    private static final native String native_FileGetFirmwareVersionString(String fileName);


    static {
        System.loadLibrary("CxAudioHidLib_EMBCB_jni");
        native_init();
    }
}
