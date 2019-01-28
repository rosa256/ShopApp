package com.example.damian.projectandroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.damian.projectandroid.Adapters.ItemAdapter;
import com.example.damian.projectandroid.Models.Item;
import com.example.damian.projectandroid.Models.User;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {
    private ShopDbHelper shopDbHelper;
    private User loggedUser;
    private ArrayList<Item> myItems;
    private boolean isMyCart = true;
    public static Double totalPrice = 0.0;
    boolean isCartEmpty;

    private ListView itemsListView;
    private LinearLayout linearLayoutPrice;
    private RelativeLayout relativeLayoutPrice;
    public static TextView totalPriceTextView;
    private Button clearCartButton;
    private TextView deleteItemButton;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        loggedUser = (User) getIntent().getSerializableExtra("loggedUser");
        shopDbHelper = new ShopDbHelper(this);

        linearLayoutPrice = (LinearLayout) findViewById(R.id.linear_cart_layout);
        relativeLayoutPrice = (RelativeLayout) findViewById(R.id.relative_cart_layout);
        itemsListView = (ListView) findViewById(R.id.myItems_listView);
        totalPriceTextView = (TextView) findViewById(R.id.cart_total_textView);
        clearCartButton = (Button) findViewById(R.id.clear_cart_button);

        deleteItemButton = (TextView) findViewById(R.id.delete_from_cardButton);

        myItems = shopDbHelper.getItemsFromCard(loggedUser.get_id());



        if (!myItems.isEmpty()) {
            final ItemAdapter adapter = new ItemAdapter(this, myItems, (User) getIntent().getSerializableExtra("loggedUser"), isMyCart);
            itemsListView.setAdapter(adapter);


            isCartEmpty = false;
            changeContent(isCartEmpty);

            for (Item i : myItems)
                totalPrice += i.getPrice();

            totalPrice = totalPrice * 100;
            totalPrice = Double.valueOf(Math.round(totalPrice));
            totalPrice = totalPrice / 100;
            totalPriceTextView.setText("Podsumowanie: "+ totalPrice +"zł");

            clearCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Item i : myItems)
                        shopDbHelper.deleteFromCart(loggedUser.get_id(),i.get_id());
                    adapter.notifyDataSetChanged();
                    isCartEmpty = true;
                    changeContent(isCartEmpty);
                }
            });


        } else {
            isCartEmpty = true;
            changeContent(isCartEmpty);
        }
    }

    private void changeContent(boolean isCartEmpty) {
        if(isCartEmpty == true){
            System.out.println("tuuuure");
            relativeLayoutPrice.setVisibility(View.VISIBLE);
            linearLayoutPrice.setVisibility(View.GONE);
        }else{
            System.out.println("faaalsidło");
            relativeLayoutPrice.setVisibility(View.GONE);
            linearLayoutPrice.setVisibility(View.VISIBLE);
        }
    }


    public TextView getPriceTextView(){
        return totalPriceTextView;
    }

}
