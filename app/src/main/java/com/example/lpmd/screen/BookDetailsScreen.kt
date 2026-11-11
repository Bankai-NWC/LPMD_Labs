package com.example.lpmd.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lpmd.R
import com.example.lpmd.model.Book

@Composable
fun BookDetailsScreen(
    book: Book?,
    onToggleRead: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (book == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(id = R.string.book_not_found))
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextButton(
            onClick = onBack
        ) {
            Text(stringResource(id = R.string.back))
        }

        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = book.author,
            style = MaterialTheme.typography.titleMedium
        )

        book.description?.let {
            Text(it)
        }

        Text(
            text = if (book.isRead) {
                stringResource(id = R.string.status_read)
            } else {
                stringResource(id = R.string.status_not_read)
            }
        )

        Button(
            onClick = onToggleRead
        ) {
            Text(
                if (book.isRead) {
                    stringResource(id = R.string.mark_unread)
                } else {
                    stringResource(id = R.string.mark_read)
                }
            )
        }
    }
}