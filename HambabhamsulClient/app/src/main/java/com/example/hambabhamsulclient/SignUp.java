package com.example.hambabhamsulclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SignUp extends AppCompatActivity {
    ActionBar ab;
    private Uri filePath; //그림 이동로

    public int buttonSignUpState = 0;

    String userId;
    String userPassword;
    String userName;
    String userNickname;
    String userBirth;
    int userGender; //0 = 남자, 1 = 여자
    int[] userEatingCharacter = new int[2]; //[0] - 양, [1] - 속도
    boolean[] userCharacter = new boolean[5];

    EditText editTextSignUpId;
    EditText editTextSignUpPassword;
    EditText editTextSignUpSamePassword;
    EditText editTextSignUpName;
    EditText editTextSignUpYear;
    EditText editTextSignUpMonth;
    EditText editTextSignUpDay;
    EditText editTextSignUpNickname;

    TextView textViewSignUpId, textViewSignUpPassword, textViewSignUpSamePassword, textViewSignUpName, textViewSignUpBirth, textViewSignUpGender,
            textViewSignUpNickname, textViewSignUpCharacter, textViewSignUpEatingAmount, textViewSignUpEatingSpeed;

    ImageButton imageViewSignupProfile;
    Button buttonSignUpSignUp;
    Button buttonSignUpIdDuplication;

    RadioGroup radioGroupSignUpGender;
    RadioButton radioButtonSignUpMale;
    RadioButton radioButtonSignUpFemale;

    RadioGroup radioGroupSignUpCharacter1;
    RadioButton radioButtonSignUpChar1_1;
    RadioButton radioButtonSignUpChar1_2;

    RadioGroup radioGroupSignUpCharacter2;
    RadioButton radioButtonSignUpChar2_1;
    RadioButton radioButtonSignUpChar2_2;

    RadioGroup radioGroupSignUpCharacter3;
    RadioButton radioButtonSignUpChar3_1;
    RadioButton radioButtonSignUpChar3_2;

    RadioGroup radioGroupSignUpCharacter4;
    RadioButton radioButtonSignUpChar4_1;
    RadioButton radioButtonSignUpChar4_2;

    RadioGroup radioGroupSignUpCharacter5;
    RadioButton radioButtonSignUpChar5_1;
    RadioButton radioButtonSignUpChar5_2;

    RadioGroup radioGroupSignUpEatingAmount;
    RadioButton radioButtonSignUpLittle;
    RadioButton radioButtonSignUpProper;
    RadioButton radioButtonSignUpMany;

    RadioGroup radioGroupSignUpEatingSpeed;
    RadioButton radioButtonSignUpSlow;
    RadioButton radioButtonSignUpMiddle;
    RadioButton radioButtonSignUpFast;

    int speed = -1;
    int amount = -1;

    int buttonSignUpIdDuplicationState = 0;

    ImageView locked;
    ImageView unlocked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setActionbar();
        textViewSignUpId = findViewById(R.id.textViewSignUpId);
        textViewSignUpPassword = findViewById(R.id.textViewSignUpPassword);
        textViewSignUpSamePassword = findViewById(R.id.textViewSignUpSamePassword);
        textViewSignUpName = findViewById(R.id.textViewSignUpName);
        textViewSignUpBirth = findViewById(R.id.textViewSignUpBirth);
        textViewSignUpGender = findViewById(R.id.textViewSignUpGender);
        textViewSignUpNickname = findViewById(R.id.textViewSignUpNickname);
        textViewSignUpCharacter = findViewById(R.id.textViewSignUpCharacter);
        textViewSignUpEatingAmount = findViewById(R.id.textViewSignUpEatingAmount);
        textViewSignUpEatingSpeed = findViewById(R.id.textViewSignUpEatingSpeed);

        buttonSignUpSignUp = findViewById(R.id.buttonSignUpSignUp);

        editTextSignUpId = findViewById(R.id.editTextSignUpId);
        editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
        editTextSignUpSamePassword = findViewById(R.id.editTextSignUpSamePassword);
        editTextSignUpName = findViewById(R.id.editTextSignUpName);
        editTextSignUpYear = findViewById(R.id.editTextSignUpYear);
        editTextSignUpMonth = findViewById(R.id.editTextSignUpMonth);
        editTextSignUpDay = findViewById(R.id.editTextSignUpDay);
        editTextSignUpNickname = findViewById(R.id.editTextSignUpNickname);

        imageViewSignupProfile = findViewById(R.id.imageViewSignupProfile);

        buttonSignUpIdDuplication = findViewById(R.id.buttonSignUpIdDuplication);

        radioGroupSignUpGender = findViewById(R.id.radioGroupSignUpGender);
        radioGroupSignUpCharacter1 = findViewById(R.id.radioGroupSignUpCharacter1);
        radioGroupSignUpCharacter2 = findViewById(R.id.radioGroupSignUpCharacter2);
        radioGroupSignUpCharacter3 = findViewById(R.id.radioGroupSignUpCharacter3);
        radioGroupSignUpCharacter4 = findViewById(R.id.radioGroupSignUpCharacter4);
        radioGroupSignUpCharacter5 = findViewById(R.id.radioGroupSignUpCharacter5);
        radioGroupSignUpEatingAmount = findViewById(R.id.radioGroupSignUpEatingAmount);
        radioGroupSignUpEatingSpeed = findViewById(R.id.radioGroupSignUpEatingSpeed);

        radioButtonSignUpMale = findViewById(R.id.radioButtonSignUpMale);
        radioButtonSignUpFemale = findViewById(R.id.radioButtonSignUpFemale);
        radioButtonSignUpChar1_1 = findViewById(R.id.radioButtonSignUpChar1_1);
        radioButtonSignUpChar1_2 = findViewById(R.id.radioButtonSignUpChar1_2);
        radioButtonSignUpChar2_1 = findViewById(R.id.radioButtonSignUpChar2_1);
        radioButtonSignUpChar2_2 = findViewById(R.id.radioButtonSignUpChar2_2);
        radioButtonSignUpChar3_1 = findViewById(R.id.radioButtonSignUpChar3_1);
        radioButtonSignUpChar3_2 = findViewById(R.id.radioButtonSignUpChar3_2);
        radioButtonSignUpChar4_1 = findViewById(R.id.radioButtonSignUpChar4_1);
        radioButtonSignUpChar4_2 = findViewById(R.id.radioButtonSignUpChar4_2);
        radioButtonSignUpChar5_1 = findViewById(R.id.radioButtonSignUpChar5_1);
        radioButtonSignUpChar5_2 = findViewById(R.id.radioButtonSignUpChar5_2);
        radioButtonSignUpLittle = findViewById(R.id.radioButtonSignUpLittle);
        radioButtonSignUpProper = findViewById(R.id.radioButtonSignUpProper);
        radioButtonSignUpMany = findViewById(R.id.radioButtonSignUpMany);
        radioButtonSignUpSlow = findViewById(R.id.radioButtonSignUpSlow);
        radioButtonSignUpMiddle = findViewById(R.id.radioButtonSignUpMiddle);
        radioButtonSignUpFast = findViewById(R.id.radioButtonSignUpFast);

        locked = findViewById(R.id.locked);
        unlocked = findViewById(R.id.unlocked);

        //프로필 변경 버튼을 눌렀을때
        imageViewSignupProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });
        //아이디 중복확인 버튼을 눌렀을 때
        buttonSignUpIdDuplication.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editTextSignUpId.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주십시오.", Toast.LENGTH_LONG).show();
                    textViewSignUpId.setTextColor(Color.parseColor("#ff0000"));
                    buttonSignUpIdDuplicationState = 0;
                } else {
                    if (MainActivity.fireBaseManager.user.containsKey(editTextSignUpId.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "중복된 아이디입니다.", Toast.LENGTH_LONG).show();
                        textViewSignUpId.setTextColor(Color.parseColor("#ff0000"));
                        buttonSignUpIdDuplicationState = 0;
                    } else {
                        Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_LONG).show();
                        textViewSignUpId.setTextColor(Color.parseColor("#696969"));
                        buttonSignUpIdDuplicationState = 1;
                        userId = editTextSignUpId.getText().toString();
                    }
                }
            }
        });

        //비밀번호 입력과 확인
        editTextSignUpSamePassword.addTextChangedListener(new TextWatcher() {
            //입력되는 텍스트에 변화가 있을때
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //입력이 끝났을 떄
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextSignUpSamePassword.getText().toString().equals(editTextSignUpPassword.getText().toString())) {
                    unlocked.setVisibility(View.VISIBLE);
                    locked.setVisibility(View.INVISIBLE);
                }
            }

            //입력하기 전에
            @Override
            public void afterTextChanged(Editable s) {
                if (editTextSignUpSamePassword.getText().toString().equals(editTextSignUpPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_LONG).show();
                    unlocked.setVisibility(View.INVISIBLE);
                    locked.setVisibility(View.VISIBLE);
                    userPassword = editTextSignUpPassword.getText().toString();
                }
            }
        });

        buttonSignUpSignUp.setOnClickListener(new View.OnClickListener() {
            public int checkCondition() {
                int checkAllCondition = 0;
                int idCheck = 0;
                int pwCheck = 0;
                int pwSameCheck = 0;
                int nameCheck = 0;
                int birthCheck = 0;
                int birthYearCheck = 0;
                int birthMonthCheck = 0;
                int birthDayCheck = 0;
                int genderCheck = 0;
                int nicknameCheck = 0;
                int[] characterCheck = {0, 0, 0, 0, 0};
                int eatingSpeedCheck = 0;
                int eatingAmountCheck = 0;

                //속도 입력 x
                if (speed == -1) {
                    Toast.makeText(getApplicationContext(), "'밥 먹는 속도'를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    eatingSpeedCheck = -1;
                    textViewSignUpEatingSpeed.setTextColor(Color.parseColor("#ff0000"));
                }
                //양 입력 x
                if (amount == -1) {
                    Toast.makeText(getApplicationContext(), "'밥 먹는 양'을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    eatingAmountCheck = -1;
                    textViewSignUpEatingAmount.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격 (성급 - 여유) 입력 x
                radioGroupSignUpCharacter5.getCheckedRadioButtonId();
                if (radioButtonSignUpChar5_1.isChecked()) {
                    userCharacter[4] = true; //"extrovert";
                    characterCheck[4] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar5_2.isChecked()) {
                    userCharacter[4] = false;//"introvert";
                    characterCheck[4] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar5_1.isChecked() == false && radioButtonSignUpChar5_2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(성급한, 여유로운) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    characterCheck[4] = -1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격 (장난 - 진중) 입력 x
                radioGroupSignUpCharacter4.getCheckedRadioButtonId();
                if (radioButtonSignUpChar4_1.isChecked()) {
                    userCharacter[3] = true; //"extrovert";
                    characterCheck[3] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar4_2.isChecked()) {
                    userCharacter[3] = false;//"introvert";
                    characterCheck[3] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar4_1.isChecked() == false && radioButtonSignUpChar4_2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(장난스러운, 진중한) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    characterCheck[3] = -1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격 (평범 - 개성) 입력 x
                radioGroupSignUpCharacter3.getCheckedRadioButtonId();
                if (radioButtonSignUpChar3_1.isChecked()) {
                    userCharacter[2] = true; //"extrovert";
                    characterCheck[2] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar3_2.isChecked()) {
                    userCharacter[2] = false;//"introvert";
                    characterCheck[2] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar3_1.isChecked() == false && radioButtonSignUpChar3_2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(평범한, 개성있는) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    characterCheck[2] = -1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격 (감성 - 이성) 입력 x
                radioGroupSignUpCharacter2.getCheckedRadioButtonId();
                if (radioButtonSignUpChar2_1.isChecked()) {
                    userCharacter[1] = true; //"extrovert";
                    characterCheck[1] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar2_2.isChecked()) {
                    userCharacter[1] = false;//"introvert";
                    characterCheck[1] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar2_1.isChecked() == false && radioButtonSignUpChar2_2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(감성적인, 이성적인) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    characterCheck[1] = -1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격 (외향 - 내향) 입력 x
                radioGroupSignUpCharacter1.getCheckedRadioButtonId();
                if (radioButtonSignUpChar1_1.isChecked()) {
                    userCharacter[0] = true; //"extrovert";
                    characterCheck[0] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar1_2.isChecked()) {
                    userCharacter[0] = false;//"introvert";
                    characterCheck[0] = 1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpChar1_1.isChecked() == false && radioButtonSignUpChar1_2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(외향적인, 내향적인) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    characterCheck[0] = -1;
                    textViewSignUpCharacter.setTextColor(Color.parseColor("#ff0000"));
                }
                //닉네임 입력 x
                if (editTextSignUpNickname.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    nicknameCheck = -1;
                    textViewSignUpNickname.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    nicknameCheck = 1;
                    textViewSignUpNickname.setTextColor(Color.parseColor("#696969"));
                }
                //성별 입력 x
                radioGroupSignUpGender.getCheckedRadioButtonId();
                if (radioButtonSignUpMale.isChecked()) {
                    userGender = 0;
                    genderCheck = 0;
                    textViewSignUpGender.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpFemale.isChecked()) {
                    userGender = 1;
                    genderCheck = 1;
                    textViewSignUpGender.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpMale.isChecked() == false && radioButtonSignUpFemale.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성별을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    genderCheck = -1;
                    textViewSignUpGender.setTextColor(Color.parseColor("#ff0000"));
                }
                //일 입력 x
                if (editTextSignUpDay.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "태어난 날을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    birthDayCheck = -1;
                    textViewSignUpBirth.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    birthDayCheck = 1;
                    textViewSignUpBirth.setTextColor(Color.parseColor("#696969"));
                }
                //월 입력 x
                if (editTextSignUpMonth.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "태어난 달을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    birthMonthCheck = -1;
                    textViewSignUpBirth.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    birthMonthCheck = 1;
                    textViewSignUpBirth.setTextColor(Color.parseColor("#696969"));
                }
                //년 입력 x
                if (editTextSignUpYear.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "태어난 년도를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    birthYearCheck = -1;
                    textViewSignUpBirth.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    birthYearCheck = 1;
                    textViewSignUpBirth.setTextColor(Color.parseColor("#696969"));
                }
                //생년월일 확인
                String userBirthYear = editTextSignUpYear.getText().toString();
                String userBirthMonth = editTextSignUpMonth.getText().toString();
                String userBirthDay = editTextSignUpDay.getText().toString();

                if (editTextSignUpMonth.getText().toString().length() == 1)
                    userBirthMonth = "0" + userBirthMonth;

                if (editTextSignUpDay.getText().toString().length() == 1)
                    userBirthDay = "0" + userBirthDay;

                userBirth = userBirthYear + userBirthMonth + userBirthDay;

                if (userBirth.length() == 8) {
                    birthCheck = 1;
                    textViewSignUpName.setTextColor(Color.parseColor("#696969"));
                } else {
                    Toast.makeText(getApplicationContext(), "생년월일을 확인해주세요!", Toast.LENGTH_SHORT).show();
                    nameCheck = -1;
                    textViewSignUpName.setTextColor(Color.parseColor("#ff0000"));
                }

                //이름 입력 x
                if (editTextSignUpName.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    nameCheck = -1;
                    textViewSignUpName.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    nameCheck = 1;
                    textViewSignUpName.setTextColor(Color.parseColor("#696969"));
                }

                //비밀번호 확인 입력 x
                if (editTextSignUpSamePassword.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    pwSameCheck = -1;
                    textViewSignUpSamePassword.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    pwSameCheck = 1;
                    textViewSignUpSamePassword.setTextColor(Color.parseColor("#696969"));
                }
                //비밀번호 입력 x
                if (editTextSignUpPassword.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    pwCheck = -1;
                    textViewSignUpPassword.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    pwCheck = 1;
                    textViewSignUpPassword.setTextColor(Color.parseColor("#696969"));
                }

                //아이디 중복확인 버튼 x


                //아이디 입력 x
                if (editTextSignUpId.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    idCheck = -1;
                    textViewSignUpId.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    idCheck = 1;
                    textViewSignUpId.setTextColor(Color.parseColor("#696969"));
                }


                //밥 먹는 양
                radioGroupSignUpEatingAmount.getCheckedRadioButtonId();
                if (radioButtonSignUpLittle.isChecked()) {
                    userEatingCharacter[0] = 0;
                    eatingAmountCheck = 1;
                    textViewSignUpEatingAmount.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpProper.isChecked()) {
                    userEatingCharacter[0] = 1;
                    eatingAmountCheck = 1;
                    textViewSignUpEatingAmount.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpMany.isChecked()) {
                    userEatingCharacter[0] = 2;
                    eatingAmountCheck = 1;
                    textViewSignUpEatingAmount.setTextColor(Color.parseColor("#696969"));
                }

                //밥 먹는 속도
                radioGroupSignUpEatingSpeed.getCheckedRadioButtonId();
                if (radioButtonSignUpSlow.isChecked()) {
                    userEatingCharacter[1] = 0;
                    eatingSpeedCheck = 1;
                    textViewSignUpEatingSpeed.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpMiddle.isChecked()) {
                    userEatingCharacter[1] = 1;
                    eatingSpeedCheck = 1;
                    textViewSignUpEatingSpeed.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonSignUpFast.isChecked()) {
                    userEatingCharacter[1] = 2;
                    eatingSpeedCheck = 1;
                    textViewSignUpEatingSpeed.setTextColor(Color.parseColor("#696969"));
                }

                //전체 조건 확인
                if (buttonSignUpIdDuplicationState == 1 && idCheck == 1 && pwCheck == 1 && pwSameCheck == 1 && nameCheck == 1 && birthCheck == 1 && birthYearCheck == 1 && birthMonthCheck == 1 && birthDayCheck == 1 && genderCheck == 1 && nicknameCheck == 1 && eatingAmountCheck == 1 && eatingSpeedCheck == 1 && characterCheck[0] == 1 && characterCheck[1] == 1 && characterCheck[2] == 1 && characterCheck[3] == 1 && characterCheck[04] == 1)
                    checkAllCondition = 1;
                else
                    checkAllCondition = -1;

                return checkAllCondition;
            }

            public void onClick(View v) {
                if (checkCondition() == 1) {
                    userName = editTextSignUpName.getText().toString();
                    userNickname = editTextSignUpNickname.getText().toString();

                    Toast.makeText(getApplicationContext(), "가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    StorageReference riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com").child("userProfiles/" + userId + ".jpg");
                    if (filePath != null) {
                        riversRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                System.out.println("성공______________");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("실패______________");
                            }
                        });

                    } else {
                    }

                    MainActivity.fireBaseManager.inputUserInfo(userId, userPassword, userName, userNickname, userBirth, userGender, userEatingCharacter, userCharacter);
                    //buttonSignUpState = 1;
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            Glide.with(this)
                    .load(filePath)
                    .into(imageViewSignupProfile);
        }
    }

    public void setActionbar() {
        //액션바 만들기
        ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);      //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.
        ab.setHomeAsUpIndicator(R.drawable.menu_icon);

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.customer_actionbar_login, null);
        ab.setCustomView(mCustomView);
    }
}