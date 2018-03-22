package com.example.user.todolist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 06-11-2017.
 */

public class MyAdapter extends ArrayAdapter<TodoData> {
    Context context;
    int resource;
    List<TodoData>dataList=new ArrayList<>();
    LayoutInflater inflater;
    TodoData todoData;
    public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TodoData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        dataList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        todoData = dataList.get(position);
        inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource,null,false);
        TextView textTopDate = (TextView)view.findViewById(R.id.textView);
        TextView textTitle = (TextView)view.findViewById(R.id.textView2);
        TextView textDescrip = (TextView)view.findViewById(R.id.textView3);
        TextView texDate =(TextView)view.findViewById(R.id.tv4);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        textTopDate.setText(todoData.getDate());
        textTitle.setText(todoData.getTitle());
        textDescrip.setText(todoData.getDescription());
        texDate.setText(todoData.getDate());
        DBHelper helper = new DBHelper(context);
        int stat=helper.getTodoData().get(position).getStatus();
        if(stat==0){
            todoData.setImage(R.drawable.incomplete);
            imageView.setImageResource(todoData.getImage());
        }else{
            todoData.setImage(R.drawable.completed);
            imageView.setImageResource(todoData.getImage());
        }


        return view;
    }

    @Override
    public int getCount()
    {
        return dataList.size();
    }
}
