package com.syna.updatefirmwaredemo.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.syna.updatefirmwaredemo.R;
import com.syna.updatefirmwaredemo.common.FileBean;

import java.util.List;

/**
 * Created by yuh3 on 12/22/2016.
 */
public class FirmwareFileAdapter extends BaseAdapter {

    private Context mContext;
    private List<FileBean> mFirmwareFileList;

    public FirmwareFileAdapter(Context context, List<FileBean> firmwares) {
        mContext = context;
        mFirmwareFileList = firmwares;
    }

    @Override
    public int getCount() {
        return mFirmwareFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFirmwareFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_firmware_list_item, parent, false);
            viewHolder.tv_firmware_name = (CusTextView) convertView.findViewById(R.id.tv_firmware_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_firmware_name.setText(mFirmwareFileList.get(position).getName());
        return convertView;
    }

    public class ViewHolder {
        public CusTextView tv_firmware_name;
    }
}
