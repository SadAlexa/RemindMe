package com.gpluslf.remindme.search.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.search.model.SearchBarState
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(modifier: Modifier = Modifier, state: SearchBarState) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 30.dp),
                expandedHeight = 80.dp,
                title = {
                    Text(
                        "Results", style = MaterialTheme.typography.headlineLarge
                    )
                }
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding).padding(horizontal = 20.dp)) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

            }
            FloatingSearchBar(state)
        }
    }
}

@Composable
fun FloatingSearchBar(state: SearchBarState) {
    TextField(
        value = "",
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = { },
        placeholder = { Text("Search...") },
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                Icons.Outlined.Menu,
                contentDescription = "Options",
            )
        },
        trailingIcon = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = "Search",
            )
        }
    )

}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun SearchScreenPreviewLight() {
    RemindMeTheme {
        Scaffold  { padding ->
            SearchScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                state = SearchBarState("papopepo"))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SearchScreenPreviewDark() {
    RemindMeTheme {
        Scaffold  { padding ->
            SearchScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                state = SearchBarState("papopepo"))
        }
    }
}