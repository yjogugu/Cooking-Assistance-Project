package com.taijoo.cookingassistance.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "StorageMaterial" )
data class StorageMaterialData(@PrimaryKey(autoGenerate = true) val seq : Long,
                               @ColumnInfo(name = "name") var name : String,
                               @ColumnInfo(name = "size") var size : Int,
                               @ColumnInfo(name = "type") var type : Int,
                               @ColumnInfo(name = "note") var note : String,
                               @ColumnInfo(name = "expiration_date") var expiration_date : String,
                               @ColumnInfo(name = "date") var date : String,
                               @ColumnInfo(name = "deleteType") var deleteType : Int){

    constructor() : this(0,"",0,0,"","","",0)
}