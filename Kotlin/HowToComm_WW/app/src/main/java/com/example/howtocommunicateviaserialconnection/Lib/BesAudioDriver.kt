package com.example.besfirmtestapp.DeviceDriver

import android.hardware.usb.UsbDeviceConnection
import android.util.Log

class BesAudioDriver {

    private val TAG = "BesAudioDriver"
    private val SET_POWER_STATE = 0
    private val SET_FM_BAND = 1
    private val SET_CHAN_RSSI_TH = 2
    private val SET_CHAN_SPACING = 3
    private val SET_MUTE = 4
    private val SET_VOLUME = 5
    private val SET_MONO_MODE = 6
    private val SEEK_START = 7
    private val SEEK_STOP = 8
    private val SET_CHAN = 9
    private val SET_RDS = 10
    private val SET_DNS = 11
    private val SET_AF = 12
    private val GET_FM_IC_TYPE = 1
    private val GET_FM_IC_BOOT_UP_STATUS = 2
    private val GET_CURRENT_FM_BAND = 3
    private val GET_CURRENT_SEEK_CHAN_RSSI_TH = 4
    private val GET_CURRENT_SEEK_CHAN_SPACING = 5
    private val GET_CURRENT_MUTE_STATUS = 6
    private val GET_CURRENT_CHANNEL_SETTING = 7
    private val GET_CURRENT_VOLUME_SETTING = 8
    private val GET_CURRENT_SEEK_STATUS = 9
    private val GET_CURRENT_RDS_SETTINGS = 10
    private val GET_CURRENT_DNS_SETTING = 11
    private val GET_CURRENT_AF_SETTING = 12
    private val GET_CURRENT_CHANNEL = 13
    private var mUsbDeviceConnection: UsbDeviceConnection? = null

    constructor(paramUsbDeviceConnection: UsbDeviceConnection){
        this.mUsbDeviceConnection = paramUsbDeviceConnection
    }

    fun getFirmwareVersion(): String {

        val getFWVersion = byteArrayOf(81, 85, 69, 82, 89, 95, 83, 87, 95, 86, 69, 82)
        var ret = this.mUsbDeviceConnection?.controlTransfer(64, 6, 0, 0, getFWVersion, getFWVersion.size, 4000)
        val fwString = getFWVersion.toString()
        Log.d("MainActivity",  "getFirmwareVersion getFirmwareVersion SEND  ${ret}  data =  ${getFWVersion}  ascii =  ${fwString}")

        var buffer = byteArrayOf(81, 85, 69, 82, 89, 95, 83, 87, 95, 86, 69, 82, 1, 1);
        ret = this.mUsbDeviceConnection?.controlTransfer(192, 12, 0, 0, buffer, 14, 0)
        val versionString = buffer.toString()
        Log.d("MainActivity",  "getFirmwareVersion getFirmwareVersion BACK  ${ret}  data =  ${buffer}  ascii =  ${versionString}")
        return versionString
    }

    fun getFirmwareVersionUsingParam(paramUsbDeviceConnection: UsbDeviceConnection): String {

        val getFWVersion = byteArrayOf(81, 85, 69, 82, 89, 95, 83, 87, 95, 86, 69, 82)
        var ret = paramUsbDeviceConnection?.controlTransfer(64, 6, 0, 0, getFWVersion, getFWVersion.size, 4000)
        val fwString = getFWVersion.toString()
        Log.d("MainActivity",  "getFirmwareVersion getFirmwareVersion SEND  ${ret}  data =  ${getFWVersion}  ascii =  ${fwString}")

        var buffer = null;
        ret = paramUsbDeviceConnection?.controlTransfer(192, 12, 0, 0, buffer, 14, 0)
        val versionString = buffer.toString()
        Log.d("MainActivity",  "getFirmwareVersion getFirmwareVersion BACK  ${ret}  data =  ${buffer}  ascii =  ${versionString}")
        return versionString
    }
}