package az.autumn.myhome.ui.page

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import az.autumn.myhome.R
import az.autumn.myhome.data.repository.SettingsRepository
import az.autumn.myhome.viewmodel.SettingsViewModel
import az.autumn.myhome.viewmodel.SettingsViewModelFactory
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Github
import compose.icons.fontawesomeicons.brands.Telegram
import compose.icons.fontawesomeicons.solid.Globe

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPage(
    viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(SettingsRepository(LocalContext.current))
    ),
) {
    val backendUrl by viewModel.backendUrl.collectAsState()

    var isEditingBackendUrl by remember { mutableStateOf(false) }
    var editingText by remember { mutableStateOf(backendUrl) }
    var showAboutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        SettingItem(
            title = "Backend URL",
            description = if (!isEditingBackendUrl) backendUrl else null,
            icon = Icons.Outlined.Info,
            onClick = {
                isEditingBackendUrl = true
                editingText = backendUrl
            },
            trailing = {
                if (isEditingBackendUrl) {
                    // 当编辑状态下，显示一个保存按钮
                    IconButton(onClick = {
                        viewModel.updateBackendUrl(editingText)
                        viewModel.saveBackendUrl()
                        isEditingBackendUrl = false
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Save"
                        )
                    }
                }
            }
        )

        if (isEditingBackendUrl) {
            // 显示一个TextField以编辑URL
            TextField(
                value = editingText,
                onValueChange = { editingText = it },
                label = { Text("Input Backend URL") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 这里还可以添加其他设置项，例如主题选择、语言选择
        SettingItem(
            title = "Theme",
            description = "Light/Dark mode",
            icon = Icons.Outlined.Check,
            onClick = { /* 打开主题选择弹窗 */ }
        )

        SettingItem(
            title = "Language",
            description = "Change app language",
            icon = Icons.Outlined.LocationOn,
            onClick = { /* 打开语言选择对话框 */ }
        )

        SettingItem(
            title = "Others",
            description = "Change app language",
            icon = Icons.Outlined.Build,
            onClick = { /* 打开语言选择对话框 */ }
        )


        SettingItem(
            title = "About",
            description = "App info",
            icon = Icons.Outlined.Info,
            onClick = { showAboutDialog = true }
        )
    }
    if (showAboutDialog) {
        AboutDialog(context = context, onDismiss = { showAboutDialog = false })
    }
}


@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            if (!description.isNullOrEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
        trailing?.invoke()
    }
}

@Composable
fun AboutDialog(context: Context, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Surface(
                modifier = Modifier,
                shape = RoundedCornerShape(24.dp),
                onClick = { onDismiss() }
            ) {
                Column(
                    modifier = Modifier
                        .width(350.dp)
                        .height(500.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.mipmap.ic_launcher_foreground),
                        contentDescription = ""
                    )
                    Text(
                        text = "My Home",
                        textAlign = TextAlign.Right,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 38.sp
                        ),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    Row() {
                        IconButton(
                            onClick = {
                                ContextCompat.startActivity(
                                    context, Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://baidu.com/")
                                    ), null
                                )
                            },
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Globe,
                                contentDescription = "like",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.9f
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        IconButton(
                            onClick = {
                                ContextCompat.startActivity(
                                    context, Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://github.com/aixiao0621/")
                                    ), null
                                )
                            },
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Brands.Github,
                                contentDescription = "github",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.9f
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        IconButton(
                            onClick = {
                                ContextCompat.startActivity(
                                    context,
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://baidu.com")
                                    ), null
                                )
                            },
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Brands.Telegram,
                                contentDescription = "telegram",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.9f
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

        }
    }
}