package com.example.besfirmtestapp.Utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Parcelable

class UsbConnectionManager {
    private val TAG = "UsbConnectionManager"
    private val ACTION_REQ_PERMISSION = "com.usb.device.action.request.permission"

    val CONNECTION_CONNECTED = 0
    val CONNECTION_FAILED_CONNECTION_NULL = 1
    val CONNECTION_FAILED_NO_PERMISSION = 2

    private var mContext: Context? = null
    private var mUsbManager: UsbManager? = null
    private var mUsbDevice: UsbDevice? = null
    private var mUsbDeviceConnection: UsbDeviceConnection? = null
    private var mPermissionBroadcastReceiver: PermissionBroadcastReceiver? = null

    constructor(context: Context?){
        mContext = context
        mPermissionBroadcastReceiver = PermissionBroadcastReceiver()
        init()
        registerReceiver()
    }
    fun getmUsbDevice(): UsbDevice? {
        return mUsbDevice
    }

    fun getmUsbDeviceConnection(): UsbDeviceConnection? {
        return mUsbDeviceConnection
    }

    /**
     * initialization
     * @return
     */
    private fun init(): Boolean {
        mUsbManager = mContext!!.getSystemService(Context.USB_SERVICE) as UsbManager
        if (null == mUsbManager) {
//            LOG("Get UsbManager fail! null == usbManager")
        } else {
            val deviceMap = mUsbManager!!.deviceList
//            LOG("deviceMap.size(): " + deviceMap.size)
            if (!deviceMap.isEmpty()) {
                val iterator = deviceMap.values.iterator()
                while (iterator.hasNext()) {
                    mUsbDevice = iterator.next()
//                    LOG(" find usb device name =  " + mUsbDevice!!.productName!!)
                }
                return true
            } else {
                return false
            }
        }
        return false
    }

    fun connect(usbDevice: UsbDevice): Boolean {
        mUsbDevice = usbDevice
        if (null == mUsbDevice || null == mUsbManager) {
//            LOG("mDevice is null")
            return false
        }
        if (mUsbManager!!.hasPermission(mUsbDevice)) {
//            LOG("USB has acquired system authorization ")
            if (connectWithPermission())
                return true
            else
                return false
        }
        else {
//            LOG("USB does not get system authorization, ready to request system authorization")
            val intent = Intent(ACTION_REQ_PERMISSION)
            val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            mUsbManager!!.requestPermission(mUsbDevice, pendingIntent)
            return false
        }
    }

    /**
     * Initiate a connection when authorized
     * @return
     */
    private fun connectWithPermission(): Boolean {
        if (mUsbDeviceConnection != null) {
            disconnect(true)
        }
        mUsbDeviceConnection = mUsbManager!!.openDevice(mUsbDevice)
        if (mUsbDeviceConnection == null) {
//            LOG("mManager.openDevice(mDevice) is null ret false")
            return false
        }
        if (onConnectionChangerCallBack != null) {
            onConnectionChangerCallBack!!.onConnected(mUsbDevice, mUsbDeviceConnection!!)
        }
        return true
    }

    fun disconnect(isBesDevice: Boolean) {
//        LOG("USB 通讯断开")
        if (mUsbDeviceConnection != null) {
            mUsbDeviceConnection!!.close()
            mUsbDeviceConnection = null
        }
        if (onConnectionChangerCallBack != null) {
            onConnectionChangerCallBack!!.onDisConnected(isBesDevice)
        }
    }

    /**
     * Determine the validity of the id before canceling the connection, use the vender command instead,
     * and judge the validity by calling the getProfileVersion interface after the connection is successful.
     * @param device
     * @return
     */
    @Deprecated("")
    fun isBESDevice(device: UsbDevice): Boolean {
        return true
    }

    fun release() {
        unregisterReceiver()
        disconnect(true)
    }

    private fun registerReceiver() {
        if (mContext != null && mPermissionBroadcastReceiver != null) {
            try {
                unregisterReceiver()
            } catch (e: Exception) {

            } finally {
                val filter = IntentFilter()
                filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
                filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
                filter.addAction(ACTION_REQ_PERMISSION)
                mContext!!.registerReceiver(mPermissionBroadcastReceiver, filter)
            }
        }
    }

    private fun unregisterReceiver() {
        mContext!!.unregisterReceiver(mPermissionBroadcastReceiver)
    }

    internal inner class PermissionBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent?) {
            if (intent != null && intent.action != null) {
                val action = intent.action
                if (UsbManager.ACTION_USB_DEVICE_ATTACHED == action) {
//                    LOG("UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)")
                    val device = intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice
                    if (onConnectionChangerCallBack != null) {
                        onConnectionChangerCallBack!!.onAttachedCallBack(device)
                    }
                    mUsbDevice = device
                    connect(device)
                } else if (UsbManager.ACTION_USB_DEVICE_DETACHED == action) {
//                    LOG("UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)")
                    if (onConnectionChangerCallBack != null) {
                        onConnectionChangerCallBack!!.onDetachedCallBack()
                    }
                    disconnect(true)
                    mUsbDevice = null
                } else if (ACTION_REQ_PERMISSION == action) {
                    val bundle = intent.extras
                    val granted = bundle!!.getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED)
                    if (granted) {
                        if (connectWithPermission()) {
//                            LOG("USB connection is successful")
                        } else {
//                            LOG("USB connection failed")
                            if (onConnectionChangerCallBack != null) {
                                onConnectionChangerCallBack!!.onConnectionFailed(CONNECTION_FAILED_CONNECTION_NULL)
                            }
                        }
                    } else {
//                        LOG("USB authorization failed")
                        if (onConnectionChangerCallBack != null) {
                            onConnectionChangerCallBack!!.onConnectionFailed(CONNECTION_FAILED_NO_PERMISSION)
                        }
                    }
                }
            }
        }
    }

    internal var onConnectionChangerCallBack: OnConnectionChangerCallBack? = null
    fun setOnConnectionChangerCallBack(onConnectionChangerCallBack: OnConnectionChangerCallBack) {
        this.onConnectionChangerCallBack = onConnectionChangerCallBack
    }

    interface OnConnectionChangerCallBack {
        fun onConnected(usbDevice: UsbDevice?, usbDeviceConnection: UsbDeviceConnection)
        fun onDisConnected(isBesDevice: Boolean)
        fun onConnectionFailed(status: Int)
        fun onAttachedCallBack(usbDevice: UsbDevice)
        fun onDetachedCallBack()
    }

//    private fun LOG(msg: String?) {
//        if (msg != null) {
//            LogUtils.writeComm(TAG, FileUtils.USB_FM_FILE, msg)
//        }
//    }

}