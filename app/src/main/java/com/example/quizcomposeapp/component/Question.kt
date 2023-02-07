package com.example.quizcomposeapp.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizcomposeapp.model.QuestionItem
import com.example.quizcomposeapp.screens.QuestionViewModel
import com.example.quizcomposeapp.util.AppColors

@Composable
fun Questions(questionViewModel: QuestionViewModel) {
    val indexOfQuestion = remember {
        mutableStateOf(0)
    }

    val questions = questionViewModel.listOfQuestionsStateResult.value.data?.toMutableList()
    if (questionViewModel.listOfQuestionsStateResult.value.loading == true) {
        Box(modifier = Modifier.size(35.dp)) {
            CircularProgressIndicator(modifier = Modifier.align(Center))
        }
    } else {
        DisplayQuestion(
            question = questions.let { questions!![indexOfQuestion.value] },
            questions.let { questions!!.size },
            indexOfQuestion,
        ) {
            indexOfQuestion.value++
        }
    }
}

@Composable
fun DisplayQuestion(
    question: QuestionItem,
    numberOfQuestions: Int,
    questionIndex: MutableState<Int>,
    onNextQuestion: (questionIndex: Int) -> Unit
) {
    val answers = remember {
        question.choices.toMutableList()
    }

    val isCorrect = remember {
        mutableStateOf<Boolean?>(null)
    }

    val selectedAnswer = remember { mutableStateOf<Int?>(null) }
    val updateAnswer: (Int) -> Unit = remember {
        {
            selectedAnswer.value = it
            isCorrect.value = answers[it] == question.answer
        }
    }

    val pathEffect = PathEffect.dashPathEffect( // PathEffect - to draw dashed line
        intervals = floatArrayOf(10f, 10f),
        phase = 0f
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            ShowProgressBar(score = questionIndex.value.toFloat(), numberOfQuestions)
            QuestionTracker(questionIndex.value, numberOfQuestions)
            DrawDottedLine(pathEffect = pathEffect)
            Text(
                text = question.question,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .padding(top = 20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = AppColors.mOffWhite
            )
            answers.forEachIndexed { index, answer ->
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 2.dp, brush = Brush.horizontalGradient(
                                listOf(AppColors.mOffWhite, AppColors.mOffWhite)
                            ), shape = RoundedCornerShape(12.dp)
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedAnswer.value == index),
                        onClick = { updateAnswer(index) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = if (isCorrect.value == true) Color.Green else Color.Red,
                            unselectedColor = AppColors.mOffWhite,
                        )
                    )
                    val annotatedStringAnswer = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                if (isCorrect.value == true && index == selectedAnswer.value) Color.Green
                                else if (isCorrect.value == false && index == selectedAnswer.value) Color.Red
                                else Color.White
                            )
                        ) {
                            append(answer)
                        }
                    }
                    Text(text = annotatedStringAnswer)
                }
            }
            Button(
                onClick = {
                    selectedAnswer.value = null
                    onNextQuestion(questionIndex.value)
                          },
                modifier = Modifier
                    .padding(3.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.mBlue,
                    contentColor = AppColors.mOffWhite
                ),
                shape = RoundedCornerShape(34.dp)
            ) {
                Text("Next", fontSize = 25.sp)
            }
        }
    }
}

@Composable
fun ShowProgressBar(score: Float, numOfQuestions: Int) {
    val progress = remember {
        mutableStateOf(0f)
    }

    progress.value = (score / numOfQuestions)

    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .padding(top = 20.dp),
        color = AppColors.mLightGray,
        progress = progress.value
    )
}

@Composable
fun QuestionTracker(counter: Int, numOfQuestions: Int) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) { //ParagraphStyle - style for all paragraph
            withStyle(
                style = SpanStyle(color = AppColors.mLightGray, fontWeight = FontWeight.Bold, fontSize = 27.sp)
            ) { // SpanStyle - style for part of the string
                append("Question $counter/")
            }
            withStyle(
                style = SpanStyle(color = AppColors.mLightGray, fontWeight = FontWeight.Light, fontSize = 18.sp)
            ) {
                append("$numOfQuestions")
            }
        }
    }, modifier = Modifier.padding(26.dp))
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}