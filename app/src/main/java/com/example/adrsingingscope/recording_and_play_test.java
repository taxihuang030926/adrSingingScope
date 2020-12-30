package com.example.adrsingingscope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.IOException;
import java.util.UUID;

public class recording_and_play_test extends AppCompatActivity {

    private Button rcrd_btn, pl_btn, stp_rcrd_btn, ps_btn;
    String pathSave = "";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    final int REQUEST_PERMISSION_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_and_play_test);

        //Request Runtime Permission
        if (!checkPermissionFromDevice())
            requestPermission();

        //Init view
        pl_btn = (Button)findViewById(R.id.play_btn);
        rcrd_btn = (Button)findViewById(R.id.record_button);
        stp_rcrd_btn = (Button)findViewById(R.id.stop_record_btn);
        ps_btn = (Button)findViewById(R.id.pause_btn);


        //From Android M, need request Run-time permission

            rcrd_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkPermissionFromDevice()) {

                        pathSave = Environment.getExternalStorageDirectory()
                                .getAbsolutePath() + "/"
                                + UUID.randomUUID().toString() + "audio_record.3gp";
                        setupMediaRecorder();
                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        pl_btn.setEnabled(false);
                        ps_btn.setEnabled(false);
                        rcrd_btn.setEnabled(false);
                        stp_rcrd_btn.setEnabled(true);

                        Toast.makeText(recording_and_play_test.this, "Recording...", Toast.LENGTH_SHORT).show();
                    } else {
                        requestPermission();
                    }
                }
            });

            stp_rcrd_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaRecorder.stop();
                    stp_rcrd_btn.setEnabled(false);
                    pl_btn.setEnabled(true);
                    rcrd_btn.setEnabled(true);
                    ps_btn.setEnabled(false);
                }
            });

            pl_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ps_btn.setEnabled(true);
                    stp_rcrd_btn.setEnabled(false);
                    rcrd_btn.setEnabled(false);

                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(pathSave);
                        mediaPlayer.prepare();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    mediaPlayer.start();
                    Toast.makeText(recording_and_play_test.this, "Playing...", Toast.LENGTH_SHORT).show();
                }
            });

            ps_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stp_rcrd_btn.setEnabled(false);
                    rcrd_btn.setEnabled(true);
                    pl_btn.setEnabled(true);
                    ps_btn.setEnabled(false);

                    if (mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        setupMediaRecorder();
                        
                    }
                }
            });

    }

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);
    }

    //Press command + O/ Ctrl + O

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private boolean checkPermissionFromDevice(){
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                record_audio_result == PackageManager.PERMISSION_GRANTED;

    }
}