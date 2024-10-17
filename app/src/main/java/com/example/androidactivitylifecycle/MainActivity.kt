package com.example.androidactivitylifecycle

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidactivitylifecycle.ui.theme.AndroidActivityLifecycleTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "NotesAppLifecycle"
        private const val SAVED_NOTE_KEY = "savedNoteKey"
    }

    private var savedNoteState: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        savedNoteState = savedInstanceState?.getString(SAVED_NOTE_KEY)

        Toast.makeText(this, "onCreate() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onCreate()")

        setContent {
            AndroidActivityLifecycleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NotesApp(
                        modifier = Modifier.padding(innerPadding),
                        restoredNote = savedNoteState
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "onPause() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "onStop() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "onRestart() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onDestroy()")
    }

    // New lifecycle methods for saving and restoring instance state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_NOTE_KEY, savedNoteState)
        Toast.makeText(this, "onSaveInstanceState() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedNoteState = savedInstanceState.getString(SAVED_NOTE_KEY)
        Toast.makeText(this, "onRestoreInstanceState() called", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onRestoreInstanceState()")
    }
}

@Composable
fun NotesApp(modifier: Modifier = Modifier, restoredNote: String?) {
    var noteText by remember { mutableStateOf(restoredNote ?: "") }
    var savedNote by remember { mutableStateOf(restoredNote ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Text input field for note
        BasicTextField(
            value = noteText,
            onValueChange = { noteText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(8.dp)
                .border(1.dp, Color.Gray),  // Fix for border modifier
            decorationBox = { innerTextField ->
                if (noteText.isEmpty()) {
                    Text(text = "Enter your note here", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                innerTextField()
            }
        )

        // Button to save the note
        Button(
            onClick = {
                savedNote = noteText
                noteText = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save Note")
        }

        // Display saved note
        Text(text = "Saved Note: $savedNote", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun NotesAppPreview() {
    AndroidActivityLifecycleTheme {
        NotesApp(restoredNote = "")
    }
}
