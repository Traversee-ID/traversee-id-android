package com.alvindev.traverseeid.feature_forum.presentation.forum_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumTextField
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = ScreenRoute.ForumPost
)
@Composable
fun ForumPostScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        ForumTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .shadow(4.dp, shape = Shapes.large)
                .background(color = Color.White, shape = Shapes.large)
                .padding(16.dp),
            label = "Caption",
            placeholder = "Write your caption here...",
            actionText = "Post",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForumPostScreenPreview() {
    TraverseeTheme {
        ForumPostScreen()
    }
}