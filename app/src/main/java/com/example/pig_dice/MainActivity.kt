package com.example.pig_dice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pig_dice.ui.theme.PIGDiceTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Data class to hold all game state for saving/restoring
 */
data class GameState(
    val playerTotalScore: Int = 0,
    val computerTotalScore: Int = 0,
    val playerTurnScore: Int = 0,
    val computerTurnScore: Int = 0,
    val playerGamesWon: Int = 0,
    val computerGamesWon: Int = 0,
    val isPlayerTurn: Boolean = true,
    val dice1Value: Int = 1,
    val dice2Value: Int = 1,
    val gameOver: Boolean = false,
    val playerWon: Boolean = false,
    val isFirstRollOfTurn: Boolean = true,
    val winningScore: Int = 100
)

class MainActivity : ComponentActivity() {
    private var savedGameState by mutableStateOf<GameState?>(null)

    companion object {
        private const val KEY_PLAYER_TOTAL = "player_total_score"
        private const val KEY_COMPUTER_TOTAL = "computer_total_score"
        private const val KEY_PLAYER_TURN = "player_turn_score"
        private const val KEY_COMPUTER_TURN = "computer_turn_score"
        private const val KEY_PLAYER_GAMES = "player_games_won"
        private const val KEY_COMPUTER_GAMES = "computer_games_won"
        private const val KEY_IS_PLAYER_TURN = "is_player_turn"
        private const val KEY_DICE1 = "dice1_value"
        private const val KEY_DICE2 = "dice2_value"
        private const val KEY_GAME_OVER = "game_over"
        private const val KEY_PLAYER_WON = "player_won"
        private const val KEY_FIRST_ROLL = "is_first_roll_of_turn"
        private const val KEY_WINNING_SCORE = "winning_score"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Restore game state from savedInstanceState if available
        // This happens BEFORE setContent, ensuring proper state restoration on rotation
        savedGameState = savedInstanceState?.let {
            GameState(
                playerTotalScore = it.getInt(KEY_PLAYER_TOTAL, 0),
                computerTotalScore = it.getInt(KEY_COMPUTER_TOTAL, 0),
                playerTurnScore = it.getInt(KEY_PLAYER_TURN, 0),
                computerTurnScore = it.getInt(KEY_COMPUTER_TURN, 0),
                playerGamesWon = it.getInt(KEY_PLAYER_GAMES, 0),
                computerGamesWon = it.getInt(KEY_COMPUTER_GAMES, 0),
                isPlayerTurn = it.getBoolean(KEY_IS_PLAYER_TURN, true),
                dice1Value = it.getInt(KEY_DICE1, 1),
                dice2Value = it.getInt(KEY_DICE2, 1),
                gameOver = it.getBoolean(KEY_GAME_OVER, false),
                playerWon = it.getBoolean(KEY_PLAYER_WON, false),
                isFirstRollOfTurn = it.getBoolean(KEY_FIRST_ROLL, true),
                winningScore = it.getInt(KEY_WINNING_SCORE, 100)
            )
        }
        
        enableEdgeToEdge()
        setContent {
            PIGDiceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PigDiceGame(initialState = savedGameState)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Get current game state and save it
        savedGameState?.let { state ->
            outState.putInt(KEY_PLAYER_TOTAL, state.playerTotalScore)
            outState.putInt(KEY_COMPUTER_TOTAL, state.computerTotalScore)
            outState.putInt(KEY_PLAYER_TURN, state.playerTurnScore)
            outState.putInt(KEY_COMPUTER_TURN, state.computerTurnScore)
            outState.putInt(KEY_PLAYER_GAMES, state.playerGamesWon)
            outState.putInt(KEY_COMPUTER_GAMES, state.computerGamesWon)
            outState.putBoolean(KEY_IS_PLAYER_TURN, state.isPlayerTurn)
            outState.putInt(KEY_DICE1, state.dice1Value)
            outState.putInt(KEY_DICE2, state.dice2Value)
            outState.putBoolean(KEY_GAME_OVER, state.gameOver)
            outState.putBoolean(KEY_PLAYER_WON, state.playerWon)
            outState.putBoolean(KEY_FIRST_ROLL, state.isFirstRollOfTurn)
            outState.putInt(KEY_WINNING_SCORE, state.winningScore)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore game state from bundle
        savedGameState = GameState(
            playerTotalScore = savedInstanceState.getInt(KEY_PLAYER_TOTAL, 0),
            computerTotalScore = savedInstanceState.getInt(KEY_COMPUTER_TOTAL, 0),
            playerTurnScore = savedInstanceState.getInt(KEY_PLAYER_TURN, 0),
            computerTurnScore = savedInstanceState.getInt(KEY_COMPUTER_TURN, 0),
            playerGamesWon = savedInstanceState.getInt(KEY_PLAYER_GAMES, 0),
            computerGamesWon = savedInstanceState.getInt(KEY_COMPUTER_GAMES, 0),
            isPlayerTurn = savedInstanceState.getBoolean(KEY_IS_PLAYER_TURN, true),
            dice1Value = savedInstanceState.getInt(KEY_DICE1, 1),
            dice2Value = savedInstanceState.getInt(KEY_DICE2, 1),
            gameOver = savedInstanceState.getBoolean(KEY_GAME_OVER, false),
            playerWon = savedInstanceState.getBoolean(KEY_PLAYER_WON, false),
            isFirstRollOfTurn = savedInstanceState.getBoolean(KEY_FIRST_ROLL, true),
            winningScore = savedInstanceState.getInt(KEY_WINNING_SCORE, 100)
        )
    }

    /**
     * Update the saved game state when game state changes
     */
    fun updateGameState(state: GameState) {
        savedGameState = state
    }
}

/**
 * Main composable for the PIG Dice Game
 * Manages the game state and displays the UI
 */
@Composable
fun PigDiceGame(initialState: GameState? = null) {
    val context = LocalContext.current
    // Update MainActivity's savedGameState when game state changes
    val activity = context as? MainActivity
    
    GameContent(
        initialState = initialState,
        onShowAbout = {
            // Navigate to AboutActivity
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        },
        onShowLeaderboard = {
            // Navigate to LeaderboardActivity
            val intent = Intent(context, LeaderboardActivity::class.java)
            context.startActivity(intent)
        },
        onStateChanged = { state ->
            activity?.updateGameState(state)
        }
    )
}

/**
 * Game content composable separated for navigation
 */
@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
@Composable
fun GameContent(
    initialState: GameState? = null,
    onShowAbout: () -> Unit,
    onShowLeaderboard: () -> Unit,
    onStateChanged: (GameState) -> Unit = {}
) {
    // Game state variables - initialize from saved state if available
    var playerTotalScore by remember { mutableStateOf(initialState?.playerTotalScore ?: 0) }
    var computerTotalScore by remember { mutableStateOf(initialState?.computerTotalScore ?: 0) }
    var playerTurnScore by remember { mutableStateOf(initialState?.playerTurnScore ?: 0) }
    var computerTurnScore by remember { mutableStateOf(initialState?.computerTurnScore ?: 0) }
    var playerGamesWon by remember { mutableStateOf(initialState?.playerGamesWon ?: 0) }
    var computerGamesWon by remember { mutableStateOf(initialState?.computerGamesWon ?: 0) }
    var isPlayerTurn by remember { mutableStateOf(initialState?.isPlayerTurn ?: true) }
    var dice1Value by remember { mutableStateOf(initialState?.dice1Value ?: 1) }
    var dice2Value by remember { mutableStateOf(initialState?.dice2Value ?: 1) }
    var gameOver by remember { mutableStateOf(initialState?.gameOver ?: false) }
    var playerWon by remember { mutableStateOf(initialState?.playerWon ?: false) }
    var isRolling by remember { mutableStateOf(false) }
    var isAnimating by remember { mutableStateOf(false) } // Track animation state for dice
    var isFirstRollOfTurn by remember { mutableStateOf(initialState?.isFirstRollOfTurn ?: true) } // Track if this is the first roll of the turn
    var winningScore by remember { mutableStateOf(initialState?.winningScore ?: 100) } // Configurable winning score
    var showSettings by remember { mutableStateOf(false) } // Show settings dialog
    
    // Function to update saved game state
    fun updateState() {
        onStateChanged(
            GameState(
                playerTotalScore = playerTotalScore,
                computerTotalScore = computerTotalScore,
                playerTurnScore = playerTurnScore,
                computerTurnScore = computerTurnScore,
                playerGamesWon = playerGamesWon,
                computerGamesWon = computerGamesWon,
                isPlayerTurn = isPlayerTurn,
                dice1Value = dice1Value,
                dice2Value = dice2Value,
                gameOver = gameOver,
                playerWon = playerWon,
                isFirstRollOfTurn = isFirstRollOfTurn,
                winningScore = winningScore
            )
        )
    }

    val coroutineScope = rememberCoroutineScope()
    
    // Automatically update saved state whenever any game state changes
    LaunchedEffect(
        playerTotalScore, computerTotalScore, playerTurnScore, computerTurnScore,
        playerGamesWon, computerGamesWon, isPlayerTurn, dice1Value, dice2Value,
        gameOver, playerWon, isFirstRollOfTurn, winningScore
    ) {
        updateState()
    }

    /**
     * Function to roll both dice
     * Returns a Pair of Int representing the two dice values
     * Prevents rolling 1s on the first roll of a turn for strategic gameplay
     */
    fun rollDice(isFirstRoll: Boolean = false): Pair<Int, Int> {
        var roll1: Int
        var roll2: Int

        if (isFirstRoll) {
            // On first roll of turn, ensure neither die shows a 1
            // This prevents immediate turn loss at the start of a turn
            do {
                roll1 = Random.nextInt(1, 7)
                roll2 = Random.nextInt(1, 7)
            } while (roll1 == 1 || roll2 == 1)
        } else {
            // Normal roll - any value is allowed
            roll1 = Random.nextInt(1, 7)
            roll2 = Random.nextInt(1, 7)
        }

        dice1Value = roll1
        dice2Value = roll2
        return Pair(roll1, roll2)
    }

    /**
     * Function to handle the player's roll action
     * Implements the game rules for rolling dice
     */
    fun playerRoll() {
        if (isRolling || !isPlayerTurn || gameOver) return

        isRolling = true
        isAnimating = true

        // Animate dice rolling
        coroutineScope.launch {
            // Quick dice changes for animation effect
            repeat(8) {
                dice1Value = Random.nextInt(1, 7)
                dice2Value = Random.nextInt(1, 7)
                delay(80)
            }

            // Final roll - pass isFirstRollOfTurn to prevent 1s on first roll
            val (roll1, roll2) = rollDice(isFirstRollOfTurn)
            isAnimating = false

            // After first roll, mark that subsequent rolls can have 1s
            if (isFirstRollOfTurn) {
                isFirstRollOfTurn = false
            }

            when {
                // Both dice show 1 - player loses total score and turn
                roll1 == 1 && roll2 == 1 -> {
                    playerTotalScore = 0
                    playerTurnScore = 0
                    isPlayerTurn = false
                    isFirstRollOfTurn = true // Reset for next turn
                    // Computer takes turn after a delay
                    delay(1500)
                    computerTakeTurn(
                        currentTotalScore = computerTotalScore,
                        onRoll = { r1, r2 ->
                            dice1Value = r1
                            dice2Value = r2
                        },
                        onTurnEnd = { score ->
                            computerTurnScore = 0
                            when {
                                score == -1 -> {
                                    // Computer rolled two 1s - reset total score
                                    computerTotalScore = 0
                                }
                                score > 0 -> {
                                    // Computer held - add to total score
                                    computerTotalScore += score
                                }
                                // score == 0 means computer lost turn score only (one 1)
                            }
                            // Check if computer won
                            if (computerTotalScore >= winningScore) {
                                gameOver = true
                                playerWon = false
                                computerGamesWon++
                            } else {
                                isPlayerTurn = true
                                isFirstRollOfTurn = true // Reset for player's next turn
                            }
                            isRolling = false
                        },
                        setTurnScore = { computerTurnScore = it },
                        winningScore = winningScore
                    )
                }
                // One die shows 1 - player loses turn score only
                roll1 == 1 || roll2 == 1 -> {
                    playerTurnScore = 0
                    isPlayerTurn = false
                    isFirstRollOfTurn = true // Reset for next turn
                    // Computer takes turn after a delay
                    delay(1500)
                    computerTakeTurn(
                        currentTotalScore = computerTotalScore,
                        onRoll = { r1, r2 ->
                            dice1Value = r1
                            dice2Value = r2
                        },
                        onTurnEnd = { score ->
                            computerTurnScore = 0
                            when {
                                score == -1 -> {
                                    // Computer rolled two 1s - reset total score
                                    computerTotalScore = 0
                                }
                                score > 0 -> {
                                    // Computer held - add to total score
                                    computerTotalScore += score
                                }
                                // score == 0 means computer lost turn score only (one 1)
                            }
                            // Check if computer won
                            if (computerTotalScore >= winningScore) {
                                gameOver = true
                                playerWon = false
                                computerGamesWon++
                            } else {
                                isPlayerTurn = true
                                isFirstRollOfTurn = true // Reset for player's next turn
                            }
                            isRolling = false
                        },
                        setTurnScore = { computerTurnScore = it },
                        winningScore = winningScore
                    )
                }
                // No 1s - add to turn score
                else -> {
                    playerTurnScore += roll1 + roll2
                    isRolling = false
                }
            }
        }
    }

    /**
     * Function to handle the player's hold action
     * Adds turn score to total score and switches to computer turn
     */
    fun playerHold() {
        if (isRolling || !isPlayerTurn || gameOver) return

        isRolling = true
        playerTotalScore += playerTurnScore
        playerTurnScore = 0
        isFirstRollOfTurn = true // Reset for next turn

        // Check if player won
        if (playerTotalScore >= winningScore) {
            gameOver = true
            playerWon = true
            playerGamesWon++
            isRolling = false
        } else {
            isPlayerTurn = false
            // Computer takes turn after a delay
            coroutineScope.launch {
                delay(1500)
                computerTakeTurn(
                    currentTotalScore = computerTotalScore,
                    onRoll = { r1, r2 ->
                        dice1Value = r1
                        dice2Value = r2
                    },
                    onTurnEnd = { score ->
                        computerTurnScore = 0
                        when {
                            score == -1 -> {
                                // Computer rolled two 1s - reset total score
                                computerTotalScore = 0
                            }
                            score > 0 -> {
                                // Computer held - add to total score
                                computerTotalScore += score
                            }
                            // score == 0 means computer lost turn score only (one 1)
                        }
                        // Check if computer won
                        if (computerTotalScore >= winningScore) {
                            gameOver = true
                            playerWon = false
                            computerGamesWon++
                        } else {
                            isPlayerTurn = true
                            isFirstRollOfTurn = true // Reset for player's next turn
                        }
                        isRolling = false
                    },
                    setTurnScore = { computerTurnScore = it },
                    winningScore = winningScore
                )
            }
        }
    }

    /**
     * Function to start a new game
     * Resets all scores but keeps games won count
     */
    fun startNewGame() {
        playerTotalScore = 0
        computerTotalScore = 0
        playerTurnScore = 0
        computerTurnScore = 0
        isPlayerTurn = true
        dice1Value = 1
        dice2Value = 1
        gameOver = false
        playerWon = false
        isRolling = false
        isAnimating = false
        isFirstRollOfTurn = true // Reset for new game
    }

    // Display game UI
    if (gameOver) {
        // Show winner/loser screen
        GameOverScreen(
            playerWon = playerWon,
            score = if (playerWon) playerTotalScore else computerTotalScore,
            onNewGame = { startNewGame() },
            onShowLeaderboard = onShowLeaderboard
        )
    } else {
        // Show main game screen
        GameScreen(
            isPlayerTurn = isPlayerTurn,
            playerGamesWon = playerGamesWon,
            computerGamesWon = computerGamesWon,
            playerTotalScore = playerTotalScore,
            computerTotalScore = computerTotalScore,
            playerTurnScore = playerTurnScore,
            computerTurnScore = computerTurnScore,
            dice1Value = dice1Value,
            dice2Value = dice2Value,
            onRoll = { playerRoll() },
            onHold = { playerHold() },
            isRolling = isRolling,
            isAnimating = isAnimating,
            onShowAbout = onShowAbout,
            onShowLeaderboard = onShowLeaderboard,
            onShowSettings = { showSettings = true },
            winningScore = winningScore
        )
    }

    // Settings dialog
    if (showSettings) {
        SettingsDialog(
            currentWinningScore = winningScore,
            onDismiss = { showSettings = false },
            onSave = { newScore ->
                winningScore = newScore
                showSettings = false
            }
        )    }
}

/**
 * Computer AI function that takes up to 3 rolls during its turn
 * Follows the same game rules as the player
 * Prevents rolling 1s on the first roll of the turn
 * Checks if total score would reach winning score and holds immediately to win
 * onTurnEnd parameter: -1 means reset total score (both dice = 1), 0 means just lose turn, positive means add to total
 */
suspend fun computerTakeTurn(
    currentTotalScore: Int,
    onRoll: (Int, Int) -> Unit,
    onTurnEnd: (Int) -> Unit,
    setTurnScore: (Int) -> Unit,
    winningScore: Int = 100
) {
    var turnScore = 0
    setTurnScore(0)
    var rollCount = 0
    val maxRolls = 3

    // Computer rolls up to 3 times
    while (rollCount < maxRolls) {
        delay(1500) // Delay between rolls for visualization

        // Animate computer rolling
        repeat(6) {
            val animRoll1 = Random.nextInt(1, 7)
            val animRoll2 = Random.nextInt(1, 7)
            onRoll(animRoll1, animRoll2)
            delay(100)
        }

        var roll1: Int
        var roll2: Int

        // On first roll of turn, prevent rolling 1s (same protection as player)
        if (rollCount == 0) {
            do {
                roll1 = Random.nextInt(1, 7)
                roll2 = Random.nextInt(1, 7)
            } while (roll1 == 1 || roll2 == 1)
        } else {
            // Normal roll - any value is allowed
            roll1 = Random.nextInt(1, 7)
            roll2 = Random.nextInt(1, 7)
        }

        onRoll(roll1, roll2)
        rollCount++

        when {
            // Both dice show 1 - lose ALL TOTAL POINTS (pass -1 as signal)
            roll1 == 1 && roll2 == 1 -> {
                setTurnScore(0)
                delay(1500)
                onTurnEnd(-1) // Special flag: -1 means reset total score
                return
            }
            // One die shows 1 - lose turn score only
            roll1 == 1 || roll2 == 1 -> {
                setTurnScore(0)
                delay(1500)
                onTurnEnd(0) // 0 means just lose turn score
                return
            }
            // No 1s - add to turn score
            else -> {
                turnScore += roll1 + roll2
                setTurnScore(turnScore)

                // Check if computer would win by holding now
                if (currentTotalScore + turnScore >= winningScore) {
                    delay(1500)
                    onTurnEnd(turnScore) // Hold immediately to win the game
                    return
                }
            }
        }
    }

    // After 3 successful rolls, hold
    delay(1500)
    onTurnEnd(turnScore)
}

/**
 * Main game screen composable
 * Displays all game elements including scores, dice, and buttons
 * Automatically detects orientation and adjusts layout accordingly
 */
@Composable
fun GameScreen(
    isPlayerTurn: Boolean,
    playerGamesWon: Int,
    computerGamesWon: Int,
    playerTotalScore: Int,
    computerTotalScore: Int,
    playerTurnScore: Int,
    computerTurnScore: Int,
    dice1Value: Int,
    dice2Value: Int,
    onRoll: () -> Unit,
    onHold: () -> Unit,
    isRolling: Boolean,
    isAnimating: Boolean,
    onShowAbout: () -> Unit,
    onShowLeaderboard: () -> Unit,
    onShowSettings: () -> Unit,
    winningScore: Int
) {
    // BoxWithConstraints automatically recomposes when constraints change
    // This handles ALL rotations including 180-degree flips
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isLandscape = this.maxWidth > this.maxHeight
        val widthKey = this.maxWidth.value.toInt()
        val heightKey = this.maxHeight.value.toInt()
        
        // Use unique key based on actual dimensions to force recreation
        key(widthKey, heightKey) {
            if (isLandscape) {
                GameScreenLandscape(
                    isPlayerTurn = isPlayerTurn,
                    playerGamesWon = playerGamesWon,
                    computerGamesWon = computerGamesWon,
                    playerTotalScore = playerTotalScore,
                    computerTotalScore = computerTotalScore,
                    playerTurnScore = playerTurnScore,
                    computerTurnScore = computerTurnScore,
                    dice1Value = dice1Value,
                    dice2Value = dice2Value,
                    onRoll = onRoll,
                    onHold = onHold,
                    isRolling = isRolling,
                    isAnimating = isAnimating,
                    onShowAbout = onShowAbout,
                    onShowLeaderboard = onShowLeaderboard,
                    onShowSettings = onShowSettings,
                    winningScore = winningScore
                )
            } else {
                GameScreenPortrait(
                    isPlayerTurn = isPlayerTurn,
                    playerGamesWon = playerGamesWon,
                    computerGamesWon = computerGamesWon,
                    playerTotalScore = playerTotalScore,
                    computerTotalScore = computerTotalScore,
                    playerTurnScore = playerTurnScore,
                    computerTurnScore = computerTurnScore,
                    dice1Value = dice1Value,
                    dice2Value = dice2Value,
                    onRoll = onRoll,
                    onHold = onHold,
                    isRolling = isRolling,
                    isAnimating = isAnimating,
                    onShowAbout = onShowAbout,
                    onShowLeaderboard = onShowLeaderboard,
                    onShowSettings = onShowSettings,
                    winningScore = winningScore
                )
            }
        }
    }
}

/**
 * Portrait layout for the game screen
 */
@Suppress("UNUSED_PARAMETER")
@Composable
fun GameScreenPortrait(
    isPlayerTurn: Boolean,
    playerGamesWon: Int,
    computerGamesWon: Int,
    playerTotalScore: Int,
    computerTotalScore: Int,
    playerTurnScore: Int,
    computerTurnScore: Int,
    dice1Value: Int,
    dice2Value: Int,
    onRoll: () -> Unit,
    onHold: () -> Unit,
    isRolling: Boolean,
    isAnimating: Boolean,
    onShowAbout: () -> Unit,
    onShowLeaderboard: () -> Unit,
    onShowSettings: () -> Unit,
    winningScore: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Game Title with About and Settings Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onShowSettings) {
                Text(
                    text = stringResource(R.string.settings_button),
                    fontSize = 14.sp,
                    color = Color(0xFF6200EE)
                )
            }
            Text(
                text = stringResource(R.string.game_title),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9E9E9E),
                textAlign = TextAlign.Center
            )
            TextButton(onClick = onShowAbout) {
                Text(
                    text = stringResource(R.string.about_button),
                    fontSize = 14.sp,
                    color = Color(0xFF6200EE)
                )
            }
        }

        // Current player indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Player indicator
            Text(
                text = stringResource(R.string.player_label),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (isPlayerTurn) Color.Black else Color.Gray,
                modifier = Modifier
                    .background(
                        if (isPlayerTurn) Color(0xFFFFEB3B) // Yellow highlight
                        else Color.Transparent
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )

            // Computer indicator
            Text(
                text = stringResource(R.string.computer_label),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (!isPlayerTurn) Color.Black else Color.Gray,
                modifier = Modifier
                    .background(
                        if (!isPlayerTurn) Color(0xFFFFEB3B) // Yellow highlight
                        else Color.Transparent
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Games Won Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$playerGamesWon", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.games_won_label), fontSize = 16.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$computerGamesWon", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Total Score Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$playerTotalScore", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.total_score_label), fontSize = 16.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$computerTotalScore", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF44336))
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Turn Score Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$playerTurnScore", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.turn_total_label), fontSize = 16.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$computerTurnScore", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Animation states for dice
        val infiniteTransition = rememberInfiniteTransition(label = "dice_animation")
        val rotation by infiniteTransition.animateFloat(
            initialValue = -15f,
            targetValue = 15f,
            animationSpec = infiniteRepeatable(
                animation = tween(150, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "rotation"
        )
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.95f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(150, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )

        // Dice Display
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Dice 1
            Image(
                painter = painterResource(id = getDiceImage(dice1Value)),
                contentDescription = stringResource(R.string.dice_showing, 1, dice1Value),
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer {
                        if (isAnimating) {
                            rotationZ = rotation
                            scaleX = scale
                            scaleY = scale
                        }
                    }
            )

            // Dice 2
            Image(
                painter = painterResource(id = getDiceImage(dice2Value)),
                contentDescription = stringResource(R.string.dice_showing, 2, dice2Value),
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer {
                        if (isAnimating) {
                            rotationZ = -rotation // Rotate opposite direction for variety
                            scaleX = scale
                            scaleY = scale
                        }
                    }
            )
        }

        // Dice Total
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "${dice1Value + dice2Value}",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
            Text(
                text = stringResource(R.string.dice_total_label),
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // ROLL Button
            Button(
                onClick = onRoll,
                enabled = isPlayerTurn && !isRolling,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE),
                    disabledContainerColor = Color.Gray
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = stringResource(R.string.roll_button), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            // HOLD Button
            Button(
                onClick = onHold,
                enabled = isPlayerTurn && !isRolling && playerTurnScore > 0,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE),
                    disabledContainerColor = Color.Gray
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = stringResource(R.string.hold_button), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Show Leaderboard Button
        Button(
            onClick = onShowLeaderboard,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF222739)
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.leaderboard_button),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Landscape layout for the game screen
 * Optimized for horizontal screen orientation
 */
@Suppress("UNUSED_PARAMETER")
@Composable
fun GameScreenLandscape(
    isPlayerTurn: Boolean,
    playerGamesWon: Int,
    computerGamesWon: Int,
    playerTotalScore: Int,
    computerTotalScore: Int,
    playerTurnScore: Int,
    computerTurnScore: Int,
    dice1Value: Int,
    dice2Value: Int,
    onRoll: () -> Unit,
    onHold: () -> Unit,
    isRolling: Boolean,
    isAnimating: Boolean,
    onShowAbout: () -> Unit,
    onShowLeaderboard: () -> Unit,
    onShowSettings: () -> Unit,
    winningScore: Int
) {
    // Animation states for dice
    val infiniteTransition = rememberInfiniteTransition(label = "dice_animation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left side - Player and Computer info
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Title with Settings and About buttons
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onShowSettings) {
                        Text(
                            text = stringResource(R.string.settings_button),
                            fontSize = 12.sp,
                            color = Color(0xFF6200EE)
                        )
                    }
                    Text(
                        text = stringResource(R.string.game_title),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9E9E9E),
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = onShowAbout) {
                        Text(
                            text = stringResource(R.string.about_button),
                            fontSize = 12.sp,
                            color = Color(0xFF6200EE)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Current player indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.player_label),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isPlayerTurn) Color.Black else Color.Gray,
                        modifier = Modifier
                            .background(
                                if (isPlayerTurn) Color(0xFFFFEB3B)
                                else Color.Transparent
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )

                    Text(
                        text = stringResource(R.string.computer_label),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (!isPlayerTurn) Color.Black else Color.Gray,
                        modifier = Modifier
                            .background(
                                if (!isPlayerTurn) Color(0xFFFFEB3B)
                                else Color.Transparent
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Games Won
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$playerGamesWon", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(text = stringResource(R.string.games_won_label), fontSize = 14.sp, color = Color.Gray)
                    Text(text = "$computerGamesWon", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Total Score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$playerTotalScore", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                    Text(text = stringResource(R.string.total_score_label), fontSize = 14.sp, color = Color.Gray)
                    Text(text = "$computerTotalScore", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF44336))
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Turn Score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$playerTurnScore", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(text = stringResource(R.string.turn_total_label), fontSize = 14.sp, color = Color.Gray)
                    Text(text = "$computerTurnScore", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Buttons at bottom
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onRoll,
                    enabled = isPlayerTurn && !isRolling,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE),
                        disabledContainerColor = Color.Gray
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = stringResource(R.string.roll_button), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onHold,
                    enabled = isPlayerTurn && !isRolling && playerTurnScore > 0,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE),
                        disabledContainerColor = Color.Gray
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = stringResource(R.string.hold_button), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Show Leaderboard Button
            Button(
                onClick = onShowLeaderboard,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF222739)
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = stringResource(R.string.leaderboard_button),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Right side - Dice display
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Dice Display
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = getDiceImage(dice1Value)),
                    contentDescription = stringResource(R.string.dice_showing, 1, dice1Value),
                    modifier = Modifier
                        .size(90.dp)
                        .graphicsLayer {
                            if (isAnimating) {
                                rotationZ = rotation
                                scaleX = scale
                                scaleY = scale
                            }
                        }
                )

                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    painter = painterResource(id = getDiceImage(dice2Value)),
                    contentDescription = stringResource(R.string.dice_showing, 2, dice2Value),
                    modifier = Modifier
                        .size(90.dp)
                        .graphicsLayer {
                            if (isAnimating) {
                                rotationZ = -rotation
                                scaleX = scale
                                scaleY = scale
                            }
                        }
                )
            }

            // Dice Total
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "${dice1Value + dice2Value}",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6200EE)
                )
                Text(
                    text = stringResource(R.string.dice_total_label),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

/**
 * Game over screen showing winner or loser message
 * Clickable to start a new game
 */
@Composable
fun GameOverScreen(
    playerWon: Boolean,
    score: Int,
    onNewGame: () -> Unit,
    onShowLeaderboard: () -> Unit
) {
    val context = LocalContext.current
    var dataSaved by remember { mutableStateOf(false) }

    // Save game data to leaderboard file once
    LaunchedEffect(Unit) {
        if (!dataSaved) {
            val winner = if (playerWon) {
                context.getString(R.string.player_label)
            } else {
                context.getString(R.string.computer_label)
            }
            val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val date = dateFormat.format(Date())

            // Save directly to file
            try {
                val file = java.io.File(context.filesDir, "leaderboard.txt")
                val timestamp = System.currentTimeMillis()
                val entry = "$winner|$score|$date|$timestamp\n"
                file.appendText(entry)
                dataSaved = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (playerWon) Color(0xFF4CAF50) else Color(0xFFF44336))
            .clickable { onNewGame() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Display winner or loser image
            Image(
                painter = painterResource(id = if (playerWon) R.drawable.winner else R.drawable.loser),
                contentDescription = stringResource(if (playerWon) R.string.winner_image else R.string.loser_image),
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .weight(1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Only show the second text line (winner/another)
            val secondText = if (playerWon) stringResource(R.string.winner) else stringResource(R.string.another)
            if (secondText.isNotBlank()) {
                Text(
                    text = secondText,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Leaderboard Button
            Button(
                onClick = onShowLeaderboard,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222739)),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.leaderboard_button),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.tap_to_play_again),
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * Settings dialog for configuring game parameters
 */
@Composable
fun SettingsDialog(
    currentWinningScore: Int,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    var tempScore by remember { mutableStateOf(currentWinningScore.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.settings_title),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.winning_score_label),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = tempScore,
                    onValueChange = { newValue ->
                        // Only allow digits
                        if (newValue.all { it.isDigit() } && newValue.length <= 4) {
                            tempScore = newValue
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val score = tempScore.toIntOrNull()
                    if (score != null && score > 0) {
                        onSave(score)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(stringResource(R.string.save_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(stringResource(R.string.cancel_button))
            }
        }
    )
}

/**
 * Helper function to get the correct dice image resource
 * based on the dice value (1-6)
 */
fun getDiceImage(value: Int): Int {
    return when (value) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_1
    }
}

