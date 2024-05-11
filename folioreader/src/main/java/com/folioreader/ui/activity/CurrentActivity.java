package com.folioreader.ui.activity;

import android.app.Activity;

public class CurrentActivity {
    private static final CurrentActivity instance = new CurrentActivity();
    public static CurrentActivity getInstance(){
        return instance;
    }
    public CurrentActivity() {
        currentActivity = null;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public void notACurrentActivity(Activity activity){
        if(currentActivity != null && currentActivity.equals(activity)){
            currentActivity = null;
        }

    }
    private Activity currentActivity;


}
