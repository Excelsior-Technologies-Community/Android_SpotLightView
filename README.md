# **🌟 SpotlightView**

---
A lightweight, customizable Android Spotlight / Coach‑Mark / Tutorial Overlay library built using a Custom View. It helps you highlight important UI elements and guide users through your app with smooth animations and clean visuals.

---

## ✨ **Features**

- 🔦 Highlight any View (TextView, Button, ImageView, etc.)

- 🎨 Fully customizable via XML and Kotlin

- 🔵 Multiple spotlight shapes:

   - Circle

   - Rectangle

   - Rounded Rectangle

- 🌓 Dimmed overlay background

- ✍️ Title + Description text support (multi‑line)

- 🎞️ Smooth reveal and hide animations

- 🧩 Simple API – easy to integrate



  ---

# **Preview**
---
<p align="center">
  <img src="https://github.com/user-attachments/assets/33ac7cd3-2951-4c3b-b40b-84f087b013f2"
       alt="Demo GIF"
       width="200">



</p>


## ⚡ **Installation**

**Step 1:** Add JitPack repository to your root build.gradle:

```gradle
maven { url = uri("https://jitpack.io") }
```

**Step 2:** Add the dependency in your app `build.gradle` (example if hosted on JitPack):  

```gradle
dependencies {
	        implementation 'com.github.Excelsior-Technologies-Community:Android_ChatStyles:1.0.0'

}
```
## ⚡ **attrs file**

```

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="SpotlightView">
        <!-- Overlay color for the dimmed background -->
        <attr name="overlayColor" format="color" />

        <!-- Shape of the spotlight: circle, rectangle, or rounded rectangle -->
        <attr name="spotlightShape" format="enum">
            <enum name="circle" value="0" />
            <enum name="rectangle" value="1" />
            <enum name="roundedRectangle" value="2" />
        </attr>

        <!-- Padding around the target view -->
        <attr name="spotlightPadding" format="dimension" />

        <!-- Animation duration in milliseconds -->
        <attr name="animationDuration" format="integer" />

        <!-- Title text displayed above/below the spotlight -->
        <attr name="titleText" format="string" />

        <!-- Description text displayed below title -->
        <attr name="descriptionText" format="string" />

        <!-- Title text color -->
        <attr name="titleTextColor" format="color" />

        <!-- Description text color -->
        <attr name="descriptionTextColor" format="color" />

        <!-- Title text size -->
        <attr name="titleTextSize" format="dimension" />

        <!-- Description text size -->
        <attr name="descriptionTextSize" format="dimension" />

        <!-- Margin between spotlight and text -->
        <attr name="textMargin" format="dimension" />

        <!-- Enable/disable animation -->
        <attr name="showAnimation" format="boolean" />
    </declare-styleable>
</resources>


```

## ⚡ **Usage**

1. Add in XML

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5"
    tools:context=".MainActivity">

    <!-- Sample UI elements to highlight -->

    <TextView
        android:id="@+id/tvTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Spotlight Demo"
        android:textSize="24sp"
        android:textStyle="bold"
        android:textColor="#333333"
        android:layout_marginTop="32dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <TextView
        android:id="@+id/tvClickHere"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Click to Spotlight"
        android:textSize="18sp"
        android:textColor="#2196F3"
        android:textStyle="bold"
        android:padding="16dp"
        android:background="?attr/selectableItemBackground"
        android:layout_marginTop="100dp"
        app:layout_constraintTop_toBottomOf="@id/tvTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <TextView
        android:id="@+id/tvDescription"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Welcome to SpotlightView Library Demo.\n\nThis library helps you create beautiful tutorial overlays and highlight important UI elements.\n\nTap the text above to start the demo."
        android:textSize="16sp"
        android:textColor="#666666"
        android:gravity="center"
        android:lineSpacingExtra="4dp"
        android:layout_marginTop="48dp"
        android:paddingHorizontal="32dp"
        app:layout_constraintTop_toBottomOf="@id/tvClickHere"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintWidth_percent="0.9" />


    <com.ext.android_spot_light_view.SpotlightView
        android:id="@+id/spotlightView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:overlayColor="#DD000000"
        app:spotlightShape="circle"
        app:spotlightPadding="24dp"
        app:animationDuration="500"
        app:titleText="XML Configured Title"
        app:descriptionText="This spotlight style is set via XML\nNo Kotlin code needed for styling!"
        app:titleTextColor="#FFFFFF"
        app:descriptionTextColor="#E0E0E0"
        app:titleTextSize="22sp"
        app:descriptionTextSize="16sp"
        app:textMargin="80dp"
        app:showAnimation="true"
        android:clickable="true"
        android:focusable="true" />

</androidx.constraintlayout.widget.ConstraintLayout>



```

## ⚡ **Main Activity**

**Show Spotlight on a View**

```
spotlightView = findViewById(R.id.spotlightView)
tvTitle = findViewById(R.id.tvTitle)

spotlightView.showSpotlight(targetView)

```
**Hide Spotlight**
```
spotlightView.hideSpotlight()

```

**⚙️ Programmatic Customization**

```
spotlightView.apply {
setTitleText("App Title")
setDescriptionText("This is the main feature")
setSpotlightShape(SpotlightView.SHAPE_ROUNDED_RECTANGLE)
setOverlayColor(Color.parseColor("#CC1A237E"))
setTitleTextColor(Color.YELLOW)
setDescriptionTextColor(Color.WHITE)
setSpotlightPadding(30f)
}

```

**🔘 Available Spotlight Shapes**

- SpotlightView.SHAPE_CIRCLE
- SpotlightView.SHAPE_RECTANGLE
- SpotlightView.SHAPE_ROUNDED_RECTANGLE


## **📄 License**

**MIT License**  
```
Copyright (c) 2025 Excelsior Technologies

Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the "Software"), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED **"AS IS"**, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
```



  
