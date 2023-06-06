package com.alvindev.traverseeid.feature_forum.presentation.forum

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.alvindev.destinations.CampaignDetailsScreenDestination
import com.alvindev.destinations.ForumDetailsScreenDestination
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumPostItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeErrorState
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
    val posts = viewModel.getAllForumPosts().collectAsLazyPagingItems()

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
        items(posts, key = { post -> post.forum.id }) { post ->
            var isLiked by remember { mutableStateOf(post?.isLiked ?: false) }
            var totalLikes by remember { mutableStateOf(post?.forum?.totalLikes ?: 0) }

            ForumPostItem(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        val postArgument = post?.copy(
                            forum = post.forum.copy(
                                totalLikes = totalLikes,
                            ),
                            isLiked = isLiked,
                        )
                        navigator.navigate(ForumDetailsScreenDestination(post = postArgument))
                    },
                authorName = post?.forum?.authorName ?: "-",
                authorCaption = post?.forum?.text ?: "",
                totalLike = totalLikes,
                totalComment = post?.forum?.totalComments ?: 0,
                postTime = post?.forum?.createdAt ?: "",
                authorImage = post?.forum?.authorProfileImage,
                isOfficial = false,
                isLiked = isLiked,
                onLiked = {
                    if (isLiked) {
                        viewModel.unlikePost(post?.forum?.id ?: 0)
                        isLiked = false
                        totalLikes -= 1
                    } else {
                        viewModel.likePost(post?.forum?.id ?: 0)
                        isLiked = true
                        totalLikes += 1
                    }
                },
                campaign = post?.campaign,
                cardOnClick = {
                    post?.campaign?.id?.let { campaignId ->
                        navigator.navigate(CampaignDetailsScreenDestination(id = campaignId))
                    }
                }
            )
            TraverseeDivider(
                modifier = Modifier.padding(16.dp),
            )
        }


        when (posts.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item {
                    TraverseeErrorState(
                        modifier = Modifier.fillParentMaxSize(),
                        image = painterResource(id = R.drawable.empty_error),
                        title = stringResource(id = R.string.error_title),
                        description = stringResource(id = R.string.error_description),
                    )
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
                        TraverseeErrorState(
                            image = painterResource(id = R.drawable.empty_list),
                            title = stringResource(id = R.string.no_post_found),
                            description = stringResource(id = R.string.no_post_found_description),
                        )
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