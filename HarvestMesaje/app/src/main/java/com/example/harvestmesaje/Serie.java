package com.example.harvestmesaje;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Serie implements Parcelable {
    private String Titlu;
    private String Data;
    private String Pasaj;
    private String Rezumat;
    private int Image;
    private List<Mesaj> Mesaje;

    public Serie() {
    }

    public Serie(Parcel source) {
        Titlu = source.readString();
        Data = source.readString();
        Pasaj = source.readString();
        Rezumat = source.readString();
        Image = source.readInt();
        Mesaje = new ArrayList<Mesaj>();
        source.readParcelableList(Mesaje, Mesaj.class.getClassLoader());
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
        dest.writeString(Rezumat);
        dest.writeInt(Image);
        dest.writeParcelableList(Mesaje, 0);
    }
    public static final Parcelable.Creator<Serie> CREATOR =
            new Parcelable.Creator<Serie>() {
                @Override
                public Serie createFromParcel(Parcel source) {
                    return new Serie(source);
                }

                @Override
                public Serie[] newArray(int size) {
                    return new Serie[size];
                }
            };

    public List<Mesaj> getMesaje() {
        return Mesaje;
    }

    public void setMesaje(List<Mesaj> mesaje) {
        Mesaje = mesaje;
    }

    public String getTitlu() {
        return Titlu;
    }

    public void setTitlu(String titlu) {
        Titlu = titlu;
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

    public String getRezumat() {
        return Rezumat;
    }

    public void setRezumat(String rezumat) {
        Rezumat = rezumat;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
