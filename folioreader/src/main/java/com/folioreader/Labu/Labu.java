package com.folioreader.Labu;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public class Labu {
    private String _description;
    private String _Publisher;
    private String _LabuMin;
    private String _LabuTextPath;
    private String _CopyRight;
    private String _LabuMinTom;
    private String _LabuDataPath;
    private String _LabuPdfPath;
    private Integer _NoOfSong;
    private String _labuIcon;
    private String _pdfFile;

    public int get_iconID() {
        return _iconID;
    }

    public void set_iconID(int _iconID) {
        this._iconID = _iconID;
    }

    private int _iconID;
    private static final String ns = null;
    private ArrayList<String> _titles;
    private ArrayList<String> _sortedtitles;
    private ArrayList<Integer> _keysval;
    private ArrayList<String> _songId;
    private ArrayList<Integer> _songPdfPageNo;


    public Labu(String _description, String _Publisher, String _LabuMin, String _LabuTextPath, String _CopyRight, String _LabuMinTom, String _LabuDataPath, String LabuPdfPath,
                Integer noofsong, String labuIcon, String pdffile) {
        this._description = _description;
        this._Publisher = _Publisher;
        this._LabuMin = _LabuMin;
        this._LabuTextPath = _LabuTextPath;
        this._CopyRight = _CopyRight;
        this._LabuMinTom = _LabuMinTom;
        this._LabuDataPath = _LabuDataPath;
        this._LabuPdfPath = LabuPdfPath;
        this._NoOfSong = noofsong;
        this._labuIcon = labuIcon;
        this._pdfFile = pdffile;
        _iconID = -1;
        _titles = new ArrayList<String>();
        _sortedtitles = new ArrayList<String>();
        _keysval  = new ArrayList<Integer>();
        _songId = new ArrayList<String>();
        _songPdfPageNo = new ArrayList<Integer>();



    }

    public int get_noofSong() {
        return _NoOfSong;
    }
    public String get_laNoNMin(int id ){
        return _songId.get(id) + " " + _titles.get(id);

    }
    public  String get_laMin(int id) {
        return _titles.get(id);
    }
    public String get_laNo(int id) {
        return _songId.get(id);
    }
    public ArrayList<String> get_sortedTitle() {

        return _sortedtitles;
    }
    public Integer get_songKeyVal(int id) {
        return _keysval.get(id);
    }
    public Integer get_songPdfPage(int id) {
        return _songPdfPageNo.get(id);
    }
    public String get_LabuPdfPath(){
        return _LabuPdfPath;
    }
    public String get_LabuPdfFile() { return _pdfFile;}
 //   public String get_labuIcon() { return _labuIcon;}
    public String get_description() {
        return _description;
    }

    public String get_Publisher() {
        return _Publisher;
    }

    public String get_LabuMin() {

        return _LabuMin;
    }



    public String get_CopyRight() {
        return _CopyRight;
    }

    public String get_LabuMinTom() {
        return _LabuMinTom;
    }
    public String get_LabuTextFullPathInAsset() {
        String filepath = _LabuTextPath;
        return filepath;
    }
    public String get_LabuTextDataPathInAsset () {
        String filepath = _LabuDataPath;
        return filepath;

    }
    private boolean isFileExist(String filepath) {
       // Log.d("Labu:", "check if file exist " + filepath);
        AssetManager assetManager = LabuApplication.getApplication().getAssets();

        try {
            InputStream inputStream = null;

            inputStream = assetManager.open(filepath);
            if (inputStream != null) {

                inputStream.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }
    public boolean isLabuPathValid() {
        String filepath = get_LabuTextFullPathInAsset();
        return isFileExist(filepath);

    }
    public boolean isLabuDataPathValid(){
        String filepath = get_LabuTextDataPathInAsset();
        boolean ret =  isFileExist(filepath);
        if (ret == false) return ret;
        return populatLabuData();

    }
    public Bitmap getIconBitmap() {
      //  _labuIcon;
        AssetManager assetManager = LabuApplication.getApplication().getAssets();
        InputStream open = null;
        Bitmap bitmap = null;
        try {
            open = assetManager.open(_labuIcon);
            if (open != null) {
                bitmap = BitmapFactory.decodeStream(open);
                open.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }
    private boolean populatLabuData() {

        String filepath = get_LabuTextDataPathInAsset();
        AssetManager assetManager = LabuApplication.getApplication().getAssets();

        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(filepath);
            if (inputStream != null) {
               /* byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                String str = new String(bytes);*/
                paseData(inputStream);
                inputStream.close();
                Collections.sort(_sortedtitles);
                // Log.d("Labu:xml ", str );

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (_NoOfSong != _titles.size()) {
            Log.e("Labu Error", "Size of la in property and xml not equal");
            return false;
        }
        return true;
    }

    private void paseData(InputStream in) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
          //  Log.d("Labu: xml", parser.getName());
            parser.require(XmlPullParser.START_TAG, ns, "songbook");

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("song")) {
             //       Log.d("Labu inside: ", name);
                    readSong(parser);

                } else if (name.equals("noofsong")) {
                    parser.require(XmlPullParser.START_TAG, ns, "noofsong");
                    String noofsong= readText(parser);

                    parser.require(XmlPullParser.END_TAG, ns, "noofsong");
                    int no = Integer.valueOf(noofsong);
                    Log.d("numberofsong ", no + " and store " + get_noofSong());

                    if (no != get_noofSong()) {
                        Log.d("numberofsong not equal", no + " != " + get_noofSong());
                        assert 0 == 0;
                    }
                }
                else
                 {
                   Log.d("Labu skip: ", name);

                    skip(parser);
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }
    private String readKey(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "key");
        String key = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "key");
        return key;
    }
    private String readKeyVal(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "keyval");
        String keyval= readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "keyval");
        return keyval;
    }
    private String readId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "id");
        String id = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "id");
        return id;
    }

    private String readPdfId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pdfid");
        String id = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pdfid");
        return id;
    }
    private void readSong(XmlPullParser parser) throws XmlPullParserException, IOException {
        String title = null;
        String key = null;
        String keyval = null;
        String id = null;
        String pdfid = null;
        parser.require(XmlPullParser.START_TAG, ns, "song");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            //Log.d("Labu song ", name);

            if (name.equals("title")) {
                 title =  readTitle(parser);
            }else if (name.equals("key")) {
                key =  readKey(parser);
            }else if (name.equals("keyval")) {
                keyval =  readKeyVal(parser);
            }else if (name.equals("id")) {
                id = readId(parser);
            }else if (name.equals("pdfid")) {
                pdfid = readPdfId(parser);
            }else {
                skip(parser);
            }


        }
        title = title.trim();
        _titles.add(title);
        _sortedtitles.add(title);
        int result = 0;
        if (keyval != null) {
            result = Integer.valueOf(keyval);
        }
        _keysval.add(result);
        int pdf = 0;
        if (pdfid != null && pdfid.isEmpty() == false) {
            pdf = Integer.valueOf(pdfid);
        }
        _songId.add(id);
        _songPdfPageNo.add(pdf);
      //  Log.d("Labu la", id + " " + title + " " + result + " (" + keyval +")" );

    }
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
