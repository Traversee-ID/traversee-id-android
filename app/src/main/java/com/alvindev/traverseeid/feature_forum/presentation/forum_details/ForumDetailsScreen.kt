package com.alvindev.traverseeid.feature_forum.presentation.forum_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumTextField
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumCommentItem
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumPostItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = ScreenRoute.ForumDetails,
)
@Composable
fun ForumDetailsScreen() {
    LazyColumn{
        item {
            ForumPostItem(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            )
        }
        item{
            TraverseeDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 4.dp
            )
        }
        item {
            ForumTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .shadow(4.dp, shape = Shapes.large)
                    .background(color = Color.White, shape = Shapes.large)
                    .padding(16.dp),
                label = "Comment",
                placeholder = "Write your comment here...",
                actionText = "Comment"
            )
        }
        item{
            Spacer(modifier = Modifier.height(16.dp))
        }
        item{
            Text(
                text = "Comments",
                style = Typography.h2,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            )
        }
        items(5){
            ForumCommentItem(
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            TraverseeDivider(
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForumDetailsScreenPreview() {
    TraverseeTheme {
        ForumDetailsScreen()
    }
}