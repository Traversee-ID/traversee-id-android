package com.alvindev.traverseeid.core.presentation.detail_image

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.alvindev.destinations.DetailImageScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeErrorState
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
@Destination(
    route = ScreenRoute.DetailImage
)
@Composable
fun DetailImageScreen(
    navigator: DestinationsNavigator,
    imageUri: Uri? = null,
    imageTitle: String? = null,
    imageDescription: String? = null,
) {
    imageUri?.let {
        Scaffold(
            topBar = {
                DetailImageTopBar(
                    onBackClick = {
                        navigator.popBackStack()
                    },
                    title = imageTitle ?: stringResource(R.string.detail_image)
                )
            }
        ) { contentPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = it,
                    contentDescription = imageDescription,
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    fallback = painterResource(id = R.drawable.empty_error)
                )
            }
        }
    } ?: TraverseeErrorState(
        modifier = Modifier.fillMaxSize(),
        image = painterResource(id = R.drawable.empty_error),
        title = stringResource(id = R.string.error_title),
        description = stringResource(id = R.string.error_description),
    )
}

@Composable
fun DetailImageTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    title: String = "Detail Image"
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.padding(horizontal = 12.dp),
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailImageScreen() {
    TraverseeTheme() {
        DetailImageScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}