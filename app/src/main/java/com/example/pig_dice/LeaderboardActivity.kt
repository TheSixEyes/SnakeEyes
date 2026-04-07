package com.example.pig_dice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pig_dice.ui.theme.PIGDiceTheme
import java.io.File

/**
 * Data class to represent a leaderboard entry
 */
data class LeaderboardEntry(
    val winner: String,      // "You" or "Computer"
    val score: Int,
    val date: String,
    val timestamp: Long      // For sorting
)

class LeaderboardActivity : ComponentActivity() {
    companion object {
        const val EXTRA_WINNER = "extra_winner"
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_DATE = "extra_date"
        private const val LEADERBOARD_FILE = "leaderboard.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if there's new game data to save
        val winner = intent.getStringExtra(EXTRA_WINNER)
        val score = intent.getIntExtra(EXTRA_SCORE, -1)
        val date = intent.getStringExtra(EXTRA_DATE)

        if (winner != null && score != -1 && date != null) {
            saveGameResult(winner, score, date)
        }

        enableEdgeToEdge()
        setContent {
            PIGDiceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LeaderboardScreen(
                        onNavigateToGame = {
                            val intent = Intent(this@LeaderboardActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }

    /**
     * Save game result to file
     */
    private fun saveGameResult(winner: String, score: Int, date: String) {
        try {
            val file = File(filesDir, LEADERBOARD_FILE)
            val timestamp = System.currentTimeMillis()
            val entry = "$winner|$score|$date|$timestamp\n"
            file.appendText(entry)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Load all game results from file
     */
    private fun loadGameResults(): List<LeaderboardEntry> {
        return try {
            val file = File(filesDir, LEADERBOARD_FILE)
            if (!file.exists()) {
                return emptyList()
            }

            file.readLines()
                .filter { it.isNotBlank() }
                .mapNotNull { line ->
                    val parts = line.split("|")
                    if (parts.size == 4) {
                        LeaderboardEntry(
                            winner = parts[0],
                            score = parts[1].toIntOrNull() ?: 0,
                            date = parts[2],
                            timestamp = parts[3].toLongOrNull() ?: 0L
                        )
                    } else null
                }
                .sortedByDescending { it.timestamp } // Newest first
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @Composable
    fun LeaderboardScreen(onNavigateToGame: () -> Unit) {
        val entries = remember { loadGameResults() }

        // BoxWithConstraints for orientation detection
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isLandscape = this.maxWidth > this.maxHeight
            val widthKey = this.maxWidth.value.toInt()
            val heightKey = this.maxHeight.value.toInt()

            key(widthKey, heightKey) {
                if (isLandscape) {
                    LeaderboardScreenLandscape(entries, onNavigateToGame)
                } else {
                    LeaderboardScreenPortrait(entries, onNavigateToGame)
                }
            }
        }
    }

    @Composable
    fun LeaderboardScreenPortrait(
        entries: List<LeaderboardEntry>,
        onNavigateToGame: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = stringResource(R.string.leaderboard_title),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9E9E9E),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Leaderboard list
            if (entries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_games_yet),
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(entries) { entry ->
                        LeaderboardEntryCard(entry)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Play Game button
            Button(
                onClick = onNavigateToGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text(
                    text = stringResource(R.string.play_game_button),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    @Composable
    fun LeaderboardScreenLandscape(
        entries: List<LeaderboardEntry>,
        onNavigateToGame: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = stringResource(R.string.leaderboard_title),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9E9E9E),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // Leaderboard list
            if (entries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_games_yet),
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(entries) { entry ->
                        LeaderboardEntryCard(entry)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Play Game button
            Button(
                onClick = onNavigateToGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text(
                    text = stringResource(R.string.play_game_button),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    @Composable
    fun LeaderboardEntryCard(entry: LeaderboardEntry) {
        val isPlayer = entry.winner.equals("You", ignoreCase = true) || 
                       entry.winner.equals("Tú", ignoreCase = true)
        val backgroundColor = if (isPlayer) Color(0xFF4CAF50) else Color(0xFFF44336)
        val textColor = Color.White

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Winner name
                Text(
                    text = entry.winner,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )

                // Colon and Score in a Row for perfect alignment
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = ":",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${entry.score}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }

                // Date
                Text(
                    text = entry.date,
                    fontSize = 16.sp,
                    color = textColor,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
