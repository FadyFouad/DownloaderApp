package com.etatech.downloaderapp

/****************************************************
 **Created by Fady Fouad on 2019-04-19 at 10:33 PM.**
 ***************************************************/
class FeedEntery {
    var name: String = ""
    var author:String = ""
    var date :String = ""
    var summary : String = ""
    var imgUrl : String = ""

    override fun toString(): String {
        return "FeedEntery(name='$name', author='$author', date='$date', summary='$summary', imgUrl='$imgUrl')"
    }


}