package com.strait.ivblanc.util

import com.strait.ivblanc.R

class MaterialCode {
    companion object {
        val COTTON = 1
        val LINEN = 2
        val POLYESTER = 3
        val WOOL = 4
        val FUR = 5
        val TWEED = 6
        val NYLON = 7
        val DENIM = 8
        val LEATHER = 9
        val SUEDE = 10
        val VELVET = 11
        val CHIFFON = 12
        val SILK = 13
        val CORDE_DU_ROI = 14
        val METALIC = 15
        val ETC = 16
    }

    private val _codeSet = mutableMapOf<Int, Int>().apply {
        put(COTTON, R.string.cotton)
        put(LINEN, R.string.linen)
        put(POLYESTER, R.string.polyester)
        put(WOOL, R.string.wool)
        put(FUR, R.string.fur)
        put(TWEED, R.string.tweed)
        put(NYLON, R.string.nylon)
        put(DENIM, R.string.denim)
        put(LEATHER, R.string.leather)
        put(SUEDE, R.string.suede)
        put(VELVET, R.string.velvet)
        put(CHIFFON, R.string.chiffon)
        put(SILK, R.string.silk)
        put(CORDE_DU_ROI, R.string.cordeDuRoi)
        put(METALIC, R.string.metalic)
        put(ETC, R.string.etc)
    }
    val codeSet: HashMap<Int, Int> get() = _codeSet as HashMap<Int, Int>
}