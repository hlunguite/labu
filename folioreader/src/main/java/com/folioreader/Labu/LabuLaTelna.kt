package com.folioreader.Labu

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder

class LabuLaTelna(val context: Context, var labuid: Int) {

    private var helper: LabuDBHelper? = null
    private var labuId: Int = labuid
    private fun getDatabase(): SQLiteDatabase {

        if (helper == null) {
            helper = LabuDBHelper(context)
        }

        if (helper != null) {
            return helper!!.readableDatabase
        } else {
            throw IllegalArgumentException("Database not readable!")
        }
    }



    /**
     * Get the movies that match a given text.
     */
    fun search(text: String): List<LabuLatel> {
        val latel = mutableListOf<LabuLatel>()
        var query: String
        if (labuId > -1) {
            query = "SELECT title,songid,verse,bookid  FROM LABU WHERE LABU MATCH '$text*' AND bookid = $labuId  ORDER BY songid"

        } else {
            query = "SELECT title,songid,verse,bookid   FROM LABU WHERE LABU MATCH '$text*' ORDER BY songid"


        }
Log.d("SEARCH query ", query)
        val cursor = getDatabase().rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
              //  val score = OkapiBM25.score(matchinfo = cursor.getBlob(4).toIntArray(), column = 0)
                val la = LabuLatel(
                        title = cursor.getString(0),
                        verse = cursor.getString(2),
                        lano = cursor.getInt(1),
                        bookno = cursor.getInt(3),
                        score = 1.0)
              //  Log.d("SEARCH " , cursor.getString(3) + " [" + cursor.getString(2) + "] " + cursor.getString(0) +":" + cursor.getString(1) )
             //   Log.d("SEARCH ", la.title +" bookno "+ la.bookno)
                latel.add(la)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return latel//latel.sortedByDescending { it.score }
    }

    /**
     * Convert byte array to int array (little endian).
     */
    fun ByteArray.toIntArray(): Array<Int> {
        val intBuf = ByteBuffer.wrap(this)
                .order(ByteOrder.LITTLE_ENDIAN)
                .asIntBuffer()
        val array = IntArray(intBuf.remaining())
        intBuf.get(array)
        return array.toTypedArray()
    }

}