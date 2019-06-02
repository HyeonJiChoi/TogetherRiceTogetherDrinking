package com.example.hambabhamsulclient;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SeekerMeetingListFragment extends ListFragment {
    ActionBar ab;
    ListViewAdapter adapter;
    Bundle conditions;
    String clientId = LoginActivity.userId, date, sido, sigugun, dongeupmyun;
    int foodkind, time, period;
    int[] eatingCharacter = new int[2];
    boolean[] character = new boolean[5];
    ArrayList<HashMap<String, Object>> match_meetings;
    FireBaseManager fireBaseManager;
    StorageReference riversRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com");
        fireBaseManager = MainActivity.fireBaseManager;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        conditions = getArguments();
        getConditions(conditions);


        ConditionMatching conditionMatch = new ConditionMatching(clientId, period, date, foodkind,
                fireBaseManager.user.get(clientId).get("birth").toString(), time,
                Integer.parseInt(fireBaseManager.user.get(clientId).get("gender").toString()), eatingCharacter, character);
        match_meetings = conditionMatch.getMatchMeetings(sido, sigugun, dongeupmyun);

        //리스트들 보여주기
        adapter = new ListViewAdapter();
        if (match_meetings.size() > 0) {
            for (int i = 0; i < match_meetings.size(); i++) {
                //아이템 추가.
                adapter.addItem(match_meetings.get(i).get("id").toString()
                        ,match_meetings.get(i).get("leaderId").toString()
                        ,riversRef.child("meetingProfiles/" + match_meetings.get(i).get("id") + ".jpg")
                        , match_meetings.get(i).get("name").toString()
                        , match_meetings.get(i).get("loca_sido").toString()
                        , match_meetings.get(i).get("explain").toString()
                        , match_meetings.get(i).get("date").toString()
                        , fireBaseManager.user.get(match_meetings.get(i).get("leaderId").toString()).get("nickname").toString());
            }
        }
        setListAdapter(adapter);
    }

    public void getConditions(Bundle conditions) {
        foodkind = conditions.getInt("foodkind");
        period = conditions.getInt("period");
        date = conditions.getString("date");
        time = conditions.getInt("time");
        eatingCharacter[0] = conditions.getInt("amount");
        eatingCharacter[1] = conditions.getInt("speed");
        /*for(int i=0; i<5; i++){
            character[i] = conditions.getBoolean("char"+i);
        }*/
        character = new boolean[]{true, false, true, true, true};
        sido = conditions.getString("sido");
        sigugun = conditions.getString("sigugun");
        dongeupmyun = conditions.getString("dongeupmyun");


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
                    HashMap<String, Object> item = match_meetings.get(pos);
                    //인텐트를 보냄
                    Intent intent = new Intent(getActivity(), SpecificMeetingActivity.class);
                    intent.putExtra("meetingInfo", item);
                    startActivity(intent);
                }
            });

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageViewSeekerMeetingFragmentImage);
            TextView textViewMeetingLocation = (TextView) convertView.findViewById(R.id.textViewMeetingLocation);
            TextView textViewMeetingDescription = (TextView) convertView.findViewById(R.id.textViewMeetingDescription);
            TextView textViewMeetingTitle = (TextView) convertView.findViewById(R.id.textViewMeetingTitle);
            TextView textViewMeetingDate = (TextView) convertView.findViewById(R.id.textViewMeetingDate);
            CircleImageView MeetingLeaderProfile = convertView.findViewById(R.id.MeetingLeaderProfile);
            final ImageButton imageButton = convertView.findViewById(R.id.ImagebuttonMeetingList);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            final HashMap<String, Object> listViewItem = listViewItemList.get(position);
            if (MainActivity.fireBaseManager.wantRoom != null) {
                if (MainActivity.fireBaseManager.wantRoom.containsKey(LoginActivity.userId)) {
                    if (MainActivity.fireBaseManager.wantRoom.get(LoginActivity.userId).containsKey(listViewItem.get("id"))) {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.jjim1));
                    }
                }
            }
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.fireBaseManager.wantRoom != null) {
                        if (MainActivity.fireBaseManager.wantRoom.containsKey(LoginActivity.userId)) {
                            if (MainActivity.fireBaseManager.wantRoom.get(LoginActivity.userId).containsKey(listViewItem.get("id").toString())) {
                                imageButton.setImageDrawable(getResources().getDrawable(R.drawable.jjim2));
                                MainActivity.fireBaseManager.deleteWantMeetingRoom(LoginActivity.userId, listViewItem.get("id").toString());
                                Toast.makeText(getActivity(), "찜하기 취소.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.jjim1));
                    MainActivity.fireBaseManager.inputWantMeetingRoom(LoginActivity.userId,listViewItem.get("id").toString());
                    Toast.makeText(getActivity(), "찜하기 완료.", Toast.LENGTH_SHORT).show();
                }
            });
            // 아이템 내 각 위젯에 데이터 반영
            Glide.with(getActivity() /* context */)
                    .using(new FirebaseImageLoader())
                    .load((StorageReference) listViewItem.get("icon"))
                    .into(iconImageView);
            iconImageView.setAlpha(250); // 투명도 설정

            textViewMeetingLocation.setText(listViewItem.get("sido").toString());
            textViewMeetingDescription.setText(listViewItem.get("explain").toString());
            textViewMeetingTitle.setText(listViewItem.get("name").toString());
            textViewMeetingDate.setText(listViewItem.get("date").toString());
            //CircleImageView 아이템 내 각 위젯에 데이터 반영
            Glide.with(getActivity())
                    .using(new FirebaseImageLoader())
                    .load(riversRef.child("userProfiles/" + listViewItem.get("leaderId") + ".jpg"))
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
        public void addItem(String id,String leaderId,StorageReference icon, String title, String sido, String explain, String date, String manager) {
            HashMap<String, Object> item = new HashMap<String, Object>();

            item.put("id",id);
            item.put("leaderId",leaderId);
            item.put("icon", icon);
            item.put("name", title);
            item.put("sido", sido);
            item.put("explain", explain);
            item.put("date", date);
            item.put("manager", manager);

            listViewItemList.add(item);
        }
    }

}