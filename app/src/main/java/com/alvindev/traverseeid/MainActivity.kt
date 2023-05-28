package com.alvindev.traverseeid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alvindev.NavGraphs
import com.alvindev.appCurrentDestinationAsState
import com.alvindev.startAppDestination
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.util.LocaleUtil
import com.alvindev.traverseeid.navigation.NavigationItem
import com.alvindev.traverseeid.navigation.NavigationMapper
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val bottomBarRoutes = listOf(
        ScreenRoute.Campaign,
        ScreenRoute.Forum,
        ScreenRoute.Tourism,
        ScreenRoute.Sentiment,
        ScreenRoute.Settings,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraverseeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val currentDestination = navController.appCurrentDestinationAsState().value
                        ?: NavGraphs.root.startAppDestination
                    val currentRoute = currentDestination.baseRoute
                    val navigationStringId: Int =
                        NavigationMapper.mapToNavigationStringResource(currentRoute)

                    Scaffold(
                        bottomBar = {
                            if (currentRoute in bottomBarRoutes) {
                                BottomBar(
                                    navController = navController,
                                )
                            }
                        },
                        topBar = {
                            if (navigationStringId != -1) {
                                routeName = ""
                                val title =
                                    stringResource(navigationStringId)
                                if (currentRoute in bottomBarRoutes) {
                                    if(currentRoute == ScreenRoute.Forum){
                                        TopBarMainScreen(
                                            title = title,
                                            actions = {
                                                IconButton(
                                                    onClick = {
                                                        navController.navigate(ScreenRoute.ForumPost)
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.PostAdd,
                                                        contentDescription = "Add Post"
                                                    )
                                                }
                                            }
                                        )
                                    }else{
                                        TopBarMainScreen(title = title)
                                    }
                                } else {
                                    TopBarCommonScreen(
                                        title = title,
                                        onBackClick = {
                                            navController.popBackStack()
                                        }
                                    )
                                }
                            } else if (routeName.isNotEmpty()) {
                                TopBarCommonScreen(
                                    title = routeName,
                                    onBackClick = {
                                        navController.popBackStack()
                                        routeName = ""
                                    }
                                )
                            }
                        }
                    ) { contentPadding ->
                        BackHandler {
                            if (currentRoute in bottomBarRoutes) {
                                finish()
                            } else {
                                val canPop = navController.popBackStack()
                                if (!canPop) {
                                    finish()
                                }
                                routeName = ""
                            }
                        }

                        DestinationsNavHost(
                            modifier = Modifier.padding(contentPadding),
                            navController = navController,
                            navGraph = NavGraphs.root,
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        var routeName = ""
    }
}

@Composable
private fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.menu_campaign),
            icon = Icons.Default.Campaign,
            route = ScreenRoute.Campaign,
            contentDescription = "campaign_page"
        ),
        NavigationItem(
            title = stringResource(R.string.menu_forum),
            icon = Icons.Default.Forum,
            route = ScreenRoute.Forum,
            contentDescription = "forum_page"
        ),
        NavigationItem(
            title = stringResource(R.string.menu_tourism),
            icon = Icons.Default.Place,
            route = ScreenRoute.Tourism,
            contentDescription = "tourism_page"
        ),
        NavigationItem(
            title = stringResource(R.string.menu_sentiment),
            icon = Icons.Default.Analytics,
            route = ScreenRoute.Sentiment,
            contentDescription = "sentiment_page"
        ),
        NavigationItem(
            title = stringResource(R.string.menu_settings),
            icon = Icons.Default.AccountCircle,
            route = ScreenRoute.Settings,
            contentDescription = "settings_page"
        ),
    )
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        contentColor = MaterialTheme.colors.primary,
        elevation = 4.dp
    ) {
        val currentDestination = navController.appCurrentDestinationAsState().value
            ?: NavGraphs.root.startAppDestination
        val currentRoute = currentDestination.route

        navigationItems.map { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        item.title,
                        fontSize = 11.sp
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TopBarMainScreen(
    title: String,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        actions = actions
    )
}

@Composable
fun TopBarCommonScreen(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        }
    )
}
