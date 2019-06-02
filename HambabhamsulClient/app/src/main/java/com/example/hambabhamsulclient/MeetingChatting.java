package com.example.hambabhamsulclient;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MeetingChatting extends AppCompatActivity {
    ActionBar ab;
    ListView listViewChattingMeetingName;
    EditText editTextChattingMessage;
    Button buttonChattingSend;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> room = new ArrayList<>();

    String chattingMeetingName, chattingUserName;
    //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().getRoot();
    DatabaseReference reference;
    String key;
    String chattingUser;
    String chattingMessage;

    String meetingId = SpecificMeetingActivity.meetingId;
    //String meetingId = "20190601222830";
    //String userId = LoginActivity.userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_chatting);

        listViewChattingMeetingName = findViewById(R.id.listViewChattingMessage);
        editTextChattingMessage = findViewById(R.id.editTextChattingMessage);
        buttonChattingSend = findViewById(R.id.buttonChattingSend);

        //buttonChattingSend.setBackgroundResource(R.drawable.meetingroom_button_selector);

        chattingUserName = getIntent().getExtras().get("chattingUserName").toString();
        chattingMeetingName = getIntent().getExtras().get("chattingMeetingName").toString();

        //actionbar
        setActionbar();

        reference = FirebaseDatabase.getInstance().getReference().child("ChattingRoom").child(meetingId);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, room);
        listViewChattingMeetingName.setAdapter(arrayAdapter);

        buttonChattingSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println(meetingId);

                Map<String, Object> map = new HashMap<String, Object>();
                key = reference.push().getKey();

                reference.updateChildren(map);

                DatabaseReference root = reference.child(key);

                Map<String, Object> objectMap = new HashMap<String, Object>();

                objectMap.put("name", chattingUserName);
                objectMap.put("message", editTextChattingMessage.getText().toString());

                root.updateChildren(objectMap);

                editTextChattingMessage.setText("");
            }
        });
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void chatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            chattingMessage = (String) ((DataSnapshot) i.next()).getValue();
            chattingUser = (String) ((DataSnapshot) i.next()).getValue();

            arrayAdapter.add(chattingUser + " : " + chattingMessage);
        }

        arrayAdapter.notifyDataSetChanged();

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
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_chatting_room, null);
        //채팅방이름 설정
        TextView textViewChattingRoom = mCustomView.findViewById(R.id.textViewChattingRoom);
        textViewChattingRoom.setText(chattingMeetingName);
        //actionbar에 있는 button
        final ImageButton imageButtonActionBarIcon = mCustomView.findViewById(R.id.imageButtonActionBarIcon);
        imageButtonActionBarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ab.setCustomView(mCustomView);
    }
}