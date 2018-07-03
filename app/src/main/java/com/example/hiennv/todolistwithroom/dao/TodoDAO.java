package com.example.hiennv.todolistwithroom.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hiennv.todolistwithroom.model.Todo;
import com.example.hiennv.todolistwithroom.utils.Const;

import java.util.List;

@Dao
public interface TodoDAO {
    /*Thêm 1 bản ghi todo*/
    @Insert
    long insertTodo(Todo todo);

    /*Thêm 1 danh sách*/
    @Insert
    void insertTodoList(List<Todo> listTodo);

    /*Lấy danh sách Todo*/
    @Query("SELECT * FROM " + Const.TABLE_NAME)
    List<Todo> fetchAllTodos();

    /*Lấy danh sách todo theo category*/
    @Query("SELECT * FROM " + Const.TABLE_NAME + " WHERE " + Const.CATEGORY + " = :category")
    List<Todo> fetchTodoByCategory(String category);

    /*Lay Todo theo Id*/
    @Query("SELECT * FROM " + Const.TABLE_NAME + " WHERE " + Const.ID + " = :id")
    Todo fetchTodoById(Long id);

    /*Update todo theo id*/
    @Update
    int updateTodo(Todo todo);

    /*Delete todo*/
    @Delete
    int deleteTodo(Todo todo);
}
