package com.example.compose_sample.data

import com.example.compose_sample.data.Account
import com.example.compose_sample.data.AccountsRepository
import com.example.compose_sample.data.local.LocalAccountsDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 5:20 PM
 * @desc
 */
class AccountsRepositoryImpl : AccountsRepository {
    override fun getDefaultUserAccount(): Flow<Account> = flow {
        emit(LocalAccountsDataProvider.getDefaultUserAccount())
    }

    override fun getAllUserAccounts(): Flow<List<Account>> = flow {
        emit(LocalAccountsDataProvider.allUserAccounts)
    }

    override fun getContractAccountByUid(uid: Long): Flow<Account> = flow {
        emit(LocalAccountsDataProvider.getContactAccountByUid(uid))
    }
}