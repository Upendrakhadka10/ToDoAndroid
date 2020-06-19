package com.example.todoapplication.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.Database.Todo;
import com.example.todoapplication.R;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private final LayoutInflater mInflater;
    private List<Todo> myTodos; //
    private static ClickListener clickListener;
    private static DeleteClickListener deleteClickListener;
    private static EditClickListener editClickListener;

    public TodoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public TodoListAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.item_todo,viewGroup,false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.TodoViewHolder todoViewHolder, int position) {
        if(myTodos != null){
            Todo current = myTodos.get(position);
            todoViewHolder.todoItemView.setText(current.getTitle());
            todoViewHolder.todoItemDesc.setText(current.getDate() + " " + current.getTime());


            //Setting priority color
            String color;
            if(current.getPriority().equals("High")){
                color = "#EC0841";
            } else if(current.getPriority().equals("Medium")){
                color = "#F8CE52";
            } else{
                color = "#10E436";
            }
            todoViewHolder.priority_indicator.setBackgroundColor(Color.parseColor(color));

        }

        else{
            todoViewHolder.todoItemView.setText("No Todo Added");
        }


    }

    @Override
    public int getItemCount() {
        if(myTodos != null)
            return myTodos.size();
        else return 0;
    }



    public class TodoViewHolder extends RecyclerView.ViewHolder {
        private final TextView todoItemView;
        private final TextView todoItemDesc;
        private final View priority_indicator;
        private LinearLayout wrapper;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            wrapper = itemView.findViewById(R.id.todo_each_wrapper);
            todoItemView = itemView.findViewById(R.id.todo_item_title);
            todoItemDesc = itemView.findViewById(R.id.todo_item_desc);
            priority_indicator = itemView.findViewById(R.id.priority_indicator);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    void setTodos(List<Todo> todo){
        myTodos = todo;
        notifyDataSetChanged();
    }

    public Todo getTodoAtPosition(int position) {return myTodos.get(position); }


    public void setOnItemClickListener(ClickListener clickListener){
        TodoListAdapter.clickListener = clickListener;
    }

    public void setOnDeleteClickListener(DeleteClickListener clickListener){
        TodoListAdapter.deleteClickListener = clickListener;
    }

    public void setOnEditClickListener(EditClickListener clickListener){
        TodoListAdapter.editClickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(View v, int position);
    }

    public interface DeleteClickListener{
        void onDeleteClick(View v, int position);
    }

    public interface EditClickListener{
        void onEditClick(View v, int postion);
    }
}