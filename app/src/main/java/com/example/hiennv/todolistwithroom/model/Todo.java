package com.example.hiennv.todolistwithroom.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.hiennv.todolistwithroom.utils.Const;

import java.io.Serializable;

@Entity(tableName = Const.TABLE_NAME)
public class Todo implements Serializable{
    /*@PrimaryKey khóa chính autogen*/
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String description;

    public String category;

    @Ignore
    public String priority;

}
