package com.egsi.labsi.sog.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egsi.labsi.sog.data.EggCodeRepository
import com.egsi.labsi.sog.model.*
import com.egsi.labsi.sog.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncyclopediaScreen() {
    val repository = remember { EggCodeRepository() }
    val categories = repository.getAllCategories()
    val housingTypes = repository.getAllHousingTypes()
    var selectedTab by remember { mutableStateOf(0) }
    
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) DarkBackground else CreamBackground)
    ) {
        TopAppBar(
            title = { 
                Text(
                    "Encyclopedia",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = if (isDarkMode) DarkCardBackground else CardBackground
            )
        )
        
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground,
            contentColor = if (isDarkMode) DarkGreenFresh else GreenFresh
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Categories") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Housing") }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Countries") }
            )
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    item {
                        InfoCard(
                            title = "Egg Categories",
                            description = "Eggs are classified by weight and quality standards",
                            icon = Icons.Default.Category,
                            color = YellowAccent
                        )
                    }
                    items(categories) { category ->
                        CategoryCard(category)
                    }
                }
                1 -> {
                    item {
                        InfoCard(
                            title = "Housing Types",
                            description = "Different farming methods affect egg quality and price",
                            icon = Icons.Default.Home,
                            color = GreenFresh
                        )
                    }
                    items(housingTypes) { housing ->
                        HousingCard(housing)
                    }
                }
                2 -> {
                    item {
                        InfoCard(
                            title = "Country Codes",
                            description = "ISO codes indicate the country of origin",
                            icon = Icons.Default.Public,
                            color = YellowAccent
                        )
                    }
                    item {
                        CountryCodesCard()
                    }
                }
            }
            
            item {
                StorageTipsCard()
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(40.dp)
            )
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDarkMode) DarkTextSecondary else TextSecondary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: EggCategory) {
    var expanded by remember { mutableStateOf(false) }
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    Card(
        onClick = { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = YellowAccent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            category.code,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = YellowAccent
                        )
                    }
                    Text(
                        category.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }
            
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = if (isDarkMode) DarkDividerColor else DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        category.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isDarkMode) DarkTextSecondary else TextSecondary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HousingCard(housing: HousingType) {
    var expanded by remember { mutableStateOf(false) }
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    val color = when (housing) {
        HousingType.ORGANIC, HousingType.FREE_RANGE -> if (isDarkMode) DarkGreenFresh else GreenFresh
        else -> if (isDarkMode) DarkTextSecondary else TextSecondary
    }
    
    Card(
        onClick = { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (housing) {
                            HousingType.ORGANIC -> Icons.Default.Eco
                            HousingType.FREE_RANGE -> Icons.Default.Grass
                            HousingType.BARN -> Icons.Default.Home
                            HousingType.CAGE -> Icons.Default.Grid4x4
                        },
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text(
                            housing.displayName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Code: ${housing.code}",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isDarkMode) DarkTextSecondary else TextSecondary
                        )
                    }
                }
                
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }
            
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = if (isDarkMode) DarkDividerColor else DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        housing.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isDarkMode) DarkTextSecondary else TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun CountryCodesCard() {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    val countries = mapOf(
        "DE" to "Germany",
        "UK" to "United Kingdom",
        "FR" to "France",
        "ES" to "Spain",
        "IT" to "Italy",
        "NL" to "Netherlands",
        "PL" to "Poland",
        "BE" to "Belgium",
        "AT" to "Austria",
        "DK" to "Denmark",
        "SE" to "Sweden",
        "IE" to "Ireland"
    )
    
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Common Country Codes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            
            countries.forEach { (code, country) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        code,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkMode) DarkGreenFresh else GreenFresh
                    )
                    Text(
                        country,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isDarkMode) DarkTextPrimary else TextPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun StorageTipsCard() {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkGreenFresh.copy(alpha = 0.15f) else GreenFresh.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = if (isDarkMode) DarkGreenFresh else GreenFresh
                )
                Text(
                    "Storage Tips",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Text(
                "• Store eggs in the refrigerator at 4°C (40°F) or below",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "• Keep eggs in their original carton",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "• Don't wash eggs before storing",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "• Use within 3-5 weeks of purchase date",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "• Store eggs with the pointed end down",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

