package com.tinybinlabs.notes.feature_note.presentation

sealed class Screen(val route: String){
object NotesScreen: Screen("notes_screen")
object AddEditScreen: Screen("add_edit_notes_screen")
}
