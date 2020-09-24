package com.example.harvestmesaje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SerieAdapter.SerieAdapterOnClickHandler{

    private static final String TAG = "MainActivity";
    public static final String SERIE_INFO = "SERIE_INFO";
    // imaginile invers
    private int[] images = {R.drawable.dansul_conjugal, R.drawable.in_oras, R.drawable.elefantul_din_camera, R.drawable.ridicate_si_dute, R.drawable.in_ordine, R.drawable.vertical, R.drawable.cele_7_pacate_cardinale, R.drawable.credinta_muta_muntii, R.drawable.o_comunitate_neobisnuita, R.drawable.vorbitor_invitat, R.drawable.in_constructie_2, R.drawable.sa_fim_lumina, R.drawable.in_constructie};

    private RecyclerView mRecyclerView;
    private SerieAdapter mSerieAdapter;

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRef = FirebaseDatabase.getInstance().getReference().child("Serie");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_serii);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mSerieAdapter = new SerieAdapter(this, images);
        mRecyclerView.setAdapter(mSerieAdapter);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Serie> serieData = new ArrayList<>();
                //i-ul invers
                for(long i = dataSnapshot.getChildrenCount(); i >= 1; i-- ) {
                    DataSnapshot dataSnapshot1 = dataSnapshot.child(Integer.toString((int)i));
                    Serie serieDetails = dataSnapshot1.getValue(Serie.class);

                    if(serieDetails != null ) {

                        serieData.add(serieDetails);

                    } else {
                        System.out.println("NULL");
                    }
                    mSerieAdapter.setSerieData(serieData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //downloadImages();

    }

    @Override
    public void onClick(Serie item) {
        Context context = this;
        Toast.makeText(context, item.getTitlu(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, MesajeleSeriei.class);
        intent.putExtra(MainActivity.SERIE_INFO, item);

        startActivity(intent);
    }
}
