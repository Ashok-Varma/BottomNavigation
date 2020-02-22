[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BottomNavigation-green.svg?style=true)](https://android-arsenal.com/details/1/3612)
[![Build Status](https://travis-ci.org/Ashok-Varma/BottomNavigation.svg?branch=master)](https://travis-ci.org/Ashok-Varma/BottomNavigation)

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
  <version>2.2.0</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy
implementation 'com.ashokvarma.android:bottom-navigation-bar:2.2.0'
```
or Ivy:
```xml
<dependency org='com.ashokvarma.android' name='bottom-navigation-bar' rev='2.2.0'>
  <artifact name='$AID' ext='pom'/>
</dependency>
```

## For Usage Docs [Visit Wiki][wikiLink]

## Migration from V1 to V2
1. BadgeItem has been changed to TextBadgeItem
2. New ShapeBadgeItem implementation changed. [check this page for new api usage](https://github.com/Ashok-Varma/BottomNavigation/wiki/Badges)
3. hideText replaced with new modes. To use those mode should be set to MODE_FIXED_NO_TITLE / MODE_SHIFTING_NO_TITLE 

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

## Other Open Source Libraries
1. [Gander](https://github.com/Ashok-Varma/Gander) : Gander is a simple in-app HTTP inspector for Android OkHttp clients. Gander intercepts and persists all HTTP requests and responses inside your application, and provides a UI for inspecting their content.
2. [SqliteManager](https://github.com/Ashok-Varma/SqliteManager) : Sqlite Manager is a Dev Debug tool that helps to manage(Edit, Add, Clear) your android Sqlite Databases.
3. [SharedPrefManager](https://github.com/Ashok-Varma/SharedPrefManager) : SharedPref Manager is a Dev Debug tool that helps to manage(Edit, Add, Clear) your android Shared Preferences.


 [wikiLink]: https://github.com/Ashok-Varma/BottomNavigation/wiki
 [googlePlayStoreLink]: https://play.google.com/store/apps/details?id=com.ashokvarma.bottomnavigation.sample
 [googlePage]: https://www.google.com/design/spec/components/bottom-navigation.html
 [mavenAarDownload]:  https://repo1.maven.org/maven2/com/ashokvarma/android/bottom-navigation-bar/2.2.0/bottom-navigation-bar-2.2.0.aar
 [mavenLatestJarDownload]: https://search.maven.org/remote_content?g=com.ashokvarma.android&a=bottom-navigation-bar&v=LATEST
