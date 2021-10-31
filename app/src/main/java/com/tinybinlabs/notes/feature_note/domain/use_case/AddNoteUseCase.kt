package com.tinybinlabs.notes.feature_note.domain.use_case

import com.tinybinlabs.notes.feature_note.domain.model.Note
import com.tinybinlabs.notes.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(private val repository: NoteRepository) {

    @Throws(Note.InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        // check the validity of the data here in the use case, not in view model
        if (note.title.isBlank()) {
            throw Note.InvalidNoteException("Title of the note can't be empty.")
        }
        if (note.content.isBlank()) {
            throw Note.InvalidNoteException("Content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}