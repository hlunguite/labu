package com.paite.project.labu2019;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.folioreader.Labu.Labu;
import com.folioreader.Labu.LabuData;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LabuAdapter extends RecyclerView.Adapter<LabuAdapter.LabuViewHolder> {
    private View.OnClickListener mOnItemClickListener;
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public LabuAdapter() {

    }
    @NonNull
    @Override
    public LabuAdapter.LabuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.labu_min_view,
                parent, false);

        /*int width =  parent.getMeasuredWidth()/2;
        float height = (float) ((float) width * 1.414);//(Width/Height)
        Log.d("labuadapter " , "width " + width +" height " + height);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();

        params.height = Math.round(height);
        v.setLayoutParams(params);*/
        return new LabuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LabuAdapter.LabuViewHolder holder, int position) {
        Labu labu = LabuData.get().getLabu(position);
       // Log.d("bindview ", "Labu min " + labu.get_LabuMin() + " " + labu.get_iconID());
        holder._icon.setImageResource(labu.get_iconID());
        holder._labuminView.setText(labu.get_LabuMin());
        holder._labuminView.setVisibility(TextView.GONE);
        holder._descView.setText(labu.get_Publisher());
        holder._descView.setVisibility(TextView.GONE);
    }

    @Override
    public int getItemCount() {
        return LabuData.get().getNoOfLabu();
    }

    class LabuViewHolder extends RecyclerView.ViewHolder {
        ImageView _icon;
        TextView _labuminView;
        TextView _descView;
        public LabuViewHolder(@NonNull View itemView) {
            super(itemView);
            _icon = itemView.findViewById(R.id.labuicon);
            _labuminView = itemView.findViewById(R.id.labumin);
            _descView = itemView.findViewById(R.id.labudesc);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
           // _icon.setOnClickListener(mOnItemClickListener);
        }
    }
}
