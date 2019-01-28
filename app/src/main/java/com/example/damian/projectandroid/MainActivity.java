package com.example.damian.projectandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.damian.projectandroid.Models.User;
import com.michaldrabik.tapbarmenulib.TapBarMenu;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    TextView usernameTV;
    User activeUser = new User();
    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activeUser = (User) getIntent().getSerializableExtra("logedUser");
        usernameTV = (TextView) findViewById(R.id.usernameTV);
        usernameTV.setText(activeUser.getUsername());

        System.out.println(activeUser.getEmail());
        System.out.println(activeUser.getUsername());
        System.out.println(activeUser.getPassword());

        ButterKnife.bind(this);
    }

    @OnClick(R.id.tapBarMenu) public void onMenuButtonClick() {
        tapBarMenu.toggle();
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.register){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.login){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void aboutMe(View view){
        Intent intent = new Intent(this, AbouteMe.class);
        startActivity(intent);
    }

    public void myCart(View view){
        Intent intent = new Intent(this, MyCart.class);
        intent.putExtra("loggedUser",activeUser);
        startActivity(intent);
    }

    public void showCategories(View view){
        Intent intent = new Intent(this, CategoriesActivity.class);
        intent.putExtra("loggedUser",activeUser);
        startActivity(intent);
    }

}
