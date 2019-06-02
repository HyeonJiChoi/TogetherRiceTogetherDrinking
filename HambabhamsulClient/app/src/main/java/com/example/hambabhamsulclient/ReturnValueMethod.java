package com.example.hambabhamsulclient;

public class ReturnValueMethod {
    public String checkFoodkind(String ele) {
        String returnValue = "";

        if (ele.equals("1")) {
            returnValue = "전체";
        } else if (ele.equals("2")) {
            returnValue = "한식";
        } else if (ele.equals("3")) {
            returnValue = "분식";
        } else if (ele.equals("4")) {
            returnValue = "카페,디저트";
        } else if (ele.equals("5")) {
            returnValue = "돈까스,회,일식";
        } else if (ele.equals("6")) {
            returnValue = "치킨";
        } else if (ele.equals("7")) {
            returnValue = "피자";
        } else if (ele.equals("8")) {
            returnValue = "중국집";
        } else if (ele.equals("9")) {
            returnValue = "족발,보쌈";
        }else if (ele.equals("10")) {
            returnValue = "도시락";
        } else if (ele.equals("11")) {
            returnValue = "찜,탕";
        } else if (ele.equals("12")) {
            returnValue = "패스트푸드";
        }

        return returnValue;
    }

    public String checkPeriod(String period) {
        String returnPeriod = "";
        if (period.equals("0")) {
            returnPeriod = "단기";
        } else if (period.equals("1")) {
            returnPeriod = "중기";
        } else if (period.equals("2")) {
            returnPeriod = "장기";
        }

        return returnPeriod;
    }

    public String checkGender(String gender) {
        String returnGender = "";
        if (gender.equals("0")) {
            returnGender = "남";
        } else if (gender.equals("1")) {
            returnGender = "여";
        } else if (gender.equals("2")) {
            returnGender = "무관";
        }
        return returnGender;
    }

    public String checkChar0(String charString) {
        String returnChar0 = "";
        if (charString.equals("true")) {
            returnChar0 = "외향적인";
        } else if (charString.equals("false")) {
            returnChar0 = "내향적인";
        }
        return returnChar0;
    }

    public String checkChar1(String charString) {
        String returnChar1 = "";
        if (charString.equals("true")) {
            returnChar1 = "감정적인";
        } else if (charString.equals("false")) {
            returnChar1 = "이성적인";
        }
        return returnChar1;
    }

    public String checkChar2(String charString) {
        String returnChar2 = "";
        if (charString.equals("true")) {
            returnChar2 = "평범한";
        } else if (charString.equals("false")) {
            returnChar2 = "개성있는";
        }
        return returnChar2;
    }

    public String checkChar3(String charString) {
        String returnChar3 = "";
        if (charString.equals("true")) {
            returnChar3 = "장난스러운";
        } else if (charString.equals("false")) {
            returnChar3 = "진중한";
        }
        return returnChar3;
    }

    public String checkChar4(String charString) {
        String returnChar4 = "";
        if (charString.equals("true")) {
            returnChar4 = "성급한";
        } else if (charString.equals("false")) {
            returnChar4 = "여유로운";
        }
        return returnChar4;
    }

    public String checkEatingAmount(String eatingAmountString) {
        String returnEatingAmount = "";
        if (eatingAmountString.equals("0")) {
            returnEatingAmount = "1인분도 많아요!";
        } else if (eatingAmountString.equals("1")) {
            returnEatingAmount = "1인분이 적당해요!";
        } else if (eatingAmountString.equals("2")) {
            returnEatingAmount = "1인분은 당연히 부족하죠!";
        }
        return returnEatingAmount;
    }

    public String checkEatingSpeed(String eatingSpeedString) {
        String returnEatingSpeed = "";
        if (eatingSpeedString.equals("0")) {
            returnEatingSpeed = "느려요!";
        } else if (eatingSpeedString.equals("1")) {
            returnEatingSpeed = "적당해요!";
        } else if (eatingSpeedString.equals("2")) {
            returnEatingSpeed = "빨라요!";
        }
        return returnEatingSpeed;
    }

    public String checkAnomity(String anomityString) {
        String returnAnomity = "";
        if (anomityString.equals("0")) {
            returnAnomity = "공개";
        } else if (anomityString.equals("1")) {
            returnAnomity = "비공개";
        }
        return returnAnomity;
    }
}