package com.example.howtocommunicateviaserialconnection

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.besfirmtestapp.BESimpl
import com.felhr.usbserial.UsbSerialDevice
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var m_usbManager : UsbManager
    var m_device : UsbDevice? = null
    var m_serial : UsbSerialDevice? = null
    var m_connection : UsbDeviceConnection? = null

    val ACTION_USB_PERMISSON = "permission"
    var besLib = BESimpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m_usbManager = getSystemService(Context.USB_SERVICE) as UsbManager

        val filter = IntentFilter()
        filter.addAction(ACTION_USB_PERMISSON)
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        registerReceiver(broadcastReceiver, filter) //해당 작동이 감지되면 broadcastReceiver를 호출하도록 함.

        button_on.setOnClickListener{
            sendData("o")
        }

        button_off.setOnClickListener{
            sendData("x")
        }

        button_connect.setOnClickListener{
            startUsbConnecting()
        }

        button_disconnect.setOnClickListener{
            disconnecting()
        }

    }

    private fun startUsbConnecting(){

        val usbDevice : HashMap<String, UsbDevice> = m_usbManager.deviceList

        if(!usbDevice?.isEmpty()!!){
            var keep = true
            usbDevice.forEach { entry ->
                m_device = entry.value
                val deviceVendorId : Int? = m_device?.vendorId
                Log.i("serial", "vendor ID :" + deviceVendorId)

                if(deviceVendorId == 0x04E8){
                    val intent : PendingIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_USB_PERMISSON), 0)
                    m_usbManager.requestPermission(m_device, intent) //권한 요청하는 부분. 해당 부분이 있어야 openDevice가 가능하다.
                    keep = false
                    Log.i("serial", "connection successful")
                    textView.text = "connection successful"
                }
                else{
                    m_connection = null
                    m_device = null
                    Log.i("serial", "Unable to connect")
                    textView.text = "Unable to connect"
                }

                if(!keep){
                    return
                }
            }
        }
        else{
            Log.i("serial", "no usb device Connection")
            textView.text = "no usb device Connection"
        }
    }

    private  fun sendData(input : String){
        m_serial?.write(input.toByteArray())
        Log.i("serial", "input data :" + input)
        textView.text = "input data : + ${input}"
    }

    private fun disconnecting(){
        m_serial?.close()
        textView.text = "Disconnect!"
    }

    private val broadcastReceiver = object : BroadcastReceiver(){ // usb 연결, 해제 모두 감지하여 자동 호출.
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action!! == ACTION_USB_PERMISSON){
                m_connection = null
                val granted : Boolean = intent.extras!!.getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED)
                if(granted){
                    m_connection = m_usbManager.openDevice(m_device) //GRANT 권한이 안주어지면 openDevice가 불가능하다.
                    if(m_connection != null){
                        textView.text = "Device Open!"
                    }

                    besLib.init(context, m_connection, m_device)
                    val fwVer = besLib.getTypeCVersionString()
                    textView.text = fwVer

//                    m_serial = UsbSerialDevice.createUsbSerialDevice(m_device, m_connection)
//                    if(m_serial != null){
//                        if(m_serial!!.open()) {
//                            m_serial!!.setBaudRate(9600)
//                            m_serial!!.setDataBits(UsbSerialInterface.DATA_BITS_8)
//                            m_serial!!.setStopBits(UsbSerialDevice.STOP_BITS_1)
//                            m_serial!!.setParity(UsbSerialDevice.PARITY_NONE)
//                            m_serial!!.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF)
//                        }
//                        else {
//                            Log.i("Serial", "port not open")
//                            textView.text = "PORT NOT OPEN"
//                        }
//                    }
//                    else{
//                        Log.i("Serial", "port is null")
//                        textView.text = "port is null"
//                    }
                }
                else{
                    Log.i("Serial", "permission not granted")
                    textView.text = "permission not granted"
                }
            }
            else if(intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED){
                startUsbConnecting()
            }
            else if(intent.action == UsbManager.ACTION_USB_DEVICE_DETACHED){
                disconnecting()

            }
        }
    }
}
