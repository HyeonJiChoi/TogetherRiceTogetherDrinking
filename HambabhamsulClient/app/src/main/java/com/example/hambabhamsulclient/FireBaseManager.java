package com.example.hambabhamsulclient;

import android.os.Message;
import android.support.annotation.NonNull;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBaseManager {
    public HashMap<String, HashMap<String, Object>> user = new HashMap<String, HashMap<String, Object>>();
    public HashMap<String, HashMap<String, Object>> meeting = new HashMap<String, HashMap<String, Object>>();
    public HashMap<String, HashMap<String, Object>> myRoom = new HashMap<String, HashMap<String, Object>>();
    public HashMap<String, HashMap<String, Object>> wantRoom = new HashMap<String, HashMap<String, Object>>();
    public HashMap<String, HashMap<String, Object>> chattingRoom = new HashMap<String, HashMap<String, Object>>();
    public DatabaseReference database;


    public FireBaseManager() {
        database = FirebaseDatabase.getInstance().getReference();
        setMeetingInfo();
        setUserInfo();
        setMyMeetingRoom();
        setWantRoom();
        setChattingRoom();
    }

    public void inputUserInfo(String id, String password, String name, String nickname, String birth, int gender, int[] eatingCharacter, boolean[] character) {

        HashMap<String, Object> newuser = new HashMap<String, Object>();
        newuser.put("Password", password);
        newuser.put("name", name);
        newuser.put("nickname", nickname);
        newuser.put("birth", birth);
        newuser.put("gender", gender);
        newuser.put("anonymity",1);

        for (int i = 0; i < 2; i++)
            newuser.put("eating_character" + i, eatingCharacter[i]);

        for (int i = 0; i < 5; i++)
            newuser.put("character" + i, character[i]);

        database.child("UserInfo").child(id).setValue(newuser);
    }
    public void updateUserInfo(String id, String password, String name, String nickname, String birth, int gender, int anonymity, int[] eatingCharacter, boolean[] character) {
        HashMap<String, Object> updateuser = new HashMap<String, Object>();
        updateuser.put("Password", password);
        updateuser.put("name", name);
        updateuser.put("nickname", nickname);
        updateuser.put("birth", birth);
        updateuser.put("gender", gender);
        updateuser.put("anonymity",anonymity);

        for (int i = 0; i < 2; i++)
            updateuser.put("eating_character" + i, eatingCharacter[i]);

        for (int i = 0; i < 5; i++)
            updateuser.put("character" + i, character[i]);

        database.child("UserInfo").child(id).updateChildren(updateuser);
    }

    public void inputMeetingInfo(String id, String explain, String name, String leaderId, int period, String sido, String sigugun, String dongeupmyun, String date, int startTime, int endTime, int foodkind, int minage, int maxage, int gender, int limit) {
        HashMap<String, Object> newmeeting = new HashMap<String, Object>();
        newmeeting.put("id", id);
        newmeeting.put("explain", explain);
        newmeeting.put("name", name);
        newmeeting.put("leaderId", leaderId);
        newmeeting.put("period", period);
        newmeeting.put("loca_sido", sido);
        newmeeting.put("loca_sigugun", sigugun);
        newmeeting.put("loca_dongeupmyun", dongeupmyun);
        newmeeting.put("startTime", startTime);
        newmeeting.put("endTime", endTime);
        newmeeting.put("date", date);
        newmeeting.put("foodkind", foodkind);
        newmeeting.put("minage", minage);
        newmeeting.put("maxage", maxage);
        newmeeting.put("gender", gender);
        newmeeting.put("limit", limit);

        HashMap<String,Object> members = new HashMap<>();
        members.put(leaderId,user.get(leaderId).get("nickname"));
        newmeeting.put("member", members);

        database.child("MeetingInfo").child(id).setValue(newmeeting);
        inputMyMeetingRoom(leaderId,id,true);
    }

    public void updateMeetingInfo (String id, String explain, String leaderNickname, int period, String sido, String sigugun, String dongeupmyun, String date, int startTime, int endTime, int foodkind, int minage, int maxage, int gender, int limit) {
        HashMap<String, Object> updatemeeting = new HashMap<String, Object>();

        updatemeeting.put("id", id);
        updatemeeting.put("explain", explain);
        updatemeeting.put("leaderId", leaderNickname);
        updatemeeting.put("period", period);
        updatemeeting.put("loca_sido", sido);
        updatemeeting.put("loca_sigugun", sigugun);
        updatemeeting.put("loca_dongeupmyun", dongeupmyun);
        updatemeeting.put("startTime", startTime);
        updatemeeting.put("endTime", endTime);
        updatemeeting.put("date", date);
        updatemeeting.put("foodkind", foodkind);
        updatemeeting.put("minage", minage);
        updatemeeting.put("maxage", maxage);
        updatemeeting.put("gender", gender);
        updatemeeting.put("limit", limit);

        database.child("MeetingInfo").child(id).updateChildren(updatemeeting);
    }

    public void inputMyMeetingRoom(String clientId, String meetingId, boolean whetherLeader) {
        database.child("MyMeetingRoom").child(clientId).child(meetingId).setValue(whetherLeader);
    }
    public void inputWantMeetingRoom(String clientId, String meetingId) {
        database.child("MyWantMeetingRoom").child(clientId).child(meetingId).setValue(true);
    }

    public void inputMeetingMember(String id,String userId) {
        database.child("MeetingInfo").child(id).child("member").child(userId).setValue(MainActivity.fireBaseManager.user.get(userId).get("nickname").toString());
    }

    public void inputChattingRoom(String meetingId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(meetingId, "");
        database.child("ChattingRoom").updateChildren(map);

    }

    public void inputNotification(String id, String userId){
        database.child("MeetingInfo").child(id).child("notification").child(userId).setValue(true);
    }
    public void deleteNotification(String id, String userId){
        database.child("MeetingInfo").child(id).child("notification").child(userId).setValue(null);
    }
    public void setAnonymity(String id,int num){
        database.child("UserInfo").child(id).child("anonymity").setValue(num);
    }
    public void deleteMeetingMember(String id){
        database.child("MeetingInfo").child(id).child("member").child(LoginActivity.userId).setValue(null);
        if(meeting.get(id).get("member") == null){
            database.child("MeetingInfo").child(id).setValue(null);
        }
    }
    public void deleteWantMeetingRoom(String clientId, String meetingId) {
        database.child("MyWantMeetingRoom").child(clientId).child(meetingId).setValue(null);
    }
    public void deleteMyMeetingRoom(String clientId, String meetingId) {
        database.child("MyMeetingRoom").child(clientId).child(meetingId).setValue(null);
    }

    public void setUserInfo() {
        DatabaseReference mPostReference = database.child("UserInfo");
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //여기서 데이터를 받아오면 됨
                user = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                System.out.println("FireBase_user");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }

    public void setMeetingInfo() {
        DatabaseReference mPostReference = database.child("MeetingInfo");
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //여기서 데이터를 받아오면 됨
                meeting = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                System.out.println("FireBase_meeting");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }

    public void setMyMeetingRoom() {
        DatabaseReference mPostReference = database.child("MyMeetingRoom");
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //여기서 데이터를 받아오면 됨
                myRoom = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                System.out.println("FireBase_mymeetingroom");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
    public void setWantRoom(){
        DatabaseReference mPostReference = database.child("MyWantMeetingRoom");
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //여기서 데이터를 받아오면 됨
                wantRoom = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                System.out.println("FireBase_wantmeetingroom");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    public void setChattingRoom() {
        DatabaseReference mPostReference = database.child("ChattingRoom");
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //여기서 데이터를 받아오면 됨
                chattingRoom = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                System.out.println("FireBase_chattingroom");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Getting Post failed, log a message
                Log.w("error", "loadPost:onCancelled", databaseError.toException());
                //...
            }
        });
    }

}