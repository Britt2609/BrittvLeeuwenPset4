package com.example.britt.brittvleeuwenpset4;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by britt on 20-11-2017.
 */

public class TodoAdapter extends ResourceCursorAdapter {

    public TodoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_todo, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView todo = view.findViewById(R.id.textView);
        CheckBox completed = view.findViewById(R.id.checkBox);

        Integer index_title = cursor.getColumnIndex("title");
        Integer index_completed = cursor.getColumnIndex("completed");

        String value_title = cursor.getString(index_title);
        Integer value_completed = cursor.getInt(index_completed);

        todo.setText(value_title);
        if (value_completed == 0) {
            completed.setChecked(false);
        }
        else {
            completed.setChecked(true);
        }
    }
}
