package com.ashokvarma.bottomnavigation.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar bottomNavigationBar;

    FloatingActionButton fabHome;

    CheckBox modeFixed;
    CheckBox modeShifting;
    CheckBox bgStatic;
    CheckBox bgRipple;
    CheckBox showText;
    CheckBox items3;
    CheckBox items4;
    CheckBox items5;
    CheckBox autoHide;

    Button toggleHide;
    Button toggleBadge;

    TextView message;
    TextView scrollableText;

    int lastSelectedPosition = 0;

    boolean showTextFlag = true;

    BadgeItem badgeItem;

    ShapeBadgeItem circularBadgeItem, sqaureBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        fabHome = (FloatingActionButton) findViewById(R.id.fab_home);

        modeFixed = (CheckBox) findViewById(R.id.mode_fixed);
        modeShifting = (CheckBox) findViewById(R.id.mode_shifting);
        bgStatic = (CheckBox) findViewById(R.id.bg_static);
        bgRipple = (CheckBox) findViewById(R.id.bg_ripple);
        showText = (CheckBox) findViewById(R.id.show_text);
        items3 = (CheckBox) findViewById(R.id.items_3);
        items4 = (CheckBox) findViewById(R.id.items_4);
        items5 = (CheckBox) findViewById(R.id.items_5);
        autoHide = (CheckBox) findViewById(R.id.auto_hide);

        toggleHide = (Button) findViewById(R.id.toggle_hide);
        toggleBadge = (Button) findViewById(R.id.toggle_badge);

        message = (TextView) findViewById(R.id.message);
        scrollableText = (TextView) findViewById(R.id.scrollable_text);

        modeFixed.setOnCheckedChangeListener(this);
        modeShifting.setOnCheckedChangeListener(this);
        bgRipple.setOnCheckedChangeListener(this);
        bgStatic.setOnCheckedChangeListener(this);
        showText.setOnCheckedChangeListener(this);
        items3.setOnCheckedChangeListener(this);
        items4.setOnCheckedChangeListener(this);
        items5.setOnCheckedChangeListener(this);
        autoHide.setOnCheckedChangeListener(this);

        toggleHide.setOnClickListener(this);
        toggleBadge.setOnClickListener(this);
        fabHome.setOnClickListener(this);

        bottomNavigationBar.setTabSelectedListener(this);

        refresh();
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
        if (v.getId() == R.id.toggle_hide) {
            if (bottomNavigationBar != null) {
                bottomNavigationBar.toggle();
            }
        } else if (v.getId() == R.id.toggle_badge) {
            if (badgeItem != null) {
                badgeItem.toggle();
            }
            if(circularBadgeItem != null){
                circularBadgeItem.toggle();
            }
        } else if (v.getId() == R.id.fab_home) {
            final Snackbar snackbar = Snackbar.make(message, "Fab Clicked", Snackbar.LENGTH_LONG);
            snackbar.setAction("dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.bg_ripple:
                bgStatic.setChecked(!isChecked);
                break;
            case R.id.bg_static:
                bgRipple.setChecked(!isChecked);
                break;
            case R.id.mode_fixed:
                modeShifting.setChecked(!isChecked);
                break;
            case R.id.mode_shifting:
                modeFixed.setChecked(!isChecked);
                break;
            case R.id.show_text:
                showTextFlag = isChecked;
                break;
            case R.id.items_3:
                if (isChecked) {
                    items4.setChecked(false);
                    items5.setChecked(false);
                }
                break;
            case R.id.items_4:
                if (isChecked) {
                    items3.setChecked(false);
                    items5.setChecked(false);
                }
                break;
            case R.id.items_5:
                if (isChecked) {
                    items4.setChecked(false);
                    items3.setChecked(false);
                }
                break;
        }
        if (!items5.isChecked() && !items3.isChecked() && !items4.isChecked()) {
            buttonView.setChecked(true);
        }
        refresh();
    }

    private void refresh() {

        bottomNavigationBar.clearAll();
//        bottomNavigationBar.setFab(fabHome, BottomNavigationBar.FAB_BEHAVIOUR_TRANSLATE_AND_STICK);
        bottomNavigationBar.setFab(fabHome);

        setScrollableText(lastSelectedPosition);

        badgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("2")
                .setHideOnSelect(true)
                .setHideOnSelect(autoHide.isChecked());

        /*
        * Sample to show how to add a dot like badge issue #109
        * */
        circularBadgeItem =   new ShapeBadgeItem()
                //.setBorderWidth(8)
                .setBackgroundColorResource(R.color.red)
                .setDimen(8) // the size to badge in dp
                .setShape(ShapeBadgeItem.CIRCLE) // Choose between ShapeBadgeItem.CIRCLE or ShapeBadgeItem.SQUARE
                .setMargins(0, 2, 4, 0) // left, top, right, bottom
                .setHideOnSelect(autoHide.isChecked());

        sqaureBadgeItem =   new ShapeBadgeItem()
                //.setBorderWidth(8)
                .setBackgroundColorResource(R.color.teal)
                .setDimen(8) // the size to badge in dp
                .setShape(ShapeBadgeItem.SQUARE) // Choose between ShapeBadgeItem.CIRCLE or ShapeBadgeItem.SQUARE
                .setMargins(0, 2, 4, 0) // left, top, right, bottom
                .setHideOnSelect(autoHide.isChecked());

        if (modeFixed.isChecked()) {
            bottomNavigationBar
                    .setMode(BottomNavigationBar.MODE_FIXED);
        } else if (modeShifting.isChecked()) {
            bottomNavigationBar
                    .setMode(BottomNavigationBar.MODE_SHIFTING);
        }

        if (bgStatic.isChecked()) {
            bottomNavigationBar
                    .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        } else if (bgRipple.isChecked()) {
            bottomNavigationBar
                    .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        }

        if (items3.isChecked()) {
            if (showTextFlag) {
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "Nearby").setActiveColorResource(R.color.orange).setTextBadgeItem(badgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "Find").setActiveColorResource(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Categories").setActiveColorResource(R.color.blue))
                        .setFirstSelectedPosition(lastSelectedPosition > 2 ? 2 : lastSelectedPosition)
                        .initialise();
            } else {
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp).setActiveColorResource(R.color.orange).setTextBadgeItem(badgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp).setActiveColorResource(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp).setActiveColorResource(R.color.blue))
                        .setFirstSelectedPosition(lastSelectedPosition > 2 ? 2 : lastSelectedPosition)
                        .initialise();
            }
        } else if (items4.isChecked()) {
            if (showTextFlag) {
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setTextBadgeItem(badgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue))
                        .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                        .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                        .initialise();
            } else {
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp).setActiveColorResource(R.color.orange).setTextBadgeItem(badgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp).setActiveColorResource(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp).setActiveColorResource(R.color.blue))
                        .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp).setActiveColorResource(R.color.brown))
                        .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                        .initialise();
            }
        } else if (items5.isChecked()) {
            if (showTextFlag) {
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setTextBadgeItem(badgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal).setShapeBadgeItem(circularBadgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setShapeBadgeItem(sqaureBadgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                        .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey))
                        .setFirstSelectedPosition(lastSelectedPosition)
                        .initialise();
            } else {
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp).setActiveColorResource(R.color.orange).setTextBadgeItem(badgeItem))
                        .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp).setActiveColorResource(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp).setActiveColorResource(R.color.blue))
                        .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp).setActiveColorResource(R.color.brown))
                        .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp).setActiveColorResource(R.color.grey))
                        .setFirstSelectedPosition(lastSelectedPosition)
                        .initialise();
            }
        }
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        setMessageText(position + " Tab Selected");
        if (badgeItem != null) {
            badgeItem.setText(Integer.toString(position));
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
                scrollableText.setText(R.string.para1);
                break;
            case 1:
                scrollableText.setText(R.string.para2);
                break;
            case 2:
                scrollableText.setText(R.string.para3);
                break;
            case 3:
                scrollableText.setText(R.string.para4);
                break;
            case 4:
                scrollableText.setText(R.string.para5);
                break;
            default:
                scrollableText.setText(R.string.para6);
                break;
        }
    }
}
