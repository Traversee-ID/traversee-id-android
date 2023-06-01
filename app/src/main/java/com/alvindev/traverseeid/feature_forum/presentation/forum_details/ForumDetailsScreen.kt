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
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.presentation.component.TraverseeAlertDialog
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostEntity
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumTextField
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumCommentItem
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumPostItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = ScreenRoute.ForumDetails,
)
@Composable
fun ForumDetailsScreen(
    post: ForumPostEntity? = null,
    viewModel: ForumDetailsViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shouldStartPaginate = remember {
        derivedStateOf {
            state.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -5) >= (lazyColumnListState.layoutInfo.totalItemsCount - 3)
        }
    }

    val comments = state.comments

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && state.listState == ListState.IDLE){
            viewModel.getForumComments(post?.id ?: 0)
        }
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

    if (state.isShowDialog) {
        TraverseeAlertDialog(
            title = stringResource(id = R.string.delete_comment),
            text = stringResource(id = R.string.are_you_sure),
            onConfirm = {
                viewModel.deleteComment(post?.id ?: 0, state.commentId)
            },
            onCancel = {
                viewModel.setShowDialog(false)
            },
            enabled = state.isSubmitting.not(),
        )
    }

    LazyColumn(
        state = lazyColumnListState,
    ) {
        item {
            post?.let {
                ForumPostItem(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    authorName = post.authorId,
                    postTime = "2 hours ago",
                    totalLike = post.totalLikes ?: 0,
                    totalComment = 10,
                    authorCaption = post.text ?: "",
                    onLiked = { },
                    isOfficial = false,
                    imageUrl = null
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
                        viewModel.createComment(post?.id ?: 0)
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
        items(comments, key = { post -> post.id }) { post ->
            ForumCommentItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                imageUrl = null,
                isOfficial = false,
                commentTime = "2 hours ago",
                isUser = state.userId == post.authorId,
                commentAuthor = post.authorId ?: "",
                comment = post.text ?: "",
                onDelete = {
                    viewModel.setCommentId(post.id ?: 0)
                    viewModel.setShowDialog(true)
                },
            )
            TraverseeDivider(
                modifier = Modifier.padding(16.dp),
            )
        }

        item (
            key = state.listState,
        ) {
            when(state.listState) {
                ListState.LOADING -> {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
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
        ForumDetailsScreen()
    }
}