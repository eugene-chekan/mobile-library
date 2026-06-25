package com.course.app.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.course.app.data.ItemType
import com.course.app.data.LibraryItemEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
    item: LibraryItemEntity?,
    onSave: (LibraryItemEntity) -> Unit,
    onBack: () -> Unit
) {
    var selectedType by rememberSaveable { mutableStateOf(item?.type ?: ItemType.BOOK) }
    var title       by rememberSaveable { mutableStateOf(item?.title ?: "") }
    var isRead      by rememberSaveable { mutableStateOf(item?.isRead ?: false) }
    var titleError  by rememberSaveable { mutableStateOf(false) }

    // Book fields
    var author       by rememberSaveable { mutableStateOf(item?.author ?: "") }
    var isbn         by rememberSaveable { mutableStateOf(item?.isbn ?: "") }
    var yearPub      by rememberSaveable { mutableStateOf(item?.yearPublished?.toString() ?: "") }

    // Magazine fields
    var publisher    by rememberSaveable { mutableStateOf(item?.publisher ?: "") }
    var issueNumber  by rememberSaveable { mutableStateOf(item?.issueNumber?.toString() ?: "") }
    var issueYear    by rememberSaveable { mutableStateOf(item?.issueYear?.toString() ?: "") }

    fun save() {
        if (title.isBlank()) { titleError = true; return }
        onSave(
            LibraryItemEntity(
                id    = item?.id ?: 0,
                type  = selectedType,
                title = title.trim(),
                isRead = isRead,
                author       = if (selectedType == ItemType.BOOK) author.trim().ifEmpty { null } else null,
                isbn         = if (selectedType == ItemType.BOOK) isbn.trim().ifEmpty { null } else null,
                yearPublished= if (selectedType == ItemType.BOOK) yearPub.toIntOrNull() else null,
                publisher    = if (selectedType == ItemType.MAGAZINE) publisher.trim().ifEmpty { null } else null,
                issueNumber  = if (selectedType == ItemType.MAGAZINE) issueNumber.toIntOrNull() else null,
                issueYear    = if (selectedType == ItemType.MAGAZINE) issueYear.toIntOrNull() else null,
            )
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = { Text(if (item == null) "New Item" else "Edit Item") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { save() }) {
                        Text("Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Type", style = MaterialTheme.typography.labelLarge)

            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                ItemType.entries.forEachIndexed { index, type ->
                    SegmentedButton(
                        selected = selectedType == type,
                        onClick  = { selectedType = type },
                        shape    = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = ItemType.entries.size
                        ),
                        label = { Text(if (type == ItemType.BOOK) "Book" else "Magazine") }
                    )
                }
            }

            OutlinedTextField(
                value         = title,
                onValueChange = { title = it; titleError = false },
                label         = { Text("Title *") },
                isError       = titleError,
                supportingText = if (titleError) ({ Text("Title is required") }) else null,
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            if (selectedType == ItemType.BOOK) {
                OutlinedTextField(
                    value         = author,
                    onValueChange = { author = it },
                    label         = { Text("Author") },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true
                )
                OutlinedTextField(
                    value         = isbn,
                    onValueChange = { isbn = it },
                    label         = { Text("ISBN") },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value         = yearPub,
                    onValueChange = { yearPub = it },
                    label         = { Text("Year published") },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            } else {
                OutlinedTextField(
                    value         = publisher,
                    onValueChange = { publisher = it },
                    label         = { Text("Publisher") },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true
                )
                OutlinedTextField(
                    value         = issueNumber,
                    onValueChange = { issueNumber = it },
                    label         = { Text("Issue number") },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value         = issueYear,
                    onValueChange = { issueYear = it },
                    label         = { Text("Issue year") },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Mark as read", style = MaterialTheme.typography.bodyLarge)
                Switch(checked = isRead, onCheckedChange = { isRead = it })
            }
        }
    }
}
