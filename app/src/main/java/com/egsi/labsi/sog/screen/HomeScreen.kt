package com.egsi.labsi.sog.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egsi.labsi.sog.model.*
import com.egsi.labsi.sog.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: com.egsi.labsi.sog.viewmodel.EggLabelViewModel,
    onNavigateToDecoder: (String) -> Unit,
    onNavigateToEncyclopedia: () -> Unit
) {
    val recentChecks by viewModel.recentChecks.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) DarkBackground else CreamBackground)
    ) {
        // Top App Bar
        TopAppBar(
            title = { 
                Text(
                    "EggLabel",
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
            // Welcome Card
            item {
                WelcomeCard()
            }
            
            // Search Section
            item {
                SearchSection(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    onSearch = { 
                        if (searchQuery.isNotBlank()) {
                            onNavigateToDecoder(searchQuery)
                        }
                    }
                )
            }
            
            // Quick Actions
//            item {
//                QuickActionsSection(
//                    onScanClick = { /* TODO: Implement camera scan */ },
//                    onGuideClick = onNavigateToEncyclopedia,
//                    onInfoClick = { /* TODO: Show info dialog */ }
//                )
//            }
            
            // Recent Checks
            item {
                Text(
                    "Recent Checks",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(recentChecks) { check ->
                RecentCheckCard(check)
            }
        }
    }
}

@Composable
fun WelcomeCard() {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkYellowAccent else YellowAccent
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Egg,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                tint = if (isDarkMode) DarkTextPrimary else TextPrimary
            )
            Column {
                Text(
                    "Welcome to EggLabel!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkMode) DarkTextPrimary else TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Decode egg markings and learn about egg quality",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDarkMode) DarkTextPrimary else TextPrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Check Egg Code",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter code from egg or package") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = if (isDarkMode) DarkCardBackground else CardBackground,
                unfocusedContainerColor = if (isDarkMode) DarkCardBackground else CardBackground
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        
        Button(
            onClick = onSearch,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isDarkMode) DarkGreenFresh else GreenFresh
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = searchQuery.isNotBlank()
        ) {
            Icon(Icons.Default.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Decode Code",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun QuickActionsSection(
    onScanClick: () -> Unit,
    onGuideClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Column {
        Text(
            "Quick Actions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionButton(
                icon = Icons.AutoMirrored.Filled.MenuBook,
                label = "Guide",
                onClick = onGuideClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = if (isDarkMode) DarkGreenFresh else GreenFresh
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RecentCheckCard(check: CheckHistory) {
    val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    check.code,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "${check.eggCode.producer} • ${check.eggCode.country}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDarkMode) DarkTextSecondary else TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    dateFormat.format(check.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDarkMode) DarkTextSecondary else TextSecondary
                )
            }
            
            StatusBadge(check.eggCode.status)
        }
    }
}

@Composable
fun StatusBadge(status: EggStatus) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    val (color, text) = when (status) {
        EggStatus.FRESH -> (if (isDarkMode) DarkGreenFresh else GreenFresh) to "Fresh"
        EggStatus.EXPIRING -> (if (isDarkMode) DarkYellowAccent else YellowAccent) to "Expiring"
        EggStatus.EXPIRED -> (if (isDarkMode) DarkRedWarning else RedWarning) to "Expired"
    }
    
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

// No longer needed - using real data from ViewModel

