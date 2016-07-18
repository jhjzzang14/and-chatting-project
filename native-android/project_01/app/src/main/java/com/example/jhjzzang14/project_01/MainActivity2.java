package com.example.jhjzzang14.project_01;


import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2 extends Activity{

    private Imagepaint drawView;
    private ImageButton currPaint;
    LinearLayout color;
    TextView bg;
    TextView text;
    LinearLayout pullscr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);

        color = (LinearLayout) findViewById(R.id.cColor);
        bg = (TextView) findViewById(R.id.tv4);
        text = (TextView) findViewById(R.id.text1);
        pullscr = (LinearLayout) findViewById(R.id.LinearLayout1);
        registerForContextMenu(color);
        registerForContextMenu(bg);

        drawView = (Imagepaint) findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
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
                text.setTextColor(Color.RED);
                return true;
            case R.id.fblue:
                text.setTextColor(Color.BLUE);
                return true;
            case R.id.fgreen:
                text.setTextColor(Color.GREEN);
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
