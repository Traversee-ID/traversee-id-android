package com.alvindev.traverseeid.feature_forum.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
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
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeRed
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun ForumCommentItem(
    modifier: Modifier = Modifier,
    isOfficial: Boolean = false,
    authorImage: String? = null,
    commentAuthor: String,
    commentTime: String,
    comment: String,
    isUser: Boolean = false,
    onDelete: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        authorImage?.let {
            AsyncImage(
                model = it,
                contentDescription = commentAuthor,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        } ?: Image(
            painterResource(id = R.drawable.ic_profile),
            contentDescription = commentAuthor,
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
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = commentAuthor,
                        style = Typography.subtitle2
                    )
                    if(isOfficial){
                        Icon(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    color = Color.Green.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(50)
                                ),
                            imageVector = Icons.Default.Check,
                            contentDescription = "Official",
                            tint = Color.Green,
                        )
                    }
                }

                if(isUser){
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = onDelete
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Comment",
                            tint = TraverseeRed
                        )
                    }
                }
            }

            Text(
                modifier= Modifier.padding(bottom=8.dp),
                text = commentTime,
                style = Typography.caption
            )

            Text(
                modifier = Modifier,
                text = comment,
                style = Typography.body2,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForumCommentItemPreview() {
    TraverseeTheme {
        ForumCommentItem(
            isOfficial = true,
            commentAuthor = "Alvin Dev",
            commentTime = "2 hours ago",
            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, diam sit amet aliquet tincidunt, nisl nunc aliquam nunc, vitae aliquam nunc nisl vitae nisl. Sed euismod, diam sit amet aliquet tincidunt, nisl nunc aliquam nunc, vitae aliquam nunc nisl vitae nisl.",
        )
    }
}