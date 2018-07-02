package com.example.hiennv.todolistwithroom;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;

import com.example.hiennv.todolistwithroom.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

public class TodoListActivity extends AppCompatActivity {
    @BindView(R.id.rv_todo)
    RecyclerView rvTodo;
    @BindView(R.id.btn_add_new_todo)
    FloatingActionButton btnAddNewTodo;
    @BindView(R.id.sp_category)
    Spinner spCategory;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        unbinder = ButterKnife.bind(this);
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

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
