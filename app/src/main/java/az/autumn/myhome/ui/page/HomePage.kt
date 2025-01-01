package az.autumn.myhome.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import az.autumn.myhome.viewmodel.HomeViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.DoorClosed
import compose.icons.fontawesomeicons.solid.DoorOpen
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.Lightbulb

@Composable
fun HomePage(viewModel: HomeViewModel) {
    val deviceState by viewModel.deviceState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDeviceState()
    }

   Box(modifier = Modifier){
       LazyColumn(
           modifier = Modifier.fillMaxSize(),
           verticalArrangement = Arrangement.spacedBy(16.dp),
           contentPadding = PaddingValues(16.dp)
       ) {
           item {
               Text(
                   text = "Temperature: ${deviceState?.temp ?: "--"}°C",
                   style = MaterialTheme.typography.headlineLarge
               )
           }

           item {
               LightControl(
                   label = "Light 1",
                   checked = deviceState?.light1 == true,
                   onCheckedChange = { isChecked ->
                       val newState = deviceState?.copy(light1 = isChecked)
                       if (newState != null) {
                           viewModel.updateDeviceState(newState)
                       }
                   }
               )
           }

           item {
               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Text(text = "Light 2", style = MaterialTheme.typography.bodyLarge)
                   Spacer(modifier = Modifier.weight(1f))
                   Switch(
                       checked = deviceState?.light2 == true,
                       onCheckedChange = { isChecked ->
                           val newState = deviceState?.copy(light2 = isChecked)
                           if (newState != null) {
                               viewModel.updateDeviceState(newState)
                           }
                       }
                   )
               }
           }

           item {
               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Icon(imageVector = Icons.Rounded.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                   Spacer(modifier = Modifier.padding(8.dp))
                   Text(text = "Door", style = MaterialTheme.typography.bodyLarge)
                   Spacer(modifier = Modifier.weight(1f))
                   Switch(
                       checked = deviceState?.door == true,
                       onCheckedChange = { isChecked ->
                           val newState = deviceState?.copy(door = if (isChecked) true else false)
                           if (newState != null) {
                               viewModel.updateDeviceState(newState)
                           }
                       }
                   )
               }
           }
           item {
               Row {
                   SmallControlCard(
                       title = "Main Light",
                       description = "Room",
                       icon = FontAwesomeIcons.Solid.Lightbulb,
                       isChecked = deviceState?.light2 == true,
                       onCheckedChange = { isChecked ->
                           val newState = deviceState?.copy(light2 = isChecked)
                           if (newState != null) {
                               viewModel.updateDeviceState(newState)
                           }
                       },
                       modifier = Modifier.padding(6.dp)
                   )
                   Spacer(modifier = Modifier.width(8.dp))
                   SmallControlCard(
                       title = "Bedside",
                       description = "Room",
                       icon = FontAwesomeIcons.Solid.Lightbulb,
                       isChecked = deviceState?.light1 == true,
                       onCheckedChange = { isChecked ->
                           val newState = deviceState?.copy(light1 = isChecked)
                           if (newState != null) {
                               viewModel.updateDeviceState(newState)
                           }
                       },
                       modifier = Modifier.padding(6.dp)
                   )
               }
           }
           item {
               Row {
                   SmallControlCard(
                       title = "Main Light",
                       description = "Room",
                       icon = FontAwesomeIcons.Solid.Lightbulb,
                       isChecked = deviceState?.light2 == true,
                       onCheckedChange = { isChecked ->
                           val newState = deviceState?.copy(light2 = isChecked)
                           if (newState != null) {
                               viewModel.updateDeviceState(newState)
                           }
                       },
                       modifier = Modifier.padding(6.dp)
                   )
                   Spacer(modifier = Modifier.width(8.dp))
                   SmallControlCard(
                       title = "Bedside",
                       description = "Room",
                       icon = FontAwesomeIcons.Solid.Lightbulb,
                       isChecked = deviceState?.light1 == true,
                       onCheckedChange = { isChecked ->
                           val newState = deviceState?.copy(light1 = isChecked)
                           if (newState != null) {
                               viewModel.updateDeviceState(newState)
                           }
                       },
                       modifier = Modifier.padding(6.dp)
                   )
               }
           }

           item {
               BigControlCard(
                   title = "Main Door",
                   description = "Door",
                   icon = FontAwesomeIcons.Solid.DoorOpen,
                   isChecked = deviceState?.door == true,
                   onCheckedChange = { isChecked ->
                       val newState = deviceState?.copy(door = isChecked)
                       if (newState != null) {
                           viewModel.updateDeviceState(newState)
                       }
                   },
                   modifier = Modifier.padding(6.dp)
               )
           }
       }

   }
}

@Composable
fun LightControl(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SmallControlCard(
    title: String,
    description: String?,
    icon: ImageVector?,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .width(180.dp)
            .height(120.dp)
            .clickable {
                onCheckedChange(!isChecked)
            }
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (!description.isNullOrBlank()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

            }

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp)
            )
        }
    }
}

@Composable
fun BigControlCard(
    title: String,
    description: String?,
    icon: ImageVector?,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .width(380.dp)
            .height(220.dp)
            .clickable {
                onCheckedChange(!isChecked)
            }
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (!description.isNullOrBlank()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

            }

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp)
            )
        }
    }
}

/**
 * @Composable
 * fun ElevatedCardExample() {
 *     ElevatedCard(
 *         elevation = CardDefaults.cardElevation(
 *             defaultElevation = 6.dp
 *         ),
 *         modifier = Modifier
 *             .(width = 240.dp, height = 100.dp)
 *     ) {
 *         Text(
 *             text = "Elevated",
 *             modifier = Modifier
 *                 .padding(16.dp),
 *             textAlign = TextAlign.Center,
 *         )
 *     }
 * }
 */

//@Composable
//fun BooleanControl(deviceState:DeviceState,viewModel: HomeViewModel){
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(text = "Light 1", style = MaterialTheme.typography.bodyLarge)
//        Spacer(modifier = Modifier.weight(1f))
//        Switch(
//            checked = deviceState?.light1 == true,
//            onCheckedChange = { isChecked ->
//                val newState = deviceState?.copy(light1 = isChecked)
//                if (newState != null) {
//                    viewModel.updateDeviceState(newState)
//                }
//            }
//        )
//    }
//}