package com.paite.project.labu2019;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import com.folioreader.Config;
import com.folioreader.FolioReader;
import com.folioreader.Labu.Labu;
import com.folioreader.Labu.LabuApplication;
import com.folioreader.Labu.LabuData;
import com.folioreader.Labu.PDFReader;
import com.folioreader.Labu.constant;
import com.folioreader.model.HighLight;
import com.folioreader.model.locators.ReadLocator;
import com.folioreader.util.AppUtil;
import com.folioreader.util.SharedPreferenceUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcel;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paite.project.labu2019.EpubReader;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadLocatorListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnHighlightListener, ReadLocatorListener, FolioReader.OnClosedListener
{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private  FolioReader folioReader;
    private String lastReadKPref = "LAST_READ";
    private String lastread;
    private ArrayList<LaMin> _laMinListFull;
    private LabuAdapter _labuAdapter;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            openBook(position, -1);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        //toolbar.setNavigationIcon(R.drawable.labu);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.labu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Log.d("Mainactivity", "mainactivity");
        Bundle bundle = getIntent().getExtras();
        _laMinListFull = bundle.getParcelableArrayList("laminlistfull");
        setupRecycleView();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }

    private void setupRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.labulist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        _labuAdapter = new LabuAdapter();

        recyclerView.setAdapter(_labuAdapter);
        _labuAdapter.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
        PDFReader.get().closeRanderer();
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_info) {
            Intent intent = new Intent(this, theihtuak.class);
            //new Intent(this, about.class);
            //new Intent(this, AboutApp.class);
            startActivity(intent);



            return true;
        }

       if (id == R.id.search_title) {
           Intent intent = new Intent (this, SearchTitleActivity.class);
           Bundle bundle = new Bundle();
           ArrayList<LaMin> lst = new ArrayList<>();
           lst.addAll(_laMinListFull);
           bundle.putSerializable("TITLES", lst);
           intent.putExtra("TITLES",bundle);
           //intent.put("TITLES", _laMinListFull);
           startActivityForResult(intent, constant.SEARCHTITLE);

            return true;
        }

        return true;
    }


    @Override
    public void onFolioReaderClosed() {

    }

    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {

    }

    @Override
    public void saveReadLocator(ReadLocator readLocator) {

        Log.i(LOG_TAG, "-> saveReadLocator -> " + readLocator.toJson());

      //  SharedPreferenceUtil.putSharedPreferencesString(this,lastReadKPref, readLocator.toJson());
        //lastread =  SharedPreferenceUtil.getSharedPreferencesString(this,lastReadKPref,"");

    }
/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openBook(position, -1);


    }*/

    private void openBook(int position, int songindex) {
        LabuData.get().set_currentLabuIndex(position);

        Labu labu = LabuData.get().getCurrentLabu();
        String pdffile = labu.get_LabuPdfFile();
        String pdffilepath = labu.get_LabuPdfPath();
        if (pdffile != null && pdffilepath != null) {
            PDFReader.get().openPDFFile(pdffilepath,pdffile);
        }else {
            Log.d("MainActivity" , "No PDFfile");
        }

        folioReader = FolioReader.get()
                .setOnHighlightListener(this)
                .setReadLocatorListener(this)
                .setOnClosedListener(this);
        Config config = AppUtil.getSavedConfig(getApplicationContext());
        if (config == null)
            config = new Config();
        config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);
        config.setDirection(Config.Direction.HORIZONTAL);
        config.setThemeColorRes(R.color.default_theme_accent_color);
//                config.setShowTts(true);

      /*  if (lastread.isEmpty() == false){
            ReadLocator readLocator = ReadLocator.fromJson(lastread);
            Log.i(LOG_TAG, "-> setReadLocator -> " + readLocator.toJson());

            folioReader.setReadLocator(readLocator);
        }*/
        String file = "file:///android_asset/" +  labu.get_LabuTextFullPathInAsset();
        //folioReader.setConfig(config, true).openBook("file:///android_asset/labupi/labu/pnlepub");

        folioReader.setConfig(config, true).openBook(file,songindex);
        //EpubReader reader = new EpubReader (getApplicationContext());
        //reader.readBook(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  Log.d(LOG_TAG, "activity result " + requestCode + " " + resultCode + " :" + Activity.RESULT_OK );
        if (requestCode== constant.SEARCHTITLE) {
            if (resultCode == Activity.RESULT_OK) {
                int labuno = data.getIntExtra(SearchTitleActivity.BOOK_NO, -1);
                int lano = data.getIntExtra(SearchTitleActivity.LA_NO, -1);
                if (labuno > -1) {
                    openBook(labuno, lano);
                }
               // Log.d(LOG_TAG, "labu selected " + labuno + " no " + lano);
            }
        }
    }

//https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
