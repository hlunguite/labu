package com.folioreader.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.folioreader.Labu.Labu;
import com.folioreader.Labu.LabuData;
import com.folioreader.R;
import com.folioreader.ui.activity.CurrentActivity;

import static com.folioreader.Constants.BOOK_TITLE;
import static com.folioreader.Constants.CHAPTER_SELECTED;
import static com.folioreader.Constants.SELECTED_CHAPTER_INDEX;
import static com.folioreader.Constants.SELECTED_CHAPTER_POSITION;
import static com.folioreader.Constants.TYPE;

public class SongGridAdapter extends BaseAdapter {

    private int _size;
    private int _resource;
    private Context _conttext;
    private int _textSize;
    private boolean _ascending;

    public SongGridAdapter(int _size, int _resource, Context _conttext, int _textSize, boolean ascending ) {
        this._size = _size;
        this._resource = _resource;
        this._conttext = _conttext;
        this._textSize = _textSize;
        this._ascending = ascending;

    }

    @Override
    public int getCount() {
        return _size;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        PlaceHolder holder = null;
        if(v == null){
            LayoutInflater layoutInflater = (LayoutInflater) _conttext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(_resource,parent,false);
            holder = new PlaceHolder();
            Button songNoView = (Button)v.findViewById(R.id.btn_songpick);
            holder._songNoButton = songNoView;

            v.setTag(holder);
        }else {
            holder = (PlaceHolder) v.getTag();
        }
        Labu labu = LabuData.get().getCurrentLabu();
        int laindex = position;
        if (_ascending == false) {
            laindex = _size - 1 - position;
        }
        String lano =  labu.get_laNo(laindex);

        holder._songNoButton.setText(lano);
        holder._songNoButton.setTextSize(_textSize);


        holder._songNoButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        int index = position;
                                                        if (_ascending == false) {
                                                            index = _size - 1 - position;
                                                        }
                                                        Log.d("grid buttoon", "ckick " + index);

                                                        Intent intent=new Intent();
                                                        intent.putExtra(SELECTED_CHAPTER_POSITION, "");
                                                       // intent.putExtra(BOOK_TITLE, tocLinkWrapper.getTocLink().getTitle());
                                                        intent.putExtra(TYPE, CHAPTER_SELECTED);
                                                        intent.putExtra(SELECTED_CHAPTER_INDEX, index);
                                                        CurrentActivity.getInstance().getCurrentActivity().setResult(Activity.RESULT_OK, intent);
                                                        CurrentActivity.getInstance().getCurrentActivity().finish();
                                                    }
                                                }

        );

        return v;

    }

    private static class PlaceHolder {
        public Button _songNoButton ;
    }
}
