package com.folioreader.Labu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.folioreader.R
import android.text.Spannable
import android.text.SpannableString
import android.text.Html
import android.os.Build
import android.text.Spanned
import android.graphics.Typeface
import android.content.res.ColorStateList
import android.text.SpannableStringBuilder
import android.text.style.*
import java.util.regex.Pattern


class LaTelnaAdapter(val latel: List<LabuLatel>, val searchString:String, val  addmintom:Boolean) : RecyclerView.Adapter<LaTelnaAdapter.ViewHolder>() {

    private var mOnItemClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): LaTelnaAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.latel_layout, viewGroup, false)
        return ViewHolder(view)
    }

    fun setOnItemClickListener(itemClickListener: View.OnClickListener) {
        mOnItemClickListener = itemClickListener
    }



    override fun getItemCount(): Int {
        return latel.size
    }


    override fun onBindViewHolder(holder: LaTelnaAdapter.ViewHolder, i: Int) {
        holder.bind(latel = latel[i])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val title: TextView
        private val verses: TextView

        init {
            title = view.findViewById(R.id.lalehlabumin)
            verses = view.findViewById(R.id.latelte)
            view.tag = this
            view.setOnClickListener(mOnItemClickListener)
        }

        fun bind(latel: LabuLatel) {
            var labu = LabuData.get().getLabu(latel.bookno)
            if (addmintom) {
                var shortname = labu._LabuMinTom

                title.text = shortname + " " + latel.title
            } else {
                title.text = latel.title

            }
           // var text = setTitlebols(latel.verse, searchString)
            //verses.setText(text, TextView.BufferType.SPANNABLE)
            //setHighLightedText(latel.verse, searchString)
            //verses.text = latel.verse
            setAdvancedTitleHighlight(latel.verse, searchString)
        }

        fun setAdvancedTitleHighlight( fullText: String, searchText: String?) {
            var searchText = searchText

            searchText = searchText!!.replace("'", "")

            val WORD_SINGLE = " "

            // highlight search text
            if (null != searchText && !searchText.isEmpty() && searchText != WORD_SINGLE) {

                val wordSpan = SpannableStringBuilder(fullText)
                val words = searchText.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (wr in words) {
                    val p = Pattern.compile(wr, Pattern.CASE_INSENSITIVE)
                    val m = p.matcher(fullText)
                    while (m.find()) {

                        val WORD_BOUNDARY = ' '

                        var wordStart = m.start()
                        while (wordStart >= 0 && fullText[wordStart] != WORD_BOUNDARY) {
                            --wordStart
                        }
                        wordStart = wordStart + 1

                        var wordEnd = m.end()
                        while (wordEnd < fullText.length && fullText[wordEnd] != WORD_BOUNDARY) {
                            ++wordEnd
                        }

                        // Now highlight based on the word boundaries
                        /*val redColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(-0x5ef6ff))
                        val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, redColor, null)

                        wordSpan.setSpan(highlightSpan, wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        wordSpan.setSpan(BackgroundColorSpan(-0x300b8), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        wordSpan.setSpan(RelativeSizeSpan(1.25f), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)*/
                        wordSpan.setSpan(StyleSpan(Typeface.BOLD), wordStart, wordEnd, 0)
                        wordSpan.setSpan(UnderlineSpan(), wordStart, wordEnd, 0)

                    }
                }

                verses.setText(wordSpan, TextView.BufferType.SPANNABLE)

            } else {
                verses.text = fullText
            }
        }



    }








}