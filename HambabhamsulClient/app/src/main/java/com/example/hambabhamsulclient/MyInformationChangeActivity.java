package com.example.hambabhamsulclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;

public class MyInformationChangeActivity extends AppCompatActivity {
    ActionBar ab;
    StorageReference riversRef;
    private Uri filePath;
    HashMap<String, HashMap<String, Object>> user;
    int checkId = 0;
    String userId = LoginActivity.userId;
    ImageButton imageViewChangeProfile;
    EditText editTextChangeId, editTextChangePassword, editTextChangePasswordCheck, editTextChangeName, editTextChangeYear, editTextChangeMonth, editTextChangeDay, editTextChangeNickname;
    RadioGroup radioGroupChangeGender, radioGroupChangeCharacter1, radioGroupChangeCharacter2, radioGroupChangeCharacter3
            , radioGroupChangeCharacter4, radioGroupChangeCharacter5, radioGroupChangeEatingAmount
            , radioGroupChangeEatingSpeed, radioGroupAnomityCheck;
    RadioButton radioButtonChangeMale, radioButtonChangeFemale, radioButtonChangeChar1_1, radioButtonChangeChar1_2, radioButtonChangeChar2_1, radioButtonChangeChar2_2, radioButtonChangeChar3_1
            , radioButtonChangeChar3_2, radioButtonChangeChar4_1, radioButtonChangeChar4_2, radioButtonChangeChar5_1, radioButtonChangeChar5_2, radioButtonChangeLittle, radioButtonChangeProper
            , radioButtonChangeMany, radioButtonChangeSlow, radioButtonChangeMiddle, radioButtonChangeFast, radioButtonAnomityTrue, radioButtonAnomityFalse;

    ImageView locked;
    ImageView unlocked;

    String updatePw;
    String updateName;
    String updateBirth;
    String updateNickname;
    int updateGender;
    int updateAnonymity;
    int[] updateEatingChar = new int[2];
    boolean [] updateChar = new boolean[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information_change);

        setActionbar();

        riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com");
        imageViewChangeProfile = findViewById(R.id.imageButtonChangeProfile);
        editTextChangeId = findViewById(R.id.editTextChangeId);
        editTextChangePassword = findViewById(R.id.editTextChangePassword);
        editTextChangePasswordCheck = findViewById(R.id.editTextChangePasswordCheck);
        editTextChangeName = findViewById(R.id.editTextChangeName);
        editTextChangeYear = findViewById(R.id.editTextChangeYear);
        editTextChangeMonth = findViewById(R.id.editTextChangeMonth);
        editTextChangeDay = findViewById(R.id.editTextChangeDay);
        editTextChangeNickname = findViewById(R.id.editTextChangeNickname);

        radioGroupChangeGender = findViewById(R.id.radioGroupChangeGender);
        radioGroupChangeCharacter1 = findViewById(R.id.radioGroupChangeCharacter1);
        radioGroupChangeCharacter2 = findViewById(R.id.radioGroupChangeCharacter2);
        radioGroupChangeCharacter3 = findViewById(R.id.radioGroupChangeCharacter3);
        radioGroupChangeCharacter4 = findViewById(R.id.radioGroupChangeCharacter4);
        radioGroupChangeCharacter5 = findViewById(R.id.radioGroupChangeCharacter5);
        radioGroupChangeEatingAmount = findViewById(R.id.radioGroupChangeEatingAmount);
        radioGroupChangeEatingSpeed = findViewById(R.id.radioGroupChangeEatingSpeed);
        radioGroupAnomityCheck = findViewById(R.id.radioGroupAnomityCheck);

        radioButtonChangeMale = findViewById(R.id.radioButtonChangeMale);
        radioButtonChangeFemale = findViewById(R.id.radioButtonChangeFemale);
        radioButtonChangeChar1_1 = findViewById(R.id.radioButtonChangeChar1_1);
        radioButtonChangeChar1_2 = findViewById(R.id.radioButtonChangeChar1_2);
        radioButtonChangeChar2_1 = findViewById(R.id.radioButtonChangeChar2_1);
        radioButtonChangeChar2_2 = findViewById(R.id.radioButtonChangeChar2_2);
        radioButtonChangeChar3_1 = findViewById(R.id.radioButtonChangeChar3_1);
        radioButtonChangeChar3_2 = findViewById(R.id.radioButtonChangeChar3_2);
        radioButtonChangeChar4_1 = findViewById(R.id.radioButtonChangeChar4_1);
        radioButtonChangeChar4_2 = findViewById(R.id.radioButtonChangeChar4_2);
        radioButtonChangeChar5_1 = findViewById(R.id.radioButtonChangeChar5_1);
        radioButtonChangeChar5_2 = findViewById(R.id.radioButtonChangeChar5_2);
        radioButtonChangeLittle = findViewById(R.id.radioButtonChangeLittle);
        radioButtonChangeProper = findViewById(R.id.radioButtonChangeProper);
        radioButtonChangeMany = findViewById(R.id.radioButtonChangeMany);
        radioButtonChangeSlow = findViewById(R.id.radioButtonChangeSlow);
        radioButtonChangeMiddle = findViewById(R.id.radioButtonChangeMiddle);
        radioButtonChangeFast = findViewById(R.id.radioButtonChangeFast);
        radioButtonAnomityTrue = findViewById(R.id.radioButtonAnomityTrue);
        radioButtonAnomityFalse = findViewById(R.id.radioButtonAnomityFalse);

        locked = findViewById(R.id.locked);
        unlocked = findViewById(R.id.unlocked);

        user = MainActivity.fireBaseManager.user;

        editTextChangeId.setText(userId);
        editTextChangeId.setEnabled(false);
        editTextChangeName.setText(user.get(userId).get("name").toString());
        editTextChangeNickname.setText(user.get(userId).get("nickname").toString());
        editTextChangeYear.setText(user.get(userId).get("birth").toString().substring(0,4));
        editTextChangeMonth.setText(user.get(userId).get("birth").toString().substring(4,6));
        editTextChangeDay.setText(user.get(userId).get("birth").toString().substring(6,8));


        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(riversRef.child("userProfiles/" + LoginActivity.userId + ".jpg"))
                .into(imageViewChangeProfile);
        imageViewChangeProfile.setAlpha(130);

        //프로필 변경 버튼을 눌렀을때
        imageViewChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });
        /*
        //아이디 중복확인 버튼을 눌렀을 때
        buttonChangeIdDuplication.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editTextChangeId.getText().toString().length() == 0)
                    Toast.makeText(getApplicationContext(), "ID를 입력해주세요", Toast.LENGTH_LONG).show();
                else {
                    if (MainActivity.fireBaseManager.user.containsKey(editTextChangeId.getText().toString()))
                        Toast.makeText(getApplicationContext(), "현재 다른 사용자가 사용중인 ID입니다", Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getApplicationContext(), "사용가능한 ID입니다", Toast.LENGTH_LONG).show();
                        checkId = 1;
                        userId = editTextChangeId.getText().toString();
                    }
                }
            }
        });
        */

        //비밀번호 입력과 확인
        editTextChangePasswordCheck.addTextChangedListener(new TextWatcher() {
            //입력되는 텍스트에 변화가 있을때
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //입력이 끝났을 떄
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextChangePasswordCheck.getText().toString().equals(editTextChangePassword.getText().toString())) {
                    unlocked.setVisibility(View.VISIBLE);
                    locked.setVisibility(View.INVISIBLE);
                }
            }

            //입력하기 전에
            @Override
            public void afterTextChanged(Editable s) {
                if (editTextChangePasswordCheck.getText().toString().equals(editTextChangePassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_LONG).show();
                    unlocked.setVisibility(View.INVISIBLE);
                    locked.setVisibility(View.VISIBLE);
                    updatePw = editTextChangePassword.getText().toString();
                }
            }
        });

        if(user.get(userId).get("gender").toString().equals("0")) {
            radioButtonChangeMale.setChecked(true);
        }
        else{
            radioButtonChangeFemale.setChecked(true);
        }

        if(user.get(userId).get("character0").toString().equals("true")) {
            radioButtonChangeChar1_1.setChecked(true);
        }
        else{
            radioButtonChangeChar1_2.setChecked(true);
        }

        if(user.get(userId).get("character1").toString().equals("true")) {
            radioButtonChangeChar2_1.setChecked(true);
        }
        else{
            radioButtonChangeChar2_2.setChecked(true);
        }

        if(user.get(userId).get("character2").toString().equals("true")) {
            radioButtonChangeChar3_1.setChecked(true);
        }
        else{
            radioButtonChangeChar3_2.setChecked(true);
        }

        if(user.get(userId).get("character3").toString().equals("true")) {
            radioButtonChangeChar4_1.setChecked(true);
        }
        else{
            radioButtonChangeChar4_2.setChecked(true);
        }

        if(user.get(userId).get("character4").toString().equals("true")) {
            radioButtonChangeChar5_1.setChecked(true);
        }
        else{
            radioButtonChangeChar5_2.setChecked(true);
        }

        if(user.get(userId).get("eating_character0").toString().equals("0")) {
            radioButtonChangeLittle.setChecked(true);
        }
        else if(user.get(userId).get("eating_character0").toString().equals("1")){
            radioButtonChangeProper.setChecked(true);
        }
        else{
            radioButtonChangeMany.setChecked(true);
        }

        if(user.get(userId).get("eating_character1").toString().equals("0")) {
            radioButtonChangeSlow.setChecked(true);
        }
        else if(user.get(userId).get("eating_character0").toString().equals("1")){
            radioButtonChangeMiddle.setChecked(true);
        }
        else{
            radioButtonChangeFast.setChecked(true);
        }

        if(user.get(userId).get("anonymity").toString().equals("0")) {
            radioButtonAnomityTrue.setChecked(true);
        }
        else{
            radioButtonAnomityFalse.setChecked(true);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            Glide.with(this)
                    .load(filePath)
                    .into(imageViewChangeProfile);
        }
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
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_my_information_change, null);
        final Button buttonCompleteChangeInfoCompleteActionBar = mCustomView.findViewById(R.id.buttonCompleteChangeInfoCompleteActionBar);
        buttonCompleteChangeInfoCompleteActionBar.setOnClickListener(new View.OnClickListener() {
            public void setUpdateInfo() {
                updatePw = editTextChangePasswordCheck.getText().toString();
                updateName = editTextChangeName.getText().toString();
                updateBirth = editTextChangeYear.getText().toString() + editTextChangeMonth.getText().toString() + editTextChangeDay.getText().toString();
                updateNickname = editTextChangeNickname.getText().toString();

                radioGroupChangeCharacter1.getCheckedRadioButtonId();
                if (radioButtonChangeChar1_1.isChecked()) {
                    updateChar[0] = true; //"extrovert";
                }
                if (radioButtonChangeChar1_2.isChecked()) {
                    updateChar[0] = false;//"introvert";
                }
                radioGroupChangeCharacter2.getCheckedRadioButtonId();
                if (radioButtonChangeChar2_1.isChecked()) {
                    updateChar[1] = true; //"extrovert";
                }
                if (radioButtonChangeChar2_2.isChecked()) {
                    updateChar[1] = false;//"introvert";
                }
                radioGroupChangeCharacter3.getCheckedRadioButtonId();
                if (radioButtonChangeChar3_1.isChecked()) {
                    updateChar[2] = true; //"extrovert";
                }
                if (radioButtonChangeChar3_2.isChecked()) {
                    updateChar[2] = false;//"introvert";
                }
                radioGroupChangeCharacter4.getCheckedRadioButtonId();
                if (radioButtonChangeChar4_1.isChecked()) {
                    updateChar[3] = true; //"extrovert";
                }
                if (radioButtonChangeChar4_2.isChecked()) {
                    updateChar[3] = false;//"introvert";
                }
                radioGroupChangeCharacter5.getCheckedRadioButtonId();
                if (radioButtonChangeChar5_1.isChecked()) {
                    updateChar[4] = true; //"extrovert";
                }
                if (radioButtonChangeChar5_2.isChecked()) {
                    updateChar[4] = false;//"introvert";
                }

                radioGroupChangeGender.getCheckedRadioButtonId();
                if (radioButtonChangeMale.isChecked()) {
                    updateGender = 0;
                }
                if (radioButtonChangeFemale.isChecked()) {
                    updateGender = 1;
                }

                //밥 먹는 양
                radioGroupChangeEatingAmount.getCheckedRadioButtonId();
                if (radioButtonChangeLittle.isChecked()) {
                    updateEatingChar[0] = 0;
                }
                if (radioButtonChangeProper.isChecked()) {
                    updateEatingChar[0] = 1;
                }
                if (radioButtonChangeMany.isChecked()) {
                    updateEatingChar[0] = 2;
                }

                //밥 먹는 속도
                radioGroupChangeEatingSpeed.getCheckedRadioButtonId();
                if (radioButtonChangeSlow.isChecked()) {
                    updateEatingChar[1] = 0;
                }
                if (radioButtonChangeMiddle.isChecked()) {
                    updateEatingChar[1] = 1;
                }
                if (radioButtonChangeFast.isChecked()) {
                    updateEatingChar[1] = 2;
                }

                radioGroupAnomityCheck.getCheckedRadioButtonId();
                if (radioButtonAnomityTrue.isChecked()) {
                    updateAnonymity = 0;
                }
                if (radioButtonAnomityFalse.isChecked()) {
                    updateAnonymity = 1;
                }
            }
            @Override
            public void onClick(View v) {
                setUpdateInfo();
                MainActivity.fireBaseManager.updateUserInfo(userId, updatePw, updateName, updateNickname, updateBirth, updateGender, updateAnonymity, updateEatingChar, updateChar);
                Intent intent = new Intent(getApplicationContext(), MyInformationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ab.setCustomView(mCustomView);
    }
}