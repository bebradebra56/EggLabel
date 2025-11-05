package com.egsi.labsi.sog.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecoderScreen(
    viewModel: com.egsi.labsi.sog.viewmodel.EggLabelViewModel,
    initialCode: String = ""
) {
    var codeInput by remember { mutableStateOf(initialCode) }
    val decodedEgg = viewModel.currentDecodedEgg
    
    // Decode initial code if provided
    LaunchedEffect(initialCode) {
        if (initialCode.isNotBlank()) {
            viewModel.decodeEggCode(initialCode)
        }
    }
    
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) DarkBackground else CreamBackground)
    ) {
        TopAppBar(
            title = { 
                Text(
                    "Decode Egg Code",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = if (isDarkMode) DarkCardBackground else CardBackground
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Input Section
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Enter Egg Code",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    OutlinedTextField(
                        value = codeInput,
                        onValueChange = { codeInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g., 0DE12345 or 1UK67890") },
                        leadingIcon = { Icon(Icons.Default.Egg, contentDescription = null) },
                        trailingIcon = {
                            if (codeInput.isNotEmpty()) {
                                IconButton(onClick = { codeInput = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                    Button(
                        onClick = {
                            viewModel.decodeEggCode(codeInput)
                        },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isDarkMode) DarkGreenFresh else GreenFresh
            ),
                            shape = RoundedCornerShape(12.dp),
                            enabled = codeInput.isNotBlank()
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Decode")
                        }

                    }
                }
            }
            
            // Results Section
            AnimatedVisibility(
                visible = decodedEgg != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                decodedEgg?.let { egg ->
                    EggCodeResultCard(egg, viewModel)
                }
            }
            
            // Instructions Card
            InstructionsCard()
        }
    }
}

@Composable
fun EggCodeResultCard(
    eggCode: EggCode,
    viewModel: com.egsi.labsi.sog.viewmodel.EggLabelViewModel
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkCardBackground else CardBackground
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Decode Results",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                StatusBadge(eggCode.status)
            }
            
            HorizontalDivider(color = if (isDarkMode) DarkDividerColor else DividerColor)
            
            // Code Display
            ResultItem(
                icon = Icons.Default.Egg,
                label = "Code",
                value = eggCode.code,
                valueColor = TextPrimary
            )
            
            // Category
            ResultItem(
                icon = Icons.Default.Category,
                label = "Category",
                value = eggCode.category.displayName,
                description = eggCode.category.description
            )
            
            // Housing Type
            ResultItem(
                icon = Icons.Default.Home,
                label = "Housing Type",
                value = eggCode.housingType.displayName,
                description = eggCode.housingType.description,
                valueColor = when (eggCode.housingType) {
                    HousingType.ORGANIC -> GreenFresh
                    HousingType.FREE_RANGE -> GreenFresh
                    else -> TextPrimary
                }
            )
            
            // Country
            ResultItem(
                icon = Icons.Default.Public,
                label = "Country",
                value = eggCode.country
            )
            
            // Producer
            ResultItem(
                icon = Icons.Default.Factory,
                label = "Producer",
                value = eggCode.producer
            )
            
            // Expiry Date
            eggCode.expiryDate?.let { date ->
                ResultItem(
                    icon = Icons.Default.CalendarToday,
                    label = "Expiry Date",
                    value = dateFormat.format(date),
                    valueColor = when (eggCode.status) {
                        EggStatus.FRESH -> if (isDarkMode) DarkGreenFresh else GreenFresh
                        EggStatus.EXPIRING -> if (isDarkMode) DarkYellowAccent else YellowAccent
                        EggStatus.EXPIRED -> if (isDarkMode) DarkRedWarning else RedWarning
                    }
                )
            }
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { 
                        viewModel.addToFavorites(eggCode)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Save")
                }

            }
        }
    }
}

@Composable
fun ResultItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    description: String? = null,
    valueColor: androidx.compose.ui.graphics.Color = TextPrimary
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isDarkMode) DarkGreenFresh else GreenFresh,
            modifier = Modifier.size(24.dp)
        )
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                label,
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = valueColor
            )
            description?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    it,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
fun InstructionsCard() {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkYellowAccent.copy(alpha = 0.2f) else YellowAccent.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = if (isDarkMode) DarkYellowAccent else YellowAccent
                )
                Text(
                    "How to Find the Code",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Text(
                "• Look for printed numbers on the egg shell",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "• Check the package label for batch codes",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "• Usually starts with 0-3 followed by country code",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "• Example format: 0DE12345 or 1UK67890",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

