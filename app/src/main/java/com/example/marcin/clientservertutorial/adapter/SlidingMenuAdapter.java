package com.example.marcin.clientservertutorial.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marcin.clientservertutorial.R;
import com.example.marcin.clientservertutorial.model.ItemSlideMenu;

import java.util.List;

/**
 * Created by Marcin on 2016-12-06.
 */
public class SlidingMenuAdapter extends BaseAdapter {

    private Context context;
    private List<ItemSlideMenu> IstItem;

    public SlidingMenuAdapter(Context context, List<ItemSlideMenu> istItem) {
        this.context = context;
        IstItem = istItem;
    }

    @Override
    public int getCount() {
        return IstItem.size();
    }

    @Override
    public Object getItem(int position) {
        return IstItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_sliding_menu, null);
        ImageView img = (ImageView)v.findViewById(R.id.item_img);
        TextView tv = (TextView)v.findViewById(R.id.item_title);

        ItemSlideMenu item = IstItem.get(position);
        img.setImageResource(item.getImgId());
        tv.setText(item.getTitle());

        return v;
    }
}
