package com.etatech.downloaderapp

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.security.AccessControlContext
import kotlin.properties.Delegates

/////////////////////////////////////////////////////
//Created by Fady Fouad on 2019-04-19 at 09:26 PM.//
///////////////////////////////////////////////////

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate: Called")

        val downloadData= DownloadData(this,recycler_view)
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
        Log.d(TAG,"onCreate: Done")

    }

    companion object {
        private class DownloadData(context: Context,recyclerView :ListView) : AsyncTask<String, Void, String>(){
            private val TAG = "DownloadData"

            var proContext:Context by Delegates.notNull()
            var propRV:ListView by Delegates.notNull()

            init {
                proContext = context
                propRV = recyclerView
            }


            override fun doInBackground(vararg params: String?): String {
                Log.d(TAG,"doInBackground: Started With ${params[0]}")
                val rssFeed = downloadXML(params[0])
                if ( rssFeed.isEmpty()){
                    Log.e(TAG,"doInBackground : Download error")
                }
                return rssFeed
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                Log.d(TAG,"onPostExecute: parameter Is $result")
                val parseApplication = ParseApplication()
                parseApplication.parse(result)

                var items:ArrayList<FeedEntery> = ArrayList()
                items.add(FeedEntery())
                val arrayAdapter = ArrayAdapter<FeedEntery>(proContext,R.layout.item_layout,parseApplication.applications)
//                val arrayAdapter = CustomAdapter(items,proContext)
                propRV.adapter=arrayAdapter
            }
            private fun downloadXML(s: String?): String {
                val xmlResult = StringBuilder()
                try {
                    val url = URL(s)
                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    val response = connection.responseCode
                    Log.d(TAG, "download xml the code response $response")

                    val inputStream = connection.inputStream
                    val inputStreamReader = InputStreamReader(inputStream)
//                    val reader = BufferedReader(inputStreamReader)

//                    var inputBuffer = CharArray(500)
//                    var charRead = 0
//                    while (charRead>=0){
//                        charRead = reader.read(inputBuffer)
//                        if (charRead>0){
//                            xmlResult.append(String(inputBuffer,0,charRead))
//                        }
//                    }
//                    reader.close()

                    connection.inputStream.buffered().reader().use { reader ->
                        xmlResult.append(reader.readText())
                    }
                    Log.d(TAG,"Recived ${xmlResult.length} bytes")
                    return xmlResult.toString()
                }catch (e: MalformedURLException){
                    Log.d(TAG,(e.message))
                }catch (e:IOException){
                    Log.d(TAG,(e.message))
                }
            return "" //TODO: return empty String (ERROR)
            }
        }
    }

}



/**

addAnimals()

// Creates a vertical Layout Manager
recycler_view.layoutManager = LinearLayoutManager(this)

// You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

// Access the RecyclerView Adapter and load the data into it
recycler_view.adapter = CustomAdapter(animals, this)

}

// Adds animals to the empty animals ArrayList
fun addAnimals() {
animals.add(FeedEntery("dog"))
animals.add(FeedEntery("cat"))
animals.add(FeedEntery("owl"))
animals.add(FeedEntery("cheetah"))
animals.add(FeedEntery("raccoon"))
animals.add(FeedEntery("bird"))
animals.add(FeedEntery("snake"))
animals.add(FeedEntery("lizard"))
animals.add(FeedEntery("hamster"))
animals.add(FeedEntery("bear"))
animals.add(FeedEntery("lion"))
animals.add(FeedEntery("tiger"))
animals.add(FeedEntery("horse"))
animals.add(FeedEntery("frog"))
animals.add(FeedEntery("fish"))
animals.add(FeedEntery("shark"))
animals.add(FeedEntery("turtle"))
animals.add(FeedEntery("elephant"))
animals.add(FeedEntery("cow"))
animals.add(FeedEntery("beaver"))
animals.add(FeedEntery("bison"))
animals.add(FeedEntery("porcupine"))
animals.add(FeedEntery("rat"))
animals.add(FeedEntery("mouse"))
animals.add(FeedEntery("goose"))
animals.add(FeedEntery("deer"))
animals.add(FeedEntery("fox"))
animals.add(FeedEntery("moose"))
animals.add(FeedEntery("buffalo"))
animals.add(FeedEntery("monkey"))
animals.add(FeedEntery("penguin"))
animals.add(FeedEntery("parrot"))
}


 **/