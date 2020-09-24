package com.example.harvestmesaje;

import android.os.Parcel;
import android.os.Parcelable;

class Punct implements Parcelable {

    private String Numar;
    private String Titlu;

    public Punct() {
    }

    protected Punct(Parcel in) {
        Numar = in.readString();
        Titlu = in.readString();
    }

    public static final Creator<Punct> CREATOR = new Creator<Punct>() {
        @Override
        public Punct createFromParcel(Parcel in) {
            return new Punct(in);
        }

        @Override
        public Punct[] newArray(int size) {
            return new Punct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Numar);
        dest.writeString(Titlu);
    }

    public String getNumar() {
        return Numar;
    }

    public void setNumar(String numar) {
        Numar = numar;
    }

    public String getTitlu() {
        return Titlu;
    }

    public void setTitlu(String titlu) {
        Titlu = titlu;
    }
}
