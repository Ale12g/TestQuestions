package com.example.testquestions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

data class Question(val text: String, val answer: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GeoQuizScreen()
                }
            }
        }
    }
}

@Composable
fun GeoQuizScreen() {

    val questionBank = listOf(
        Question("Canberra is the capital of Australia.", true),
        Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
        Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
        Question("The source of the Nile River is in Egypt.", false),
        Question("The Amazon River is the longest river in the Americas.", true),
        Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
    )

    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var isAnswered by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }

    val currentQuestion = questionBank[currentIndex]
    val isLastQuestion = currentIndex == questionBank.lastIndex

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = currentQuestion.text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Button(
                onClick = {
                    if (!isAnswered) {
                        if (currentQuestion.answer) score++
                        isAnswered = true
                    }
                },
                enabled = !isAnswered,
                modifier = Modifier.alpha(if (isAnswered) 0f else 1f)
            ) {
                Text("TRUE")
            }

            Button(
                onClick = {
                    if (!isAnswered) {
                        if (!currentQuestion.answer) score++
                        isAnswered = true
                    }
                },
                enabled = !isAnswered,
                modifier = Modifier.alpha(if (isAnswered) 0f else 1f)
            ) {
                Text("FALSE")
            }
        }


        if (!isLastQuestion) {
            Button(
                onClick = {
                    if (isAnswered) {
                        currentIndex++
                        isAnswered = false
                    }
                },
                enabled = isAnswered,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (isLastQuestion && isAnswered) 0f else 1f)
            ) {
                Text("NEXT")
            }
        } else {

            LaunchedEffect(isAnswered) {
                if (isAnswered) {
                    showResultDialog = true
                }
            }
        }
    }



}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GeoQuizScreenPreview() {
    MaterialTheme {
        GeoQuizScreen()
    }
}