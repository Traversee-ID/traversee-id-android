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
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.util.digitSeparator

@Composable
fun ForumPostItem(
    modifier: Modifier = Modifier,
    isOfficial: Boolean = false,
    authorImage: String? = null,
    authorName: String,
    authorCaption: String,
    postTime: String,
    totalLike: Int = 0,
    totalComment: Int = 0,
    onLiked: () -> Unit = {},
    isLiked: Boolean = false,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        authorImage?.let {
            AsyncImage(
                model = it,
                contentDescription = authorName,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        } ?: Image(
            painterResource(id = R.drawable.ic_profile),
            contentDescription = authorName,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50)),
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
                    text = authorName,
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
                        contentDescription = "Official Account",
                        tint = Color.Green,
                    )
                }
            }

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = postTime,
                style = Typography.caption
            )

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = authorCaption,
                style = Typography.body2,
                textAlign = TextAlign.Justify,
            )

//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp),
//                elevation = 4.dp,
//                onClick = onClickCard,
//                shape = Shapes.large,
//                backgroundColor = Color.White,
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp, vertical = 8.dp),
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.dummy_borobudur),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(100.dp)
//                            .clip(Shapes.large),
//                        contentScale = ContentScale.Crop,
//                        alignment = Alignment.Center
//                    )
//
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 8.dp),
//                        verticalArrangement = Arrangement.spacedBy(4.dp)
//                    ) {
//                        Text(
//                            text = "Cultural",
//                            style = Typography.caption,
//                        )
//                        Text(
//                            text = "Share your experience at Borobudur",
//                            style = Typography.subtitle2,
//                            color = MaterialTheme.colors.primaryVariant,
//                        )
//                    }
//                }
//            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TraverseeRowIcon(
                    modifier = Modifier.clickable {
                        onLiked()
                    },
                    icon = if (isLiked) Icons.Default.ThumbUp else Icons.Outlined.ThumbUp,
                    text = totalLike.digitSeparator(),
                )
                TraverseeRowIcon(
                    icon = Icons.Outlined.Comment,
                    text = totalComment.digitSeparator(),
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
            isOfficial = true,
            authorName = "Alvin Dev",
            authorCaption = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae nisi quis nisl aliquet aliquam. Sed vitae nisi quis nisl aliquet aliquam.",
            postTime = "1 hour ago",
            totalLike = 100,
            totalComment = 100,
        )
    }
}