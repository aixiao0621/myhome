package az.autumn.myhome.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
            style = MaterialTheme.typography.titleLarge
        )

        // Light 1 Control
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Light 1", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = deviceState?.light1 == "1",
                onCheckedChange = { isChecked ->
                    val newState = deviceState?.copy(light1 = if (isChecked) "1" else "0")
                    if (newState != null) {
                        viewModel.updateDeviceState(newState)
                    }
                }
            )
        }

        // Light 2 Control
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Light 2", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = deviceState?.light2 == "1",
                onCheckedChange = { isChecked ->
                    val newState = deviceState?.copy(light2 = if (isChecked) "1" else "0")
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
            Text(text = "Door", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = deviceState?.door == "1",
                onCheckedChange = { isChecked ->
                    val newState = deviceState?.copy(door = if (isChecked) "1" else "0")
                    if (newState != null) {
                        viewModel.updateDeviceState(newState)
                    }
                }
            )
        }
    }
}