package com.paite.project.labu2019

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.text.Html
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.folioreader.Constants
import com.folioreader.FolioReader
import com.folioreader.Labu.LabuApplication
import com.folioreader.R
import com.folioreader.ui.activity.FolioActivity
import com.folioreader.ui.base.HtmlTask
import com.folioreader.ui.base.HtmlTaskCallback
import com.folioreader.ui.base.HtmlUtil
import com.folioreader.util.AppUtil
import com.folioreader.util.FileUtil
import org.readium.r2.shared.Link
import org.readium.r2.shared.Publication
import org.readium.r2.streamer.parser.CbzParser
import org.readium.r2.streamer.parser.EpubParser
import org.readium.r2.streamer.parser.PubBox
import org.readium.r2.streamer.server.Server
import android.webkit.WebViewClient
//import android.R




class EpubReader constructor (context : Context): HtmlTaskCallback {
    private var pubBox: PubBox? = null
    private var spine: List<Link>? = null
    private var streamerUri: Uri? = null
    private lateinit var chapterUrl: Uri
    private lateinit var spineItem: Link
    private lateinit var uiHandler: Handler
    private var _context :Context? = null
    private var size : Int = 0
    private var currentId:Int = -1
    private  var read: Boolean = true

    init {
        _context = context
    }

    public fun readBook(mEpubFilePath: String) {
        try {
            initBook(mEpubFilePath)
            readSuccess()
        } catch (e: Exception) {

        }

    }
    private fun readSuccess() {
        val publication = pubBox!!.publication
        spine = publication.readingOrder
        size = spine!!.size
        currentId = 0
        read = true
        //Log.d("READER", "size " + spine!!.size )
  /*      while (true) {
            if (read) {
                if (currentId >= size) {
                    break
                }
                read = false
                readPage(currentId)
                ++currentId
            }
                  }*/


        //  title = publication.metadata.title
    }
    private fun readPage(pageno: Int) {
        if (pageno >= size) {
            return
        }
        currentId = pageno
        spineItem = (spine as MutableList<Link>).get(pageno)
        Log.d("READER", "href " + spineItem.href)

        chapterUrl = Uri.parse(streamerUri.toString() + spineItem.href!!.substring(1))
        uiHandler = Handler()

        HtmlTask(this).execute(chapterUrl.toString())
    }
    override fun onReceiveHtml(html: String) {
        //<string name="script_tag"><![CDATA[<script type=\"text/javascript\" src=\"%s\"></script>]]></string>

       var jsPath :String = "![CDATA[<script type=\"text/javascript\" src=\"file:///android_asset/js/Bridge.js\"></script>]] "

        "</script>"
        val toInject = "\n$jsPath\n</head>"
        var htmlText = html.replace("</head>", toInject)
       // Log.d("READER HTML", htmlText)


        val href = spineItem.href
        var path = ""
        val forwardSlashLastIndex = href!!.lastIndexOf('/')
        if (forwardSlashLastIndex != -1) {
            path = href.substring(1, forwardSlashLastIndex + 1)
        }
        val mimeType: String =
                if (spineItem.typeLink!!.equals("application/xhtml+xml", true)) {
                    "application/xhtml+xml"
                } else {
                    "text/html"
                }

        var mWebview : WebView = WebView(_context)
        //mWebview  = WebView(_context)
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.addJavascriptInterface(this, "EpubReader")

        mWebview.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                //  Log.d("READER", "loading done")
                mWebview.loadUrl("javascript:getHTMLBodyText()")
            }
        })

        uiHandler.post {
            mWebview.loadDataWithBaseURL(
                    streamerUri.toString() + path,
                    htmlText,
                    mimeType,
                    "UTF-8", null
            )
          //  Log.d("READER setHtml", "done")



           // mWebview!!.loadUrl("javascript:getHTMLBodyText()")

        }

    }
    override fun onError() {}

    @JavascriptInterface
    fun htmlContent(aContent : String){
        var htmlstring = Html.fromHtml(aContent).toString();
        var lines = htmlstring.lines()
        lines.forEach {
            Log.d("READERLINES", it)
        }
        read = true
      //  readPage(currentId + 1)
       // Log.d("READER body",Html.fromHtml(aContent).toString())
    }
    @Throws(Exception::class)
    private fun initBook(mEpubFilePath: String) {

       var  bookFileName:String = getEpubFilename(  mEpubFilePath )
        val path = FileUtil.saveEpubFileAndLoadLazyBook(
                _context, FolioActivity.EpubSourceType.ASSETS, mEpubFilePath,
                0, bookFileName
        )
        Log.d("READER", bookFileName)
        Log.d("READER", path)
        val extension: Publication.EXTENSION
        var extensionString: String? = null
        try {
            extensionString = FileUtil.getExtensionUppercase(path)
            extension = Publication.EXTENSION.valueOf(extensionString)
        } catch (e: IllegalArgumentException) {
            throw Exception("-> Unknown book file extension `$extensionString`", e)
        }
        pubBox  = when (extension) {
            Publication.EXTENSION.EPUB -> {
                val epubParser = EpubParser()
                epubParser.parse(path!!, "")
            }
            Publication.EXTENSION.CBZ -> {
                val cbzParser = CbzParser()
                cbzParser.parse(path!!, "")
            }
            else -> {
                null
            }
        }

        var portNumber = Constants.DEFAULT_PORT_NUMBER
        portNumber = AppUtil.getAvailablePortNumber(portNumber)

        var r2StreamerServer: Server? = Server(portNumber)
        r2StreamerServer!!.addEpub(
                pubBox!!.publication, pubBox!!.container,
                "/" + bookFileName!!, null
        )

        r2StreamerServer!!.start()

        streamerUri  = Uri.parse(String.format(Constants.STREAMER_URL_TEMPLATE, Constants.LOCALHOST, portNumber, bookFileName))

        FolioReader.initRetrofit(streamerUri.toString())
    }

    private fun getEpubFilename( epubFilePath : String) : String {
        val temp = epubFilePath.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
       var epubFileName: String = temp[temp.size - 1]
        val fileMaxIndex = epubFileName.length
        epubFileName = epubFileName.substring(0, fileMaxIndex - 5)

        return epubFileName
    }
}