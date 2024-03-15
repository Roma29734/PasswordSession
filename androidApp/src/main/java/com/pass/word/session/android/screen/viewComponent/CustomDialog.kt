package com.pass.word.session.android.screen.viewComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.R
import com.pass.word.session.ui.CustomColor

@Composable
fun CustomLoadingDialog() {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .height(70.dp),
                color = CustomColor().brandBlueLight
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Please wait",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Your data is being downloaded from the blockchain",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun CustomChoseTypeDialog(
    textTitle: String,
    textSubTitle: String,
    textButtonFirst: String,
    textButtonSecond: String,
    handlerFirstButton: () -> Unit,
    handlerSecondButton: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                colorFilter = ColorFilter.tint(CustomColor().brandGreen)
            )


            Text(
                text = textTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Text(
                text = textSubTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColor().grayLight
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .background(CustomColor().brandBlueLight),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {

                TextButton(onClick = {
                    handlerFirstButton()
                }) {
                    Text(
                        textButtonFirst,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(CustomColor().grayLight),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    handlerSecondButton()
                }) {
                    Text(
                        textButtonSecond,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun CustomErrorDialog(
    textTitle: String,
    textSubTitle: String,
    textButton: String,
    handlerButton: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                colorFilter = ColorFilter.tint(CustomColor().brandRedMain)
            )


            Text(
                text = textTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Text(
                text = textSubTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColor().grayLight
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .background(CustomColor().brandBlueLight),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {

                TextButton(onClick = {
                    handlerButton()
                }) {
                    Text(
                        textButton,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}