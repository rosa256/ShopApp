package com.example.damian.projectandroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.projectandroid.Adapters.ItemAdapter;
import com.example.damian.projectandroid.Models.Item;
import com.example.damian.projectandroid.Models.User;
import com.squareup.seismic.ShakeDetector;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity implements ShakeDetector.Listener {
    private ShopDbHelper shopDbHelper;
    private User loggedUser;
    private ArrayList<Item> myItems;
    public static Double totalPrice = 0.0;
    boolean isCartEmpty;
    public static boolean isDiscount = false;
    private boolean isMyCart = true;

    private ListView itemsListView;
    private LinearLayout linearLayoutPrice;
    private RelativeLayout relativeLayoutPrice;
    public static TextView totalPriceTextView;
    private Button clearCartButton;
    private Button discountButton;
    private Button orderButton;
    private AlertDialog dialog;
    private TextView deleteItemButton;
    private String priceMessage="";
    private ShakeDetector shakeDetector;
    private ItemAdapter adapter;

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
        discountButton = (Button) findViewById(R.id.discountButton);
        orderButton = (Button) findViewById(R.id.orderButton);

        deleteItemButton = (TextView) findViewById(R.id.delete_from_cardButton);

        myItems = shopDbHelper.getItemsFromCard(loggedUser.get_id());
        isDiscount=false;


        if (!myItems.isEmpty()) {
            adapter = new ItemAdapter(this, myItems, (User) getIntent().getSerializableExtra("loggedUser"), isMyCart);
            itemsListView.setAdapter(adapter);


            isCartEmpty = false;
            changeContent(isCartEmpty);

            setTotalCost();


            if(isDiscount)
                priceMessage = getApplicationContext().getString(R.string.totalPrice)+ totalPrice + getApplicationContext().getString(R.string.currenecy)+" -5%";
            else
                priceMessage = getApplicationContext().getString(R.string.totalPrice)+ totalPrice + getApplicationContext().getString(R.string.currenecy);

            totalPriceTextView.setText(priceMessage);

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

            discountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog(MyCart.this,getApplicationContext().getString(R.string.discount),getApplicationContext().getString(R.string.shake_to_discount));
                }
            });

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isDiscount)
                        showAlertDialog(
                                MyCart.this,getApplicationContext().getString(R.string.order),
                                getApplicationContext().getString(R.string.orderMSG) +(totalPrice*0.95) + " "+getApplicationContext().getString(R.string.currenecy),
                                getApplicationContext().getString(R.string.order_confirm),
                                getApplicationContext().getString(R.string.order_cancel));
                    else
                        showAlertDialog(
                                MyCart.this,getApplicationContext().getString(R.string.order),
                                getApplicationContext().getString(R.string.orderMSG) +totalPrice + " "+getApplicationContext().getString(R.string.currenecy),
                                getApplicationContext().getString(R.string.order_confirm),
                                getApplicationContext().getString(R.string.order_cancel));
                }
            });
        } else {
            isCartEmpty = true;
            changeContent(isCartEmpty);
        }
    }

    private void setTotalCost() {
        totalPrice=0.0;

        for (Item i : myItems)
            totalPrice += i.getPrice();

        totalPrice = totalPrice * 100;
        totalPrice = Double.valueOf(Math.round(totalPrice));
        totalPrice = totalPrice / 100;
    }

    private void changeContent(boolean isCartEmpty) {
        if(isCartEmpty == true){
            relativeLayoutPrice.setVisibility(View.VISIBLE);
            linearLayoutPrice.setVisibility(View.GONE);
        }else{
            relativeLayoutPrice.setVisibility(View.GONE);
            linearLayoutPrice.setVisibility(View.VISIBLE);
        }
    }

    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);
        shakeDetector.setSensitivity(10);

        dialog = builder.create();
        dialog.show();
    }

    public void showAlertDialog(final Context context, String title, String message, String posBtnMsg, String negBtnMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBtnMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (Item item: myItems)
                shopDbHelper.deleteFromCart(loggedUser.get_id(),item.get_id());

                isCartEmpty = true;
                changeContent(isCartEmpty);

                Toast.makeText(context,getApplicationContext().getString(R.string.ordered),Toast.LENGTH_SHORT).show();

                dialog.cancel();
            }
        });
        builder.setNegativeButton(negBtnMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public TextView getPriceTextView(){
        return totalPriceTextView;
    }

    @Override
    public void hearShake() {
        dialog.cancel();
        Toast.makeText(this,getApplicationContext().getString(R.string.getDiscountMSG),Toast.LENGTH_SHORT).show();
        isDiscount=true;
        totalPriceTextView.setText(getApplicationContext().getString(R.string.totalPrice)+ totalPrice + getApplicationContext().getString(R.string.currenecy)+" -5%");
        shakeDetector.stop();

    }
}
