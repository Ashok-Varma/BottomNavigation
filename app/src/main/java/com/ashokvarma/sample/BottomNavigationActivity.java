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
    BottomNavigationItem biHome;
    BottomNavigationItem biMusic;

    CheckBox modeClassic;
    CheckBox modeShifting;
    CheckBox bgStatic;
    CheckBox bgRipple;
    CheckBox items3;
    CheckBox items4;
    CheckBox items5;

    Button toggleHide;
    Button toggleBadge;

    TextView message;
    TextView scrollableText;

    int lastSelectedPosition = 0;

    boolean hidden = false;

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

        toggleHide = (Button) findViewById(R.id.toggle_hide);
        toggleBadge = (Button) findViewById(R.id.toggle_badge);

        message = (TextView) findViewById(R.id.message);
        scrollableText = (TextView) findViewById(R.id.scrollable_text);

        modeClassic.setOnCheckedChangeListener(this);
        modeShifting.setOnCheckedChangeListener(this);
        bgRipple.setOnCheckedChangeListener(this);
        bgStatic.setOnCheckedChangeListener(this);
        items3.setOnCheckedChangeListener(this);
        items4.setOnCheckedChangeListener(this);
        items5.setOnCheckedChangeListener(this);

        toggleHide.setOnClickListener(this);
        toggleBadge.setOnClickListener(this);

        bottomNavigationBar.setTabSelectedListener(this);

        refresh();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toggle_hide) {
            if (bottomNavigationBar != null) {
                if (hidden) {
                    bottomNavigationBar.unHide();
                } else {
                    bottomNavigationBar.hide();
                }
                hidden = !hidden;
            }
        }
        if (v.getId() == R.id.toggle_badge) {
            if (bottomNavigationBar != null) {
                if (bottomNavigationBar.getBadgeCountFromTab(0) == null || bottomNavigationBar.getBadgeCountFromTab(0).equalsIgnoreCase("0")) {
                    bottomNavigationBar.setBadgeCountOnTab(0, "2");
                } else {
                    bottomNavigationBar.setBadgeCountOnTab(0, "0");
                }
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
        refresh();
    }

    private void refresh() {

        bottomNavigationBar.clearAll();

        setScrollableText(lastSelectedPosition);

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
                    .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "Location").setActiveColor(R.color.orange).setBadgeCount(2))
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
                    .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColor(R.color.orange)
                            .setBadgeCount(2)
                            .setBadgeDrawable(R.drawable.new_badge_background))
                    .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColor(R.color.teal))
                    .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColor(R.color.blue))
                    .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColor(R.color.brown))
                    .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColor(R.color.grey))
                    .setFirstSelectedPosition(lastSelectedPosition)
                    .initialise();
        }
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        message.setText(position + " Tab Selected");
        setScrollableText(position);
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

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        message.setText(position + " Tab Reselected");
    }
}
