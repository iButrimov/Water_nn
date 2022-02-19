package com.example.water_nn.data.database.entity

import androidx.room.TypeConverter
import com.example.water_nn.domain.models.DeliveryTime

class DeliveryTimeConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromDeliveryTime(list: List<DeliveryTime>?): String {
            return list?.joinToString(",") { it.name } ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun toDeliveryTime(data: String?): List<DeliveryTime> {
            return data?.split(",")?.mapNotNull {
                safeValueOf<DeliveryTime>(it)
            } ?: emptyList()
        }
    }
}
