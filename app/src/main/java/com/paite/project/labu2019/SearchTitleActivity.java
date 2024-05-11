package com.paite.project.labu2019;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;

import com.folioreader.Labu.LaTelnaAdapter;
import com.folioreader.Labu.Labu;
import com.folioreader.Labu.LabuData;
import com.folioreader.Labu.LabuLaTelna;
import com.folioreader.Labu.LabuLatel;
import com.folioreader.Labu.constant;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchTitleActivity extends AppCompatActivity {
    //private LaMinAdapter _laAdapter;
    LabuLaTelna _laTenla = null;
   // private List<LaMin> _lamin;
    RecyclerView _recycleView = null;
    public static final String BOOK_NO = "BOOK_NO";
    public static final String LA_NO = "LA_NO";
    Toolbar toolbar = null;
    ActionBar actionBar = null;
    ImageButton collapseButton = null;
    List<LabuLatel> latel;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            Intent intent = new Intent();
            LabuLatel la = latel.get(position);

          //  LaMin lamin = _laAdapter.getLaMin(position);
            intent.putExtra(BOOK_NO,la.getBookno());
            intent.putExtra(LA_NO, la.getLano());
            setResult(Activity.RESULT_OK, intent);
            finish();
            //Log.d(" reccle view ", _laAdapter.getLaMin(position).get_lamin());
        }
    };

    private View.OnLayoutChangeListener layoutChangeListener = new View.OnLayoutChangeListener(){

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            for (int i = 0; i < toolbar.getChildCount() ; i++) {
                View view = toolbar.getChildAt(i);
                String desc = (String)view.getContentDescription();
                 if (TextUtils.isEmpty(desc)) {
                     continue;
                 }
                Log.d("Searchtitle1", desc);

                if (desc == "Collapse") {
                    collapseButton = (ImageButton)view;
                    collapseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("Searchtitle","back click");
                            navigateUp();
                        }
                    });

                    toolbar.removeOnLayoutChangeListener(this);
                    return;
                }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_title);
        toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        _laTenla = new LabuLaTelna(this,-1);

         //       fillLaList();
        setupRecycleView();
        toolbar.addOnLayoutChangeListener(layoutChangeListener);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

    }

    private void setupRecycleView() {
        _recycleView = findViewById(R.id.lamin_list);
        _recycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        _recycleView.setLayoutManager(layoutManager);
        //_laAdapter = new LaMinAdapter(_lamin);

        //recyclerView.setAdapter(_laAdapter);
        //_laAdapter.setOnItemClickListener(onItemClickListener);


    }

  /*  private void fillLaList() {
        Bundle bundle = getIntent().getBundleExtra("TITLES");
        _lamin = (ArrayList<LaMin>) bundle.getSerializable("TITLES");

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_search, menu);

        MenuItem searchItem = menu.findItem(R.id.title_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchItem.expandActionView();

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(this, SearchTitleActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty() == false) {
                    latel = _laTenla.search(newText);
                }else {
                    latel = new ArrayList<LabuLatel>();
                }
                LaTelnaAdapter laTelnaAdapter = new LaTelnaAdapter(latel,newText, true);
                laTelnaAdapter.setOnItemClickListener(onItemClickListener);
                _recycleView.setAdapter(laTelnaAdapter);
              //  _laAdapter.getFilter().filter(newText);


                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       // Log.d("Search menu click ", "id " + id + " " + android.R.id.home );
        if (id == android.R.id.home) {
            navigateUp();
        }
        return super.onOptionsItemSelected(item);
    }
    public void navigateUp() {
        //Log.d("Searchtitle", "nevigateupfunction");
        Intent intent = new Intent();
        //LaMin lamin = _laAdapter.getLaMin(position);
        //intent.putExtra(BOOK_NO,lamin.get_labuid());
        //intent.putExtra(LA_NO, lamin.get_laid());
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
