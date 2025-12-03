package com.example.f1companion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.f1companion.data.Circuit
import com.example.f1companion.data.F1ApiService
import com.example.f1companion.data.MockF1ApiService
import com.example.f1companion.ui.theme.DarkGray
import com.example.f1companion.ui.theme.LightGray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CircuitsViewModel(private val apiService: F1ApiService) : ViewModel() {
    private val _circuits = MutableStateFlow<List<Circuit>>(emptyList())
    val circuits = _circuits.asStateFlow()

    init {
        viewModelScope.launch {
            _circuits.value = apiService.getCircuits()
        }
    }

    companion object {
        fun provideFactory(apiService: F1ApiService): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CircuitsViewModel(apiService) as T
                }
            }
        }
    }
}

@Composable
fun CircuitsScreen(modifier: Modifier = Modifier) {
    val viewModel: CircuitsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = CircuitsViewModel.provideFactory(MockF1ApiService()))
    val circuits by viewModel.circuits.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(circuits) { circuit ->
            CircuitDetailCard(circuit = circuit)
        }
    }
}

@Composable
fun CircuitDetailCard(circuit: Circuit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Placeholder for Track Map Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(DarkGray, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Track Map Placeholder", color = Color.Gray, fontWeight = FontWeight.Bold)
            }

            // Circuit Name and Location
            Column(Modifier.padding(16.dp)) {
                Text(circuit.name, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text("${circuit.location}, ${circuit.country}", fontSize = 14.sp, color = Color.Gray)
            }

            HorizontalDivider(color = DarkGray, thickness = 1.dp)

            // Track Details (DRS Zones, etc.)
            Column(Modifier.padding(16.dp)) {
                Text("Track Info", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
                InfoRow(label = "DRS Zones", value = "3 (Mock Data)")
                InfoRow(label = "Corners", value = "15 (Mock Data)")
                InfoRow(label = "Length", value = "5.412 km (Mock Data)")
            }

            HorizontalDivider(color = DarkGray, thickness = 1.dp)

            // Track History Panel
            Column(Modifier.padding(16.dp)) {
                Text("History", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    "This is a placeholder for the circuit's history. You would fetch this information from Wikipedia or another source via your backend and display it here. The first Grand Prix at this circuit was held in [Year], and it has been a staple of the F1 calendar ever since, known for its iconic [feature] and memorable races.",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$label:", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.width(100.dp))
        Text(text = value, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}
