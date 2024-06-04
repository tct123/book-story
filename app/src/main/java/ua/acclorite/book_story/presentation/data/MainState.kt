package ua.acclorite.book_story.presentation.data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.google.mlkit.nl.translate.TranslateLanguage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ua.acclorite.book_story.domain.model.LanguageHistory
import ua.acclorite.book_story.domain.util.Constants
import ua.acclorite.book_story.domain.util.DataStoreConstants
import ua.acclorite.book_story.presentation.ui.DarkTheme
import ua.acclorite.book_story.presentation.ui.PureDark
import ua.acclorite.book_story.presentation.ui.Theme
import ua.acclorite.book_story.presentation.ui.ThemeContrast
import ua.acclorite.book_story.presentation.ui.toDarkTheme
import ua.acclorite.book_story.presentation.ui.toPureDark
import ua.acclorite.book_story.presentation.ui.toTheme
import ua.acclorite.book_story.presentation.ui.toThemeContrast
import java.util.Locale

val translatorHistoryMoshi: JsonAdapter<List<LanguageHistory>> = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
    .adapter(
        Types.newParameterizedType(
            List::class.java,
            LanguageHistory::class.java
        )
    )

/**
 * Main State. All app's settings/preferences/permanent-variables are here. Wrapped in SavedStateHandle, so it won't reset.
 */
@Immutable
data class MainState(
    val language: String? = null,
    val theme: Theme? = null,
    val darkTheme: DarkTheme? = null,
    val pureDark: PureDark? = null,
    val themeContrast: ThemeContrast? = null,
    val fontFamily: String? = null,
    val isItalic: Boolean? = null,
    val fontSize: Int? = null,
    val lineHeight: Int? = null,
    val paragraphHeight: Int? = null,
    val paragraphIndentation: Boolean? = null,
    val backgroundColor: Long? = null,
    val fontColor: Long? = null,
    val showStartScreen: Boolean? = null,
    val checkForUpdates: Boolean? = null,
    val sidePadding: Int? = null,
    val enableTranslator: Boolean? = null,
    val translateFrom: String? = null,
    val translateTo: String? = null,
    val doubleClickTranslation: Boolean? = null,
    val translatorLanguageHistory: List<LanguageHistory>? = null,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        // String
        language = parcel.readString(),
        // String
        theme = Theme.valueOf(parcel.readString() ?: Theme.BLUE.name),
        // String
        darkTheme = DarkTheme.valueOf(parcel.readString() ?: DarkTheme.FOLLOW_SYSTEM.name),
        // String
        pureDark = PureDark.valueOf(parcel.readString() ?: PureDark.OFF.name),
        // String
        themeContrast = ThemeContrast.valueOf(parcel.readString() ?: ThemeContrast.STANDARD.name),
        // String
        fontFamily = parcel.readString(),
        // Boolean
        isItalic = if (parcel.readByte().toInt() != 0) parcel.readByte().toInt() == 1 else null,
        // Int
        fontSize = if (parcel.readByte().toInt() != 0) parcel.readInt() else null,
        // Int
        lineHeight = if (parcel.readByte().toInt() != 0) parcel.readInt() else null,
        // Int
        paragraphHeight = if (parcel.readByte().toInt() != 0) parcel.readInt() else null,
        // Boolean
        paragraphIndentation = if (parcel.readByte().toInt() != 0) parcel.readByte()
            .toInt() == 1 else null,
        // Long
        backgroundColor = if (parcel.readByte().toInt() != 0) parcel.readLong() else null,
        // Long
        fontColor = if (parcel.readByte().toInt() != 0) parcel.readLong() else null,
        // Boolean
        showStartScreen = if (parcel.readByte().toInt() != 0) parcel.readByte()
            .toInt() == 1 else null,
        // Boolean
        checkForUpdates = if (parcel.readByte().toInt() != 0) parcel.readByte()
            .toInt() == 1 else null,
        // Int
        sidePadding = if (parcel.readByte().toInt() != 0) parcel.readInt() else null,
        // Boolean
        enableTranslator = if (parcel.readByte().toInt() != 0) parcel.readByte()
            .toInt() == 1 else null,
        // String
        translateFrom = parcel.readString(),
        // String
        translateTo = parcel.readString(),
        // Boolean
        doubleClickTranslation = if (parcel.readByte().toInt() != 0) parcel.readByte()
            .toInt() == 1 else null,
        translatorLanguageHistory = parcel.readString()?.let {
            translatorHistoryMoshi.fromJson(it)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        // Language
        parcel.writeString(language)
        // Theme
        parcel.writeString(theme?.name)
        // Dark Theme
        parcel.writeString(darkTheme?.name)
        // Pure Dark
        parcel.writeString(pureDark?.name)
        // Contrast Level
        parcel.writeString(themeContrast?.name)
        // Font Family
        parcel.writeString(fontFamily)
        // Is Italic
        parcel.writeByte((if (isItalic != null) 1 else 0).toByte())
        isItalic?.let { parcel.writeByte(if (it) 1 else 0) }
        // Font Size
        parcel.writeByte((if (fontSize != null) 1 else 0).toByte())
        fontSize?.let { parcel.writeInt(it) }
        // Line Height
        parcel.writeByte((if (lineHeight != null) 1 else 0).toByte())
        lineHeight?.let { parcel.writeInt(it) }
        // Paragraph Height
        parcel.writeByte((if (paragraphHeight != null) 1 else 0).toByte())
        paragraphHeight?.let { parcel.writeInt(it) }
        // Paragraph Indentation
        parcel.writeByte((if (paragraphIndentation != null) 1 else 0).toByte())
        paragraphIndentation?.let { parcel.writeByte(if (it) 1 else 0) }
        // Background color
        parcel.writeByte((if (backgroundColor != null) 1 else 0).toByte())
        backgroundColor?.let { parcel.writeLong(it) }
        // Font color
        parcel.writeByte((if (fontColor != null) 1 else 0).toByte())
        fontColor?.let { parcel.writeLong(it) }
        // Show Start Screen
        parcel.writeByte((if (showStartScreen != null) 1 else 0).toByte())
        showStartScreen?.let { parcel.writeByte(if (it) 1 else 0) }
        // Check For Updates
        parcel.writeByte((if (checkForUpdates != null) 1 else 0).toByte())
        checkForUpdates?.let { parcel.writeByte(if (it) 1 else 0) }
        // Side Padding
        parcel.writeByte((if (sidePadding != null) 1 else 0).toByte())
        sidePadding?.let { parcel.writeInt(it) }
        // Enable Translator
        parcel.writeByte((if (enableTranslator != null) 1 else 0).toByte())
        enableTranslator?.let { parcel.writeByte(if (it) 1 else 0) }
        // Translate From
        parcel.writeString(translateFrom)
        // Translate To
        parcel.writeString(translateTo)
        // Double Click Translation
        parcel.writeByte((if (doubleClickTranslation != null) 1 else 0).toByte())
        doubleClickTranslation?.let { parcel.writeByte(if (it) 1 else 0) }
        // Translator Language History
        parcel.writeString(translatorHistoryMoshi.toJson(translatorLanguageHistory))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainState> {
        override fun createFromParcel(parcel: Parcel): MainState {
            return MainState(parcel)
        }

        override fun newArray(size: Int): Array<MainState?> {
            return arrayOfNulls(size)
        }

        /**
         * Initializes [MainState] by given [Map].
         */
        fun initialize(data: Map<String, Any>): MainState {
            DataStoreConstants.apply {
                val language: String = data[LANGUAGE.name] as? String ?: if (
                    Constants.LANGUAGES.any { Locale.getDefault().language.take(2) == it.first }
                ) {
                    Locale.getDefault().language.take(2)
                } else {
                    "en"
                }

                val theme: String = data[THEME.name] as? String ?: if (
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                ) Theme.DYNAMIC.name else Theme.BLUE.name

                val darkTheme: String = data[DARK_THEME.name] as? String
                    ?: DarkTheme.FOLLOW_SYSTEM.name

                val pureDark: String = data[PURE_DARK.name] as? String
                    ?: PureDark.OFF.name

                val themeContrast: String = data[THEME_CONTRAST.name] as? String
                    ?: ThemeContrast.STANDARD.name

                val showStartScreen: Boolean = data[SHOW_START_SCREEN.name] as? Boolean
                    ?: true

                val backgroundColor: Long = data[BACKGROUND_COLOR.name] as? Long
                    ?: Color.DarkGray.value.toLong()

                val fontColor: Long = data[FONT_COLOR.name] as? Long
                    ?: Color.LightGray.value.toLong()

                val fontFamily: String = data[FONT.name] as? String
                    ?: Constants.FONTS[0].id

                val isItalic: Boolean = data[IS_ITALIC.name] as? Boolean
                    ?: false

                val fontSize: Int = data[FONT_SIZE.name] as? Int
                    ?: 16

                val lineHeight: Int = data[LINE_HEIGHT.name] as? Int
                    ?: 4

                val paragraphHeight: Int = data[PARAGRAPH_HEIGHT.name] as? Int
                    ?: 8

                val paragraphIndentation: Boolean = data[PARAGRAPH_INDENTATION.name] as? Boolean
                    ?: false

                val checkForUpdates: Boolean = data[CHECK_FOR_UPDATES.name] as? Boolean
                    ?: false

                val sidePadding: Int = data[SIDE_PADDING.name] as? Int
                    ?: 6

                val enableTranslator: Boolean = data[ENABLE_TRANSLATOR.name] as? Boolean
                    ?: false

                val translateFrom: String = data[TRANSLATE_FROM.name] as? String
                    ?: "auto"

                val translateTo: String = data[TRANSLATE_TO.name] as? String ?: if (
                    TranslateLanguage.getAllLanguages().any { it == language }
                ) {
                    language
                } else {
                    "en"
                }

                val doubleClickTranslation: Boolean =
                    data[DOUBLE_CLICK_TRANSLATION.name] as? Boolean
                        ?: true

                val translatorLanguageHistory: List<LanguageHistory> =
                    (data[TRANSLATOR_LANGUAGE_HISTORY.name] as? String)?.let {
                        translatorHistoryMoshi.fromJson(it)
                    } ?: emptyList()


                return MainState(
                    language = language,
                    theme = theme.toTheme(),
                    darkTheme = darkTheme.toDarkTheme(),
                    pureDark = pureDark.toPureDark(),
                    themeContrast = themeContrast.toThemeContrast(),
                    showStartScreen = showStartScreen,
                    backgroundColor = backgroundColor,
                    fontColor = fontColor,
                    fontFamily = fontFamily,
                    isItalic = isItalic,
                    fontSize = fontSize,
                    lineHeight = lineHeight,
                    paragraphHeight = paragraphHeight,
                    paragraphIndentation = paragraphIndentation,
                    checkForUpdates = checkForUpdates,
                    sidePadding = sidePadding,
                    enableTranslator = enableTranslator,
                    translateFrom = translateFrom,
                    translateTo = translateTo,
                    doubleClickTranslation = doubleClickTranslation,
                    translatorLanguageHistory = translatorLanguageHistory
                )
            }
        }
    }
}