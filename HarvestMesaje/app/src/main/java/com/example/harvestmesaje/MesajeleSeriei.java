package com.example.harvestmesaje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MesajeleSeriei extends AppCompatActivity implements MesajAdapter.MesajAdapterOnClickHandler {

    private TextView mTextViewSerieTitlu;
    private TextView mTextViewSerieRezumat;

    public static final String MESAJ_INFO = "MESAJ_INFO";
    private RecyclerView mRecyclerView;
    private MesajAdapter mMesajAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajele_seriei);

        mTextViewSerieTitlu = (TextView) findViewById(R.id.tv_serie_titlu);
        mTextViewSerieRezumat = (TextView) findViewById(R.id.tv_rezumat);

        Intent intent = getIntent();
        Serie serieClicked = intent.getParcelableExtra(MainActivity.SERIE_INFO);
        mTextViewSerieTitlu.setText(serieClicked.getTitlu());
        mTextViewSerieRezumat.setText(serieClicked.getRezumat());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_mesaje);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMesajAdapter = new MesajAdapter(this);
        mRecyclerView.setAdapter(mMesajAdapter);

        List<Mesaj> mesaje = new ArrayList<>();
        for(int i = serieClicked.getMesaje().size() - 1; i >=0; i--) {
            mesaje.add(serieClicked.getMesaje().get(i));
        }
//        for(Mesaj mesaj : serieClicked.getMesaje()) {
//            mesaje.add(mesaj);
//        }
        mMesajAdapter.setMesajData(mesaje);
    }

    @Override
    public void onClick(Mesaj mesaj) {
        Context context = this;
        Toast.makeText(context, mesaj.getTitlu(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, AudioActivity.class);
        intent.putExtra(MesajeleSeriei.MESAJ_INFO, mesaj);
        intent.putExtra(Intent.EXTRA_SUBJECT, mTextViewSerieTitlu.getText());

        startActivity(intent);
    }
}
