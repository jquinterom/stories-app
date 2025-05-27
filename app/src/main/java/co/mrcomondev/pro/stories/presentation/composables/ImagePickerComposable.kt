package co.mrcomondev.pro.stories.presentation.composables

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.Icon

/**
 * Created by gesoft
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ImagePicker(
  modifier: Modifier = Modifier,
  onImageSelected: (Uri) -> Unit
) {
  val context = LocalContext.current

  var hasPermission by remember {
    // Initialize by checking the current permission status
    mutableStateOf(
      ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_MEDIA_IMAGES
      ) == PackageManager.PERMISSION_GRANTED
    )
  }

  val imagePickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent(),
    onResult = { uri ->
      uri?.let(onImageSelected)
    }
  )

  // Lanzador para permisos
  val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    hasPermission = isGranted
    if (isGranted) {
      imagePickerLauncher.launch("image/*")
    }
  }

  Box(
    modifier = modifier
      .clickable {
        val currentPermissionStatus = ContextCompat.checkSelfPermission(
          context,
          Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED

        hasPermission = currentPermissionStatus

        if (hasPermission) {
          imagePickerLauncher.launch("image/*")
        } else {
          permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }
      }
      .background(Color.White.copy(alpha = 0.2f), CircleShape),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      imageVector = Icons.Default.Add,
      contentDescription = "Select image",
      modifier = Modifier.size(24.dp)
    )
  }
}