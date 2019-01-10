package com.sopt.famfam.data;

import com.sopt.famfam.adapter.item.AlbumItem;
import com.sopt.famfam.adapter.item.AllPhotoItem;

import java.util.ArrayList;

public class SampleData {

    ArrayList<AlbumItem> items = new ArrayList<>();
    ArrayList<AllPhotoItem> itemdatas = new ArrayList<>();


    public ArrayList<AlbumItem> getItems() {

        AlbumItem photo1 = new AlbumItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIioLeXf1nG39oA-P5DpGhrN2tforplCnfSnRDND-kv6xC_bfpiA");

        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);
        items.add(photo1);


        return items;
    }


}
