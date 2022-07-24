package com.example.ui_user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.theme.*

@Composable
fun AboutMeScreen() {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.txt_about_me),
            style = MaterialTheme.typography.x3_bold,
            color = MaterialTheme.colors.textPrimary
        )
    }
}
