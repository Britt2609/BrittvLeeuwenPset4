package com.example.britt.brittvleeuwenpset4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.britt.brittvleeuwenpset4.TodoDatabase.getInstance;

public class MainActivity extends AppCompatActivity {

    private static String TABLE_NAME = "todos";
    ListView list;
    Button add;
    EditText todo;
    TodoDatabase Database;
    TodoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todo = findViewById(R.id.todo);
        add = findViewById(R.id.add);
        list = findViewById(R.id.list);
        Database = TodoDatabase.getInstance(getApplicationContext());

        Cursor cursor = Database.selectAll();
        adapter = new TodoAdapter(getApplicationContext(), cursor);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener());
        list.setOnItemLongClickListener(new OnItemLongClickListener());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = todo.getText().toString();
                if (item.length() != 0) {
                    addItem(item, false);
                    todo.setText("");
                    updateData();
                }
                else {
                    Toast.makeText(MainActivity.this, "please add a todo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData() {
        Cursor cursor = Database.selectAll();
        adapter = new TodoAdapter(getApplicationContext(), cursor);
        list.setAdapter(adapter);
    }

    public void addItem(String title, Boolean completed) {
        Database = TodoDatabase.getInstance(this);
        boolean addItem = Database.insert(title, completed);

        if (addItem) {
            Toast.makeText(MainActivity.this, "item added", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "item not added", Toast.LENGTH_SHORT).show();
        }
    }


    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Database.Delete(id);
            updateData();
            return true;
        }
    }
    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CheckBox checkBox = view.findViewById(R.id.checkBox);
            boolean status = checkBox.isChecked();
            Database.Update(id, status);
            updateData();
        }
    }
}
