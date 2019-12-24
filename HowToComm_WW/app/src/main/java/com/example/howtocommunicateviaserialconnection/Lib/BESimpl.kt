package com.example.besfirmtestapp

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import com.example.besfirmtestapp.DeviceDriver.BesAudioDriver

class BESimpl {
    internal var TAG = "BESCdcOtaImpl"
    val INIT_DONE = 0
    val INIT_NO_BES_DEVICE = 1
    val INIT_USB_DEVICE_NULL = 2
    val INIT_USB_NO_CONNECTED = 3
    val INIT_CONTEXT_NULL = 4
    val DOWNLAOD_DONE_IN_AUTO_MODE = 0
    val DOWNLAOD_DONE_IN_HAND_MODE = 1
    val DOWNLOAD_FILE_NOT_FOUND = 2
    val DOWNLOAD_NO_DEVICE_CONNECTED = 3
    val DOWNLOAD_HANDSHAKE_FAILED = 4
    val DOWNLOAD_PROGRAMMER_INFO_FAILED = 5
    val DOWNLOAD_RUN_PROGRAMMER_FAILED = 6
    val DOWNLOAD_BURN_INFO_FAILED = 7
    val DOWNLOAD_OTHER_ERROR = 8
    val DOWNLOAD_NO_IN_CDC_MODE = 9
    val DOWNLOAD_READ_FILE_VERSION_FIALED = 10
    private val DOWNLOAD_READY = 11
    val DOWNLAOD_NO_BULK_ENDPOINT = 12
    val DOWNLAOD_FAILED = 13
    val DOWNLOAD_BUSSING = 14
    val IDLE_STATUS = 15
    private val downMsgCode = 15
    private val REQ_ERASE_BAT_BOOT_FLAG: Byte = 16
    private val REQ_WRITE_BOOT_FLAG_TO_BAT_ADDR: Byte = 17
    private val REQ_WRITE_MAGIC_NUMBER: Byte = 18
    private val REQ_ERASE_BOOT_FLAG: Byte = 19
    private val REQ_WRITE_BOOT_FALG: Byte = 20
    private val REQ_SET_BOOT_MODE: Byte = 21
    private val REQ_REBOOT_SYSTEM: Byte = 22
    private val REQ_OTA_FAILED_REBOOT_SYSTEM: Byte = 22
    internal val OTA_START = 0
    internal val OTA_FIRST_HANDSHAKE_START = 1
    internal val OTA_FIRST_HANDSHAKE_FAILED = 2
    internal val OTA_FIRST_HANDSHAKE_SUCCESSFUL = 3
    internal val OTA_START_DOWNLOAD_PROGRAMMER = 4
    internal val OTA_START_DOWNLOAD_PROGRAMMER_BIN = 5
    internal val OTA_DOWNLOAD_PROGRAMMER_FAILED = 6
    internal val OTA_DOWNLOAD_PROGRAMMER_SUCCESSFUL = 7
    internal val OTA_RUN_PROGRAMMER = 8
    internal val OTA_RUN_PROGRAMMER_SUCCESSFUL = 9
    internal val OTA_RUN_PROGRAMMER_FAILED = 10
    internal val OTA_WAIT_RAM_RUN = 11
    internal val OTA_READ_BOOT_FLAG = 12
    internal val OTA_ERASE_BAT_BOOT_FLAG = 13
    internal val OTA_COPY_TO_BAT_BOOT_FLAG = 14
    internal val OTA_START_DOWNLOAD_BURN = 15
    internal val OTA_START_DOWNLOAD_BURN_NEXT = 16
    internal val OTA_START_DOWNLOAD_BURN_FAILED = 17
    internal val OTA_START_DOWNLOAD_BURN_SUCCESFUL = 18
    internal val OTA_BURN_WIRTE_MAGIC_NUMBER = 19
    internal val OTA_BURN_ERASE_BOOT_FLAG = 20
    internal val OTA_BURN_WRITE_BOOT_FLAG = 21
    internal val OTA_SET_BOOT_MODE = 22
    internal val OTA_REBOOT_FLASH = 23
    internal val OTA_DONE_IN_AUTO_MODE = 24
    internal val OTA_DONE_IN_HAND_MODE = 25
    internal val OTA_OTHER_ERROR = 26
    internal val OTA_FAILED = 27
    internal val OTA_IDLE = 28
    internal var mOtaStatus = 28
    internal lateinit var mContext: Context
    internal lateinit var mUsbDeviceConnection: UsbDeviceConnection
    internal var mUsbDevice: UsbDevice? = null
    internal var mBesAudioDriver: BesAudioDriver? = null
    internal lateinit var mProfileVersion: String
    internal lateinit var mSerialNumber: String
    internal var mTypeCVersion: String? = null
    internal var mFirmwareFilePath: String? = null
    internal var mProcess: Int = 0
    internal var copyBootFlag: ByteArray? = null
    internal var mSectorSize = 0
    internal var isAboot = true
    internal var detectNewFirmwareCode = 0
    internal var isHandMode = false
    private val mLock = Any()

    constructor() { }

    fun init(context: Context?, usbDeviceConnection: UsbDeviceConnection?, usbDevice: UsbDevice?): Int {

        if (context == null) {
            return 4
        } else if (usbDevice == null) {
            return 3
        } else if (usbDeviceConnection == null) {
            return 2
        } else {
            this.mContext = context
            this.mUsbDeviceConnection = usbDeviceConnection
            this.mUsbDevice = usbDevice
            this.mBesAudioDriver = BesAudioDriver(mUsbDeviceConnection)

            try {
                this.mTypeCVersion = this.mBesAudioDriver!!.getFirmwareVersion()

            } catch (var5: Exception) {
                return -1
            }
            return 0
        }
    }

    fun finalize() {
        if (this.mBesAudioDriver != null) {
            this.mBesAudioDriver = null
        }
    }

    fun getVendorID(): Int {
        return if (this.mUsbDevice != null) this.mUsbDevice!!.vendorId else 0
    }

    fun getProductID(): Int {
        return if (this.mUsbDevice != null) this.mUsbDevice!!.productId else 0
    }

    fun getManufacturerString(): String? {
        return if (this.mUsbDevice != null) this.mUsbDevice!!.manufacturerName else null
    }

    fun getProductString(): String? {
        return if (this.mUsbDevice != null) this.mUsbDevice!!.productName else null
    }

    fun getProfileVersion(): String {
        return this.mProfileVersion
    }

    fun getSerialNumberString(): String {
        return this.mSerialNumber
    }

    fun getTypeCVersionString(): String? {
        this.mTypeCVersion.let{
            return it
        }
    }
}