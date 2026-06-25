package com.course.app.navigation

object Routes {
    const val LIST   = "list"
    const val DETAIL = "detail/{itemId}"
    const val EDIT   = "edit/{itemId}"

    fun detail(itemId: Int) = "detail/$itemId"
    fun edit(itemId: Int)   = "edit/$itemId"
    fun addNew()            = "edit/0"
}
