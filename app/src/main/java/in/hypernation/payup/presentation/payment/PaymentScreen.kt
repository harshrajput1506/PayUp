package `in`.hypernation.payup.presentation.payment

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.hypernation.payup.ui.theme.DMSansFamily
import `in`.hypernation.payup.ui.theme.GhostBlack
import `in`.hypernation.payup.ui.theme.GhostBlack20
import `in`.hypernation.payup.ui.theme.GhostBlack60
import `in`.hypernation.payup.ui.theme.GhostBlack80
import `in`.hypernation.payup.utils.RUPEE_SYMBOL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    backToHome :  () -> Unit
){
    var amount by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var remarks by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val context = LocalContext.current
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            PaymentAppBar{
                backToHome()
            }
            Column (
                Modifier.padding(20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "you're pay to",
                    fontSize = 32.sp,
                    fontFamily = DMSansFamily,
                    color = GhostBlack
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Harsh Rajput",
                    fontSize = 18.sp,
                    fontFamily = DMSansFamily,
                    fontWeight = FontWeight.Medium,
                    color = GhostBlack
                )

                Spacer(modifier = Modifier.height(50.dp))

                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = amount,
                    onValueChange = {
                        amount = it
                    },
                    textStyle = TextStyle(
                        fontSize = 64.sp,
                        fontFamily = DMSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = GhostBlack80
                    ),
                    singleLine = true,
                    enabled = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                ) {
                    TextFieldDefaults.DecorationBox(
                        value = amount.text,
                        innerTextField = it,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = remember { MutableInteractionSource() },
                        prefix = {
                            Text(
                                text = RUPEE_SYMBOL,
                                fontSize = 64.sp,
                                fontFamily = DMSansFamily,
                                fontWeight = FontWeight.Bold,
                                color = GhostBlack80
                            )
                        },
                        
                        placeholder = {
                            Text(
                                text = "00",
                                fontSize = 64.sp,
                                fontFamily = DMSansFamily,
                                fontWeight = FontWeight.Bold,
                                color = GhostBlack20,
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = GhostBlack,
                            unfocusedTextColor = GhostBlack80,
                            disabledTextColor = GhostBlack20,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = remarks,
                    onValueChange = {
                        remarks = it
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = DMSansFamily,
                        color = GhostBlack60
                    ),
                    singleLine = true,
                    enabled = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                ) {
                    TextFieldDefaults.DecorationBox(
                        value = remarks.text,
                        innerTextField = it,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = remember { MutableInteractionSource() },

                        placeholder = {
                            Text(
                                text = "Remarks",
                                fontSize = 16.sp,
                                fontFamily = DMSansFamily,
                                color = GhostBlack60,
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = GhostBlack,
                            unfocusedTextColor = GhostBlack80,
                            disabledTextColor = GhostBlack20,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    )
                }
            }

        }

        Button(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(30.dp)
                .fillMaxWidth(),
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "Pay",
                fontFamily = DMSansFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }

    }

    BackHandler {
        backToHome()
    }
}

@Composable
private fun PaymentAppBar(onBack : () -> Unit){
    Box (
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton  (
            onClick = { onBack() }
        ){
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back Btn",
                tint = GhostBlack,
                modifier = Modifier
                    .size(24.dp)

            )
        }

        Text(
            text = "Payment",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontFamily = DMSansFamily,
            fontWeight = FontWeight.Medium,
            color = GhostBlack,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
@Preview(showSystemUi = true)
fun PreviewPaymentScreen(){
    PaymentScreen { }
}
