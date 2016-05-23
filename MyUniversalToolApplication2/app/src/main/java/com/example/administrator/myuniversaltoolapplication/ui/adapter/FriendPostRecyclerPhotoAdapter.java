package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/2/21.
 */
public class FriendPostRecyclerPhotoAdapter extends BaseAdapter {
    private ArrayList<BmobFile> bmobFilesList;
    private Context context;
    private GridView gv_photo;

    public FriendPostRecyclerPhotoAdapter(GridView gv_photo, Context context, ArrayList<BmobFile> bmobFilesList) {
        this.context = context;
        this.bmobFilesList = bmobFilesList;
        this.gv_photo = gv_photo;
    }

    @Override
    public int getCount() {
        return bmobFilesList.size();
    }

    @Override
    public Object getItem(int position) {
        return bmobFilesList.get(position);
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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(bmobFilesList.get(position).getFileUrl(context)).into(holder.iv_photo);
        Logger.d("-----------------" + position + "---------------------");
        return convertView;
    }

    class ViewHolder {

        public ImageView iv_photo;

    }
}
