package com.syna.updatefirmwaredemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.syna.FirmwareParam;
import com.syna.FirmwareUpdate;
import com.syna.updatefirmwaredemo.R;
import com.syna.updatefirmwaredemo.common.AppApplication;
import com.syna.updatefirmwaredemo.common.CommonUtil;
import com.syna.updatefirmwaredemo.common.FileBean;
import com.syna.updatefirmwaredemo.common.SynaConstants;
import com.syna.updatefirmwaredemo.helper.FirmwareFileAdapter;
import com.syna.updatefirmwaredemo.helper.LoadFileHelper;
import com.syna.updatefirmwaredemo.helper.SvcModClient;
import com.syna.updatefirmwaredemo.usbctrl.SynaUsbDeviceBase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int INSTALL_TOKEN = 1;

    private final Object mObjSync = new Object();
    private boolean mIsResumed = false;
    private boolean mHasDelayedPermissionRequest = false;

    private TextView mTvVendorId, mTvProductId;
    private Button mBtnBrowserFile, mBtnUpdateFw;
    private AlertDialog mFirmwareDialog;
    private TextView firmwarePathTv;
    private ListView firmwareFileLv;
    private TextView mTvFwStatus, mTvManufacture, mTvProduct, mTvSerial, mTvDevice,
            mTvFwVersion, mTvCurrentFwPartition, mTvCRCValue, mTvFileFwVersion;
    private LinearLayout mLinearLayoutDeviceInfo;

    private AlertDialog mAlertDialog;
    private ProgressDialog mUpdateFwProgressDialog;

    private List<FileBean> firmwareFileList = new ArrayList<>();
    private String curSelectedFirmwarePath;
    private boolean isLoadFirmware = false;

    private UpdateFwAsyncTask mUpdateFwAsyncTask;
    private Handler mUpdateFWHandler = null;
    private HandlerThread mUpdateFWHandlerThread = null;
    private int updateRtr = 0;

    private Runnable mUpdateFWRunnable = new Runnable() {
        @Override
        public void run() {
            //int updateRtr = SvcModClient.getUsb().updateFirmware(curSelectedFirmwarePath);
            Log.d(TAG, "Synaptics firmware update function\n");
            updateRtr = SvcModClient.get().getUsbCtrl().updateFirmware(curSelectedFirmwarePath);
            Log.d(TAG, "Synaptics mRunnable: update firmware : " + (updateRtr == SynaConstants.RC_SUCCESS ? " Success" : " Failure"));
            if (null != mUpdateFwProgressDialog && mUpdateFwProgressDialog.isShowing()) {
                mUpdateFwProgressDialog.dismiss();
            }
            if(updateRtr == SynaConstants.RC_SUCCESS) {
                SvcModClient.get().getUsbCtrl().resetDevice();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppApplication.getInstance().addActivity(this);
        CommonUtil.createAppDirFile();
        Log.d(TAG, "onCreate: ");
        initData();
        initView();
    }

    private void initData() {
        mUpdateFWHandlerThread = new HandlerThread("UpdateFirmware");
        mUpdateFWHandlerThread.start();
        mUpdateFWHandler = new Handler(mUpdateFWHandlerThread.getLooper());

        SvcModClient.initClient(MainActivity.this);
        SvcModClient.get().addDevicePlugListener(mDevicePlugListener);
        SvcModClient.get().addDevicePermissionListener(mDevicePermissionListener);
        SvcModClient.get().forceDetectUSBDevice();
    }

    private void initView() {

        firmwarePathTv = (TextView) findViewById(R.id.tv_firmware_path);
        mTvFileFwVersion = (TextView) findViewById(R.id.tv_file_fw_version);

        mBtnBrowserFile = (Button) findViewById(R.id.btn_browser_file);
        mBtnBrowserFile.setOnClickListener(this);
        mBtnUpdateFw = (Button) findViewById(R.id.btn_update_firmware);
        mBtnUpdateFw.setOnClickListener(this);
        mBtnUpdateFw.setEnabled(false);

        mTvFwStatus = (TextView) findViewById(R.id.tv_firmware_status);

        mLinearLayoutDeviceInfo = (LinearLayout) findViewById(R.id.linear_layout_device_info);
        mTvManufacture = (TextView) findViewById(R.id.tv_manufacture);
        mTvProduct = (TextView) findViewById(R.id.tv_product);
        mTvSerial = (TextView) findViewById(R.id.tv_serial);
        mTvVendorId = (TextView) findViewById(R.id.tv_vendor_id);
        mTvProductId = (TextView) findViewById(R.id.tv_product_id);
        mTvDevice = (TextView) findViewById(R.id.tv_device);
        mTvFwVersion = (TextView) findViewById(R.id.tv_firmware_version);

        mTvCurrentFwPartition = (TextView) findViewById(R.id.tv_current_fw_partition);
        mTvCRCValue = (TextView) findViewById(R.id.tv_crc_value);
    }

    private void showFirmwareFileDialog() {
        if (null != firmwareFileList) {
            firmwareFileList.clear();
        }
        firmwareFileList = LoadFileHelper.getSingleton(this).queryFiles();
        if (1 > firmwareFileList.size()) {
            Toast.makeText(this, "Not found firmware file", Toast.LENGTH_SHORT).show();
            return;
        }
        mFirmwareDialog = new AlertDialog.Builder(this).create();
        mFirmwareDialog.show();
        Window window = mFirmwareDialog.getWindow();
        window.setContentView(R.layout.layout_firmware_file_list);
        firmwareFileLv = (ListView) window.findViewById(R.id.lv_firmware_file);
        firmwareFileLv.setOnItemClickListener(firmwareFileItemClickListener);
        Display display = getWindowManager().getDefaultDisplay();
        int minHeight = (int) (display.getHeight() * 0.16);
        firmwareFileLv.setMinimumHeight(minHeight);

        FirmwareFileAdapter firmwareFileAdapter = new FirmwareFileAdapter(this, firmwareFileList);
        firmwareFileLv.setAdapter(firmwareFileAdapter);
        firmwareFileAdapter.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener firmwareFileItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            curSelectedFirmwarePath = firmwareFileList.get(position).getPath();
            Log.d(TAG, "onItemClick: path = " + curSelectedFirmwarePath);
            refreshState();
            mFirmwareDialog.dismiss();
        }
    };

    private void refreshState() {
        isLoadFirmware = true;
        firmwarePathTv.setText(curSelectedFirmwarePath);

        if (null != SvcModClient.get() && null != SvcModClient.get().getUsbCtrl()) {
            mBtnUpdateFw.setEnabled(true);
            String fileFwVersion = SvcModClient.get().getUsbCtrl().getFileFwVersion(curSelectedFirmwarePath);
            mTvFileFwVersion.setText(fileFwVersion);
        }else{
            if(null != curSelectedFirmwarePath){
                FirmwareUpdate mFirmware = new FirmwareUpdate();
                if(null != mFirmware){
                    String fileFwVersion = mFirmware.fileGetFwVersion(curSelectedFirmwarePath);
                    mTvFileFwVersion.setText(fileFwVersion);
                }
                mFirmware = null;
            }
        }
    }

    public void UpdateFirmwareInfo() {
        mLinearLayoutDeviceInfo.setVisibility(View.VISIBLE);
        if (null != SvcModClient.get()) {
            FirmwareParam firmwareParam = SvcModClient.get().getFirmwareInfo();
            if (null != firmwareParam) {
                showFirmwareInfo(firmwareParam);
            }
/*
            //get Firmware Partition
            int curFwPatition = SvcModClient.get().getCurFwPartition();
            mTvCurrentFwPartition.setText(String.valueOf(curFwPatition));

            //get Firmware CRC value
            long fwCrcValue = SvcModClient.get().getFwCrcValue();
            mTvCRCValue.setText(Long.toHexString(fwCrcValue));
*/
        }
    }

    private void showFirmwareInfo(FirmwareParam param) {
        mTvFwStatus.setText(SynaConstants.DEVICE_CONNECTED);
        mTvManufacture.setText(param.getManufacture());
        mTvProduct.setText(param.getProduct());
        mTvSerial.setText(param.getSerial());
        mTvVendorId.setText(Integer.toHexString(param.getUsb_vendor_id()));
        mTvProductId.setText(Integer.toHexString(param.getUsb_product_id()));
        mTvDevice.setText(String.valueOf(param.getDevice()));
        mTvFwVersion.setText(param.getFirmware_version());
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnBrowserFile) {
            showFirmwareFileDialog();
        }else if (view == mBtnUpdateFw) {
            if (isLoadFirmware && !"".equals(curSelectedFirmwarePath)) {
                if (null != SvcModClient.get() && null != SvcModClient.get().getUsbCtrl()) {
                    Log.d(TAG, "onClick: execute firmware update method");
                    showWarnDialog();
                }
            }
        }
    }

    private void showWarnDialog() {
        String msg = SynaConstants.FW_UPGRADE_WARN_MSG;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(SynaConstants.FW_UPGRADE_WARN_TITLE);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Log.d(TAG, "Synaptics click the Warning dialog\n");
                showUpdateFirmwareDialog();
                mUpdateFWHandler.removeCallbacks(mUpdateFWRunnable);
                mUpdateFWHandler.post(mUpdateFWRunnable);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.show();
    }

    private void showUpdateFirmwareDialog() {
        mUpdateFwProgressDialog = new ProgressDialog(this);
        mUpdateFwProgressDialog.setTitle(SynaConstants.FW_UPGRADE_PROGRESS_MSG);
        mUpdateFwProgressDialog.setCancelable(false);
        mUpdateFwProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        if (mUpdateFwAsyncTask != null && mUpdateFwAsyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            mUpdateFwAsyncTask.cancel(true);
        }
        //new UpdateFirmwareAsyncTask().execute();
        mUpdateFwAsyncTask = new UpdateFwAsyncTask();
        mUpdateFwAsyncTask.execute();
    }

    private class UpdateFwAsyncTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ");
            mUpdateFwProgressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: ");
            try {
                int curProgress = 0;
                updateRtr = 0;
                while (curProgress >= 0 && curProgress <= 99 && updateRtr == 0) {
                    Thread.sleep(50);
                    if(SvcModClient.get() == null || SvcModClient.get().getUsbCtrl() == null){
                        return INSTALL_TOKEN;
                    }
                    curProgress = SvcModClient.get().getUsbCtrl().getUpdateFwProgress();
                    publishProgress(curProgress);
                }

                return INSTALL_TOKEN;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //Log.d(TAG, "onProgressUpdate: values[0] = " + values[0]);
            mUpdateFwProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Log.d(TAG, "onPostExecute: ");
            mUpdateFwProgressDialog.dismiss();
           // Toast.makeText(MainActivity.this, "Firmware Update Success", Toast.LENGTH_SHORT).show();
            showUpdatedToastDialog();
        }
    }

    private void showUpdatedToastDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Notification");
        dialog.setMessage(updateRtr == SynaConstants.RC_SUCCESS? SynaConstants.UPDATE_TOAST_SUCCESS: SynaConstants.UPDATE_TOAST_FAILURE);
        dialog.show();
    }


    private final SvcModClient.DevicePlugListener mDevicePlugListener = new SvcModClient.DevicePlugListener() {
        @Override
        public void onStateChange(SynaUsbDeviceBase mSynaUsbDeviceBase, boolean isAttach) {
            if (isAttach) {
                synchronized (mObjSync) {
                    Log.d(TAG, "onStateChange: permission : " + mSynaUsbDeviceBase.getHasPermission());
                    if (!mSynaUsbDeviceBase.getHasPermission()) {
                        mHasDelayedPermissionRequest = !mIsResumed;
                        if (mIsResumed) {
                            SvcModClient.get().requestPermission();
                        }
                    } else {
                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        initView();
                                        UpdateFirmwareInfo();
                                    }
                                });
                    }
                }
            } else {
                Message msg = new Message();
                msg.obj = 1;
                mHandler.sendMessage(msg);
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj.equals(1)) {
                updateViewAfterDetachDevice();
            }
        }
    };

    private void updateViewAfterDetachDevice() {
        mTvFwStatus.setText(SynaConstants.DEVICE_NOT_FOUND);
        mBtnUpdateFw.setEnabled(false);

        firmwarePathTv.setText(SynaConstants.NON_INFO);
        mTvFileFwVersion.setText(SynaConstants.NON_INFO);
        mLinearLayoutDeviceInfo.setVisibility(View.GONE);

        /*firmwarePathTv.setText(SynaConstants.NON_INFO);
        mTvManufacture.setText(SynaConstants.NON_INFO);
        mTvProduct.setText(SynaConstants.NON_INFO);
        mTvSerial.setText(SynaConstants.NON_INFO);
        mTvVendorId.setText(SynaConstants.NON_INFO);
        mTvProductId.setText(SynaConstants.NON_INFO);
        tv_configuration.setText(SynaConstants.NON_INFO);
        mTvDevice.setText(SynaConstants.NON_INFO);
        mTvFwVersion.setText(SynaConstants.NON_INFO);*/
    }

    private SvcModClient.DevicePermissionListener mDevicePermissionListener = new SvcModClient.DevicePermissionListener() {
        @Override
        public void onStateChange(SynaUsbDeviceBase mSynaUsbDeviceBase, boolean isAllowed) {
            if (isAllowed) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                        UpdateFirmwareInfo();
                    }
                });
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        synchronized (mObjSync) {
            if (mHasDelayedPermissionRequest) {
                SvcModClient.get().requestPermission();
                mHasDelayedPermissionRequest = false;
            }

            mIsResumed = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mIsResumed = false;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (null != mAlertDialog && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }

        if (null != mUpdateFwProgressDialog && mUpdateFwProgressDialog.isShowing()) {
            mUpdateFwProgressDialog.dismiss();
            mUpdateFwProgressDialog = null;
        }

        if (null != mUpdateFWRunnable) {
            mUpdateFWHandler.removeCallbacks(mUpdateFWRunnable);
            mUpdateFWRunnable = null;
        }

        if (null != mUpdateFWHandler) {
            mUpdateFWHandler = null;
        }

        if (mUpdateFwAsyncTask != null && mUpdateFwAsyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            mUpdateFwAsyncTask.cancel(true);
        }
        SvcModClient.releaseClient();

        super.onDestroy();
    }

}
