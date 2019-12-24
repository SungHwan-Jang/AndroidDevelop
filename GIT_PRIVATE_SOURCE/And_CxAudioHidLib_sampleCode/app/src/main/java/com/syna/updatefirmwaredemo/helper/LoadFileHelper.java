package com.syna.updatefirmwaredemo.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.syna.updatefirmwaredemo.common.FileBean;
import com.syna.updatefirmwaredemo.common.CommonUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class LoadFileHelper {
    private static final String TAG = "LoadFileHelper";

    private Context mContext;
    private static LoadFileHelper mLoadFileHelper = null;
    private static final String PATH = Environment.getExternalStorageDirectory().toString();
    private static final String SEARCH_URI = "content://media/external/file";

    public LoadFileHelper(Context context) {
        mContext = context;
    }

    public static LoadFileHelper getSingleton(Context context) {
        if (null == mLoadFileHelper) {
            mLoadFileHelper = new LoadFileHelper(context);
        }
        return mLoadFileHelper;
    }

    public List<FileBean> queryFiles() {//String[] extension
        List<FileBean> fileBeanList = new ArrayList<>();
        String filePath = CommonUtil.getFirmwareDirPath();
        Log.d(TAG, "getFileDir: filePath= " + filePath);
        try {
            String suffixName = ".hid";

            File f = new File(filePath);
            File[] files = f.listFiles();// list all file
            if (files != null) {
                int count = files.length;// file count
                for (int i = 0; i < count; i++) {
                    FileBean firmwareFileBean = new FileBean();
                    File file = files[i];
                    if (file.getPath().contains(suffixName)) {
                        firmwareFileBean.setPath(file.getPath());
                        firmwareFileBean.setName(file.getName());
                        fileBeanList.add(firmwareFileBean);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return fileBeanList;
    }

    private String getFormatName(String url){
        File file = new File(url);
        return file.getName();
    }

    /*Cursor cursor = mContext.getContentResolver().query(
                Uri.parse(SEARCH_URI),
                projection,
                MediaStore.Files.FileColumns.DATA + " like ?",
                new String[]{"%.ptc"},
                null);*/

}
