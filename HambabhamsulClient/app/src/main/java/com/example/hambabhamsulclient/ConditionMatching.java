package com.example.hambabhamsulclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class ConditionMatching {
    FireBaseManager fireBaseManager;
    String clientId, birth;
    int time, foodkind, period;
    String date;
    int gender;
    int[] eating_character;
    boolean[] character;
    HashMap<String, Object> memberId;

    public ConditionMatching() {
    }

    public ConditionMatching(String clientId, int period, String date, int foodkind, String birth, int time, int gender, int[] eating_character, boolean[] character) {
        this.clientId = clientId;
        this.birth = birth;
        this.time = time;
        this.period = period;
        this.date = date;
        this.gender = gender;
        this.foodkind = foodkind;
        this.character = character;
        this.eating_character = eating_character;
        this.fireBaseManager = MainActivity.fireBaseManager;
    }

    //미팅들의 성격을 정해주는 것
    public boolean[] setMeetingCondition(String meetingId) {
        //멤버들의 정보를 갖고 옴
        if (memberId != null) {
            Set<String> number = memberId.keySet();
            boolean[] meetingCondition = new boolean[5];

            for (int character = 0; character < 5; character++) {
                int truecount = 0, falsecount = 0;
                Iterator<String> iter = number.iterator();
                while (iter.hasNext()) {
                    Boolean newcharacter = Boolean.parseBoolean(fireBaseManager.user.get(iter.next()).get("character" + character).toString());
                    if (newcharacter == true) truecount++;
                    else if (newcharacter == false) falsecount++;
                }
                if (truecount >= falsecount) meetingCondition[character] = true;
                else meetingCondition[character] = false;
            }
            return meetingCondition;
        } else {
            String name = fireBaseManager.meeting.get(meetingId).get("leaderId").toString();
            boolean[] meetingcondition = new boolean[]{Boolean.parseBoolean(fireBaseManager.user.get(name).get("character0").toString()), Boolean.parseBoolean(fireBaseManager.user.get(name).get("character1").toString()),
                    Boolean.parseBoolean(fireBaseManager.user.get(name).get("character2").toString()), Boolean.parseBoolean(fireBaseManager.user.get(name).get("character3").toString()), Boolean.parseBoolean(fireBaseManager.user.get(name).get("character4").toString())};
            return meetingcondition;
        }
    }

    public int[] setEatingCondition(String meetingId) {
        memberId = (HashMap<String, Object>) fireBaseManager.meeting.get(meetingId).get("member");
        if (memberId != null) {
            Set<String> number = memberId.keySet();
            int[] eatingCondition = new int[2];

            for (int character = 0; character < 2; character++) {
                int[] count = new int[3];
                Iterator<String> iter = number.iterator();
                while (iter.hasNext()) {
                    int newcharacter = Integer.parseInt(fireBaseManager.user.get(iter.next()).get("eating_character" + character).toString());
                    if (newcharacter == 0) count[0]++;
                    else if (newcharacter == 1) count[1]++;
                    else if (newcharacter == 2) count[2]++;
                }

                //max 찾기
                int max = count[0];
                int max_num = 0;
                for (int i = 1; i < 3; i++) {
                    if (max > count[i]) {
                        max = count[i];
                        max_num = i;
                    }
                }
                eatingCondition[character] = max_num;
            }
            return eatingCondition;
        } else {
            int[] eatingcondition = new int[]{Integer.parseInt(fireBaseManager.user.get(fireBaseManager.meeting.get(meetingId).get("leaderId")).get("eating_character0").toString()), Integer.parseInt(fireBaseManager.user.get(fireBaseManager.meeting.get(meetingId).get("leaderId")).get("eating_character1").toString())};
            return eatingcondition;
        }
    }

    public ArrayList<HashMap<String, Object>> getMatchMeetings(String sido, String sigugun, String dongeupmyun) {
        ArrayList<HashMap<String, Object>> Sortedmeeting = new ArrayList<HashMap<String, Object>>();
        ArrayList<Integer[]> match_count = new ArrayList<Integer[]>();
        ArrayList<Integer[]> eating_match_count = new ArrayList<Integer[]>();
        ArrayList<String> eating_meeting_id = new ArrayList<String>();
        ArrayList<String> meetingsId = new ArrayList<String>(), meetingsId_time = new ArrayList<String>(), meetingsId_3 = new ArrayList<String>(), meetingsId_period = new ArrayList<String>(); //매칭 되는 것들 모아둔 것
        //먼저 지역을 판단한다.
        Set<String> ids = fireBaseManager.meeting.keySet();
        Iterator<String> iter = ids.iterator();
        //장소제외
        while (iter.hasNext()) {
            String id = iter.next();
            String other_meeting_sido = (String) fireBaseManager.meeting.get(id).get("loca_sido");
            String other_meeting_sigugun = (String) fireBaseManager.meeting.get(id).get("loca_sigugun");
            String other_meeting_dongeupmyun = (String) fireBaseManager.meeting.get(id).get("loca_dongeupmyun");
            if (sido == null) {
                meetingsId.add(id);
            } else if (sigugun == null && other_meeting_sido.equals(sido)) {
                meetingsId.add(id);
            } else if (dongeupmyun == null && other_meeting_sido.equals(sido) && other_meeting_sigugun.equals(sigugun)) {
                meetingsId.add(id);
            } else if (other_meeting_sido.equals(sido) && other_meeting_sigugun.equals(sigugun) && other_meeting_dongeupmyun.equals(dongeupmyun)) {
                meetingsId.add(id);
            }
        }
        //시간제외
        for (int i = 0; i < meetingsId.size(); i++) {
            if (time == 0 &&
                    Integer.parseInt(fireBaseManager.meeting.get(meetingsId.get(i)).get("startTime").toString()) >= 6 && Integer.parseInt(fireBaseManager.meeting.get(meetingsId.get(i)).get("startTime").toString()) <= 10) {
                meetingsId_time.add(meetingsId.get(i));
            } else if (time == 1 &&
                    Integer.parseInt(fireBaseManager.meeting.get(meetingsId.get(i)).get("startTime").toString()) >= 10 && Integer.parseInt(fireBaseManager.meeting.get(meetingsId.get(i)).get("startTime").toString()) <= 17) {
                meetingsId_time.add(meetingsId.get(i));
            } else if (time == 2 &&
                    Integer.parseInt(fireBaseManager.meeting.get(meetingsId.get(i)).get("startTime").toString()) >= 16 && Integer.parseInt(fireBaseManager.meeting.get(meetingsId.get(i)).get("startTime").toString()) <= 23) {
                meetingsId_time.add(meetingsId.get(i));
            } else if (time == 3)
                meetingsId_time.add(meetingsId.get(i));
        }
        //주기와 기간제외
        for (int i = 0; i < meetingsId_time.size(); i++) {
            if (Integer.parseInt(fireBaseManager.meeting.get(meetingsId_time.get(i)).get("period").toString()) == this.period || period == 3) {
                if (fireBaseManager.meeting.get(meetingsId_time.get(i)).get("date").toString().equals(this.date))
                    meetingsId_period.add(meetingsId_time.get(i));
            }
        }

        //성별, 나이, 음식종류 제외
        for (int i = 0; i < meetingsId_period.size(); i++) {
            if ((Integer.parseInt(fireBaseManager.meeting.get(meetingsId_period.get(i)).get("gender").toString()) == this.gender) || (Integer.parseInt(fireBaseManager.meeting.get(meetingsId_period.get(i)).get("gender").toString()) == 2)) {
                if (Integer.parseInt(fireBaseManager.meeting.get(meetingsId_period.get(i)).get("minage").toString()) <= ((getDateString() + 1) - (Integer.parseInt(this.birth) / 10000 ))
                        && Integer.parseInt(fireBaseManager.meeting.get(meetingsId_period.get(i)).get("maxage").toString()) >= ((getDateString() + 1) - (Integer.parseInt(this.birth)  / 10000))) {
                    if (Integer.parseInt(fireBaseManager.meeting.get(meetingsId_period.get(i)).get("foodkind").toString()) == this.foodkind || this.foodkind == 1) {
                        meetingsId_3.add(meetingsId_period.get(i));
                    }
                }
            }
        }

        //미팅들을 하나하나 비교해가며 먹는 성격 매칭,
        int count3 = meetingsId_3.size();
        if (count3 > 0) {
            eating_match_count.add(new Integer[]{0, EatingConditionMatch(meetingsId_3.get(0))});
            for (int i = 1; i < count3; i++) {
                int number = EatingConditionMatch(meetingsId_3.get(i));
                int count = eating_match_count.size();
                for (int j = 0; j < count; j++) {
                    if (number >= eating_match_count.get(j)[1]) {
                        eating_match_count.add(j, new Integer[]{i, number});
                        break;
                    } else if (j == eating_match_count.size() - 1) {
                        eating_match_count.add(new Integer[]{i, number});
                    }
                }

            }

            //받은 순서대로 array를 정리해준다.
            for (int i = 0; i < eating_match_count.size(); i++) {
                eating_meeting_id.add(meetingsId_3.get(eating_match_count.get(i)[0]));
            }
        }

        //미팅들을 하나하나 비교해가며 성격 매치가 높은 것부터 판단한다,
        int count4 = eating_match_count.size();
        if (count4 > 0) {
            match_count.add(new Integer[]{0, ConditionMatch(eating_meeting_id.get(0))});
            for (int i = 1; i < count4; i++) {
                int number = ConditionMatch(eating_meeting_id.get(i));
                int count = match_count.size();
                for (int j = 0; j < count; j++) {
                    if (number >= match_count.get(j)[1]) {
                        match_count.add(j, new Integer[]{i, number});
                        break;
                    } else if (j == match_count.size() - 1) {
                        match_count.add(new Integer[]{i, number});
                        break;
                    }
                }

            }
            //받은 순서대로 array를 정리해준다.
            for (int i = 0; i < match_count.size(); i++) {
                Sortedmeeting.add(fireBaseManager.meeting.get(eating_meeting_id.get(match_count.get(i)[0])));
            }
        }
        return Sortedmeeting;
    }

    public int ConditionMatch(String meetingId) {
        boolean[] conditions = setMeetingCondition(meetingId);
        int match_count = 0;
        for (int i = 0; i < 5; i++) {
            if (conditions[i] == (boolean) fireBaseManager.user.get(clientId).get("character" + i)) {
                match_count++;
            }

        }
        return match_count;
    }

    public int EatingConditionMatch(String meetingId) {
        int[] conditions = setEatingCondition(meetingId);
        int match_count = 0;
        for (int i = 0; i < 2; i++) {
            if (conditions[i] == Integer.parseInt(fireBaseManager.user.get(clientId).get("eating_character" + i).toString())) {
                match_count++;
            }
        }
        return match_count;
    }

    //오늘 날짜 구하기
    public int getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.KOREA);
        int int_date = Integer.parseInt(df.format(new Date()));

        return int_date;
    }
}