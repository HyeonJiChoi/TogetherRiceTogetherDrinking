package com.example.hambabhamsulclient;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpecificMeetingActivity extends AppCompatActivity {
    Bundle meetingInfo;
    ScrollView scrollViewSpecificMeetingPage;
    ImageButton imageButtonChangeMeeting, imageButtonChatting, imageButton;
    StorageReference riversRef;
    ListViewAdapter adapter;
    ListView listViewSeekerMembers;
    Button buttonSeekerParticipate;
    TextView textViewSpecificMeetingName;
    TextView textViewSeekerSimpleDescription;
    TextView textViewSeekerExplanation;
    TextView textViewSeekerConditionDescription;
    TextView textViewSeekerMemberNumber;
    HashMap<String, Object> meeting;
    String userId;
    static String meetingId = "";
    static String leaderId = "";
    ReturnValueMethod rvm = new ReturnValueMethod();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_meeting_page);
        userId = LoginActivity.userId;

        scrollViewSpecificMeetingPage = findViewById(R.id.scrollViewSeeker);
        buttonSeekerParticipate = findViewById(R.id.buttonSeekerParticipate);
        textViewSpecificMeetingName = findViewById(R.id.textViewSpecificMeetingName);
        textViewSeekerSimpleDescription = findViewById(R.id.textViewSeekerSimpleDescription);
        textViewSeekerExplanation = findViewById(R.id.textViewSeekerExplanation);
        textViewSeekerConditionDescription = findViewById(R.id.textViewSeekerConditionDescription);
        textViewSeekerMemberNumber = findViewById(R.id.textViewSeekerMemberNumber);
        listViewSeekerMembers = findViewById(R.id.ListViewSeekerMembers);
        adapter = new ListViewAdapter();
        riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com");

        //해당 모임 정보 가져오기
        meetingInfo = getIntent().getExtras();
        meeting = (HashMap<String, Object>) meetingInfo.get("meetingInfo");
        setTitle();
        if (LoginActivity.userId.equals(meeting.get("leaderId")))
            sendNotification();
        //모임에 대한 정보 인자에 삽입하기
        textViewSpecificMeetingName.setText(meeting.get("name").toString());
        textViewSeekerSimpleDescription.setText("[분류]" + rvm.checkFoodkind(meeting.get("foodkind").toString()) + "   [참여자수]" + ((HashMap<String, Object>) meeting.get("member")).size() + "   [지역]" + meeting.get("loca_sido") + " " + meeting.get("loca_sigugun") + " " + meeting.get("loca_dongeupmyun"));
        textViewSeekerExplanation.setText(meeting.get("explain").toString());
        textViewSeekerConditionDescription.setText("모임 이름: " + meeting.get("name").toString() + "\n" +
                "모임장: " + MainActivity.fireBaseManager.user.get(meeting.get("leaderId").toString()).get("nickname").toString() + "\n" +
                "기간: " + rvm.checkPeriod(meeting.get("period").toString()) + "\n" +
                "장소: " + meeting.get("loca_sido").toString() + " " + meeting.get("loca_sigugun").toString() + " " + meeting.get("loca_dongeupmyun").toString() + "\n" +
                "시간: " + meeting.get("startTime").toString() + "시부터 " + meeting.get("endTime").toString() + "시까지\n" +
                "나이: " + meeting.get("minage").toString() + " 세이상 " + meeting.get("maxage").toString() + " 세이하" + "\n" +
                "성별: " + rvm.checkGender(meeting.get("gender").toString()) + "\n" +
                "정원제한: " + meeting.get("limit").toString() + "명");
        leaderId = meeting.get("leaderId").toString();
        meetingId = meeting.get("id").toString();
        textViewSeekerMemberNumber.setText("총 " + ((HashMap<String, Object>) meeting.get("member")).size() + "명");



        setAdapter();
        //listViewSeekerMembers의 눌럿을 때 보여주기
        listViewSeekerMembers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public String getId(int position) {
                return ((HashMap<String, Object>) meeting.get("member")).keySet().toArray()[position].toString();
            }

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String userid = getId(position);
                if (MainActivity.fireBaseManager.user.get(userid).get("anonymity").toString().equals("0")) {    //공개
                    memberShow1(userid);
                } else {    //비공개
                    memberShow2(userid);
                }
                return true;
            }
        });

        onChangeButton();
    }

    public void onChangeButton() {
        if (MainActivity.fireBaseManager.myRoom.containsKey(LoginActivity.userId)) {
            if (MainActivity.fireBaseManager.myRoom.get(LoginActivity.userId).containsKey(meeting.get("id").toString())) {
                buttonSeekerParticipate.setText("모임탈퇴하기");
                buttonSeekerParticipate.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        show1();
                    }
                });
            } else {
                buttonSeekerParticipate.setText("모임참가하기");
                buttonSeekerParticipate.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        show2();
                    }
                });
            }
        } else {
            buttonSeekerParticipate.setText("모임참가하기");
            buttonSeekerParticipate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    show2();
                }
            });
        }
    }


    public void setAdapter() {
        HashMap<String, Object> members = (HashMap<String, Object>) meeting.get("member");
        Set memberId = members.keySet();
        Iterator<String> iter = memberId.iterator();
        String leaderId = meeting.get("leaderId").toString();
        String position;
        position = "모임장";
        adapter.addItem(riversRef.child("userProfiles/" + leaderId + ".jpg"),
                MainActivity.fireBaseManager.user.get(leaderId).get("name").toString(),
                MainActivity.fireBaseManager.user.get(leaderId).get("nickname").toString(),
                position);
        while (iter.hasNext()) {
            String newMemberId = iter.next();
            if (!newMemberId.equals(meeting.get("leaderId").toString())) {
                position = "멤버";
                adapter.addItem(riversRef.child("userProfiles/" + newMemberId + ".jpg"),
                        MainActivity.fireBaseManager.user.get(newMemberId).get("name").toString(),
                        MainActivity.fireBaseManager.user.get(newMemberId).get("nickname").toString(),
                        position);
            }
        }
        listViewSeekerMembers.setAdapter(adapter);
    }


    public void show1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("탈퇴신청");
        builder.setMessage("정말 이 모임에서 탈퇴하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "예를 선택했습니다.", Toast.LENGTH_LONG).show();
                        //탈퇴
                        MainActivity.fireBaseManager.deleteMeetingMember(meeting.get("id").toString()); //미팅멤버에서 제거
                        MainActivity.fireBaseManager.deleteMyMeetingRoom(LoginActivity.userId, meeting.get("id").toString()); //마이미팅룸에서 제거
                        Toast.makeText(getApplicationContext(), "탈퇴 신청 되셨습니다.", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onChangeButton();
                            }
                        }, 2000);
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "탈퇴신청을 취소했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void show2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("참가신청");
        builder.setMessage("정말 이 모임에 참가하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "예를 선택했습니다.", Toast.LENGTH_LONG).show();

                        if (MainActivity.fireBaseManager.myRoom.containsKey(LoginActivity.userId)) {
                            if (!MainActivity.fireBaseManager.myRoom.get(LoginActivity.userId).containsKey(meeting.get("id").toString())) {
                                MainActivity.fireBaseManager.inputNotification(meeting.get("id").toString(), LoginActivity.userId);
                                Toast.makeText(getApplicationContext(), "참가신청이 완료되었습니다! 허락 요청을 기다려주세요!", Toast.LENGTH_LONG).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        onChangeButton();
                                    }
                                }, 2000);
                            } else {
                                Toast.makeText(getApplicationContext(), "이미 신청하신 모임입니다!", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            MainActivity.fireBaseManager.inputNotification(meeting.get("id").toString(), LoginActivity.userId);
                            MainActivity.fireBaseManager.inputMyMeetingRoom(LoginActivity.userId, meeting.get("id").toString(), false);
                            Toast.makeText(getApplicationContext(), "참가신청이 완료되었습니다! 허락 요청을 기다려주세요!", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    onChangeButton();
                                }
                            }, 2000);
                        }
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "참가신청을 취소했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void memberShow1(String id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("'" + MainActivity.fireBaseManager.user.get(id).get("name").toString() + "'" + " 멤버 정보");
        builder.setMessage(
                "이름: " + MainActivity.fireBaseManager.user.get(id).get("name").toString() + "\n\n" +
                        "닉네임: " + MainActivity.fireBaseManager.user.get(id).get("nickname").toString() + "\n\n" +
                        "생년월일: " + MainActivity.fireBaseManager.user.get(id).get("birth").toString() + "\n\n" +
                        "성별: " + rvm.checkGender(MainActivity.fireBaseManager.user.get(id).get("gender").toString()));
        builder.setNegativeButton("닫기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    public void memberShow2(String id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("멤버 정보");
        builder.setMessage("비공개 설정");
        builder.setNegativeButton("닫기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    //어뎁터 구현
    public class ListViewAdapter extends BaseAdapter {
        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private ArrayList<HashMap<String, Object>> listViewItemList = new ArrayList<>();

        // ListViewAdapter의 생성자
        public ListViewAdapter() {

        }

        // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            return listViewItemList.size();
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.member_listview, parent, false);
            }

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            LinearLayout linearLayout = convertView.findViewById(R.id.linearlayoutMemberProfile);
            CircleImageView circleImageView = convertView.findViewById(R.id.circleImageViewMemberProfile);
            TextView managerTextView = (TextView) convertView.findViewById(R.id.textViewMemberListViewManager);
            TextView positionTextView = (TextView) convertView.findViewById(R.id.textViewMemberListViewPosition);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            HashMap<String, Object> listViewItem = listViewItemList.get(position);

            if (listViewItem.get("position").toString().equals("모임장"))
                linearLayout.setBackgroundColor(getResources().getColor(R.color.colorBeige));
            else
                linearLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            // 아이템 내 각 위젯에 데이터 반영
            Glide.with(getApplicationContext() /* context */)
                    .using(new FirebaseImageLoader())
                    .load((StorageReference) listViewItem.get("icon"))
                    .into(circleImageView);
            listViewItem.get("name").toString();
            managerTextView.setText(listViewItem.get("manager").toString());
            positionTextView.setText(listViewItem.get("position").toString());

            return convertView;
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            return listViewItemList.get(position);
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
        public void addItem(StorageReference icon, String title, String manager, String position) {
            HashMap<String, Object> item = new HashMap<String, Object>();

            item.put("icon", icon);
            item.put("name", title);
            item.put("manager", manager);
            item.put("position", position);

            listViewItemList.add(item);
        }

    }

    public void setTitle() {
        ReturnValueMethod tmp = new ReturnValueMethod();
        //액션바 만들기
        ActionBar ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.customer_actionbar_specificmeeting, null);
        imageButtonChangeMeeting = mCustomView.findViewById(R.id.imageButtonChangeMeeting);
        if(leaderId.equals(userId)) {
            imageButtonChangeMeeting.setVisibility(View.VISIBLE);
            imageButtonChangeMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakerConditionCheck.class);
                    startActivity(intent);
                }
            });
        }
        else
            imageButtonChangeMeeting.setVisibility(View.INVISIBLE);

        //채팅방으로 가기 버튼
        imageButtonChatting = mCustomView.findViewById(R.id.imageButtonChatting);
        if (MainActivity.fireBaseManager.myRoom.containsKey(LoginActivity.userId)) {
            if (MainActivity.fireBaseManager.myRoom.get(LoginActivity.userId).containsKey(meeting.get("id").toString())) {
                imageButtonChatting.setVisibility(View.VISIBLE);
                imageButtonChatting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MeetingChatting.class);
                        intent.putExtra("chattingMeetingName", meeting.get("name").toString());
                        intent.putExtra("chattingUserName", MainActivity.fireBaseManager.user.get(userId).get("nickname").toString());
                        startActivity(intent);
                    }
                });
            } else {
                imageButtonChatting.setVisibility(View.INVISIBLE);
            }
            //찜하기 버튼
            imageButton = mCustomView.findViewById(R.id.ImagebuttonCustomerActionbar);
            if (MainActivity.fireBaseManager.wantRoom != null) {
                if (MainActivity.fireBaseManager.wantRoom.containsKey(userId)) {
                    if (MainActivity.fireBaseManager.wantRoom.get(userId).containsKey(meeting.get("id"))) {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.jjim1));
                    }
                }
            }
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.fireBaseManager.wantRoom != null) {
                        if (MainActivity.fireBaseManager.wantRoom.containsKey(LoginActivity.userId)) {
                            if (MainActivity.fireBaseManager.wantRoom.get(LoginActivity.userId).containsKey(meeting.get("id").toString())) {
                                imageButton.setImageDrawable(getResources().getDrawable(R.drawable.jjim2));
                                MainActivity.fireBaseManager.deleteWantMeetingRoom(LoginActivity.userId, meeting.get("id").toString());
                                Toast.makeText(getApplicationContext(), "찜하기 취소", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.jjim1));
                    MainActivity.fireBaseManager.inputWantMeetingRoom(LoginActivity.userId, meeting.get("id").toString());
                    Toast.makeText(getApplicationContext(), "찜하기 완료", Toast.LENGTH_SHORT).show();
                }
            });
            ab.setCustomView(mCustomView);
        }
    }

    private void sendNotification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (meeting.get("notification") != null) {
            Set<String> keys = ((HashMap<String, Object>) meeting.get("notification")).keySet();
            Iterator iter = keys.iterator();
            while (iter.hasNext()) {
                final String id = iter.next().toString();
                builder.setTitle("참가신청");
                builder.setMessage("모임장님, \n" + MainActivity.fireBaseManager.user.get(id).get("name").toString() + "의 참가 신청을 받겠습니까?");
                builder.setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.fireBaseManager.inputMeetingMember(meeting.get("id").toString(), id); //미팅멤버에다 추가
                                MainActivity.fireBaseManager.inputMyMeetingRoom(id, meeting.get("id").toString(), false); //마이미팅룸에 추가
                                Toast.makeText(getApplicationContext(), "참가신청을 허락했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "참가신청을 거절했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });

                builder.show();
                MainActivity.fireBaseManager.deleteNotification(meeting.get("id").toString(), id);
            }
        }
    }
}