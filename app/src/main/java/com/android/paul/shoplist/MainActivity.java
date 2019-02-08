package com.android.paul.shoplist;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import com.android.paul.shoplist.Entities.Ingredient;
import com.android.paul.shoplist.Entities.MenuElement;
import com.android.paul.shoplist.Entities.Quantity;
import com.android.paul.shoplist.Entities.ShopElement;
import com.android.paul.shoplist.Recycler.RecyclerItemTouchHelper;
import com.android.paul.shoplist.Recycler.RecyclerViewAdapter;
import com.android.paul.shoplist.Recycler.RecyclerViewHolder;
import com.android.paul.shoplist.Recycler.RecyclerViewMenuAdapter;
import com.android.paul.shoplist.filecommunication.Reader;
import com.android.paul.shoplist.filecommunication.Writer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerViewMenuAdapter adapterMenu;
    private View mainView;
    private EditText inputIngredient;
    private Button addButton;
    private FloatingActionButton fab;
    String fileName = "ShopListeeeeeee";
    String quantityNum = "";
    String quantityUnit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        mainView = findViewById(R.id.content_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        initData();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
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
            //liste de courses
            // Handle the camera action
            recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
            //recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            adapter = new RecyclerViewAdapter();
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);

            initData();

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            fab = (FloatingActionButton) findViewById(R.id.fab);
            //fab.setImageResource(R.drawable);
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
            fab.show();

        } else if (id == R.id.nav_gallery) {
            //mes menus
            //Intent gameActivity = new Intent(MainActivity.this, MenuActivity.class);
            //startActivity(gameActivity);
            adapterMenu = new RecyclerViewMenuAdapter(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapterMenu);

            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL) {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    // Do not draw the divider
                }
            });

            initDataMenu();

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            fab.hide();


        } else if (id == R.id.nav_slideshow) {
            // mon stock
            //liste de courses
            // Handle the camera action
            recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
            //recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            adapter = new RecyclerViewAdapter();
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);

            initData();

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_menu_camera);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                    //onButtonShowPopupWindowClick(findViewById(R.id.content_main));
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            fab.show();

        } else if (id == R.id.nav_manage) {
            //suggestions

            adapterMenu = new RecyclerViewMenuAdapter();
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapterMenu);

            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL) {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    // Do not draw the divider
                }
            });

            initDataMenu();

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            fab.hide();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initData() {
        try{
            adapter.setShopList(Reader.readJsonStream(new FileInputStream(getFilesDir() + fileName)));
        }
        catch(IOException ex) {
            System.out.println("problem reading");
        }
    }

    public void initDataMenu() {
        // try {
        List<MenuElement> menuList = new ArrayList<MenuElement>();
        List<ShopElement> shopElementList = new ArrayList<ShopElement>();
        shopElementList.add(new ShopElement(new Ingredient("Spaghetti"), new Quantity("500","g")));
        shopElementList.add(new ShopElement(new Ingredient("Tomates"), new Quantity("200","g")));
        shopElementList.add(new ShopElement(new Ingredient("Viande de boeuf"), new Quantity("200","g")));
        shopElementList.add(new ShopElement(new Ingredient("Sel"), new Quantity("","")));
        menuList.add(new MenuElement(shopElementList, "Spaghetti Bolognaise"));
        menuList.add(new MenuElement(shopElementList, "Boeuf bourguignon"));

        adapterMenu.setMenuList(menuList);

        //adapter.setMenuList(Reader.readJsonStream(new FileInputStream(getFilesDir() + menuFileName)));
        //} catch (IOException ex) {
        //  System.out.println("problem reading");
        // }
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

        View outPopup = popupView.findViewById(R.id.outPopup);
        // dismiss the popup window when touched
        outPopup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        final View popupContent = popupView.findViewById(R.id.popupContent);
        popupContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(popupContent.getWindowToken(), 0);

                //Utils.hideSoftKeyboard();
                return true;
            }
        });

        addButton = (Button) popupView.findViewById(R.id.popupButton);
        addButton.setEnabled(false);
        inputIngredient = (EditText) popupView.findViewById(R.id.inputIngredient);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRow(new ShopElement(new Ingredient(inputIngredient.getText().toString()), new Quantity(quantityNum, quantityUnit)));
                popupWindow.dismiss();
                quantityNum = "";
                quantityUnit = "";
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


        NumberPicker nbPicker = popupView.findViewById(R.id.nbPicker);
        //Initializing a new string array with elements
        final String[] values= {"---","1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11", "12", "100", "250", "500"};
        //Populate NumberPicker values from String array values
        //Set the minimum value of NumberPicker
        nbPicker.setMinValue(0); //from array first value
        //Specify the maximum value/number of NumberPicker
        nbPicker.setMaxValue(values.length-1); //to array last value
        //Specify the NumberPicker data source as array elements
        nbPicker.setDisplayedValues(values);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        nbPicker.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        nbPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                if(!values[newVal].equals("---")){
                    quantityNum = values[newVal];
                }
                else{
                    quantityNum = "";
                }
            }
        });

        NumberPicker nbPicker2 = popupView.findViewById(R.id.nbPicker2);
        //Initializing a new string array with elements
        final String[] values2= {"---","g", "kg"};
        //Populate NumberPicker values from String array values
        //Set the minimum value of NumberPicker
        nbPicker2.setMinValue(0); //from array first value
        //Specify the maximum value/number of NumberPicker
        nbPicker2.setMaxValue(values2.length-1); //to array last value
        //Specify the NumberPicker data source as array elements
        nbPicker2.setDisplayedValues(values2);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        nbPicker2.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        nbPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                if(!values2[newVal].equals("---")){
                    quantityUnit = values2[newVal];
                }
                else{
                    quantityUnit = "";
                }
            }
        });

    }

    public void addRow(ShopElement newShopElement) {
        try{
            adapter.addItem(newShopElement);
            Writer.writeJsonStream(new FileOutputStream(getFilesDir() + fileName), adapter.getShopList());
        }
        catch(IOException ex)
        {
            System.out.println("problem writing");
        }
    }

    private void checkRequiredFields() {
        if (!inputIngredient.getText().toString().isEmpty()) {
            addButton.setEnabled(true);
        } else {
            addButton.setEnabled(false);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof RecyclerViewHolder) {
            // get the removed item name to display it in snack bar
            String deletedName = adapter.getItem(viewHolder.getAdapterPosition()).getIngredient().getName();

            // backup of removed item for undo purpose
            final ShopElement deletedItem = adapter.getItem(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            try{
                adapter.removeItem(viewHolder.getAdapterPosition());
                Writer.writeJsonStream(new FileOutputStream(getFilesDir() + fileName), adapter.getShopList());
            }
            catch(IOException ex)
            {
                System.out.println("problem writing");
            }

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(mainView, deletedName + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    try{
                        adapter.restoreItem(deletedItem, deletedIndex);
                        Writer.writeJsonStream(new FileOutputStream(getFilesDir() + fileName), adapter.getShopList());
                    }
                    catch(IOException ex)
                    {
                        System.out.println("problem writing");
                    }
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }









    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
