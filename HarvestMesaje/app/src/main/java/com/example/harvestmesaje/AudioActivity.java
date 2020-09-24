package com.example.harvestmesaje;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AudioActivity extends AppCompatActivity {

    private static final int PICK_PDF_CODE = 1000;

    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private String audio = "Songs/";
    private TextView mTextViewTitlu;
    private TextView mTextViewPasajData;
    private TextView mTextViewIdeeaCentrala;
    private TextView mTextViewPuncte;
    PlayerNotificationManager mPlayerNotificationManager;


    private Button btn_open_pdf;

    private String image;
    private Boolean isPlayingToPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        //Request Read& Write External Storage
        Dexter.withActivity(this)
                .withPermission(READ_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        super.onPermissionGranted(response);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        super.onPermissionDenied(response);
                    }
                }).check();

        Dexter.withActivity(this)
                .withPermission(WRITE_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        super.onPermissionGranted(response);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        super.onPermissionDenied(response);
                    }
                }).check();


//        mPlayerView = findViewById(R.id.video_view);
//        mPlayer = ExoPlayerFactory.newSimpleInstance(this);
//        mPlayerView.setPlayer(mPlayer);

        mTextViewTitlu = (TextView) findViewById(R.id.tv_titlu);
        mTextViewPasajData = (TextView) findViewById(R.id.tv_pasaj_data);
        mTextViewIdeeaCentrala = (TextView) findViewById(R.id.tv_idee_centrala);
        mTextViewPuncte = (TextView) findViewById(R.id.tv_puncte);

        Intent intent = getIntent();
        final Mesaj mesajClicked = intent.getParcelableExtra(MesajeleSeriei.MESAJ_INFO);
        mTextViewTitlu.setText(mesajClicked.getTitlu());
        String pasajData = mesajClicked.getPasaj();
        if(mesajClicked.getData() != null) {
            pasajData +=" | " + mesajClicked.getData();
        }
        mTextViewPasajData.setText(pasajData);
        String ideeaCentrala = "Ideea Centrala: " + mesajClicked.getIdeeaCentrala();
        mTextViewIdeeaCentrala.setText(ideeaCentrala);

        String puncte = "";
        for(int i = 0; i<mesajClicked.getPuncte().size(); i++) {
            puncte += mesajClicked.getPuncte().get(i).getNumar() +
                    ". " + mesajClicked.getPuncte().get(i).getTitlu() + "\n";
        }
        mTextViewPuncte.setText(puncte);

        final String titlul_seriei = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        btn_open_pdf = (Button) findViewById(R.id.btn_open_pdf);
        btn_open_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AudioActivity.this, PdfActivity.class);
                intent.putExtra("ViewType", "internet");
                intent.putExtra(MesajeleSeriei.MESAJ_INFO, mesajClicked);
                intent.putExtra(Intent.EXTRA_SUBJECT, titlul_seriei);
                startActivity(intent);
                isPlayingToPDF = true;
            }
        });


        String titlulSeriei = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        image = titlulSeriei;
        /// TODO 2: imaginea dupa titlul seriei
        /// TODO 3: sa mearga si in background cu notificare sus
        /// TODO 3.2: culorile sa se schimbe in functie de serie
        /// TODO 4: atunci cand bag o predica noua sa apara o notificare
        audio += titlul_seriei + "/" + mesajClicked.getTitlu() + ".mp3";


        //initializePlayer();
    }
    public static PlayerNotificationManager createWithNotificationChannel(
            Context context, String channelId, @StringRes int channelName, int notificationId,
            PlayerNotificationManager.MediaDescriptionAdapter mediaDescriptionAdapter) {

        NotificationUtil.createNotificationChannel(
                context, channelId, channelName, NotificationUtil.IMPORTANCE_LOW);
        return new PlayerNotificationManager(
                context, channelId, notificationId, mediaDescriptionAdapter);

    }




    private void initializePlayer() {

        mPlayerView = findViewById(R.id.video_view);
        mPlayer = ExoPlayerFactory.newSimpleInstance(this);
        mPlayerView.setPlayer(mPlayer);
        StorageReference ref = FirebaseStorage.getInstance()
                .getReference().child(audio);

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                MediaSource mediaSource = buildMediaSource(uri);


                mPlayer.prepare(mediaSource, false, false);
                mPlayer.setPlayWhenReady(playWhenReady);
                mPlayer.seekTo(currentWindow, playbackPosition);
            }
        });
        System.out.println("Playbackposition = " + playbackPosition);
        mPlayer.setPlayWhenReady(playWhenReady);
        mPlayer.seekTo(currentWindow, playbackPosition);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TIME", Integer.toString((int) playbackPosition));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String time = savedInstanceState.getString("TIME");
        playbackPosition = Integer.parseInt(time);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "PlayingAudioFromFirebase");
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    @Override
    public void onStart() {
        System.out.println("STAAAAAAAAAAAART");
        //releasePlayerOnPause();
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        System.out.println("REEEEEEEESUMEEE");
        //releasePlayerOnPause();
        super.onResume();
//        hideSystemUi();
        if ((Util.SDK_INT < 24 || mPlayer == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
//        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        System.out.println("PAUSEEEEEEEEE");
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        System.out.println("STOOOOOOOOP");
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    @Override
    protected void onRestart() {
        System.out.println("REEEEEESTAAART");
        super.onRestart();
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            playWhenReady = mPlayer.getPlayWhenReady();
            playbackPosition = mPlayer.getCurrentPosition();
            System.out.println("AAAAAAAAAAAAAAAA=========" + playbackPosition);
            currentWindow = mPlayer.getCurrentWindowIndex();
            mPlayer.release();
            mPlayer = null;
        }
    }
    private void releasePlayerOnPause() {
        if (mPlayer != null) {
            playWhenReady = mPlayer.getPlayWhenReady();
            playbackPosition = mPlayer.getCurrentPosition();
            System.out.println("AAAAAAAAAAAAAAAA=========" + playbackPosition);
            currentWindow = mPlayer.getCurrentWindowIndex();
        }
    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null) {
//            Uri selectedPDF = data.getData();
//            Intent intent = new Intent(AudioActivity.this, PdfActivity.class);
//            intent.putExtra("ViewType", "storage");
//            intent.putExtra("FileUri", selectedPDF.toString());
//            startActivity(intent);
//        }
//    }

}
