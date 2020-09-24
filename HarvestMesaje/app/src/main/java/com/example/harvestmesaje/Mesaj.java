package com.example.harvestmesaje;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

class Mesaj implements Parcelable {

    private String Titlu;
    private String Data;
    private String Pasaj;
    private String IdeeaCentrala;
    private List<Punct> Puncte;

    public Mesaj() {
    }

    protected Mesaj(Parcel source) {
        Titlu = source.readString();
        Data = source.readString();
        Pasaj = source.readString();
        IdeeaCentrala = source.readString();
        Puncte = new ArrayList<>();
        source.readParcelableList(Puncte, Punct.class.getClassLoader());
    }

    public static final Creator<Mesaj> CREATOR = new Creator<Mesaj>() {
        @Override
        public Mesaj createFromParcel(Parcel source) {
            return new Mesaj(source);
        }

        @Override
        public Mesaj[] newArray(int size) {
            return new Mesaj[size];
        }
    };

    public String getIdeeaCentrala() {
        return IdeeaCentrala;
    }

    public void setIdeeaCentrala(String ideeaCentrala) {
        IdeeaCentrala = ideeaCentrala;
    }

    public List<Punct> getPuncte() {
        return Puncte;
    }

    public void setPuncte(List<Punct> puncte) {
        Puncte = puncte;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getPasaj() {
        return Pasaj;
    }

    public void setPasaj(String pasaj) {
        Pasaj = pasaj;
    }

    public String getTitlu() {
        return Titlu;
    }

    public void setTitlu(String titlu) {
        Titlu = titlu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Titlu);
        dest.writeString(Data);
        dest.writeString(Pasaj);
        dest.writeString(IdeeaCentrala);
        dest.writeParcelableList(Puncte, 0 );
    }
}
