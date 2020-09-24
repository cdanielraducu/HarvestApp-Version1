package com.example.harvestmesaje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PdfActivity extends AppCompatActivity {

    PDFView mPDFView;
    ProgressBar mProgressBar;
    String pdf = "Pdfs/";
    TextView TVonFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        mPDFView = (PDFView) findViewById(R.id.pdf_viewer);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        TVonFailure = (TextView) findViewById(R.id.textViewOnFailure);
        Intent intent = getIntent();
        Mesaj mesajClicked = intent.getParcelableExtra(MesajeleSeriei.MESAJ_INFO);

        System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(mesajClicked.getTitlu());

        String titlul_seriei = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        System.out.println(titlul_seriei);
        pdf += titlul_seriei + "/" + mesajClicked.getTitlu() + ".pdf";
        TVonFailure.setText(R.string.pdfNOT);


        if(getIntent() != null) {
            String viewType = getIntent().getStringExtra("ViewType");
            if(viewType != null || !TextUtils.isEmpty(viewType)) {
                if (viewType.equals("internet")) {
                    mProgressBar.setVisibility(View.VISIBLE);

                    StorageReference ref = FirebaseStorage.getInstance()
                            .getReference().child(pdf);


                    System.out.println("AAAAAAAAAAAA");
                    System.out.println(ref.toString());

                        ref.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
                                System.out.println(pdf);
                                Toast.makeText(PdfActivity.this, "Acest mesaj nu are intrebari!", Toast.LENGTH_LONG).show();
                                mProgressBar.setVisibility(View.INVISIBLE);
                                mPDFView.setVisibility(View.INVISIBLE);
                                System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZ");
                                System.out.println(TVonFailure.getText());
                                TVonFailure.setVisibility(View.VISIBLE);

                            }
                        });

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                FileLoader.with(PdfActivity.this)
//                            .load("http://www.africau.edu/images/default/sample.pdf") // the url u want to load
                                        .load(uri.toString())
                                        .fromDirectory("PDFFiles", FileLoader.DIR_INTERNAL) // trebuie neaparat DIR_INTERNAL
                                        .asFile(new FileRequestListener<File>() {
                                            @Override
                                            public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                                mProgressBar.setVisibility(View.GONE);
                                                File pdfFile = response.getBody();

                                                mPDFView.fromFile(pdfFile)
                                                        .password(null) // if have pass
                                                        .defaultPage(0) // open default page, you can remember this value to open from last time
                                                        .enableSwipe(true)
                                                        .swipeHorizontal(false)
                                                        .enableDoubletap(true) // Double tap to zoom
                                                        .onDraw(new OnDrawListener() {
                                                            @Override
                                                            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                                                // code here if you ant to do something
                                                            }
                                                        })
                                                        .onDrawAll(new OnDrawListener() {
                                                            @Override
                                                            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                                                // code here if you ant to do something
                                                            }
                                                        })
                                                        .onPageError(new OnPageErrorListener() {
                                                            @Override
                                                            public void onPageError(int page, Throwable t) {
                                                                Toast.makeText(PdfActivity.this, "Error while open page " + page, Toast.LENGTH_LONG).show();
                                                            }
                                                        })
                                                        .onPageChange(new OnPageChangeListener() {
                                                            @Override
                                                            public void onPageChanged(int page, int pageCount) {
                                                                // code here if you ant to do something
                                                            }
                                                        })
                                                        .onTap(new OnTapListener() {
                                                            @Override
                                                            public boolean onTap(MotionEvent e) {
                                                                return true;
                                                            }
                                                        })
                                                        .onRender(new OnRenderListener() {
                                                            @Override
                                                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                                                mPDFView.fitToWidth(); // fixed screen size
                                                            }
                                                        })
                                                        .enableAnnotationRendering(true)
                                                        .invalidPageColor(Color.WHITE)
                                                        .load();
                                            }

                                            @Override
                                            public void onError(FileLoadRequest request, Throwable t) {
                                                Toast.makeText(PdfActivity.this, "Acest mesaj nu are intrebari!", Toast.LENGTH_SHORT).show();
                                                mProgressBar.setVisibility(View.GONE);
                                                TVonFailure.setText(R.string.pdfNOT);
                                                TVonFailure.setVisibility(View.VISIBLE);
                                            }
                                        });
                            }
                        });
                    }
                }
        }
    }
}
