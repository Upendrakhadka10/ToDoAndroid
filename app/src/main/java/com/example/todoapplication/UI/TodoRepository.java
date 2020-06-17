package com.example.todoapplication.UI;

import android.app.Application;
import com.example.todoapplication.TodoRoomDatabase;
import com.example.todoapplication.Database.Todo;
import com.example.todoapplication.Database.TodoDao;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveDate<List<Todo>> allTodos;

    TodoRepository(Application application){
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        todoDao =  db.todoDao();
        allTodos = todoDao.getAllTodos();

    }
    LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }


}
