package com.ashokvarma.bottomnavigation.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @since 10 Jul 2017
 */
public class HomeActivityJava extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, BottomNavigationBar.OnTabSelectedListener, AdapterView.OnItemSelectedListener {

    BottomNavigationBar bottomNavigationBar;

    FloatingActionButton fabHome;

    Spinner modeSpinner;
    Spinner shapeSpinner;
    Spinner itemSpinner;
    Spinner bgSpinner;
    CheckBox autoHide;

    Button toggleHide;
    Button toggleBadge;

    TextView message;
    TextView scrollableText;

    int lastSelectedPosition = 0;

    TextFragment fragment1;
    TextFragment fragment2;
    TextFragment fragment3;
    TextFragment fragment4;
    TextFragment fragment5;
    TextFragment fragment6;

    @Nullable
    TextBadgeItem numberBadgeItem;

    @Nullable
    ShapeBadgeItem shapeBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        fabHome = findViewById(R.id.fab_home);

        modeSpinner = findViewById(R.id.mode_spinner);
        bgSpinner = findViewById(R.id.bg_spinner);
        shapeSpinner = findViewById(R.id.shape_spinner);
        itemSpinner = findViewById(R.id.item_spinner);
        autoHide = findViewById(R.id.auto_hide);

        toggleHide = findViewById(R.id.toggle_hide);
        toggleBadge = findViewById(R.id.toggle_badge);

        message = findViewById(R.id.message);

        fragment1 = TextFragmentKt.newTextFragmentInstance(getString(R.string.para1));
        fragment2 = TextFragmentKt.newTextFragmentInstance(getString(R.string.para2));
        fragment3 = TextFragmentKt.newTextFragmentInstance(getString(R.string.para3));
        fragment4 = TextFragmentKt.newTextFragmentInstance(getString(R.string.para4));
        fragment5 = TextFragmentKt.newTextFragmentInstance(getString(R.string.para5));
        fragment6 = TextFragmentKt.newTextFragmentInstance(getString(R.string.para6));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"MODE_DEFAULT", "MODE_FIXED", "MODE_SHIFTING", "MODE_FIXED_NO_TITLE", "MODE_SHIFTING_NO_TITLE"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);
        modeSpinner.setSelection(2);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"3 items", "4 items", "5 items"});
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemAdapter);
        itemSpinner.setSelection(2);

        ArrayAdapter<String> shapeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"SHAPE_OVAL", "SHAPE_RECTANGLE", "SHAPE_HEART", "SHAPE_STAR_3_VERTICES", "SHAPE_STAR_4_VERTICES", "SHAPE_STAR_5_VERTICES", "SHAPE_STAR_6_VERTICES"});
        shapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeSpinner.setAdapter(shapeAdapter);
        shapeSpinner.setSelection(5);

        ArrayAdapter<String> bgAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"BACKGROUND_STYLE_DEFAULT", "BACKGROUND_STYLE_STATIC", "BACKGROUND_STYLE_RIPPLE"});
        bgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bgSpinner.setAdapter(bgAdapter);
        bgSpinner.setSelection(1);

        modeSpinner.setOnItemSelectedListener(this);
        bgSpinner.setOnItemSelectedListener(this);
        shapeSpinner.setOnItemSelectedListener(this);
        itemSpinner.setOnItemSelectedListener(this);
        autoHide.setOnCheckedChangeListener(this);

        toggleHide.setOnClickListener(this);
        toggleBadge.setOnClickListener(this);
        fabHome.setOnClickListener(this);

        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github:
                String url = "https://github.com/Ashok-Varma/BottomNavigation";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toggle_hide:
                if (bottomNavigationBar != null) {
                    bottomNavigationBar.toggle();
                }
                break;
            case R.id.toggle_badge:
                if (numberBadgeItem != null) {
                    numberBadgeItem.toggle();
                }
                if (shapeBadgeItem != null) {
                    shapeBadgeItem.toggle();
                }
                break;
            case R.id.fab_home:
                final Snackbar snackbar = Snackbar.make(message, "Fab Clicked", Snackbar.LENGTH_LONG);
                snackbar.setAction("dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        refresh();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        refresh();
    }

    private void refresh() {

        bottomNavigationBar.clearAll();
//        bottomNavigationBar.setFab(fabHome, BottomNavigationBar.FAB_BEHAVIOUR_TRANSLATE_AND_STICK);
        bottomNavigationBar.setFab(fabHome);

        setScrollableText(lastSelectedPosition);

        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(autoHide.isChecked());

        shapeBadgeItem = new ShapeBadgeItem()
                .setShape(shapeSpinner.getSelectedItemPosition())
                .setShapeColorResource(R.color.teal)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(autoHide.isChecked());

        bottomNavigationBar.setMode(modeSpinner.getSelectedItemPosition());
        bottomNavigationBar.setBackgroundStyle(bgSpinner.getSelectedItemPosition());


        switch (itemSpinner.getSelectedItemPosition()) {
            case 0:
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "Nearby").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "Find").setActiveColorResource(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Categories").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                        .setFirstSelectedPosition(lastSelectedPosition > 2 ? 2 : lastSelectedPosition)
                        .initialise();
                break;
            case 1:
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                        .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                        .initialise();
                break;
            case 2:
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                        .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey))
                        .setFirstSelectedPosition(lastSelectedPosition)
                        .initialise();
                break;
        }
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        setMessageText(position + " Tab Selected");
        if (numberBadgeItem != null) {
            numberBadgeItem.setText(Integer.toString(position));
        }
        setScrollableText(position);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
        setMessageText(position + " Tab Reselected");
    }

    private void setMessageText(String messageText) {
        message.setText(messageText);
    }

    private void setScrollableText(int position) {
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment1).commitAllowingStateLoss();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment2).commitAllowingStateLoss();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment3).commitAllowingStateLoss();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment4).commitAllowingStateLoss();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment5).commitAllowingStateLoss();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment6).commitAllowingStateLoss();
                break;
        }
    }
}
