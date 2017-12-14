package com.demo.fish.loginAndRegist;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.fish.R;
import com.demo.fish.app.main.ui.MainActivity;
import com.facebook.common.internal.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends AppCompatActivity implements View.OnClickListener{
    private String string3="";
    private String string4="";
    private EditText username, password;
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button forgive_pwd;
    private Button bt_pwd_eye;
    private Button login;
    private Button register;
    private boolean isOpen = false;
    String name="";
    String pwd="";
    EditText editText1;
    EditText editText2;
    boolean isUser=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null)
            actionbar.hide();
        initView();
    }
    private void initView() {

        username = (EditText) findViewById(R.id.username);
        // 监听文本框内容变化
        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String user = username.getText().toString().trim();
                if ("".equals(user)) {
                    // 用户名为空,设置按钮不可见
                    bt_username_clear.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    bt_username_clear.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password = (EditText) findViewById(R.id.password);
        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String pwd = password.getText().toString().trim();
                if ("".equals(pwd)) {
                    // 用户名为空,设置按钮不可见
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        bt_username_clear.setOnClickListener(this);

        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        bt_pwd_clear.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        forgive_pwd = (Button) findViewById(R.id.forgive_pwd);
        forgive_pwd.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_username_clear:
                // 清除登录名
                username.setText("");
                break;
            case R.id.bt_pwd_clear:
                // 清除密码
                password.setText("");
                break;
            case R.id.login:
                // TODO 登录按钮
                editText1 =(EditText)findViewById(R.id.username);
                name=editText1.getText().toString();
                editText2 =(EditText)findViewById(R.id.password);
                pwd=editText2.getText().toString();
                if (name.equals("") || pwd.equals("")) {
                    // 弹出消息框
                    Toast.makeText(login.this, "用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                } else {
                    login.setOnClickListener(new LoginListener());
                }

                break;
            case R.id.register:
                // 注册按钮
//                Toast.makeText(login.this, "注册",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(login.this,regist.class);
                startActivity(intent);
                break;
            case R.id.forgive_pwd:
                // 忘记密码按钮
                Toast.makeText(login.this, "忘记密码",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
    class LoginListener implements View.OnClickListener,Runnable{
        public void run() {
            EditText username1 = (EditText) findViewById(R.id.username);
            EditText password1 = (EditText) findViewById(R.id.password);
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
                            = "SELECT username,userpass  FROM User where username=? and userpass=?";

                    prest=con.prepareStatement(sql);
                    prest.setString(1, username2);
                    prest.setString(2, password2);

                    ResultSet rs = prest.executeQuery();
                    while (rs.next()){
                        string3=rs.getString(1);
                        string4=rs.getString(2);
                        count++;
                        System.out.println(string3+' '+string4);
                        isUser=true;
                    }
                    if (string3.equals(username2)&string4.equals(password2)){
                        System.out.println("successful");
                        Intent intent=new Intent(login.this,MainActivity.class);
                        startActivity(intent);
                    }
                    if(!isUser) {
                        Toast.makeText(login.this,"用户名或密码不正确！",Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
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


//
//package com.demo.fish.loginAndRegist;
//
//import android.database.Cursor;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.demo.fish.R;
//import com.demo.fish.db.DBOper;
//
//import java.io.DataOutputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class login extends AppCompatActivity implements View.OnClickListener {
//    private EditText username, password;
//    private Button bt_username_clear;
//    private Button bt_pwd_clear;
//    private Button forgive_pwd;
//    private Button bt_pwd_eye;
//    private Button login;
//    private Button register;
//    private boolean isOpen = false;
//    private String string1="";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        ActionBar actionbar=getSupportActionBar();
//        if(actionbar!=null)
//            actionbar.hide();
//        initView();
//    }
//    private void initView() {
//
//        username = (EditText) findViewById(R.id.username);
//        // 监听文本框内容变化
//        username.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                // 获得文本框中的用户
//                String user = username.getText().toString().trim();
//                if ("".equals(user)) {
//                    // 用户名为空,设置按钮不可见
//                    bt_username_clear.setVisibility(View.INVISIBLE);
//                } else {
//                    // 用户名不为空，设置按钮可见
//                    bt_username_clear.setVisibility(View.VISIBLE);
//                }
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        password = (EditText) findViewById(R.id.password);
//        password.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                // 获得文本框中的用户
//                String pwd = password.getText().toString().trim();
//                if ("".equals(pwd)) {
//                    // 用户名为空,设置按钮不可见
//                    bt_pwd_clear.setVisibility(View.INVISIBLE);
//                } else {
//                    // 用户名不为空，设置按钮可见
//                    bt_pwd_clear.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
//        bt_username_clear.setOnClickListener(this);
//
//        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
//        bt_pwd_clear.setOnClickListener(this);
//
//        login = (Button) findViewById(R.id.login);
//
//
//        register = (Button) findViewById(R.id.register);
//        register.setOnClickListener(this);
//
//        forgive_pwd = (Button) findViewById(R.id.forgive_pwd);
//        forgive_pwd.setOnClickListener(this);
//
//    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_username_clear:
//                // 清除登录名
//                username.setText("");
//                break;
//            case R.id.bt_pwd_clear:
//                // 清除密码
//                password.setText("");
//                break;
//            case R.id.login:
//                // TODO 登录按钮
//                login.setOnClickListener(new LoginListener());
//                break;
//            case R.id.register:
//                // 注册按钮
//                Toast.makeText(login.this, "注册",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.forgive_pwd:
//                // 忘记密码按钮
//                Toast.makeText(login.this, "忘记密码",Toast.LENGTH_SHORT).show();
//                break;
//
//            default:
//                break;
//        }
//
//
//    }
//    class LoginListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            // TODO Auto-generated method stub
//            String name = username.getText().toString();
//            String pwd = password.getText().toString();
//            if (name.equals("") || pwd.equals("")) {
//                // 弹出消息框
//                new AlertDialog.Builder(login.this).setTitle("错误")
//                        .setMessage("帐号或密码不能空").setPositiveButton("确定", null)
//                        .show();
//            } else {
//                isUserinfo(name, pwd);
//            }
//        }
//        // 判断输入的用户是否正确
//        public Boolean isUserinfo(String name, String pwd){
//            Connection con = null;
//            Statement statement = null;
//            ResultSet rs = null;
//            int count = 0;
//            boolean isNull = false;   //根据用户名查询数据库是否查到
//            try{
//                Class.forName("com.mysql.jdbc.Driver");
//                con = DriverManager.getConnection
//                        ("jdbc:mysql://47.95.249.131:3306/FindTwo","two","two");
//                try{
//                    statement = con.createStatement();
//                    rs = statement.executeQuery("select userpass from User where username='" + name + "'");
//                    ;
//                    while (rs.next()){
//                        String backPassword;
//                        backPassword = rs.getString("userpass");
//                        if (backPassword.equals(pwd)) {
//                            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(getApplicationContext(),"密码不正确",Toast.LENGTH_SHORT).show();
//                        }
//                        isNull=true;
//                    }
//                    if(!isNull){
//                        Toast.makeText(getApplicationContext(),"用户不存在",Toast.LENGTH_SHORT).show();
//                        rs.close();
//                        statement.close();
//                        con.close();
//                    }else {
//                        System.out.println("数据库连接失败");
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//
//                }
//            }
//            catch (Exception s){
//                System.out.println("SQL statement is not executed!");
//            }
//            return false;
//        }
//    }
//
//}
