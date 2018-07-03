package com.example.hiennv.todolistwithroom.view;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.hiennv.todolistwithroom.R;
import com.example.hiennv.todolistwithroom.adapter.TodoAdapter;
import com.example.hiennv.todolistwithroom.callback.OnItemClickListener;
import com.example.hiennv.todolistwithroom.database.TodoDatabase;
import com.example.hiennv.todolistwithroom.model.Todo;
import com.example.hiennv.todolistwithroom.utils.Const;
import com.example.hiennv.todolistwithroom.utils.SharedUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

public class TodoListActivity extends AppCompatActivity implements OnItemClickListener{
    private static final String TAG = TodoListActivity.class.getSimpleName();
    @BindView(R.id.rv_todo)
    RecyclerView rvTodo;
    @BindView(R.id.btn_add_new_todo)
    FloatingActionButton btnAddNewTodo;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    private String[] categories = {"All","Android","Swift","Java","Kotlin"};
    private List<String> listCategory = new ArrayList<>(Arrays.asList(categories));
    private ArrayAdapter<String> cateAdapter;

    private List<Todo> listTodos;
    private TodoAdapter todoAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Unbinder unbinder;

    private TodoDatabase todoDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        unbinder = ButterKnife.bind(this);
        listTodos = new ArrayList<>();
        cateAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listCategory);
        cateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(cateAdapter);
        spCategory.setSelection(0);

        //Create TodoDatabase
        todoDatabase = Room.databaseBuilder(getApplicationContext(), TodoDatabase.class,Const.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        todoAdapter = new TodoAdapter(this,listTodos,this);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvTodo.setLayoutManager(layoutManager);
        rvTodo.setHasFixedSize(true);
        rvTodo.setItemAnimator(new DefaultItemAnimator());
        rvTodo.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvTodo.setAdapter(todoAdapter);

        //Check first database
        SharedUtils sharedUtils = new SharedUtils(getApplicationContext());
        if (sharedUtils.getBoolean(Const.KEY_FIRST_TIME,true)){
            sharedUtils.setBoolean(Const.KEY_FIRST_TIME,false);
            dummyTodos();
        }
/*        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                handleFilterCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
    }

    //Dummy todo
    private void dummyTodos() {
        Todo todo = new Todo();
        todo.title = "Android tutorial 1";
        todo.description = "Description Android";
        todo.category = "Android";
        listTodos.add(todo);

        todo = new Todo();
        todo.title = "Swift tutorial 1";
        todo.description = "Description Swift";
        todo.category = "Swift";
        listTodos.add(todo);

        todo = new Todo();
        todo.title = "Java tutorial 1";
        todo.description = "Description Java";
        todo.category = "Java";
        listTodos.add(todo);

        todo = new Todo();
        todo.title = "Kotlin";
        todo.description = "Kotlin tutorial 1";
        todo.category = "Kotlin";
        listTodos.add(todo);

        //Insert list todo
        insertListTodo(listTodos);
    }

    /**
     *
     * @param todos
     */
    private void insertListTodo(List<Todo> todos) {
        //use Asynctask insert
        new AsyncTask<List<Todo>,Void,Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(List<Todo>... lists) {
                todoDatabase.todoDAO().insertTodoList(lists[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(todos);
        //todoDatabase.todoDAO().insertTodoList(todos);
    }

    @OnClick({R.id.btn_add_new_todo})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_add_new_todo:
                handleOpenAddActivity();
                break;
        }
    }

    @OnItemSelected(R.id.sp_category)
    public void onItemSelected(int position){
        handleFilterCategory(position);
    }

    @OnItemSelected(value = R.id.sp_category, callback = OnItemSelected.Callback.NOTHING_SELECTED)
    public void onNothingSelected(){

    }

    /**
     *
     * @param position
     */
    private void handleFilterCategory(int position) {
        if (position == 0){
            fetchAllTodo();
        }else {
            String strFilter = listCategory.get(position);
            fetchTodoByCategory(strFilter);
        }
    }

    /**
     *
     * @param filter
     */
    private void fetchTodoByCategory(String filter) {
        new AsyncTask<String,Void,List<Todo>>(){

            @Override
            protected List<Todo> doInBackground(String... strings) {
                return todoDatabase.todoDAO().fetchTodoByCategory(strings[0]);
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                super.onPostExecute(todos);
                if (todos != null && todos.size() > 0) {
                    todoAdapter.updateTodoList(todos);
                }
            }
        }.execute(filter);
    }

    //FetchAllTodo
    private void fetchAllTodo() {
        new AsyncTask<Void,Void,List<Todo>>(){

            @Override
            protected List<Todo> doInBackground(Void... voids) {
                return todoDatabase.todoDAO().fetchAllTodos();
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                super.onPostExecute(todos);
                if (todos != null && todos.size() > 0){
                    todoAdapter.updateTodoList(todos);
                }
            }
        }.execute();
    }

    private void handleOpenAddActivity() {
        Intent intent = new Intent(TodoListActivity.this,AddTodoActivity.class);
        startActivityForResult(intent, Const.REQUEST_ADD_NEW_TODO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_ADD_NEW_TODO && resultCode
                 == RESULT_OK){
            Long id = data.getLongExtra(Const.KEY_INSERT_SUCCESS,0);
            Log.d(TAG, "onActivityResult: " + id);
            fetchAllTodoAndInsert(id);
        }else if (requestCode == Const.REQUEST_UPDATE_TODO &&
                resultCode == RESULT_OK){
            boolean isDelete = data.getBooleanExtra(Const.KEY_DELETE_SUCCESS,false);
            if (isDelete){
                Log.d(TAG, "onActivityResult: Delete: " + data.getIntExtra(Const.KEY_ID_DELETE,0));
            }else {
                Log.d(TAG, "onActivityResult: Update: " + data.getIntExtra(Const.KEY_UPDATE_SUCCESS,0));
            }
            fetchAllTodo();
        }
    }

    /**
     *
     */
    private void fetchAllTodoAndInsert(Long id) {
        new AsyncTask<Long,Void,Todo>(){

            @Override
            protected Todo doInBackground(Long... longs) {
                return todoDatabase.todoDAO().fetchTodoById(longs[0]);
            }

            @Override
            protected void onPostExecute(Todo todo) {
                super.onPostExecute(todo);
                if (todo != null){
                    todoAdapter.addTodo(todo);
                }
            }
        }.execute(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(Todo todo) {
        Intent intent = new Intent(this,AddTodoActivity.class);
        intent.putExtra(Const.KEY_EDIT_TODO,todo);
        startActivityForResult(intent,Const.REQUEST_UPDATE_TODO);
    }
}
