[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BottomNavigation-green.svg?style=true)](https://android-arsenal.com/details/1/3612)


# BottomNavigation

**get sample apk from [Google Play Store][googlePlayStoreLink]**

<img src="https://raw.githubusercontent.com/Ashok-Varma/BottomNavigation/master/all.gif" width="300" height="550" />

## What is this component about?

This component that mimics the new [Material Design Bottom Navigation pattern][googlePage].

**(currently under active development, expect to see new releases almost daily)**

## Features

* This library offers ton of customisations that you can do to Bottom Navigation Bar.
* Follows google [bottom navigation bar guidelines][googlePage]
* Choose your background style and tab mode.
* each tab has it's own colors
* supports badges with complete customization

## Download

Based on your IDE you can import library in one of the following ways

Download [the latest JAR][mavenAarDownload] or grab via Maven:

```xml
<dependency>
  <groupId>com.ashokvarma.android</groupId>
  <artifactId>bottom-navigation-bar</artifactId>
  <version>1.2.0</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy
compile 'com.ashokvarma.android:bottom-navigation-bar:1.2.0'
```
or Ivy:
```xml
<dependency org='com.ashokvarma.android' name='bottom-navigation-bar' rev='1.2.0'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```
## Usage

### Basic setup

**in your activity xml :**

```xml
    <com.ashokvarma.bottomnavigation.BottomNavigationBar
        android:layout_gravity="bottom"
        android:id="@+id/bottom_navigation_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

**In your class**
```java
BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books"))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music"))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV"))
                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games"))
                .initialise();
```

**Add TabSelectedListener**
```java
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
```
**all methods are self explanatory**

### BottomNavigationBar Customizations

 | MODE_FIXED | MODE_SHIFTING
------------ | ------------ | -------------
BACKGROUND_STYLE_STATIC | <img src="https://raw.githubusercontent.com/Ashok-Varma/BottomNavigation/master/fixed_static.gif" width="320" height="50" /> | <img src="https://raw.githubusercontent.com/Ashok-Varma/BottomNavigation/master/shift_static.gif" width="320" height="50" />
BACKGROUND_STYLE_RIPPLE | <img src="https://raw.githubusercontent.com/Ashok-Varma/BottomNavigation/master/fixed_ripple.gif" width="320" height="50" /> | <img src="https://raw.githubusercontent.com/Ashok-Varma/BottomNavigation/master/shift_ripple.gif" width="320" height="50" />

#### 1. Modes
Attribute: `bnbMode` Values: `mode_default, mode_fixed, mode_shifting`  
Method: `setMode()`  Values:`MODE_DEFAULT, MODE_FIXED, MODE_SHIFTING`  

##### Code Snippet Example
**to set mode :**		
 ```java
 bottomNavigationBar
                 .setMode(BottomNavigationBar.MODE_FIXED)
 ```

**MODE_DEFAULT:** if number of tabs are less than or equal to three then MODE_FIXED will be used other cases MODE_SHIFTING will be used.

#### 2. Background Styles
  Attribute: `bnbBackgroundStyle` Values: `background_style_default, background_style_static, background_style_ripple`  
  Method: `setBackgroundStyle()` Values: `BACKGROUND_STYLE_DEFAULT, BACKGROUND_STYLE_STATIC, BACKGROUND_STYLE_RIPPLE`  

##### Code Snippet Example
**to set background style:**		
 ```java
 bottomNavigationBar
                 .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)		
 ```

**BACKGROUND_STYLE_DEFAULT:** if mode is MODE_FIXED then BACKGROUND_STYLE_STATIC will be used if mode is MODE_SHIFTING then BACKGROUND_STYLE_RIPPLE will be used.

#### 3. Colors
Attributes: `bnbActiveColor, bnbInactiveColor, bnbBackgroundColor` Value: Color value or resource   
Methods: `setActiveColor, setInActiveColor, setBarBackgroundColor` Value: Color value or resource   

##### Code Snippet Example
**to set colors:**	
```java
 bottomNavigationBar
                 .setActiveColor(R.color.primary)
                 .setInActiveColor("#FFFFFF")
                 .setBarBackgroundColor("#ECECEC")
 ```

**in-active color :** is the icon and text color of the in-active/un-selected tab

**active color :** In BACKGROUND_STYLE_STATIC active color is the icon and text color of the active/selected tab. In BACKGROUND_STYLE_RIPPLE active color is the bottom bar background color (which comes with ripple animation)

**background color :** In BACKGROUND_STYLE_STATIC background color is the bottom bar background color. In BACKGROUND_STYLE_RIPPLE background color is the icon and text color of the active/selected tab.

**Default colors:**  
1. Theme's Primary Color will be active color.  
2. Color.LTGRAY will be in-active color.  
3. Color.WHITE will be background color.  

#### 4. Individual BottomNavigationItem Colors

**set colors to BottomNavigationItem :** if you need different active/in-active colors for different tabs. you can also set active and inactive color for the BottomNavigationItem.
##### Code Snippet Example
```java
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColor(R.color.orange).setInActiveColor(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColor("#FFFF00"))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setInActiveColor("#00FFFF"))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV"))
                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColor(R.color.grey))
```
if you didn't set active/in-active colors for the BottomNavigationItem then these will inherited from BottomNavigationBar active/in-active colors respectively

#### 5. Badges 

**add badge to BottomNavigationItem:** you may add a BadgeItem for different tabs.

##### Code Snippet Example
```java
     BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("5")
                .setHideOnSelect(autoHide.isChecked());

     bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
```


#### 5. Elevation
Attribute: `bnbElevation` 

You can set elevation to `0dp` if you don't want a shadow or plan to draw your own.

**Default elevation:** `8dp`  

## License

```
BottomNavigation library for Android
Copyright (c) 2016 Ashok Varma (http://ashokvarma.me/).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

 [googlePlayStoreLink]: https://play.google.com/store/apps/details?id=com.ashokvarma.bottomnavigation.sample
 [googlePage]: https://www.google.com/design/spec/components/bottom-navigation.html
 [mavenAarDownload]:  https://repo1.maven.org/maven2/com/ashokvarma/android/bottom-navigation-bar/1.2.0/bottom-navigation-bar-1.2.0.aar
 [mavenLatestJarDownload]: https://search.maven.org/remote_content?g=com.ashokvarma.android&a=bottom-navigation-bar&v=LATEST
