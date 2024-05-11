package com.paite.project.labu2019;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;


public class LaMin implements Comparable< LaMin> , Parcelable {
    private int _labuResourceid;
    private String _laNo;
    private String _lamin;
    private String _labuMin;

    private int _labuid;
    private int _laid;

    public LaMin(int _labuResourceid, String lano, String _lamin, String _labuMin, int _labuid, int _laid) {
        this._labuResourceid = _labuResourceid;
        this._laNo = lano;
        this._lamin = _lamin;
        this._labuMin = _labuMin;
        this._labuid = _labuid;
        this._laid = _laid;
    }

    public static final Creator<LaMin> CREATOR = new Creator<LaMin>() {
        @Override
        public LaMin createFromParcel(Parcel in) {
            return new LaMin(in);
        }

        @Override
        public LaMin[] newArray(int size) {
            return new LaMin[size];
        }
    };

    public int get_labuResourceid() {
        return _labuResourceid;
    }
    public String get_laNo() { return _laNo;}
    public String get_lamin() {
        return _lamin;
    }

    public String get_labuMin() {
        return _labuMin;
    }

    public int get_labuid() {
        return _labuid;
    }

    public int get_laid() {
        return _laid;
    }

    @Override
    public int compareTo(LaMin o) {
        return this._lamin.compareTo(o._lamin);
    }

    public LaMin (Parcel in) {
        _labuResourceid = in.readInt();
        _laNo = in.readString();
        _lamin = in.readString();
        _labuMin = in.readString();
        _labuid = in.readInt();
        _laid = in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_labuResourceid);
        dest.writeString(_laNo);
        dest.writeString(_lamin);
        dest.writeString(_labuMin);
        dest.writeInt(_labuid);
        dest.writeInt(_laid);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof  LaMin) {
            LaMin lm = (LaMin)obj;
            return this.get_lamin().equalsIgnoreCase(lm.get_lamin()) && this.get_labuMin().equalsIgnoreCase(lm.get_labuMin()) ;
        }
        return false;
       // return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (this.get_lamin()).hashCode();
    }
}
