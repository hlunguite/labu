package com.paite.project.labu2019;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LaMinAdapter extends RecyclerView.Adapter<LaMinAdapter.LaMinViewHolder> implements Filterable {
    private List<LaMin> _laMinList;
    private List<LaMin> _laMinListFull;
    private View.OnClickListener mOnItemClickListener;

    public LaMinAdapter(List<LaMin> _laMinList) {
        this._laMinList = _laMinList;
        this._laMinListFull = new ArrayList<>(_laMinList);
    }
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
    public LaMin getLaMin( int i ) {
        return _laMinList.get(i);
    }

    class LaMinViewHolder extends RecyclerView.ViewHolder {
        ImageView _icon;
        TextView _laminView;
        TextView _labuMinView;
        public LaMinViewHolder(@NonNull View itemView) {
            super(itemView);
            _icon = itemView.findViewById(R.id.labu_icon);
            _laminView = itemView.findViewById(R.id.la_min);
            _labuMinView = itemView.findViewById(R.id.labu_min);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }


    @NonNull
    @Override
    public LaMinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lamin_view_layout,
                parent, false);

        return new LaMinViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LaMinViewHolder holder, int position) {
        LaMin lamin =   _laMinList.get(position);
        holder._icon.setImageResource(lamin.get_labuResourceid());
        holder._icon.setVisibility(View.GONE);
        holder._labuMinView.setText(lamin.get_labuMin() + " " +lamin.get_laNo());
        holder._laminView.setText(lamin.get_lamin());

    }

    @Override
    public int getItemCount() {
        return _laMinList.size();
    }

    private Filter laFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<LaMin> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() ==0 ){
                filterList.addAll(_laMinListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (LaMin item : _laMinListFull) {
                    Log.d("Trying to match", item.get_lamin().toLowerCase() +" with " + filterPattern);
                    if (item.get_lamin().toLowerCase().startsWith(filterPattern)) {
                        Log.d("match", item.get_lamin());
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            _laMinList.clear();
            _laMinList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return laFilter;
    }


}
