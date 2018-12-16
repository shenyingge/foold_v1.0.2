package com.example.foold;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

    private Button mregister_go;
    private EditText muserid;
    private EditText mpwd;
    private EditText mpwd2;
    private MyDatabaseHelper myDatabaseHelper;
    private Button mbackbBtn;


    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.regist);

        mregister_go = (Button)findViewById(R.id.regist_reg);
        muserid = (EditText)findViewById(R.id.username_reg);
        mpwd = (EditText)findViewById(R.id.password_reg);
        mpwd2 = (EditText)findViewById(R.id.confirm1_reg);
        mbackbBtn = (Button)findViewById(R.id.back_reg);

        mbackbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
        mregister_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterSave();
            }
        });
    }

    private void RegisterSave(){

        String userId = muserid.getText().toString();
        String password1 = mpwd.getText().toString();
        String password2 = mpwd2.getText().toString();

        boolean creatUser = true;
        if( userId.equals("") || password1.equals("") || password2.equals("")){
            Toast.makeText(this,"请输入完整的注册内容",Toast.LENGTH_SHORT).show();
        }else if (!password1.equals(password2)){
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
        }else if (password1.length()<6){
            Toast.makeText(this,"密码小于六位数",Toast.LENGTH_SHORT).show();
        }else{
            myDatabaseHelper = new MyDatabaseHelper(Register.this,"foold.db",null,2);
            SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.query("user",new String[]{"id"},null,null,null,null,null);

            while (cursor.moveToNext()){
                if(userId.equals(cursor.getString(cursor.getColumnIndex("id")))){
                    Toast.makeText(this,"该用户已存在",Toast.LENGTH_SHORT).show();
                    creatUser = false;
                }
            }

            if (creatUser){
                ContentValues values = new ContentValues();
                values.put("id",userId);
                values.put("password",password1);
                db.insert("user",null,values);
                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
