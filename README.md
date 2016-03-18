# BlurLockView

![BlurLockView](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/in_out.gif)  

Lock view with blur effect. Easy to customise.

# Note

1. The blur effect comes from [500px-android-blur](https://github.com/500px/500px-android-blur).
2. More animations for showing or hiding the BlurLockView will be added.

# Usage

### Demo

Try demo here:  
[Download from Fir](http://fir.im/yakc)  
[Download from Github](https://github.com/Nightonke/BlurLockView/blob/master/Apk/BlurLock-V1.0.0.apk?raw=true)  
![Fir](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/fir.png)  
You can get all about BlurLockView from the demo.  
![Settings](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/test.png)
![Operations](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/operations.png)

### Gradle

Add this to build.gradle:  
```java
dependencies {
    ...
    compile 'com.nightonke:blurlockview:1.0.0'
    ...
}
```

### Easy to Use

Add the xml code:  
```xml
<com.nightonke.blurlockview.BlurLockView
    android:id="@+id/blurlockview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
```
Notice that you should set the BlurLockView to cover the view than need to be blured.

Add this to initialize the BlurLockView:  
```java
// Set the view that need to be blurred
blurLockView.setBlurredView(imageView1);

// Set the password
blurLockView.setCorrectPassword(getIntent().getStringExtra("PASSWORD"));
```

### Show and Hide

You can choose duration, direction and ease type to show or hide the BlurLockView. 
For instance, the gif at the start of readme shows as ```ShowType.FADE_IN``` with 1000ms and ```HideType.FADE_OUT``` with 1000ms. 
You can check all the directions and ease types in the demo above.  

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
Notice that the right button is set as "Backspace" usually, so there is not OnRightButtonClickListener.  

### Blur Effect

You can set the effect of blur with 3 parameters.  
1. **DownsampleFactor**, with ```setDownsampleFactor(int downsampleFactor)```, the smaller, the clearer.
2. **BlurRadius**, with ```setBlurRadius(int blurRadius)```, the smaller, the clearer.
3. **OverlayColor**, with ```setOverlayColor(int color)```, to change the overlay color of BlurLockView.

Examples:  
![clear](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/clear.png)
![unclear](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/unclear.png)

![red](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/red.png)
![blue](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/blue.png)

### Keyboard

You can use different keyboard to get different password.  
```java
setType(Password type, boolean smoothly);
```
Choose ```Password.NUMBER```(default) or ```Password.TEXT``` and whether change password type smoothly. 
Notice that the password with text is case-insensitive(I will improve this).  

![Password Type](https://github.com/Nightonke/BlurLockView/blob/master/Pictures/keyboard.gif)

### Text

1. Set the text of title with ```setTitle(String string)```.
2. Set the text of left button with ```setLeftButton(String string)```.
3. Set the text of right button with ```setRightButton(String string)```.

### Font

You can set all the font of text with ```setTypeface(Typeface typeface)```.

### Style

1. Set the background of buttons in Password.TEXT with ```setSmallButtonViewsBackground(int id)```. The default resource drawable is:  
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
