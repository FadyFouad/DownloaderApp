package com.etatech.downloaderapp

/****************************************************
 **Created by Fady Fouad on 2019-04-19 at 10:33 PM.**
 ***************************************************/
class FeedEntery {
    var name: String = ""
    var id: String = ""
    var artist:String = ""
    var releaseDate :String = ""
    var summary : String = ""
    var image : String = ""

    override fun toString(): String {
        return "FeedEntery(name='$name',id='$id', artist='$artist', releaseDate='$releaseDate', summary='$summary', image='$image')"
    }


}