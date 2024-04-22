package com.example.compose_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_sample.data.Email
import com.example.compose_sample.data.EmailsRepository
import com.example.compose_sample.data.EmailsRepositoryImpl
import com.example.compose_sample.ui.utils.ReplyContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 5:03 PM
 * @desc
 */
class ReplyHomeViewModel(private val emailsRepository: EmailsRepository = EmailsRepositoryImpl()) :
    ViewModel()
{
    init {
        observeEmails()
    }

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(ReplyHomeUIState(loading = true))
    val uiState: StateFlow<ReplyHomeUIState> = _uiState

    private fun observeEmails(){
        viewModelScope.launch {
            emailsRepository.getAllEmails()
                .catch { ex ->
                    _uiState.value = ReplyHomeUIState(error = ex.message)
                }
                .collect{ emails ->

                    _uiState.value = ReplyHomeUIState(emails = emails, selectedEmail = emails.first())
                }
        }
    }

    fun setSelectedEmail(emailId: Long, contentType: ReplyContentType){
        val email = uiState.value.emails.find { it.id == emailId }
        _uiState.value = _uiState.value.copy(
            selectedEmail = email,
            isDetailOnlyOpen = contentType == ReplyContentType.SINGLE_PANE
        )
    }

    fun closeDetailScreen(){
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedEmail = _uiState.value.emails.first()
            )
    }
}

data class ReplyHomeUIState(
    val emails: List<Email> = emptyList(),
    val selectedEmail: Email? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)