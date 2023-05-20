package com.alvindev.traverseeid.core.presentation.detail_image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.alvindev.destinations.DetailImageScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Composable
@Destination(
    route = ScreenRoute.DetailImage,
    navArgsDelegate = DetailImageNavArgs::class
)
fun DetailImageScreen(
    navigator: DestinationsNavigator,
) {
    val navController = rememberNavController()
    val bundle = navController.currentBackStackEntry?.savedStateHandle
    val navArgs = bundle?.let { DetailImageScreenDestination.argsFrom(bundle) }

    navArgs?.imageUri?.let {
        Scaffold(
            topBar = {
                DetailImageTopBar(
                    onBackClick = {
                        navigator.popBackStack()
                    },
                    title = navArgs.imageTitle ?: stringResource(R.string.detail_image)
                )
            }
        ) {contentPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = navArgs.imageUri,
                    contentDescription = navArgs?.imageDescription,
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun DetailImageTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    title: String = "Detail Image"
){
    TopAppBar(
        modifier = modifier,
        title = {
                Text(text = title)
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.padding(horizontal = 12.dp),
                onClick = onBackClick,
            ){
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