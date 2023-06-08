package com.alvindev.traverseeid.feature_forum.presentation.forum_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.destinations.CampaignDetailsScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.presentation.component.TraverseeAlertDialog
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_forum.domain.constant.DialogType
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostItem
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumTextField
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumCommentItem
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumPostItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.ForumDetails,
)
@Composable
fun ForumDetailsScreen(
    post: ForumPostItem? = null,
    navigator: DestinationsNavigator,
    viewModel: ForumDetailsViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current
    val lazyColumnListState = rememberLazyListState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            state.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -5) >= (lazyColumnListState.layoutInfo.totalItemsCount - 3)
        }
    }

    val comments = state.comments

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        viewModel.setIsLiked(post?.isLiked ?: false)
        viewModel.setTotalLikes(post?.forum?.totalLikes ?: 0)
        viewModel.setTotalComments(post?.forum?.totalComments ?: 0)

        if (shouldStartPaginate.value && state.listState == ListState.IDLE) {
            viewModel.getForumComments(post?.forum?.id ?: 0)
        }
    }

    if(state.error != null){
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    if (state.isDeleted) {
        Toast.makeText(context, stringResource(id = R.string.post_deleted), Toast.LENGTH_SHORT)
            .show()
        navigator.popBackStack()
    }

    if (state.isSuccess) {
        viewModel.setIsSuccess(false)
        if (state.commentId != 0) {
            viewModel.setCommentId(0)
            Toast.makeText(
                context,
                stringResource(id = R.string.comment_deleted),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(context, stringResource(id = R.string.comment_added), Toast.LENGTH_SHORT)
                .show()
        }
    }

    if (state.isShowDialog != null) {
        TraverseeAlertDialog(
            title = stringResource(id = if (state.isShowDialog == DialogType.DELETE_COMMENT) R.string.delete_comment else R.string.delete_post),
            text = stringResource(id = R.string.are_you_sure),
            onConfirm = {
                if (state.isShowDialog == DialogType.DELETE_COMMENT) {
                    viewModel.deleteComment(post?.forum?.id ?: 0, state.commentId)
                } else {
                    viewModel.deletePost(post?.forum?.id ?: 0)
                }
            },
            onCancel = {
                viewModel.setShowDialog(null)
            },
            enabled = state.isSubmitting.not(),
        )
    }

    LazyColumn(
        state = lazyColumnListState,
    ) {
        item {
            post?.forum?.let {
                ForumPostItem(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    authorName = post.forum.authorName ?: "-",
                    authorCaption = post.forum.text ?: "",
                    authorTitle = post.forum.title ?: "-",
                    postTime = post.forum.createdAt ?: "-",
                    totalLike = state.totalLikes,
                    totalComment = state.totalComments,
                    onLiked = {
                        if (state.isLiked) {
                            viewModel.unlikePost(post.forum.id)
                        } else {
                            viewModel.likePost(post.forum.id)
                        }
                    },
                    isOfficial = false,
                    authorImage = post.forum.authorProfileImage,
                    isLiked = state.isLiked,
                    campaign = post.campaign,
                    postImageUrl = post.forum.imageUrl,
                    cardOnClick = {
                        post.campaign?.id?.let { campaignId ->
                            navigator.navigate(CampaignDetailsScreenDestination(id = campaignId))
                        }
                    },
                    isUser = post.forum.authorId == state.userId,
                    postOnDelete = {
                        viewModel.setShowDialog(DialogType.DELETE_POST)
                    },
                )
            }
        }
        item {
            TraverseeDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 4.dp
            )
        }
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .shadow(4.dp, shape = Shapes.large)
                    .background(color = Color.White, shape = Shapes.large)
                    .padding(16.dp),
            ) {
                ForumTextField(
                    label = stringResource(id = R.string.comment),
                    placeholder = stringResource(id = R.string.comment),
                    value = state.text,
                    onValueChange = viewModel::onCommentChange,
                )
                TraverseeButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = stringResource(id = R.string.submit),
                    onClick = {
                        viewModel.createComment(post?.forum?.id ?: 0)
                    },
                    enabled = state.isSubmitting.not() && state.text.isNotBlank(),
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = stringResource(id = R.string.comments),
                style = Typography.h2,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            )
        }
        items(comments, key = { post -> post.id }) { comment ->
            ForumCommentItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                authorImage = comment.authorProfileImage,
                isOfficial = false,
                commentTime = comment.createdAt ?: "",
                isUser = state.userId == comment.authorId,
                commentAuthor = comment.authorName ?: "",
                comment = comment.text ?: "",
                onDelete = {
                    viewModel.setCommentId(comment.id ?: 0)
                    viewModel.setShowDialog(DialogType.DELETE_COMMENT)
                },
            )
            TraverseeDivider(
                modifier = Modifier.padding(16.dp),
            )
        }

        item(
            key = state.listState,
        ) {
            when (state.listState) {
                ListState.LOADING -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                ListState.PAGINATING -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                else -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForumDetailsScreenPreview() {
    TraverseeTheme {
        ForumDetailsScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}