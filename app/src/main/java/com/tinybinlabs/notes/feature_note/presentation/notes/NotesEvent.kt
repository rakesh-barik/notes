package com.tinybinlabs.notes.feature_note.presentation.notes

import com.tinybinlabs.notes.feature_note.domain.model.Note
import com.tinybinlabs.notes.feature_note.domain.util.NoteOrder

/**
 * Contains UI events which can be passed from Compose components to ViewModels
 *
 * */
sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
