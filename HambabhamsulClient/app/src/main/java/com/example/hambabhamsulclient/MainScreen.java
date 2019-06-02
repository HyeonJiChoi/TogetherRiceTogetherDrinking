package com.example.hambabhamsulclient;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class MainScreen extends AppCompatActivity {
    String[] items;
    DrawerLayout drawerLayout;
    View drawerView;
    ViewPager vp;
    ActionBar ab;

    Button buttonMainMakeMeeting, buttonMenuLogout;
    ImageButton buttonMenuPrivarcy, buttonMenuMyMeeting, buttonMenuWant, buttonMenuSet, buttonMenuNotice;
    ImageButton buttonMainWhole, buttonMainHansik,
            buttonMainBunsik, buttonMainCafeDessert, buttonMainDonkkasSushiJapanese, buttonMainChicken, buttonMainPizza, buttonMainChinese,
            buttonMainJokbalBossam, buttonMainSoup, buttonMainLunchbox, buttonMainFastfood;

    Thread thread = null;
    Handler handler = null;
    int p = 0;    //페이지번호
    int v = 1;    //화면 전환 뱡향

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        setActionbar();

        //메뉴 만들기
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerView = (View) findViewById(R.id.drawer);
        buttonMainMakeMeeting = findViewById(R.id.buttonMainMakeMeeting);

        //버튼 설정
        buttonMenuLogout = findViewById(R.id.buttonMenuLogout);
        buttonMenuNotice = findViewById(R.id.buttonMenuNotice);
        buttonMenuWant = findViewById(R.id.buttonMenuWant);
        buttonMenuPrivarcy = findViewById(R.id.buttonMenuPrivarcy);
        buttonMenuMyMeeting = findViewById(R.id.buttonMenuMymeeting);
        buttonMainWhole = findViewById(R.id.buttonMainWhole);
        buttonMainHansik = findViewById(R.id.buttonMainHansik);
        buttonMainBunsik = findViewById(R.id.buttonMainBunsik);
        buttonMainCafeDessert = findViewById(R.id.buttonMainCafeDessert);
        buttonMainDonkkasSushiJapanese = findViewById(R.id.buttonMainDonkkasSushiJapanese);
        buttonMainChicken = findViewById(R.id.buttonMainChicken);
        buttonMainPizza = findViewById(R.id.buttonMainPizza);
        buttonMainChinese = findViewById(R.id.buttonMainChinese);
        buttonMainJokbalBossam = findViewById(R.id.buttonMainJokbalBossam);
        buttonMainLunchbox = findViewById(R.id.buttonMainLunchbox);
        buttonMainSoup = findViewById(R.id.buttonMainSoup);
        buttonMainFastfood = findViewById(R.id.buttonMainFastfood);

        vp = (ViewPager) findViewById(R.id.viewPager);

        items = new String[5];
        for (int i = 0; i < 5; i++) {
            if (MainActivity.fireBaseManager.meeting != null)
                items[i] = MainActivity.fireBaseManager.meeting.keySet().toArray()
                        [MainActivity.fireBaseManager.meeting.keySet().toArray().length - i - 1].toString();

        }

        CustomAdapter adapter = new CustomAdapter(getLayoutInflater(), items, this);
        vp.setAdapter(adapter);


        //2초 지나면 자동으로 스크롤
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                p = vp.getCurrentItem();
                if (p++ == 4)
                    p = 0;
                vp.setCurrentItem(p);
            }
        };

        thread = new Thread() {
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        buttonMenuNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });
        //찜
        buttonMenuWant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WantMeetingRoomActivity.class);
                startActivity(intent);
            }
        });
        //로그아웃, 출처:http://blog.naver.com/PostView.nhn?blogId=rain483&logNo=220812563378&parentCategoryNo=&categoryNo=16&viewDate=&isShowPopularPosts=false&from=postView
        buttonMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        //세팅
        buttonMenuPrivarcy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyInformationActivity.class);
                startActivity(intent);
            }
        });
        buttonMenuMyMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyMeetingRoom.class);
                startActivity(intent);
            }
        });
        buttonMainMakeMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakerConditionSetting.class);
                startActivity(intent);
            }
        });

        //음식메뉴 설정
        buttonMainWhole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 1);
                startActivity(intent);
            }
        });
        buttonMainHansik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 2);
                startActivity(intent);
            }
        });
        buttonMainBunsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 3);
                startActivity(intent);
            }
        });
        buttonMainCafeDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 4);
                startActivity(intent);
            }
        });
        buttonMainDonkkasSushiJapanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 5);
                startActivity(intent);
            }
        });
        buttonMainChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 6);
                startActivity(intent);
            }
        });
        buttonMainPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 7);
                startActivity(intent);
            }
        });
        buttonMainChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 8);
                startActivity(intent);
            }
        });
        buttonMainJokbalBossam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 9);
                startActivity(intent);
            }
        });
        buttonMainLunchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 10);
                startActivity(intent);
            }
        });
        buttonMainSoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 11);
                startActivity(intent);
            }
        });
        buttonMainFastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeekerMain.class);
                intent.putExtra("foodkind", 12);
                startActivity(intent);
            }
        });

        showNitification();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(drawerView);
        }
        return true;
    }

    public void setActionbar() {
        //액션바 만들기
        ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.
        ab.setHomeAsUpIndicator(R.drawable.menu_icon);

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.customer_actionbar_login, null);
        ab.setCustomView(mCustomView);
    }

    public void showNitification() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> notification = new ArrayList<>();
        if (MainActivity.fireBaseManager.myRoom.get(LoginActivity.userId) != null) {
            Iterator<String> iter = MainActivity.fireBaseManager.myRoom.get(LoginActivity.userId).keySet().iterator();
            while (iter.hasNext()) {
                String id_ = iter.next();
                if (Boolean.parseBoolean(MainActivity.fireBaseManager.myRoom.get(LoginActivity.userId).get(id_).toString())) {
                    if (MainActivity.fireBaseManager.meeting.get(id_).get("notification") != null)
                        notification.add(id_);
                }
            }
            for (int i = 0; i < notification.size(); i++) {
                builder.setTitle("신청 확인");
                builder.setMessage(MainActivity.fireBaseManager.meeting.get(notification.get(i)).get("name").toString()
                        + "에서 참가 신청이 들어왔어! 빨리 확인해줘!");
                builder.setNegativeButton("알겠어",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();
            }
        }


    }
}