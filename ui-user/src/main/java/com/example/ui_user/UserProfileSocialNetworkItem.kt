package com.example.ui_user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.theme.*
import com.example.domain_user.model.UserProfileSocialNetworkEntity

@Composable
fun UserProfileSocialNetworkItem(
    item: UserProfileSocialNetworkEntity,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item.title?.let {
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.x3_bold,
                color = MaterialTheme.colors.textPrimary
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = MaterialTheme.colors.textPrimary,
            )
        }
    }
}
