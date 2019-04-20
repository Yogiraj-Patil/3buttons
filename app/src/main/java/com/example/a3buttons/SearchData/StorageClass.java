package com.example.a3buttons.SearchData;

public class StorageClass {
    public static ItemListRecyclerData data;
    public static String sortedEvent_data;

    public String capitalizeText(String str) {
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap + " ");
        }
        return builder.toString();
    }
}
