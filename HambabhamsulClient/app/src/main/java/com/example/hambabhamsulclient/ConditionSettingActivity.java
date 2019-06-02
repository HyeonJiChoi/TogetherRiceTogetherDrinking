package com.example.hambabhamsulclient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ConditionSettingActivity extends AppCompatActivity {
    ActionBar ab;
    RadioGroup radioGroupSeekerPeriod;
    RadioButton radioButtonSeekerShort, radioButtonSeekerMedium, radioButtonSeekerLong, radioButtonSeekerPeriodNothing;
    RadioGroup radioGroupSeekerTime;
    RadioButton radioButtonSeekerMorning, radioButtonSeekerLunch, radioButtonSeekerNight, radioButtonSeekerTimeNothing;
    RadioGroup radioGroupSeekerEatingAmount;
    RadioButton radioButtonSeekerLittle, radioButtonSeekerProper, radioButtonSeekerMany;
    RadioGroup radioGroupSeekerEatingSpeed;
    RadioButton radioButtonSeekerSlow, radioButtonSeekerMiddle, radioButtonSeekerFast;
    RadioGroup radioGroupSeekerCharacter0;
    RadioButton radioButtonSeekerExtrovert, radioButtonSeekerIntrovert;
    RadioGroup radioGroupSeekerCharacter1;
    RadioButton radioButtonSeekerEmotional, radioButtonSeekerColdHearted;
    RadioGroup radioGroupSeekerCharacter2;
    RadioButton radioButtonSeeker2_1, radioButtonSeeker2_2;
    RadioGroup radioGroupSeekerCharacter3;
    RadioButton radioButtonSeeker3_1, radioButtonSeeker3_2;
    RadioGroup radioGroupSeekerCharacter4;
    RadioButton radioButtonSeeker4_1, radioButtonSeeker4_2;
    TextView textViewSeekerPeriod, textViewSeekerDate, textViewSeekerTime, textViewSeekerLocation, textViewEatingAmount, textViewEatingSpeed, textViewSeekerCharacter0, textViewSeekerCharacter1, textViewSeekerCharacter2, textViewSeekerCharacter3, textViewSeekerCharacter4;

    Button buttonSeekerDate;
    final int DIALOG_DATE = 1;

    String date;
    Thread thread = null;
    Handler handler = null;
    int selectedYear, selectedMonth, selectedDay;
    String selectedSido, selectedSigugun, selectedDongEupMyun;

    int period = -1;
    int time = -1;
    int amount = -1;
    int speed = -1;
    boolean char0 = false;
    boolean char1 = false;
    boolean char2 = false;
    boolean char3 = false;
    boolean char4 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condition_setting);

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
        final Spinner spinnerSeekerSiDo = (Spinner) findViewById(R.id.spinnerSeekerSiDo);
        final Spinner spinnerSeekerSiGuGun = (Spinner) findViewById(R.id.spinnerSeekerSiGuGun);
        final Spinner spinnerSeekerDongEupMyun = (Spinner) findViewById(R.id.spinnerSeekerDongEupMyun);

        buttonSeekerDate = (Button) findViewById(R.id.buttonSeekerDate);

        radioGroupSeekerPeriod = findViewById(R.id.radioGroupSeekerPeriod);
        radioButtonSeekerShort = findViewById(R.id.radioButtonSeekerShort);
        radioButtonSeekerMedium = findViewById(R.id.radioButtonSeekerMedium);
        radioButtonSeekerLong = findViewById(R.id.radioButtonSeekerLong);
        radioButtonSeekerPeriodNothing = findViewById(R.id.radioButtonSeekerPeriodNothing);

        radioGroupSeekerTime = findViewById(R.id.radioGroupSeekerTime);
        radioButtonSeekerMorning = findViewById(R.id.radioButtonSeekerMorning);
        radioButtonSeekerLunch = findViewById(R.id.radioButtonSeekerLunch);
        radioButtonSeekerNight = findViewById(R.id.radioButtonSeekerNight);
        radioButtonSeekerTimeNothing = findViewById(R.id.radioButtonSeekerTimeNothing);

        radioGroupSeekerEatingAmount = findViewById(R.id.radioGroupSeekerEatingAmount);
        radioButtonSeekerLittle = findViewById(R.id.radioButtonSeekerLittle);
        radioButtonSeekerProper = findViewById(R.id.radioButtonSeekerProper);
        radioButtonSeekerMany = findViewById(R.id.radioButtonSeekerMany);

        radioGroupSeekerEatingSpeed = findViewById(R.id.radioGroupSeekerEatingSpeed);
        radioButtonSeekerSlow = findViewById(R.id.radioButtonSeekerSlow);
        radioButtonSeekerMiddle = findViewById(R.id.radioButtonSeekerMiddle);
        radioButtonSeekerFast = findViewById(R.id.radioButtonSeekerFast);

        radioGroupSeekerCharacter0 = findViewById(R.id.radioGroupSeekerCharacter0);
        radioButtonSeekerExtrovert = findViewById(R.id.radioButtonSeekerExtrovert);
        radioButtonSeekerIntrovert = findViewById(R.id.radioButtonSeekerIntrovert);

        radioGroupSeekerCharacter1 = findViewById(R.id.radioGroupSeekerCharacter1);
        radioButtonSeekerEmotional = findViewById(R.id.radioButtonSeekerEmotional);
        radioButtonSeekerColdHearted = findViewById(R.id.radioButtonSeekerColdHearted);

        radioGroupSeekerCharacter2 = findViewById(R.id.radioGroupSeekerCharacter2);
        radioButtonSeeker2_1 = findViewById(R.id.radioButtonSeeker2_1);
        radioButtonSeeker2_2 = findViewById(R.id.radioButtonSeeker2_2);

        radioGroupSeekerCharacter3 = findViewById(R.id.radioGroupSeekerCharacter3);
        radioButtonSeeker3_1 = findViewById(R.id.radioButtonSeeker3_1);
        radioButtonSeeker3_2 = findViewById(R.id.radioButtonSeeker3_2);

        radioGroupSeekerCharacter4 = findViewById(R.id.radioGroupSeekerCharacter4);
        radioButtonSeeker4_1 = findViewById(R.id.radioButtonSeeker4_1);
        radioButtonSeeker4_2 = findViewById(R.id.radioButtonSeeker4_2);

        textViewSeekerPeriod = findViewById(R.id.textViewSeekerPeriod);
        textViewSeekerDate = findViewById(R.id.textViewSeekerDate);
        textViewSeekerTime = findViewById(R.id.textViewSeekerTime);
        textViewSeekerLocation = findViewById(R.id.textViewSeekerLocation);
        textViewEatingAmount = findViewById(R.id.textViewEatingAmount);
        textViewEatingSpeed = findViewById(R.id.textViewEatingSpeed);
        textViewSeekerCharacter0 = findViewById(R.id.textViewSeekerCharacter0);
        textViewSeekerCharacter1 = findViewById(R.id.textViewSeekerCharacter1);
        textViewSeekerCharacter2 = findViewById(R.id.textViewSeekerCharacter2);
        textViewSeekerCharacter3 = findViewById(R.id.textViewSeekerCharacter3);
        textViewSeekerCharacter4 = findViewById(R.id.textViewSeekerCharacter4);

        Button buttonSeekerNext = (Button) findViewById(R.id.buttonSeekerNext);

        //어떻게 조건 설정해야되는지 알려주기
        //기간
        textViewSeekerPeriod.setOnLongClickListener(new TextView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                periodDescription();
                return true;
            }
        });
        //시간
        textViewSeekerTime.setOnLongClickListener(new TextView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                timeDescription();
                return true;
            }
        });


        //TODO: Spinner 마무리 해야됨
        //(시/도)
        final ArrayAdapter<String> arrayAdapterSiDo = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, sido);
        spinnerSeekerSiDo.setAdapter(arrayAdapterSiDo);
        //(시/구/군)
        String[] sigugun = {"(시/구/군)"};
        final ArrayAdapter<String> arrayAdapterSiGuGun = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, sigugun);
        spinnerSeekerSiGuGun.setAdapter(arrayAdapterSiGuGun);
        //(동/읍/면)
        String[] dongeupmyun = {"(동/읍/면)"};
        final ArrayAdapter<String> arrayAdapterDongEupMyun = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, dongeupmyun);
        spinnerSeekerDongEupMyun.setAdapter(arrayAdapterDongEupMyun);

        //서울
        final ArrayAdapter<CharSequence> arrayAdapterSeoul = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.seoul, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeoul.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강남구
        final ArrayAdapter<CharSequence> arrayAdapterGangnamgu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.gangnamgu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangnamgu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강동구
        final ArrayAdapter<CharSequence> arrayAdapterGangdonggu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.gangdonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangdonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강북구
        final ArrayAdapter<CharSequence> arrayAdapterGangbukgu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.gangbukgu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangbukgu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강서구
        final ArrayAdapter<CharSequence> arrayAdapterGangseogu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.gangseogu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangseogu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //도봉구
        final ArrayAdapter<CharSequence> arrayAdapterDobonggu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.dobonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterDobonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //동대문구
        final ArrayAdapter<CharSequence> arrayAdapterDongdaemungu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.dongdaemungu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterDongdaemungu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //송파구
        final ArrayAdapter<CharSequence> arrayAdapterSongpagu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.songpagu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSongpagu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);

        //경기
        final ArrayAdapter<CharSequence> arrayAdapterGyeonggi = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.gyeonggi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyeonggi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //광명시
        final ArrayAdapter<CharSequence> arrayAdapterGyunagmyungsi = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.gyunagmyungsi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyunagmyungsi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //광주시
        final ArrayAdapter<CharSequence> arrayAdapterGyuangjusi = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.gyuangjusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyuangjusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //김포시
        final ArrayAdapter<CharSequence> arrayAdapterGimposi = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.gimposi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGimposi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //남양주시
        final ArrayAdapter<CharSequence> arrayAdapterNamyangjusi = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.namyangjusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterNamyangjusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //성남시 분당구
        final ArrayAdapter<CharSequence> arrayAdapterSeongnamsibundanggu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.seongnamsibundanggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeongnamsibundanggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //성남시 수정구
        final ArrayAdapter<CharSequence> arrayAdapterSeongnamsisujeonggu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.seongnamsisujeonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeongnamsisujeonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //수원시 권선구
        final ArrayAdapter<CharSequence> arrayAdapterSuwonsigyunseongu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.suwonsigyunseongu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSuwonsigyunseongu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //수원시 영통구
        final ArrayAdapter<CharSequence> arrayAdapterSuwonsiyeongtonggu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.suwonsiyeongtonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSuwonsiyeongtonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //용인시 기흥구
        final ArrayAdapter<CharSequence> arrayAdapterYonginsigiheonggu = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.yonginsigiheonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterYonginsigiheonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //의정부시
        final ArrayAdapter<CharSequence> arrayAdapterEuijeongbusi = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.euijeongbusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterEuijeongbusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //화성시
        final ArrayAdapter<CharSequence> arrayAdapterWhasungsi = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.whasungsi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterWhasungsi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //인천
        final ArrayAdapter<CharSequence> arrayAdapterIncheon = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.incheon, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterIncheon.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //제주
        final ArrayAdapter<CharSequence> arrayAdapterJeju = ArrayAdapter.createFromResource(ConditionSettingActivity.this, R.array.jeju, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterJeju.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);


        spinnerSeekerSiDo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedSido = sido[position];

                if (arrayAdapterSiDo.getItem(position).equals("서울")) {
                    spinnerSeekerSiGuGun.setAdapter(arrayAdapterSeoul);

                    spinnerSeekerSiGuGun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSigugun = seoul[position];

                            if (arrayAdapterSeoul.getItem(position).equals("강남구")) {
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterGangnamgu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gannamgu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강동구")) {//'강동구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterGangdonggu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangdonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강북구")) {//'강북구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterGangbukgu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangbukgu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강서구")) {//'강서구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterGangseogu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangseogu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("도봉구")) {//'도봉구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterDobonggu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = dobonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("동대문구")) {//'동대문구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterDongdaemungu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = dongdaemungu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("송파구")) {//'송파구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterSongpagu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = songpagu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerSeekerSiGuGun.setSelection(0);
                        }
                    });
                } else if (arrayAdapterSiDo.getItem(position).equals("경기")) {//'경기' 선택했을 경우
                    spinnerSeekerSiGuGun.setAdapter(arrayAdapterGyeonggi);

                    spinnerSeekerSiGuGun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSigugun = gyeonggi[position];

                            if (arrayAdapterGyeonggi.getItem(position).equals("광명시")) {//'광명시' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterGyunagmyungsi);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gyunagmyungsi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            }
                            else if (arrayAdapterGyeonggi.getItem(position).equals("광주시")) {//'광주시' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterGyuangjusi);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gyuangjusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("김포시")) {//'김포시' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterGimposi);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gimposi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("남양주시")) {//'남양주시' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterNamyangjusi);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = namyangjusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("성남시 분당구")) {//'성남시 분당구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterSeongnamsibundanggu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = seongnamsibundanggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("성남시 수정구")) {//'성남시 분당구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterSeongnamsisujeonggu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = seongnamsisujeonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("수원시 권선구")) {//'수원시 권선구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterSuwonsigyunseongu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = suwonsigyunseongu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("수원시 영통구")) {//'수원시 영통구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterSuwonsiyeongtonggu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = suwonsiyeongtonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("용인시 기흥구")) {//'용인시 기흥구' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterYonginsigiheonggu);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = yonginsigiheonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("의정부시")) {//'의정부시' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterEuijeongbusi);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = euijeongbusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("화성시")) {//'의정부시' 선택했을 경우
                                spinnerSeekerDongEupMyun.setAdapter(arrayAdapterWhasungsi);

                                spinnerSeekerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = whasungsi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerSeekerDongEupMyun.setSelection(0);
                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerSeekerSiGuGun.setSelection(0);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {
                spinnerSeekerSiDo.setSelection(0);
            }
        });

        //날짜 자동으로
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                buttonSeekerDate.setText(date);
            }
        };

        //날짜선택
        //참고:https://bitsoul.tistory.com/18
        buttonSeekerDate.setOnClickListener(new View.OnClickListener() {
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

        //'다음' 화면으로 넘어가는 버튼 클릭 - Fragment : 모임 종류들 보여주는 화면
        buttonSeekerNext.setOnClickListener(new View.OnClickListener() {

            public int checkCondition() {
                int checkAllCondition = 0;
                int regionCheck = 0;
                int dateCheck = 0;
                int periodCheck = 0;
                int timeCheck = 0;
                int amountCheck = 0;
                int speedCheck = 0;
                int char0Check = 0;
                int char1Check = 0;
                int char2Check = 0;
                int char3Check = 0;
                int char4Check = 0;

                //오늘 날짜 나누기
                String todayDate = getDateString();
                String[] wordDate = todayDate.split("-");
                //오늘 날짜
                Calendar today = Calendar.getInstance();
                today.set(Calendar.YEAR, Integer.parseInt(wordDate[0]));
                today.set(Calendar.MONTH, Integer.parseInt(wordDate[1]) - 1);
                today.set(Calendar.DAY_OF_MONTH, Integer.parseInt(wordDate[2]));
                //선택한 날짜
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, selectedYear);
                selectedDate.set(Calendar.MONTH, selectedMonth);
                selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                //성격4 선택 안됐을 경우
                radioGroupSeekerCharacter4.getCheckedRadioButtonId();
                if (radioButtonSeeker4_1.isChecked()) {
                    char4 = true; //"emotional";
                    char4Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeeker4_2.isChecked()) {
                    char4 = false; //"coldhearted";
                    char4Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeeker4_1.isChecked() == false && radioButtonSeeker4_2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(성급한, 여유로운) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    char4Check = -1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격3 선택 안됐을 경우
                radioGroupSeekerCharacter3.getCheckedRadioButtonId();
                if (radioButtonSeeker3_1.isChecked()) {
                    char3 = true; //"emotional";
                    char3Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeeker3_2.isChecked()) {
                    char3 = false; //"coldhearted";
                    char3Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeeker3_1.isChecked() == false && radioButtonSeeker3_2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(장난스러운, 진중한) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    char3Check = -1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격2 선택 안됐을 경우
                radioGroupSeekerCharacter2.getCheckedRadioButtonId();
                if (radioButtonSeeker2_1.isChecked()) {
                    char2 = true; //"emotional";
                    char2Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeeker2_2.isChecked()) {
                    char2 = false; //"coldhearted";
                    char2Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeeker2_1.isChecked() == false && radioButtonSeeker2_2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(평범한, 개성있는) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    char2Check = -1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격1 선택 안됐을 경우
                radioGroupSeekerCharacter1.getCheckedRadioButtonId();
                if (radioButtonSeekerEmotional.isChecked()) {
                    char1 = true; //"emotional";
                    char1Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerColdHearted.isChecked()) {
                    char1 = false; //"coldhearted";
                    char1Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerEmotional.isChecked() == false && radioButtonSeekerColdHearted.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(감정적인, 이성적인) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    char1Check = -1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#ff0000"));
                }
                //성격0 선택 안됐을 경우
                radioGroupSeekerCharacter0.getCheckedRadioButtonId();
                if (radioButtonSeekerExtrovert.isChecked()) {
                    char0 = true; //"extrovert";
                    char0Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerIntrovert.isChecked()) {
                    char0 = false;//"introvert";
                    char0Check = 1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerExtrovert.isChecked() == false && radioButtonSeekerIntrovert.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성격(외향적인, 내향적인) 중에 하나 골라주세요!", Toast.LENGTH_SHORT).show();
                    char0Check = -1;
                    textViewSeekerCharacter0.setTextColor(Color.parseColor("#ff0000"));
                }
                //속도 선택 x
                //밥 먹는 속도
                radioGroupSeekerEatingSpeed.getCheckedRadioButtonId();
                if (radioButtonSeekerSlow.isChecked()) {
                    speed = 0;
                    speedCheck = 1;
                    textViewEatingSpeed.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerMiddle.isChecked()) {
                    speed = 1;
                    speedCheck = 1;
                    textViewEatingSpeed.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerFast.isChecked()) {
                    speed = 2;
                    speedCheck = 1;
                    textViewEatingSpeed.setTextColor(Color.parseColor("#000000"));
                }
                if (speed == -1) {
                    Toast.makeText(getApplicationContext(), "모임 성격 '밥 먹는 속도'를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    speedCheck = -1;
                    textViewEatingSpeed.setTextColor(Color.parseColor("#ff0000"));
                }
                //밥 먹는 양
                radioGroupSeekerEatingAmount.getCheckedRadioButtonId();
                if (radioButtonSeekerLittle.isChecked()) {
                    amount = 0;
                    amountCheck = 1;
                    textViewEatingAmount.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerProper.isChecked()) {
                    amount = 1;
                    amountCheck = 1;
                    textViewEatingAmount.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerMany.isChecked()) {
                    amount = 2;
                    amountCheck = 1;
                    textViewEatingAmount.setTextColor(Color.parseColor("#000000"));
                }
                //양 선택 x
                if (amount == -1) {
                    Toast.makeText(getApplicationContext(), "모임 성격 '밥 먹는 양'을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    amountCheck = -1;
                    textViewEatingAmount.setTextColor(Color.parseColor("#ff0000"));
                }
                //지역 선택 x
                if (spinnerSeekerSiDo.getSelectedItem().equals("(시/도)")) {
                    Toast.makeText(getApplicationContext(), "(시/도)를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    regionCheck = -1;
                    textViewSeekerLocation.setTextColor(Color.parseColor("#ff0000"));
                } else if (spinnerSeekerSiGuGun.getSelectedItem().equals("(시/구/군)")) {
                    Toast.makeText(getApplicationContext(), "(시/구/군)을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    regionCheck = -1;
                    textViewSeekerLocation.setTextColor(Color.parseColor("#ff0000"));
                } else if (spinnerSeekerDongEupMyun.getSelectedItem().equals("(동/읍/면)")) {
                    Toast.makeText(getApplicationContext(), "(동/읍/면)을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    regionCheck = -1;
                    textViewSeekerLocation.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    regionCheck = 1;
                    textViewSeekerLocation.setTextColor(Color.parseColor("#000000"));
                }
                //시간
                radioGroupSeekerTime.getCheckedRadioButtonId();
                if (radioButtonSeekerMorning.isChecked()) {
                    time = 0;
                    timeCheck = 1;
                    textViewSeekerTime.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerLunch.isChecked()) {
                    time = 1;
                    timeCheck = 1;
                    textViewSeekerTime.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerNight.isChecked()) {
                    time = 2;
                    timeCheck = 1;
                    textViewSeekerTime.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerTimeNothing.isChecked()) {
                    time = 3;
                    timeCheck = 1;
                    textViewSeekerTime.setTextColor(Color.parseColor("#000000"));
                }
                //시간 선택 x
                if (time == -1) {
                    Toast.makeText(getApplicationContext(), "'시간'을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    timeCheck = -1;
                    textViewSeekerTime.setTextColor(Color.parseColor("#ff0000"));
                }
                //날짜 선택 x
                if (buttonSeekerDate.getText().equals("날짜 선택") || buttonSeekerDate.getText().equals(null)) {   //날짜가 선택 안 되었을 경우
                    Toast.makeText(getApplicationContext(), "날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    dateCheck = -1;
                    textViewSeekerDate.setTextColor(Color.parseColor("#ff0000"));
                } else if (today.compareTo(selectedDate) >= 1) { //오늘 날짜와 선택한 날짜 비교
                    Toast.makeText(getApplicationContext(), "날짜가 오늘(" + wordDate[0] + "-" + "0" + (Integer.parseInt(wordDate[1])) + "-" + wordDate[2] + ")보다 빨라요! 오늘보다 나중날짜를 선택해주세요!", Toast.LENGTH_LONG).show();
                    dateCheck = -1;
                    textViewSeekerDate.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    dateCheck = 1;
                    textViewSeekerDate.setTextColor(Color.parseColor("#000000"));
                }
                //기간
                radioGroupSeekerPeriod.getCheckedRadioButtonId();
                if (radioButtonSeekerShort.isChecked()) {
                    period = 0;
                    periodCheck = 1;
                    textViewSeekerPeriod.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerMedium.isChecked()) {
                    period = 1;
                    periodCheck = 1;
                    textViewSeekerPeriod.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerLong.isChecked()) {
                    period = 2;
                    periodCheck = 1;
                    textViewSeekerPeriod.setTextColor(Color.parseColor("#000000"));
                }
                if (radioButtonSeekerPeriodNothing.isChecked()) {
                    period = 3;
                    periodCheck = 1;
                    textViewSeekerPeriod.setTextColor(Color.parseColor("#000000"));
                }
                //기간 선택 x
                if (period == -1) {
                    Toast.makeText(getApplicationContext(), "'기간'을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    periodCheck = -1;
                    textViewSeekerPeriod.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    periodCheck = 1;
                    textViewSeekerDate.setTextColor(Color.parseColor("#000000"));
                }

                //전체 조건 확인
                if (regionCheck == 1 && dateCheck == 1 && periodCheck == 1 && timeCheck == 1 && amountCheck == 1 && speedCheck == 1 && char0Check == 1 && char1Check == 1 && char2Check == 1 && char3Check == 1 && char4Check == 1)
                    checkAllCondition = 1;
                else
                    checkAllCondition = -1;

                return checkAllCondition;
            }

            //체크 다 하면 전송
            @Override
            public void onClick(View v) {
                //위의 조건들이 모두 체크되어야지만 다음 화면으로 넘어가기
                if (checkCondition() == 1) {
                    //데이터 전송 - intent 사용
                    //인텐트를 보내기 위해 새로운 인텐트를 만듦
                    Intent conditionSendIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("period", period);
                    bundle.putString("date", date);
                    bundle.putInt("time", time);
                    bundle.putInt("amount", amount);
                    bundle.putInt("speed", speed);
                    bundle.putBoolean("char0", char0);
                    bundle.putBoolean("char1", char1);
                    bundle.putBoolean("char2", char2);
                    bundle.putBoolean("char3", char3);
                    bundle.putBoolean("char4", char4);
                    bundle.putString("sido", selectedSido);
                    bundle.putString("sigugun", selectedSigugun);
                    if (selectedDongEupMyun.equals("전체")) {
                        bundle.putString("dongeupmyun", null);
                    } else {
                        bundle.putString("dongeupmyun", selectedDongEupMyun);
                    }
                    conditionSendIntent.putExtras(bundle);

                    //어느 프래그먼트로 이동할지, id를 받기 위해서 인텐트를 받음
                    Intent fragmentSelectedIntent = getIntent();

                    //상황에 맞게 배달
                    if (fragmentSelectedIntent.getExtras().get("id").equals("firstCondition"))
                        setResult(123, conditionSendIntent); //새로운 인텐트와 result를 보내 상황을 달리함
                    else if (fragmentSelectedIntent.getExtras().get("id").equals("meetingList"))
                        setResult(456, conditionSendIntent);

                    finish();
                }
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
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_condition_setting, null);
        ab.setCustomView(mCustomView);
    }

    public void periodDescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("<기간>");
        builder.setMessage("단기: 1일\n중기: 1주일 ~ 1개월\n장기: 1개월 ~ 3개월");
        builder.setNegativeButton("닫기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    public void timeDescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("<시간>");
        builder.setMessage("아침: 6시 ~ 10시\n점심: 10시 ~ 17시\n저녁: 16시 이후");
        builder.setNegativeButton("닫기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
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
                DatePickerDialog dpd = new DatePickerDialog(ConditionSettingActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                }, y, m - 1, d); //기본값 연월일 설정
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
}