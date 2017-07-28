package com.example.jhjzzang14.project_01;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jhjzzang14 on 2015-12-03.
 */
public class roomcreate extends Activity {
    BufferedReader br=null;
    PrintWriter pw;
    private DBHelfer mydb;
    int id=0;
    SocketHandler sHandler;
    Socket socket;
    EditText ed1;
    String subject="";
    Button btn ;
    Intent intent;
    String roomname;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);

    //   subject = ed1.getText().toString();
        name = getIntent().getStringExtra("name").toString();
        sHandler = getIntent().getParcelableExtra("sock");
        socket = sHandler.getSocket();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomname  = ed1.getText().toString();
                new roomThread().start();
                intent = new Intent();
                intent.putExtra("sock",sHandler);
                intent.putExtra("id", name);
                startActivity(intent);
                finish();
            }
        });
    }

    class roomThread extends Thread
    {
        public void run() {
            try
            {
                //sHandler =  new SocketHandler();
                //socket = sHandler.getSocket();
                br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));

                pw.println("Room_"+roomname);
                pw.flush();
            }catch(IOException e){}
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
