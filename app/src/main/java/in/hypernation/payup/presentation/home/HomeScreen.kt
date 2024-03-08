package `in`.hypernation.payup.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.hypernation.payup.R
import `in`.hypernation.payup.ui.theme.notoSansFamily


@Composable
fun HomeView(){
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
        Column (
            modifier = Modifier.fillMaxSize()
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.Top)

            ) {
                AvatarCard()
                Column (
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                ) {
                    Text(
                        text = "hi,",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal
                    )
                    Text(
                        text = "what a beautiful day!",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal
                    )

                }
                Image(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "setting",
                    modifier = Modifier.size(24.dp)
                        .weight(1f)
                )

            }
        }
        
    }
}


@Preview
@Composable
fun AvatarCard(){
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.size(100.dp)
    ) {
        ElevatedCard (
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomCenter)
        ) {

        }
        Image(
            painter = painterResource(id = R.drawable.male_avatar),
            contentDescription = "bg",
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 4.dp)
                .align(Alignment.Center)
        )


    }
}