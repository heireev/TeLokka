package com.reev.telokkaapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reev.telokkaapp.ui.theme.MyRed
import com.reev.telokkaapp.ui.theme.MyWhite
import com.reev.telokkaapp.ui.theme.TeLokkaAppTheme


@Composable
fun TwoButtonsRow(
    buttonText1: String,
    buttonText2: String,
    cancelEnabled: Boolean = true,
    planningEnabled: Boolean = true,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MyButton(
            text = buttonText1,
            enabled = cancelEnabled,
            onClick = onCancelClick,
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            buttonColor = MyRed,
            textColor = MyWhite,
        )

        MyButton(
            text = buttonText2,
            enabled = planningEnabled,
            onClick = onConfirmClick,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TwoButtonsRowPreview() {
    TeLokkaAppTheme() {
        TwoButtonsRow(
            buttonText1 = "Cancel",
            buttonText2 = "Confirm",
            onCancelClick = {},
            onConfirmClick = {}
        )
    }
}