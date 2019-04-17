package com.example.a3buttons.UserData;

public class UserDataClass {
    private static String Name,email,user_id,mobile,user_type,user_area;

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserDataClass.email = email;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        UserDataClass.user_id = user_id;
    }

    public static String getMobile() {
        return mobile;
    }

    public static void setMobile(String mobile) {
        UserDataClass.mobile = mobile;
    }

    public static String getUser_type() {
        return user_type;
    }

    public static void setUser_type(String user_type) {
        UserDataClass.user_type = user_type;
    }

    public static String getUser_area() {
        return user_area;
    }

    public static void setUser_area(String user_area) {
        UserDataClass.user_area = user_area;
    }
}
