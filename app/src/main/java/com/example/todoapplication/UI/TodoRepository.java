package com.example.todoapplication.UI;

import android.app.Application;
import com.example.todoapplication.TodoRoomDatabase;
import com.example.todoapplication.Database.Todo;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TodoRepository {
    private Todo todo;
    private LiveDate<List<Todo>> allTodos;

    TodoRepository(Application application){

    }


}
