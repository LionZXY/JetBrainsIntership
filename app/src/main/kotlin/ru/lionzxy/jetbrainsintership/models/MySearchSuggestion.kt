package ru.lionzxy.jetbrainsintership.models

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion


/**
 * Created by Nikita Kulikov on 30.04.17.
 *
 * Возможно полное или частичное копирование
 */
class MySearchSuggestion(val query: String) : SearchSuggestion {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(query)
    }


    override fun describeContents(): Int {
        return 1
    }

    override fun getBody(): String {
        return query
    }

    val CREATOR: Parcelable.Creator<MySearchSuggestion> = object : Parcelable.Creator<MySearchSuggestion> {
        override fun createFromParcel(`in`: Parcel): MySearchSuggestion {
            return MySearchSuggestion(`in`.readString())
        }

        override fun newArray(size: Int): Array<MySearchSuggestion?> {
            return arrayOfNulls(size)
        }
    }

}