package com.example.todoapplication.UI;

import android.app.Application;

import com.example.todoapplication.AppExecutor;
import com.example.todoapplication.TodoRoomDatabase;
import com.example.todoapplication.Database.Todo;
import com.example.todoapplication.Database.TodoDao;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;

    TodoRepository(Application application){
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        todoDao = db.todoDao();
        allTodos = todoDao.getAllTodos();
    }

    LiveData<List<Todo>> getAllTodos() {return allTodos; }

    public void deleteTodo(final  Todo todo){
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                todoDao.deleteTodo(todo);
            }
        });
    }

    public void update(final Todo todo){
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                todoDao.update(todo);
            }
        });
    }

    public void insert(final Todo todo){
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                todoDao.insert(todo);
            }
        });
    }
}
