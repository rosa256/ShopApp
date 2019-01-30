package com.example.damian.projectandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.damian.projectandroid.Models.User;

public class LoginActivity extends AppCompatActivity {
    ShopDbHelper shopDbHelper;
    TextView usernameTextView, passwordTextView;
    Button loginButton,createAccountButton;
    TextView warningView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        shopDbHelper = new ShopDbHelper(this);


        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        passwordTextView= (TextView) findViewById(R.id.passwordTextView);

        loginButton = (Button) findViewById(R.id.button_login);
        createAccountButton = (Button) findViewById(R.id.create_account);

        warningView = (TextView) findViewById(R.id.warnUser);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shopDbHelper.checkUser(
                    usernameTextView.getText().toString(),
                    passwordTextView.getText().toString())) {

                    Intent accountsIntent = new Intent(getApplicationContext(), MainActivity.class);
                    User logedUser = shopDbHelper.getUser(
                            usernameTextView.getText().toString(),
                            passwordTextView.getText().toString()
                    );
                    accountsIntent.putExtra("logedUser",logedUser);
                    emptyInputEditText();
                    warningView.setVisibility(View.GONE);

                startActivity(accountsIntent);

                }else{
                warningView.setVisibility(View.VISIBLE);
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
    private void emptyInputEditText() {
        passwordTextView.setText(null);
        usernameTextView.setText(null);
    }

}
