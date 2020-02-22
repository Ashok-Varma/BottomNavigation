package com.ashokvarma.bottomnavigation.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class HomeActivity() : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener, BottomNavigationBar.OnTabSelectedListener, AdapterView.OnItemSelectedListener, Parcelable {

    // Views
    private lateinit var bottomNavigationBar: BottomNavigationBar

    private lateinit var fabHome: FloatingActionButton

    private lateinit var modeSpinner: Spinner
    private lateinit var shapeSpinner: Spinner
    private lateinit var itemSpinner: Spinner
    private lateinit var bgSpinner: Spinner
    private lateinit var autoHide: CheckBox

    private lateinit var toggleHide: Button
    private lateinit var toggleBadge: Button

    private lateinit var message: TextView

    private lateinit var fragment1: TextFragment
    private lateinit var fragment2: TextFragment
    private lateinit var fragment3: TextFragment
    private lateinit var fragment4: TextFragment
    private lateinit var fragment5: TextFragment
    private lateinit var fragment6: TextFragment

    // Variables
    private var lastSelectedPosition = 0

    private lateinit var numberBadgeItem: TextBadgeItem
    private lateinit var shapeBadgeItem: ShapeBadgeItem

    constructor(parcel: Parcel) : this() {
        lastSelectedPosition = parcel.readInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // All late init's
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar)
        fabHome = findViewById(R.id.fab_home)

        modeSpinner = findViewById(R.id.mode_spinner)
        bgSpinner = findViewById(R.id.bg_spinner)
        shapeSpinner = findViewById(R.id.shape_spinner)
        itemSpinner = findViewById(R.id.item_spinner)
        autoHide = findViewById(R.id.auto_hide)

        toggleHide = findViewById(R.id.toggle_hide)
        toggleBadge = findViewById(R.id.toggle_badge)

        message = findViewById(R.id.message)

        fragment1 = newTextFragmentInstance(getString(R.string.para1))
        fragment2 = newTextFragmentInstance(getString(R.string.para2))
        fragment3 = newTextFragmentInstance(getString(R.string.para3))
        fragment4 = newTextFragmentInstance(getString(R.string.para4))
        fragment5 = newTextFragmentInstance(getString(R.string.para5))
        fragment6 = newTextFragmentInstance(getString(R.string.para6))

        numberBadgeItem = TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("0")
                .setHideOnSelect(autoHide.isChecked)

        shapeBadgeItem = ShapeBadgeItem()
                .setShapeColorResource(R.color.teal)
                .setGravity(Gravity.TOP or Gravity.END)
                .setHideOnSelect(autoHide.isChecked)

        // adapters
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Arrays.asList("MODE_DEFAULT", "MODE_FIXED", "MODE_SHIFTING", "MODE_FIXED_NO_TITLE", "MODE_SHIFTING_NO_TITLE"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modeSpinner.adapter = adapter
        modeSpinner.setSelection(2)

        val itemAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Arrays.asList("3 items", "4 items", "5 items"))
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        itemSpinner.adapter = itemAdapter
        itemSpinner.setSelection(2)

        val shapeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Arrays.asList("SHAPE_OVAL", "SHAPE_RECTANGLE", "SHAPE_HEART", "SHAPE_STAR_3_VERTICES", "SHAPE_STAR_4_VERTICES", "SHAPE_STAR_5_VERTICES", "SHAPE_STAR_6_VERTICES"))
        shapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shapeSpinner.adapter = shapeAdapter
        shapeSpinner.setSelection(5)

        val bgAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Arrays.asList("BACKGROUND_STYLE_DEFAULT", "BACKGROUND_STYLE_STATIC", "BACKGROUND_STYLE_RIPPLE"))
        bgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bgSpinner.adapter = bgAdapter
        bgSpinner.setSelection(1)

        // listeners
        modeSpinner.onItemSelectedListener = this
        bgSpinner.onItemSelectedListener = this
        shapeSpinner.onItemSelectedListener = this
        itemSpinner.onItemSelectedListener = this
        autoHide.setOnCheckedChangeListener(this)

        toggleHide.setOnClickListener(this)
        toggleBadge.setOnClickListener(this)
        fabHome.setOnClickListener(this)

        bottomNavigationBar.setTabSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_github -> {
                val url = "https://github.com/Ashok-Varma/BottomNavigation"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.toggle_hide -> bottomNavigationBar.toggle()
            R.id.toggle_badge -> {
                numberBadgeItem.toggle()
                shapeBadgeItem.toggle()
            }
            R.id.fab_home -> {
                val snackbar = Snackbar.make(message, "Fab Clicked", Snackbar.LENGTH_LONG)
                snackbar.setAction("dismiss") { snackbar.dismiss() }
                snackbar.show()
            }
        }
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        refresh()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {

    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        refresh()
    }

    private fun refresh() {
        numberBadgeItem
                .show()
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(autoHide.isChecked)

        shapeBadgeItem
                .show()
                .setShape(shapeSpinner.selectedItemPosition)
                .setHideOnSelect(autoHide.isChecked)

        bottomNavigationBar.clearAll()
        //        bottomNavigationBar.setFab(fabHome, BottomNavigationBar.FAB_BEHAVIOUR_TRANSLATE_AND_STICK);
        bottomNavigationBar.setFab(fabHome)

        bottomNavigationBar.setMode(modeSpinner.selectedItemPosition)
        bottomNavigationBar.setBackgroundStyle(bgSpinner.selectedItemPosition)


        when (itemSpinner.selectedItemPosition) {
            0 -> {
                bottomNavigationBar
                        .addItem(BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "Nearby").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                        .addItem(BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "Find").setActiveColorResource(R.color.teal))
                        .addItem(BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Categories").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                        .initialise()
                bottomNavigationBar.selectTab(if (lastSelectedPosition > 2) 2 else lastSelectedPosition, true)
            }
            1 -> {
                bottomNavigationBar
                        .addItem(BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                        .addItem(BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                        .addItem(BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                        .addItem(BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                        .initialise()
                bottomNavigationBar.selectTab(if (lastSelectedPosition > 3) 3 else lastSelectedPosition, true)
            }
            2 -> {
                bottomNavigationBar
                        .addItem(BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                        .addItem(BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                        .addItem(BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                        .addItem(BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                        .addItem(BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey))
                        .initialise()
                bottomNavigationBar.selectTab(lastSelectedPosition, true)
            }
        }

    }

    override fun onTabSelected(position: Int) {
        lastSelectedPosition = position
        setMessageText("${position + 1} Tab Selected")
        numberBadgeItem.setText((position + 1).toString())
        replaceFragments(position)
    }

    override fun onTabUnselected(position: Int) {}

    override fun onTabReselected(position: Int) {
        setMessageText("${position + 1} Tab Reselected")
    }

    private fun setMessageText(messageText: String) {
        message.text = messageText
    }

    private fun replaceFragments(position: Int) {
        supportFragmentManager.beginTransaction().apply {
            when (position) {
                0 -> replace(R.id.home_activity_frag_container, fragment1)
                1 -> replace(R.id.home_activity_frag_container, fragment2)
                2 -> replace(R.id.home_activity_frag_container, fragment3)
                3 -> replace(R.id.home_activity_frag_container, fragment4)
                4 -> replace(R.id.home_activity_frag_container, fragment5)
                else -> replace(R.id.home_activity_frag_container, fragment6)
            }
        }.commitAllowingStateLoss()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(lastSelectedPosition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeActivity> {
        override fun createFromParcel(parcel: Parcel): HomeActivity {
            return HomeActivity(parcel)
        }

        override fun newArray(size: Int): Array<HomeActivity?> {
            return arrayOfNulls(size)
        }
    }
}
