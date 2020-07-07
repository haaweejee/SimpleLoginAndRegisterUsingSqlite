package id.laka.loginregisterusingsqlite.activity.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val name: String,
    val password: String

): Parcelable