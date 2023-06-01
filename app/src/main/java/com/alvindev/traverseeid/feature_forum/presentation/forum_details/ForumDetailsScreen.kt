package com.alvindev.traverseeid.feature_forum.presentation.forum_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.alvindev.traverseeid.R
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
    val comments = viewModel.getForumComments(post?.id ?: 0).collectAsLazyPagingItems()
    val state = viewModel.state
    val context = LocalContext.current

    if(state.isSuccess){
        comments.retry()
        viewModel.setIsSuccess(false)
        Toast.makeText(context, "Comment added", Toast.LENGTH_SHORT).show()
    }

    LazyColumn {
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
        items(comments, key = { comment -> comment.id }) { comment ->
            ForumCommentItem(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                imageUrl = null,
                isOfficial = false,
                comment = comment?.text ?: "",
                commentAuthor = comment?.authorId ?: "",
                commentTime = "2 hours ago",
            )
            TraverseeDivider(
                modifier = Modifier.padding(16.dp),
            )
        }

        when (comments.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        text = stringResource(id = R.string.error_occurred),
                        style = Typography.body2,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
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
                if (comments.itemCount == 0) {
                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            text = stringResource(id = R.string.no_comments),
                            style = Typography.body2,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }

        when (comments.loadState.append) {
            is LoadState.Error -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            text = stringResource(id = R.string.error_occurred),
                            style = Typography.body2,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
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
fun ForumDetailsScreenPreview() {
    TraverseeTheme {
        ForumDetailsScreen()
    }
}