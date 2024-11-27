
package com.example1.vamooosaatraparloss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example1.vamooosaatraparloss.services.driverAdapter.PokemonDetailsDriverAdapter
import com.example1.vamooosaatraparloss.services.driverAdapter.RegionDriverAdapter
import com.example1.vamooosaatraparloss.services.driverAdapter.PokemonDriverAdapter
import com.example1.vamooosaatraparloss.ui.theme.VamooosaAtraparlossTheme
import com.example1.vamooosaatraparloss.services.models.Region
import com.example1.vamooosaatraparloss.services.models.Pokemon
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val regionDriverAdapter by lazy { RegionDriverAdapter() }
    private val pokemonDriverAdapter by lazy { PokemonDriverAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VamooosaAtraparlossTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    regionDriverAdapter = regionDriverAdapter,
                    pokemonDriverAdapter = pokemonDriverAdapter
                )
            }
        }
    }
}


@Composable
fun PokemonApp(
    modifier: Modifier = Modifier,
    regionDriverAdapter: RegionDriverAdapter,
    pokemonDriverAdapter: PokemonDriverAdapter,
    onPokemonClick: (String) -> Unit
) {
    var selectedRegion by remember { mutableStateOf<Region?>(null) }
    var pokemons by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var regions by remember { mutableStateOf<List<Region>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        regions = regionDriverAdapter.fetchRegions()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)

    ) {
        if (selectedRegion == null) {
            // Mostrar regiones
            Text(
                text = "Pokédex",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Selecciona una región para ver los Pokémon:",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            RegionList(regions = regions, onRegionSelected = { region ->
                selectedRegion = region
                isLoading = true
                coroutineScope.launch {
                    try {
                        pokemons = pokemonDriverAdapter.fetchPokemonByRegion(region.id.toString())
                    } catch (e: Exception) {
                        pokemons = emptyList()
                    } finally {
                        isLoading = false
                    }
                }

            })
        } else {
            // Mostrar Pokémon y botón para regresar
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = { selectedRegion = null },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Volver a las regiones")
            }

            if (isLoading) {
                Text("Cargando Pokémon...")
            } else {
                Text(
                    text = "Pokémon en la región ${selectedRegion?.name}:",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                PokemonList(
                    pokemons = pokemons,
                    onPokemonClick = onPokemonClick
                )
            }
        }
    }
}




@Composable
fun RegionList(regions: List<Region>, onRegionSelected: (Region) -> Unit) {
    if (regions.isEmpty()) {
        Text(text = "Cargando regiones...", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(regions) { region ->
                RegionItem(region = region, onClick = { onRegionSelected(region) })
            }
        }
    }
}

@Composable
fun RegionItem(region: Region, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = region.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSecondary)
        )
    }
}




@Composable
fun PokemonList(pokemons: List<Pokemon>, onPokemonClick: (String) -> Unit) {
    if (pokemons.isEmpty()) {
        Text(text = "Cargando Pokémon...", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pokemons) { pokemon ->
                PokemonItem(pokemon = pokemon, onClick = { onPokemonClick(pokemon.name) })
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = pokemon.name.capitalize(),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSecondary)
        )
    }
}



@Composable
fun AppNavigation(
    navController: NavHostController,
    regionDriverAdapter: RegionDriverAdapter,
    pokemonDriverAdapter: PokemonDriverAdapter
) {
    val pokemonDetailsDriverAdapter = PokemonDetailsDriverAdapter()

    NavHost(
        navController = navController,
        startDestination = "pokemonList"
    ) {
        composable("pokemonList") {
            PokemonApp(
                regionDriverAdapter = regionDriverAdapter,
                pokemonDriverAdapter = pokemonDriverAdapter,
                onPokemonClick = { pokemonName ->
                    navController.navigate("pokemonDetails/$pokemonName")
                }
            )
        }
        composable("pokemonDetails/{pokemonName}") { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: "Desconocido"
            PokemonDetailsScreen(
                pokemonName = pokemonName,
                pokemonDetailsDriverAdapter = pokemonDetailsDriverAdapter,
                onBackClick = { navController.popBackStack() } // Acción de regresar
            )
        }

    }
}