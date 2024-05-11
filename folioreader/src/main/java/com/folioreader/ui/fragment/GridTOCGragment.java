package com.folioreader.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.folioreader.Config;
import com.folioreader.Labu.Labu;
import com.folioreader.Labu.LabuApplication;
import com.folioreader.Labu.LabuData;
import com.folioreader.R;
import com.folioreader.ui.adapter.OnItemClickListener;
import com.folioreader.ui.adapter.SongGridAdapter;
import com.folioreader.util.AppUtil;
import com.folioreader.util.UiUtil;

import org.jetbrains.annotations.NotNull;
import org.readium.r2.shared.Publication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static com.folioreader.Constants.BOOK_TITLE;
import static com.folioreader.Constants.PUBLICATION;
import static com.folioreader.Constants.SELECTED_CHAPTER_POSITION;

public class GridTOCGragment extends Fragment  {

    private TextView errorView;
    private Config mConfig;
    private String mBookTitle;
    private Publication publication;
    private GridView songGrid;
    private SongGridAdapter gridAdapter;
    private boolean ascending;
    public static GridTOCGragment newInstance(Publication publication,
                                              String selectedChapterHref, String bookTitle) {
        GridTOCGragment tableOfContentFragment = new GridTOCGragment();
        Bundle args = new Bundle();
        args.putSerializable(PUBLICATION, publication);
        args.putString(SELECTED_CHAPTER_POSITION, selectedChapterHref);
        args.putString(BOOK_TITLE, bookTitle);
        tableOfContentFragment.setArguments(args);

        return tableOfContentFragment;
    }
    public void setAscending( boolean ascending) {
        this.ascending = ascending;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        publication = (Publication) getArguments().getSerializable(PUBLICATION);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.song_no_grid, container, false);
        mConfig = AppUtil.getSavedConfig(getActivity());
        mBookTitle = getArguments().getString(BOOK_TITLE);
        if (mConfig.isNightMode()) {
            mRootView.findViewById(R.id.song_grid).
                    setBackgroundColor(ContextCompat.getColor(getActivity(),
                            R.color.black));
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songGrid = (GridView)view.findViewById(R.id.song_grid);
        Labu labu = LabuData.get().getCurrentLabu();
        int size = labu.get_noofSong();
        int textsize = getNumberOfCol(size);
        gridAdapter = new SongGridAdapter(size, R.layout.song_pick_button, getActivity(), textsize, ascending);
        songGrid.setAdapter(gridAdapter);
        /*songGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Grid lick for ", "position " + position);
            }
        });*/

    }

    private int getNumberOfCol(int size) {
        int textsize = 15;
        boolean portrait = UiUtil.isPortrait();
        if (size <= 6) {
            songGrid.setNumColumns(1);
            return textsize;
        }
        else if (size <= 10){
            songGrid.setNumColumns(size);
            return textsize;
        }else if(size > 100){
            textsize = 14;
        }
        //Log.d("Height of view ", "" + gridView.getHeight() + " " + gridView.getWidth());
        int widthOfTheScreen = LabuApplication.getApplication().getResources().getDisplayMetrics().widthPixels;
        int heightOfTheScreen = LabuApplication.getApplication().getResources().getDisplayMetrics().heightPixels;
        int paddingtoView = LabuApplication.getApplication().getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        widthOfTheScreen -= (paddingtoView*2);   // remove the padding
        heightOfTheScreen -=(paddingtoView*2);
        double areaOfCells = (widthOfTheScreen*heightOfTheScreen)/size;
        double side = Math.sqrt(areaOfCells);
        side = Math.round(side);
        int finalside = (int)side;
        int nocolumn = widthOfTheScreen/finalside;
        //textsize = 12;//11;
        if(portrait ){
            if(nocolumn > 10){
                nocolumn = 10;
                textsize = 11;
            }
        }else if(nocolumn > 15){
            nocolumn = 15;
            textsize = 11;
        }
        // Log.d("final noofcolumn", "" + nocolumn);
        songGrid.setNumColumns(nocolumn);
        return textsize;
    }



}
