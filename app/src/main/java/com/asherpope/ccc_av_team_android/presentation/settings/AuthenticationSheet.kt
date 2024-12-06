package com.asherpope.ccc_av_team_android.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AuthenticationSheet(
    onDismiss: () -> Unit,
    onAuthenticated: (Boolean) -> Unit
) {
    var passcodeText by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var firstAttempt by remember { mutableStateOf(true) }
    var errorText by remember { mutableStateOf("Wrong passcode, please try again.") }

    val errorMessages = listOf(
        "Wrong passcode, try again. Maybe you're a secret agent in disguise?",
        "Oops! That ain't it. Try again.",
        "Nice try, but the passcode fairy didn't bless you this time.",
        "Hmm, that's not the passcode. Are you sure you're not a robot?",
        "Incorrect passcode. If at first you don't succeed, try, try, try again!",
        "That's not it. Perhaps you should call a psychic for help.",
        "Close, but no cigar. Your passcode is still a mystery.",
        "Looks like you're trying to unlock a treasure chest with the wrong key.",
        "Nope, that's not the passcode. Have you tried shaking the phone to see if it helps?",
        "No good. Did you try turning it off and on again?",
        "The passcode you entered is incorrect. Have you tried guessing?",
        "Wrong code. Is this a secret test of your persistence?",
        "Passcode incorrect. Do you need a hint? Just kidding, keep trying.",
        "That's not the one. Are you testing the system or just making things interesting?",
        "Incorrect passcode. Did you remember to read the manual?",
        "Oops! Wrong passcode. Maybe it's time to consult chatGPT.",
        "Fail! The passcode doesn't match. Maybe try a more secure guess?",
        "Wrong passcode. It's almost like you're playing a game of 'guess the right code'."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Enter the passcode provided by the AV Team Manager.",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = passcodeText,
            onValueChange = { passcodeText = it },
            label = { Text("Passcode") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        if (showError) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    if (passcodeText == "i am legit") {
                        onAuthenticated(true)
                        onDismiss()
                    } else {
                        if (firstAttempt) {
                            showError = true
                            passcodeText = ""
                            firstAttempt = false
                        } else {
                            passcodeText = ""
                            errorText = errorMessages.random()
                        }
                    }
                }
            ) {
                Text("Enter")
            }
        }

        Spacer(modifier = Modifier.weight(2f))
    }
}
