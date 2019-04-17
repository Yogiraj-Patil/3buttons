package com.example.a3buttons.SearchData;

public class ItemListRecyclerData {

    private String name,s_date,e_date,r_amt,amt,policy_id,policy_type;
    private int imgResource;

    public String getPolicy_type() {
        return policy_type;
    }

    public ItemListRecyclerData(String name, String s_date, String e_date, String r_amt, String amt, String policy_id, String type, int imgResource) {
        this.name = name;
        this.s_date = s_date;
        this.e_date = e_date;
        this.r_amt = r_amt;
        this.amt = amt;
        this.policy_id = policy_id;
        this.imgResource = imgResource;
        this.policy_type = type;
    }


    public String getName() {
        return name;
    }

    public String getS_date() {
        return s_date;
    }

    public String getE_date() {
        return e_date;
    }

    public String getR_amt() {
        return r_amt;
    }

    public String getAmt() {
        return amt;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public int getImgResource() {
        return imgResource;
    }
}
