package com.example.hambabhamsulclient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MakerConditionCheck extends AppCompatActivity {
    ActionBar ab;
    private Uri filePath; //그림 이동로
    HashMap<String, HashMap<String,Object>> meeting;
    HashMap<String, HashMap<String,Object>> user;
    ImageButton imageButtonMakerConditionCheckProfile;
    TextView textViewMakerMeetingMeetingName;

    EditText editTextMakerMeetingName;
    EditText editTextMakerMeetingFoodKind;
    EditText editTextMakerMeetingDate;
    EditText editTextMakerMeetingLocation;
    EditText editTextMakerMeetingMinAge;
    EditText editTextMakerMeetingMaxAge;
    EditText editTextMakerMeetingLimit;
    EditText editTextMakerMeetingIntroduce;

    EditText editTextMakerMeetingStartTime;
    EditText editTextMakerMeetingEndTime;

    RadioGroup radioGroupMakerMeetingPeriod;
    RadioButton radioButtonMakerMeetingShort;
    RadioButton radioButtonMakerMeetingMedium;
    RadioButton radioButtonMakerMeetingLong;

    RadioGroup radioGroupMakerMeetingGender;
    RadioButton radioButtonMakerMeetingMale;
    RadioButton radioButtonMakerMeetingFemale;
    RadioButton radioButtonMakerMeetingNeutrality;

    Spinner spinnerMakerMeetingFoodKind, spinnerMakerMeetingSiDo, spinnerMakerMeetingSiGuGun, spinnerMakerMeetingDongEupMyun;
    Button buttonMakerMeetingDate;

    String meetingId = SpecificMeetingActivity.meetingId;
    String userId = LoginActivity.userId;

    String makerMeetingFoodKind;
    String makerMeetingSido;
    String makerMeetingSigugun;
    String makerMeetingDongEupMyun;

    String date;
    Thread thread = null;
    Handler handler = null;
    int selectedYear;
    int selectedMonth;
    int selectedDay;

    static String selectedSido, selectedSigugun, selectedDongEupMyun = "";

    int updateFoodKind;
    String updateDate;
    int updatePeriod;
    int updateStartTime;
    int updateEndTime;
    int updateMinAge;
    int updateMaxAge;
    int updateGender;
    int updateLimit;
    String updateIntroduce;

    String foodkind = "";
    String[] spinnerfoodkind = {"전체", "한식", "분식", "카페,디저트", "돈까스,회,일식", "치킨", "피자", "중국집", "족발,보쌈", "도시락", "찜,탕", "패스트푸드"};

    final int DIALOG_DATE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_condition_check);

        setActionbar();

        final String[] sido = getResources().getStringArray(R.array.sidoName);

        final String[] seoul = getResources().getStringArray(R.array.seoul);
        final String[] gannamgu = getResources().getStringArray(R.array.gangnamgu);
        final String[] gangdonggu = getResources().getStringArray(R.array.gangdonggu);
        final String[] gangbukgu = getResources().getStringArray(R.array.gangbukgu);
        final String[] gangseogu = getResources().getStringArray(R.array.gangseogu);
        final String[] dobonggu = getResources().getStringArray(R.array.dobonggu);
        final String[] dongdaemungu = getResources().getStringArray(R.array.dongdaemungu);
        final String[] songpagu = getResources().getStringArray(R.array.songpagu);


        final String[] gyeonggi = getResources().getStringArray(R.array.gyeonggi);
        final String[] gyunagmyungsi = getResources().getStringArray(R.array.gyunagmyungsi);
        final String[] gyuangjusi = getResources().getStringArray(R.array.gyuangjusi);
        final String[] gimposi = getResources().getStringArray(R.array.gimposi);
        final String[] namyangjusi = getResources().getStringArray(R.array.namyangjusi);
        final String[] seongnamsisujeonggu = getResources().getStringArray(R.array.seongnamsisujeonggu);
        final String[] seongnamsibundanggu = getResources().getStringArray(R.array.seongnamsibundanggu);
        final String[] suwonsigyunseongu = getResources().getStringArray(R.array.suwonsigyunseongu);
        final String[] suwonsiyeongtonggu = getResources().getStringArray(R.array.suwonsiyeongtonggu);
        final String[] yonginsigiheonggu = getResources().getStringArray(R.array.yonginsigiheonggu);
        final String[] euijeongbusi = getResources().getStringArray(R.array.euijeongbusi);
        final String[] whasungsi = getResources().getStringArray(R.array.whasungsi);

        final String[] incheon = getResources().getStringArray(R.array.incheon);

        final String[] jeju = getResources().getStringArray(R.array.jeju);

        //findViewById
        spinnerMakerMeetingSiDo = (Spinner) findViewById(R.id.spinnerMakerMeetingSiDo);
        spinnerMakerMeetingSiGuGun = (Spinner) findViewById(R.id.spinnerMakerMeetingSiGuGun);
        spinnerMakerMeetingDongEupMyun = (Spinner) findViewById(R.id.spinnerMakerMeetingDongEupMyun);

        meeting = MainActivity.fireBaseManager.meeting;
        user = MainActivity.fireBaseManager.user;

        textViewMakerMeetingMeetingName = findViewById(R.id.textViewMakerMeetingMeetingName);

        spinnerMakerMeetingFoodKind = (Spinner)findViewById(R.id.spinnerMakerMeetingFoodKind);

        editTextMakerMeetingName = findViewById(R.id.editTextMakerMeetingName);
        editTextMakerMeetingIntroduce = findViewById(R.id.editTextMakerMeetingIntroduce);
        editTextMakerMeetingMinAge = findViewById(R.id.editTextMakerMeetingMinAge);
        editTextMakerMeetingMaxAge = findViewById(R.id.editTextMakerMeetingMaxAge);
        editTextMakerMeetingLimit = findViewById(R.id.editTextMakerMeetingLimit);
        editTextMakerMeetingStartTime = findViewById(R.id.editTextMakerMeetingStartTime);
        editTextMakerMeetingEndTime = findViewById(R.id.editTextMakerMeetingEndTime);

        radioGroupMakerMeetingPeriod = findViewById(R.id.radioGroupMakerMeetingPeriod);
        radioButtonMakerMeetingShort = findViewById(R.id.radioButtonMakerMeetingShort);
        radioButtonMakerMeetingMedium = findViewById(R.id.radioButtonMakerMeetingMedium);
        radioButtonMakerMeetingLong = findViewById(R.id.radioButtonMakerMeetingLong);

        radioGroupMakerMeetingGender = findViewById(R.id.radioGroupMakerMeetingGender);
        radioButtonMakerMeetingMale = findViewById(R.id.radioButtonMakerMeetingMale);
        radioButtonMakerMeetingFemale = findViewById(R.id.radioButtonMakerMeetingFamale);
        radioButtonMakerMeetingNeutrality = findViewById(R.id.radioButtonMakerMeetingNeutrality);

        buttonMakerMeetingDate = findViewById(R.id.buttonMakerMeetingDate);

        imageButtonMakerConditionCheckProfile = findViewById(R.id.imageButtonMakerConditionCheckProfile);

        //TODO: Spinner 마무리 해야됨
        //(시/도)
        final ArrayAdapter<String> arrayAdapterSiDo = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, sido);
        spinnerMakerMeetingSiDo.setAdapter(arrayAdapterSiDo);
        //(시/구/군)
        String[] sigugun = {"(시/구/군)"};
        final ArrayAdapter<String> arrayAdapterSiGuGun = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, sigugun);
        spinnerMakerMeetingSiGuGun.setAdapter(arrayAdapterSiGuGun);
        //(동/읍/면)
        String[] dongeupmyun = {"(동/읍/면)"};
        final ArrayAdapter<String> arrayAdapterDongEupMyun = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, dongeupmyun);
        spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterDongEupMyun);

        //서울
        final ArrayAdapter<CharSequence> arrayAdapterSeoul = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.seoul, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeoul.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강남구
        final ArrayAdapter<CharSequence> arrayAdapterGangnamgu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.gangnamgu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangnamgu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강동구
        final ArrayAdapter<CharSequence> arrayAdapterGangdonggu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.gangdonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangdonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강북구
        final ArrayAdapter<CharSequence> arrayAdapterGangbukgu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.gangbukgu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangbukgu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강서구
        final ArrayAdapter<CharSequence> arrayAdapterGangseogu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.gangseogu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangseogu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //도봉구
        final ArrayAdapter<CharSequence> arrayAdapterDobonggu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.dobonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterDobonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //동대문구
        final ArrayAdapter<CharSequence> arrayAdapterDongdaemungu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.dongdaemungu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterDongdaemungu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //송파구
        final ArrayAdapter<CharSequence> arrayAdapterSongpagu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.songpagu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSongpagu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);

        //경기
        final ArrayAdapter<CharSequence> arrayAdapterGyeonggi = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.gyeonggi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyeonggi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //광명시
        final ArrayAdapter<CharSequence> arrayAdapterGyunagmyungsi = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.gyunagmyungsi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyunagmyungsi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //광주시
        final ArrayAdapter<CharSequence> arrayAdapterGyuangjusi = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.gyuangjusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyuangjusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //김포시
        final ArrayAdapter<CharSequence> arrayAdapterGimposi = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.gimposi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGimposi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //남양주시
        final ArrayAdapter<CharSequence> arrayAdapterNamyangjusi = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.namyangjusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterNamyangjusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //성남시 분당구
        final ArrayAdapter<CharSequence> arrayAdapterSeongnamsibundanggu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.seongnamsibundanggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeongnamsibundanggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //성남시 수정구
        final ArrayAdapter<CharSequence> arrayAdapterSeongnamsisujeonggu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.seongnamsisujeonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeongnamsisujeonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //수원시 권선구
        final ArrayAdapter<CharSequence> arrayAdapterSuwonsigyunseongu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.suwonsigyunseongu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSuwonsigyunseongu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //수원시 영통구
        final ArrayAdapter<CharSequence> arrayAdapterSuwonsiyeongtonggu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.suwonsiyeongtonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSuwonsiyeongtonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //용인시 기흥구
        final ArrayAdapter<CharSequence> arrayAdapterYonginsigiheonggu = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.yonginsigiheonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterYonginsigiheonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //의정부시
        final ArrayAdapter<CharSequence> arrayAdapterEuijeongbusi = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.euijeongbusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterEuijeongbusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //화성시
        final ArrayAdapter<CharSequence> arrayAdapterWhasungsi = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.whasungsi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterWhasungsi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //인천
        final ArrayAdapter<CharSequence> arrayAdapterIncheon = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.incheon, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterIncheon.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //제주
        final ArrayAdapter<CharSequence> arrayAdapterJeju = ArrayAdapter.createFromResource(MakerConditionCheck.this, R.array.jeju, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterJeju.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);


        spinnerMakerMeetingSiDo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedSido = sido[position];

                if (arrayAdapterSiDo.getItem(position).equals("서울")) {
                    spinnerMakerMeetingSiGuGun.setAdapter(arrayAdapterSeoul);

                    spinnerMakerMeetingSiGuGun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSigugun = seoul[position];

                            if (arrayAdapterSeoul.getItem(position).equals("강남구")) {
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterGangnamgu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gannamgu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강동구")) {//'강동구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterGangdonggu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangdonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강북구")) {//'강북구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterGangbukgu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangbukgu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강서구")) {//'강서구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterGangseogu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangseogu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("도봉구")) {//'도봉구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterDobonggu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = dobonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("동대문구")) {//'동대문구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterDongdaemungu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = dongdaemungu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("송파구")) {//'송파구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterSongpagu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = songpagu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerMakerMeetingSiGuGun.setSelection(0);
                        }
                    });
                } else if (arrayAdapterSiDo.getItem(position).equals("경기")) {//'경기' 선택했을 경우
                    spinnerMakerMeetingSiGuGun.setAdapter(arrayAdapterGyeonggi);

                    spinnerMakerMeetingSiGuGun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSigugun = gyeonggi[position];

                            if (arrayAdapterGyeonggi.getItem(position).equals("광명시")) {//'광명시' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterGyunagmyungsi);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gyunagmyungsi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            }
                            else if (arrayAdapterGyeonggi.getItem(position).equals("광주시")) {//'광주시' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterGyuangjusi);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gyuangjusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("김포시")) {//'김포시' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterGimposi);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gimposi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("남양주시")) {//'남양주시' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterNamyangjusi);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = namyangjusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("성남시 분당구")) {//'성남시 분당구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterSeongnamsibundanggu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = seongnamsibundanggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("성남시 수정구")) {//'성남시 분당구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterSeongnamsisujeonggu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = seongnamsisujeonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("수원시 권선구")) {//'수원시 권선구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterSuwonsigyunseongu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = suwonsigyunseongu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("수원시 영통구")) {//'수원시 영통구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterSuwonsiyeongtonggu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = suwonsiyeongtonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("용인시 기흥구")) {//'용인시 기흥구' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterYonginsigiheonggu);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = yonginsigiheonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("의정부시")) {//'의정부시' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterEuijeongbusi);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = euijeongbusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("화성시")) {//'의정부시' 선택했을 경우
                                spinnerMakerMeetingDongEupMyun.setAdapter(arrayAdapterWhasungsi);

                                spinnerMakerMeetingDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = whasungsi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerMeetingDongEupMyun.setSelection(0);
                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerMakerMeetingSiGuGun.setSelection(0);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {
                spinnerMakerMeetingSiDo.setSelection(0);
            }
        });

        ArrayAdapter<String> arrayAdapterFoodKind = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerfoodkind);
        spinnerMakerMeetingFoodKind.setAdapter(arrayAdapterFoodKind);

        spinnerMakerMeetingFoodKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                foodkind = spinnerfoodkind[position];
            }
            @Override
            public void onNothingSelected(AdapterView adapterView) {
            }
        });

        //이미지 변경 클릭
        imageButtonMakerConditionCheckProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });

        textViewMakerMeetingMeetingName.setText(meeting.get(meetingId).get("name").toString());
        //editTextMakerMeetingName.setText(user.get(userId).get("name").toString());
        editTextMakerMeetingName.setText(user.get(meeting.get(meetingId).get("leaderId").toString()).get("nickname").toString());
        editTextMakerMeetingName.setEnabled(false);

        //음식종류 불러오기

        //날짜 불러오기

        //나이 불러오기
        editTextMakerMeetingMinAge.setText(meeting.get(meetingId).get("minage").toString());
        editTextMakerMeetingMaxAge.setText(meeting.get(meetingId).get("maxage").toString());

        //시간 불러오기
        editTextMakerMeetingStartTime.setText(meeting.get(meetingId).get("startTime").toString());
        editTextMakerMeetingEndTime.setText(meeting.get(meetingId).get("endTime").toString());

        //정원 불러오기
        editTextMakerMeetingLimit.setText(meeting.get(meetingId).get("limit").toString());

        //소개글 불러오기
        editTextMakerMeetingIntroduce.setText(meeting.get(meetingId).get("explain").toString());

        //지역 불러오기

        //기간 불러오기
        if(meeting.get(meetingId).get("period").toString().equals("0")) {
            radioButtonMakerMeetingShort.setChecked(true);
        }
        else if(meeting.get(meetingId).get("period").toString().equals("1")) {
            radioButtonMakerMeetingMedium.setChecked(true);
        }
        else if(meeting.get(meetingId).get("period").toString().equals("2")) {
            radioButtonMakerMeetingLong.setChecked(true);
        }

        //성별 불러오기
        if(meeting.get(meetingId).get("gender").toString().equals("0")) {
            radioButtonMakerMeetingMale.setChecked(true);
        }
        else if(meeting.get(meetingId).get("gender").toString().equals("1")) {
            radioButtonMakerMeetingFemale.setChecked(true);
        }
        else if(meeting.get(meetingId).get("gender").toString().equals("2")) {
            radioButtonMakerMeetingNeutrality.setChecked(true);
        }

        //날짜 자동으로
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                buttonMakerMeetingDate.setText(date);
            }
        };

        //날짜선택
        //참고:https://bitsoul.tistory.com/18
        buttonMakerMeetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
                thread = new Thread() {
                    public void run() {
                        super.run();
                        while (true) {
                            try {
                                Thread.sleep(100);
                                handler.sendEmptyMessage(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                thread.start();
            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        //오늘 날짜 연월일로 나누기
        String todayDate = getDateString();
        String[] wordDate = todayDate.split("-");
        int y = Integer.parseInt(wordDate[0]);
        int m = Integer.parseInt(wordDate[1]);
        int d = Integer.parseInt(wordDate[2]);

        switch (id) {
            case DIALOG_DATE:
                DatePickerDialog dpd = new DatePickerDialog(MakerConditionCheck.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedYear = year;
                        selectedMonth = monthOfYear;
                        selectedDay = dayOfMonth;
                        if (monthOfYear + 1 < 10)
                            date = Integer.toString(year) + '0' + Integer.toString(monthOfYear + 1) + Integer.toString(dayOfMonth);
                        if (dayOfMonth < 10)
                            date = Integer.toString(year) + Integer.toString(monthOfYear + 1) + '0' + Integer.toString(dayOfMonth);
                        if (monthOfYear + 1 < 10 && dayOfMonth < 10)
                            date = Integer.toString(year) + '0' + Integer.toString(monthOfYear + 1) + '0' + Integer.toString(dayOfMonth);
                    }
                }, y, m-1, d); //기본값 연월일 설정
                return dpd;
        }
        return super.onCreateDialog(id);
    }

    //오늘 날짜 구하기
    //이거 month는 +1 해줘야되!
    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String str_date = df.format(new Date());

        return str_date;
    }

    public int makerFoodKindCheck(String foodKind) {
        int check = 0;

        if(foodKind.equals("전체"))
            check = 1;
        if(foodKind.equals("한식"))
            check = 2;
        if(foodKind.equals("분식"))
            check = 3;
        if(foodKind.equals("카페,디저트"))
            check = 4;
        if(foodKind.equals("돈까스,회,일식"))
            check = 5;
        if(foodKind.equals("치킨"))
            check = 6;
        if(foodKind.equals("피자"))
            check = 7;
        if(foodKind.equals("중국집"))
            check = 8;
        if(foodKind.equals("족발,보쌈"))
            check = 9;
        if(foodKind.equals("도시락"))
            check = 10;
        if(foodKind.equals("찜,탕"))
            check = 11;
        if(foodKind.equals("패스트푸드"))
            check = 12;

        return check;
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
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_changing_meeting_room, null);
        //actionbar에 있는 button
        final Button buttonCompleteMakingMeetingRoomCompleteActionBar = mCustomView.findViewById(R.id.buttonCompleteMakingMeetingRoomCompleteActionBar);

        buttonCompleteMakingMeetingRoomCompleteActionBar.setOnClickListener(new View.OnClickListener() {
            public void setUpdateInfo () {
                try{
                    updateStartTime = Integer.parseInt(editTextMakerMeetingStartTime.getText().toString());
                    updateEndTime = Integer.parseInt(editTextMakerMeetingEndTime.getText().toString());
                    updateMinAge = Integer.parseInt(editTextMakerMeetingMinAge.getText().toString());
                    updateMaxAge = Integer.parseInt(editTextMakerMeetingMaxAge.getText().toString());
                    updateLimit = Integer.parseInt(editTextMakerMeetingLimit.getText().toString());
                } catch (NumberFormatException e) {
                } catch (Exception e) {
                }

                radioGroupMakerMeetingGender.getCheckedRadioButtonId();
                if (radioButtonMakerMeetingMale.isChecked()) {
                    updateGender = 0;
                }
                if (radioButtonMakerMeetingFemale.isChecked()) {
                    updateGender = 1;
                }
                if (radioButtonMakerMeetingNeutrality.isChecked()) {
                    updateGender = 2;
                }

                radioGroupMakerMeetingPeriod.getCheckedRadioButtonId();
                if (radioButtonMakerMeetingShort.isChecked()) {
                    updatePeriod = 0;
                }
                if (radioButtonMakerMeetingMedium.isChecked()) {
                    updatePeriod = 1;
                }
                if (radioButtonMakerMeetingLong.isChecked()) {
                    updatePeriod = 2;
                }
                updateIntroduce = editTextMakerMeetingIntroduce.getText().toString();
                updateFoodKind = makerFoodKindCheck(foodkind);

                //오늘 날짜 나누기
                String todayDate = getDateString();
                String[] wordDate = todayDate.split("-");
                //오늘 날짜
                Calendar today = Calendar.getInstance();
                today.set(Calendar.YEAR, Integer.parseInt(wordDate[0]));
                today.set(Calendar.MONTH, Integer.parseInt(wordDate[1])-1);
                today.set(Calendar.DAY_OF_MONTH, Integer.parseInt(wordDate[2]));
                //선택한 날짜
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, selectedYear);
                selectedDate.set(Calendar.MONTH, selectedMonth);
                selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);

            }

            @Override
            public void onClick(View v) {
                setUpdateInfo();

                MainActivity.fireBaseManager.updateMeetingInfo(meetingId, updateIntroduce, SpecificMeetingActivity.leaderId, updatePeriod, selectedSido, selectedSigugun, selectedDongEupMyun, date, updateStartTime, updateEndTime, updateFoodKind, updateMinAge, updateMaxAge, updateGender, updateLimit);

                StorageReference riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com").child("meetingProfiles/" + meetingId + ".jpg");
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
                }
                //확인 버튼 누르면 메인스크린으로 이동
                //Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                //startActivity(intent);
                finish();
            }
        });

        ab.setCustomView(mCustomView);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            System.out.println(data.getData().toString());
            Glide.with(this)
                    .load(filePath)
                    .into(imageButtonMakerConditionCheckProfile);
        }
    }
}