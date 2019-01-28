package com.example.damian.projectandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.damian.projectandroid.Models.User;

import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    ShopDbHelper shopDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        shopDbHelper = new ShopDbHelper(this);

        Button registerButton;
        final TextView emailTextView, usernameTextView ,passwordTextView;
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        registerButton = (Button) findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextView.getText().toString();
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                shopDbHelper.addUser(new User(username,email,password));

                emailTextView.setText("");
                usernameTextView.setText("");
                passwordTextView.setText("");

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
