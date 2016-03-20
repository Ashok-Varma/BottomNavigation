package com.ashokvarma.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class BottomNavigationActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, BottomNavigationBar.OnTabSelectedListener {


    BottomNavigationBar bottomNavigationBar;

    CheckBox modeClassic;
    CheckBox modeShifting;
    CheckBox bgStatic;
    CheckBox bgRipple;
    CheckBox items3;
    CheckBox items4;
    CheckBox items5;

    Button refresh;

    TextView message;

    int lastSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        modeClassic = (CheckBox) findViewById(R.id.mode_classic);
        modeShifting = (CheckBox) findViewById(R.id.mode_shifting);
        bgStatic = (CheckBox) findViewById(R.id.bg_static);
        bgRipple = (CheckBox) findViewById(R.id.bg_ripple);
        items3 = (CheckBox) findViewById(R.id.items_3);
        items4 = (CheckBox) findViewById(R.id.items_4);
        items5 = (CheckBox) findViewById(R.id.items_5);

        refresh = (Button) findViewById(R.id.refresh);

        message = (TextView) findViewById(R.id.message);

        modeClassic.setOnCheckedChangeListener(this);
        modeShifting.setOnCheckedChangeListener(this);
        bgRipple.setOnCheckedChangeListener(this);
        bgStatic.setOnCheckedChangeListener(this);
        items3.setOnCheckedChangeListener(this);
        items4.setOnCheckedChangeListener(this);
        items5.setOnCheckedChangeListener(this);

        refresh.setOnClickListener(this);

        bottomNavigationBar.setTabSelectedListener(this);

        onClick(refresh);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.refresh) {
            bottomNavigationBar.clearAll();

            if (modeClassic.isChecked()) {
                bottomNavigationBar.setMode(BottomNavigationBar.MODE_CLASSIC);
            } else if (modeShifting.isChecked()) {
                bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
            }

            if (bgStatic.isChecked()) {
                bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
            } else if (bgRipple.isChecked()) {
                bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
            }

            if (items3.isChecked()) {
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "Location").setActiveColor(R.color.orange))
                        .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "Find").setActiveColor(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Favorites").setActiveColor(R.color.blue))
                        .setFirstSelectedPosition(lastSelectedPosition > 2 ? 2 : lastSelectedPosition)
                        .initialise();
            } else if (items4.isChecked()) {
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColor(R.color.orange))
                        .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColor(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColor(R.color.blue))
                        .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColor(R.color.brown))
                        .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                        .initialise();
            } else {
                items5.setChecked(true);
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColor(R.color.orange))
                        .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColor(R.color.teal))
                        .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColor(R.color.blue))
                        .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColor(R.color.brown))
                        .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColor(R.color.grey))
                        .setFirstSelectedPosition(lastSelectedPosition)
                        .initialise();
            }

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
            case R.id.mode_classic:
                modeShifting.setChecked(!isChecked);
                break;
            case R.id.mode_shifting:
                modeClassic.setChecked(!isChecked);
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
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        message.setText(position + " Tab Selected");
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        message.setText(position + " Tab Reselected");
    }
}
