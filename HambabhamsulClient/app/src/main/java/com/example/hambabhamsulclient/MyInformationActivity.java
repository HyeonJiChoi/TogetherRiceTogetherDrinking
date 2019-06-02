package com.example.hambabhamsulclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class MyInformationActivity extends AppCompatActivity {
    ActionBar ab;
    StorageReference riversRef;
    TextView textViewMyId, textViewMyName, textViewMyBirthday, textViewMyGender, textViewMyNickname, textViewMyEatingAmount, textViewMyEatingSpeed, textViewMyAnomity;
    ImageView imageViewMyProfile, imageViewChar0, imageViewChar1, imageViewChar2, imageViewChar3, imageViewChar4;
    HashMap<String, HashMap<String, Object>> user;
    String userId = LoginActivity.userId;
    ReturnValueMethod rvm = new ReturnValueMethod();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information);

        setActionbar();

        riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com");

        user = MainActivity.fireBaseManager.user;
        textViewMyId = findViewById(R.id.textViewMyId);
        textViewMyName = findViewById(R.id.textViewMyName);
        textViewMyBirthday = findViewById(R.id.textViewMyBirthday);
        textViewMyGender = findViewById(R.id.textViewMyGender);
        textViewMyNickname = findViewById(R.id.textViewMyNickname);
        imageViewChar0 = findViewById(R.id.imageViewChar0);
        imageViewChar1 = findViewById(R.id.imageViewChar1);
        imageViewChar2 = findViewById(R.id.imageViewChar2);
        imageViewChar3 = findViewById(R.id.imageViewChar3);
        imageViewChar4 = findViewById(R.id.imageViewChar4);
        textViewMyEatingAmount = findViewById(R.id.textViewMyEatingAmount);
        textViewMyEatingSpeed = findViewById(R.id.textViewMyEatingSpeed);
        textViewMyAnomity = findViewById(R.id.textViewMyAnomity);
        imageViewMyProfile = findViewById(R.id.imageViewMyProfile);

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(riversRef.child("userProfiles/" + LoginActivity.userId + ".jpg"))
                .into(imageViewMyProfile);
        imageViewMyProfile.setAlpha(250);

        //파이어베이스에서 가져온 개인정보 textView에다가 각각 넣기
        textViewMyId.setText(userId);
        textViewMyName.setText(user.get(userId).get("name").toString());
        textViewMyBirthday.setText(user.get(userId).get("birth").toString());
        textViewMyGender.setText(rvm.checkGender(user.get(userId).get("gender").toString()));
        textViewMyNickname.setText(user.get(userId).get("nickname").toString());
        if(user.get(userId).get("character0").toString().equals("true")){
            imageViewChar0.setImageResource(R.drawable.extrovert);
        } else{
            imageViewChar0.setImageResource(R.drawable.introvert);
        }
        if(user.get(userId).get("character1").toString().equals("true")){
            imageViewChar1.setImageResource(R.drawable.emotional);
        } else{
            imageViewChar1.setImageResource(R.drawable.coldhearted);
        }
        if(user.get(userId).get("character2").toString().equals("true")){
            imageViewChar2.setImageResource(R.drawable.normal);
        } else{
            imageViewChar2.setImageResource(R.drawable.unique);
        }
        if(user.get(userId).get("character3").toString().equals("true")){
            imageViewChar3.setImageResource(R.drawable.mischievous);
        } else{
            imageViewChar3.setImageResource(R.drawable.sedate);
        }
        if(user.get(userId).get("character4").toString().equals("true")){
            imageViewChar4.setImageResource(R.drawable.hasty);
        }else{
            imageViewChar4.setImageResource(R.drawable.relaxed);
        }
        textViewMyEatingAmount.setText(rvm.checkEatingAmount(user.get(userId).get("eating_character0").toString()));
        textViewMyEatingSpeed.setText(rvm.checkEatingSpeed(user.get(userId).get("eating_character1").toString()));
        textViewMyAnomity.setText(rvm.checkAnomity(user.get(userId).get("anonymity").toString()));

    }

    public void setActionbar(){
        //액션바 만들기
        ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);      //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_my_information, null);

        //actionbar에 있는 button
        final Button buttonCompleteChangeInfoActionBar = mCustomView.findViewById(R.id.buttonCompleteChangeInfoActionBar);
        buttonCompleteChangeInfoActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyInformationChangeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ab.setCustomView(mCustomView);
    }
}