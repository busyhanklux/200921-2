package com.example.a200921_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText edtID,edtPW,edtContent;
    private Button btnAppend,btnClear,btnEnd;
    private static final String FILENAME = "login.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtID = findViewById(R.id.edID);
        edtPW = findViewById(R.id.edPW);
        edtContent = findViewById(R.id.edC);

        btnAppend = findViewById(R.id.btnAppend);
        btnClear  = findViewById(R.id.btnClear);
        btnEnd    = findViewById(R.id.btnEnd);

        btnAppend.setOnClickListener(Lis);
        btnClear.setOnClickListener(Lis);
        btnEnd.setOnClickListener(Lis);

        DisplayFile(FILENAME);
    }
    private Button.OnClickListener Lis = new Button.OnClickListener()
    {
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnAppend:
                    if(edtID.getText().toString().equals("")||
                            edtPW.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"請輸入帳密!",Toast.LENGTH_LONG).show();
                        break;
                }
                    FileOutputStream fout = null;
                    BufferedOutputStream buffout = null;
                    try {
                        fout = openFileOutput(FILENAME,MODE_APPEND);
                        buffout = new BufferedOutputStream(fout);

                        buffout.write(edtID.getText().toString().getBytes());
                        buffout.write("\n".getBytes());
                        buffout.write(edtPW.getText().toString().getBytes());
                        buffout.write("\n".getBytes());
                        buffout.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    DisplayFile(FILENAME);
                    edtID.setText("");
                    edtPW.setText("");
                    break;
                case R.id.btnClear:
                    try {
                        fout = openFileOutput(FILENAME,MODE_PRIVATE);
                        fout.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    DisplayFile(FILENAME);
                    break;
                case R.id.btnEnd:
                    finish();
            }
        }
    };

    private void DisplayFile(String fname)
    {
        FileInputStream fin = null;
        BufferedInputStream buffin = null;
        try {
            fin = openFileInput(fname);
            buffin = new BufferedInputStream(fin);
            byte[] buffbyte = new byte[20];
            edtContent.setText("");
            do {
                int flag = buffin.read(buffbyte);
                if (flag == -1) break;
                else
                    edtContent.append(new String(buffbyte), 0, flag);
            } while (true);
            buffin.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}