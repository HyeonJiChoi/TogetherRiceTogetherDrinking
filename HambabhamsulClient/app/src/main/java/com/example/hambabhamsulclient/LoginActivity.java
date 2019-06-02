package com.example.hambabhamsulclient;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import static com.example.hambabhamsulclient.MainActivity.fireBaseManager;

public class LoginActivity extends AppCompatActivity {
    ActionBar ab;
    Button buttonLoginLogin;
    Button buttonLoginSignUp;

    EditText editTextLoginId;
    EditText editTextLoginPassword;

    static String userId = "";
    static String userPw = "";

    int checkId = 0;
    int checkPw = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setActionbar();
        buttonLoginLogin = findViewById(R.id.buttonLoginLogin);
        buttonLoginSignUp = findViewById(R.id.buttonLoginSignUp);

        editTextLoginId = findViewById(R.id.editTextLoginId);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);

        buttonLoginSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        String loginId = auto.getString("inputId", null);
        String loginPwd = auto.getString("inputPwd", null);

        //MainActivity로 들어왔을 때 둘다 null이 아니면 값이 있다는 뜻 = 자동로그인
        if (loginId != null && loginPwd != null) {
            userId = loginId;
            Toast.makeText(getApplicationContext(), "'" + loginId + "'" + "님 자동로그인 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainScreen.class);
            startActivity(intent);
            finish();
        } else if (loginId == null && loginPwd == null) {   //아무것도 안 적혀있으면 현재 화면 보여주기
            buttonLoginLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkLogin();
                    if (checkId == 1 && checkPw == 1) {
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("inputId", editTextLoginId.getText().toString());
                        autoLogin.putString("inputPwd", editTextLoginPassword.getText().toString());
                        autoLogin.commit();
                        Toast.makeText(getApplicationContext(), "'" + editTextLoginId.getText().toString() + "'님 환영합니다!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    public void checkLogin() {
        if (editTextLoginId.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_LONG).show();
            checkId = 0;
        } else {
            if (fireBaseManager.user.containsKey(editTextLoginId.getText().toString())) {
                checkId = 1;
                if (editTextLoginPassword.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                    checkPw = 0;
                } else {
                    if (editTextLoginPassword.getText().toString().equals(fireBaseManager.user.get(editTextLoginId.getText().toString()).get("Password").toString())) {
                        userId = editTextLoginId.getText().toString();
                        userPw = editTextLoginPassword.getText().toString();
                        checkPw = 1;
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                        checkPw = 0;
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다.", Toast.LENGTH_LONG).show();
                checkId = 0;
            }
        }
    }

    public void setActionbar() {
        //액션바 만들기
        ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.customer_actionbar, null);
        ab.setCustomView(mCustomView);
    }

}