package com.example.vincentho.lab2;

import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder choosePic ;
    String[] p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        choosePic = new AlertDialog.Builder(this);
        ImageView image = (ImageView) findViewById(R.id.sysuicon);
        final RadioButton student = (RadioButton) findViewById(R.id.id1);
        final RadioButton teacher = (RadioButton) findViewById(R.id.id2);
        final RadioGroup chooseID = (RadioGroup) findViewById(R.id.radioGroup);
        p1 = new String[]{"拍摄", "从相册选择"};

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                choosePic.setTitle("上传头像")
                        .setItems(p1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(getApplicationContext(), p1[which], Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "cancel喔", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
            }
        });




        chooseID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedID){
                if(checkedID == R.id.id1){
                    //Toast.makeText(getApplication(), "你选择了学生", Toast.LENGTH_SHORT).show();
                    Snackbar.make(chooseID, " 你选择了学生", Snackbar.LENGTH_LONG)
                            .setAction("确定", new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(getApplicationContext(), "按确定托啊", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
                else if(checkedID == R.id.id2){
                    //Toast.makeText(getApplication(), "你选择了教师", Toast.LENGTH_SHORT).show();
                    Snackbar.make(chooseID, " 你选择了教师", Snackbar.LENGTH_LONG)
                            .setAction("确定", new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(getApplicationContext(), "按确定托啊", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            }
        });

        Button signinbtn = (Button) findViewById(R.id.signup);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(student.isChecked())
                    Snackbar.make(chooseID, "未开通学生注册功能，请过主", Snackbar.LENGTH_LONG)
                            .setAction("确定", new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(getApplicationContext(), "按确定托啊", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                else if(teacher.isChecked())
                    Snackbar.make(chooseID, " 教职工注册也没开通，lan", Snackbar.LENGTH_LONG)
                            .setAction("确定", new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(getApplicationContext(), "按确定托啊", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
            }
        });

        Button loginbtn = (Button) findViewById(R.id.login);
        final EditText input1 = (EditText) findViewById(R.id.inputID);
        final EditText input2 = (EditText) findViewById(R.id.inputPassword);
        final TextInputLayout user = (TextInputLayout) findViewById(R.id.ID);
        final TextInputLayout pass = (TextInputLayout) findViewById(R.id.password);
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(TextUtils.isEmpty(input1.getText()) && !TextUtils.isEmpty(input2.getText())){
                    user.setError("学号不能为空");
                    user.setErrorEnabled(true);
                    pass.setErrorEnabled(false);
                }
                else if(TextUtils.isEmpty(input2.getText()) && !TextUtils.isEmpty(input1.getText())){
                    pass.setError("密码不能为空");
                    pass.setErrorEnabled(true);
                    user.setErrorEnabled(false);
                }
                else if (TextUtils.isEmpty(input1.getText()) && TextUtils.isEmpty(input2.getText())){
                    user.setError("学号不能为空");
                    pass.setError("密码不能为空");
                    pass.setErrorEnabled(true);
                    user.setErrorEnabled(true);
                }
                else{
                    pass.setErrorEnabled(false);
                    user.setErrorEnabled(false);
                    if(input1.getText().toString().equals("123456") && input2.getText().toString().equals("6666"))
                        Snackbar.make(chooseID, "成功login啊", Snackbar.LENGTH_LONG)
                                .setAction("确定", new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v){
                                        Toast.makeText(getApplicationContext(), "按确定托啊", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                    else
                        Snackbar.make(chooseID, "账号密码唔match啊", Snackbar.LENGTH_LONG)
                                .setAction("确定", new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v){
                                        Toast.makeText(getApplicationContext(), "按确定托啊", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                }
            }
        });
    }

}
