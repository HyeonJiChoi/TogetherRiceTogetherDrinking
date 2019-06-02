package com.example.hambabhamsulclient;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class CustomAdapter extends PagerAdapter {
    LayoutInflater inflater;
    String[] meetingId;
    Activity nowActivity;

    public CustomAdapter(LayoutInflater inflater, String[] meetingId, Activity nowActivity) {
        this.inflater = inflater;
        this.meetingId = meetingId;
        this.nowActivity = nowActivity;
    }


    @Override
    public int getCount() {
        return 5; //이미지 개수 리턴(그림이 10개라서 10을 리턴)
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = null;

        view = inflater.inflate(R.layout.main_screen_fragment_first, null);
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nowActivity, SpecificMeetingActivity.class);
                intent.putExtra("meetingInfo", MainActivity.fireBaseManager.meeting.get(meetingId[position]));
                nowActivity.startActivity(intent);
            }
        });
        ImageView iconImageView = (ImageView) view.findViewById(R.id.imageViewMainScreenImage);
        TextView titleTextView = (TextView) view.findViewById(R.id.textViewTitle);
        TextView managerTextView = (TextView) view.findViewById(R.id.textViewMainScreenManager);
        TextView explainTextView = (TextView) view.findViewById(R.id.textViewMainScreenExplain);
        TextView dateTextView = (TextView) view.findViewById(R.id.textViewMainScreenDate);
        TextView locationTextView = (TextView) view.findViewById(R.id.textViewMainScreenLocation);


        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(nowActivity/* context */)
                .using(new FirebaseImageLoader())
                .load((StorageReference) FirebaseStorage.getInstance().getReferenceFromUrl("gs://hambabhamsulclient.appspot.com")
                        .child("meetingProfiles/" + meetingId[position] + ".jpg"))
                .into(iconImageView);
        iconImageView.setAlpha(130); // 투명도 설정
        titleTextView.setText(MainActivity.fireBaseManager.meeting.get(meetingId[position]).get("name").toString());
        managerTextView.setText(MainActivity.fireBaseManager.user.get(
                MainActivity.fireBaseManager.meeting.get(meetingId[position]).get("leaderId").toString())
                .get("nickname").toString());
        explainTextView.setText(MainActivity.fireBaseManager.meeting.get(meetingId[position]).get("explain").toString());
        dateTextView.setText(MainActivity.fireBaseManager.meeting.get(meetingId[position]).get("date").toString());
        locationTextView.setText(MainActivity.fireBaseManager.meeting.get(meetingId[position]).get("loca_sido").toString() + " " +
                MainActivity.fireBaseManager.meeting.get(meetingId[position]).get("loca_sigugun").toString() + " " + MainActivity.fireBaseManager.meeting.get(meetingId[position]).get("loca_dongeupmyun").toString());
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }
}