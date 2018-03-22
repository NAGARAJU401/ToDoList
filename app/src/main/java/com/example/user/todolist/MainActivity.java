package com.example.user.todolist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //public is a method and fields can be accessed by the members of any class.
    //class is a collections of objects.
    //created MainActivity and extends with AppCompatActivity which is Parent class.

    EditText title,description;
    Button save,cancel;
    DBHelper helper;
    int i=0;
    DatePicker picker;
    TodoData todoData;
   MyAdapter adapter;
    ListView listView;

    Dialog dialog;
    String d;
    List<TodoData>todoDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //protected can be accessed by within the package and class and subclasses
        //The Void class is an uninstantiable placeholder class to hold a reference to the Class object
        //representing the Java keyword void.
        //The savedInstanceState is a reference to a Bundle object that is passed into the onCreate method of every Android Activity
        // Activities have the ability, under special circumstances, to restore themselves to a previous state using the data stored in this bundle.

        super.onCreate(savedInstanceState);
        //Android class works in same.You are extending the Activity class which have onCreate(Bundle bundle) method in which meaningful code is written
        //So to execute that code in our defined activity. You have to use super.onCreate(bundle)

        setContentView(R.layout.activity_main);
        //R means Resource
        //layout means design
        //main is the xml you have created under res->layout->main.xml
        //Whenever you want to change your current Look of an Activity or when you move from one Activity to another .
        //he other Activity must have a design to show . So we call this method in onCreate and this is the second statement to set
        //the design

        todoData = new TodoData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //giving the toolbar with the id
        setSupportActionBar(toolbar);
        //declaring id's and set adapter to the recyclerview
        helper = new DBHelper(MainActivity.this);
        listView = (ListView)findViewById(R.id.list);
        todoDataList = new ArrayList<>();
        TodoData data = new TodoData();
        todoDataList.clear();
        todoDataList=helper.getTodoData();
        adapter = new MyAdapter(MainActivity.this,R.layout.list_items,todoDataList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dialogtitle = todoDataList.get(position).getTitle();
                String dialogdesc = todoDataList.get(position).getDescription();
                String dialogtopdate = todoDataList.get(position).getDate();
                String dialogdate = todoDataList.get(position).getDate();
                int dilogimage = todoDataList.get(position).getImage();
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.list_item_details_dialog);
                dialog.show();
                TextView dTitle = (TextView)dialog.findViewById(R.id.dialogtitle);
                TextView dDesc =(TextView)dialog.findViewById(R.id.dialogdesc);
                TextView dTopDate = (TextView)dialog.findViewById(R.id.dialogtopdate);
                TextView dDate = (TextView)dialog.findViewById(R.id.dialogdate);
                ImageView dImage = (ImageView)dialog.findViewById(R.id.dialogstat);

                dTitle.setText(dialogtitle);
                dDesc.setText(dialogdesc);
                dTopDate.setText(dialogtopdate);
                dDate.setText(dialogdate);
                dImage.setImageResource(dilogimage);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                todoDataList.get(position).setStatus(helper.updatestatus(todoDataList.get(position).getId(),1));

                if(todoDataList.get(position).getStatus()==1){
                    Toast.makeText(MainActivity.this, "Task completed", Toast.LENGTH_SHORT).show();

                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(MainActivity.this, "not completed", Toast.LENGTH_SHORT).show();

                }


                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("OnPause","onpause state");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.show();
            title = (EditText)dialog.findViewById(R.id.editText);
            description = (EditText)dialog.findViewById(R.id.editText2);
            save = (Button)dialog.findViewById(R.id.button2);
            cancel = (Button)dialog.findViewById(R.id.button);
            picker = (DatePicker)dialog.findViewById(R.id.datePicker);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoData data = new TodoData();

                        data.setTitle(title.getText().toString());
                        data.setDescription(description.getText().toString());
                        int m = picker.getMonth() + 1;
                        d = String.valueOf(picker.getYear() + "/" + m + "/" + picker.getDayOfMonth());
                        Toast.makeText(MainActivity.this, "" + d, Toast.LENGTH_SHORT).show();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

                    data.setDate(d);
                        data.setStatus(0);
                        helper.insertData(data);

                        todoDataList.add(data);

                        listView.setAdapter(adapter);
                    }

            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    adapter.notifyDataSetChanged();
                }
            });
            return true;
        }else if (id==R.id.stat){
            Intent intent = new Intent(MainActivity.this, CompletedTaskList.class);
                    startActivity(intent);
                }

        return super.onOptionsItemSelected(item);
    }


}
