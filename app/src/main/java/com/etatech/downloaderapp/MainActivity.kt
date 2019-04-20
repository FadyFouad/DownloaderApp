package com.etatech.downloaderapp

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

/////////////////////////////////////////////////////
//Created by Fady Fouad on 2019-04-19 at 09:26 PM.//
///////////////////////////////////////////////////

class MainActivity : AppCompatActivity() {

    private var dataState = "DATA"
    private var rssURL:String = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=100/xml"

    private val downloadData by lazy { DownloadData(this, recycler_view) }
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: Called")

        downloadData.execute(rssURL)
        Log.d(TAG, "onCreate: Done")

    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData.cancel(true)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(dataState,rssURL)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.getString(rssURL)
    }

    companion object {
        private class DownloadData(context: Context, recyclerView: RecyclerView) : AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            var proContext: Context by Delegates.notNull()
            var propRV: RecyclerView by Delegates.notNull()

            init {
                proContext = context
                propRV = recyclerView
            }


            override fun doInBackground(vararg params: String?): String {
                Log.d(TAG, "doInBackground: Started With ${params[0]}")
                val rssFeed = downloadXML(params[0])
                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground : Download error")
                }
                return rssFeed
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute: parameter Is $result")
                val parseApplication = ParseApplication()
                parseApplication.parse(result)

                var items: ArrayList<FeedEntery> = ArrayList()
                items.add(FeedEntery())
//                val arrayAdapter = ArrayAdapter<FeedEntery>(proContext,R.layout.item_layout,parseApplication.applications)
                val arrayAdapter = CustomAdapter(parseApplication.applications, proContext)
                propRV.adapter = arrayAdapter
                propRV.layoutManager = LinearLayoutManager(proContext)
                propRV.setHasFixedSize(true)
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
                    Log.d(TAG, "Recived ${xmlResult.length} bytes")
                    return xmlResult.toString()
                } catch (e: MalformedURLException) {
                    Log.d(TAG, (e.message))
                } catch (e: IOException) {
                    Log.d(TAG, (e.message))
                }
                return "" //TODO: return empty String (ERROR)
            }
        }
    }

}