package com.egsi.labsi.sog.screen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egsi.labsi.sog.model.*
import com.egsi.labsi.sog.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: com.egsi.labsi.sog.viewmodel.EggLabelViewModel
) {
    val favorites by viewModel.favorites.collectAsState()
    
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) DarkBackground else CreamBackground)
    ) {
        TopAppBar(
            title = { 
                Text(
                    "Favorites",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = if (isDarkMode) DarkCardBackground else CardBackground
            )
        )
        
        if (favorites.isEmpty()) {
            EmptyFavoritesState()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) DarkYellowAccent.copy(alpha = 0.2f) else YellowAccent.copy(alpha = 0.2f)
        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = if (isDarkMode) DarkYellowAccent else YellowAccent
                            )
                            Text(
                                "Save your trusted producers for quick access",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                
            items(favorites) { favorite ->
                FavoriteProducerCard(
                    favorite = favorite,
                    onRemove = { viewModel.removeFromFavorites(favorite.id) }
                )
            }
            }
        }
    }
}

@Composable
fun EmptyFavoritesState() {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = if (isDarkMode) DarkTextSecondary.copy(alpha = 0.5f) else TextSecondary.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "No Favorites Yet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Start adding your trusted egg producers to access them quickly",
            style = MaterialTheme.typography.bodyLarge,
            color = if (isDarkMode) DarkTextSecondary else TextSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FavoriteProducerCard(
    favorite: FavoriteProducer,
    onRemove: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val isDarkMode = MaterialTheme.colorScheme.background == DarkBackground
    
    Card(
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    color = GreenFresh.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Factory,
                        contentDescription = null,
                        tint = if (isDarkMode) DarkGreenFresh else GreenFresh,
                        modifier = Modifier
                            .padding(12.dp)
                            .size(32.dp)
                    )
                }
                
                Column {
                    Text(
                        favorite.producer,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Public,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (isDarkMode) DarkTextSecondary else TextSecondary
                        )
                        Text(
                            favorite.country,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Added ${dateFormat.format(favorite.addedDate)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
            
            IconButton(
                onClick = onRemove
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Remove from favorites",
                    tint = if (isDarkMode) DarkRedWarning else RedWarning
                )
            }
        }
    }
}

// No longer needed - using real data from ViewModel

