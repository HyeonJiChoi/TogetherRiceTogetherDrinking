package com.example.hambabhamsulclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class WantMeetingRoomActivity extends AppCompatActivity {
    ActionBar ab;
    HashMap<String, Object> wantMeetings;
    FireBaseManager fireBaseManager;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_meeting_room);
        setActionbar();
        fireBaseManager = MainActivity.fireBaseManager;
        if (fireBaseManager.wantRoom.containsKey(LoginActivity.userId)) {
            wantMeetings = fireBaseManager.wantRoom.get(LoginActivity.userId);
            listView = findViewById(R.id.listViewWantMeetingRooms);
            StorageReference riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com");
            System.out.println(wantMeetings);
            if (wantMeetings != null) {
                ListViewAdapter adapter = new ListViewAdapter();
                Set keys = wantMeetings.keySet();
                Iterator<String> iter = keys.iterator();
                //바 설정
                while (iter.hasNext()) {
                    //아이템 추가.
                    String id = iter.next();
                    adapter.addItem(riversRef.child("meetingProfiles/" + id + ".jpg")
                            , fireBaseManager.meeting.get(id).get("name").toString()
                            , fireBaseManager.meeting.get(id).get("loca_sido").toString()
                            , fireBaseManager.meeting.get(id).get("explain").toString()
                            , fireBaseManager.meeting.get(id).get("date").toString()
                            , fireBaseManager.user.get(fireBaseManager.meeting.get(id).get("leaderId").toString()).get("nickname").toString());
                }
                listView.setAdapter(adapter);
            }
        }
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
                convertView = inflater.inflate(R.layout.meeting_listview, parent, false);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Set keys = wantMeetings.keySet();
                    Iterator<String> iter = keys.iterator();
                    String item = (keys.toArray())[pos].toString();
                    Intent intent = new Intent(getApplicationContext(), SpecificMeetingActivity.class);
                    intent.putExtra("meetingInfo", fireBaseManager.meeting.get(item));
                    startActivity(intent);

                }
            });
// 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageViewSeekerMeetingFragmentImage);
            TextView textViewMeetingLocation = (TextView) convertView.findViewById(R.id.textViewMeetingLocation);
            ImageButton ImagebuttonMeetingList = (ImageButton) convertView.findViewById(R.id.ImagebuttonMeetingList);
            TextView textViewMeetingDescription = (TextView) convertView.findViewById(R.id.textViewMeetingDescription);
            TextView textViewMeetingTitle = (TextView) convertView.findViewById(R.id.textViewMeetingTitle);
            TextView textViewMeetingDate = (TextView) convertView.findViewById(R.id.textViewMeetingDate);
            CircleImageView MeetingLeaderProfile = convertView.findViewById(R.id.MeetingLeaderProfile);

            ImagebuttonMeetingList.setImageDrawable(getResources().getDrawable(R.drawable.jjim1));

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            HashMap<String, Object> listViewItem = listViewItemList.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            Glide.with(getApplicationContext() /* context */)
                    .using(new FirebaseImageLoader())
                    .load((StorageReference) listViewItem.get("icon"))
                    .into(iconImageView);
            iconImageView.setAlpha(250); // 투명도 설정
            textViewMeetingLocation.setText(listViewItem.get("sido").toString());
            textViewMeetingDescription.setText(listViewItem.get("explain").toString());
            textViewMeetingTitle.setText(listViewItem.get("name").toString());
            textViewMeetingDate.setText(listViewItem.get("date").toString());
            //CircleImageView 아이템 내 각 위젯에 데이터 반영
            final Set keys = wantMeetings.keySet();
            Iterator<String> iter = keys.iterator();
            StorageReference riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com");
            Glide.with(getApplicationContext())
                    .using(new FirebaseImageLoader())
                    .load(riversRef.child("userProfiles/" +
                            MainActivity.fireBaseManager.meeting.get((keys.toArray())[pos].toString()).get("leaderId")
                            + ".jpg"))
                    .into(MeetingLeaderProfile);
            MeetingLeaderProfile.setAlpha(250);

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
        public void addItem(StorageReference icon, String title, String sido, String explain, String date, String manager) {
            HashMap<String, Object> item = new HashMap<String, Object>();

            item.put("icon", icon);
            item.put("name", title);
            item.put("sido", sido);
            item.put("explain", explain);
            item.put("date", date);
            item.put("manager", manager);

            listViewItemList.add(item);
        }


    }

    public void setActionbar() {
        //액션바 만들기
        ab = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        ab.setDisplayShowTitleEnabled(false);      //액션바에 표시되는 제목의 표시유무를 설정합니다.
        ab.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_wanting_meeting_room, null);
        ab.setCustomView(mCustomView);
    }

}