package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R

@Composable
fun TraverseeAlertDialog(
    onDismissRequest: () -> Unit = {},
    title: String,
    text: String,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton ={
            TraverseeButton(
                modifier = Modifier.clip(RoundedCornerShape(100.dp)),
                onClick = onConfirm,
                text = stringResource(id = R.string.confirm),
            )
        },
        dismissButton = {
            TraverseeOutlinedButton(
                modifier = Modifier.clip(RoundedCornerShape(100.dp)),
                onClick = onCancel,
                text = stringResource(id = R.string.cancel),
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun TraverseeAlertDialogPreview() {
    TraverseeAlertDialog(
        title = "Title",
        text = "Text",
    )
}