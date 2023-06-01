package com.alvindev.traverseeid.feature_forum.presentation.forum_post

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.theme.*
import com.alvindev.traverseeid.core.util.ComposeFileProvider
import com.alvindev.traverseeid.feature_forum.presentation.component.ForumTextField
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterialApi::class)
@Destination(
    route = ScreenRoute.ForumPost
)
@Composable
fun ForumPostScreen(
    viewModel: ForumPostViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context= LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onSelectedImagePicker(uri) }
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { hasImage -> hasImage}
    )

    if(state.isShowDialog){
        ImageDialog(
            onTakePhotoClick = {
                val uri = ComposeFileProvider.getImageUri(context)
                viewModel.onSelectedImagePicker(uri)
                takePhotoLauncher.launch(uri)
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

    LazyColumn{
        item{
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                elevation = 4.dp,
                onClick = {},
                shape = Shapes.large,
                backgroundColor = Color.White,
                enabled = false,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dummy_borobudur),
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
                            text = "Cultural",
                            style = Typography.caption,
                        )
                        Text(
                            text = "Share your experience at Borobudur",
                            style = Typography.subtitle2,
                            color = MaterialTheme.colors.primaryVariant,
                        )
                    }
                }
            }
        }
        item{
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .shadow(4.dp, shape = Shapes.large)
                    .background(color = Color.White, shape = Shapes.large)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){


                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text ="Caption *",
                        style = MaterialTheme.typography.subtitle2
                    )
                    ForumTextField(
                        label = "Caption",
                        placeholder = "Write your caption here..."
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text ="Photo (Optional)",
                        style = MaterialTheme.typography.subtitle2
                    )
                    state.selectedImagePicker?.let {
                        AsyncImage(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(Shapes.large)
                                .clickable { viewModel.setShowDialog(true) },
                            model = it,
                            contentDescription = null,
                        )
                    } ?: IconButton(
                        modifier = Modifier
                            .size(70.dp),
                        onClick = {
                            viewModel.setShowDialog(true)
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
                    text = "Post",
                    onClick = {},
                    enabled = true,
                )
            }
        }
    }
}


@Composable
fun ImageDialog(
    onTakePhotoClick: () -> Unit = {},
    onPickPhotoClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
){
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
                onClick = onTakePhotoClick,
                contentPadding = PaddingValues(8.dp),
            ){
                Text(
                    text = stringResource(R.string.take_picture),
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }

                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onPickPhotoClick,
                    contentPadding = PaddingValues(8.dp),
                ){
                    Text(
                        text = stringResource(R.string.pick_from_gallery),
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForumPostScreenPreview() {
    TraverseeTheme {
        ForumPostScreen()
    }
}