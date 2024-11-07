package com.example.enishop.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormScreen(
    name: MutableState<String>,
    email: MutableState<String>,
    isFormSubmitted: MutableState<Boolean>,
    onSubmit: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Formulaire d'inscription", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Button(
            onClick = {
                isFormSubmitted.value = true
                onSubmit()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Soumettre")
        }

        if (isFormSubmitted.value) {
            Text(
                text = "Formulaire soumis avec succès!",
                color = Color.Green,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}