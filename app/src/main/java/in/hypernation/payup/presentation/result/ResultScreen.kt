package `in`.hypernation.payup.presentation.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import `in`.hypernation.payup.R
import `in`.hypernation.payup.ui.theme.DMSansFamily
import `in`.hypernation.payup.ui.theme.GhostBlack
import `in`.hypernation.payup.ui.theme.GhostBlack80
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResultScreen(
    viewModel: ResultViewModel = koinViewModel()
){
    val status = viewModel.status
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(status == "success"){
                Image(
                    painter = painterResource(id = R.drawable.success),
                    contentDescription = "Success Image"
                )
            }

            Spacer(Modifier.height(5.dp))

            Text(
                if(status == "success")"Payment Successful" else "Payment Failed",
                fontSize = 36.sp,
                fontFamily = DMSansFamily,
                fontWeight = FontWeight.Normal,
                color = GhostBlack
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ResultPreview(){
    ResultScreen()
}