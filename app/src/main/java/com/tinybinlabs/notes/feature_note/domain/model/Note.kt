package com.tinybinlabs.notes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tinybinlabs.notes.ui.theme.*
import java.lang.Exception

@Entity
class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: String,
    @PrimaryKey val id: Int? = null
) {

    companion object {
        val noteColors = listOf(RedOrange, RedPink, Violet, BabyBlue, LightGreen)
    }

    class InvalidNoteException(message: String) : Exception(message)
}