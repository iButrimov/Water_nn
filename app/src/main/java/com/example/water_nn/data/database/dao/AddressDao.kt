package com.example.water_nn.data.database.dao

import androidx.room.*
import com.example.water_nn.data.database.entity.Address

@Dao
interface AddressDao {

    @Query("SELECT * FROM address_table WHERE id IN (:addressId)")
    fun getAddressById(addressId: String): Address

    @Query("SELECT * FROM address_table")
    fun getAddressList(): List<Address>

    @Query("SELECT * FROM address_table ORDER BY is_Selected DESC")
    fun getAddressListBySelected(): List<Address>

    @Update
    fun updateAddress(address: Address)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAddress(address: Address)

    @Delete
    fun deleteAddress(address: Address)
}