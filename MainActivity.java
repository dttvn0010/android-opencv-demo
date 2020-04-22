package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    final static int CAMERA_REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final File imagePath = new File(getFilesDir(), "images");
        imagePath.mkdir();

        final Context context = this;
        Button capture = (Button) findViewById(R.id.btnCapture);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Uri outputFileUri = Uri.fromFile(newfile);
                File imageFile = new File(imagePath.getPath(), "test.jpg");

                Uri outputFileUri = FileProvider.getUriForFile(context, getPackageName() + ".provider", imageFile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent authActivityResult){
        super.onActivityResult(requestCode, resultCode, authActivityResult);

        if(requestCode == CAMERA_REQUEST_CODE) {
            File imgFile = new File(getFilesDir(), "images/test.jpg");

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = (ImageView) findViewById(R.id.imgView1);

                myImage.setImageBitmap(myBitmap);

                Mat img = Imgcodecs.imread(imgFile.getAbsolutePath());

                TextView textView = (TextView) findViewById(R.id.textView1);
                textView.setText(String.format("Width: %s, height: %s", img.width(), img.height()));
            }
        }
    }

}
