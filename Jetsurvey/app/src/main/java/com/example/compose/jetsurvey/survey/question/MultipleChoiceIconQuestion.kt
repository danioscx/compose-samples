/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetsurvey.survey.question

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.Answer
import com.example.compose.jetsurvey.survey.PossibleAnswer
import com.example.compose.jetsurvey.theme.JetsurveyTheme

@Composable
fun MultipleChoiceIconQuestion(
    possibleAnswer: PossibleAnswer.MultipleChoiceIcon,
    answer: Answer.MultipleChoice?,
    onAnswerSelected: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = possibleAnswer.optionsStringIconRes.associateBy { stringResource(id = it.second) }
    Column(modifier = modifier) {
        for (option in options) {
            var checkedState by remember(answer) {
                val selectedOption = answer?.answersStringRes?.contains(option.value.second)
                mutableStateOf(selectedOption ?: false)
            }
            val answerBorderColor = if (checkedState) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            }
            val answerBackgroundColor = if (checkedState) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            } else {
                MaterialTheme.colorScheme.background
            }
            Surface(
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    width = 1.dp,
                    color = answerBorderColor
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                checkedState = !checkedState
                                onAnswerSelected(option.value.second, checkedState)
                            }
                        )
                        .background(answerBackgroundColor)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = option.value.first),
                        contentDescription = null,
                        modifier = Modifier
                            .width(56.dp)
                            .height(56.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                    Text(text = option.key)

                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = { selected ->
                            checkedState = selected
                            onAnswerSelected(option.value.second, selected)
                        },
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MultipleChoiceIconQuestionPreview() {
    JetsurveyTheme {
        Surface {
            MultipleChoiceIconQuestion(
                possibleAnswer = PossibleAnswer.MultipleChoiceIcon(
                    listOf(
                        Pair(R.drawable.spark, R.string.spark),
                        Pair(R.drawable.lenz, R.string.lenz),
                        Pair(R.drawable.bug_of_chaos, R.string.bugchaos),
                        Pair(R.drawable.frag, R.string.frag)
                    )
                ),
                answer = null,
                onAnswerSelected = { _, _ -> }
            )
        }
    }
}