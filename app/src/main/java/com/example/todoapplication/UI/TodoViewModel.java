package com.example.todoapplication.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapplication.Database.Todo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository todoRepository;
    private LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        allTodos = todoRepository.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {return allTodos;}

    public void deleteTodo(Todo todo) {todoRepository.deleteTodo(todo);}

    public void insert(Todo todo) {todoRepository.insert(todo);}

    public void update(Todo todo) {todoRepository.update(todo);}
}
