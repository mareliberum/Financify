package com.example.yandexsummerschool.ui.features.accountScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.yandexsummerschool.R
import com.example.yandexsummerschool.domain.utils.Currencies
import com.example.yandexsummerschool.ui.common.components.ListItem
import com.example.yandexsummerschool.ui.common.components.ListItemSize
import com.example.yandexsummerschool.ui.theme.dangerAction
import com.example.yandexsummerschool.ui.theme.white

@Composable
fun BottomSheetContent(
    onCurrencySelected: (String) -> Unit,
    onCancel: () -> Unit,
) {
    Column {
        ListItem(
            title = "Российский рубль ₽",
            leadingIcon = painterResource(R.drawable.rub_14),
            listItemSize = ListItemSize.BIG,
            leadingIconBackground = Color.Transparent,
            onClick = {
                onCurrencySelected(Currencies.RUB.code)
            },
        )
        ListItem(
            title = "Американский доллар $",
            leadingIcon = painterResource(R.drawable.usd_10),
            listItemSize = ListItemSize.BIG,
            leadingIconBackground = Color.Transparent,
            onClick = {
                onCurrencySelected(Currencies.USD.code)
            },
        )
        ListItem(
            title = "Евро €",
            leadingIcon = painterResource(R.drawable.eur_19),
            listItemSize = ListItemSize.BIG,
            leadingIconBackground = Color.Transparent,
            onClick = {
                onCurrencySelected(Currencies.EUR.code)
            },
        )
        ListItem(
            leadingIcon = painterResource(R.drawable.cancel_20),
            title = "Отмена",
            listItemSize = ListItemSize.BIG,
            leadingIconBackground = dangerAction,
            textColor = white,
            modifier = Modifier.background(color = dangerAction),
            onClick = onCancel,
        )
    }
}
