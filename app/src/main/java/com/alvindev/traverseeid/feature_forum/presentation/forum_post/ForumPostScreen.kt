package com.alvindev.traverseeid.feature_forum.presentation.forum_post

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeTextField
import com.alvindev.traverseeid.core.theme.*
import com.alvindev.traverseeid.core.util.ComposeFileProvider
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCampaignEntity
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumTextField
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.ForumPost
)
@Composable
fun ForumPostScreen(
    navigator: DestinationsNavigator,
    viewModel: ForumPostViewModel = hiltViewModel(),
    campaign: ForumCampaignEntity? = null,
) {
    val state = viewModel.state
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onSelectedImagePicker(uri) }
    )

    if (state.isSuccess) {
        Toast.makeText(context, stringResource(id = R.string.post_created), Toast.LENGTH_SHORT)
            .show()
        navigator.popBackStack()
    }

    if (state.error != null) {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    if (state.isShowDialog) {
        ImageDialog(
            onDeletePhotoClick = {
                viewModel.onSelectedImagePicker(null)
                viewModel.setShowDialog(false)
            },
            onPickPhotoClick = {
                photoPickerLauncher.launch(PickVisualMediaRequest())
                viewModel.setShowDialog(false)
            },
            onDismissRequest = {
                viewModel.setShowDialog(false)
            },
        )
    }

    LazyColumn {
        campaign?.let {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    elevation = 4.dp,
                    shape = Shapes.large,
                    backgroundColor = Color.White,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        AsyncImage(
                            model = it.imageUrl,
                            fallback = painterResource(id = R.drawable.dummy_borobudur),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(Shapes.large),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = it.category,
                                style = Typography.caption,
                            )
                            Text(
                                text = it.name,
                                style = Typography.subtitle2,
                                color = MaterialTheme.colors.primaryVariant,
                            )
                        }
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .shadow(4.dp, shape = Shapes.large)
                    .background(color = Color.White, shape = Shapes.large)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.title) + " *",
                        style = MaterialTheme.typography.subtitle2
                    )
                    TraverseeTextField(
                        label = {
                            Text(
                                text = stringResource(id = R.string.title),
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.title_message),
                            )
                        },
                        value = state.title,
                        onValueChange = viewModel::onTitleChange,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.caption) + " *",
                        style = MaterialTheme.typography.subtitle2
                    )
                    ForumTextField(
                        label = stringResource(id = R.string.caption),
                        placeholder = stringResource(id = R.string.caption_message),
                        value = state.text,
                        onValueChange = viewModel::onTextChange,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Photo (${stringResource(id = R.string.optional)})",
                        style = MaterialTheme.typography.subtitle2
                    )
                    state.selectedImagePicker?.let {
                        AsyncImage(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(Shapes.medium)
                                .clickable { viewModel.setShowDialog(true) },
                            model = it,
                            contentDescription = null,
                        )
                    } ?: IconButton(
                        modifier = Modifier
                            .size(80.dp),
                        onClick = {
                            photoPickerLauncher.launch(PickVisualMediaRequest())
                            viewModel.setShowDialog(false)
                        },
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize(),
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = stringResource(id = R.string.add_photo),
                        )
                    }
                }

                TraverseeButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.post),
                    onClick = {
                        val image = state.selectedImagePicker?.let { ComposeFileProvider.uriToFile(state.selectedImagePicker, context) }
                        viewModel.onPostClick(campaign?.id, image)
                    },
                    enabled = state.isSubmitting.not() && state.text.isNotBlank() && state.title.isNotBlank(),
                )
            }
        }
    }
}


@Composable
fun ImageDialog(
    onPickPhotoClick: () -> Unit = {},
    onDeletePhotoClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onPickPhotoClick,
                contentPadding = PaddingValues(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.change_picture),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primaryVariant
                )
            }

            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onDeletePhotoClick,
                contentPadding = PaddingValues(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.delete_picture),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForumPostScreenPreview() {
    TraverseeTheme {
        ForumPostScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}