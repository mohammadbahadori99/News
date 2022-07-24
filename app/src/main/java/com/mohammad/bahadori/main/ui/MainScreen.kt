package com.mohammad.bahadori.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.ObserveLifecycle
import com.example.core.navigation.NavigationRoutes
import com.example.core.theme.*
import com.example.ui_article.ArticleListScreen
import com.example.ui_article.ArticleListViewModel
import com.example.ui_article.ArticleScreen
import com.example.ui_article.ArticleViewModel
import com.example.ui_article.KEY_ARTICLE_ID
import com.example.ui_user.AboutMeScreen
import com.example.ui_user.UserProfileScreen
import com.example.ui_user.UserProfileViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator


@ExperimentalMaterialNavigationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val scaffoldState = rememberScaffoldState()
    val showBottomBar = remember { mutableStateOf(false) }

    val items = listOf(
        NavigationRoutes.ArticleList,
        NavigationRoutes.UserProfile
    )

    navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBar.value = when (destination.route) {
            NavigationRoutes.ArticleList.route, NavigationRoutes.UserProfile.route, NavigationRoutes.AboutMe.route -> true
            else -> false
        }
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetBackgroundColor = MaterialTheme.colors.background,
        scrimColor = MaterialTheme.colors.primary.copy(alpha = 0.2f)
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                if (showBottomBar.value) {
                    Column {
                        Divider(
                            color = MaterialTheme.colors.divider,
                            thickness = 1.dp
                        )
                        BottomNavigation(
                            backgroundColor = MaterialTheme.colors.background,
                            elevation = 0.dp
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = screen.icon),
                                            contentDescription = stringResource(id = screen.title)
                                        )
                                    },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(screen.route) { inclusive = true }
                                        }
                                    },
                                    selectedContentColor = MaterialTheme.colors.textPrimary,
                                    unselectedContentColor = MaterialTheme.colors.textPrimaryLight
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.Splash.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(
                    route = NavigationRoutes.Splash.route
                ) {
                    SplashScreen(
                        navController = navController
                    )
                }
                composable(
                    route = NavigationRoutes.ArticleList.route
                ) {
                    val viewModel = hiltViewModel<ArticleListViewModel>(it)
                    viewModel.ObserveLifecycle(lifecycle = LocalLifecycleOwner.current.lifecycle)
                    ArticleListScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                composable(
                    route = NavigationRoutes.UserProfile.route
                ) {
                    val viewModel = hiltViewModel<UserProfileViewModel>(it)
                    UserProfileScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                composable(
                    route = NavigationRoutes.Article.route + "/{$KEY_ARTICLE_ID}",
                    arguments = listOf(navArgument(KEY_ARTICLE_ID) { type = NavType.StringType })
                ) {
                    val viewModel = hiltViewModel<ArticleViewModel>(it)
                    ArticleScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                bottomSheet(
                    route = NavigationRoutes.AboutMe.route
                ) {
                    AboutMeScreen()
                }
            }
        }
    }
}
