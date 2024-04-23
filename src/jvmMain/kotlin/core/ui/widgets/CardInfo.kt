package core.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CardInfo(label: String, value: Int, icon: ImageVector) {
    Card {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.width(224.dp)) {
                Text(
                    label,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight(700)),
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .border(
                            border = BorderStroke(2.dp, color = Color.Black),
                            shape = CircleShape
                        )
                        .padding(8.dp)
                )
            }
            Text(value.toString(), style = MaterialTheme.typography.h3, modifier = Modifier.align(Alignment.Bottom))
        }
    }
}