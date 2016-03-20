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

        refresh = (Button) findViewById(R.id.refresh);

        message = (TextView) findViewById(R.id.message);

        modeClassic.setOnCheckedChangeListener(this);
        modeShifting.setOnCheckedChangeListener(this);
        bgRipple.setOnCheckedChangeListener(this);
        bgStatic.setOnCheckedChangeListener(this);

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
