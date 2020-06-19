package com.example.todoapplication.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.todoapplication.Database.Todo;
import com.example.todoapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private TodoViewModel todoViewModel;
    ConstraintLayout constraintLayout;
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.todo_recycler_view);

        final TodoListAdapter adapter = new TodoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        todoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                adapter.setTodos(todos);
            }
        });


        //Swipe action
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    //when user swipes a word, delete item from dataase
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();

                        if(direction == ItemTouchHelper.LEFT){
                            final Todo currentTodo = adapter.getTodoAtPosition(position);


                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Are You Sure ?")
                                    .setContentText("You won't be able to recover this item")
                                    .setCancelText("Cancel")
                                    .setConfirmText("Yes")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("Ok")
                                                    .setContentText("Todo deleted successfully")
                                                    .show();
                                            //Delete the item
                                            todoViewModel.deleteTodo(currentTodo);
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    adapter.notifyDataSetChanged();
                                }
                            })
                                    .show();
                        } else {
                            Todo todo = adapter.getTodoAtPosition(position);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", todo.getId());
                            bundle.putString("title", todo.getTitle());
                            bundle.putString("time", todo.getTime());
                            bundle.putString("date", todo.getDate());
                            bundle.putString("priority", todo.getPriority());

                            openAddPopup("edit", bundle);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        Bitmap icon;
                        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                            View itemView = viewHolder.itemView;
                            float height = (float) itemView.getBottom() - (float) itemView.getTop();
                            float width = height / 3;
                            if (dX > 0) {
                                p.setColor(Color.parseColor("#388E3C"));

                                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop() + 23, dX, (float) itemView.getBottom());
                                c.drawRect(background, p);
                                icon = getBitmap(R.drawable.ic_edit_black_24dp);

                                RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                                c.drawBitmap(icon, null, icon_dest, p);
                            } else {
                                p.setColor(Color.parseColor("#D32F2F"));
                                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop() + 23, (float) itemView.getRight(), (float) itemView.getBottom());
                                c.drawRect(background, p);
                                icon = getBitmap(R.drawable.ic_delete_black_24dp);
                                RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                                c.drawBitmap(icon, null, icon_dest, p);
                            }
                        }
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                }
        );
        //Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(recyclerView);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddPopup("add", new Bundle());
            }
        });

        //Edit mode on tap
        adapter.setOnItemClickListener(new TodoListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent i = new Intent(MainActivity.this, TodoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        adapter.setOnDeleteClickListener(new TodoListAdapter.DeleteClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                Todo currentTodo = adapter.getTodoAtPosition(position);
                Toast.makeText(MainActivity.this, "Deleting"+ " "+ currentTodo.getTitle(), Toast.LENGTH_LONG).show();

                //Delete the word
                todoViewModel.deleteTodo(currentTodo);
            }
        });

        adapter.setOnEditClickListener(new TodoListAdapter.EditClickListener() {
            @Override
            public void onEditClick(View v, int postion) {
                Todo todo = adapter.getTodoAtPosition(postion);
                Bundle bundle = new Bundle();
                bundle.putInt("id", todo.getId());
                bundle.putString("title", todo.getTitle());
                bundle.putString("time", todo.getTime());
                bundle.putString("date", todo.getDate());
                bundle.putString("priority", todo.getPriority());
                openAddPopup("edit", bundle);
            }
        });
    }
    private void openAddPopup(String mode, Bundle bundle){
        String title;
        if(mode == "edit"){
            title = "Edit Todo";
        } else {
            title = "Add todo";
        }
        AddTodo addTodo = new AddTodo(title, mode, bundle);
        addTodo.show(getSupportFragmentManager(), "Add Todo");
    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}