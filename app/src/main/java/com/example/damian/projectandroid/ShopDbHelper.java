package com.example.damian.projectandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.damian.projectandroid.Models.Category;
import com.example.damian.projectandroid.Models.Item;
import com.example.damian.projectandroid.Models.User;

import java.util.ArrayList;

public class ShopDbHelper extends SQLiteOpenHelper {

    private final ArrayList<Category> categoryList = new ArrayList<Category>();
    private final ArrayList<Item> itemList = new ArrayList<Item>();

    private static final String DATABASE_NAME = "ShopDb";
    private static final String TABLE_ITEMS = "itemsTable";
    private static final String TABLE_CATEGORY = "categoryTable";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BANNER_IMAGE = "bannerImage";
    private static final String KEY_BANNER_ITEM = "bannerItem";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_CATEGORY_FK = "categoryFK";
    private static final String KEY_USER_FK = "userFK";
    private static final String KEY_ITEM_FK = "itemFK";
    private static final String TABLE_USER_ITEM = "user_item";


    private static final String TABLE_USER = "userTable";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";


    private static final int DATABASE_VERSION = 1;

    public ShopDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE "+ TABLE_USER+ "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);


        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT, "
                + KEY_BANNER_IMAGE + " INTEGER " + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT, "
                + KEY_PRICE + " DOUBLE, "
                + KEY_BANNER_ITEM + " INTEGER, "
                + KEY_CATEGORY_FK + " INTEGER, "
                + " FOREIGN KEY ("+KEY_CATEGORY_FK+") REFERENCES "+TABLE_CATEGORY+"("+ KEY_ID +"));";
        db.execSQL(CREATE_ITEMS_TABLE);

        String CREATE_USER_ITEM = "CREATE TABLE " + TABLE_USER_ITEM + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_FK + " INTEGER, "
                + KEY_ITEM_FK + " INTEGER, "
                + " FOREIGN KEY ("+KEY_USER_FK+") REFERENCES "+TABLE_USER+"("+ KEY_ID +"),"
                + " FOREIGN KEY ("+KEY_ITEM_FK+") REFERENCES "+TABLE_ITEMS+"("+ KEY_ID +"));";
        db.execSQL(CREATE_USER_ITEM);

        populateDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, category.getCategoryTitle()); // Category Title
        values.put(KEY_BANNER_IMAGE, category.getCategoryImageBanner()); // CAtegory Image

        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close(); // Closing database connection
    }

    public void addToUserCard(int userFK, int itemFK) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USER_FK, userFK); // Category Title
        values.put(KEY_ITEM_FK, itemFK); // CAtegory Image

        // Inserting Row
        db.insert(TABLE_USER_ITEM, null, values);
        db.close(); // Closing database connection
    }


    public ArrayList<Category> getCategories() {
        try {
            categoryList.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setCategory_id(Integer.parseInt(cursor.getString(0)));
                    category.setCategoryTitle(cursor.getString(1));
                    category.setCategoryImageBanner(Integer.parseInt(cursor.getString(2)));
                    // Adding category to list
                    categoryList.add(category);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return categoryList;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_word", "" + e);
        }

        return categoryList;
    }

    public void addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail()); // User Emial
        values.put(KEY_USERNAME, user.getUsername()); // User username
        values.put(KEY_PASSWORD, user.getPassword()); // User password

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }


    public User getFirstUser() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " +  TABLE_USER, null);

        cursor.moveToFirst();

        User u = new User(
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD))
                );


        db.close(); // Closing database connection
        return u;
    }

    public ArrayList<Item> getItemsFromCategory(int catId){
        itemList.clear();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_ITEMS + " WHERE categoryFK = '"+ catId +"'", null);

        cursor.moveToFirst();

        do{
            itemList.add(new Item(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID))),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICE))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORY_FK))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_BANNER_ITEM)))
            ));
        }while (cursor.moveToNext());


        return itemList;
    }

    public ArrayList<Item> getItemsFromCard(int userFK){
        itemList.clear();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_ITEMS + " i,"+ TABLE_USER_ITEM +" ui WHERE i._id = ui.itemFK AND ui.userFK = '"+ userFK +"'", null);

        cursor.moveToFirst();

        System.out.println("CCCCCCCOOOOOUNT: "+ cursor.getCount());
        if(cursor.getCount() != 0)
        do{
            itemList.add(new Item(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ITEM_FK))),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICE))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORY_FK))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_BANNER_ITEM)))
            ));
        }while (cursor.moveToNext());


        return itemList;
    }


    public User getUser(String username, String password){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_USER + " WHERE username = '"+ username +"' AND password = '"+password+"'", null);
        cursor.moveToFirst();
        User user = new User(
                Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID))),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD))
                );
        return user;
    }

    public boolean checkUser(String username, String password) {

        // array of columns to fetch
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = KEY_USERNAME + " = ?" + " AND " + KEY_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {username, password};

        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public void deleteFromCart(int userFK, int itemFK) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_ITEM, KEY_USER_FK + " = ? AND " +KEY_ITEM_FK + " = ?",
                new String[]{String.valueOf(userFK), String.valueOf(itemFK)});
        db.close();
    }



    public void populateDB(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        ArrayList<Category> categriesInitList = new ArrayList<>();

        Category c1 = new Category("Komputery i Laptopy", R.drawable.computer_and_laptops);
        Category c2 = new Category("Procesory", R.drawable.procesors_banner);
        Category c3 = new Category("Płyty Główne", R.drawable.motherboard_banner);
        Category c5 = new Category("Klawiatury", R.drawable.keyboard_banner);
        Category c4 = new Category("Myszki ", R.drawable.mouse_banner);

        categriesInitList.add(c1);
        categriesInitList.add(c2);
        categriesInitList.add(c3);
        categriesInitList.add(c4);
        categriesInitList.add(c5);

        for (Category category : categriesInitList) {
            values.put(KEY_TITLE, category.getCategoryTitle()); // Category Title
            values.put(KEY_BANNER_IMAGE, category.getCategoryImageBanner()); // CAtegory Image
            db.insert(TABLE_CATEGORY,null,values);
        }
        values.clear();
        ArrayList<Item> itemsInitList = new ArrayList<>();

        Item i1 = new Item("Laptop Acer Predator",2500.0,1,R.drawable.acer_predator);
        Item i2 = new Item("Laptop Hp Paque",2999.99,1,R.drawable.acer_predator);
        Item i3 = new Item("Laptop Acer Premium",3250.0,1,R.drawable.acer_predator);

        Item i4 = new Item("Procesor Intel i7",1440.0,2,R.drawable.procesor);
        Item i5 = new Item("Procesor Ryzen AMD",980.99,2,R.drawable.procesor);
        Item i6 = new Item("Procesor Intel i5",570.80,2,R.drawable.procesor);

        itemsInitList.add(i1);
        itemsInitList.add(i2);
        itemsInitList.add(i3);
        itemsInitList.add(i4);
        itemsInitList.add(i5);
        itemsInitList.add(i6);

        for(Item item: itemsInitList){
            values.put(KEY_NAME, item.getName());
            values.put(KEY_PRICE, item.getPrice());
            values.put(KEY_CATEGORY_FK, item.getCategoryFK());
            values.put(KEY_BANNER_ITEM, item.getItemBanner());
            db.insert(TABLE_ITEMS,null,values);
        }
    }
}

