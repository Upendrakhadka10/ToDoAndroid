package com.example.todoapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.todoapplication.Database.Todo;
import com.example.todoapplication.PagerTransformer;
import com.example.todoapplication.R;

import java.util.List;

public class TodoDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TodoViewModel todoViewModel;

    private TodoDetailAdapter todoDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        final Bundle bundle = getIntent().getExtras();
        viewPager = findViewById(R.id.todo_pager);
        viewPager.setPageTransformer(true, new PagerTransformer());
        todoDetailAdapter = new TodoDetailAdapter((getSupportFragmentManager()));
        viewPager.setAdapter(todoDetailAdapter);

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        todoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                todoDetailAdapter.setTodos(todos);
                viewPager.setCurrentItem(bundle.getInt("position"));
            }
        });

        viewPager.setCurrentItem(bundle.getInt("position"));
    }
}