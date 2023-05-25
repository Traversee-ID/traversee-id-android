package com.alvindev.traverseeid.feature_forum.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForumPostItem(
    modifier: Modifier = Modifier,
    isOfficial: Boolean = false,
    onLiked: () -> Unit = {},
    onClickCard: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50)),
            painter = painterResource(id = R.drawable.dummy_komodo_island),
            contentDescription = "Alvin Avatar",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Alvin",
                    style = Typography.subtitle2
                )
                if (isOfficial) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .background(
                                color = Color.Green.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(50)
                            ),
                        imageVector = Icons.Default.Check,
                        contentDescription = "Campaign Ended",
                        tint = Color.Green,
                    )
                }
            }

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "19 Juni 2023",
                style = Typography.caption
            )

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Ini pertanyaan sangat valid, mengingat tradisi Buddhisme Thai saat ini adalah Theravada, sedangkan tradisi Borobudur itu Mahayana tantrik. Memang secara sejarah, tradisi Mahayana Khmer yang mirip Borobudur (CMIIW) pernah berkembang pesat di Thailand, tapi ya sudah tidak relevan.",
                style = Typography.body2,
                textAlign = TextAlign.Justify,
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                elevation = 4.dp,
                onClick = onClickCard,
                shape = Shapes.large,
                backgroundColor = Color.White,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dummy_borobudur),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(Shapes.large),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Cultural",
                            style = Typography.caption,
                        )
                        Text(
                            text = "Share your experience at Borobudur",
                            style = Typography.subtitle2,
                            color = MaterialTheme.colors.primaryVariant,
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TraverseeRowIcon(
                    modifier = Modifier.clickable {
                        onLiked()
                    },
                    icon = Icons.Outlined.ThumbUp,
                    text = "100",
                )
                TraverseeRowIcon(
                    icon = Icons.Outlined.Comment,
                    text = "2",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForumPostItemPreview() {
    TraverseeTheme {
        ForumPostItem(
            isOfficial = true
        )
    }
}