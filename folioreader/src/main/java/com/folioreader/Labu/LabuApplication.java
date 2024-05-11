package com.folioreader.Labu;


import android.app.Application;
import android.util.Log;

public class LabuApplication {
    private static Application _labuApplication;
    public static void setApplication( Application  application) {
        _labuApplication = application;
    }
    public static Application getApplication() {
        return _labuApplication;
    }
}
