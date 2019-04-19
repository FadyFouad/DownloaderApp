package com.etatech.downloaderapp

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/////////////////////////////////////////////////////
//Created by Fady Fouad on 2019-04-19 at 09:26 PM.//
///////////////////////////////////////////////////

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate: Called")

        val downloadData= DownloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
        Log.d(TAG,"onCreate: Done")

    }

    companion object {
        private class DownloadData : AsyncTask<String, Void, String>(){
            private val TAG = "DownloadData"
            override fun doInBackground(vararg params: String?): String {
                Log.d(TAG,"doInBackground: Started With ${params[0]}")
                val rssFeed = downloadXML(params[0])
                if ( rssFeed.isEmpty()){
                    Log.e(TAG,"doInBackground : DOwnload error")
                }
                return rssFeed
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG,"onPostExecute: parameter Is $result")

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
                    val reader = BufferedReader(inputStreamReader)

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
            return "" //TODO return empty String (ERROR)
            }
        }
    }

}
