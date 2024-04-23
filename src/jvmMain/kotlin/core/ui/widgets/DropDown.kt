package core.ui.widgets

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import java.awt.Cursor
import java.util.*


@Composable
fun <T> DropDown(dropdownItems: List<DropDownItem<T>>) {
    var mExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(-1) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    LaunchedEffect(dropdownItems) {
        if (dropdownItems.isNotEmpty()) selectedValue = 0
    }

    Column {
        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = MaterialTheme.colors.onSurface,
                backgroundColor = MaterialTheme.colors.surface
            ),
            value = if (selectedValue == -1) "Please select camera first" else dropdownItems[selectedValue].label,
            readOnly = true,
            enabled = false,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mExpanded = !mExpanded }
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = { Text("Camera") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))).clickable { mExpanded = !mExpanded })
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(
                with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            dropdownItems.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedValue = item.value.toString().toIntOrNull() ?: 0
                    mExpanded = false
                }) {
                    Text(text = item.label)
                }
            }
        }
    }
}


data class DropDownItem<T>(
    val id: String,
    val label: String,
    val value: T
)

@Composable
@Preview
private fun Preview() {
    MaterialTheme {
        Surface {
            Column(Modifier.fillMaxSize()) {
                DropDown<String>(
                    dropdownItems = listOf(
                        DropDownItem(id = UUID.randomUUID().toString(), label = "Item 0", value = "-"),
                        DropDownItem(id = UUID.randomUUID().toString(), label = "Item 1", value = "-"),
                        DropDownItem(id = UUID.randomUUID().toString(), label = "Item 2", value = "-"),
                    )
                )
            }
        }
    }
}