package com.example.vincentho.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText enter_password, confirm_password;
    public Button ok_btn, clear_btn;
    private SharedPreferences sharedPreferences;
    private String mode = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if(sharedPreferences.getString("password", "").length() != 0){
            confirm_password.setVisibility(View.GONE);
            enter_password.setHint("password");
            mode = "registered";
        }

        button_listner();
    }

    void init(){
        enter_password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        ok_btn = findViewById(R.id.ok_btn);
        clear_btn = findViewById(R.id.clear_btn);
        sharedPreferences = getSharedPreferences("Vincent_preferences", MODE_PRIVATE);
        mode = "unregistered";
    }
    
    void button_listner(){
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("unregistered")){
                    String new_password = enter_password.getText().toString();
                    String confirm = confirm_password.getText().toString();
                    if(confirm.isEmpty())
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    else if(!new_password.equals(confirm))
                        Toast.makeText(MainActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                    else{
                        // save password in sharePreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("password", new_password);
                        editor.commit();
                        startActivity(new Intent(MainActivity.this, fileEditor.class));
                    }
                }
                else if(mode.equals("registered")){
                    String password = sharedPreferences.getString("password", "");
                    if(!enter_password.getText().toString().equals(password))
                        Toast.makeText(MainActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                    else
                        startActivity(new Intent(MainActivity.this, fileEditor.class));
                }
            }
        });
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_password.setText("");
                confirm_password.setText("");
            }
        });
    }
}
