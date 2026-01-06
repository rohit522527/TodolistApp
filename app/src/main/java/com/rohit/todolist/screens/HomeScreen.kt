package com.rohit.todolist.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import com.rohit.todolist.R
import com.rohit.todolist.localdatabase.TaskEntity
import com.rohit.todolist.navigation.Routs
import com.rohit.todolist.ui.theme.appFont
import com.rohit.todolist.viewmodels.TaskViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: TaskViewModel) {
    val tasks = viewModel.tasks.collectAsState().value
    var selectedTask by remember { mutableStateOf<TaskEntity?>(null) }
    var showAlertDialog by remember { mutableStateOf(false) }
    var showPopUp by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routs.AddTaskScreen) {
                        popUpTo(Routs.HomeScreen)
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("TodoList", fontFamily = appFont, fontSize = 32.sp) }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(tasks) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(if (it.isCompleted) 0xFFDCEBFF else 0xFFFFCDCD)
                    ),
                    elevation = CardDefaults.cardElevation(4.dp),
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = if (it.isCompleted) R.drawable.check else R.drawable.wall_clock),
                                contentDescription = "completeTaskImage",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    it.title,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = appFont
                                )
                                Text(it.description, color = Color.DarkGray)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "EditTask", modifier = Modifier.clickable{
                                selectedTask=it
                                showPopUp=true
                            })
                        }
                        Divider(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            color = Color.Gray,
                            thickness = 1.dp
                        )
                        Spacer(Modifier.height(5.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "",
                                modifier = Modifier.size(32.dp)
                            )
                            Text("Due: ${getTimeLeft(it.duoDate,it.createdAt)}")
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "",
                                tint = Color(
                                    0xFFEACC05
                                ),
                                modifier = Modifier.size(28.dp)
                            )
                            Text("High priority")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                tint = Color.Red,
                                modifier = Modifier.size(32.dp)
                                    .clickable{
                                        selectedTask=it
                                       showAlertDialog=true
                                    }
                            )
                        }
                    }
                }

            }
        }
        if (showPopUp) {
            Dialog(onDismissRequest = {
                showPopUp=false
                selectedTask = null
            }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                   selectedTask?.let {
                       Text(it.title)
                   }
                    Button(
                        onClick = {
                            viewModel.toggleTask(selectedTask!!)
                        }
                    ) { }
                }
            }
        }
        if (showAlertDialog){
            AlertDialog(
                onDismissRequest = {showAlertDialog=false},
                title = {Text("Are you sure want to delete this task")},
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteTask(selectedTask!!)
                            showAlertDialog=false
                            selectedTask=null
                        }
                    ) {
                        Text("Yas")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showAlertDialog=false
                            selectedTask=null
                        }
                    ) {
                        Text("No")
                    }
                }
            )
        }

    }
}

fun formatTime(millisecons: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d,yyy")
    return Instant.ofEpochMilli(millisecons)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}
fun getTimeLeft(targetMillis: Long,currentMilliseconds: Long): String {
    val now = currentMilliseconds
    val diff = targetMillis - now

    if (diff <= 0) return "Expired"

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "$days day${if (days > 1) "s" else ""} left"
        hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} left"
        minutes > 0 -> "$minutes min left"
        else -> "Few seconds left"
    }
}
