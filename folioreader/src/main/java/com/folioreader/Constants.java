package com.folioreader;

import android.Manifest;

/**
 * Created by mobisys on 10/4/2016.
 */
public class Constants {
    public static final String PUBLICATION = "PUBLICATION";
    public static final String SELECTED_CHAPTER_POSITION = "selected_chapter_position";
    public static final String TYPE = "type";
    public static final String CHAPTER_SELECTED = "chapter_selected";
    public static final String HIGHLIGHT_SELECTED = "highlight_selected";
    public static final String BOOK_TITLE = "book_title";

    public static final String LOCALHOST = "http://127.0.0.1";
    public static final int DEFAULT_PORT_NUMBER = 8080;
    public static final String STREAMER_URL_TEMPLATE = "%s:%d/%s/";
    public static final String DEFAULT_STREAMER_URL = LOCALHOST + ":" + DEFAULT_PORT_NUMBER + "/";

    public static final String SELECTED_WORD = "selected_word";
    public static final String DICTIONARY_BASE_URL = "https://api.pearson.com/v2/dictionaries/entries?headword=";
    public static final String WIKIPEDIA_API_URL = "https://en.wikipedia.org/w/api.php?action=opensearch&namespace=0&format=json&search=";
    public static final int FONT_ANDADA = 1;
    public static final int FONT_LATO = 2;
    public static final int FONT_LORA = 3;
    public static final int FONT_RALEWAY = 4;
    public static final String DATE_FORMAT = "MMM dd, yyyy | HH:mm";
    public static final String ASSET = "file:///android_asset/";
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST = 102;
    public static final String CHAPTER_ID = "id";
    public static final String HREF = "href";
    public static final String SELECTED_CHAPTER_INDEX = "selected_chapter_index";

    public static String[] getWriteExternalStoragePerms() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

    }

    public static final int SOUND_A = 1;
    public static final int SOUND_B = 2;
    public static final int SOUND_C = 3;
    public static final int SOUND_D = 4;
    public static final int SOUND_E = 5;
    public static final int SOUND_F = 6;
    public static final int SOUND_G = 7;
    //sharp
    public static final int SOUND_AS = 11;
    public static final int SOUND_BS = 12;
    public static final int SOUND_CS = 13;
    public static final int SOUND_DS = 14;
    public static final int SOUND_ES = 15;
    public static final int SOUND_FS = 16;
    public static final int SOUND_GS = 17;
//minor
    public static final int SOUND_Am = 21;
    public static final int SOUND_Bm = 22;
    public static final int SOUND_Cm = 23;
    public static final int SOUND_Dm = 24;
    public static final int SOUND_Em = 25;
    public static final int SOUND_Fm = 26;
    public static final int SOUND_Gm = 27;
    //flat
    public static final int SOUND_Ab = 31;
    public static final int SOUND_Bb = 32;
    public static final int SOUND_Cb = 33;
    public static final int SOUND_Db = 34;
    public static final int SOUND_Eb = 35;
    public static final int SOUND_Fb = 36;
    public static final int SOUND_Gb = 37;


}
