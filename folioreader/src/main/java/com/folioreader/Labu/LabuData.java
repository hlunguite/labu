package com.folioreader.Labu;

import android.app.LauncherActivity;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class LabuData {
    private static final String LOG_TAG = LabuData.class.getSimpleName();

    private static LabuData _labuData = null;
    private int   _noofLabu = 0;
    private int   _currentLabuIndex = -1;

    private ArrayList<Labu> _labuList = null;
    private LabuData() {
        //_labuData = new LabuData();
    }
    public Labu getCurrentLabu() {
        return _labuList.get(_currentLabuIndex);
    }
    public void set_currentLabuIndex(int i) {
        _currentLabuIndex = i;
    }
    public int get_currentLabuIndex() { return _currentLabuIndex;}
    public int getNoOfLabu() {
        return _labuList.size();
    }
    public  Labu getLabu(int i) {
        return _labuList.get(i);
    }
    public void clear() {
        _noofLabu = 0;
        if (_labuList == null)
            _labuList = new ArrayList<Labu>();
        _labuList.clear();
    }
    public boolean isLabuData() {
        if (_noofLabu > 0) return true;

        return false;
    }
    public static LabuData get() {
        if (_labuData == null) {
            synchronized (LabuData.class) {
                if (_labuData == null) {

                    _labuData = new LabuData();
                }
            }
        }

        return _labuData;
    }
    void populateProperty (String propertyfile) {
        Log.d(LOG_TAG," prporrty file " + propertyfile);
        InputStream inputStream = null;
        AssetManager assetManager = LabuApplication.getApplication().getAssets();
        try {
            inputStream = assetManager.open(constant.CONFIG_DIR + File.separator + propertyfile);
            Properties prop = new Properties();
            prop.load(inputStream);

            boolean status = addLaProperty(prop);
            if (status == false) {
                return;
            }
            ++_noofLabu;

            inputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void populateLabuData() {
        if (isLabuData()) {
            return;
        }
        clear();
        AssetManager assetManager = LabuApplication.getApplication().getAssets();

       // try {
            //biaknala.property
            //biaknalehphatna.property
            //pathianngahla.property
            //suangmantamte.property
            populateProperty("pathianngahla.property");
            populateProperty("biaknala.property");
            populateProperty("suangmantamte.property");
            populateProperty("biaknalehphatna.property");

         /*   String [] proplist = assetManager.list(constant.CONFIG_DIR);
            for (String file: proplist) {
                Log.d(LOG_TAG," prporrty file " + file);
                InputStream inputStream = null;
                inputStream = assetManager.open(constant.CONFIG_DIR + File.separator + file);
                Properties prop = new Properties();
                prop.load(inputStream);

                boolean status =  addLaProperty(prop);
                if(status == false){
                    continue ;
                }
                ++_noofLabu;

                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    private  boolean addLaProperty(Properties prop) {

        String Description = prop.getProperty(constant.Description);
        String Publisher = prop.getProperty(constant.Publisher);
        String LabuMin = prop.getProperty(constant.LabuMin);
        String LabuTextPath = prop.getProperty(constant.LabuTextPath);
        String CopyRight = prop.getProperty(constant.CopyRight);
        String LabuMinTom = prop.getProperty(constant.LabuMinTom);
        String LabuDataPath = prop.getProperty(constant.LabuDataPath);
        String LabuPdfPath = prop.getProperty(constant.LabuPdfPath);
        String NoOfSong = prop.getProperty(constant.NoOfSong);
        String LabuIconFile = prop.getProperty(constant.LabuIconFile);
        String LabuPdfFile = prop.getProperty(constant.LabuPdfFile);
      //  Log.d("Labudata", "PDF path " + LabuPdfPath + " file " + LabuPdf);
        int noofsong = Integer.valueOf(NoOfSong);
        if ( LabuTextPath == null || LabuTextPath.isEmpty()) return false;
        if (LabuDataPath == null || LabuDataPath.isEmpty()) return false;
        Labu labu = new Labu(Description, Publisher, LabuMin, LabuTextPath, CopyRight, LabuMinTom,
                LabuDataPath, LabuPdfPath,noofsong, LabuIconFile, LabuPdfFile);
       // Log.d(LOG_TAG, "Labu created");
        if (labu.isLabuDataPathValid() == false) return false;
      //  Log.d(LOG_TAG, "Labu data path valid");

        if (labu.isLabuPathValid() == false) return false;
        Log.d(LOG_TAG, "Labu added " + LabuMin);
        _labuList.add(labu);


            return true;
    }

}
