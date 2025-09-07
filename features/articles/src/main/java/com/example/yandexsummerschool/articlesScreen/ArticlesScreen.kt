package com.example.yandexsummerschool.articlesScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexsummerschool.common.R
import com.example.yandexsummerschool.ui.common.components.BottomNavigationBar
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.example.yandexsummerschool.ui.common.components.TopAppBar
import com.example.yandexsummerschool.ui.common.components.TopAppBarElement
import com.example.yandexsummerschool.ui.common.screens.BasicEmptyScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    navController: NavController,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel: ArticlesScreenViewModel = viewModel(factory = viewModelFactory)
    val articleState by viewModel.articlesScreenState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(TopAppBarElement.Articles, navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            when (val state = articleState) {
                ArticlesScreenState.Empty ->
                    BasicEmptyScreen(
                        stringResource(R.string.no_articles_now),
                        stringResource(R.string.try_later),
                    )

                ArticlesScreenState.Loading -> LoadingIndicator()
                is ArticlesScreenState.Content -> {
                    ArticlesDivContent(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
