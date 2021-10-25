package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView Info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLogin();
            }
        });
    }

    public void myLogin()
    {
        EditText usernameCtrl = (EditText) findViewById(R.id.editText2);
        EditText passwordCtrl = (EditText) findViewById(R.id.editText3);
        Info = (TextView) findViewById(R.id.tvid1);
        String username = usernameCtrl.getText().toString();
        String password = passwordCtrl.getText().toString();

        if(!username.isEmpty() && !password.isEmpty())// if username and password fields are entered
        {
            if(username.equals("Admin")&&password.equals("Admin"))//if username and password matched with user credentials
            {
                Intent redirect = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(redirect);// redirect to home page
            }
            else
            {
                Info.setText("Invalid Login Credentials");//if invalid credentials
            }
        }
        else
        {
            Info.setText("Please Enter Username and Password");//pop up user clicks login button with blank fields
        }
    }
}