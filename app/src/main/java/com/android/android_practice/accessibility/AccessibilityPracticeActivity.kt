package com.android.android_practice.accessibility

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.databinding.DataBindingUtil
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivityAccessibilityPracticeBinding

/*
* - Accessibility services are more like plugins used to be able to cater to users with varying disabilities that use the android device
* - Examples of services :
*   -> Talkback (screen reader that gives you spoken feedback  )
*   -> Switch Access (users with limited mobility would be able to interact with the device via switches i.e external switches , buttons , device buttons e.t.c)
*
* - Some common issues for a11y :
*   -> missing labels which makes it hard for the scanner to know the purpose of the ui controls (images,buttons,text view with links e.t.c)
*   -> touch targets that are too small
*
* - Use a11y api for when :
*   -> adding labels to images
*   -> custom view types
*   -> describing different states for both custom ui or overriding the default states of the box android widgets
*   -> overriding the default action of the out of the box android widgets or custom ones
*
* - Rule of thumb when using custom views is to try and leverage the already existing widgets i.e extending from a Button class rather than extending from a View class
*   because the Button class already has some a11y implementations out of the box
*
* - some helpful tips for labeling :
*   -> set the content description
*   -> if app only supports devices from android 4.1 (API 16) and above then set the important for accessibility attribute
*   -> for editable views i.e edit text , set the android hint attribute
*   -> if an element in the UI exists only for visual purposes then set the content description to null (@null)
*
* - Always provide content label for :
*   -> ImageViews
*   -> ImageButtons
*   -> Views that convey meaningful or actionable information
*
* - For small touch targets you can either make them bigger or register a touch delegate ( enables you to expand the touch target of the ui widget). Always
*   use sparingly (touch size should be at least 48x48 dps)
*
* - use 'labelFor' attribute to denote when an element is describing the intent of another view (text view with label for attribute for an edit text)
*
* - Use WebAIM to check for color contrast in views
*
* - State description (a string ) describes the state of a view (it's different from contentDescription as it is dynamic but contentDescription is static).
*
* - Using state description API to set / get a view's state description (use ViewCompat.setStateDescription(viewContext, string) )
*
* - To mark view as a accessibility heading use ViewCompat.setAccessibilityHeading(..) which is useful when :
*   -> There are section titles
*   -> There is a category label i.e 'Personal Details' , 'Profile' e.t.c
*   -> There are important UI elements that define a group of related content
*
* - To mark a distinct section for accessibility use ViewCompat.setAccessibilityPaneTitle() useful when :
*   -> Dashboard sections
*   -> Modals ,dialogs or bottom sheets
*   -> Collapsible panels i.e 'filters' , 'advanced settings' e.t.c
*   -> Any section with dynamic content updates as it announces the updates / changes
*
* - AccessibilityDelegate defines how accessibility services interact with UI elements , useful for :
*   -> Adding custom actions (Enables new interactions i.e swipe to delete)
*   -> Changes how elements are announced by TalkBack (modifies announcement for views)
*   -> Make items focusable ( setting 'importantForAccessibility' which can then be triggered when 'announceForAccessibility()'  is called)
*
* - '.announceForAccessibility()' forces talkback to read a view ( make non-focusable view to be announced)
*
* - 'info' which is an instance of 'AccessibilityNodeInfoCompat' has metadata about the view ( how it is described, announced and interacted with i.e actions )
*
* - 'info' has some important properties and their uses :
*   -> info.text : specifies what TalkBack reads
*   -> info.className : tells TalkBack what kind of UI element this is (Button, EditText, Switch, etc.).
*   -> info.isClickable : marks a view as clickable so talkback announces it properly (also for others i.e longClick etc)
*   -> info.heading : marks a view as a section header
*   -> info.addAction() : adds a custom accessibility action
*   -> info.isEnabled : marks a view as disabled so talkback tells the user it's not interactive (i.e submit button on a form disabled till fields are filled)
*
*
* */

private lateinit var binding : ActivityAccessibilityPracticeBinding

class AccessibilityPracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_accessibility_practice)

        binding.back.setOnClickListener {
            finish()
        }

        binding.breakfast.setOnClickListener {
            Toast.makeText(this,"Yam and Egg",Toast.LENGTH_SHORT).show()
        }

        binding.lunch.setOnClickListener {
            Toast.makeText(this,"Amala and Stew",Toast.LENGTH_SHORT).show()
        }

        binding.dinner.setOnClickListener {
            Toast.makeText(this,"Bread and Beans",Toast.LENGTH_SHORT).show()
        }

        //set custom state description on switch widget
        //binding.dietPromoSwitch.setOnCheckedChangeListener { _, isChecked ->
        //    binding.dietPromoSwitch.stateDescription = if ( isChecked) "engaged" else "disengaged"
        //}

        //binding.headerTitle.setAsAccessibilityHeader()

        binding.customHeader.setAsAccessibilityPane()

    }

    private fun View.setAsAccessibilityHeader() {
        ViewCompat.setAccessibilityHeading(this,true )
    }

    private fun View.setAsAccessibilityPane() {
        ViewCompat.setAccessibilityPaneTitle(this,"Dashboard Section")
    }

    private fun View.setCustomStateDescription() {
        ViewCompat.setStateDescription(this,"engaged" )
    }

    //could be on View or ViewGroup
    private fun View.setCustomViewGroupInfoProperties() {
        ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat(){
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)

                //makes talkback recognize this as a heading
                info.isHeading = true

                //marks a view as clickable so talkback announces it properly
                info.isClickable = true

                //tells talkback this is a Button UI component ( android.widget.*)
                info.className = "android.widget.Button"

                //talkback would announce ".... , Custom Role "
                info.roleDescription = "Custom Role"

                info.addAction(
                    AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        AccessibilityNodeInfoCompat.ACTION_CLICK,
                        "Bookmark"     //"Double tap to Bookmark" (custom action added to accessibility menu)
                    )
                )

                //ensure view is focused , so when '.announceForAccessibility()' is called it reads the view
                host.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
            }
        })
    }

}