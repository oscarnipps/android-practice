package com.android.android_practice.recyclerview

sealed class SectionedItem{
    data class HeaderItems (val header : Char) : SectionedItem ()

    data class SectionItems (val student : Student) : SectionedItem ()
}
