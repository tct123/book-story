package ua.acclorite.book_story.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.acclorite.book_story.domain.model.Category

@Entity
data class BookEntity(
    @PrimaryKey(true) val id: Int = 0,
    val title: String,
    val author: String?,
    val description: String?,
    val textPath: String,
    val filePath: String,
    val scrollIndex: Int,
    val scrollOffset: Int,
    val progress: Float,
    val image: String? = null,
    val category: Category,
    @ColumnInfo(defaultValue = "false") val enableTranslator: Boolean,
    @ColumnInfo(defaultValue = "auto") val translateFrom: String,
    @ColumnInfo(defaultValue = "en") val translateTo: String,
    @ColumnInfo(defaultValue = "true") val doubleClickTranslation: Boolean,
    @ColumnInfo(defaultValue = "false") val translateWhenOpen: Boolean
)
