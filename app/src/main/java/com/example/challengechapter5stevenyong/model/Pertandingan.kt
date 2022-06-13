package com.example.challengechapter5stevenyong.model

import android.os.Parcel
import android.os.Parcelable

class Pertandingan() : Parcelable {
    var phase: Int = 0
        get() = field        // getter
        set(value) {         // setter
            field = value
        }

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pertandingan> {
        override fun createFromParcel(parcel: Parcel): Pertandingan {
            return Pertandingan(parcel)
        }

        override fun newArray(size: Int): Array<Pertandingan?> {
            return arrayOfNulls(size)
        }
    }
}