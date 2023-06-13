package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeTheme

@Composable
fun TraverseeErrorState(
    modifier: Modifier = Modifier.fillMaxSize(),
    image: Painter,
    title: String,
    description: String? = null,
    isCanRetry: Boolean = false,
    onRetry: () -> Unit = { }
) {
    Box(modifier = modifier){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .width(250.dp)
                    .height(200.dp)
                    .padding(horizontal = 32.dp),
                painter = image,
                contentDescription = title,
                alignment = Alignment.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            if(description != null){
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
            }
            if(isCanRetry){
                Spacer(modifier = Modifier.height(16.dp))
                TraverseeButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    text = stringResource(id = R.string.retry),
                    onClick = onRetry
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TraverseeErrorStatePreview() {
    TraverseeTheme {
        TraverseeErrorState(
            image = painterResource(id = R.drawable.empty_list),
            title = "Title",
            description = "Description",
            isCanRetry = true,
        )
    }
}