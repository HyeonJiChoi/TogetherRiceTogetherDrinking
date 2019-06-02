package com.example.hambabhamsulclient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MakerConditionSetting extends AppCompatActivity {
    ActionBar ab;
    static String meetingId = ""; //미팅아이디 설정 해줘야함.
    private Uri filePath; //그림 이동로
    ImageButton imageViewMakerConditionSettingProfile;
    RadioGroup radioGroupMakerPeriod;
    RadioButton radioButtonMakerShort;
    RadioButton radioButtonMakerMedium;
    RadioButton radioButtonMakerLong;

    EditText editTextMakerStartTime;
    EditText editTextMakerEndTime;

    EditText editTextMakerMinAge;
    EditText editTextMakerMaxAge;

    RadioGroup radioGroupMakerGender;
    RadioButton radioButtonMakerMale;
    RadioButton radioButtonMakerFemale;
    RadioButton radioButtonMakerNeutrality;

    EditText editTextMakerLimit;
    EditText editTextMakerMeetingName;
    EditText editTextMakerIntroduce;

    TextView textViewMakerDate;
    TextView textViewMakerLocation;
    TextView textViewMakerPeriod;
    TextView textViewMakerMeetingName;
    TextView textViewMakerTime;
    TextView textViewMakerGender;
    TextView textViewMakerAge;
    TextView textViewMakerLimit;

    Button buttonMakerDate;
    Button buttonMakerMake;

    String makerIntroduce;
    String makerMeetingName;
    String makerLeaderId;
    int makerPeriod = -1;
    String makerSido;
    String makerSigugun;
    String makerDongEupMyun;
    int makerStartTime;
    int makerEndTime;
    int makerFoodKind;
    int makerMinAge;
    int makerMaxAge;
    int makerGender = -1;
    int makerLimit;

    String date;
    Thread thread = null;
    Handler handler = null;
    int selectedYear;
    int selectedMonth;
    int selectedDay;

    String selectedSido, selectedSigugun, selectedDongEupMyun;

    String foodKind;
    String[] foodkind = {"전체", "한식", "분식", "카페,디저트", "돈까스,회,일식", "치킨", "피자", "중국집", "족발,보쌈", "도시락", "찜,탕", "패스트푸드"};

    final int DIALOG_DATE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_condition_setting);

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
        final Spinner spinnerMakerSiDo = (Spinner) findViewById(R.id.spinnerMakerSiDo);
        final Spinner spinnerMakerSiGuGun = (Spinner) findViewById(R.id.spinnerMakerSiGuGun);
        final Spinner spinnerMakerDongEupMyun = (Spinner) findViewById(R.id.spinnerMakerDongEupMyun);


        imageViewMakerConditionSettingProfile = findViewById(R.id.imageViewMakerConditionSettingProfile);
        Spinner spinnerMakerFoodKind = (Spinner)findViewById(R.id.spinnerMakerFoodKind);

        radioGroupMakerPeriod = findViewById(R.id.radioGroupMakerPeriod);
        radioButtonMakerShort = findViewById(R.id.radioButtonMakerShort);
        radioButtonMakerMedium = findViewById(R.id.radioButtonMakerMedium);
        radioButtonMakerLong = findViewById(R.id.radioButtonMakerLong);

        editTextMakerStartTime = findViewById(R.id.editTextMakerStartTime);
        editTextMakerEndTime = findViewById(R.id.editTextMakerEndTime);

        buttonMakerDate = (Button)findViewById(R.id.buttonMakerDate);
        buttonMakerMake = (Button)findViewById(R.id.buttonMakerMake);

        editTextMakerMinAge = findViewById(R.id.editTextMakerMinAge);
        editTextMakerMaxAge = findViewById(R.id.editTextMakerMaxAge);

        radioGroupMakerGender = findViewById(R.id.radioGroupMakerGender);
        radioButtonMakerMale = findViewById(R.id.radioButtonMakerMale);
        radioButtonMakerFemale = findViewById(R.id.radioButtonMakerFemale);
        radioButtonMakerNeutrality = findViewById(R.id.radioButtonMakerNeutrality);

        editTextMakerLimit = findViewById(R.id.editTextMakerLimit);
        editTextMakerMeetingName = findViewById(R.id.editTextMakerMeetingName);
        editTextMakerIntroduce = findViewById(R.id.editTextMakerIntroduce);
        textViewMakerDate = findViewById(R.id.textViewMakerDate);
        textViewMakerLocation = findViewById(R.id.textViewMakerLocation);
        textViewMakerPeriod = findViewById(R.id.textViewMakerPeriod);
        textViewMakerMeetingName = findViewById(R.id.textViewMakerMeetingName);
        textViewMakerTime = findViewById(R.id.textViewMakerTime);
        textViewMakerGender = findViewById(R.id.textViewMakerGender);
        textViewMakerAge = findViewById(R.id.textViewMakerAge);
        textViewMakerLimit = findViewById(R.id.textViewMakerLimit);

        //어떻게 조건 설정해야되는지 알려주기
        //기간
        textViewMakerPeriod.setOnLongClickListener(new TextView.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                periodDescription();
                return true;
            }
        });
        //시간
        textViewMakerTime.setOnLongClickListener(new TextView.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                timeDescription();
                return true;
            }
        });

        //(시/도)
        final ArrayAdapter<String> arrayAdapterSiDo = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, sido);
        spinnerMakerSiDo.setAdapter(arrayAdapterSiDo);
        //(시/구/군)
        String[] sigugun = {"(시/구/군)"};
        final ArrayAdapter<String> arrayAdapterSiGuGun = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, sigugun);
        spinnerMakerSiGuGun.setAdapter(arrayAdapterSiGuGun);
        //(동/읍/면)
        String[] dongeupmyun = {"(동/읍/면)"};
        final ArrayAdapter<String> arrayAdapterDongEupMyun = new ArrayAdapter<>(this, R.layout.custom_simple_dropdown_item_1line, dongeupmyun);
        spinnerMakerDongEupMyun.setAdapter(arrayAdapterDongEupMyun);

        //서울
        final ArrayAdapter<CharSequence> arrayAdapterSeoul = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.seoul, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeoul.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강남구
        final ArrayAdapter<CharSequence> arrayAdapterGangnamgu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.gangnamgu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangnamgu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강동구
        final ArrayAdapter<CharSequence> arrayAdapterGangdonggu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.gangdonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangdonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강북구
        final ArrayAdapter<CharSequence> arrayAdapterGangbukgu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.gangbukgu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangbukgu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //강서구
        final ArrayAdapter<CharSequence> arrayAdapterGangseogu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.gangseogu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGangseogu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //도봉구
        final ArrayAdapter<CharSequence> arrayAdapterDobonggu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.dobonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterDobonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //동대문구
        final ArrayAdapter<CharSequence> arrayAdapterDongdaemungu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.dongdaemungu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterDongdaemungu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //송파구
        final ArrayAdapter<CharSequence> arrayAdapterSongpagu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.songpagu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSongpagu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);

        //경기
        final ArrayAdapter<CharSequence> arrayAdapterGyeonggi = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.gyeonggi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyeonggi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //광명시
        final ArrayAdapter<CharSequence> arrayAdapterGyunagmyungsi = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.gyunagmyungsi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyunagmyungsi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //광주시
        final ArrayAdapter<CharSequence> arrayAdapterGyuangjusi = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.gyuangjusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGyuangjusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //김포시
        final ArrayAdapter<CharSequence> arrayAdapterGimposi = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.gimposi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterGimposi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //남양주시
        final ArrayAdapter<CharSequence> arrayAdapterNamyangjusi = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.namyangjusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterNamyangjusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //성남시 분당구
        final ArrayAdapter<CharSequence> arrayAdapterSeongnamsibundanggu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.seongnamsibundanggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeongnamsibundanggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //성남시 수정구
        final ArrayAdapter<CharSequence> arrayAdapterSeongnamsisujeonggu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.seongnamsisujeonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSeongnamsisujeonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //수원시 권선구
        final ArrayAdapter<CharSequence> arrayAdapterSuwonsigyunseongu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.suwonsigyunseongu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSuwonsigyunseongu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //수원시 영통구
        final ArrayAdapter<CharSequence> arrayAdapterSuwonsiyeongtonggu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.suwonsiyeongtonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterSuwonsiyeongtonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //용인시 기흥구
        final ArrayAdapter<CharSequence> arrayAdapterYonginsigiheonggu = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.yonginsigiheonggu, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterYonginsigiheonggu.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //의정부시
        final ArrayAdapter<CharSequence> arrayAdapterEuijeongbusi = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.euijeongbusi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterEuijeongbusi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //화성시
        final ArrayAdapter<CharSequence> arrayAdapterWhasungsi = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.whasungsi, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterWhasungsi.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //인천
        final ArrayAdapter<CharSequence> arrayAdapterIncheon = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.incheon, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterIncheon.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);
        //제주
        final ArrayAdapter<CharSequence> arrayAdapterJeju = ArrayAdapter.createFromResource(MakerConditionSetting.this, R.array.jeju, R.layout.custom_simple_dropdown_item_1line);
        arrayAdapterJeju.setDropDownViewResource(R.layout.custom_simple_dropdown_item_1line);


        spinnerMakerSiDo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedSido = sido[position];

                if (arrayAdapterSiDo.getItem(position).equals("서울")) {
                    spinnerMakerSiGuGun.setAdapter(arrayAdapterSeoul);

                    spinnerMakerSiGuGun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSigugun = seoul[position];

                            if (arrayAdapterSeoul.getItem(position).equals("강남구")) {
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterGangnamgu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gannamgu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강동구")) {//'강동구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterGangdonggu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangdonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강북구")) {//'강북구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterGangbukgu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangbukgu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("강서구")) {//'강서구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterGangseogu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gangseogu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("도봉구")) {//'도봉구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterDobonggu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = dobonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("동대문구")) {//'동대문구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterDongdaemungu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = dongdaemungu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterSeoul.getItem(position).equals("송파구")) {//'송파구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterSongpagu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = songpagu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerMakerSiGuGun.setSelection(0);
                        }
                    });
                } else if (arrayAdapterSiDo.getItem(position).equals("경기")) {//'경기' 선택했을 경우
                    spinnerMakerSiGuGun.setAdapter(arrayAdapterGyeonggi);

                    spinnerMakerSiGuGun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSigugun = gyeonggi[position];

                            if (arrayAdapterGyeonggi.getItem(position).equals("광명시")) {//'광명시' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterGyunagmyungsi);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gyunagmyungsi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            }
                            else if (arrayAdapterGyeonggi.getItem(position).equals("광주시")) {//'광주시' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterGyuangjusi);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gyuangjusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("김포시")) {//'김포시' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterGimposi);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = gimposi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("남양주시")) {//'남양주시' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterNamyangjusi);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = namyangjusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("성남시 분당구")) {//'성남시 분당구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterSeongnamsibundanggu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = seongnamsibundanggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("성남시 수정구")) {//'성남시 분당구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterSeongnamsisujeonggu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = seongnamsisujeonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("수원시 권선구")) {//'수원시 권선구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterSuwonsigyunseongu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = suwonsigyunseongu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("수원시 영통구")) {//'수원시 영통구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterSuwonsiyeongtonggu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = suwonsiyeongtonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("용인시 기흥구")) {//'용인시 기흥구' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterYonginsigiheonggu);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = yonginsigiheonggu[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("의정부시")) {//'의정부시' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterEuijeongbusi);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = euijeongbusi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            } else if (arrayAdapterGyeonggi.getItem(position).equals("화성시")) {//'의정부시' 선택했을 경우
                                spinnerMakerDongEupMyun.setAdapter(arrayAdapterWhasungsi);

                                spinnerMakerDongEupMyun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedDongEupMyun = whasungsi[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMakerDongEupMyun.setSelection(0);
                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerMakerSiGuGun.setSelection(0);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {
                spinnerMakerSiDo.setSelection(0);
            }
        });

        ArrayAdapter<String> arrayAdapterFoodKind = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, foodkind);
        spinnerMakerFoodKind.setAdapter(arrayAdapterFoodKind);

        //이미지 변경 클릭
        imageViewMakerConditionSettingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });

        spinnerMakerFoodKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                foodKind = foodkind[position];
            }
            @Override
            public void onNothingSelected(AdapterView adapterView) {
            }
        });

        //날짜 자동으로
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                buttonMakerDate.setText(date);
            }
        };

        //날짜선택
        //참고:https://bitsoul.tistory.com/18
        buttonMakerDate.setOnClickListener(new View.OnClickListener() {
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

        //'만들기' 화면으로 넘어가는 버튼 클릭 - Fragment : 모임 종류들 보여주는 화면
        buttonMakerMake.setOnClickListener(new View.OnClickListener(){
            public int checkCondition() {
                int checkAllCondition = 0;
                int regionCheck = 0;
                int dateCheck = 0;
                int periodCheck = 0;
                int nameCheck = 0;
                int timeCheck = 0;
                int ageCheck = 0;
                int genderCheck = 0;
                int limitCheck = 0;

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

                //정원 입력 안했을 경우
                if(editTextMakerLimit.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "정원을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    limitCheck = -1;
                    textViewMakerLimit.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    limitCheck = 1;
                    textViewMakerLimit.setTextColor(Color.parseColor("#696969"));
                }
                //성별
                radioGroupMakerGender.getCheckedRadioButtonId();
                if (radioButtonMakerMale.isChecked()) {
                    makerGender = 0;
                    genderCheck = 1;
                    textViewMakerGender.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonMakerFemale.isChecked()) {
                    makerGender = 1;
                    genderCheck = 1;
                    textViewMakerGender.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonMakerNeutrality.isChecked()) {
                    makerGender = 2;
                    genderCheck = 1;
                    textViewMakerGender.setTextColor(Color.parseColor("#696969"));
                }
                //성별 입력 안했을 경우
                if (makerGender == -1) {
                    Toast.makeText(getApplicationContext(), "'성별'을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    genderCheck = -1;
                    textViewMakerGender.setTextColor(Color.parseColor("#ff0000"));
                }
                //나이 입력 안했을 경우
                if(editTextMakerMinAge.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "최소 나이를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    ageCheck = -1;
                    textViewMakerAge.setTextColor(Color.parseColor("#ff0000"));
                }
                if(editTextMakerMaxAge.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "최대 나이를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    ageCheck = -1;
                    textViewMakerAge.setTextColor(Color.parseColor("#ff0000"));
                }
                else {
                    ageCheck = 1;
                    textViewMakerAge.setTextColor(Color.parseColor("#696969"));
                }
                //지역 입력 안했을 경우
                if (spinnerMakerSiDo.getSelectedItem().equals("(시/도)")) {
                    Toast.makeText(getApplicationContext(), "(시/도)를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    regionCheck = -1;
                    textViewMakerLocation.setTextColor(Color.parseColor("#ff0000"));
                } else if (spinnerMakerSiGuGun.getSelectedItem().equals("(시/구/군)")) {
                    Toast.makeText(getApplicationContext(), "(시/구/군)을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    regionCheck = -1;
                    textViewMakerLocation.setTextColor(Color.parseColor("#ff0000"));
                } else if (spinnerMakerDongEupMyun.getSelectedItem().equals("(동/읍/면)")) {
                    Toast.makeText(getApplicationContext(), "(동/읍/면)을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    regionCheck = -1;
                    textViewMakerLocation.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    regionCheck = 1;
                    textViewMakerLocation.setTextColor(Color.parseColor("#696969"));
                }
                //시간 입력 안했을 경우
                if(editTextMakerStartTime.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "최소 시간을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    timeCheck = -1;
                    textViewMakerTime.setTextColor(Color.parseColor("#ff0000"));
                }
                if(editTextMakerEndTime.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "최대 시간을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    timeCheck = -1;
                    textViewMakerTime.setTextColor(Color.parseColor("#ff0000"));
                }
                else {
                    timeCheck = 1;
                    textViewMakerTime.setTextColor(Color.parseColor("#696969"));
                }
                //날짜 입력 안했을 경우
                if (buttonMakerDate.getText().equals("날짜 선택") || buttonMakerDate.getText().equals(null)) {   //날짜가 선택 안 되었을 경우
                    Toast.makeText(getApplicationContext(), "날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    dateCheck = -1;
                    textViewMakerDate.setTextColor(Color.parseColor("#ff0000"));
                } else if (today.compareTo(selectedDate) >= 1) { //오늘 날짜와 선택한 날짜 비교
                    Toast.makeText(getApplicationContext(), "날짜가 오늘(" + wordDate[0] + "-" +  "0" + (Integer.parseInt(wordDate[1])) + "-" + wordDate[2] + ")보다 빨라요! 오늘보다 나중날짜를 선택해주세요!", Toast.LENGTH_LONG).show();
                    dateCheck = -1;
                    textViewMakerDate.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    dateCheck = 1;
                    textViewMakerDate.setTextColor(Color.parseColor("#696969"));
                }
                //기간
                radioGroupMakerPeriod.getCheckedRadioButtonId();
                if (radioButtonMakerShort.isChecked()) {
                    makerPeriod = 0;
                    periodCheck = 1;
                    textViewMakerPeriod.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonMakerMedium.isChecked()) {
                    makerPeriod = 1;
                    periodCheck = 1;
                    textViewMakerPeriod.setTextColor(Color.parseColor("#696969"));
                }
                if (radioButtonMakerLong.isChecked()) {
                    makerPeriod = 2;
                    periodCheck = 1;
                    textViewMakerPeriod.setTextColor(Color.parseColor("#696969"));
                }

                //기간 입력 안했을 경우
                if (makerPeriod == -1) {
                    Toast.makeText(getApplicationContext(), "'기간'을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    periodCheck = -1;
                    textViewMakerPeriod.setTextColor(Color.parseColor("#ff0000"));
                }
                //이름 입력 안했을 경우
                if(editTextMakerMeetingName.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "모임 이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    nameCheck = -1;
                    textViewMakerMeetingName.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    nameCheck = 1;
                    textViewMakerMeetingName.setTextColor(Color.parseColor("#696969"));
                }

                //전체 조건 확인
                if (regionCheck == 1 && dateCheck == 1 && periodCheck == 1 && nameCheck == 1 && timeCheck == 1 && ageCheck == 1 && genderCheck == 1 && limitCheck == 1)
                    checkAllCondition = 1;
                else
                    checkAllCondition = -1;

                return checkAllCondition;
            }


            public String meetingIdSetting() {
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMddHHmmss");
                String time = sdfNow.format(new Date(System.currentTimeMillis()));

                return time;
            }
            @Override
            public void onClick(View v){
                if(checkCondition() == 1){
                    meetingId = meetingIdSetting();

                    makerIntroduce = editTextMakerIntroduce.getText().toString();
                    makerMeetingName = editTextMakerMeetingName.getText().toString();
                    makerLeaderId = LoginActivity.userId;
                    try{
                        makerStartTime = Integer.parseInt(editTextMakerStartTime.getText().toString());
                        makerEndTime = Integer.parseInt(editTextMakerEndTime.getText().toString());
                        makerMinAge = Integer.parseInt(editTextMakerMinAge.getText().toString());
                        makerMaxAge = Integer.parseInt(editTextMakerMaxAge.getText().toString());
                        makerLimit = Integer.parseInt(editTextMakerLimit.getText().toString());
                    } catch (NumberFormatException e) {
                    } catch (Exception e) {
                    }

                    makerFoodKind = makerFoodKindCheck(foodKind);

                    MainActivity.fireBaseManager.inputMeetingInfo(meetingId, makerIntroduce, makerMeetingName, makerLeaderId, makerPeriod, selectedSido, selectedSigugun, selectedDongEupMyun, date, makerStartTime, makerEndTime, makerFoodKind, makerMinAge, makerMaxAge, makerGender, makerLimit);
                    MainActivity.fireBaseManager.inputChattingRoom(meetingId);


                    Toast.makeText(getApplicationContext(), "모임을 성공적으로 만들었습니다.", Toast.LENGTH_LONG).show();
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

                    finish();
                }

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            System.out.println(data.getData().toString());
            Glide.with(this)
                    .load(filePath)
                    .into(imageViewMakerConditionSettingProfile);
        }
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
                DatePickerDialog dpd = new DatePickerDialog(MakerConditionSetting.this, new DatePickerDialog.OnDateSetListener() {
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
    public void setActionbar(){
        //액션바 만들기
        ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.
        ab.setHomeAsUpIndicator(R.drawable.menu_icon);

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_making_meeting_room, null);
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
}