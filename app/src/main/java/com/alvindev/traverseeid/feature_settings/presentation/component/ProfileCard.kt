package com.alvindev.traverseeid.feature_settings.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.domain.entity.UserPreference
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.Typography
import java.util.*

@Composable
fun ProfileCard(
    actionOnClick: () -> Unit = {},
    user: UserPreference
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.secondary,
                shape = Shapes.large
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(50)),
            model = user.avatarUrl,
            fallback = painterResource(id = R.drawable.ic_profile),
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { actionOnClick() },
                    text = stringResource(id = R.string.edit),
                    style = Typography.button,
                    color = Color.White,
                )
            }
            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = user.name ?: "",
                style = Typography.subtitle2,
                color = Color.White,
            )
            Text(
                text = user.email ?: "",
                style = Typography.caption,
                color = Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    ProfileCard(
        user = UserPreference(
            name = "Alvin",
            email = "alvintriseptia@gmail.com",
        )
    )
}