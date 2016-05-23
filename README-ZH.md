# BlurLockView 
[![WoWoViewPager](https://github.com/Nightonke/WoWoViewPager/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)](https://github.com/Nightonke/WoWoViewPager)
[![BoomMenu](https://github.com/Nightonke/BoomMenu/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)](https://github.com/Nightonke/BoomMenu)
[![CoCoin](https://github.com/Nightonke/CoCoin/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)](https://github.com/Nightonke/CoCoin)
[![BlurLockView](https://github.com/Nightonke/BlurLockView/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)](https://github.com/Nightonke/BlurLockView)
[![LeeCo](https://github.com/Nightonke/LeeCo/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)](https://github.com/Nightonke/LeeCo)
[![GithubWidget](https://github.com/Nightonke/GithubWidget/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)](https://github.com/Nightonke/GithubWidget)
[![JellyToggleButton](https://github.com/Nightonke/JellyToggleButton/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)](https://github.com/Nightonke/JellyToggleButton)
[![FaceOffToggleButton](https://github.com/Nightonke/FaceOffToggleButton/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)](https://github.com/Nightonke/FaceOffToggleButton)

![BlurLockView](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/in_out.gif)  

毛玻璃效果的解锁界面。

# [English Version](https://github.com/Nightonke/BlurLockView)

# 备注

1. 使用了[500px-android-blur](https://github.com/500px/500px-android-blur)的毛玻璃效果。
2. 会添加更多显示、隐藏BlurLockView的动画。
3. 在demo里，使用了[Material-Dialogs](https://github.com/afollestad/material-dialogs)以便调整颜色。

# 使用

[Demo](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#demo)  
[Gradle](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#gradle)  
[快速使用](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#快速使用)  
[显示和隐藏](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#显示和隐藏)  
[Listeners](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#listeners)  
[毛玻璃效果](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#毛玻璃效果)  
[键盘](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#键盘)  
[文本](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#文本)  
[字体](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#字体)  
[风格](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#风格)  
[错误密码](https://github.com/Nightonke/BlurLockView/blob/master/README-ZH.md#错误密码)  

### Demo

下载demo：  
[Download from Fir](http://fir.im/yakc)  
[Download from Github](https://github.com/Nightonke/BlurLockView/blob/master/Apk/BlurLock-V1.0.0.apk?raw=true)  
![Fir](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/fir.png)  
Demo中含有目前BlurLockView的所有功能  
![Settings](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/test.png)
![Operations](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/operations.png)

### Gradle

添加到module的build.gradle中  
```java
dependencies {
    ...
    compile 'com.nightonke:blurlockview:1.0.0'
    ...
}
```

### 快速使用

加入到xml中：  
```xml
<com.nightonke.blurlockview.BlurLockView
    android:id="@+id/blurlockview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
```
注意BlurLockView应该放置在需要被覆盖的控件的正上方，也就是覆盖其他控件。

加入以下代码即可使用：  
```java
// Set the view that need to be blurred
blurLockView.setBlurredView(imageView1);

// Set the password
blurLockView.setCorrectPassword(getIntent().getStringExtra("PASSWORD"));
```

### 显示和隐藏

你可以选择显示或隐藏的时延、方式、缓动函数。
比如，本文开头的gif以```ShowType.FADE_IN```为方式，以1000ms为时延来显示，以```HideType.FADE_OUT```为方式，以1000ms为时延来隐藏。 
你可以在demo中尝试各种效果。  

![Ease](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/ease.gif)  

### Listeners

BlurLockView.OnPasswordInputListener  
```java
@Override
public void correct(String inputPassword) {
    // the input password is correct
    // you can hide the BlurLockView, for example
}

@Override
public void incorrect(String inputPassword) {
    // the input password is incorrect
}

@Override
public void input(String inputPassword) {
    // the password is being input
}
```

BlurLockView.OnLeftButtonClickListener  
```java
@Override
public void onClick() {
    // The left button is being clicked
}
```
实现以上Listeners然后加入以下代码：    
```java
blurLockView.setOnLeftButtonClickListener(this);
blurLockView.setOnPasswordInputListener(this);
```
注意右下角的按钮是用于退格的，所以没有右下角按钮的监听器。    

### 毛玻璃效果

你可以使用三种不同的参数来调整毛玻璃效果：    
1. 用```setDownsampleFactor(int downsampleFactor)```来调整**DownsampleFactor**，其值越小，毛玻璃效果越淡。  
2. 用```setBlurRadius(int blurRadius)```来调整**BlurRadius**，其值越小，毛玻璃效果越淡。  
3. 用```setOverlayColor(int color)```来调整**OverlayColor**，可以改变毛玻璃效果颜色。

例子：    
![clear](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/clear.png)
![unclear](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/unclear.png)

![red](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/red.png)
![blue](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/blue.png)

### 键盘

你可以用不同的键盘来输入不同类型的密码。    
```java
setType(Password type, boolean smoothly);
```
密码类型有```Password.NUMBER```（默认）和```Password.TEXT```，还可以选择是否顺畅切换键盘。
注意字母类型密码是不区分大小写的，我会改进这个不足。  

![Password Type](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/keyboard.gif)

### 文本

1. 用```setTitle(String string)```来改变标题文本。
2. 用```setLeftButton(String string)```来改变左下角按钮文本。
3. 用```setRightButton(String string)```来改变右下角按钮文本。

### 字体

用```setTypeface(Typeface typeface)```来改变BlurLockView的字体。

### 风格

**1.** 改变字母类型密码的按钮背景，可以用```setSmallButtonViewsBackground(int id)```，默认的背景是：    
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android" >
    <item android:state_pressed="true" >
        <shape android:shape="oval"  >

        </shape>
    </item>
    <item android:state_focused="true">
        <shape android:shape="oval"  >
            <stroke android:width="1dip" android:color="@color/default_button_press" />
            <solid android:color="@android:color/transparent"/>
        </shape>
    </item>
    <item >
        <shape android:shape="oval"  >
            <stroke android:width="1dip" android:color="@color/default_button_press" />
            <solid android:color="@android:color/transparent"/>
        </shape>
    </item>
</selector>
```

**2.** 改变字母类型密码的按钮效果，可以用```setBigButtonViewsClickEffect(int id)```，默认的效果是：    
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android" >
    <item >
        <shape android:shape="oval"  >
            <solid android:color="@color/default_button_press"/>
        </shape>
    </item>
</selector>
```
**3.** 当点击按钮时，上述提到的按钮效果会渐逝，可以用```setSmallButtonViewsClickEffectDuration(int duration)```来调整时延。

**4.** 类似的，可以用这三个函数来改变数字类型密码的风格```setBigButtonViewsBackground(int id)```，```setBigButtonViewsClickEffect(int id)```和```setBigButtonViewsClickEffectDuration(int duration)```。

**5.** 可以用```setTextColor(int color)```来改变所有文字颜色。

**6.** 下面的函数可以获得BlurLockView的各个控件：    
1. ```public TextView getTitle() {return title;}```获取标题控件。   
2. ```public TextView getLeftButton() {return leftButton;}```获取左下角按钮控件。    
3. ```public TextView getRightButton() {return rightButton;}```获取右下角按钮控件。    
4. ```public BigButtonView[] getBigButtonViews() {return bigButtonViews;}```获取10个数字按钮控件。    
5. ```public SmallButtonView[][] getSmallButtonViews() {return smallButtonViews;}``` 获取所有的字母按钮控件，注意有些数组元素为null，具体的控件下标如下表：    
    
```java
private final char CHARS[][] = {
        {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'},
        {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'},
        {   'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'  },
        {        'Z', 'X', 'C', 'V', 'B', 'N', 'M'       }
};
```
    
### 错误密码

BlurLockView会记录输入错误密码次数，可以用```getIncorrectInputTimes()```来获取当前错误密码次数，或者用```setIncorrectInputTimes(int incorrectInputTimes)```来重置次数。  

# 版本
### 1.0.0  

# Todo
1. 更多的动画效果。    
2. 让字母密码输入类型可以输入大小写字母和其他特殊符号。

# License

    Copyright 2016 Nightonke

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
