package com.example.compose_sample.data

import kotlinx.coroutines.flow.Flow

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 5:22 PM
 * @desc
 */
interface EmailsRepository {
    fun getAllEmails(): Flow<List<Email>>
    fun getCategoryEmails(category: MailboxType): Flow<List<Email>>
    fun getAllFolders(): List<String>
    fun getEmailFromId(id: Long): Flow<Email?>
}