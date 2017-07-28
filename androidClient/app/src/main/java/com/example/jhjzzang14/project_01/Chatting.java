package com.example.jhjzzang14.project_01;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by jhjzzang14 on 2015-12-05.
 */
public class Chatting extends AppCompatActivity {
    private Activity activiy;
    EditText send;
    String ID;
    Button sendMessage;
    Button Send;
    TextView MainView;
    TextView loginid;
    BufferedReader br=null;
    PrintWriter pw;
    String Msg1="";
    String Msg="";
    String name;
    SocketHandler sHandler;
    static Socket sk;
    private Imagepaint drawView;
    private ImageButton currPaint;
    LinearLayout color;
    TextView bg;
    TextView text;
    ArrayList <Object> al;
    boolean ag =true;
    ScrollView pullscr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);
        color = (LinearLayout) findViewById(R.id.cColor);
        bg = (TextView) findViewById(R.id.tv4);
        text = (TextView) findViewById(R.id.text1);
        pullscr = (ScrollView) findViewById(R.id.LinearLayout1);
        al=new ArrayList<>();
        registerForContextMenu(color);
        registerForContextMenu(bg);
        Send = (Button)findViewById(R.id.button2);
        drawView = (Imagepaint) findViewById(R.id.drawing);
        final LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        Intent intent = getIntent();
        sHandler = intent.getParcelableExtra("sock");
         name = intent.getStringExtra("id").toString();
        sk = sHandler.getSocket();

        send = (EditText) findViewById(R.id.send);
        sendMessage = (Button) findViewById(R.id.sendbtn);
        MainView = (TextView) findViewById(R.id.mainview);
        new chattingThread().start();
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ag==true)
                {
                    al.add(drawView);
                    drawView.invalidate();
                    drawView.setVisibility(View.INVISIBLE);
                    ag=false;
                }else
                {
                    drawView.setVisibility(View.VISIBLE);
                    ag=true;
                }

            }
        });
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pw.println("name_"+Msg);
                //pw.flush();
                if(al.size()!=0)
                {
                    for(int i=0;i<al.size();i++)
                    {
                   al.get(i);
                    }
                }
                Msg = send.getText().toString();
                pw.println(name + " : " + Msg);
                pw.flush();
                send.setText("");
                // MainView.append(Msg1+"\n");
            }
        });
    }

    @Override
    protected void onStop() {
       // pw.println();
      //  pw.flush();
        //loginid.setText("");
        pw.println("quit");
        pw.flush();
        Toast.makeText(this,"종료",Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    class chattingThread extends Thread
    {
        @Override
        public void run() {
            try {
                //sk = new Socket("172.16.42.159", 8000);

                br = new BufferedReader(new InputStreamReader(sk.getInputStream(),"UTF-8"));

                pw = new PrintWriter(new OutputStreamWriter(sk.getOutputStream(),"UTF-8"));

                pw.println("MSG");

                pw.flush();

                String Message="";
                while(true)
                {

                    Message= br.readLine();

                    if(Message==null)break;

                    setString(Message, MainView);
                    //Msg1=Message;
                }


            }catch (Exception e)
            {}
        }
    }
    public void setString(final String msg, final View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) view).append(msg + "\n");
            }
        });
    }
    public void setidInfo(final String msg, final View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) view).append(msg + "\n");
            }
        });
    }
    public void clicked(View view) {
        if(view != currPaint) {
        String color = view.getTag().toString();
        drawView.setColor(color);
        currPaint = (ImageButton) view;
    }
}
    public void eraser(View view) {
        if(view != currPaint) {
            String color = view.getTag().toString();
            drawView.setColor(color);
            currPaint = (ImageButton) view;

        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mInflater = getMenuInflater();
        if(v==color) {
            menu.setHeaderTitle("글자색 변경");
            mInflater.inflate(R.menu.menu_main,menu);
        }
        if(v==bg) {
            menu.setHeaderTitle("배경색 변경");
            mInflater.inflate(R.menu.menu2,menu);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.fred:
                MainView.setTextColor(Color.RED);
                return true;
            case R.id.fblue:
                MainView.setTextColor(Color.BLUE);
                return true;
            case R.id.fgreen:
                MainView.setTextColor(Color.GREEN);
                return true;
            case R.id.bgAir:
                pullscr.setBackground(getResources().getDrawable(R.drawable.airport7));
                return true;
            case R.id.bgApple:
                pullscr.setBackground(getResources().getDrawable(R.drawable.apple));
                return true;
            case R.id.bgMerry:
                pullscr.setBackground(getResources().getDrawable(R.drawable.merry));
                return true;
            case R.id.bgDtouch:
                pullscr.setBackground(getResources().getDrawable(R.drawable.donttouch));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
