package com.example.hambabhamsulclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FirstConditionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_firstcondition, container, false);

        Button buttonSeekerCondition = (Button) rootView.findViewById(R.id.buttonSeekerCondition);

        //'조건설정' 버튼을 클릭하면 조건설정창(conditionSettingActivity, condition_setting.xml)으로 감.
        buttonSeekerCondition.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //인텐트를 상황에 맞게 보냄
                Intent intent = new Intent(getActivity(),ConditionSettingActivity.class);
                intent.putExtra("id","firstCondition");
                startActivityForResult(intent, 1);
            }
        });

        return rootView;
    }
}
