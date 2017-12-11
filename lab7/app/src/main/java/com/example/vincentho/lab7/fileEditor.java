package com.example.vincentho.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fileEditor extends AppCompatActivity {

    public EditText file_name, file_content;
    public Button save_btn, load_btn, clear_content_btn, delete_btn;
    public ListView listView;
    private List<String> File_list;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_edit_layout);

        init();
        Button_listener();
    }

    void init(){
        file_name = findViewById(R.id.filename);
        file_content = findViewById(R.id.file_content);
        save_btn = findViewById(R.id.save_btn);
        load_btn = findViewById(R.id.load_btn);
        clear_content_btn = findViewById(R.id.file_clear_btn);
        delete_btn = findViewById(R.id.delete_btn);
        listView = findViewById(R.id.file_list);
        File_list = new ArrayList<String>();

        // add existing files to File_list
        String[] files = fileList();
        for(String file : files)
            File_list.add(file);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.file_item, File_list);
        listView.setAdapter(arrayAdapter);
    }

    boolean fileAlreadyExist(String file){
        for(int i = 0; i < File_list.size(); i++)
            if(File_list.get(i).equals(file))
                return true;
        return false;
    }

    void Button_listener(){
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = file_name.getText().toString();
                try{
                    if(!fileAlreadyExist(file)){
                        FileOutputStream fileOutputStream = openFileOutput(file, MODE_PRIVATE);
                        fileOutputStream.write(file_content.getText().toString().getBytes());
                        File_list.add(file);
                        arrayAdapter.notifyDataSetChanged();
                        Toast.makeText(fileEditor.this, "save file successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(fileEditor.this, "file has already existed", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e){
                    Toast.makeText(fileEditor.this, "file name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = file_name.getText().toString();
                try{
                    FileInputStream fileInputStream = openFileInput(file);
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    file_content.setText(new String(contents));
                    Toast.makeText(fileEditor.this, "load file successfully", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e){
                    Toast.makeText(fileEditor.this, "file doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear_content_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_content.setText("");
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = file_name.getText().toString();
                if(deleteFile(file)){
                    file_content.setText("");
                    file_name.setText("");
                    File_list.remove(file);
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(fileEditor.this, "delete file successfully", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(fileEditor.this, "fails to delete file", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String file = File_list.get(position);
                file_name.setText(file);
                try{
                    FileInputStream fileInputStream = openFileInput(file);
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    file_content.setText(new String(contents));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
