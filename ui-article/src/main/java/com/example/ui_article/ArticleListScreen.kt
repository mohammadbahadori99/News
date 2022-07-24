package com.example.ui_article

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.core.OnBottomReached
import com.example.core.R
import com.example.core.collectAsStateLifecycleAware
import com.example.core.navigation.NavigationRoutes
import com.example.core.theme.*
import com.example.core.viewModel.BaseViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ArticleListScreen(
    viewModel: ArticleListViewModel,
    navController: NavHostController
) {
    val networkViewState by viewModel.networkViewState.collectAsStateLifecycleAware(initial = BaseViewModel.NetworkViewState())
    val articleList by viewModel.articleList.collectAsStateLifecycleAware(initial = emptyList())

    val swipeRefreshState = rememberSwipeRefreshState(networkViewState.showProgress)
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (networkViewState.showError) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .background(MaterialTheme.colors.surface)
                    .clickable {
                        viewModel.refresh()
                    }
                    .padding(top = 12.dp, bottom = 12.dp, end = 16.dp, start = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = networkViewState.errorIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                    tint = MaterialTheme.colors.red
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 16.dp)
                        .weight(1f),
                    text = stringResource(id = networkViewState.errorMessage),
                    style = MaterialTheme.typography.x3_normal,
                    color = MaterialTheme.colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(id = R.string.msg_retry),
                    style = MaterialTheme.typography.x3_normal,
                    color = MaterialTheme.colors.red
                )
            }
            Divider(
                color = MaterialTheme.colors.divider,
                thickness = 1.dp
            )
        }
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = swipeRefreshState,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.textPrimary
                )
            },
            onRefresh = { viewModel.refresh() }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {
                items(
                    items = articleList ?: emptyList(),
                    key = { it.id }
                ) { item ->
                    ArticleListItem(item = item) {
                        val route = NavigationRoutes.Article.route + "/${item.id}"
                        navController.navigate(route)
                    }
                    Divider(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        color = MaterialTheme.colors.divider,
                        thickness = 1.dp
                    )
                }
                if (networkViewState.showProgressMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.Center),
                                color = MaterialTheme.colors.primary,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }

    lazyListState.OnBottomReached {
        if (!networkViewState.showProgress && !networkViewState.showProgressMore) {
            viewModel.getNextPage()
        }
    }
}
