package com.example.compose_sample.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.example.compose_sample.R
import com.example.compose_sample.ReplyHomeUIState
import com.example.compose_sample.data.Email
import com.example.compose_sample.data.local.LocalEmailsDataProvider
import com.example.compose_sample.ui.components.EmailDetailAppBar
import com.example.compose_sample.ui.components.ReplyEmailListItem
import com.example.compose_sample.ui.components.ReplyEmailThreadItem
import com.example.compose_sample.ui.components.ReplySearchBar
import com.example.compose_sample.ui.utils.ReplyContentType
import com.example.compose_sample.ui.utils.ReplyNavigationType
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 11:46 PM
 * @desc
 */

@Composable
fun ReplyInboxScreen(
    contentType: ReplyContentType,
    replyHomeUIState: ReplyHomeUIState,
    navigationType: ReplyNavigationType,
    displayFeature: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = contentType) {
        if (contentType == ReplyContentType.SINGLE_PANE && !replyHomeUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }

    val emailLazyListState = rememberLazyListState()

    if (contentType == ReplyContentType.DUAL_PANE) {
        TwoPane(
            first = {
                ReplyEmailList(
                    emails = replyHomeUIState.emails,
                    emailLazyListState = emailLazyListState,
                    navigateToDetail = navigateToDetail
                )
            },
            second = {
                ReplyEmailDetail(
                    email = replyHomeUIState.selectedEmail ?: replyHomeUIState.emails.first(),
                    isFullScreen = false
                )
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
            displayFeatures = displayFeature
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            ReplySinglePaneContent(
                replyHomeUIState = replyHomeUIState,
                emailLazyListState = emailLazyListState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
            )
            if (navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
                LargeFloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ReplyInboxScreenPreview() {
    ReplyInboxScreen(
        contentType = ReplyContentType.SINGLE_PANE,
        replyHomeUIState = ReplyHomeUIState(
            emails = LocalEmailsDataProvider.allEmails,
            selectedEmail = LocalEmailsDataProvider.allEmails.first(),
            isDetailOnlyOpen = true
        ),
        navigationType = ReplyNavigationType.BOTTOM_NAVIGATION,
        displayFeature = emptyList(),
        closeDetailScreen = { },
        navigateToDetail = { _, _ -> }
    )
}

@Composable
fun ReplySinglePaneContent(
    replyHomeUIState: ReplyHomeUIState,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit
) {
    if (replyHomeUIState.selectedEmail != null && replyHomeUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        ReplyEmailDetail(email = replyHomeUIState.selectedEmail) {
            closeDetailScreen()
        }
    } else {
        ReplyEmailList(
            emails = replyHomeUIState.emails,
            emailLazyListState = emailLazyListState,
            modifier = modifier,
            navigateToDetail = navigateToDetail
        )
    }
}

@Preview
@Composable
fun ReplySinglePaneContentPreview() {
    ReplySinglePaneContent(
        replyHomeUIState = ReplyHomeUIState(
            emails = LocalEmailsDataProvider.allEmails,
            selectedEmail = LocalEmailsDataProvider.allEmails.first(),
            isDetailOnlyOpen = true
        ),
        emailLazyListState = LazyListState(),
        closeDetailScreen = { },
        navigateToDetail = { _, _ -> }
    )
}


@Composable
fun ReplyEmailList(
    emails: List<Email>,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long, ReplyContentType) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = emailLazyListState
    ) {
        item {
            ReplySearchBar(modifier = Modifier.fillMaxWidth())
        }
        items(items = emails, key = { it.id }) { email ->
            ReplyEmailListItem(email = email) { emailId ->
                navigateToDetail(emailId, ReplyContentType.SINGLE_PANE)
            }
        }
    }
}

@Preview
@Composable
fun ReplyEmailListPreview() {
    ReplyEmailList(
        emails = LocalEmailsDataProvider.allEmails,
        emailLazyListState = LazyListState(),
        navigateToDetail = { _, _ -> }
    )
}

@Composable
fun ReplyEmailDetail(
    email: Email,
    isFullScreen: Boolean = true,
    modifier: Modifier = Modifier.fillMaxSize(),
    onBackPressed: () -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(top = 16.dp)
    ) {
        item {
            EmailDetailAppBar(email = email, isFullScreen = isFullScreen) {
                onBackPressed()
            }
        }
        items(items = email.threads, key = { it.id }) { email ->
            ReplyEmailThreadItem(email = email)
        }
    }
}

@Preview
@Composable
fun ReplyEmailDetailPreview() {
    ReplyEmailDetail(email = LocalEmailsDataProvider.get(0L)!!)
}