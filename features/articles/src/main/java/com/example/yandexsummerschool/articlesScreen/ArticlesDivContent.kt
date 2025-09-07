package com.example.yandexsummerschool.articlesScreen

import android.view.ContextThemeWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.yandexsummerschool.ui.common.components.LoadingIndicator
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.div2.DivData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

@Composable
fun ArticlesDivContent(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val imageLoader = remember(context) { PicassoDivImageLoader(context) }
    val configuration = remember(imageLoader) { DivConfiguration.Builder(imageLoader).build() }
    val contextThemeWrapper = remember { ContextThemeWrapper(context, context.theme) }

    val divContext = remember(contextThemeWrapper, configuration, lifecycleOwner) {
        Div2Context(
            baseContext = contextThemeWrapper,
            configuration = configuration,
            lifecycleOwner = lifecycleOwner
        )
    }

    var divData: DivData? by remember { mutableStateOf(null) }

    LaunchedEffect(context) {
        divData = withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets
                    .open("articlesLayout.json")
                    .bufferedReader()
                    .use { it.readText() }

                val jsonObject = JSONObject(jsonString)
                jsonObject.asDiv2DataWithTemplates()
            } catch (e: Exception) {
                null
            }
        }
    }

    if (divData == null) {
        LoadingIndicator()
    } else {
        AndroidView(
            modifier = modifier,
            factory = { Div2View(divContext) },
            update = { view ->
                divData?.let { data -> view.setData(data, DivDataTag("div2")) }
            }
        )
    }
}


