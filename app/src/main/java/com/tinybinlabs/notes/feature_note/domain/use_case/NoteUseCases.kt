package com.tinybinlabs.notes.feature_note.domain.use_case

/**
 * A wrapper class to hold the different types of use cases
 * */
data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNotesUseCase: DeleteNotesUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase
)
