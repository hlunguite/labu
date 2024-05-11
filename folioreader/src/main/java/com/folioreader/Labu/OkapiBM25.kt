package com.folioreader.Labu

import kotlin.math.log

class OkapiBM25 {

    companion object {

        fun score(matchinfo: Array<Int>, column: Int, b: Double = 0.75, k1: Double = 1.2): Double {

            val pOffset = 0
            val cOffset = 1
            val nOffset = 2
            val aOffset = 3

            val termCount = matchinfo[pOffset]
            val colCount = matchinfo[cOffset]

            val lOffset = aOffset + colCount
            val xOffset = lOffset + colCount

            val totalDocs = matchinfo[nOffset].toDouble()
            val avgLength = matchinfo[aOffset + column].toDouble()
            val docLength = matchinfo[lOffset + column].toDouble()

            var score = 0.0

            for (i in 0 until termCount) {

                val currentX = xOffset + (3 * (column + i * colCount))
                val termFrequency = matchinfo[currentX].toDouble()
                val docsWithTerm = matchinfo[currentX + 2].toDouble()

                val p = totalDocs - docsWithTerm + 0.5
                val q = docsWithTerm + 0.5
                val idf = log(p,q)

                val r = termFrequency * (k1 + 1)
                val s = b * (docLength / avgLength)
                val t = termFrequency + (k1 * (1 - b + s))
                val rightSide = r/t

                score += (idf * rightSide)
            }

            return score

        }

    }

}