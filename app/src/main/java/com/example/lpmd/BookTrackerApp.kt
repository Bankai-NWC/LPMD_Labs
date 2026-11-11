package com.example.lpmd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.lpmd.model.Book
import com.example.lpmd.navigation.AddBookRoute
import com.example.lpmd.navigation.BookDetailsRoute
import com.example.lpmd.navigation.BookListRoute
import com.example.lpmd.screen.AddBookScreen
import com.example.lpmd.screen.BookDetailsScreen
import com.example.lpmd.screen.BookListScreen
import com.example.lpmd.ui.theme.LPMDTheme

private val initialBooks = listOf(
    Book(
        id = 1,
        title = "Clean Code",
        author = "Robert C. Martin",
        description = "A handbook of agile software craftsmanship.",
        isRead = true
    ),
    Book(
        id = 2,
        title = "Kotlin in Action",
        author = "Dmitry Jemerov, Svetlana Isakova",
        description = null,
        isRead = false
    ),
    Book(
        id = 3,
        title = "Refactoring",
        author = "Martin Fowler",
        description = "Improving the design of existing code.",
        isRead = false
    )
)

private fun toggleBookRead(
    books: List<Book>,
    bookId: Int
): List<Book> {
    return books.map { book ->
        if (book.id == bookId) {
            book.copy(isRead = !book.isRead)
        } else {
            book
        }
    }
}

@Composable
fun BookTrackerApp() {
    var books by remember { mutableStateOf(initialBooks) }
    val backStack = rememberNavBackStack(BookListRoute)

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<BookListRoute> {
                BookListScreen(
                    books = books,
                    onBookClick = { bookId ->
                        backStack.add(BookDetailsRoute(bookId = bookId))
                    },
                    onAddBook = {
                        backStack.add(AddBookRoute)
                    }
                )
            }

            entry<AddBookRoute> {
                AddBookScreen(
                    onCancel = {
                        backStack.removeLastOrNull()
                    },
                    onAddBook = { title, author, description, isRead ->
                        val nextId = (books.maxOfOrNull { it.id } ?: 0) + 1
                        books = books + Book(
                            id = nextId,
                            title = title,
                            author = author,
                            description = description,
                            isRead = isRead
                        )
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<BookDetailsRoute> { route ->
                val book = books.find { it.id == route.bookId }
                BookDetailsScreen(
                    book = book,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onToggleRead = {
                        books = toggleBookRead(
                            books = books,
                            bookId = route.bookId
                        )
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BookTrackerPreview() {
    LPMDTheme {
        BookTrackerApp()
    }
}