package com.sopt.famfam.data;

import com.sopt.famfam.adapter.item.AllPhotoItem;

import java.util.ArrayList;

public class SampleDate {

    ArrayList<AllPhotoItem> items = new ArrayList<>();


    public ArrayList<AllPhotoItem> getItems() {

        AllPhotoItem photo1 = new AllPhotoItem("https://t1.daumcdn.net/cfile/tistory/23A6C7335972266836");

        items.add(photo1);


        return items;
    }
}
