package com.example.hiennv.todolistwithroom.utils;

public class Const {
    public static final String DATABASE_NAME = "todo.db";

    public static final String TABLE_NAME = "tb_todo";
    public static final int DATABASE_VERSION = 1;
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";

    /*REQUEST*/
    public static final int REQUEST_ADD_NEW_TODO = 113;
    public static final int REQUEST_UPDATE_TODO = 114;

    public static final String KEY_EDIT_TODO = "edit";
    public static final String KEY_INSERT_SUCCESS = "insert_success";
    public static final String KEY_UPDATE_SUCCESS = "update_success";
    public static final String KEY_DELETE_SUCCESS = "delete_success";
    public static final String KEY_ID_DELETE = "delete_id";

    /*Shared preferences*/
    public static final String FILE_NAME = "file_first_time";
    public static final String KEY_FIRST_TIME = "is_first_time";


}
