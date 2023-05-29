package com.alvindev.traverseeid.feature_forum.presentation.forum

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumPostItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeOrange
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.Forum,
)
@Composable
fun ForumScreen(
    navigator: DestinationsNavigator
) {
    LazyColumn(
        modifier = Modifier,
    ) {
        item{
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            TraverseeRowIcon(
                modifier = Modifier.padding(horizontal = 16.dp),
                icon = Icons.Default.Campaign,
                text = stringResource(id = R.string.announcement),
                textStyle = Typography.h2,
                iconTintColor = TraverseeOrange
            )
        }
        item{
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            ForumPostItem(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        navigator.navigate(ScreenRoute.ForumDetails)
                    },
            )
        }
        item {
            TraverseeDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 4.dp
            )
        }
        items(5){
            ForumPostItem(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            TraverseeDivider(
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForumScreenPreview() {
    TraverseeTheme() {
        ForumScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}