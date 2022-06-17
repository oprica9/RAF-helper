package rs.raf.projekat2.ognjen_prica_10620.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import rs.raf.projekat2.ognjen_prica_10620.data.models.LoginData

@Composable
fun LoginScreen(onClick: (loginData: LoginData) -> Unit) {

    val constraintSet = ConstraintSet {
        val buttonLogin = createRefFor("buttonLogin")
        val inputUsername = createRefFor("inputUsername")
        val inputPassword = createRefFor("inputPIN")
        constrain(inputUsername) {
            top.linkTo(parent.top, 256.dp)
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
        }
        constrain(inputPassword) {
            top.linkTo(inputUsername.bottom, 8.dp)
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
        }
        constrain(buttonLogin) {
            top.linkTo(inputPassword.bottom, 32.dp)
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
        }
    }

    val focusManager = LocalFocusManager.current

    ConstraintLayout(
        constraintSet, modifier = Modifier

            .fillMaxSize()
            .clickable { focusManager.clearFocus() }
    ) {

        val context = LocalContext.current
        var username by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = ("Username")) },
            modifier = Modifier.layoutId("inputUsername")
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("PIN") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .layoutId("inputPIN")

        )
        Button(
            onClick = { onClick(LoginData(username, password)) },
            Modifier
                .layoutId("buttonLogin")
                .width(280.dp)
                .height(50.dp)
        ) {
            Text(text = "Log in")
        }
    }
}

@Preview
@Composable
fun TestPracticePreview() {
    LoginScreen() {}
}