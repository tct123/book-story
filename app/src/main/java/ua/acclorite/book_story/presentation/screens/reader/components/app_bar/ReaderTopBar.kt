package ua.acclorite.book_story.presentation.screens.reader.components.app_bar

import androidx.activity.ComponentActivity
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ua.acclorite.book_story.R
import ua.acclorite.book_story.presentation.components.CustomIconButton
import ua.acclorite.book_story.presentation.data.LocalNavigator
import ua.acclorite.book_story.presentation.data.Screen
import ua.acclorite.book_story.presentation.data.removeDigits
import ua.acclorite.book_story.presentation.data.removeTrailingZero
import ua.acclorite.book_story.presentation.screens.history.data.HistoryEvent
import ua.acclorite.book_story.presentation.screens.library.data.LibraryEvent
import ua.acclorite.book_story.presentation.screens.reader.data.ReaderEvent
import ua.acclorite.book_story.presentation.screens.reader.data.ReaderState

/**
 * Reader top bar. Displays title of the book.
 *
 * @param state [ReaderState].
 * @param onEvent [ReaderEvent] callback.
 * @param onLibraryUpdateEvent [LibraryEvent] callback.
 * @param onHistoryUpdateEvent [HistoryEvent] callback.
 * @param containerColor Container color.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderTopBar(
    state: State<ReaderState>,
    onEvent: (ReaderEvent) -> Unit,
    onLibraryUpdateEvent: (LibraryEvent.OnUpdateBook) -> Unit,
    onHistoryUpdateEvent: (HistoryEvent.OnUpdateBook) -> Unit,
    containerColor: Color
) {
    val navigator = LocalNavigator.current
    val context = LocalContext.current as ComponentActivity

    val progress by remember(state.value.book.progress) {
        derivedStateOf {
            (state.value.book.progress * 100)
                .toDouble()
                .removeDigits(2)
                .removeTrailingZero()
                .dropWhile { it == '-' } + "%"
        }
    }

    TopAppBar(
        navigationIcon = {
            CustomIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = R.string.go_back_content_desc,
                disableOnClick = true
            ) {
                onEvent(
                    ReaderEvent.OnGoBack(
                        context,
                        navigator,
                        refreshList = {
                            onLibraryUpdateEvent(LibraryEvent.OnUpdateBook(it))
                            onHistoryUpdateEvent(HistoryEvent.OnUpdateBook(it))
                        },
                        navigate = {
                            it.navigateBack()
                        }
                    )
                )
            }
        },
        title = {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    state.value.book.title,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    modifier = Modifier
                        .clickable(
                            enabled = !state.value.lockMenu,
                            interactionSource = null,
                            indication = null,
                            onClick = {
                                onEvent(
                                    ReaderEvent.OnGoBack(
                                        context,
                                        navigator,
                                        refreshList = {
                                            onLibraryUpdateEvent(LibraryEvent.OnUpdateBook(it))
                                            onHistoryUpdateEvent(HistoryEvent.OnUpdateBook(it))
                                        },
                                        navigate = {
                                            it.navigateWithoutBackStack(
                                                Screen.BOOK_INFO,
                                                true
                                            )
                                        }
                                    )
                                )
                            }
                        )
                        .basicMarquee(
                            iterations = Int.MAX_VALUE
                        )
                )
                Text(
                    stringResource(
                        id = R.string.read_query,
                        progress
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        actions = {
            CustomIconButton(
                icon = Icons.Default.Translate,
                contentDescription = R.string.translator_content_desc,
                disableOnClick = false,
                enabled = !state.value.lockMenu,
                color = if (state.value.book.enableTranslator) {
                    MaterialTheme.colorScheme.primary
                } else LocalContentColor.current
            ) {
                onEvent(ReaderEvent.OnShowHideTranslatorBottomSheet())
            }
            CustomIconButton(
                icon = Icons.Default.Settings,
                contentDescription = R.string.open_reader_settings_content_desc,
                disableOnClick = false,
                enabled = !state.value.lockMenu
            ) {
                onEvent(ReaderEvent.OnShowHideSettingsBottomSheet)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor
        )
    )
}