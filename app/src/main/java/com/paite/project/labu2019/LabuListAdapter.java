package com.paite.project.labu2019;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.folioreader.Labu.Labu;
import com.folioreader.Labu.LabuData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LabuListAdapter extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater=null;

    public LabuListAdapter(Activity activity) {
        this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

  /*  public LabuListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        _resource = resource;
    }*/

    @Override
    public int getCount() {
        return LabuData.get().getNoOfLabu();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        View v = convertView;
        PlaceHolder holder = null;
        if(convertView == null){
            Log.d("list adapter", "getview " + position);

            //  LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v = layoutInflater.inflate(_resource,parent,false);
            v = inflater.inflate(R.layout.list_row, null);
            // v = layoutInflater.inflate(R.layout.img_row_list_layout,null);
            holder = new PlaceHolder();
            TextView upView = (TextView)v.findViewById(R.id.title);
            TextView downView = (TextView)v.findViewById(R.id.publisher);
            ImageView icon = (ImageView)v.findViewById(R.id.list_image);
            holder.upTextView = upView;
            holder.bottomTextView = downView;
            holder.iconView = icon;
            v.setTag(holder);

        }
        else {
            holder = (PlaceHolder)v.getTag();
        }
        Labu labu = LabuData.get().getLabu(position);
        Log.d("list adapter " , labu.get_LabuMin());
        holder.upTextView.setText(labu.get_LabuMin());
        holder.bottomTextView.setText(labu.get_Publisher());
        holder.iconView.setImageBitmap(labu.getIconBitmap());

        return v;
    }

    /* *********************************
     * We use the holder pattern
     * It makes the view faster and avoid finding the component
     * **********************************/

    private static class PlaceHolder {
        public TextView upTextView;
        public TextView bottomTextView;
        public ImageView iconView;
    }
}
