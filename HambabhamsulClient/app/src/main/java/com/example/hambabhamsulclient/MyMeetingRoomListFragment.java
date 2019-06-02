package com.example.hambabhamsulclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class MyMeetingRoomListFragment extends ListFragment {
    ScrollView scrollView;
    LinearLayout linearLayout;
    Bundle conditions;
    FireBaseManager fireBaseManager;
    StorageReference riversRef;
    ListView listView;
    ArrayList<String> yesLeaderRoom = new ArrayList<String>();
    ArrayList<String> noLeaderRoom = new ArrayList<String>();
    int check = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        riversRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com");
        scrollView = new ScrollView(getActivity());
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        fireBaseManager = MainActivity.fireBaseManager;
        splitRooms();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        //룸 분류
        ListViewAdapter adapter = new ListViewAdapter();
        conditions = getArguments();
        if (conditions != null) {
            check = conditions.getInt("check"); //내가 만든 모임 - 0, 남이 만든 모임 - 1
        }


        if (check == 0) {
            //룸 만들기
            if (yesLeaderRoom.size() > 0) {
                for (int i = 0; i < yesLeaderRoom.size(); i++) {
                    //아이템 추가.
                    String id = yesLeaderRoom.get(i);
                    System.out.println("yesleaderroom------"+id);
                    adapter.addItem(id
                            ,LoginActivity.userId
                            ,riversRef.child("meetingProfiles/" + id + ".jpg")
                            , fireBaseManager.meeting.get(id).get("name").toString()
                            , fireBaseManager.meeting.get(id).get("loca_sido").toString()
                            , fireBaseManager.meeting.get(id).get("explain").toString()
                            , fireBaseManager.meeting.get(id).get("date").toString()
                            , fireBaseManager.user.get(fireBaseManager.meeting.get(id).get("leaderId").toString()).get("nickname").toString());
                }
            }
        }

        if (check == 1) {
            //내 미팅 룸 아닌거
            if (noLeaderRoom.size() > 0) {
                for (int i = 0; i < noLeaderRoom.size(); i++) {
                    //아이템 추가.
                    String id = noLeaderRoom.get(i);
                    adapter.addItem(id
                            ,MainActivity.fireBaseManager.meeting.get(id).get("leaderId").toString()
                            ,riversRef.child("meetingProfiles/" + id + ".jpg")
                            , fireBaseManager.meeting.get(id).get("name").toString()
                            , fireBaseManager.meeting.get(id).get("loca_sido").toString()
                            , fireBaseManager.meeting.get(id).get("explain").toString()
                            , fireBaseManager.meeting.get(id).get("date").toString()
                            , fireBaseManager.user.get(fireBaseManager.meeting.get(id).get("leaderId").toString()).get("nickname").toString());
                }
            }
        }
        setListAdapter(adapter);
    }



    public void splitRooms () {
        Set roomId = fireBaseManager.myRoom.get(LoginActivity.userId).keySet();
        Iterator<String> iter = roomId.iterator();
        while (iter.hasNext()) {
            String id = iter.next();
            if (Boolean.parseBoolean(fireBaseManager.myRoom.get(LoginActivity.userId).get(id).toString()) == true)
                yesLeaderRoom.add(id);
            else
                noLeaderRoom.add(id);
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
                    if (check == 0) {
                        String item = yesLeaderRoom.get(pos);
                        Intent intent = new Intent(getActivity(), SpecificMeetingActivity.class);
                        intent.putExtra("meetingInfo", fireBaseManager.meeting.get(item));
                        startActivity(intent);
                    } else if (check == 1) {
                        String item = noLeaderRoom.get(pos);
                        Intent intent = new Intent(getActivity(), SpecificMeetingActivity.class);
                        intent.putExtra("meetingInfo", fireBaseManager.meeting.get(item));
                        startActivity(intent);
                    }
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
            System.out.println("-----lestViewiem"+listViewItem.get("id"));
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