package com.example.api_interfaces.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api_interfaces.R
import com.example.api_interfaces.app.navigation.AppScreen

/**
 * Archvio compuesto por varios componentes que se reutilizan a lo largo del programa
 */

@Composable
fun Toolbar(navControlador: NavController, modifier: Modifier, actualscreen: String = ""){
    Box(modifier = modifier.fillMaxWidth().background(colorResource(R.color.app))){
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                border = BorderStroke(1.dp,Color.White),
                shape = RectangleShape,
                modifier = Modifier.padding(3.dp).weight(1f),
                onClick = {if (actualscreen!= AppScreen.Portada.route) navControlador.navigate(AppScreen.Portada.route) },
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.Transparent
                )
            ) {
                Text(text = "Portada")
            }
            Button(
                border = BorderStroke(1.dp,Color.White),
                shape = RectangleShape,
                modifier = Modifier.padding(3.dp).weight(1.3f),
                onClick = {if (actualscreen!=AppScreen.MainScreen.route) navControlador.navigate(AppScreen.MainScreen.route)},
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.Transparent
                )
            ) {
                Text(text = "Menu principal")
            }
            Button(
                border = BorderStroke(1.dp,Color.White),
                shape = RectangleShape,
                modifier = Modifier.padding(3.dp).weight(1f),
                onClick = {if (actualscreen!=AppScreen.APIMenu.route) navControlador.navigate(AppScreen.APIMenu.route)},
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.Transparent
                )
            ) {
                Text(text = "Menu API")
            }
        }
    }
}

@Composable
fun Footer(modifier: Modifier){
    Row(modifier = modifier.fillMaxWidth().wrapContentHeight().background(colorResource(R.color.app))){
        Text(text = "About Us", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = Color.White)
        Text(text = "Support", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = Color.White)
        Text(text = "Terms", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = Color.White)
    }
}

@Composable
fun AddPlainText(text:String,modifier: Modifier =Modifier){
    TextField(
        modifier = modifier.fillMaxWidth().wrapContentHeight().padding(0.dp,10.dp,0.dp,10.dp),
        value = text,
        readOnly = true,
        enabled = false,
        onValueChange = {},
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.White,
            disabledContainerColor = Color.Black
        )
    )
}

@Composable
fun AddButton(text:String,modifier: Modifier=Modifier,enabled: Boolean=true,onclick: () -> Unit = {}){
    Button(
        border = BorderStroke(1.dp, colorResource(R.color.app)),
        modifier = modifier.padding(10.dp),
        enabled = enabled,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Transparent
        ),
        onClick = onclick
    )
    {
        Text(text = text)
    }
}

@Composable
fun AddTextField(
    placeHolder: String,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.testTag("TextField"),
    colors: TextFieldColors = TextFieldDefaults.colors(),
    transformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true
) {
    TextField(
        modifier = modifier,
        value = text,
        placeholder = { Text(placeHolder) },
        onValueChange = onValueChange,
        colors = colors,
        enabled = enabled,
        visualTransformation = transformation
    )
}

@Composable
fun AddAlertDialog(title: String, text: String,testTag:String = "AlertDialog", dismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.testTag(testTag),
        onDismissRequest = dismiss,
        title = { Text(title, modifier = Modifier.testTag("AlertTitle")) },
        text = { Text(text, modifier = Modifier.testTag("AlertText")) },
        confirmButton = {
            Button(onClick = dismiss, modifier = Modifier.testTag(testTag+"B")) {
                Text("OK")
            }
        }
    )
}