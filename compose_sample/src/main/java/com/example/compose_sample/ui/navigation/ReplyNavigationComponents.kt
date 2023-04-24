package com.example.compose_sample.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.example.compose_sample.R
import com.example.compose_sample.ui.utils.ReplyNavigationContentPosition

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 4:41 PM
 * @desc
 */

@Composable
fun ReplyNavigationRail(
    selectedDestination: String,
    navigationContentPosition: ReplyNavigationContentPosition,
    navigateToTopLevelDestination: (ReplyTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = { },
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Layout(
            modifier = Modifier.widthIn(max = 80.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    NavigationRailItem(
                        selected = false,
                        onClick = onDrawerClicked,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    )
                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Column(
                    modifier = Modifier.layoutId(LayoutType.CONTENT),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach{ replyDestination ->
                        NavigationRailItem(
                            selected = selectedDestination == replyDestination.route,
                            onClick = { navigateToTopLevelDestination(replyDestination) },
                            icon = {
                                Icon(
                                    imageVector = replyDestination.selectedIcon,
                                    contentDescription = stringResource(id = replyDestination.iconTextId)
                                )
                            }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                lateinit var headerMeasurable: Measurable
                lateinit var contentMeasurable: Measurable
                measurables.forEach{
                    when(it.layoutId){
                        LayoutType.HEADER -> headerMeasurable = it
                        LayoutType.CONTENT -> contentMeasurable = it
                        else -> error("Unknown layoutId ${it.layoutId}")
                    }
                }
                val headerPlaceable = headerMeasurable.measure(constraints)
                val contentPlaceable = contentMeasurable.measure(
                    constraints.offset(vertical = -headerPlaceable.height)
                )
                layout(constraints.maxWidth, constraints.maxHeight){
                    headerPlaceable.placeRelative(0, 0)
                    val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                    val contentPlaceableY = when(navigationContentPosition){
                        ReplyNavigationContentPosition.TOP -> 0
                        ReplyNavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
                    }
                        .coerceAtLeast(headerPlaceable.height)
                    contentPlaceable.placeRelative(0, contentPlaceableY)
                }
            },

        )
    }
}

@Preview
@Composable
fun ReplyNavigationRailPreview(){
    ReplyNavigationRail(
        selectedDestination = "1",
        navigationContentPosition = ReplyNavigationContentPosition.TOP,
        navigateToTopLevelDestination = { }
    )
}

@Composable
fun ReplyBottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (ReplyTopLevelDestination) -> Unit,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach{ replyDestination ->
            NavigationBarItem(
                selected = selectedDestination == replyDestination.route,
                onClick = { navigateToTopLevelDestination(replyDestination) },
                icon = {
                    Icon(
                        imageVector = replyDestination.selectedIcon,
                        contentDescription = stringResource(id = replyDestination.iconTextId)
                    )
                }
            )
            
        }
    }
}

@Preview
@Composable
fun ReplyBottomNavigationBarPreview(){
    ReplyBottomNavigationBar(
        selectedDestination = "1",
        navigateToTopLevelDestination = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermanentNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: ReplyNavigationContentPosition,
    navigateToTopLevelDestination: (ReplyTopLevelDestination) -> Unit,
){
    PermanentDrawerSheet(
        modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp)
    ) {
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(16.dp),
            content ={
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(id = R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ExtendedFloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 40.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.compose),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach{ replyDestination ->
                        NavigationDrawerItem(
                            modifier = Modifier.padding(top = 4.dp),
                            selected = selectedDestination == replyDestination.route,
                            label = {
                                Text(
                                    text = stringResource(id = replyDestination.iconTextId),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = replyDestination.selectedIcon,
                                    contentDescription = stringResource(id = replyDestination.iconTextId)
                                )
                            },
                            onClick = { navigateToTopLevelDestination(replyDestination) }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                lateinit var headerMeasurable: Measurable
                lateinit var contentMeasurable: Measurable
                measurables.forEach{
                    when(it.layoutId){
                        LayoutType.HEADER -> headerMeasurable = it
                        LayoutType.CONTENT -> contentMeasurable = it
                        else -> error("Unknown layoutId ${it.layoutId}")
                    }
                }
                val headerPlaceable = headerMeasurable.measure(constraints)
                val contentPlaceable = contentMeasurable.measure(
                    constraints.offset(vertical = -headerPlaceable.height)
                )
                layout(constraints.maxWidth, constraints.maxHeight){
                    // Place the header, this goes at the top
                    headerPlaceable.placeRelative(0, 0)

                    // Determine how much space is not taken up by the content
                    val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                    val contentPlaceY = when(navigationContentPosition){
                        ReplyNavigationContentPosition.TOP -> 0
                        ReplyNavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
                    }.coerceAtLeast(headerPlaceable.height)

                    contentPlaceable.placeRelative(0, contentPlaceY)
                }
            }
        )
    }
}

@Preview
@Composable
fun PermanentNavigationDrawerContentPreview(){
    PermanentNavigationDrawerContent(
        selectedDestination = "1",
        navigationContentPosition = ReplyNavigationContentPosition.TOP,
        navigateToTopLevelDestination = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: ReplyNavigationContentPosition,
    navigateToTopLevelDestination: (ReplyTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit
){
    ModalDrawerSheet {
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = stringResource(id = R.string.app_name).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = onDrawerClicked) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    }
                    ExtendedFloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 40.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.compose),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { replyDestination ->
                        NavigationDrawerItem(
                            selected = selectedDestination == replyDestination.route,
                            label = {
                                Text(
                                    text = stringResource(id = replyDestination.iconTextId),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = replyDestination.selectedIcon,
                                    contentDescription = stringResource(id = replyDestination.iconTextId)
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            ),
                            onClick = { navigateToTopLevelDestination(replyDestination)}
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                lateinit var headerMeasurable: Measurable
                lateinit var contentMeasurable: Measurable
                measurables.forEach {
                    when(it.layoutId){
                        LayoutType.HEADER -> headerMeasurable = it
                        LayoutType.CONTENT -> contentMeasurable = it
                        else -> error("Unknown layoutId ${it.layoutId}")
                    }
                }
                val headerPlaceable = headerMeasurable.measure(constraints)
                val contentPlaceable = contentMeasurable.measure(
                    constraints.offset(vertical = -headerPlaceable.height)
                )
                layout(constraints.maxWidth, constraints.maxHeight){
                    // Place the header, this goes at the top
                    headerPlaceable.placeRelative(0, 0)

                    // Determine how much space is not taken up by the content
                    val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                    val contentPlaceableY = when(navigationContentPosition){
                        ReplyNavigationContentPosition.TOP -> 0
                        ReplyNavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
                    }
                        .coerceAtLeast(headerPlaceable.height)
                    contentPlaceable.placeRelative(0, contentPlaceableY)
                }
            }
        )
    }
}

@Preview
@Composable
fun ModalNavigationDrawerContentPreview(){
    ModalNavigationDrawerContent(
        selectedDestination = "1",
        navigationContentPosition = ReplyNavigationContentPosition.TOP,
        navigateToTopLevelDestination = { },
        onDrawerClicked = { }
    )
}

enum class LayoutType{
    HEADER,
    CONTENT
}