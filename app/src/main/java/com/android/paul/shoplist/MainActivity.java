package com.android.paul.shoplist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<ShopElement> shopList = new ArrayList<ShopElement>();
    String fileName = "IngredientListee";

    TableLayout stk;
    EditText inputQuantity;
    EditText inputIngredient;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(findViewById(R.id.content_main));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void init() {
        stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setPadding(24,24,24,24);
        tv0.setText("Need");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setPadding(24,24,24,24);
        tv1.setText("Quantity");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setPadding(24,24,24,24);
        tv2.setText("Product");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        stk.addView(tbrow0);
        stk.setBackgroundColor(Color.WHITE);

        List<ShopElement> shopElements = null;
        try{
            shopElements = readJsonStream(new FileInputStream(getFilesDir() + fileName));
        }
        catch(IOException ex) {
            System.out.println("problem reading");
        }
        if(shopElements != null)
        {
            for(ShopElement shopElement : shopElements)
            {
                addRow(shopElement.getQuantity().getNumber(), shopElement.getIngredient().getName());
            }
        }
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        addButton = (Button) popupView.findViewById(R.id.popupButton);
        addButton.setEnabled(false);
        inputIngredient = (EditText) popupView.findViewById(R.id.inputIngredient);
        inputQuantity = (EditText) popupView.findViewById(R.id.inputQuantity);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRow(inputQuantity.getText().toString(), inputIngredient.getText().toString());
                popupWindow.dismiss();
            }
        });

        inputQuantity.addTextChangedListener (new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
                checkRequiredFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        inputIngredient.addTextChangedListener (new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
                checkRequiredFields();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
    }

    public void addRow(String quantity, String ingredient) {
        TableRow tbrow = new TableRow(this);
        CheckBox checkBox = new CheckBox(this);
        checkBox.setGravity(Gravity.CENTER);
        tbrow.addView(checkBox);
        TextView t1v = new TextView(this);
        t1v.setText(quantity);
        t1v.setTextColor(Color.BLACK);
        t1v.setGravity(Gravity.CENTER);
        tbrow.addView(t1v);
        TextView t2v = new TextView(this);
        t2v.setText(ingredient);
        t2v.setTextColor(Color.BLACK);
        t2v.setGravity(Gravity.CENTER);
        tbrow.addView(t2v);
        stk.addView(tbrow);
        try{
            ShopElement newShopElement = new ShopElement(new Ingredient(ingredient), new Quantity(quantity, "g"));
            shopList.add(newShopElement);
            writeJsonStream(new FileOutputStream(getFilesDir() + fileName), shopList);
        }
        catch(IOException ex)
        {
            System.out.println("problem writing");
        }

    }

    private void checkRequiredFields() {
        if (!inputIngredient.getText().toString().isEmpty() && !inputQuantity.getText().toString().isEmpty()) {
            addButton.setEnabled(true);
        } else {
            addButton.setEnabled(false);
        }
    }






    public void writeJsonStream(OutputStream out, List<ShopElement> message) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeMessagesArray(writer, message);
        writer.close();
    }
    public void writeMessagesArray(JsonWriter writer, List<ShopElement> messages) throws IOException {
        writer.beginArray();
        for (ShopElement message : messages) {
            writeMessage(writer, message);
        }
        writer.endArray();
    }
    public void writeMessage(JsonWriter writer, ShopElement message) throws IOException {
        writer.beginObject();
        writer.name("ingredient").value(message.getIngredient().getName());
        writer.name("quantity");
        writeQuantity(writer, message.getQuantity());
        writer.endObject();
    }
    public void writeQuantity(JsonWriter writer, Quantity quantity) throws IOException {
        writer.beginObject();
        writer.name("number").value(quantity.getNumber());
        writer.name("unit").value(quantity.getUnit());
        writer.endObject();
    }





    public List<ShopElement> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<ShopElement> readMessagesArray(JsonReader reader) throws IOException {
        List<ShopElement> messages = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public ShopElement readMessage(JsonReader reader) throws IOException {
        Quantity quantity = new Quantity("","");
        Ingredient ingredient = new Ingredient("");

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("quantity")) {
                quantity = readQuantity(reader);
            } else if (name.equals("ingredient")) {
                ingredient.setName(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ShopElement(ingredient, quantity);
    }

    public Quantity readQuantity(JsonReader reader) throws IOException {
        String number = "";
        String unit = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("number")) {
                number = reader.nextString();
            } else if (name.equals("unit")) {
                unit = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Quantity(number, unit);
    }
}
