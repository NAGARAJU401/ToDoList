package com.example.user.todolist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CompletedTaskList extends AppCompatActivity {
    ListView listView;
    MyAdapter adapter2;
    List<TodoData>todoDataList1;

    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task_list);
        listView = (ListView)findViewById(R.id.completedtaskslist);
        helper = new DBHelper(CompletedTaskList.this);
        todoDataList1 = new ArrayList<>();
       todoDataList1= helper.getCompletedTaks();
      //  Toast.makeText(this, ""+todoDataList1.get(0).getStatus(), Toast.LENGTH_SHORT).show();
      adapter2 = new MyAdapter(CompletedTaskList.this,R.layout.list_items,todoDataList1);
        listView.setAdapter(adapter2);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CompletedTaskList.this);
                builder.setTitle("Delete!!");
                builder.setMessage("Do you want to delete?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteData(todoDataList1.get(position).getTitle());
                        helper.getCompletedTaks();
                        adapter2.remove(todoDataList1.get(position));
                        adapter2.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }
}
