package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/2/21.
 */
public class HotPostRecyclerPhotoAdapter extends BaseAdapter {
    private List<BmobFile> fileList;
    private Context context;

    public HotPostRecyclerPhotoAdapter(Context context, List<BmobFile> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hotpost_photos, null);

            holder.iv_photo = (ImageView) convertView.findViewById(R.id.hotpostfragmentphoto_item_iv_photo);

            for (int i = 0; i < fileList.size(); i++) {
                Picasso.with(context).load(fileList.get(position).getFileUrl(context)).into(holder.iv_photo);
            }
            Logger.d(fileList.get(position).getFileUrl(context)+"=========================");

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        if (null != fileList) {

//        }
        return convertView;
    }

    class ViewHolder {

        public ImageView iv_photo;

    }
}
