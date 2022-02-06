package com.strait.ivblanc.util

import com.strait.ivblanc.R

class CategoryCode {
    companion object {
        val UNSELECTED = -2
        val TOTAL_SMALL = -1
        val TOTAL = 0
        val TOP = 1
        val T_SHIRT = 10
        val SHIRT = 11
        val HOOD = 12
        val SWEAT_SHIRT = 13
        val BLOUSE = 14
        val CROP_TOP = 15
        val DRESS = 16
        val TOP_ETC = 17
        val BOTTOM = 2
        val DENIM_PANTS = 20
        val COTTON_PANTS = 21
        val SHORT_PANTS = 22
        val SUIT_PANTS = 23
        val SPORT_PANTS = 24
        val SKIRT = 25
        val LEGGINGS = 26
        val PANTS_ETC = 27
        val OUTER = 3
        val COAT = 30
        val JACKET = 31
        val PADDING = 32
        val VEST = 33
        val CARDIGAN = 34
        val OUTER_ETC = 35
        val SHOES = 4
        val SNEAKERS = 40
        val SPORTS_SHOES = 41
        val LOAFERS = 42
        val BOOTS = 43
        val SLIPPERS = 44
        val HEELS = 45
        val SHOES_ETC = 46
        val BAG = 5
        val BACKPACK = 50
        val CROSS_BAG = 51
        val SHOULDER_BAG = 52
        val HANDBAG = 53
        val BAG_ETC = 54
        val HAT = 6
        val HAT_TOTAL= 60
        val ETC = 7
        val ETC_TOTAL = 70
    }
    private val _codeSet = mutableMapOf<Int, Int>().apply {
        put(TOTAL_SMALL, R.string.total)
        put(TOTAL, R.string.total)
        put(TOP, R.string.top)
        put(T_SHIRT, R.string.tShirt)
        put(SHIRT, R.string.shirt)
        put(HOOD, R.string.hood)
        put(SWEAT_SHIRT, R.string.sweatShirt)
        put(BLOUSE, R.string.blouse)
        put(CROP_TOP, R.string.cropTop)
        put(DRESS, R.string.dress)
        put(TOP_ETC, R.string.etc)
        put(BOTTOM, R.string.bottom)
        put(DENIM_PANTS, R.string.denimPants)
        put(COTTON_PANTS, R.string.cottonPants)
        put(SHORT_PANTS, R.string.shortPants)
        put(SUIT_PANTS, R.string.suitPants)
        put(SPORT_PANTS, R.string.sportPants)
        put(SKIRT, R.string.skirt)
        put(LEGGINGS, R.string.leggings)
        put(PANTS_ETC, R.string.etc)
        put(OUTER, R.string.outer)
        put(COAT, R.string.coat)
        put(JACKET, R.string.jacket)
        put(PADDING, R.string.padding)
        put(VEST, R.string.vest)
        put(CARDIGAN, R.string.cardigan)
        put(OUTER_ETC, R.string.etc)
        put(SHOES, R.string.shoes)
        put(SNEAKERS, R.string.sneakers)
        put(SPORTS_SHOES, R.string.sportsShoes)
        put(LOAFERS, R.string.loafers)
        put(BOOTS, R.string.boots)
        put(SLIPPERS, R.string.slippers)
        put(HEELS, R.string.heels)
        put(SHOES_ETC, R.string.etc)
        put(BAG, R.string.bag)
        put(BACKPACK, R.string.backpack)
        put(CROSS_BAG, R.string.crossBag)
        put(SHOULDER_BAG, R.string.shoulderBag)
        put(HANDBAG, R.string.handbag)
        put(BAG_ETC, R.string.etc)
        put(HAT, R.string.hat)
        put(HAT_TOTAL, R.string.total)
        put(ETC, R.string.etc)
        put(ETC_TOTAL, R.string.total)
    }

    val codeSet: HashMap<Int, Int> get() = _codeSet as HashMap<Int, Int>
}