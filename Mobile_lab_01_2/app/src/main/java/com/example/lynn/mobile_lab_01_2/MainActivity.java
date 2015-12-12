package com.example.lynn.mobile_lab_01_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "oncreate1");
        setContentView(R.layout.activity_main);
        Button bt = (Button)this.findViewById(R.id.btnNew);


/*
        //익명 inner class 임시객체
        //1
        bt.setOnClickListener(new View.OnClickListener(){
                                  @Override

                                  public void onClick(View v) {
                                      Toast.makeText(MainActivity.this.getApplicationContext(), "Click Button", Toast.LENGTH_LONG).show();
                                  }

                              }
        );
*/



        // 익명 클래스
        //2
         bt.setOnClickListener(btnlistener2);


/*
            //3 리스너 구현
            ButtonClickListener btnlistener = new ButtonClickListener();
                bt.setOnClickListener(btnlistener);
*/

    }
    //2 익명 클래스
    View.OnClickListener btnlistener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this.getApplicationContext(), "Click Button", Toast.LENGTH_LONG).show();
        }
    };

/*
    //3 리스너 구현
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this.getApplicationContext(), "Click Button", Toast.LENGTH_LONG).show();
        }
    }
*/

    public void onNewBtnClicked(View v) {
        Toast.makeText(getApplicationContext(), "click button2.", Toast.LENGTH_LONG).show();
    }

    public void onTestBtnClicked(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}