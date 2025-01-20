//package com.gpluslf.remindme.core.presentation.components
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.core.content.FileProvider
//import java.io.File
//
//@Composable
//fun imagePickerDialog(
//    onSelected: (Uri) -> Unit,
//    onDismissRequest: () -> Unit
//) {
//    var showDialog by remember { mutableStateOf(false) }
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//
//    val context = LocalContext.current
//
//
//    // Create an ActivityResultLauncher for taking pictures
//    val takePictureLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture(),
//        onResult = { uri ->
//            if (uri != null) {
//                imageUri = uri
//            }
//        }
//    )
//
//    // Create an ActivityResultLauncher for picking images
//    val pickImageLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = { uri ->
//            if (uri != null) {
//                imageUri = uri
//            }
//        }
//    )
//
//    Button(onClick = { showDialog = true }) {
//        Text("Change Profile Image")
//    }
//
//    if (showDialog) {
//        ChangeProfileBottomSheet(
//            onDismissRequest = onDismissRequest,
//            onCameraSelected = {
//                // Create a file to save the image
//                val imageFile = File.createTempFile("image", "jpg", context.cacheDir)
//                imageUri = FileProvider.getUriForFile(
//                    context,
//                    "${context.packageName}.RemindMe",
//                    imageFile
//                )
//                takePictureLauncher.launch(Uri.parse(imageUri.toString()))
//                showDialog = false
//            },
//            onPhotoPickerSelected = {
//                pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//                showDialog = false
//            }
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChangeProfileBottomSheet(
//    onDismissRequest: () -> Unit,
//    onCameraSelected: () -> Unit,
//    onPhotoPickerSelected: () -> Unit
//) {
//    val bottomSheetState = rememberModalBottomSheetState()
//
//    ModalBottomSheet(
//        sheetState = bottomSheetState,
//        onDismissRequest = onDismissRequest
//    ) {
//        Column(
//            modifier = Modifier.padding(20.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            TextButton(onClick = onCameraSelected) {
//                Text("Camera")
//            }
//            TextButton(onClick = onPhotoPickerSelected) {
//                Text("Photo Picker")
//            }
//        }
//    }
//}
