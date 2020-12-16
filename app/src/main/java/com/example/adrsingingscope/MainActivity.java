package com.example.adrsingingscope;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    private EditText Et1,Et2;
    private Button Btn, Bt4next_page;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Et1 = (EditText)findViewById(R.id.et1);
        Et2 = (EditText)findViewById(R.id.et2);
        Btn = (Button)findViewById(R.id.btn);
        Bt4next_page = (Button)findViewById(R.id.bt4next_page);
        tv = (TextView)findViewById(R.id.text_view);
/*
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("script");

        Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                PyObject obj = pyobj.callAttr( "main", Et1.getText().toString(), Et2.getText().toString());

                //obj will contain result, so set its result to textview

                tv.setText(obj.toString());

                //code complete
            }
        }); */

        Bt4next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecordTestPage();
            }
        });

    }
    public void openRecordTestPage(){
        Intent intent = new Intent(this, recording_and_play_test.class);
        startActivity(intent);
    }
}