package com.tinybinlabs.notes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinybinlabs.notes.feature_note.domain.model.Note
import com.tinybinlabs.notes.feature_note.domain.use_case.NoteUseCases
import com.tinybinlabs.notes.feature_note.domain.util.NoteOrder
import com.tinybinlabs.notes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {

    private var recentlyDeletedNote: Note? = null
    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
        when(event) {
            is NotesEvent.Order -> {
                //checking if the same order and type is reselected, if so we don't do anything.
                //we are checking ::class here because NoteOrder is not override equals method
                // and in this case we dont want to do that in NoteOrder class for simplicity
                if(state.value.noteOrder::class == event.noteOrder::class &&
                   state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotesUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(order: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(order)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = order
                )
            }
            .launchIn(viewModelScope)
    }
}