package com.egsi.labsi.sog.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egsi.labsi.sog.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: com.egsi.labsi.sog.viewmodel.EggLabelViewModel
) {
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showClearDataDialog by remember { mutableStateOf(false) }
    
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground

    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) DarkBackground else CreamBackground)
    ) {
        TopAppBar(
            title = { 
                Text(
                    "Settings",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = if (isDarkMode) DarkCardBackground else CardBackground
            )
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // General Settings
            item {
                Text(
                    "General",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
            
            item {
                Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    SettingSwitchItem(
                        icon = Icons.Default.DarkMode,
                        title = "Dark Mode",
                        subtitle = "Use dark theme",
                        checked = viewModel.isDarkMode,
                        onCheckedChange = { viewModel.updateDarkMode(it) }
                    )
                }
            }
            
            // Notifications
//            item {
//                Text(
//                    "Notifications",
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.SemiBold,
//                    color = TextSecondary,
//                    modifier = Modifier.padding(horizontal = 4.dp)
//                )
//            }
//
//            item {
//                Card(
//        colors = CardDefaults.cardColors(
//            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
//        ),
//                    shape = RoundedCornerShape(12.dp)
//                ) {
//                    Column {
//                        SettingSwitchItem(
//                            icon = Icons.Default.Notifications,
//                            title = "Push Notifications",
//                            subtitle = "Get notified about expiry dates",
//                            checked = viewModel.notificationsEnabled,
//                            onCheckedChange = { viewModel.updateNotifications(it) }
//                        )
//                    }
//                }
//            }
            
            // Data & Privacy
            item {
                Text(
                    "Data & Privacy",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
            
            item {
                Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    SettingItem(
                        icon = Icons.Default.Delete,
                        title = "Clear History",
                        subtitle = "Remove all saved checks",
                        onClick = { showClearDataDialog = true }
                    )
                }
            }
            
            // About
            item {
                Text(
                    "About",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
            
            item {
                Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column {
                        SettingItemAbout(
                            icon = Icons.Default.Info,
                            title = "Version",
                            subtitle = "1.0.0",
                        )
                        
                        HorizontalDivider(color = DividerColor)
                        
                        SettingItem(
                            icon = Icons.Default.Description,
                            title = "Privacy Policy",
                            subtitle = "Read our privacy policy",
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://egglabbell.com/privacy-policy.html"))
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
            
            // Footer
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "EggLabel",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Made with ❤️ for egg lovers",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }
    }
    
    // Language Selection Dialog
                    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Select Language") },
            text = {
                Column {
                    listOf("English", "Deutsch", "Français", "Español", "Italiano").forEach { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateLanguage(language)
                                    showLanguageDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = viewModel.selectedLanguage == language,
                                onClick = {
                                    viewModel.updateLanguage(language)
                                    showLanguageDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(language)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Clear Data Confirmation Dialog
    if (showClearDataDialog) {
        AlertDialog(
            onDismissRequest = { showClearDataDialog = false },
            title = { Text("Clear All Data") },
            text = { Text("This will remove all your recent checks and favorites. This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearAllData()
                        showClearDataDialog = false
                    }
                ) {
                    Text("Clear", color = if (isDarkMode) DarkRedWarning else RedWarning)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDataDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SettingItemAbout(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GreenFresh,
            modifier = Modifier.size(24.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GreenFresh,
            modifier = Modifier.size(24.dp)
        )
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = if (isDarkMode) DarkTextSecondary else TextSecondary
        )
    }
}

@Composable
fun SettingSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GreenFresh,
            modifier = Modifier.size(24.dp)
        )
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = if (isDarkMode) DarkCardBackground else CardBackground,
                checkedTrackColor = if (isDarkMode) DarkGreenFresh else GreenFresh
            )
        )
    }
}

