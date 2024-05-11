package com.folioreader.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import com.folioreader.Constants;
import com.folioreader.R;

public class NotePlayer {
    private SoundPool mSoundPool;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = NotePlayer.class.getSimpleName();
   /* private float LEFT_VOL = 1.0f;
    private float RIGHT_VOL = 1.0f;
    private int PRIORITY = 1;
    private int LOOP = 0;
    private float RATE = 1.0f;


    private int a;
    private int ahash;
    private int b;
    private int c;
    private int chash;
    private int d;
    private int dhash;
    private int e;
    private int f;
    private int fhash;
    private int g;
    private int ghash;
    private boolean prepare = false;
    @SuppressWarnings("deprecation")
    private void createSoundPoolOld() {
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createSoundPoolNew() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }*/
    public NotePlayer(Context context , int  keyid ) {
        play(context, keyid);
    }
    private boolean createmediaplayer(Context context , int keyid) {
        int id = 0;

        switch(keyid) {
            case Constants.SOUND_A:
                id = R.raw.a4;
                Log.d(LOG_TAG, " play a");
                break;
            case Constants.SOUND_Ab:
                id = R.raw.ab4;
                Log.d(LOG_TAG, " play ab");

                break;
            case Constants.SOUND_B:
                id = R.raw.b4;
                Log.d(LOG_TAG, " play b");

                break;
            case Constants.SOUND_Bb:
                id = R.raw.bb4;
                Log.d(LOG_TAG, " play bb");

                break;
            case Constants.SOUND_C:
                id = R.raw.c4;
                Log.d(LOG_TAG, " play c");

                break;
            case Constants.SOUND_D:
                id = R.raw.d4;
                Log.d(LOG_TAG, " play d");

                break;
            case Constants.SOUND_Db:
                id = R.raw.db4;
                Log.d(LOG_TAG, " play db");

                break;
            case Constants.SOUND_E:
                id = R.raw.e4;
                Log.d(LOG_TAG, " play e");

                break;
            case Constants.SOUND_Eb:
                id = R.raw.eb4;
                Log.d(LOG_TAG, " play eb");

                break;
            case Constants.SOUND_F:
                id = R.raw.f4;
                Log.d(LOG_TAG, " play f");

                break;
            case Constants.SOUND_G:
                id = R.raw.g4;
                Log.d(LOG_TAG, " play g");

                break;
            case Constants.SOUND_Gb:
                id = R.raw.gb4;
                Log.d(LOG_TAG, " play gb");

                break;

                default: id = 0;
                break;

        }
        if (id != 0) {
            mPlayer = MediaPlayer.create(context, id);
            return true;
        }
        return false;

    }
    public void play(Context context, int keyid) {
        if ( mPlayer == null) {
            Log.d(LOG_TAG, "create mediaplayer");
            createmediaplayer(context, keyid);
            if (mPlayer != null) {
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.d(LOG_TAG, "complete");
                        stopPlayer();
                    }
                });
                Log.d(LOG_TAG, "start");

                mPlayer.start();
            }
        }

    }
    public void stopPlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            Log.d(LOG_TAG, "stop");

        }
    }
       /*/ if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createSoundPoolNew();
        } else {
            createSoundPoolOld();
        }
        Log.d("soundpool", "create soundpool");
        a =  mSoundPool.load(context,R.raw.a,1);
        b =  mSoundPool.load(context,R.raw.a4,1);
        c =  mSoundPool.load(context,R.raw.c,1);
        d =  mSoundPool.load(context,R.raw.d,1);
        e =  mSoundPool.load(context,R.raw.e,1);
        f =  mSoundPool.load(context,R.raw.f,1);
        g =  mSoundPool.load(context,R.raw.g,1);

        mPlayer = MediaPlayer.create(context, R.raw.a);

        mSoundPool.setOnLoadCompleteListener(new  SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                Log.d("soundpool","loading complete" + sampleId);
            }
        });
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                prepare = true;
                Log.d("soundpool media", "preparation done");
                //mp.start();
            }
        });

    }

    public void play(int soundid) {
        Log.d("Soundpool", "playing sound for " + prepare);
        mPlayer.start();
       /* switch (soundid) {
            case Constants.SOUND_A:
                Log.d("Soundpool", "playing a");
                mSoundPool.play(a, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
                    break;
            case Constants.SOUND_B:
                Log.d("Soundpool", "playing b");
                mSoundPool.play(b, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
                break;

        }*/
   /* }*/
}
