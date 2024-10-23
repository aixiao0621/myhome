package az.autumn.myhome.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
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
import androidx.compose.ui.unit.dp
import az.autumn.myhome.data.model.DeviceState
import az.autumn.myhome.viewmodel.HomeViewModel

@Composable
fun HomePage(viewModel: HomeViewModel) {
    val deviceState by viewModel.deviceState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDeviceState()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Temperature: ${deviceState?.temp ?: "--"}°C",
            style = MaterialTheme.typography.headlineLarge
        )

        // Light 1 Control
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

        // Light 2 Control
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

        // Door Control
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
fun BooleanControl(deviceState:DeviceState,viewModel: HomeViewModel){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Light 1", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = deviceState?.light1 == true,
            onCheckedChange = { isChecked ->
                val newState = deviceState?.copy(light1 = isChecked)
                if (newState != null) {
                    viewModel.updateDeviceState(newState)
                }
            }
        )
    }
}