package com.example.foold;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {


    private TextView mnewuser;
    private Button mlogin_log;
    private EditText musername_log;
    private EditText mpassword_log;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.login);

        musername_log = (EditText)findViewById(R.id.username_log);
        mpassword_log = (EditText)findViewById(R.id.password_log);

        mnewuser = (TextView)findViewById(R.id.newUser);
        mlogin_log = (Button)findViewById(R.id.login_log);

        mlogin_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId=musername_log.getText().toString();
                String password=mpassword_log.getText().toString();
                if(userId.equals("")||password.equals("")){
                    Toast.makeText(Login.this,"请输入id和密码",Toast.LENGTH_SHORT).show();
                }else {
                    db = new MyDatabaseHelper(Login.this, "foold.db", null, 2).getReadableDatabase();
                    Cursor cursor=db.query("user",new String[]{"id","password"},null,null,null,null,null);
                    boolean login=false;
                    //从数据库中匹配id和密码
                    while(cursor.moveToNext()){
                        if(userId.equals(cursor.getString(cursor.getColumnIndex("id")))&&
                                password.equals(cursor.getString(cursor.getColumnIndex("password")))){
                            login=true;
                            break;
                        }
                    }
                    if (login) {
                        Intent intent = new Intent(Login.this, WenjuanActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "id或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });






    }




}
