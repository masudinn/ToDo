package com.masudinn.todo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Todo(
    var id: Long? = null,
    var title: String? = null,
    var desc: String? = null
) : Parcelable