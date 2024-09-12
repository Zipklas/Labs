package com.example.laba1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.laba1.database.Db;
import com.example.laba1.database.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText Email;
    EditText Password;
    Button button;
    HashMap<String, String> empass = new HashMap<String, String>();
    Db db;
    public static int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email=findViewById(R.id.editTextTextPersonName);
        Password=findViewById(R.id.editTextPassword);
        button=findViewById(R.id.button);
        db = Room.databaseBuilder(getApplicationContext(),Db.class,"Db").build();
        String[] emails = getResources().getStringArray(R.array.emails);
        String[] password = getResources().getStringArray(R.array.password);
        for(int i =0;i<5;i++)
        {
            empass.put(emails[i],password[i]);
            User newUser = new User(emails[i], password[i]);
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    User user = db.getUserDao().getUser(newUser.login);
                    if (user == null) {
                        db.getUserDao().addUser(newUser);
                    }
                };
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void Permit(View view) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> usersBd = db.getUserDao().getAllUser();
                for (User currentUser : usersBd) {
                    empass.put(currentUser.getLogin(),currentUser.getPassword());
                    Log.i("user" ,""+currentUser.getId() + "   " + currentUser.getLogin() + "  " + currentUser.getPassword() + '\n');
                }
                id = db.getUserDao().getUserId(Email.getText().toString());
            }
        });
        thread.start();
        thread.join();
        if(empass.containsKey(Email.getText().toString()))
        {

            String pass1 = Password.getText().toString();
            if(Objects.equals(pass1,empass.get(Email.getText().toString())))
            {
                Userid.user_id = id;
                Intent bottomIntent =new Intent(this,BottomActivity.class);
                startActivity(bottomIntent);
            }
            else
            {
                Email.setTextColor(Color.RED);
                Password.setTextColor(Color.RED);
            }
        }
        else
        {
            Email.setTextColor(Color.RED);
            Password.setTextColor(Color.RED);
        }
    }
}