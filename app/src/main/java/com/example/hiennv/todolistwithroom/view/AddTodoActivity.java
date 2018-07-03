package com.example.hiennv.todolistwithroom.view;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hiennv.todolistwithroom.R;
import com.example.hiennv.todolistwithroom.database.TodoDatabase;
import com.example.hiennv.todolistwithroom.model.Todo;
import com.example.hiennv.todolistwithroom.utils.Const;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

public class AddTodoActivity extends AppCompatActivity {
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    private Todo todoSelected;

    private String[] categories = {"Android","Swift","Java","Kotlin"};
    private List<String> listCategory = new ArrayList<>(Arrays.asList(categories));
    private ArrayAdapter<String> arrayAdapter;
    private String category;

    private Unbinder unbinder;

    private TodoDatabase todoDatabase;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        unbinder = ButterKnife.bind(this);
        intent = getIntent();
        configSpinner();
        todoDatabase = Room.databaseBuilder(getApplicationContext(),TodoDatabase.class,Const.DATABASE_NAME)
                .build();
        initData();
    }

    private void configSpinner() {
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,listCategory);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(arrayAdapter);
        spCategory.setSelection(0);
    }

    /**
     *
     */
    private void initData() {
       todoSelected = (Todo) intent.getSerializableExtra(Const.KEY_EDIT_TODO);
       if (todoSelected != null){
           etTitle.setText(todoSelected.title);
           etDescription.setText(todoSelected.description);
           for (int i = 0; i < listCategory.size(); i++) {
               if (todoSelected.category.equalsIgnoreCase(listCategory.get(i))){
                   spCategory.setSelection(i);
               }
           }
       }else {
           spCategory.setSelection(0);
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_delete,R.id.btn_save})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_save:
                String title = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                if (todoSelected != null){
                    todoSelected.title = title;
                    todoSelected.description = description;
                    todoSelected.category = category;
                    updateTodo(todoSelected);
                }else {
                    Todo todo = new Todo();
                    todo.title = title;
                    todo.description = description;
                    todo.category = category;
                    insertNewTodo(todo);
                }
                break;
            case R.id.btn_delete:
                deleteTodo(todoSelected);
                break;
        }
    }

    /**
     *
     * @param todo
     */
    @SuppressLint("StaticFieldLeak")
    private void deleteTodo(Todo todo) {
        new AsyncTask<Todo,Void,Integer>(){

            @Override
            protected Integer doInBackground(Todo... todos) {
                return todoDatabase.todoDAO().deleteTodo(todos[0]);
            }

            @Override
            protected void onPostExecute(Integer aLong) {
                super.onPostExecute(aLong);
                intent.putExtra(Const.KEY_DELETE_SUCCESS,true);
                intent.putExtra(Const.KEY_ID_DELETE,aLong);
                setResult(RESULT_OK,intent);
                finish();
            }
        }.execute(todo);
    }

    /**
     *
     * @param todo
     */
    private void updateTodo(Todo todo) {
        new AsyncTask<Todo,Void,Integer>(){

            @Override
            protected Integer doInBackground(Todo... todos) {
                return todoDatabase.todoDAO().updateTodo(todos[0]);
            }

            @Override
            protected void onPostExecute(Integer aLong) {
                super.onPostExecute(aLong);
                intent.putExtra(Const.KEY_UPDATE_SUCCESS,aLong);
                setResult(RESULT_OK,intent);
                finish();
            }
        }.execute(todo);
    }

    /**
     *
     * @param todo
     */
    private void insertNewTodo(Todo todo) {
        new AsyncTask<Todo,Void,Long>(){

            @Override
            protected Long doInBackground(Todo... todos) {
                return todoDatabase.todoDAO().insertTodo(todos[0]);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                intent.putExtra(Const.KEY_INSERT_SUCCESS,aLong);
                setResult(RESULT_OK,intent);
                finish();
            }
        }.execute(todo);
    }

    @OnItemSelected(R.id.sp_category)
    public void onItemSelected(int position){
        category = listCategory.get(position);
    }

    @OnItemSelected(value = R.id.sp_category, callback = OnItemSelected.Callback.NOTHING_SELECTED)
    public void onNothingSelected(){

    }
}
