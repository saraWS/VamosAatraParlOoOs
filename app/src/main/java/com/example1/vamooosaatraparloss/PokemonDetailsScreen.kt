package com.example1.vamooosaatraparloss

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example1.vamooosaatraparloss.services.driverAdapter.PokemonDetailsDriverAdapter
import com.example1.vamooosaatraparloss.services.models.PokemonDetails
import coil.compose.AsyncImage


@Composable
fun PokemonDetailsScreen(
    pokemonName: String,
    pokemonDetailsDriverAdapter: PokemonDetailsDriverAdapter = PokemonDetailsDriverAdapter(),
    onBackClick: () -> Unit // Nuevo parámetro para manejar la navegación
) {
    val pokemonDetails = remember { mutableStateOf<PokemonDetails?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(pokemonName) {
        try {
            val details = pokemonDetailsDriverAdapter.fetchPokemonDetails(pokemonName)
            pokemonDetails.value = details
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading.value = false
        }
    }

    Scaffold { innerPadding ->
        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Botón para regresar
                Button(
                    onClick = onBackClick, // Acción de regresar
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Regresar")
                }

                // Contenido de detalles
                pokemonDetails.value?.let { details ->
                    PokemonDetailsContent(details)
                } ?: Text(
                    text = "No se pudieron cargar los detalles.",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun PokemonDetailsContent(details: PokemonDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Imagen del Pokémon
        AsyncImage(
            model = details.sprites.front_default, // URL de la imagen
            contentDescription = "Imagen de ${details.name}",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Información básica
        Text("ID: ${details.id}", style = MaterialTheme.typography.bodyLarge)
        Text("Nombre: ${details.name.capitalize()}", style = MaterialTheme.typography.bodyLarge)
        Text("Altura: ${details.height / 10.0} m", style = MaterialTheme.typography.bodyLarge)
        Text("Peso: ${details.weight / 10.0} kg", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        // Tipos
        Text("Tipos:", style = MaterialTheme.typography.headlineSmall)
        details.types.forEach { type ->
            Text("- ${type.type.name.capitalize()}", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Movimientos
        Text("Ataques:", style = MaterialTheme.typography.headlineSmall)
        details.moves.take(10).forEach { move -> // Mostrar solo los primeros 10 movimientos
            Text("- ${move.move.name.capitalize()}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}