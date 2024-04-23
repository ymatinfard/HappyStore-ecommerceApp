package com.matin.happystore.core.database.model

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalTypeConvertor {
    @TypeConverter
    fun bigDecimalToString(input: BigDecimal): String {
        return input.toString()
    }

    @TypeConverter
    fun stringToBigDecimal(input: String): BigDecimal {
        return input.toBigDecimal()
    }
}
