@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.nasmedia.naptracker.sample.ui

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nasmedia.naptracker.presentation.NapTracker
import com.nasmedia.naptracker.sample.DetailActivity

private enum class SampleTab(val label: String) {
    Quickstart("Quick start"),
    Examples("Examples"),
}

@Composable
fun NapTrackerSampleApp(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(SampleTab.Quickstart) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text(text = "NAP Tracker Sample") })

                TabRow(selectedTabIndex = selectedTab.ordinal) {
                    SampleTab.entries.forEach { tab ->
                        Tab(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            text = { Text(tab.label) },
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        when (selectedTab) {
            SampleTab.Quickstart -> QuickstartScreen(modifier = Modifier.padding(innerPadding))
            SampleTab.Examples -> ExamplesScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun QuickstartScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var lastAction by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            StepCard(
                title = "1) Verify SDK initialization",
                description = "The SDK initializes in Application.onCreate() (NapTrackerSampleApplication). ",
            )
        }

        item {
            StepCard(
                title = "2) Send your first event",
                description = "Tap the button below to log a sample event.",
            ) {
                Button(
                    onClick = {
                        NapTracker.logEvent("sample_event", null)
                        lastAction = "logEvent(sample_event)"
                    },
                ) {
                    Text("Log sample_event")
                }
            }
        }

        item {
            StepCard(
                title = "3) Verify automatic screen_view",
                description = "Open another Activity to trigger automatic screen_view tracking.",
            ) {
                Button(onClick = { context.startActivity(Intent(context, DetailActivity::class.java)) }) {
                    Text("Open DetailActivity")
                }
            }
        }

        if (!lastAction.isNullOrBlank()) {
            item {
                Text(
                    text = "Last action: $lastAction",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
private fun ExamplesScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var eventName by remember { mutableStateOf("sample_event") }
    var paramKey by remember { mutableStateOf("") }
    var paramValue by remember { mutableStateOf("") }
    val eventParams = remember { mutableStateListOf<Pair<String, String>>() }

    var userId by remember { mutableStateOf("") }
    var userPropKey by remember { mutableStateOf("") }
    var userPropValue by remember { mutableStateOf("") }

    val logs = remember { mutableStateListOf<String>() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = "Feature catalog. Each section calls the SDK APIs and leaves a small UI log.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        item {
            SectionTitle(text = "Log event")

            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                label = { Text("Event name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = paramKey,
                    onValueChange = { paramKey = it },
                    label = { Text("Param key") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = paramValue,
                    onValueChange = { paramValue = it },
                    label = { Text("Param value") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
                OutlinedButton(
                    onClick = {
                        if (paramKey.isNotBlank() && paramValue.isNotBlank()) {
                            eventParams.add(paramKey to paramValue)
                            paramKey = ""
                            paramValue = ""
                        }
                    },
                ) {
                    Text("Add")
                }
            }

            if (eventParams.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Params: ${eventParams.joinToString { (k, v) -> "$k=$v" }}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        val bundle = if (eventParams.isEmpty()) null else Bundle().apply {
                            eventParams.forEach { (k, v) -> putString(k, v) }
                        }
                        NapTracker.logEvent(eventName, bundle)
                        logs.add("logEvent(name=$eventName, params=${eventParams.size})")
                        eventParams.clear()
                    },
                ) {
                    Text("Log event")
                }
                OutlinedButton(
                    onClick = {
                        eventParams.clear()
                        logs.add("cleared event params")
                    },
                ) {
                    Text("Clear params")
                }
            }
        }

        item {
            SectionTitle(text = "User ID")

            OutlinedTextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("User ID") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (userId.isNotBlank()) {
                        NapTracker.setUserId(userId)
                        logs.add("setUserId($userId)")
                        userId = ""
                    }
                },
            ) {
                Text("Set user ID")
            }
        }

        item {
            SectionTitle(text = "User property")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = userPropKey,
                    onValueChange = { userPropKey = it },
                    label = { Text("Key") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = userPropValue,
                    onValueChange = { userPropValue = it },
                    label = { Text("Value") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (userPropKey.isNotBlank() && userPropValue.isNotBlank()) {
                        NapTracker.setUserProperty(userPropKey, userPropValue)
                        logs.add("setUserProperty($userPropKey=$userPropValue)")
                        userPropKey = ""
                        userPropValue = ""
                    }
                },
            ) {
                Text("Set user property")
            }
        }

        item {
            SectionTitle(text = "Screen view (auto)")
            Text(
                text = "Open another Activity to trigger automatic screen_view tracking.",
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { context.startActivity(Intent(context, DetailActivity::class.java)) }) {
                Text("Open DetailActivity")
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SectionTitle(
                    text = "Logs",
                    modifier = Modifier.weight(1f),
                )
                OutlinedButton(
                    onClick = { logs.clear() },
                ) {
                    Text("Clear")
                }
            }
        }

        items(logs.asReversed()) { log ->
            Text(text = log, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun StepCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
            content()
        }
    }
}

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
}
