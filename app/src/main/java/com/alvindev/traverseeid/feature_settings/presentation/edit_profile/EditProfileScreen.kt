package com.alvindev.traverseeid.feature_settings.presentation.edit_profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alvindev.destinations.DetailImageScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeTextField
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.EditProfile
)
@Composable
fun EditProfileScreen(
    name: String?,
    avatarUrl: Uri?,
    email: String?,
    viewModel: EditProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.setUser(name, email, avatarUrl)
    }
    val state = viewModel.state
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onSelectedImagePicker(uri) }
    )
    val context = LocalContext.current

    if(state.isSuccess){
        navigator.popBackStack()
    }

    if(state.error.isNotEmpty()){
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    if(state.isShowDialog){
        ImageDialog(
            onChangeImage = {
                viewModel.setShowDialog(false)
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            onLookImage = {
                navigator.navigate(
                    DetailImageScreenDestination(
                        imageUri = state.avatarUrl,
                        imageTitle = (state.name + " Picture") ?: "",
                        imageDescription = "Profile picture"
                    )
                )
            },
            onDismissRequest = {
                viewModel.setShowDialog(false)
            },
            isHide = state.avatarUrl == null
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UserImage(
            image = state.avatarUrl,
            onClick = {
                viewModel.setShowDialog(true)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TraverseeTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = {
                Text("Name")
            },
            placeholder = {
                Text("Name")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TraverseeTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            value = state.email,
            enabled = false,
            label = {
                Text("Email")
            },
            placeholder = {
                Text("Email")
            },
        )

        TraverseeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = viewModel::onSubmit,
            enabled = state.isSubmitting.not(),
            text = state.isSubmitting.not().let {
                if (it) "Save" else "Saving..."
            }
        )
    }
}

@Composable
fun UserImage(
    image: Uri?,
    onClick: () -> Unit = {},
) {
    TraverseeButton(
        modifier = Modifier
            .size(150.dp)
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.2.dp,
            pressedElevation = 0.5.dp,
            disabledElevation = 0.dp
        ),
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize(),
            ) {
            if (image != null) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(50)),
                    model = image,
                    contentDescription = stringResource(id = R.string.user_avatar),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.user_avatar),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(50))
                        .background(Color.LightGray)
                )
            }
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colors.primary),
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_avatar),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun ImageDialog(
    onChangeImage: () -> Unit = {},
    onLookImage: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    isHide: Boolean = false,
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
                onClick = onChangeImage,
                contentPadding = PaddingValues(8.dp),
            ){
                Text(
                    text = stringResource(R.string.change_picture),
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if(isHide.not()){
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onLookImage,
                    contentPadding = PaddingValues(8.dp),
                ){
                    Text(
                        text = stringResource(R.string.look_picture),
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(
        navigator = EmptyDestinationsNavigator,
        name = "John Doe",
        email = "johndoe@gmail.com",
        avatarUrl = null
    )
}