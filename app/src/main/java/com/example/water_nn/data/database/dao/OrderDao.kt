package com.example.water_nn.data.database.dao

import androidx.room.*
import com.example.water_nn.data.database.entity.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Query("SELECT * FROM order_table WHERE id IN (:orderId)")
    fun getOrderById(orderId: String): Order

//    @Query("SELECT * FROM order_table")
//    fun getAll(): List<Order>

    @Query("SELECT * FROM order_table ORDER BY id DESC")
    fun getAllOrders(): Flow<List<Order>>

//    @Query("SELECT * FROM order_table WHERE id IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<Order>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrder(order: Order)

    @Delete
    fun deleteOrder(order: Order)

}