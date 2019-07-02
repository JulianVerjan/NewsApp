package com.test.bonialtest.repository.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.bonialtest.repository.local.utils.Constants.SOURCE_TABLE
import com.test.bonialtest.view.utils.Constants.EMPTY_STRING
import kotlinx.android.parcel.Parcelize

@Entity(tableName = SOURCE_TABLE)
@Parcelize
data class Source(
    val id: String? = EMPTY_STRING,
    @PrimaryKey
    val name: String = EMPTY_STRING
) : Parcelable