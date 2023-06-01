package com.alvindev.traverseeid.feature_forum.presentation.forum

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.alvindev.destinations.ForumDetailsScreenDestination
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumPostItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.alvindev.traverseeid.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.Forum,
)
@Composable
fun ForumScreen(
    navigator: DestinationsNavigator,
    viewModel: ForumViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val posts = viewModel.getAllForumPosts().collectAsLazyPagingItems()
    val context = LocalContext.current

    if (state.post != null) {
        posts.itemSnapshotList.find { it?.id == state.post.id }?.copy(
            totalLikes = state.post.totalLikes,
        )
        viewModel.setPost(null)
    }

    if(state.error != null){
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
//        item {
//            Column {
//                TraverseeRowIcon(
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    icon = Icons.Default.Campaign,
//                    text = stringResource(id = R.string.announcement),
//                    textStyle = Typography.h2,
//                    iconTintColor = TraverseeOrange
//                )
//                ForumPostItem(
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .clickable {
//                            navigator.navigate(ScreenRoute.ForumDetails)
//                        },
//                )
//                TraverseeDivider(
//                    modifier = Modifier.padding(vertical = 16.dp),
//                    thickness = 4.dp
//                )
//            }
//        }
        items(posts, key = { post -> post.id }) { post ->
            ForumPostItem(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).clickable{
                    navigator.navigate(ForumDetailsScreenDestination(post = post))
                },
                authorName = post?.authorId ?: "",
                authorCaption = post?.text ?: "",
                totalLike = post?.totalLikes ?: 0,
                totalComment = 0,
                postTime = "1 hour ago",
                imageUrl = null,
                isOfficial = false,
                onLiked = {
                    viewModel.likePost(post?.id ?: 0)
                },
            )
            TraverseeDivider(
                modifier = Modifier.padding(16.dp),
            )
        }

        when (posts.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_occurred),
                            style = Typography.body2,
                            color = Color.Red,
                        )
                    }
                }
            }
            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            else -> {
                if (posts.itemCount == 0) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = stringResource(id = R.string.no_data),
                                style = Typography.body2,
                                color = Color.Red,
                            )
                        }
                    }
                }
            }
        }

        when (posts.loadState.append) {
            is LoadState.Error -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_occurred),
                            style = Typography.body2,
                            color = Color.Red,
                        )
                    }
                }
            }
            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            else -> {}
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