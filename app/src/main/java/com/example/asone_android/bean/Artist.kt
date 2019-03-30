package com.example.asone_android.bean

class Artist {
    var name = ""
    var age = ""
    var six = ""
    var brief = ""
    var head = ""
    var country = ""
    var recommend = ""
    var upId=""
    var hotNum=""
    var collect = false

    /**
     * 约定type
     * name = 1
     *  age = 2
     *  six = 3
     * country = 4
     * recommend = 5
     *
     *
     * */

    override fun toString(): String {
        return "Artist(name='$name', age='$age', six='$six', brief='$brief', head='$head', country='$country', recommend='$recommend')"
    }


}