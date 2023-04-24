package com.example.compose_sample.data

import com.example.compose_sample.data.local.LocalEmailsDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 5:44 PM
 * @desc
 */
class EmailsRepositoryImpl : EmailsRepository {
    override fun getAllEmails(): Flow<List<Email>> = flow {
        emit(LocalEmailsDataProvider.allEmails)
    }

    override fun getCategoryEmails(category: MailboxType): Flow<List<Email>> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails.filter { it.mailbox == category }
        emit(categoryEmails)
    }

    override fun getAllFolders(): List<String> = LocalEmailsDataProvider.getAllFolders()

    override fun getEmailFromId(id: Long): Flow<Email?> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails.firstOrNull { it.id == id }
        emit(categoryEmails)
    }
}