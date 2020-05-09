package com.example.android.wineshop;

public class Requests {

    private String userId;
    private String drink;
    private String type;
    private String type1;
    private String type2;
    private String type3;
    private String amount;


    public Requests() { }

    public Requests(String userId, String drink, String type, String type1, String type2, String type3, String amount) {
        this.amount = amount;
        this.userId = userId;
        this.type = type;
        this.drink = drink;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        }

    public String getAmount() {
        return amount;
    }

    public String getDrink() {
        return drink;
    }

    public String getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getType3() {
        return type3;
    }
}
