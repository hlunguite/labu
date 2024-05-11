package com.paite.project.labu2019;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.folioreader.Labu.Labu;
import com.folioreader.Labu.LabuApplication;
import com.folioreader.Labu.LabuData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;

public class SplashScreen extends Activity {

    private ArrayList<LaMin> _laMinListFull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        LabuApplication.setApplication(getApplication());
        new PrefetchData().execute();
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            LabuData.get().populateLabuData();

            //  Log.d("main activity ", "no of labu " + LabuData.get().getNoOfLabu());

            _laMinListFull = new ArrayList<LaMin>();
            int no = LabuData.get().getNoOfLabu();
            for (int i = 0; i < no; i++) {
                Labu labu = LabuData.get().getLabu(i);
                String labumin = labu.get_LabuMin();
                Log.d("MainActiviyt", labumin);
                if(labumin.equals("Biakna Late")) {
                    labu.set_iconID(R.drawable.bl1);
                    Log.d("set icon for ", "biaknala");
                }else if (labumin.equals("Pathian Ngaih La")) {
                    labu.set_iconID(R.drawable.pn1);
                    Log.d("set icon for ", "pathianngahla");

                }else if (labumin.contains("Suangmantam")){
                    labu.set_iconID(R.drawable.smt1);
                    Log.d("set icon for ", "suangmantam");

                }else if (labumin.contains("Biakna leh Phatna")){
                    labu.set_iconID(R.drawable.bp1);
                    Log.d("set icon for ", "biaknalehphatna");

                }else {
                    continue;
                }

                int lazah = labu.get_noofSong();
                for (int j = 0; j < lazah; ++j) {
                    _laMinListFull.add(new LaMin(labu.get_iconID(), labu.get_laNo(j), labu.get_laMin(j), labu.get_LabuMin(), i, j ));
                }
            }

            Collections.sort(_laMinListFull);
            return null;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("laminlistfull", _laMinListFull);
            i.putExtras(bundle);
            startActivity(i);

            // close this activity
            finish();
        }
    }
}
