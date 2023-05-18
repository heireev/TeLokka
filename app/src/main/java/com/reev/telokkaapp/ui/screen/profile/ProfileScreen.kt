package com.reev.telokkaapp.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.reev.telokkaapp.R
import com.reev.telokkaapp.ui.theme.TeLokkaAppTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(stringResource(R.string.menu_profile))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    TeLokkaAppTheme {
        ProfileScreen()
    }
}