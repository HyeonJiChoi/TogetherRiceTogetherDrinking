package com.example.hambabhamsulclient;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MyMeetingRoom extends AppCompatActivity {
    ActionBar ab;
    Bundle newBundle = new Bundle();
    MyMeetingRoomListFragment myMeetingRoomListFragment;
    Button buttonLeaderO, buttonLeaderX;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_meeting_room);
        setActionbar();

        myMeetingRoomListFragment =  (MyMeetingRoomListFragment) getSupportFragmentManager().findFragmentById(R.id.MyMeetingroomFragment);

        buttonLeaderO = findViewById(R.id.buttonLeaderO);
        buttonLeaderX = findViewById(R.id.buttonLeaderX);

        buttonLeaderO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLeaderO.setTextColor(getResources().getColor(R.color.colorBlack));
                buttonLeaderX.setTextColor(getResources().getColor(R.color.colorGrey));
                newBundle.putInt("check", 0);
                myMeetingRoomListFragment.setArguments(newBundle);
                myMeetingRoomListFragment.onResume();
            }
        });

        buttonLeaderX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLeaderO.setTextColor(getResources().getColor(R.color.colorGrey));
                buttonLeaderX.setTextColor(getResources().getColor(R.color.colorBlack));
                newBundle.putInt("check", 1);
                myMeetingRoomListFragment.setArguments(newBundle);
                myMeetingRoomListFragment.onResume();
            }
        });
    }
    public void setActionbar() {
        //액션바 만들기
        ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.
        ab.setHomeAsUpIndicator(R.drawable.menu_icon);

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_my_meeting_room, null);
        ab.setCustomView(mCustomView);
    }

}