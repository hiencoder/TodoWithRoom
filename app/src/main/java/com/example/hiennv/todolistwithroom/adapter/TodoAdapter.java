package com.example.hiennv.todolistwithroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hiennv.todolistwithroom.R;
import com.example.hiennv.todolistwithroom.callback.OnItemClickListener;
import com.example.hiennv.todolistwithroom.model.Todo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {
    private Context context;
    private List<Todo> todoList;
    private OnItemClickListener onItemClickListener;

    public TodoAdapter(Context context, List<Todo> todoList, OnItemClickListener listener) {
        this.context = context;
        this.todoList = todoList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder todoHolder, int position) {
        Todo todo = todoList.get(position);
        todoHolder.bindTodo(todo);
    }

    @Override
    public int getItemCount() {
        return (todoList != null) ? todoList.size() : 0;
    }

    public class TodoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_category)
        TextView tvCategory;

        public TodoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                onItemClickListener.onClick(todoList.get(getAdapterPosition()));
            });
        }

        public void bindTodo(Todo todo) {
            tvTitle.setText(todo.title);
            tvDescription.setText(todo.description);
            tvCategory.setText(todo.category);
        }
    }

    /**
     * update adapter
     * @param todos
     */
    public void updateTodoList(List<Todo> todos) {
        todoList.clear();
        todoList.addAll(todos);
        notifyDataSetChanged();
    }

    /**
     * update adapter
     * @param todo
     */
    public void addTodo(Todo todo){
        todoList.add(todo);
        notifyDataSetChanged();
    }
}
