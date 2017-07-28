package com.example.jhjzzang14.project_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity {
    Button btn;
    Button login2;
    Button bton;
    TextView tv;
    ArrayList<String> alist;
    private ListView myListView;
    ArrayAdapter mAdapter;
    private Socket socket;
    BufferedReader br=null;
    PrintWriter pw;
    SocketHandler sHandler;
    private ListView                m_ListView=null;
    private ArrayAdapter<String>    m_Adapter=null;
    String ID ;
    String Pw;
    int count =0;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.login);
        btn = (Button)findViewById(R.id.button);
        alist = new ArrayList<String>();
        ID = getIntent().getStringExtra("id").toString();
        Pw = getIntent().getStringExtra("pw").toString();
        tv.setText(ID + "님 환영합니다");
        new MainActiviting().start();
        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //방생성
                    if(count<1) {
                        pw.println("roomcreate_" +ID);
                        pw.flush();
                        count++;
                    }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class MainActiviting extends Thread
    {

        public void run() {
            try
            {
                sHandler =  new SocketHandler();
                socket = sHandler.getSocket();
                br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));

                pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));

                pw.println(ID);
                pw.flush();

                pw.println("roomInfo" );
                pw.flush();

                String msg="";
                while(true)
                {
                    msg = br.readLine();

                    if(msg.equals("MSG"))
                    {
                        break;
                    }
                    if(msg.contains("roomnumber_"))
                    {
                        String a[] = msg.split("_");

                        String b[]= a[1].split(",");

                        for(int i=0;i<b.length;i++)
                        {
                            alist.add(b[i]);
                        }

                        runOnUiThread(new Runnable() {
                            public void run() {
                                m_Adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, alist);

                                // Xml에서 추가한 ListView 연결
                                m_ListView = (ListView) findViewById(R.id.listview1);

                                // ListView에 어댑터 연결
                                m_ListView.setAdapter(m_Adapter);

                                m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        intent = new Intent(getApplication(), Chatting.class);
                                        intent.putExtra("sock", sHandler);
                                        intent.putExtra("id", ID);
                                        pw.println("RoomIn_" + position + "_" + ID);
                                        pw.flush();
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                    if(msg.contains("user")) {
                        String a[] = msg.split("_");
                        String b = a[1];

                        alist.add(b);

                        //방생성
                        runOnUiThread(new Runnable() {
                            public void run() {
                                m_Adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, alist);

                                // Xml에서 추가한 ListView 연결
                                m_ListView = (ListView) findViewById(R.id.listview1);

                                // ListView에 어댑터 연결
                                m_ListView.setAdapter(m_Adapter);

                                m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        intent = new Intent(getApplication(), Chatting.class);
                                        intent.putExtra("sock", sHandler);
                                        intent.putExtra("id", ID);
                                        pw.println("RoomIn," + ID);
                                        pw.flush();
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                }
            }catch(IOException e){}
        }
    }
    public Socket getSocket()
    {
        return socket;
    }
    public void setSocket(Socket socket)
    {
        this.socket=socket;
    }

}
