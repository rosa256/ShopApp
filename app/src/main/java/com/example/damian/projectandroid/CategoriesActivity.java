package com.example.damian.projectandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.damian.projectandroid.Adapters.CategoryAdapter;
import com.example.damian.projectandroid.Models.Category;
import com.example.damian.projectandroid.Models.User;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesActivity extends AppCompatActivity {
    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    ShopDbHelper shopDbHelper = new ShopDbHelper(this);
    private User activeUser;

    ArrayList<Category> categoryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);

        activeUser = (User) getIntent().getSerializableExtra("loggedUser");
        categoryArrayList = shopDbHelper.getCategories();


        CategoryAdapter adapter = new CategoryAdapter(this, categoryArrayList,(User) getIntent().getSerializableExtra("loggedUser"));

        ListView itemsListView  = (ListView) findViewById(R.id.category_ListView);
        itemsListView.setAdapter(adapter);
    }

    @OnClick(R.id.tapBarMenu) public void onMenuButtonClick() {
        tapBarMenu.toggle();
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
