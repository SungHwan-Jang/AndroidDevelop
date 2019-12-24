package com.syna.updatefirmwaredemo.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yuh3 on 9/9/2016.
 */
public class CommonUtil {
    private static final String TAG = CommonUtil.class.getSimpleName();


    public static String SHARED_PREFERENCE_PARAM = "SharedPrefsStrParam";
    public static final String SD_PATH = Environment.getExternalStorageDirectory().toString();
    public static final String APP_DIR_NAME = "CnxtUSBTypeC";
    public static final String FIRMWARE_UPDATE_DIR_NAME = "FirmwareUpdate";

    private static TypedValue mTmpValue = new TypedValue();

    public static String getAppDirPath() {
        File appDirFile = new File(SD_PATH, APP_DIR_NAME);
        return appDirFile.getAbsolutePath();
    }

    public static String getFirmwareDirPath() {
        File firmwareFile = new File(getAppDirPath(), FIRMWARE_UPDATE_DIR_NAME);
        return firmwareFile.getAbsolutePath();
    }

    public static String getFirmwareFilePath() {
        String firmwareFilePath = "";
        File file = new File(getFirmwareDirPath());
        File[] subFile = file.listFiles();
        for (int i = 0; i < subFile.length; i++) {
            if (!subFile[i].isDirectory()) {
                firmwareFilePath = subFile[i].getAbsolutePath();
            }
        }
        return firmwareFilePath;
    }

    public static void createAppDirFile() {
        try {
            File file = new File(getAppDirPath());
            if (!file.exists()) {
                Log.d(TAG, "createAppDirFile: create APP_DIR_PATH");
                file.mkdirs();
            }

            File firmwareUpdateFile = new File(getFirmwareDirPath());
            if (!firmwareUpdateFile.exists()) {
                Log.d(TAG, "createAppDirFile: create FIRMWARE_UPDATE_DIR_NAME");
                firmwareUpdateFile.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SAVE and GET Map<String,String>
    public static void putStrMapList(Context context, String key, List<Map<String, String>> mapLists) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < mapLists.size(); i++) {
            Map<String, String> itemMap = mapLists.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();

            JSONObject object = new JSONObject();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mJsonArray.put(object);
        }

        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCE_PARAM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, mJsonArray.toString());
        editor.commit();
    }

}
