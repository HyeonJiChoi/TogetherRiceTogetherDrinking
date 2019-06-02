package com.example.hambabhamsulclient;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SeekerMain extends AppCompatActivity {
    int sViewX = 0,sViewY= 0;
    Bundle newBundle = null;
    Button buttonSeekersetting;
    TextView textViewSeekerCondition;
    FirstConditionFragment firstConditionFragment;
    SeekerMeetingListFragment seekerMeetingListFragment;
    int foodkind;
    HorizontalScrollView mScrollView;
    Button buttonSeekerWhole, buttonSeekerHansik, buttonSeekerBunsik, buttonSeekerCafeDessert
            , buttonSeekerDonkkasSushiJapanese, buttonSeekerChicken, buttonSeekerPizza, buttonSeekerChinese
            , buttonSeekerJokbalBossam, buttonSeekerSoup, buttonSeekerLunchbox, buttonSeekerFastfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeker_main);

        final int colorBeige = getResources().getColor(R.color.colorBeige); //선택 안 되었을 때 색
        final int colorOrange = getResources().getColor(R.color.colorOrange);   //선택 되었을 때 색

        firstConditionFragment = (FirstConditionFragment) getSupportFragmentManager().findFragmentById(R.id.firstConditionFragment);
        seekerMeetingListFragment = new SeekerMeetingListFragment();

        buttonSeekerWhole = findViewById(R.id.buttonSeekerWhole);
        buttonSeekerHansik = findViewById(R.id.buttonSeekerHansik);
        buttonSeekerBunsik = findViewById(R.id.buttonSeekerBunsik);
        buttonSeekerCafeDessert = findViewById(R.id.buttonSeekerCafeDessert);
        buttonSeekerDonkkasSushiJapanese = findViewById(R.id.buttonSeekerDonkkasSushiJapanese);
        buttonSeekerChicken = findViewById(R.id.buttonSeekerChicken);
        buttonSeekerPizza = findViewById(R.id.buttonSeekerPizza);
        buttonSeekerChinese = findViewById(R.id.buttonSeekerChinese);
        buttonSeekerJokbalBossam = findViewById(R.id.buttonSeekerJokbalBossam);
        buttonSeekerSoup = findViewById(R.id.buttonSeekerSoup);
        buttonSeekerLunchbox = findViewById(R.id.buttonSeekerLunchbox);
        buttonSeekerFastfood = findViewById(R.id.buttonSeekerFastfood);
        textViewSeekerCondition = findViewById(R.id.textViewSeekerCondition);
        textViewSeekerCondition.setVisibility(View.GONE);
        buttonSeekersetting = findViewById(R.id.buttonSeekerSetting);
        buttonSeekersetting.setVisibility(View.GONE);
        mScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollViewSeekerMenu);

        // MainScreen에서 'foodkind'받아옴
        foodkind = getIntent().getExtras().getInt("foodkind");

        //'전체'를 눌렀을 경우, 전체 모임을 보여줌
        //여기서 meetingList조건들 모아서 다 선정 : SeekerMeetingListFragment.java에 있는 meetingRoomList 선정.
        //문제점: onActivityResult <- 여기에 있는 데이터 값을 어떻게 불러오지...?
        buttonSeekerWhole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 1;
                setTitle(foodkind);
                buttonSeekerWhole.setBackgroundColor(colorOrange);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        //'한식'을 눌렀을 경우, '한식' 모임을 보여줌
        buttonSeekerHansik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 2;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(230, 0);
                //이거 움직이는거 main_screen.xml에서 눌렀을 때도 이것도 움직여야되는데 그건 어케하는지 모르겠다...
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorOrange);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerBunsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 3;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(460, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorOrange);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerCafeDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 4;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(690, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorOrange);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerDonkkasSushiJapanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 5;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(920, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorOrange);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 6;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(1170, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorOrange);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 7;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(1400, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorOrange);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 8;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(1630, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorOrange);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerJokbalBossam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 9;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(1860, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorOrange);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerLunchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 10;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(2550, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorOrange);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerSoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 11;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(2320, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorOrange);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorBeige);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekerFastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodkind = 12;
                setTitle(foodkind);
                mScrollView.smoothScrollTo(2780, 0);
                buttonSeekerWhole.setBackgroundColor(colorBeige);
                buttonSeekerHansik.setBackgroundColor(colorBeige);
                buttonSeekerBunsik.setBackgroundColor(colorBeige);
                buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
                buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
                buttonSeekerChicken.setBackgroundColor(colorBeige);
                buttonSeekerPizza.setBackgroundColor(colorBeige);
                buttonSeekerChinese.setBackgroundColor(colorBeige);
                buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
                buttonSeekerSoup.setBackgroundColor(colorBeige);
                buttonSeekerLunchbox.setBackgroundColor(colorBeige);
                buttonSeekerFastfood.setBackgroundColor(colorOrange);
                if (newBundle != null) {
                    newBundle.putInt("foodkind", foodkind);
                    seekerMeetingListFragment.setArguments(newBundle);
                    seekerMeetingListFragment.onResume();
                }
            }
        });
        buttonSeekersetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트를 보냄
                Intent intent = new Intent(getApplicationContext(), ConditionSettingActivity.class);
                intent.putExtra("id", "meetingList");
                startActivityForResult(intent, 1);
            }
        });

        setTitle(foodkind);
        //처음에 들어갈 때, 해당 버튼 누르면 색 바뀌는거... 개노다가 뛰었다
        if (foodkind == 1) {
            buttonSeekerWhole.setBackgroundColor(colorOrange);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);

        } else if (foodkind == 2) {
            sViewX = 230;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorOrange);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 3) {
            sViewX = 460;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorOrange);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 4) {
            sViewX = 690;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorOrange);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 5) {
            sViewX = 920;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorOrange);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 6) {
            sViewX = 1170;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorOrange);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 7) {
            sViewX = 1400;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorOrange);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 8) {
            sViewX = 1630;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorOrange);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 9) {
            sViewX = 1860;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorOrange);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 10) {
            sViewX = 2090;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorOrange);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 11) {
            sViewX = 2320;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorOrange);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorBeige);
        } else if (foodkind == 12) {
            sViewX = 2550;
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(sViewX, sViewY);
                }
            });
            buttonSeekerWhole.setBackgroundColor(colorBeige);
            buttonSeekerHansik.setBackgroundColor(colorBeige);
            buttonSeekerBunsik.setBackgroundColor(colorBeige);
            buttonSeekerCafeDessert.setBackgroundColor(colorBeige);
            buttonSeekerDonkkasSushiJapanese.setBackgroundColor(colorBeige);
            buttonSeekerChicken.setBackgroundColor(colorBeige);
            buttonSeekerPizza.setBackgroundColor(colorBeige);
            buttonSeekerChinese.setBackgroundColor(colorBeige);
            buttonSeekerJokbalBossam.setBackgroundColor(colorBeige);
            buttonSeekerSoup.setBackgroundColor(colorBeige);
            buttonSeekerLunchbox.setBackgroundColor(colorBeige);
            buttonSeekerFastfood.setBackgroundColor(colorOrange);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //ConditionSettingActivity에서 받은 intent(data)를 다시 SeekerMeetingListFragment에 보내줌(conditionIntent). list선정하기 위해.
        //이걸 ArrayList로 return해서 위에서 버튼 누르면 그 해당하는 meetingList를 SeekerMainListFragment로 넘기는 방법?

        //판단 후 저장...
        if (resultCode == 123) { //firstConditionFragment에서 옴
            getSupportFragmentManager().beginTransaction().hide(firstConditionFragment).add(R.id.frameLayoutSeekerMainScreen, seekerMeetingListFragment).commit();
            newBundle = data.getExtras();
            newBundle.putInt("foodkind", foodkind);
            int period = newBundle.getInt("period");
            String date = newBundle.getString(("date"));
            int time = newBundle.getInt("time");
            textViewSeekerCondition.setText(simpleConditionSetting(period, date, time));
            buttonSeekersetting.setVisibility(View.VISIBLE);
            textViewSeekerCondition.setVisibility(View.VISIBLE);
            seekerMeetingListFragment.setArguments(newBundle);
        }
        if (resultCode == 456) { //재설정한 후 옴, SeekerMainListFragment에서 옴
            newBundle = data.getExtras();
            newBundle.putInt("foodkind", foodkind);
            int period = newBundle.getInt("period");
            String date = newBundle.getString(("date"));
            int time = newBundle.getInt("time");
            textViewSeekerCondition.setText(simpleConditionSetting(period, date, time));
            buttonSeekersetting.setVisibility(View.VISIBLE);
            textViewSeekerCondition.setVisibility(View.VISIBLE);
            seekerMeetingListFragment.setArguments(newBundle);
        }

    }

    public String simpleConditionSetting(int period, String date, int time) {
        String periodString = "";
        String timeString = "";

        //Integer로 표시된 기간 String 형태로 바꿔주기
        if (period == 0) {
            periodString = "단기";
        } else if (period == 1) {
            periodString = "중기";
        } else if (period == 2) {
            periodString = "장기";
        }else if(period == 3){
            periodString = "무관";
        }

        if (time == 0) {
            timeString = "아침";
        } else if (time == 1) {
            timeString = "점심";
        } else if (time == 2) {
            timeString = "저녁";
        }else if (time == 3) {
            timeString = "무관";
        }

        String simpleCondition = periodString + ", " + date + ", " + timeString;
        return simpleCondition;
    }
    public void setTitle(int food){
        ReturnValueMethod tmp = new ReturnValueMethod();
        String name = tmp.checkFoodkind(Integer.toString(food));
        //액션바 만들기
        ActionBar ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);      //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.customer_actionbar_seekermain, null);
        TextView textView = mCustomView.findViewById(R.id.textViewCustomerActionbar);
        ImageButton imageButtonBackActionBar = (ImageButton)mCustomView.findViewById(R.id.imageButtonBackActionBar);
        imageButtonBackActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textView.setText(name);
        ab.setCustomView(mCustomView);

    }
}