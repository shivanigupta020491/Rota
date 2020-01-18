package com.shivani.rota;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class MyRotaDay extends EventDay implements Parcelable {

    private String shift;

    public MyRotaDay(Calendar day, String shift) {
        super(day);
        this.shift = shift;
    }

//    public MyRotaDay(Calendar day, int imageResource) {
//        super(day, imageResource);
//    }

    private MyRotaDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        shift = in.readString();
    }

    public static final Creator<MyRotaDay> CREATOR = new Creator<MyRotaDay>() {
        @Override
        public MyRotaDay createFromParcel(Parcel in) {
            return new MyRotaDay(in);
        }

        @Override
        public MyRotaDay[] newArray(int size) {
            return new MyRotaDay[size];
        }
    };

    String getShift(){
        return shift;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(getImageResource());
        parcel.writeString(shift);
    }
}
