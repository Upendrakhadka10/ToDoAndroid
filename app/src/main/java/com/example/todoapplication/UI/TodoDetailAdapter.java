package com.example.todoapplication.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.todoapplication.Database.Todo;

import java.util.List;

public class TodoDetailAdapter extends FragmentStatePagerAdapter {
    private List<Todo> myTodos;

    public TodoDetailAdapter(FragmentManager fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TodoDetailFragment todoDetailFragment = new TodoDetailFragment();
        Todo current = myTodos.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("todo_title", current.getTitle());
        bundle.putString("todo_date", "Date : " + current.getDate());
        bundle.putString("todo_time", "Time : " + current.getTime());
        bundle.putString("todo_priority", "Priority : " + current.getPriority());


        todoDetailFragment.setArguments(bundle);
        return todoDetailFragment;
    }

    @Override
    public int getCount() {
        if(myTodos != null)
            return myTodos.size();
        else return 0;
    }

    public void setTodos(List<Todo> todos){
        myTodos = todos;
        notifyDataSetChanged();
    }
}

