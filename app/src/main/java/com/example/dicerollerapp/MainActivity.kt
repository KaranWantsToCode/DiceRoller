package com.example.dicerollerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dicerollerapp.ui.theme.DiceRollerAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerAppTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.background
                    )
                )
            )
    )
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }

    // Image resource mapping
    val diceImages = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    // Animation states
    val rotation by animateFloatAsState(
        targetValue = if (isRolling) 760f else 0f,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
    )
    val scale by animateFloatAsState(
        targetValue = if (isRolling) 1.2f else 1f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = diceImages[result - 1]),
                contentDescription = result.toString(),
                modifier = Modifier
                    .size(150.dp) // Increased size
                    .scale(scale)
                    .rotate(rotation)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!isRolling) {
                        isRolling = true
                        result = (1..6).random()
                        // Simulate rolling delay
                        kotlinx.coroutines.MainScope().launch {
                            kotlinx.coroutines.delay(500)
                            isRolling = false
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 200.dp, height = 60.dp) // Increased size
                    .shadow(6.dp, shape = RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = if (isRolling) "Rolling..." else stringResource(id = R.string.roll),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
