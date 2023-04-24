package com.example.compose_sample.data

import kotlinx.coroutines.flow.Flow

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 5:18 PM
 * @desc
 */
interface AccountsRepository {
    fun getDefaultUserAccount(): Flow<Account>
    fun getAllUserAccounts(): Flow<List<Account>>
    fun getContractAccountByUid(uid: Long): Flow<Account>
}