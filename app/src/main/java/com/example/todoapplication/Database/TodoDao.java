package com.example.todoapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todo todo);

    @Delete
    void deleteTodo(Todo todo);

    @Update
    void updateTodo(Todo todo);

    @Query("SELECT * FROM todo")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * from todo LIMIT 1")
    Todo[] getAnyTodo();

    @Query("SELECT * FROM todo WHERE id =:taskId")
    LiveData<Todo> loadTaskById(int taskId);
}
