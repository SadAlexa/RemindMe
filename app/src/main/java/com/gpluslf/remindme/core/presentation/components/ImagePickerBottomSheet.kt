package com.gpluslf.remindme.core.presentation.components

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.gpluslf.remindme.core.utils.permissions.rememberPermission
import java.io.File

@Composable
fun ImagePickerBottomSheet(
    onSelected: (Uri) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tempPhotoUri by remember { mutableStateOf(value = Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) imageUri = tempPhotoUri
        }
    )

    val cameraPermissionState = rememberPermission(
        permission = Manifest.permission.CAMERA,
        onResult = { permission ->
            if (permission.isGranted) {
                tempPhotoUri = FileProvider.getUriForFile(
                    context,
                    context.applicationContext.packageName + ".provider",
                    File.createTempFile("temp_photo", ".jpg", context.cacheDir)
                )
                cameraLauncher.launch(tempPhotoUri)
            }
        }
    )


    // Create an ActivityResultLauncher for picking images
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                imageUri = uri
            }
        }
    )

    LaunchedEffect(imageUri) {
        val image = imageUri
        if (image != null) {
            onSelected(image)
            imageUri = null
            onDismissRequest()
        }
    }

    CustomBottomSheet(
        onDismissRequest = onDismissRequest,
        onCameraSelected = {
            cameraPermissionState.launchPermissionRequest()
            showDialog = false
        },
        onPhotoPickerSelected = {
            pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            showDialog = false
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    onDismissRequest: () -> Unit,
    onCameraSelected: () -> Unit,
    onPhotoPickerSelected: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextButton(onClick = onCameraSelected) {
                Text("Camera")
            }
            TextButton(onClick = onPhotoPickerSelected) {
                Text("Photo Picker")
            }
        }
    }
}
