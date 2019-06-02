package com.example.hambabhamsulclient;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ActionBar ab;
    public static FireBaseManager fireBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionbar();

        fireBaseManager = new FireBaseManager();

       /* fireBaseManager.inputUserInfo("pq1031","wodnjs","신재원","jaewonjj","980909",1,eatingCharacter,character);
        fireBaseManager.inputUserInfo("ckatodmlrna","chj159258357","최현지","혀디","980909",1,eatingCharacter,character);
        fireBaseManager.inputMeetingInfo("20190517","먹을 사람 구해요~","ckatodmlrna",1,"경기도","백현군","백현동",1,0,0,100,2,20,member);
        fireBaseManager.inputMyMeetingRoom("ckatodmlrna","20190517",true);
        */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    public void setActionbar() {
        //액션바 만들기
        ab = getSupportActionBar();

        ab.hide();
    }
}