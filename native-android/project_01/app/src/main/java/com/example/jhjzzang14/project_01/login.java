package com.example.jhjzzang14.project_01;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jhjzzang14 on 2015-12-03.
 */
public class login extends Activity {
    String NickName ="";
    ArrayList<LoginInfo> al=null;
    EditText id1 ;
    EditText pw1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    class loginserver extends Thread
    {
        Socket sk=null;
        BufferedReader br=null ;
        PrintWriter pw=null;
        public void run()
        {
            try {
                id1 = (EditText)findViewById(R.id.id);
                pw1 =(EditText) findViewById(R.id.pw);
                al= new ArrayList<LoginInfo>();
                al.add(new LoginInfo(id1.getText().toString(),pw1.getText().toString()));
                String id="";
                String pw="";
                for (int i = 0; i < al.size(); i++) {
                   id =  al.get(i).getId().toString();
                    pw =  al.get(i).getPw().toString();
                    Intent intent = new Intent(getApplication(),MainActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("pw",pw );
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){}
        }
    }
    public void insert(View view) {
        new loginserver().start();

    }

    class LoginInfo
    {
        private String id;
        private String pw;
        LoginInfo(String id,String pw)
        {
            this.id=id;
            this.pw=pw;
        }

        public String getId() {
            return id;
        }

        public String getPw() {
            return pw;
        }

        public void setPw(String pw) {
            this.pw = pw;
        }

        public void setId(String id) {
            this.id = id;

        }
    }

}
