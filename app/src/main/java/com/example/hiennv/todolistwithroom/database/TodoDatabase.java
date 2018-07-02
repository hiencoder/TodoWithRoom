package com.example.hiennv.todolistwithroom.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.hiennv.todolistwithroom.dao.TodoDAO;
import com.example.hiennv.todolistwithroom.model.Todo;
import com.example.hiennv.todolistwithroom.utils.Const;

@Database(entities = {Todo.class},version = Const.DATABASE_VERSION,exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase{
    public abstract TodoDAO todoDAO();
}
