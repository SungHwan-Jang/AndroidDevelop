package com.syna.updatefirmwaredemo.common;

/**
 * Created by xiec2 on 11/6/2017.
 */
public class SynaConstants {
    public static final int OPTION_UPGRADE = 1;
    public static final int OPTION_VERIFY = 2;

    public static final int RC_SUCCESS = 0;
    public static final int RC_GENERIC_FAILURE = -1001;

    public final static String UPDATE_TOAST_SUCCESS = "Firmware Update Success";
    public final static String UPDATE_TOAST_FAILURE = "Firmware Update Failure";

    public final static String NON_INFO = "";
    public final static String DEVICE_NOT_FOUND = " device not found";
    public final static String DEVICE_CONNECTED = " device connected";

    public static final String FW_UPGRADE_WARN_MSG = "Don't disconnect or power down the device during the upgrade process. Doing so could result in a broken device!";
    public static final String FW_UPGRADE_WARN_TITLE = "Warning";
    public static final String FW_UPGRADE_PROGRESS_MSG = "Update Firmware is in progress...";

    public static final int FW_PARTITION_DEFAULT_FACTORY = 1;
    public static final int FW_PARTITION_SYSTEM_UPDATE = 2;
}
