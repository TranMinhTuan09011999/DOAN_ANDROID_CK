package com.example.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.R;
import com.example.doan.model.Selection;

import java.util.ArrayList;

public class SelectionAdapter extends BaseAdapter {
    ArrayList<Selection> list;
    Context context;

    public SelectionAdapter(ArrayList<Selection> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        ImageView img;
        TextView txt;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_navigation,null);
            viewHolder.img = convertView.findViewById(R.id.imgitemMenu);
            viewHolder.txt = convertView.findViewById(R.id.txtitemMenu);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Selection selection = list.get(position);
        viewHolder.img.setImageResource(selection.getImg());
        viewHolder.txt.setText(selection.getName().toString());
        Animation ani = AnimationUtils.loadAnimation(context,R.anim.ani_itemlistview);
        convertView.setAnimation(ani);
        return convertView;
    }
}
