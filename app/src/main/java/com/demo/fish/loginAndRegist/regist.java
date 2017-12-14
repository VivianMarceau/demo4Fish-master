package com.demo.fish.loginAndRegist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.fish.R;
import com.demo.fish.app.main.ui.MainActivity;

import static android.Manifest.permission.READ_CONTACTS;
public class regist extends AppCompatActivity implements View.OnClickListener {
    private String string3 = "";
    private String string4 = "";
    private EditText username;
    private EditText  password;
    private EditText registUser;
    private EditText  registPass;
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button forgive_pwd;
    private Button bt_pwd_eye;
    private Button login;
    private Button regist;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
//        ActionBar actionbar = getSupportActionBar();
//        if (actionbar != null)
//            actionbar.hide();
        initView();
    }
    private void initView() {

        username = (EditText) findViewById(R.id.registUser);
        // 监听文本框内容变化
        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String user = username.getText().toString().trim();
//                Toast.makeText(regist.this,user,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password = (EditText) findViewById(R.id.registPass);
        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String pwd = password.getText().toString().trim();
//                Toast.makeText(regist.this,pwd,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        regist = (Button) findViewById(R.id.regist);
        regist.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.regist:
                String name="";
                EditText editText1 =(EditText)findViewById(R.id.registUser);
                name=editText1.getText().toString();
                String pwd="";
                EditText editText2 =(EditText)findViewById(R.id.registPass);
                pwd=editText2.getText().toString();
                String pwd1="";
                EditText editText3 =(EditText)findViewById(R.id.registPass1);
                pwd1=editText3.getText().toString();
                if (name.equals("") || pwd.equals("")||pwd1.equals("")) {
                    // 弹出消息框
                    Toast.makeText(regist.this, "用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
                }else if(!pwd.equals(pwd1)){
                    Toast.makeText(regist.this, "两次密码不一致！",Toast.LENGTH_SHORT).show();
                    editText2.setText("");
                    editText3.setText("");
                }else{
                login.setOnClickListener(new regist.registeListener());
                }
                break;
            default:
                break;
        }
    }
    class registeListener implements View.OnClickListener,Runnable{
        public void run() {
            EditText username1 = (EditText) findViewById(R.id.registUser);
            EditText password1 = (EditText) findViewById(R.id.registPass);
            String username2=  username1.getText().toString().trim();
            String password2=  password1.getText().toString().trim();
            Connection con = null;
            int count = 0;
            try{

                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection
                        ("jdbc:mysql://47.95.249.131:3306/FindTwo","two","two");
                try{
                    PreparedStatement prest=null;
                    String sql;
                    sql
                            = "INSERT INTO User VALUES ( ?,?)";

                    prest=con.prepareStatement(sql);
                    prest.setString(1, username2);
                    prest.setString(2, password2);
                    int row = prest.executeUpdate();
                    if(row > 0){
                        Toast.makeText(regist.this, "注册成功！",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(regist.this,login.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(regist.this, "该用户名已存在！",Toast.LENGTH_SHORT).show();
                        registUser = (EditText) findViewById(R.id.registUser);
                        registPass = (EditText) findViewById(R.id.registPass);
                        registUser.setText("");
                        registPass.setText("");
                    }

                    System.out.println("Number of records: " + count);
                    prest.close();
                    con.close();
                }
                catch (SQLException s){
                    System.out.println("SQL statement is not executed!");

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }


        }

        @Override
        public void onClick(View view) {

            Thread thread = new Thread(this);
            thread.start();

        }
    }
}