//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syna;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class FirmwareParam implements Parcelable {
    private String manufacture;
    private String product;
    private String serial;
    private String firmware_version;
    private int usb_vendor_id;
    private int usb_product_id;
    private int device;
    public static final Creator<FirmwareParam> CREATOR = new Creator() {
        public FirmwareParam createFromParcel(Parcel in) {
            return new FirmwareParam(in);
        }

        public FirmwareParam[] newArray(int size) {
            return new FirmwareParam[size];
        }
    };

    public FirmwareParam() {
    }

    public FirmwareParam(FirmwareParam firmwareParam) {
        this.manufacture = firmwareParam.manufacture;
        this.product = firmwareParam.product;
        this.serial = firmwareParam.serial;
        this.firmware_version = firmwareParam.firmware_version;
        this.usb_vendor_id = firmwareParam.usb_vendor_id;
        this.usb_product_id = firmwareParam.usb_product_id;
        this.device = firmwareParam.device;
    }

    protected FirmwareParam(Parcel in) {
        this.manufacture = in.readString();
        this.product = in.readString();
        this.serial = in.readString();
        this.firmware_version = in.readString();
        this.usb_vendor_id = in.readInt();
        this.usb_product_id = in.readInt();
        this.device = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.manufacture);
        dest.writeString(this.product);
        dest.writeString(this.serial);
        dest.writeString(this.firmware_version);
        dest.writeInt(this.usb_vendor_id);
        dest.writeInt(this.usb_product_id);
        dest.writeInt(this.device);
    }

    public int describeContents() {
        return 0;
    }

    public String getManufacture() {
        return this.manufacture;
    }

    public void setManufacture(String manufacture) {
        if(manufacture == null) {
            this.manufacture = "00";
        } else {
            this.manufacture = manufacture;
        }

    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        if(product == null) {
            this.product = "00";
        } else {
            this.product = product;
        }

    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        if(serial == null) {
            this.serial = "00";
        } else {
            this.serial = serial;
        }

    }

    public String getFirmware_version() {
        return this.firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        if(firmware_version == null) {
            this.firmware_version = "00";
        } else {
            this.firmware_version = firmware_version;
        }

    }

    public int getUsb_vendor_id() {
        return this.usb_vendor_id;
    }

    public void setUsb_vendor_id(int usb_vendor_id) {
        this.usb_vendor_id = usb_vendor_id;
    }

    public int getUsb_product_id() {
        return this.usb_product_id;
    }

    public void setUsb_product_id(int usb_product_id) {
        this.usb_product_id = usb_product_id;
    }

    public int getDevice() {
        return this.device;
    }

    public void setDevice(int device) {
        this.device = device;
    }
}
