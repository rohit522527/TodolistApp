package com.rohit.todolist.screens

import android.text.BoringLayout
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.packInts
import androidx.navigation.NavHostController
import com.rohit.todolist.R
import com.rohit.todolist.localdatabase.TaskEntity
import com.rohit.todolist.ui.theme.appFont
import com.rohit.todolist.viewmodels.TaskViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavHostController,viewModel: TaskViewModel) {
    var taskTitle by remember{mutableStateOf("")}
    var taskDescription by remember{mutableStateOf("")}
    var duoDate by remember{mutableStateOf<Long?>(0L)}
    val datePickerState = rememberDatePickerState()
    val state = rememberTimePickerState(is24Hour = false)
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selecteHour by remember { mutableStateOf(0) }
    var selecteMinut by remember { mutableStateOf(0) }
    var selectedDate by remember { mutableStateOf<Long?>(0L) }
    val context = LocalContext.current
    Scaffold(

    ) {
        Column(Modifier
            .fillMaxSize()
            .padding(it)
            .padding(20.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(40.dp))
                Text("Add New Task", fontSize = 26.sp, fontFamily = appFont, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(50.dp))
            Text("Task title", fontSize = 18.sp)
            OutlinedTextField(
                value = taskTitle,
                onValueChange = {taskTitle=it},
                placeholder = {Text("Enter task title")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Spacer(Modifier.height(10.dp))
            Text("Description", fontSize = 18.sp)
            OutlinedTextField(
                value = taskDescription,
                onValueChange = {taskDescription=it},
                placeholder = {Text("Enter task description")},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
            )
            Divider(Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier.clickable{
                        showDatePicker=true
                    }
                ) {
                    Row {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "DuoDateIcon")
                        Text("Duo Date")
                    }
                    Text("Select date")
                }
                Column(
                    modifier = Modifier.clickable{

                    }
                ) {
                    Row {
                        Icon(painter = painterResource(id=R.drawable.flag), contentDescription = "Flagicon")
                        Text("Priority")
                    }
                    Text("Set priority")
                }
            }
            Button(
                onClick = {
                   if(taskDescription.isNotEmpty() && taskTitle.isNotEmpty() && duoDate!=null)
                        viewModel.addTask(TaskEntity(
                            title = taskTitle,
                            description = taskDescription,
                            duoDate =duoDate!!,
                            createdAt = System.currentTimeMillis(),
                            isCompleted = false
                        ))
                    else Toast.makeText(context, "Invalid title or description or selected time", Toast.LENGTH_SHORT).show()

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text("Add Task")
            }
        }
    }

    if (showDatePicker){
        DatePickerDialog(
            onDismissRequest = {showDatePicker=false},
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDate=datePickerState.selectedDateMillis
                        showDatePicker=false
                        showTimePicker=true
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker=false
                    }
                ) {
                Text("NO")
                }
            }
        ) {
            DatePicker(state=datePickerState)
        }
    }
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = {showTimePicker=false},
            confirmButton = {
                TextButton(
                    onClick = {
                        selecteHour=state.hour
                        selecteMinut=state.minute
                        duoDate=combineDateTime(selectedDate!!,selecteHour,selecteMinut)
                        showTimePicker=false
                    }
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {}
                ) {
                    Text("Cancel")
                }
            },
            title = {Text("Select time")},
            text = {
                TimePicker(state = state)
            }
        )
    }
}
fun combineDateTime(
    dateMillis: Long,
    hour: Int,
    minute: Int
): Long {
    return Calendar.getInstance().apply {
        timeInMillis = dateMillis
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }.timeInMillis
}
