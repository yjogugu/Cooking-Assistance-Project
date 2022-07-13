package com.taijoo.cookingassistance.view.ui

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.view.ui.ui.theme.CookingAssistanceTheme

//@Composable
//fun Header(activity: Activity){
//    CookingAssistanceTheme{
//        Surface(color = MaterialTheme.colors.background) {
//            Row(modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp, 5.dp, 5.dp, 5.dp)) {
//
//                Button(onClick = { activity.onBackPressed() } ,
//                    colors =  ButtonDefaults.buttonColors(Color.Transparent) , elevation = ButtonDefaults.elevation(
//                        defaultElevation = 0.dp ,
//                        pressedElevation = 0.dp ,
//                        disabledElevation = 0.dp
//                    )
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_back) ,
//                        contentDescription = "header" , colorFilter = ColorFilter.tint(
//                            colorResource(id = R.color.color_000000)))
//                }
//
//
//            }
//        }
//    }
//}


//@Preview(showBackground = true , name = "Light Mode")
//@Preview(showBackground = true , name = "Dark Mode" , uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Header(onSetDeleteDialog : () -> Unit ){
    val activity = (LocalContext.current as Activity)

    CookingAssistanceTheme{
        Surface(color = MaterialTheme.colors.background) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 5.dp, 5.dp, 5.dp)) {

                Button(onClick = {activity.onBackPressed()} ,
                    colors =  ButtonDefaults.buttonColors(Color.Transparent) , elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp ,
                        pressedElevation = 0.dp ,
                        disabledElevation = 0.dp
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_24) ,
                        contentDescription = "header" , colorFilter = ColorFilter.tint(
                            colorResource(id = R.color.color_000000)))
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    onSetDeleteDialog()
                },
                    colors =  ButtonDefaults.buttonColors(Color.Transparent) , elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp ,
                        pressedElevation = 0.dp ,
                        disabledElevation = 0.dp
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_delete) ,
                        contentDescription = "header" , colorFilter = ColorFilter.tint(
                            colorResource(id = R.color.color_000000)))
                }


            }
        }
    }
}