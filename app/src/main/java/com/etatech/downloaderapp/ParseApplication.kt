package com.etatech.downloaderapp

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.util.*

/****************************************************
 **Created by Fady Fouad on 2019-04-19 at 10:40 PM.**
 ***************************************************/
class ParseApplication {
    private val TAG ="ParseApplication"
    val applications = ArrayList<FeedEntery>()

    fun parse(xmlDate: String):Boolean{
        Log.d(TAG ,"parse Called -> $xmlDate")
        var status = true
        var inEntry = false
        var textValue = ""

        try {

            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlDate.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntery()
            while (eventType != XmlPullParser.END_DOCUMENT ){

                val tagName = xpp.name?.toLowerCase()
                when (eventType){
                    XmlPullParser.START_TAG->{
                        Log.d(TAG,"Parse Starting -> $tagName")

                        if (tagName=="entry"){
                            inEntry = true
                        }
                    }
                    XmlPullParser.TEXT->textValue = xpp.text
                    XmlPullParser.END_TAG->{
                        Log.d(TAG,"parse Ended -> $tagName")
                        if (inEntry){
                            when (tagName){
                                "entry"->{
                                    applications.add(currentRecord)
                                    inEntry = false
                                    currentRecord = FeedEntery()
                                }
                                "name"->{
                                    currentRecord.name = textValue
                                }
                                "artist"->{
                                    currentRecord.artist = textValue
                                }
                                "releaseDate"->{
                                    currentRecord.releaseDate = textValue
                                }
                                "summary"->{
                                    currentRecord.summary = textValue
                                }
                                "image"->{
                                    currentRecord.image = textValue
                                }
                                "id"->{
                                    currentRecord.id = textValue
                                }

                            }
                        }
                    }
                }
                eventType = xpp.next()
            }

            for (app in applications){
                Log.d(TAG,"=========")
                Log.d(TAG,app.toString())
            }

        }catch (e : Exception){
            e.printStackTrace()
            status = false
        }

        return status
    }
}