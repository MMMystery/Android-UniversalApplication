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
public class FriendPostRecyclerPhotoAdapter extends BaseAdapter {
    private BmobFile[] fileStr;
    private Context context;

    public FriendPostRecyclerPhotoAdapter(Context context, BmobFile[] fileStr) {
        this.context = context;
        this.fileStr = fileStr;
    }

    @Override
    public int getCount() {
        return fileStr.length;
    }

    @Override
    public Object getItem(int position) {
        return fileStr[position];
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friendpost_photos, null);

            holder.iv_photo = (ImageView) convertView.findViewById(R.id.friendpostfragmentphoto_item_iv_photo);

              Logger.d("xxxxxxxxxxxxxxxxxxxxx" + fileStr.length + "xxxxxxxxxxxxxxx");


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//因为它是一个item，所以本来就会执行多次，不是去for（）和用i去设值
      /*  Logger.d("xxxxxxxxxxxxxxxxxxxxx" + fileStr.length + "xxxxxxxxxxxxxxx");
        for (int i =0;i<fileStr.length;i++){
            Logger.d("ddddddddddddddddddddddddd" + fileStr[i].toString() + "dddddddddddddddddddddd");//对的
            Picasso.with(context).load(fileStr[i].getFileUrl(context)).into(holder.iv_photo);
        }
        Logger.d("-----------------" + position + "---------------------");
        Logger.d(",,,,,,,,,,,,,,,,,,,,,,,," + fileStr[position].toString() + ",,,,,,,,,,,,,,,,,");*/
//        for (int i =0;i<fileStr.length;i++){
//            Logger.d("ddddddddddddddddddddddddd" + fileStr[i].toString() + "dddddddddddddddddddddd");//对的
            Picasso.with(context).load(fileStr[0].getFileUrl(context)).into(holder.iv_photo);
//        }
        Logger.d("-----------------" + position + "---------------------");
        Logger.d(",,,,,,,,,,,,,,,,,,,,,,,," + fileStr[position].toString() + ",,,,,,,,,,,,,,,,,");

        return convertView;
    }

    class ViewHolder {

        public ImageView iv_photo;

    }
}
